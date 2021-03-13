package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

import static com.assetco.hotspots.optimization.ActivityLevel.*;
import static com.assetco.search.results.HotspotKey.Deals;

public class SilverDealsApplicator extends DealsApplicator {
    public SilverDealsApplicator(AssetAssessments assessments, AssetMeasurements measurements) {
        super(assessments, measurements);
    }

    @Override
    public void applyDeals(SearchResults results, Asset asset) {
        ActivityLevel assetPerformance = measurements.getAssetPerformance(asset);
        ActivityLevel assetVolume = measurements.getAssetVolume(asset);

        if (asset.getVendor().getRelationshipLevel() != AssetVendorRelationshipLevel.Silver)
            return;

        if (assessments.isAssetDealEligible(asset) || High.isLessThanOrEqualTo(assetPerformance))
            if (Elevated.isLessThanOrEqualTo(assetPerformance) && High.isLessThanOrEqualTo(assetVolume))
                results.getHotspot(Deals).addMember(asset);
    }
}
