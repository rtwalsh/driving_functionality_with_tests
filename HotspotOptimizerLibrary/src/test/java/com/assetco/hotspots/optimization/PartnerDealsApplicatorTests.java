package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import static com.assetco.hotspots.optimization.ActivityLevel.*;
import static com.assetco.search.results.AssetVendorRelationshipLevel.*;

// Identifying the "patterns" in testing allowed us to boil the concrete tests down to just sets of parameters.
// This is the most complex case, it has two sets of strict rules, a relaxed rule, and (of course) it dumps all
// the basic-level assets.
public class PartnerDealsApplicatorTests extends DealsApplicatorTests {
    public static class ForBasicAssets extends ExcludedAssetsDealsApplicatorTests {
        @Override
        protected AssetVendorRelationshipLevel getExcludedCategoryOfVendors() {
            return Basic;
        }

        @Override
        protected AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel() {
            return Partner;
        }
    }

    public static class ForSilverAssets extends StrictRuleDealsApplicatorTests {

        @Override
        protected ActivityLevel getMinimumPerformance() {
            return Extreme;
        }

        @Override
        protected ActivityLevel getMinimumVolume() {
            return Extreme;
        }

        @Override
        protected AssetVendorRelationshipLevel getAssetVendorLevel() {
            return Silver;
        }

        @Override
        protected AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel() {
            return Partner;
        }
    }

    public static class ForGoldAssets extends StrictRuleDealsApplicatorTests {
        @Override
        protected ActivityLevel getMinimumPerformance() {
            return High;
        }

        @Override
        protected ActivityLevel getMinimumVolume() {
            return Elevated;
        }

        @Override
        protected AssetVendorRelationshipLevel getAssetVendorLevel() {
            return Gold;
        }

        @Override
        protected AssetVendorRelationshipLevel getSearchResultMaximumVendorLevel() {
            return Partner;
        }
    }

    public static class ForPartnerAssets extends DealsApplicatorTests {
        @BeforeEach
        public void setup() {
            baseSetup();
            setGoverningRelationshipLevel(Partner);
            givenAssetVendorLevel(Partner);
        }

        @Test
        public void elevatedPerformance() {
            givenPerformance(Elevated);

            whenApplyDeals();

            thenAssetIsInDeals(true);
        }

        @Test
        public void insignificantPerformance() {
            givenPerformance(Insignificant);

            whenApplyDeals();

            thenAssetIsInDeals(false);
        }
    }
}
