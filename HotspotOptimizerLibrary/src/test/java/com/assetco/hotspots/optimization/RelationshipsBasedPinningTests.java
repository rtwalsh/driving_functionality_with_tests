package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RelationshipsBasedPinningTests extends OptimizerTests {
    @Test
    public void assetCountsInAndOut() {
        inputCounts(1, 0, 0, 1, 1, 1).mapToHotspotCounts(0, 0, 3, 2, 1);
        inputCounts(2, 0, 0, 1, 1, 1).mapToHotspotCounts(0, 0, 4, 3, 2);
        inputCounts(2, 2, 0, 1, 1, 1).mapToHotspotCounts(0, 0, 6, 5, 4);
        inputCounts(2, 2, 1, 1, 1, 1).mapToHotspotCounts(3, 0, 7, 6, 5);
        inputCounts(2, 4, 1, 1, 1, 1).mapToHotspotCounts(4, 0, 9, 8, 7);
        inputCounts(3, 0, 0, 1, 1, 1).mapToHotspotCounts(3, 0, 5, 4, 3);
        inputCounts(3, 1, 0, 1, 1, 1).mapToHotspotCounts(3, 0, 6, 5, 4);
        inputCounts(3, 1, 2, 1, 1, 1).mapToHotspotCounts(5, 0, 8, 7, 6);
        inputCounts(5, 0, 0, 1, 1, 1).mapToHotspotCounts(5, 0, 7, 6, 5);
        inputCounts(5, 0, 0, 5, 5, 5).mapToHotspotCounts(5, 0, 15, 10, 5);
        inputCounts(6, 0, 0, 5, 5, 5).mapToHotspotCounts(5, 1, 16, 11, 6);
        inputCounts(6, 0, 0, 10, 10, 10).mapToHotspotCounts(5, 1, 26, 16, 6);
        inputCounts(10, 0, 0, 10, 10, 10).mapToHotspotCounts(5, 5, 30, 20, 10);
    }

    private AssetCountsInputCase inputCounts(int partner1Count, int partner2Count, int partner1SupplementalCount, int goldCount, int silverCount, int basicCount) {
        setUp();
        return new AssetCountsInputCase(partner1Count, partner2Count, partner1SupplementalCount, goldCount, silverCount, basicCount);
    }

    private class AssetCountsInputCase {
        private final int partner1Count;
        private final int partner2Count;
        private final int partner1SupplementalCount;
        private final int goldCount;
        private final int silverCount;
        private final int basicCount;

        private AssetCountsInputCase(int partner1Count, int partner2Count, int partner1SupplementalCount, int goldCount, int silverCount, int basicCount) {
            this.partner1Count = partner1Count;
            this.partner2Count = partner2Count;
            this.partner1SupplementalCount = partner1SupplementalCount;
            this.goldCount = goldCount;
            this.silverCount = silverCount;
            this.basicCount = basicCount;
        }

        private void mapToHotspotCounts(int showcaseCount, int topPicksCount, int foldCount, int highValueCount, int dealsCount) {
            givenAssetsInResultsWithVendor(partner1Count, partnerVendor);
            // Concentrating certain creations into AssetInfoFactory impacted tests that depended upon it
            givenAssetsInResultsWithVendor(partner2Count, AssetInfoFactory.makeVendor(AssetVendorRelationshipLevel.Partner));
            givenAssetsInResultsWithVendor(partner1SupplementalCount, partnerVendor);
            givenAssetsInResultsWithVendor(goldCount, goldVendor);
            givenAssetsInResultsWithVendor(silverCount, silverVendor);
            givenAssetsInResultsWithVendor(basicCount, basicVendor);

            whenOptimize();
    
            thenHotSpotCountIs(HotspotKey.Showcase, showcaseCount);
            thenHotSpotCountIs(HotspotKey.TopPicks, topPicksCount);
            thenHotSpotCountIs(HotspotKey.Fold, foldCount);
            thenHotSpotCountIs(HotspotKey.HighValue, highValueCount);
            thenHotSpotCountIs(HotspotKey.Deals, dealsCount);
            thenHotSpotCountIs(HotspotKey.Highlight, 0);
        }
    }
}
