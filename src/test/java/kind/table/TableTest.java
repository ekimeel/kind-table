package kind.table;

import kind.table.cols.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TableTest {


    @Test
    public void test_emptyTableHasNoColsOrRows(){
        final Table table = new Table();

        assertTrue(table.getColCount() == 0);
        assertTrue(table.getRowCount() == 0);
    }

    @Test
    public void test_addCol(){
        final Table table = new Table();

        table.addCol(new IntCol("colA"));
        table.addCol(new IntCol("colB"));

        assertEquals(2, table.getColCount());
    }

    @Test
    public void test_addColWithDuplicateName(){
        final Table table = new Table();

        assertTrue(table.addCol(new IntCol("colA")) == 0 );
        assertTrue(table.addCol(new IntCol("colB")) == 1);
        assertNull(table.addCol(new IntCol("colA"))); // duplicate col name

        assertEquals(2, table.getColCount());
    }

    @Test
    public void test_addRows(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        table.addRow(Row.of(1, 2));
        table.addRow(Row.of(3, 4));
        table.addRow(Row.of(5, 6));

        assertEquals(3, table.getRowCount());
        assertEquals(2, table.getColCount());

        /* row:0, col:0 */
        assertTrue(table.has(0, 0));
        assertEquals((Integer)1, table.get(0, 0));
        assertEquals(0, table.getRow(0).getIndex());

        /* row:0, col:1 */
        assertTrue(table.has(0, 1));
        assertEquals((Integer)2, table.get(0, 1));

        /* row:1, col:0 */
        assertTrue(table.has(1, 0));
        assertEquals((Integer)3, table.get(1, 0));
        assertEquals(1, table.getRow(1).getIndex());

        /* row:1, col:1 */
        assertTrue(table.has(1, 1));
        assertEquals((Integer)4, table.get(1, 1));
        assertEquals(1, table.getRow(1).getIndex());

    }

    @Test
    public void test_getValues(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new DblCol("Even"));
        table.addCol(new StrCol("Name"));

        table.addRow(Row.of(1, 2.0, "Foo"));
        table.addRow(Row.of(3, 4.0, "Bar"));
        table.addRow(Row.of(5, 6.0, "Foo Bar"));

        assertEquals( (Integer) 1, table.get(0,0));
        assertEquals( 4.0, table.get(1,1), 0.0);
        assertEquals("Foo Bar", table.getLastRow().get(2));

    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_get_withBadIndex(){
        final Table table = new Table();

        table.get(0,0);

    }

    @Test
    public void test_isEmpty(){
        final Table table = new Table();
        assertTrue(table.isEmpty());

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        assertTrue(table.isEmpty());
        table.addRow(Row.of(1, 2));
        assertFalse(table.isEmpty());
    }

    @Test
    public void test_getFirstRow(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        assertNull(table.getFirstRow());

        table.addRow(Row.of(1, 2));
        table.addRow(Row.of(3, 4));
        table.addRow(Row.of(5, 6));

        assertNotNull(table.getFirstRow());
        final Row firstRow = table.getFirstRow();

        assertEquals(2, firstRow.size());
        assertEquals((Integer)1, firstRow.get(0));
        assertEquals((Integer)2, firstRow.get(1));
    }

    @Test
    public void test_getValues_withBadIndex(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        table.addRow(Row.of(1, 2));
        table.addRow(Row.of(3, 4));
        table.addRow(Row.of(5, 6));

    }



    @Test
    public void test_copy() {
        final Table tableA = new Table();

        tableA.addCol(new IntCol("Odd"));
        tableA.addCol(new IntCol("Even"));

        tableA.addRow(Row.of(1, 2));
        tableA.addRow(Row.of(3, 4));
        tableA.addRow(Row.of(5, 6));

        final Table tableB = tableA.copy();
        assertNotEquals(tableA, tableB);

        assertEquals(tableA.getRowCount(), tableB.getRowCount());
        assertEquals(tableA.getColCount(), tableB.getColCount());

        assertEquals("Odd", tableB.getColByIndex(0).getName());
        assertEquals("Even", tableB.getColByIndex(1).getName());

        assertEquals((Integer)1, tableB.get(0,0));
        assertEquals((Integer)3, tableB.get(1,0));
        assertEquals((Integer)5, tableB.get(2,0));

        assertEquals((Integer)2, tableB.get(0,1));
        assertEquals((Integer)4, tableB.get(1,1));
        assertEquals((Integer)6, tableB.get(2,1));
    }

    @Test
    public void test_addCol_withNonEmptyTable() {
        final Table tableA = new Table();

        tableA.addCol(new IntCol("Odd"));
        tableA.addCol(new IntCol("Even"));

        tableA.addRow(Row.of(1, 2));
        tableA.addRow(Row.of(3, 4));
        tableA.addRow(Row.of(5, 6));

        assertEquals(2, tableA.getColCount());
        assertEquals(2, tableA.getRow(0).size());
        assertEquals(2, tableA.getRow(1).size());
        assertEquals(2, tableA.getRow(2).size());

        tableA.addCol(new StrCol("New"));
        assertEquals(3, tableA.getColCount());
        assertEquals(3, tableA.getRow(0).size());
        assertEquals(3, tableA.getRow(1).size());
        assertEquals(3, tableA.getRow(2).size());
    }

    @Test
    public void test_getColsOfType() {
        final Table table = new Table();

        table.addCol(new BoolCol("BoolA"));

        table.addCol(new IntCol("IntA"));
        table.addCol(new IntCol("IntB"));

        table.addCol(new StrCol("StrA"));
        table.addCol(new StrCol("StrB"));
        table.addCol(new StrCol("StrC"));

        table.addCol(new DblCol("DblA"));
        table.addCol(new DblCol("DblB"));
        table.addCol(new DblCol("DblC"));
        table.addCol(new DblCol("DblD"));

        assertEquals(1, table.getColsOfType(BoolCol.class).size() );
        assertEquals(2, table.getColsOfType(IntCol.class).size() );
        assertEquals(3, table.getColsOfType(StrCol.class).size() );
        assertEquals(4, table.getColsOfType(DblCol.class).size() );

        assertEquals(6, table.getColsOfType(NumCol.class).size() ); // abstract
        assertEquals(10, table.getColsOfType(Col.class).size() ); // interface

    }



}