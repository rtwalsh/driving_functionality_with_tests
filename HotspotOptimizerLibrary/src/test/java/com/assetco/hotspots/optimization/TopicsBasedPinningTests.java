package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;
import org.junit.jupiter.api.*;

public class TopicsBasedPinningTests extends OptimizerTests{
    @Test
    public void topicsToShowcaseMappings() {
        topicsToShowcaseMappingsCase(0, 0, 0, 0, basicVendor, 0, 0, 0);
        topicsToShowcaseMappingsCase(1, 2, 3, 0, basicVendor, 0, 1, 0);
        topicsToShowcaseMappingsCase(3, 2, 3, 0, basicVendor, 0, 3, 0);
        topicsToShowcaseMappingsCase(5, 2, 3, 0, basicVendor, 0, 5, 5);
        topicsToShowcaseMappingsCase(6, 2, 3, 0, basicVendor, 0, 5, 6);
        topicsToShowcaseMappingsCase(2, 2, 2, 1, basicVendor, 0, 3, 0);
        topicsToShowcaseMappingsCase(0, 2, 2, 1, basicVendor, 0, 2, 0);
        topicsToShowcaseMappingsCase(0, 1, 1, 3, basicVendor, 0, 3, 1);
        topicsToShowcaseMappingsCase(0, 2, 2, 3, basicVendor, 0, 2, 0);
        topicsToShowcaseMappingsCase(6, 2, 3, 0, partnerVendor, 1, 5, 6);
        topicsToShowcaseMappingsCase(6, 2, 3, 0, partnerVendor, 5, 5, 6);
        topicsToShowcaseMappingsCase(0, 2, 3, 0, partnerVendor, 5, 2, 0);
        topicsToShowcaseMappingsCase(0, 2, 0, 0, partnerVendor, 5, 2, 0);
        topicsToShowcaseMappingsCase(1, 2, 0, 0, partnerVendor, 5, 1, 0);
        topicsToShowcaseMappingsCase(0, 0, 0, 0, partnerVendor, 5, 5, 0);
        topicsToShowcaseMappingsCase(0, 0, 0, 0, partnerVendor, 4, 4, 0);
        topicsToShowcaseMappingsCase(0, 0, 0, 0, goldVendor, 4, 0, 0);
    }

    @Test
    public void canHandleNullTopics() {
        setHotTopics((AssetTopic) null);
        givenAssetWithTopics(basicVendor, (AssetTopic) null);

        whenOptimize();
    }

    private void topicsToShowcaseMappingsCase(int topic1Count, int topic2Count, int topic3Count, int hotTopicSupplementalCount, AssetVendor extraAssetsVendor, int extraAssetsCount, int expectedShowcase, int expectedTopPicks) {
        setUp();
        var hotTopic1 = AssetInfoFactory.makeTopic();
        var hotTopic2 = AssetInfoFactory.makeTopic();
        var hotTopic3 = AssetInfoFactory.makeTopic();
        setHotTopics(hotTopic1, hotTopic2, hotTopic3);
        givenAssetsInResultsWithVendor(extraAssetsCount, extraAssetsVendor);
        givenAssetsWithTopics(basicVendor, topic1Count, hotTopic1);
        givenAssetsWithTopics(basicVendor, topic2Count, hotTopic2);
        givenAssetsWithTopics(basicVendor, topic3Count, hotTopic3);
        givenAssetsWithTopics(basicVendor, hotTopicSupplementalCount, hotTopic1);

        whenOptimize();

        thenHotSpotCountIs(HotspotKey.Showcase, expectedShowcase);
        thenHotSpotCountIs(HotspotKey.TopPicks, expectedTopPicks);
    }
}
