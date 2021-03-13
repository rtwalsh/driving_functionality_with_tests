package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import static com.assetco.hotspots.optimization.ActivityLevel.*;
import static com.assetco.search.results.AssetVendorRelationshipLevel.*;
import static com.assetco.search.results.AssetVendorRelationshipLevel.Basic;
import static com.assetco.search.results.AssetVendorRelationshipLevel.Silver;

// Identifying the "patterns" in testing allowed us to boil the concrete tests down to just sets of parameters.
// You can see a mixture of all the rule types expressed here.
public class GoldDealsApplicatorTests {
    public static class ForGoldAssets extends RelaxedRuleDealsApplicatorTests {
        @Override
        protected AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel() {
            return Gold;
        }

        @Override
        protected ActivityLevel getMinimumPerformance() {
            return Elevated;
        }

        @Override
        protected AssetVendorRelationshipLevel getAssetVendorLevel() {
            return Gold;
        }

        @Override
        protected ActivityLevel getDealEligibilityOverridePerformance() {
            return High;
        }

        @Override
        protected ActivityLevel getMinimumVolume() {
            return Elevated;
        }

    }

    public static class ForSilverAssets extends StrictRuleDealsApplicatorTests {
        @Override
        protected AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel() {
            return Gold;
        }

        @Override
        protected ActivityLevel getMinimumPerformance() {
            return High;
        }

        @Override
        protected AssetVendorRelationshipLevel getAssetVendorLevel() {
            return Silver;
        }

        @Override
        protected ActivityLevel getMinimumVolume() {
            return High;
        }
    }

    public static class ForBasicAssets extends ExcludedAssetsDealsApplicatorTests {
        @Override
        protected AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel() {
            return Gold;
        }

        @Override
        protected AssetVendorRelationshipLevel getExcludedCategoryOfVendors() {
            return Basic;
        }
    }
}
