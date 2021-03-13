package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;

// You may not have done this. A lot of people don't. I find it useful to take commonalities between
// groups of tests and make sure they aren't repeated. It usually provides double benefit in the case
// of common implementation details:
//   1. It ensures I don't have to find a bunch of places to make one change.
//   2. It moves to a different spot language and details that might distract from what a test truly specifies.
public class DealsApplicatorTests {
    protected AssetAssessments assessments;
    protected AssetMeasurements measurements;
    protected DealsApplicator applicator;
    protected Asset asset;
    protected SearchResults searchResults;

    protected void setGoverningRelationshipLevel(AssetVendorRelationshipLevel level) {
        applicator = DealsApplicator.createInstance(level, assessments, measurements);
    }

    public void baseSetup() {
        assessments = mock(AssetAssessments.class);
        measurements = mock(AssetMeasurements.class);
        searchResults = new SearchResults();
    }

    // Here's a good example of removing implementation details from the test.
    // Each test can specify WHAT it needs rather than how to get it.
    // How is then implemented in a single place, below, and thus not permitted to diffuse into any tests'
    // explanation of requirements.
    protected void givenAssetVendorLevel(AssetVendorRelationshipLevel basic) {
        asset = AssetInfoFactory.getAsset(basic);
    }

    protected void thenAssetIsInDeals(boolean expected) {
        Assertions.assertEquals(expected, searchResults.getHotspot(HotspotKey.Deals).getMembers().contains(asset));
    }

    protected void whenApplyDeals() {
        applicator.applyDeals(searchResults, asset);
    }

    protected void givenDealEligible(boolean value) {
        when(assessments.isAssetDealEligible(asset)).thenReturn(value);
    }

    protected void givenPerformance(ActivityLevel value) {
        when(measurements.getAssetPerformance(asset)).thenReturn(value);
    }

    protected void givenVolume(ActivityLevel value) {
        when(measurements.getAssetVolume(asset)).thenReturn(value);
    }
}
