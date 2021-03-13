package com.assetco.hotspots.optimization;

import com.assetco.hotspots.optimization.*;
import org.junit.jupiter.api.*;

import static com.assetco.search.results.HotspotKey.HighValue;

public class SalesInfoBasedPinningTests extends OptimizerTests {
    // Couldn't get away with just one family of cases.
    @Test
    public void highValueDetection() {
        highValueAssetDetectionCase(0, "0", "5000.00", 1);
        highValueAssetDetectionCase(0, "2500", "5000.00", 0);
        highValueAssetDetectionCase(0, "500", "5000.00", 1);
        highValueAssetDetectionCase(0, "1250", "5000.00", 0);
        highValueAssetDetectionCase(0, "1000.1", "5000.00", 0);
        highValueAssetDetectionCase(0, "1000", "5000.00", 1);
        highValueAssetDetectionCase(0, "100", "500.00", 0);
        highValueAssetDetectionCase(1, "100", "500.00", 1);
        highValueAssetDetectionCase(2, "100", "500.00", 2);
    }

    // Creating satisfactory mutation testing results demanded several families of pinning scenarios.
    @Test
    public void goodLongTermTrafficDetection() {
        goodLongTermTrafficDetectionCase(0, 0, 0, 0);
        goodLongTermTrafficDetectionCase(0, 10000, 10000, 0);
        goodLongTermTrafficDetectionCase(0, 50000, 50000, 1);
        goodLongTermTrafficDetectionCase(0, 50000, 10000, 1);
        goodLongTermTrafficDetectionCase(0, 50000, 900, 1);
        goodLongTermTrafficDetectionCase(0, 50000, 500, 1);
        goodLongTermTrafficDetectionCase(0, 50000, 250, 0);
        goodLongTermTrafficDetectionCase(0, 50000, 499, 1);
        goodLongTermTrafficDetectionCase(0, 50000, 399, 0);
        goodLongTermTrafficDetectionCase(0, 50000, 400, 1);
        goodLongTermTrafficDetectionCase(1, 50000, 400, 2);
        goodLongTermTrafficDetectionCase(1, 50000, 5000, 2);
    }

    // In fact, analyzing mutation testing helped DISCOVER what those families ought to be.
    @Test
    public void goodShortTermTrafficDetection() {
        goodShortTermTrafficDetectionCase(0, 0, 0, 0);
        goodShortTermTrafficDetectionCase(0, 10000, 10000, 1);
        goodShortTermTrafficDetectionCase(1, 10000, 10000, 2);
        goodShortTermTrafficDetectionCase(0, 5000, 5000, 1);
        goodShortTermTrafficDetectionCase(0, 1000, 1000, 1);
        goodShortTermTrafficDetectionCase(0, 999, 999, 0);
        goodShortTermTrafficDetectionCase(0, 1000, 999, 1);
        goodShortTermTrafficDetectionCase(0, 1000, 20, 1);
        goodShortTermTrafficDetectionCase(0, 1000, 10, 1);
        goodShortTermTrafficDetectionCase(0, 1000, 5, 1);
        goodShortTermTrafficDetectionCase(0, 1000, 4, 0);
        goodShortTermTrafficDetectionCase(0, 10000, 49, 0);
    }

    private void goodLongTermTrafficDetectionCase(int partnerAssetCount, int shown, int sold, int expectedHighValueAssetCount) {
        setUp();
        givenAssetsInResultsWithVendor(partnerAssetCount, partnerVendor);
        givenAssetWith30DayTraffic(shown, sold);

        whenOptimize();

        thenHotSpotCountIs(HighValue, expectedHighValueAssetCount);
    }

    private void goodShortTermTrafficDetectionCase(int partnerAssetCount, int shown, int sold, int expectedHighValueAssetCount) {
        setUp();
        givenAssetsInResultsWithVendor(partnerAssetCount, partnerVendor);
        givenAssetWith24HourTraffic(shown, sold);

        whenOptimize();

        thenHotSpotCountIs(HighValue, expectedHighValueAssetCount);
    }

    private void highValueAssetDetectionCase(int partnerVendors, String royalties, String revenue, int highValueAssets) {
        setUp();
        givenAssetsInResultsWithVendor(partnerVendors, partnerVendor);
        givenAssetWith30DayProfitabilityAndDealEligibility(basicVendor, royalties, revenue, false);

        whenOptimize();

        thenHotSpotCountIs(HighValue, highValueAssets);
    }
}
