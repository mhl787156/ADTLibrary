package adt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import adtInterface.Adt;
import adtInterface.AdtEntry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public abstract class TestAdt {

    Adt<String, Integer> d;

    @Before
    public abstract void setUp();

    @After
    public abstract void tearDown();

    @Test
    public void testIsEmpty() {
        assertTrue("isEmpty() failed for empty adt", d.isEmpty());
    }

    @Test
    public void testIsEmptyFalse() {
        d.put("Tigger", 20);
        assertFalse("isEmpty() failed for adt with one element", d
                .isEmpty());
    }

    @Test
    public void testClear() {
        d.clear();
        assertTrue("clear() failed for Empty adt ", d.isEmpty());
    }

    @Test
    public void testClearWithContent() {
        d.put("Jennyanydots", 4);
        d.clear();
        assertTrue("clear() failed for non-empty adt ", d.isEmpty());
    }

    @Test
    public void testSize() {
        assertEquals("size() failed for empty adt", 0, d.size());
    }

    @Test
    public void testSizeWithItem() {
        d.put("Skimbleshanks", 30);
        assertEquals("size() failed for single item adt", 1, d.size());
    }

    @Test
    public void testSizeWithItems() {
        List<String> cats = Arrays.asList("Growltiger", "Rum Tum Tugger",
                "Jellicles", "Mungojerrie", "Rumpelteazer");
        for (String cat : cats) {
            d.put(cat, cat.hashCode());
        }

        assertEquals("size() failed for multiple cats", cats.size(), d.size());
    }

    @Test
    public void testGet() {
        d.put("Tiddles", 10);
        assertEquals("get() failed for one item adt", 10, (int) d
                .get("Tiddles"));
    }

    @Test
    public void testGetMultiple() {
        List<String> cats = Arrays.asList("Bustopher Jones", "Old Deuteronomy",
                "Mr. Mistoffelees", "Gus");
        List<Integer> values = Arrays.asList(7, 2, 8, 20);

        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), values.get(i));
        }

        assertEquals("get() failed for multiple item adt", 8, (int) d
                .get("Mr. Mistoffelees"));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetException() {
        d.get("Tiddles");
    }

    @Test
    public void testPut(){
        d.put("hello" ,1);
        assertEquals("testPut" , 1 , d.size());
    }

    @Test
    public void testPutOrder(){
        d.put("b" , 5);
        d.put("d" , 1); // covers end of list
        d.put("a" , 2); // covers first of list
        d.put("c" , 5); //covers between 2 elem
        Iterator<AdtEntry<String, Integer>> it = d.iterator();
        assertEquals("first of list fail", "a", it.next().getKey());
        assertEquals("input fail", "b", it.next().getKey());
        assertEquals("between 2 elem fail", "c", it.next().getKey());
        assertEquals("end of list fail", "d", it.next().getKey());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveEmpty() {
        d.remove("Grizabella");
    }

    @Test
    public void testRemoveSingle() {
        d.put("Tiddles", 10);
        d.remove("Tiddles");
        assertEquals("remove() failed with single item adt", 0, d.size());
    }

    @Test
    public void testRemoveLast() {
        List<String> cats = Arrays.asList("Gus", "Tiddles", "Skimbleshanks");
        List<Integer> values = Arrays.asList(7, 3, 8);
        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), values.get(i));
        }

        d.remove("Skimbleshanks");

        assertEquals("remove() failed with last item adt", 2, d
                .size());
    }

    @Test
    public void testRemoveFirst(){
        List<String> cats = Arrays.asList("Gus", "Tiddles", "Skimbleshanks");
        List<Integer> values = Arrays.asList(7, 3, 8);
        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), values.get(i));
        }

        d.remove("Gus");

        assertEquals("remove() failed with multiple item adt","Skimbleshanks",d.iterator().next().getKey());
    }

    @Test
    public void testListIteratorHasNext() {
        Iterator<AdtEntry<String, Integer>> it = d.iterator();
        assertFalse("Iterator: hasNext() failed with empty adt", it
                .hasNext());
        d.clear();

        d.put("a" , 5);
        it = d.iterator();
        assertTrue("Iterator: hasNext() failed with 1 elem", it.hasNext());
        d.clear();


        d.put("b" , 5);
        d.put("d" , 1);  // covers end of list
        d.put("a" , 2);  // covers first of list
        d.put("c" , 5);  // covers between 2 elem
        it = d.iterator();
        assertTrue("Iterator: hasNext() failed with more than one elem"
                , it.hasNext());
        it.next();
        assertTrue("Iterator: hasNext() failed with stack not empty but leaf null"
                , it.hasNext());
        it.next();it.next();it.next();
        assertFalse("Iterator: hasNext() failed with elem where a child still exists below", it.hasNext());
    }


    @Test
    public void testListIteratorMany() {
        List<String> cats = Arrays.asList("practical", "dramatical",
                "pragmatical", "fanatical", "oratorical", "delphioracle",
                "skeptical", "dispeptical", "romantical", "pedantical",
                "critical", "parasitical", "allegorical", "metaphorical",
                "statistical", "mystical", "political", "hypocritical",
                "clerical", "hysterical", "cynical", "rabbinical");
        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), i);
        }

        List<String> sortedCats = new ArrayList<String>(cats);
        Collections.sort(sortedCats);

        Iterator<String> expected = sortedCats.iterator();
        Iterator<AdtEntry<String, Integer>> actual = d.iterator();

        while (expected.hasNext()) {
            assertTrue("Iterator hasNext() failed when expected", actual
                    .hasNext());

            String expectedCat = expected.next();
            AdtEntry<String, Integer> actualCat = actual.next();

            assertEquals("Iterator next() returned the wrong element",
                    expectedCat, actualCat.getKey());

        }
        assertFalse("Iterator hasNext() failed at the end of the adt",
                actual.hasNext());

    }

    @Test(expected = ConcurrentModificationException.class)
    public void testListIteratorConcurrentRemove() {
        List<String> cats = Arrays.asList("Bustopher", "Gus", "Skimbleshanks");

        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), i);
        }

        Iterator<AdtEntry<String, Integer>> it = d.iterator();

        d.remove("Bustopher");

        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testListIteratorConcurrentClear() {
        List<String> cats = Arrays.asList("Bustopher", "Gus", "Skimbleshanks");

        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), i);
        }

        Iterator<AdtEntry<String, Integer>> it = d.iterator();

        d.clear();

        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void testListIteratorConcurrentPut() {
        List<String> cats = Arrays.asList("Bustopher", "Gus", "Skimbleshanks");

        for (int i = 0; i < cats.size(); i++) {
            d.put(cats.get(i), i);
        }

        Iterator<AdtEntry<String, Integer>> it = d.iterator();

        d.put("SmellyCat" , 1);

        it.next();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testListIteratorRemoveUnimplemented() {
        d.iterator().remove();
    }

}
