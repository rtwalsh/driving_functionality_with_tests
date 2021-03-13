package com.assetco.search.results;

import java.util.*;

public class SearchResults {
    private final List<Asset> found = new ArrayList<>();
    private Map<HotspotKey, Hotspot> hotspots = new HashMap<>();
    private UserSegment userSegment;

    public UserSegment getUserSegment() {
        return userSegment;
    }

    public void setUserSegment(UserSegment theUserSegment) {
        userSegment = theUserSegment;
    }

    public void addFound(Asset asset) {
        found.add(asset);
    }

    public List<Asset> getFound() {
        return found;
    }

    public Hotspot getHotspot(HotspotKey key) {
        if (hotspots.containsKey(key))
            return hotspots.get(key);

        var result = new Hotspot();
        hotspots.put(key, result);

        return result;
    }

    public void clearHotspots() {
        hotspots = new HashMap<>();
    }
}
