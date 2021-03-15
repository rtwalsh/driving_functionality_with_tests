package com.assetco.hotspots.optimization;

import com.assetco.search.results.Asset;
import com.assetco.search.results.HotspotKey;
import com.assetco.search.results.SearchResults;

public class AssetDealsScoreCalculator {

    private SearchResults searchResults;
    private AssetMeasurements measurements;

    public AssetDealsScoreCalculator(SearchResults theSearchResults) {
        searchResults = theSearchResults;
        measurements = new AssetMeasurements();
    }

    public int computeScoreForAsset(Asset asset) {
        ActivityLevel performance = measurements.getAssetPerformance(asset);
        ActivityLevel volume = measurements.getAssetVolume(asset);
        return calculatePerformanceScore(performance) + calculateVolumeScore(volume) + recommendedDealBonus(asset);
    }

    public int recommendedDealBonus(Asset asset) {
        boolean isRecommendedDeal = searchResults.getHotspot(HotspotKey.Deals).getMembers().contains(asset);
        return (isRecommendedDeal ? 15 : 0);
    }

    public int calculatePerformanceScore(ActivityLevel performance) {
        if (ActivityLevel.Extreme.isLessThanOrEqualTo(performance)) {
            return 30;
        }

        if (ActivityLevel.High.isLessThanOrEqualTo(performance)) {
            return 5;
        }

        if (ActivityLevel.Elevated.isLessThanOrEqualTo(performance)) {
            return 2;
        }

        return 0;
    }

    public int calculateVolumeScore(ActivityLevel volume) {
        if (ActivityLevel.Extreme.isLessThanOrEqualTo(volume)) {
            return 10;
        }

        if (ActivityLevel.High.isLessThanOrEqualTo(volume)) {
            return 2;
        }

        if (ActivityLevel.Elevated.isLessThanOrEqualTo(volume)) {
            return 1;
        }

        return 0;
    }
}
