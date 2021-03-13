package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

// There was a lot of repetition in the tests, especially after adding the partner-level strategy
// in order to keep the test code maintainable, it was necessary to refactor the duplication away.
// I am aware of the irony in repeating this comment thrice.
public abstract class ExcludedAssetsDealsApplicatorTests extends DealsApplicatorTests {
    @BeforeEach
    public void setup() {
        baseSetup();
        setGoverningRelationshipLevel(getSearchResultMaximumVendorLevel());
    }

    @Test
    public void assetIsNotAdded() {
        givenAssetVendorLevel(getExcludedCategoryOfVendors());

        whenApplyDeals();

        thenAssetIsInDeals(false);
    }

    protected abstract AssetVendorRelationshipLevel getExcludedCategoryOfVendors();

    protected abstract AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel();
}
