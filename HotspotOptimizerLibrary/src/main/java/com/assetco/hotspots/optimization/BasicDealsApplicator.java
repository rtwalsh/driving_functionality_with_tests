package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

import static com.assetco.hotspots.optimization.ActivityLevel.*;
import static com.assetco.search.results.HotspotKey.Deals;

public class BasicDealsApplicator extends DealsApplicator {
    public BasicDealsApplicator(AssetAssessments assessments, AssetMeasurements measurements) {
        super(assessments, measurements);
    }

    @Override
    public void applyDeals(SearchResults results, Asset asset) {
    }
}
