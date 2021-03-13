package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import static com.assetco.hotspots.optimization.ActivityLevel.*;
import static com.assetco.search.results.AssetVendorRelationshipLevel.*;

// Identifying the "patterns" in testing allowed us to boil the concrete tests down to just sets of parameters.
// This is the simplest case, it has a single "relaxed" rule and (of course) it dumps all
// the basic-level assets.
public class SilverDealsApplicatorTests extends DealsApplicatorTests {
    public static class ForBasicAssets extends ExcludedAssetsDealsApplicatorTests {
        @Override
        protected AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel() {
            return Silver;
        }

        @Override
        protected AssetVendorRelationshipLevel getExcludedCategoryOfVendors() {
            return Basic;
        }
    }

    public static class ForSilverAssets extends RelaxedRuleDealsApplicatorTests {
        @Override
        protected AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel() {
            return Silver;
        }

        @Override
        protected AssetVendorRelationshipLevel getAssetVendorLevel() {
            return Silver;
        }

        @Override
        protected ActivityLevel getMinimumPerformance() {
            return Elevated;
        }

        @Override
        protected ActivityLevel getDealEligibilityOverridePerformance() {
            return High;
        }

        @Override
        protected ActivityLevel getMinimumVolume() {
            return High;
        }
    }
}
