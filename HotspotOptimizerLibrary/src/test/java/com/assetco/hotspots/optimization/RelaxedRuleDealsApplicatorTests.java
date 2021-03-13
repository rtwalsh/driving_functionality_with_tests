package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

// There was a lot of repetition in the tests, especially after adding the partner-level strategy
// in order to keep the test code maintainable, it was necessary to refactor the duplication away.
// I am aware of the irony in repeating this comment thrice.
public abstract class RelaxedRuleDealsApplicatorTests extends DealsApplicatorTests {
    @BeforeEach
    public void setup() {
        baseSetup();
        setGoverningRelationshipLevel(getSearchResultMaximumVendorLevel());
    }

    @Test
    public void dealEligibleSufficientPerformanceSufficientVolume() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(true);
        givenPerformance(getMinimumPerformance());
        givenVolume(getMinimumVolume());

        whenApplyDeals();

        thenAssetIsInDeals(true);
    }

    @Test
    public void dealEligibleInsufficientPerformance() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(true);
        givenPerformance(getMinimumPerformance().predecessor());
        givenVolume(getMinimumVolume());

        whenApplyDeals();

        thenAssetIsInDeals(false);
    }

    @Test
    public void dealEligibleInsufficientVolume() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(false);
        givenPerformance(getMinimumPerformance());
        givenVolume(getMinimumVolume().predecessor());

        whenApplyDeals();

        thenAssetIsInDeals(false);
    }

    @Test
    public void dealIneligibleWithPerformanceOverride() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(false);
        givenPerformance(getDealEligibilityOverridePerformance());
        givenVolume(getMinimumVolume());

        whenApplyDeals();

        thenAssetIsInDeals(true);
    }

    @Test
    public void dealEligiblePerformanceOverrideInsufficientVolume() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(true);
        givenPerformance(getDealEligibilityOverridePerformance());
        givenVolume(getMinimumVolume().predecessor());

        whenApplyDeals();

        thenAssetIsInDeals(false);
    }

    protected abstract ActivityLevel getDealEligibilityOverridePerformance();

    protected abstract ActivityLevel getMinimumVolume();

    protected abstract ActivityLevel getMinimumPerformance();

    protected abstract AssetVendorRelationshipLevel getAssetVendorLevel();

    protected abstract AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel();
}
