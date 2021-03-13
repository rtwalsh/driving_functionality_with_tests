package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

public abstract class DealsApplicator {
    protected final AssetAssessments assessments;
    protected final AssetMeasurements measurements;

    DealsApplicator(AssetAssessments assessments, AssetMeasurements measurements) {
        this.assessments = assessments;
        this.measurements = measurements;
    }

    // Another slight change for the better. It may seem like almost nothing but it also was a single,
    // automated refactoring step that I've never seen fail. So the cost was at least as low as the benefit.
    public static DealsApplicator createInstance(AssetVendorRelationshipLevel highestRelationshipLevel, AssetAssessments assessments, AssetMeasurements measurements) {
        switch (highestRelationshipLevel) {
            case Partner:
                return new PartnerDealsApplicator(assessments, measurements);
            case Gold:
                return new GoldDealsApplicator(assessments, measurements);
            case Silver:
                return new SilverDealsApplicator(assessments, measurements);
        }
        return new BasicDealsApplicator(assessments, measurements);
    }

    public abstract void applyDeals(SearchResults results, Asset asset);
}
