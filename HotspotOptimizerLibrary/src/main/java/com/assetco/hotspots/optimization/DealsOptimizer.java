package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

class DealsOptimizer {
    private final AssetMeasurements measurements = new AssetMeasurements();

    public void optimize(SearchResults results, AssetAssessments assessments) {
        var highestRelationshipLevelOptional = results.getFound().stream().map(a -> a.getVendor().getRelationshipLevel())
                .max(Enum::compareTo);

        if (highestRelationshipLevelOptional.isPresent()) {
            var highestRelationshipLevel = highestRelationshipLevelOptional.get();
            var dealsApplicator = DealsApplicator.createInstance(highestRelationshipLevel, assessments, measurements);
            for (var asset : results.getFound())
                dealsApplicator.applyDeals(results, asset);
        }
    }
}
