package pl.edu.uj.dataframe;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class SeriesTest {

    @Test
    public void testEmptySeries() {
        Series s = new Series("col1", "int");
        assertEquals(0, s.size());

        s.addValue("44", "int");
        assertEquals(1, s.size());
        assertEquals(44, s.getData().get(0).getVal());
    }

    @Test
    public void testAddValueInt() {
        Series s = new Series("col1", "int", Arrays.asList(1, 1, 3));
        assertEquals(3, s.size());

        s.addValue("44", "int");
        assertEquals(4, s.size());
        assertEquals(44, s.getData().get(3).getVal());
    }

    @Test
    public void testAddValueLong() {
        Series s = new Series("col1", "long", Arrays.asList(1L, 1L, 3L));
        assertEquals(3, s.size());

        s.addValue("44", "long");
        assertEquals(4, s.size());
        assertEquals(44L, s.getData().get(3).getVal());
    }

    @Test
    public void testAddValueShort() {
        Series s = new Series("col1", "short", Arrays.asList((short) 1, (short) 1, (short) 3));
        assertEquals(3, s.size());

        s.addValue("44", "short");
        assertEquals(4, s.size());
        assertEquals((short) 44, s.getData().get(3).getVal());
    }

    @Test
    public void testAddValueDouble() {
        Series s = new Series("col1", "double", Arrays.asList(1.1d, 2.2d, 3.3d));
        assertEquals(3, s.size());

        s.addValue("4.4", "double");
        assertEquals(4, s.size());
        assertEquals(4.4, s.getData().get(3).getVal());
    }

    @Test
    public void testAddValueBoolean() {
        Series s = new Series("col1", "boolean", Arrays.asList(false, false, false));
        assertEquals(3, s.size());

        s.addValue("true", "boolean");
        assertEquals(4, s.size());
        assertEquals(true, s.getData().get(3).getVal());

    }

    @Test
    public void testAddValueString() {
        Series s = new Series("col1", "String", Arrays.asList("aa", "bb", "cc"));
        assertEquals(3, s.size());

        s.addValue("dd", "String");
        assertEquals(4, s.size());
        assertEquals("dd", s.getData().get(3).getVal());
    }

    @Test
    public void testCopyShallow() {
        Series s = new Series("col1", "int", Arrays.asList(11, 12, 13));
        assertEquals(3, s.size());

        Series copyShallow = s.copy(false);
        copyShallow.addValue("14", "int");

        assertEquals(4, s.size());
        assertEquals(14, s.getData().get(3).getVal());
    }

    @Test
    public void testCopyDeep() {
        Series s = new Series("col1", "int", Arrays.asList(11, 12, 13));
        assertEquals(3, s.size());

        Series copyDeep = s.copy(true);
        copyDeep.addValue("14", "int");

        assertEquals(3, s.size());
        assertEquals(13, s.getData().get(2).getVal());

        assertEquals(4, copyDeep.size());
        assertEquals(14, copyDeep.getData().get(3).getVal());
    }

    @Test
    public void testGetSliceCopy() {
        Series s = new Series("col1", "float", Arrays.asList(1.1F, 2.2F, 3.3F));
        assertEquals(3, s.size());

        s.addValue("4.4f", "float");
        s.addValue("5.5f", "float");
        s.addValue("6.6f", "float");
        assertEquals(6, s.size());

        Series slice = s.getSliceCopy(2, 4);
        assertEquals(3, slice.size());
        assertEquals(6, s.size());
        assertEquals(3.3f, slice.getData().get(0).getVal());
        assertEquals(4.4f, slice.getData().get(1).getVal());
        assertEquals(5.5f, slice.getData().get(2).getVal());
    }

}
