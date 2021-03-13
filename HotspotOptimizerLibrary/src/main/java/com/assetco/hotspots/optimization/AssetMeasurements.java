package com.assetco.hotspots.optimization;

import com.assetco.search.results.*;

import java.math.*;

import static com.assetco.hotspots.optimization.ActivityLevel.*;

public class AssetMeasurements {
    private static boolean hasTopLine(Asset asset, String amount) {
        return asset.getPurchaseInfoLast30Days().getTotalRevenue().getAmount().compareTo(new BigDecimal(amount)) >= 0;
    }

    private static boolean hasProfitMargin(Asset asset, String denominator, String numerator) {
        var scaledRevenue = asset.getPurchaseInfoLast30Days().getTotalRevenue().getAmount().multiply(new BigDecimal(denominator));
        var scaledRoyalties = asset.getPurchaseInfoLast30Days().getTotalRoyaltiesOwed().getAmount().multiply(new BigDecimal(numerator));
        return scaledRevenue.compareTo(scaledRoyalties) >= 0;
    }

    ActivityLevel getAssetPerformance(Asset asset) {
        if (hasProfitMargin(asset, "1", "4"))
            return Extreme;
        else if (hasProfitMargin(asset, "1", "2"))
            return High;
        else if (hasProfitMargin(asset, "7", "10"))
            return Elevated;
        return Insignificant;
    }

    // As our understanding of the problem improves, so should our design
    // "volume" is a better term than "traffic", so I renamed this method.
    ActivityLevel getAssetVolume(Asset asset) {
       if (hasTopLine(asset, "10000"))
           return Extreme;
       else if (hasTopLine(asset, "1500"))
           return High;
       else if (hasTopLine(asset, "1000"))
           return Elevated;

       return Insignificant;
   }
}
