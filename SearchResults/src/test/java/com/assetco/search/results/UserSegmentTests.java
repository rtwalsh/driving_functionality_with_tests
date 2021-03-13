package com.assetco.search.results;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserSegmentTests {

    @Test
    public void itShouldHaveMembers() {
        UserSegment[] userSegments = UserSegment.values();
        assertEquals(3, userSegments.length);
        assertEquals("NewsMedia", userSegments[0].name());
        assertEquals("OtherMedia", userSegments[1].name());
        assertEquals("GeneralPublic", userSegments[2].name());
    }
}
