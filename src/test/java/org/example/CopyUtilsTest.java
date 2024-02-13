package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.test.Empty;
import org.example.test.Man;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CopyUtilsTest {

    @Test
    void testSimpleEmptyCopyInstantiation() {
        Empty empty = new Empty();
        Empty copy = CopyUtils.deepCopy(empty);
        assertNotSame(empty, copy);
    }

    @Test
    void testInnerStaticEmptyCopyInstantiation() {
        Empty.InnerStaticEmpty empty = Empty.InnerStaticEmpty.getInstance();
        Empty.InnerStaticEmpty copy = CopyUtils.deepCopy(empty);
        assertNotSame(empty, copy);
    }

    @Test
    void testArrayCopyInstantiation() {
        Object[] orig = {
                null, 1, "test", new Empty(), new Empty[0]
        };
        Object[] copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertArrayEquals(orig, copy);
    }

    @Test
    void testCollectionCopyInstantiation() {
        List<Object> origList = new ArrayList<>();
        Collection<Object> copy = CopyUtils.deepCopy(origList);
        assertEquals(origList, copy);
    }
    
    @Test
    void testPrimitiveCopyInstantiation() {
        int copy = CopyUtils.deepCopy(1);
        assertEquals(1, copy);
    }
    
    @Test
    void testStringCopyInstantiation() {
        String copy = CopyUtils.deepCopy("");
        assertEquals("", copy);
    }

    @Test
    void testOrdinaryManNoFriends() {
        List<String> lib = new ArrayList<>(List.of("Book1", "Book2", "Book3"));
        Man orig = new Man("name1", 20, lib, Collections.emptyList());
        Man copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

    @Test
    void testNullMan() {
        Man orig = new Man(null, 0, null, null);
        Man copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

    @Test
    void testNewbornMan() {
        Man orig = new Man("name", 0, null, null);
        Man copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

    @Test
    void testEverLivingMan() {
        Man orig = new Man("name", Integer.MAX_VALUE, null, null);
        Man copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

    @Test
    void testImpossibleAgeMan() {
        Man orig = new Man("name", Integer.MIN_VALUE, null, null);
        Man copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

    @Test
    void testNonReaderMan() {
        Man orig = new Man("Alex", 20, new ArrayList<>(), null);
        Man copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

    @Test
    void testKnowItAllGuy() {
        Man orig = new Man("Alex", 20, getLibrary(), null);
        Man copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

    @Test
    void testManWithFriends() {
        Man friend1 = new Man("Fred", 22, new ArrayList<>(List.of("Book1")), null);
        Man friend2 = new Man("Lucy", 19, new ArrayList<>(List.of("Book2")), null);
        Man orig = new Man("Alex", 20, new ArrayList<>(List.of("Book3")), null);

        friend1.setFriends(List.of(friend2, orig));
        friend2.setFriends(List.of(friend1, orig));
        orig.setFriends(List.of(friend1, friend2));

        Man copy = CopyUtils.deepCopy(orig);
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

    private List<String> getLibrary() {
        return IntStream.range(0, 10000)
                .mapToObj(i -> RandomStringUtils.randomAlphabetic(10))
                .collect(Collectors.toList());
    }

}