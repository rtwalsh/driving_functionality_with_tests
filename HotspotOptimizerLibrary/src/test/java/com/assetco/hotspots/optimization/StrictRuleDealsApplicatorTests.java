package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

// There was a lot of repetition in the tests, especially after adding the partner-level strategy
// in order to keep the test code maintainable, it was necessary to refactor the duplication away.
// I am aware of the irony in repeating this comment thrice.
public abstract class StrictRuleDealsApplicatorTests extends DealsApplicatorTests {
    @BeforeEach
    public void setup() {
        baseSetup();
        setGoverningRelationshipLevel(getSearchResultMaximumVendorLevel());
    }

    @Test
    public void allRequirementsMet() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(true);
        givenPerformance(getMinimumPerformance());
        givenVolume(getMinimumVolume());

        whenApplyDeals();

        thenAssetIsInDeals(true);
    }

    @Test
    public void dealIneligible() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(false);
        givenPerformance(getMinimumPerformance());
        givenVolume(getMinimumVolume());

        whenApplyDeals();

        thenAssetIsInDeals(false);
    }

    @Test
    public void insufficientPerformance() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(true);
        givenPerformance(getMinimumPerformance().predecessor());
        givenVolume(getMinimumVolume());

        whenApplyDeals();

        thenAssetIsInDeals(false);
    }

    @Test
    public void insufficientVolume() {
        givenAssetVendorLevel(getAssetVendorLevel());
        givenDealEligible(true);
        givenPerformance(getMinimumPerformance());
        givenVolume(getMinimumVolume().predecessor());

        whenApplyDeals();

        thenAssetIsInDeals(false);
    }

    protected abstract ActivityLevel getMinimumPerformance();

    protected abstract ActivityLevel getMinimumVolume();

    protected abstract AssetVendorRelationshipLevel getAssetVendorLevel();

    protected abstract AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel();
}
