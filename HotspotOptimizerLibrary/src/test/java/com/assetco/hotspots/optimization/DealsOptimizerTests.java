package com.assetco.hotspots.optimization;

import com.assetco.search.results.Asset;
import com.assetco.search.results.AssetVendor;
import com.assetco.search.results.HotspotKey;
import org.junit.jupiter.api.Test;

public class DealsOptimizerTests extends OptimizerTests {

    /*
     *      Single asset from partner-level vendor in search results
     */
    @Test
    public void assetWithInsignificantPerformanceFromPartnerVendorShouldNotBeInDeal() {
        final String revenue = "9.99";
        final String royalties = "7.0";
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, royalties, revenue, false);
        whenOptimize();
        thenHotspotDoesNotHave(HotspotKey.Deals, asset);
    }

    @Test
    public void assetWithElevatedPerformanceFromPartnerVendorShouldBeInDeal() {
        final String revenue = "10.0";
        final String royalties = "7.0";
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, royalties, revenue, false);
        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, asset);
    }

    @Test
    public void assetWithHighPerformanceFromPartnerVendorShouldBeInDeal() {
        final String revenue = "2.0";
        final String royalties = "1.0";
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, royalties, revenue, false);
        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, asset);
    }

    /*
     *      assets from partner and gold vendors in search results
     */
    @Test
    public void neitherGoldNorPartnerAssetQualifiesForDeal() {
        // revenue is too low
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "9.99", false);

        // revenue is high enough but is not deal eligible
        Asset goldAsset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "0.0", "1000.0", false);

        whenOptimize();
        thenHotspotDoesNotHave(HotspotKey.Deals, partnerAsset);
        thenHotspotDoesNotHave(HotspotKey.Deals, goldAsset);
    }

    @Test
    public void partnerAssetQualifiesForDealButGoldDoesNotBecauseItIsIneligible() {
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "10.0", false);

        // revenue is high enough but is not deal eligible
        Asset goldAsset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "0.0", "1000.0", false);

        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, partnerAsset);
        thenHotspotDoesNotHave(HotspotKey.Deals, goldAsset);
    }

    @Test
    public void partnerAssetQualifiesForDealButGoldDoesNotBecauseRevenueIsTooLow() {
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "10.0", false);

        // revenue is not high enough
        Asset goldAsset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "0.0", "999.99", true);

        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, partnerAsset);
        thenHotspotDoesNotHave(HotspotKey.Deals, goldAsset);
    }

    @Test
    public void goldAssetQualifiesForDealButPartnetDoesNot() {
        // revenue is too low
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "9.99", false);

        // revenue is high enough but is not deal eligible
        Asset goldAsset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "0.0", "1000.0", true);

        whenOptimize();
        thenHotspotDoesNotHave(HotspotKey.Deals, partnerAsset);
        thenHotspotHas(HotspotKey.Deals, goldAsset);
    }

    @Test
    public void bothGoldAndPartnerAssetsQualifyForDeal() {
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "10.0", false);
        Asset goldAsset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "0.0", "1000.0", true);

        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, partnerAsset);
        thenHotspotHas(HotspotKey.Deals, goldAsset);
    }

    /*
     *      assets from partner and silver vendors in search results
     */
    @Test
    public void partnerAssetQualifiesButSilverDoesNotBecauseItIsNotEligible() {
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "10.0", false);

        // volume and performance are ok, but not eligible
        Asset silverAsset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "0.0", "10000.0", false);

        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, partnerAsset);
        thenHotspotDoesNotHave(HotspotKey.Deals, silverAsset);
    }

    @Test
    public void partnerAssetQualifiesButSilverDoesNotBecauseVolumeIsLow() {
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "10.0", false);

        // volume and performance are ok, but not eligible
        Asset silverAsset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "0.0", "9999.99", true);

        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, partnerAsset);
        thenHotspotDoesNotHave(HotspotKey.Deals, silverAsset);
    }

    @Test
    public void partnerAssetQualifiesButSilverDoesNotBecausePerformanceIsLow() {
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "10.0", false);

        // volume is low
        Asset silverAsset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "2500.01", "10000.0", true);

        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, partnerAsset);
        thenHotspotDoesNotHave(HotspotKey.Deals, silverAsset);
    }

    @Test
    public void partnerAndSilverAssetsQualify() {
        Asset partnerAsset = givenAssetWith30DayProfitabilityAndDealEligibility(partnerVendor, "7.0", "10.0", false);
        Asset silverAsset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "2500.00", "10000.0", true);

        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, partnerAsset);
        thenHotspotHas(HotspotKey.Deals, silverAsset);
    }

    /*
     *      single asset from gold vendor in search results
     */
    @Test
    public void assetWithElevatedPerformanceAndVolumeShouldNotBeInDealIfIneligible() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "700.0", "1000.0", false);
        whenOptimize();
        thenHotspotDoesNotHave(HotspotKey.Deals, asset);
    }

    @Test
    public void assetWithElevatedPerformanceAndVolumeShouldBeInDealIfEligible() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "700.0", "1000.0", true);
        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, asset);
    }

    @Test
    public void assetWithHighPerformanceShouldBeInDealEvenIfIneligible() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "500.0", "1000.0", false);
        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, asset);
    }

    /*
     *      assets from gold and silver vendors in search results
     */
    @Test
    public void goldAssetQualifiesButSilverDoesNotBecauseItIsIneligible() {
        Asset goldAsset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "500.0", "1000.0", true);
        Asset silverAsset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "750.0", "1500.0", false);
        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, goldAsset);
        thenHotspotDoesNotHave(HotspotKey.Deals, silverAsset);
    }

    @Test
    public void goldAssetQualifiesButSilverDoesNotBecauseVolumeIsLow() {
        Asset goldAsset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "500.0", "1000.0", true);
        Asset silverAsset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "500.0", "1000.0", true);
        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, goldAsset);
        thenHotspotDoesNotHave(HotspotKey.Deals, silverAsset);
    }

    @Test
    public void goldAndSilverAssetsBothQualify() {
        Asset goldAsset = givenAssetWith30DayProfitabilityAndDealEligibility(goldVendor, "500.0", "1000.0", true);
        Asset silverAsset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "750.0", "1500.0", true);
        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, goldAsset);
        thenHotspotHas(HotspotKey.Deals, silverAsset);
    }

    /*
     *      single silver asset in search results
     */
    @Test
    public void assetWithLessThanHighPerformanceFromSilverVendorShouldNotBeInDealIfIneligible() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "750.0", "1000.0", false);
        whenOptimize();
        thenHotspotDoesNotHave(HotspotKey.Deals, asset);
    }

    @Test
    public void assetWithQualifyingPerformanceFromSilverVendorShouldBeInDealEvenIfIneligible() {
        Asset asset = givenAssetWith30DayProfitabilityAndDealEligibility(silverVendor, "750.0", "1500.0", false);
        whenOptimize();
        thenHotspotHas(HotspotKey.Deals, asset);
    }
}
