package com.assetco.search.results;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserSegmentTests {

    @Test
    public void itShouldHaveMembers() {
        assertEquals("NewsMedia", UserSegment.NewsMedia.toString());
        assertEquals("OtherMedia", UserSegment.OtherMedia.toString());
        assertEquals("GeneralPublic", UserSegment.GeneralPublic.toString());
    }
}
