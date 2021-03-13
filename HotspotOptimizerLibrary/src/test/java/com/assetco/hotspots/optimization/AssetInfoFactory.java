package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

import java.math.*;
import java.util.*;

public class AssetInfoFactory {
    private static int topicId = 0;

    static AssetPurchaseInfo getPurchaseInfoFromRoyaltiesAndRevenue(String royalties, String revenue) {
        return new AssetPurchaseInfo(0, 0,
                new Money(new BigDecimal(revenue)),
                new Money(new BigDecimal(royalties)));
    }

    static AssetPurchaseInfo getPurchaseInfo() {
        return getPurchaseInfoFromRoyaltiesAndRevenue("0", "0");
    }

    static AssetVendor makeVendor(AssetVendorRelationshipLevel relationshipLevel) {
        return new AssetVendor("anything", "anything", relationshipLevel, 1);
    }

    static Asset getAsset(AssetVendor vendor) {
        return new Asset("anything", "anything", null, null, getPurchaseInfo(), getPurchaseInfo(), new ArrayList<>(), vendor);
    }

    static Asset getAsset(AssetVendorRelationshipLevel level) {
        return getAsset(makeVendor(level));
    }

    static AssetPurchaseInfo getPurchaseInfoForTraffic(int shown, int sold) {
        return new AssetPurchaseInfo(shown, sold, new Money(new BigDecimal("0")), new Money(new BigDecimal("0")));
    }

    static Asset getAssetWith24HourTraffic(int shown, int sold) {
        return new Asset(null, null, null, null, getPurchaseInfo(), getPurchaseInfoForTraffic(shown, sold), new ArrayList<>(), makeVendor(AssetVendorRelationshipLevel.Basic));
    }

    static Asset getAssetWith30DayTraffic(int shown, int sold) {
        return new Asset(null, null, null, null, getPurchaseInfoForTraffic(shown, sold), getPurchaseInfo(), new ArrayList<>(), makeVendor(AssetVendorRelationshipLevel.Basic));
    }

    static AssetPurchaseInfo getPurchaseInfoFromTraffic(int shown, int sold) {
        return new AssetPurchaseInfo(shown, sold, new Money(new BigDecimal("0")), new Money(new BigDecimal("0")));
    }

    static AssetTopic makeTopic() {
        return new AssetTopic("id-" + (++topicId), "anything");
    }

    static Asset getAssetWith30DayProfitability(AssetVendor vendor, String royalties, String revenue) {
        return new Asset("anything", "anything", null, null, getPurchaseInfoFromRoyaltiesAndRevenue(royalties, revenue),
                getPurchaseInfo(),
                new ArrayList<>(),
                vendor);
    }
}
