package org.example;

import org.apache.commons.lang3.RandomStringUtils;
import org.example.test.Empty;
import org.example.test.Man;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class CopyUtilsTest {

    @Test
    void testSimpleEmptyCopyInstantiation() {
        Empty empty = new Empty();
        testDeepCopy(empty);
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

        assertSame(orig.getClass(), copy.getClass());
        assertNotSame(orig, copy);
        assertArrayEquals(orig, copy);
    }

    @Test
    void testCollectionCopyInstantiation() {
        List<Object> orig = new ArrayList<>();
        testDeepCopy(orig);
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
        Man orig = new Man("name1", 20, lib);
        testDeepCopy(orig);
    }

    @Test
    void testNullMan() {
        Man orig = new Man(null, 0, null);
        testDeepCopy(orig);
    }

    @Test
    void testNewbornMan() {
        Man orig = new Man("name", 0, null);
        testDeepCopy(orig);
    }

    @Test
    void testEverLivingMan() {
        Man orig = new Man("name", Integer.MAX_VALUE, null);
        testDeepCopy(orig);
    }

    @Test
    void testImpossibleAgeMan() {
        Man orig = new Man("name", Integer.MIN_VALUE, null);
        testDeepCopy(orig);
    }

    @Test
    void testNonReaderMan() {
        Man orig = new Man("Alex", 20, new ArrayList<>());
        testDeepCopy(orig);
    }

    @Test
    void testKnowItAllGuy() {
        Man orig = new Man("Alex", 20, getLibrary());
        testDeepCopy(orig);
    }

    @Test
    void testCopyDate() {
        Date orig = new Date();
        testDeepCopy(orig);
    }

    @Test
    void testCopyTimestamp() {
        Timestamp orig = new Timestamp(new Date().getTime());
        orig.setNanos(123);
        testDeepCopy(orig);
    }

    @Test
    void testCopySqlDate() {
        java.sql.Date orig = new java.sql.Date(new Date().getTime());
        testDeepCopy(orig);
    }

    @Test
    void testCopyHashMap() {
        List<String> lib = new ArrayList<>(List.of("Book1", "Book2", "Book3"));
        Man man1 = new Man("name1", 20, lib);
        Man man2 = new Man("name1", 20, lib);
        Map<Object, Object> orig = new HashMap<>(Map.of(
            1, 2,
            "1", "2",
            man1, man2
        ));
        testDeepCopy(orig);
    }

    @Test
    void testCopyTreeMap() {
        Map<Object, Object> orig = new TreeMap<>(Map.of(
            1, 2,
            2, "2",
            3, new Man("Fred", 22, new ArrayList<>(List.of("Book1")))
        ));
        testDeepCopy(orig);
    }

    private void testDeepCopy(Object orig) {
        Object copy = CopyUtils.deepCopy(orig);
        testAllAssertions(orig, copy);
    }

    private List<String> getLibrary() {
        return IntStream.range(0, 10000)
            .mapToObj(i -> RandomStringUtils.randomAlphabetic(10))
            .collect(Collectors.toList());
    }

    private void testAllAssertions(Object orig, Object copy) {
        assertSame(orig.getClass(), copy.getClass());
        assertNotSame(orig, copy);
        assertEquals(orig, copy);
    }

}