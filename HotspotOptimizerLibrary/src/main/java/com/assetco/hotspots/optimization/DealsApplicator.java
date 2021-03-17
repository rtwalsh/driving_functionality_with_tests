package com.assetco.hotspots.optimization;

import com.assetco.search.results.Asset;
import com.assetco.search.results.AssetVendorRelationshipLevel;
import com.assetco.search.results.SearchResults;

public interface DealsApplicator {
    // Another slight change for the better. It may seem like almost nothing but it also was a single,
    // automated refactoring step that I've never seen fail. So the cost was at least as low as the benefit.
    static DealsApplicator createInstance(AssetVendorRelationshipLevel highestRelationshipLevel, AssetAssessments assessments, AssetMeasurements measurements) {
        switch (highestRelationshipLevel) {
            case Partner:
                return new PartnerGateBasedDealsApplicator(assessments, measurements);
            case Gold:
                return new GoldGateBasedDealsApplicator(assessments, measurements);
            case Silver:
                return new SilverGateBasedDealsApplicator(assessments, measurements);
        }
        return new BasicDealsApplicator();
    }

    void applyDeals(SearchResults results, Asset asset);
}
