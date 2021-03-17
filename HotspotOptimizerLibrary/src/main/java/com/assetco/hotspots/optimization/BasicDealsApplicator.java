package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

public class BasicDealsApplicator extends GateBasedDealsApplicator {
    public BasicDealsApplicator(AssetAssessments assessments, AssetMeasurements measurements) {
        super(assessments, measurements);
    }

    @Override
    public void applyDeals(SearchResults results, Asset asset) {
    }
}
