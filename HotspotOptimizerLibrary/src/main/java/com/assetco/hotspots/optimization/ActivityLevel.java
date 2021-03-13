package com.assetco.hotspots.optimization;

public enum ActivityLevel {
    Insignificant {
        @Override
        ActivityLevel predecessor() {
            return null;
        }
    },
    Elevated,
    High,
    Extreme {
        @Override
        ActivityLevel successor() {
            return null;
        }
    };

    boolean isLessThanOrEqualTo(ActivityLevel assetPerformance) {
        return compareTo(assetPerformance) <= 0;
    }

    ActivityLevel successor() {
        return values()[ordinal() + 1];
    }

    ActivityLevel predecessor() {
        return values()[ordinal() - 1];
    }
}
