package it.unicam.cs.asdl2122.es6;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class TestDavveroBello {
    @Test
    final void testAdd() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();
        assertThrows(NullPointerException.class, () -> {
            list.add(null);
        });
        assertEquals(0, list.size());
        list.add("dsakjfbskdbf");
        assertFalse(list.isEmpty());
        assertEquals(1, list.size());
    }

    @Test
    final void testContains() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();
        list.add("aaa");
        assertTrue(list.contains("aaa"));
        assertFalse(list.contains("aaaasdasdasd"));
    }

    @Test
    final void testRemove() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();
        list.add("aaa");
        list.add("aaa");
        list.add("asa");
        list.add("agfa");

        list.remove("aaa");
        assertTrue(list.contains("aaa"));
        list.remove("aaa");
        assertFalse(list.contains("aaa"));
        assertEquals(2, list.size());
        assertEquals(6, list.getNumeroModifiche());
    }

    @Test
    final void testClear() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();

        list.add("aaa");
        list.add("aaa");
        list.add("asa");
        list.add("agfa");

        list.clear();
        assertEquals(0, list.size());
        assertEquals(5, list.getNumeroModifiche());

    }

    @Test
    final void testGet() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(-1);
        });

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(4);
        });

        assertEquals("b", list.get(1));
    }

    @Test
    final void testSet() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set(4, "aaaaaa");
        });
        assertThrows(NullPointerException.class, () -> {
            list.set(2, null);
        });
        assertEquals("c", list.set(2, "z"));
        assertEquals("z", list.get(2));
    }

    @Test
    final void testAddConIndice() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();

        list.add("a");
        list.add("b");
        list.add("c");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add(4, "aaaaaa");
        });
        assertThrows(NullPointerException.class, () -> {
            list.add(2, null);
        });
        list.add(1, "z");

        assertEquals("a", list.get(0));
        assertEquals("z", list.get(1));
        assertEquals("b", list.get(2));
        assertEquals("c", list.get(3));

        list.clear();

        list.add("a");
        list.add("b");
        list.add("c");

        list.add(0, "z");

        assertEquals("z", list.get(0));
        assertEquals("a", list.get(1));
        assertEquals("b", list.get(2));
        assertEquals("c", list.get(3));

        list.clear();

        list.add("a");
        list.add("b");
        list.add("c");

        list.add(3, "z");

        assertEquals("a", list.get(0));
        assertEquals("b", list.get(1));
        assertEquals("c", list.get(2));
        assertEquals("z", list.get(3));

    }

    @Test
    final void testRemoveCondIndice() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();

        list.add("a");
        list.add("b");
        list.add("c");

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(4);
        });

        list.remove(1);

        assertEquals("a", list.get(0));
        assertEquals("c", list.get(1));

        list.remove(0);

        assertEquals("c", list.get(0));
    }

    @Test
    final void testIndexOf() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();

        list.add("a");
        list.add("b");
        list.add("c");

        assertThrows(NullPointerException.class, () -> {
            list.indexOf(null);
        });

        assertEquals(1, list.indexOf("b"));
    }


    @Test
    final void testLastIndexOf() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();

        list.add("a");
        list.add("b");
        list.add("c");
        list.add("c");
        list.add("b");
        list.add("b");
        list.add("c");

        assertThrows(NullPointerException.class, () -> {
            list.lastIndexOf(null);
        });

        assertEquals(5, list.lastIndexOf("b"));
    }


    @Test
    final void testToArray() {
        ASDL2122SingleLinkedList<String> list = new ASDL2122SingleLinkedList<>();

        list.add("a");
        list.add("b");
        list.add("c");

        Object[] array = list.toArray();
        assertEquals("a", array[0]);
        assertEquals("b", array[1]);
        assertEquals("c", array[2]);
    }
}
