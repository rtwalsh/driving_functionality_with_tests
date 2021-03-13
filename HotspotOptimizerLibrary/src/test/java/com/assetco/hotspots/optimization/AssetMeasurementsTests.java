package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import static com.assetco.hotspots.optimization.ActivityLevel.*;
import static com.assetco.hotspots.optimization.AssetInfoFactory.*;
import static com.assetco.search.results.AssetVendorRelationshipLevel.*;

public class AssetMeasurementsTests {

    private Asset asset;
    private AssetMeasurements measurements;
    private ActivityLevel measured;

    @BeforeEach
    public void setup() {
        measurements = new AssetMeasurements();
    }

    @Test
    public void minimumVolume() {
        // Even if only one class needs it, there is still value in decoupling the "what" from the "how".
        givenRevenue("0");

        whenMeasureVolume();

        thenMeasuredActivityLevelIs(Insignificant);
    }

    @Test
    public void maximumInsignificantVolume() {
        givenRevenue("999.99");

        whenMeasureVolume();

        thenMeasuredActivityLevelIs(Insignificant);
    }

    @Test
    public void minimumElevatedVolume() {
        givenRevenue("1000.00");

        whenMeasureVolume();

        thenMeasuredActivityLevelIs(Elevated);
    }

    @Test
    public void maximumElevatedVolume() {
        givenRevenue("1499.99");

        whenMeasureVolume();

        thenMeasuredActivityLevelIs(Elevated);
    }

    @Test
    public void minimumHighVolume() {
        givenRevenue("1500.00");

        whenMeasureVolume();

        thenMeasuredActivityLevelIs(High);
    }

    @Test
    public void maximumHighVolume() {
        givenRevenue("9999.99");

        whenMeasureVolume();

        thenMeasuredActivityLevelIs(High);
    }

    @Test
    public void minimumExtremeVolume() {
        givenRevenue("10000.00");

        whenMeasureVolume();

        thenMeasuredActivityLevelIs(Extreme);
    }

    @Test
    public void maximumInsignificantPerformance() {
        givenRevenueAndRoyalties("1000.00", "700.01");

        whenMeasurePerformance();

        thenMeasuredActivityLevelIs(Insignificant);
    }

    @Test
    public void minimumElevatedPerformance() {
        givenRevenueAndRoyalties("1000.00", "700.00");

        whenMeasurePerformance();

        thenMeasuredActivityLevelIs(Elevated);
    }

    @Test
    public void maximumElevatedPerformance() {
        givenRevenueAndRoyalties("1000.00", "500.01");

        whenMeasurePerformance();

        thenMeasuredActivityLevelIs(Elevated);
    }

    @Test
    public void minimumHighPerformance() {
        givenRevenueAndRoyalties("1000.00", "500.00");

        whenMeasurePerformance();

        thenMeasuredActivityLevelIs(High);
    }

    @Test
    public void maximumHighPerformance() {
        givenRevenueAndRoyalties("1000.00", "250.01");

        whenMeasurePerformance();

        thenMeasuredActivityLevelIs(High);
    }

    @Test
    public void minimumExtremePerformance() {
        givenRevenueAndRoyalties("1000.00", "250.00");

        whenMeasurePerformance();

        thenMeasuredActivityLevelIs(Extreme);
    }

    private void whenMeasurePerformance() {
        measured = measurements.getAssetPerformance(asset);
    }

    private void thenMeasuredActivityLevelIs(ActivityLevel expected) {
        Assertions.assertEquals(expected, measured);
    }

    private void whenMeasureVolume() {
        measured = measurements.getAssetVolume(asset);
    }

    private void givenRevenue(String revenue) {
        givenRevenueAndRoyalties(revenue, "0");
    }

    private void givenRevenueAndRoyalties(String revenue, String royalties) {
        asset = getAssetWith30DayProfitability(makeVendor(Basic), royalties, revenue);
    }
}
