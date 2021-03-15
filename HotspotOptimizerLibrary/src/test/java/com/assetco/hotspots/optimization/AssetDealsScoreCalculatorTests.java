package com.assetco.hotspots.optimization;

import com.assetco.search.results.Asset;
import com.assetco.search.results.HotspotKey;
import com.assetco.search.results.SearchResults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssetDealsScoreCalculatorTests extends OptimizerTests {

    private AssetDealsScoreCalculator calculator;

    @BeforeEach
    public void setUp() {
        super.setUp();
        calculator = new AssetDealsScoreCalculator(searchResults);
    }

    @Test
    public void scoreForInsignificantPerformanceInsignificantVolumeNotRecomendedAssetShouldBe0() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(basicVendor, "8", "10", true);
        assertEquals(0, calculator.computeScoreForAsset(asset));
    }

    @Test
    public void scoreForElevatedPerformanceInsignificantVolumeRecommendedShouldbe17() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "7", "10", true);
        searchResults.getHotspot(HotspotKey.Deals).addMember(asset);
        assertEquals(17, calculator.computeScoreForAsset(asset));
    }

    @Test
    public void scoreForInsignificantPerformanceHighVolumeRecommendedShouldbe17() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "1200", "1500", true);
        searchResults.getHotspot(HotspotKey.Deals).addMember(asset);
        assertEquals(17, calculator.computeScoreForAsset(asset));
    }

    @Test
    public void scoreForExtremePerformanceExtremeVolumeNotRecommendedShouldbe40() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "1", "10000", true);
        assertEquals(40, calculator.computeScoreForAsset(asset));
    }

    @Test
    public void scoreForExtremePerformanceExtremeVolumeRecommendedShouldbe55() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "1", "10000", true);
        searchResults.getHotspot(HotspotKey.Deals).addMember(asset);
        assertEquals(55, calculator.computeScoreForAsset(asset));
    }
}
