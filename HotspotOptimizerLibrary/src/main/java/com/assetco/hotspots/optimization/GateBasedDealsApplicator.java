package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

public abstract class GateBasedDealsApplicator implements DealsApplicator {
    protected final AssetAssessments assessments;
    protected final AssetMeasurements measurements;

    GateBasedDealsApplicator(AssetAssessments assessments, AssetMeasurements measurements) {
        this.assessments = assessments;
        this.measurements = measurements;
    }

}
