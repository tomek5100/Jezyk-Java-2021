package pl.edu.uj.dataframe;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataFrameTest {

    @Test
    public void testCreateEmptyDataFrame() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3"},
                new String[]{"int", "double", "float"});
        assertEquals(0, df.size());
    }

    @Test
    public void testGetColumnFromDataFrame() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3"},
                new String[]{"int", "double", "float"});
        Series col2 = df.get("col2");
        assertEquals("col2", col2.getName());
        assertEquals("double", col2.getType());
    }

    @Test
    public void testGetNotExistentColumnFromDataFrame() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3"},
                new String[]{"int", "double", "float"});
        Series colX = df.get("colX");
        assertNull(colX);
    }

    @Test
    public void testIlocSet() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "boolean", "float", "String"});
        df.addRow(new String[]{"12", "false", "14.4", "fifteen"}, true);
        df.addRow(new String[]{"22", "0", "24.4", "twenty five"}, true);
        df.addRow(new String[]{"32", "true", "34.4", "thirty five"}, true);
        df.addRow(new String[]{"42", "1", "44.4", "forty five"}, true);
        assertEquals(4, df.size());

        Series col1 = df.get("col1");
        Series col2 = df.get("col2");
        Series col3 = df.get("col3");
        Series col4 = df.get("col4");

        assertEquals(12, col1.getData().get(0).getVal());
        assertEquals(false, col2.getData().get(1).getVal());
        assertEquals(34.4f, col3.getData().get(2).getVal());
        assertEquals("forty five", col4.getData().get(3).getVal());

        df.ilocSet(0, "col1", "92");
        df.ilocSet(1, "col2", "true");
        df.ilocSet(2, "col3", "99.4");

        assertEquals(92, col1.getData().get(0).getVal());
        assertEquals(true, col2.getData().get(1).getVal());
        assertEquals(99.4f, col3.getData().get(2).getVal());

        /*
         * New behaviour is to prohibit null and empty Strings in StringValue
         */
        assertThrows(IllegalArgumentException.class, () -> df.ilocSet(3, "col4", null));

    }

    @Test(expected = NumberFormatException.class)
    public void testIlocSetNullInteger() throws NumberFormatException {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "boolean", "float", "String"});
        df.addRow(new String[]{"12", "false", "14.4", "fifteen"}, true);
        assertEquals(1, df.size());

        df.ilocSet(0, "col1", null);

    }

    @Test
    public void testIloc() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "double", "float", "String"});
        df.addRow(new String[]{"12", "13.3", "14.4", "fifteen"}, true);
        df.addRow(new String[]{"22", "23.3", "24.4", "twenty five"}, true);
        df.addRow(new String[]{"32", "33.3", "34.4", "thirty five"}, true);
        df.addRow(new String[]{"42", "43.3", "44.4", "forty five"}, true);
        assertEquals(4, df.size());

        DataFrame newDf = df.iloc(2);
        assertEquals(1, newDf.size());
        Series col2 = newDf.get("col2");
        assertEquals(33.3d, col2.getData().get(0).getVal());

    }

    @Test
    public void testIlocFromTo() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "double", "float", "String"});
        df.addRow(new String[]{"12", "13.3", "14.4", "fifteen"}, true);
        df.addRow(new String[]{"22", "23.3", "24.4", "twenty five"}, true);
        df.addRow(new String[]{"32", "33.3", "34.4", "thirty five"}, true);
        df.addRow(new String[]{"42", "43.3", "44.4", "forty five"}, true);
        assertEquals(4, df.size());

        DataFrame newDf = df.iloc(1, 3);
        assertEquals(3, newDf.size());
        Series col4 = newDf.get("col4");
        assertEquals("twenty five", col4.getData().get(0).getVal());
        assertEquals("thirty five", col4.getData().get(1).getVal());
        assertEquals("forty five", col4.getData().get(2).getVal());

    }


    @Test
    public void testAddColumnInplace() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"long", "double", "float", "String"});
        df.addRow(new String[]{"12", "13.3", "14.4", "fifteen"}, true);
        df.addRow(new String[]{"22", "23.3", "24.4", "twenty five"}, true);
        df.addRow(new String[]{"32", "33.3", "34.4", "thirty five"}, true);
        df.addRow(new String[]{"42", "43.3", "44.4", "forty five"}, true);
        df.addRow(new String[]{"52", "53.3", "54.4", "fifty five"}, true);
        assertEquals(5, df.size());

        df.addColumn("new1", "String", true);
        df.addColumn("new2", "int", true);
        df.addColumn("new3", "float", true);
        df.addColumn("new4", "short", true);
        df.addColumn("new5", "boolean", true);
        assertEquals(5, df.size());

        Series new1 = df.get("new1");
        Series new2 = df.get("new2");
        Series new3 = df.get("new3");
        Series new4 = df.get("new4");
        Series new5 = df.get("new5");

        assertEquals("0", new1.getData().get(0).getVal());
        assertEquals(0, new2.getData().get(1).getVal());
        assertEquals(0.0f, new3.getData().get(2).getVal());
        assertEquals((short) 0, new4.getData().get(3).getVal());
        assertEquals(false, new5.getData().get(4).getVal());

    }

    @Test
    public void testAddColumnCopy() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"long", "double", "float", "String"});
        df.addRow(new String[]{"12", "13.3", "14.4", "fifteen"}, true);
        df.addRow(new String[]{"22", "23.3", "24.4", "twenty five"}, true);
        assertEquals(2, df.size());

        DataFrame newDf = df.addColumn("new1", "String", false);
        assertEquals(2, newDf.size());
        assertNotEquals(df, newDf);

        Series new1 = newDf.get("new1");
        assertEquals("0", new1.getData().get(0).getVal());

        Series oldNew1 = df.get("new1");
        assertNull(oldNew1);
    }

    @Test
    public void testGetNewFromDataFrameInplace() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "double", "float", "String"});
        df.addRow(new String[]{"12", "13.3", "14.4", "fifteen"}, true);

        DataFrame newDf = df.get(new String[]{"col4", "col2"}, false);
        Series col4 = newDf.get("col4");
        Series col2 = newDf.get("col2");
        Series col1 = newDf.get("col1");
        assertNotNull(col4);
        assertNotNull(col2);
        assertNull(col1);

        df.addRow(new String[]{"22", "23.3", "24.4", "twenty five"}, true);
        assertEquals(2, df.size());
        assertEquals(2, newDf.size());

    }

    @Test
    public void testGetNewFromDataFrameCopy() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "double", "float", "String"});
        df.addRow(new String[]{"12", "13.3", "14.4", "fifteen"}, true);

        DataFrame newDf = df.get(new String[]{"col4", "col2"}, true);
        Series col4 = newDf.get("col4");
        Series col2 = newDf.get("col2");
        Series col1 = newDf.get("col1");
        assertNotNull(col4);
        assertNotNull(col2);
        assertNull(col1);

        df.addRow(new String[]{"22", "23.3", "24.4", "twenty five"}, true);
        assertEquals(2, df.size());
        assertEquals(1, newDf.size());

    }

    @Test(expected = AssertionError.class)
    public void testGetNewFromDataFrameBreaksContract() throws AssertionError {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "double", "float", "String"});

        DataFrame newDf = df.get(new String[]{"col4", "col2"}, false);
        Series col4 = newDf.get("col4");
        Series col2 = newDf.get("col2");
        Series col1 = newDf.get("col1");
        assertNotNull(col4);
        assertNotNull(col2);
        assertNull(col1);

        // adding value to newDf will break contract!
        newDf.addRow(new String[]{"twenty five", "23.3"}, true);
        assertEquals(1, newDf.size());
        df.size();

    }

    @Test
    public void testAddRowInplace() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "double", "float", "String"});

        DataFrame newDf = df.addRow(new String[]{"12", "13.3", "14.4", "fifteen"}, true);
        assertEquals(1, df.size());
        assertEquals(1, newDf.size());
        assertEquals(df, newDf);

    }

    @Test
    public void testAddRowCopy() {
        DataFrame df = new DataFrame(new String[]{"col1", "col2", "col3", "col4"},
                new String[]{"int", "double", "float", "String"});

        DataFrame newDf = df.addRow(new String[]{"12", "13.3", "14.4", "fifteen"}, false);
        assertEquals(0, df.size());
        assertEquals(1, newDf.size());
        assertNotEquals(df, newDf);

    }

}
