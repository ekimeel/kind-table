package kind.table;

import kind.table.cols.DblColumn;
import kind.table.cols.IntColumn;
import kind.table.cols.StrColumn;
import org.junit.Test;

import static org.junit.Assert.*;

public class TableTest {


    @Test
    public void test_emptyTableHasNoColumnsOrRows(){
        final Table table = new Table();

        assertTrue(table.getColumnCount() == 0);
        assertTrue(table.getRowCount() == 0);
    }

    @Test
    public void test_addColumn(){
        final Table table = new Table();

        table.addCol(new IntColumn("colA"));
        table.addCol(new IntColumn("colB"));

        assertEquals(2, table.getColumnCount());
    }

    @Test
    public void test_addColumnWithDuplicateName(){
        final Table table = new Table();

        assertTrue(table.addCol(new IntColumn("colA")));
        assertTrue(table.addCol(new IntColumn("colB")));
        assertFalse(table.addCol(new IntColumn("colA"))); // duplicate col name

        assertEquals(2, table.getColumnCount());
    }

    @Test
    public void test_addRows(){
        final Table table = new Table();

        table.addCol(new IntColumn("Odd"));
        table.addCol(new IntColumn("Even"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(3, 4));
        table.addRow(new Row(5, 6));

        assertEquals(3, table.getRowCount());
        assertEquals(2, table.getColumnCount());

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

        table.addCol(new IntColumn("Odd"));
        table.addCol(new DblColumn("Even"));
        table.addCol(new StrColumn("Name"));

        table.addRow(new Row(1, 2.0, "Foo"));
        table.addRow(new Row(3, 4.0, "Bar"));
        table.addRow(new Row(5, 6.0, "Foo Bar"));

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

        table.addCol(new IntColumn("Odd"));
        table.addCol(new IntColumn("Even"));

        assertTrue(table.isEmpty());
        table.addRow(new Row(1, 2));
        assertFalse(table.isEmpty());
    }

    @Test
    public void test_getFirstRow(){
        final Table table = new Table();

        table.addCol(new IntColumn("Odd"));
        table.addCol(new IntColumn("Even"));

        assertNull(table.getFirstRow());

        table.addRow(new Row(1, 2));
        table.addRow(new Row(3, 4));
        table.addRow(new Row(5, 6));

        assertNotNull(table.getFirstRow());
        final Row firstRow = table.getFirstRow();

        assertEquals(2, firstRow.size());
        assertEquals((Integer)1, firstRow.get(0));
        assertEquals((Integer)2, firstRow.get(1));
    }

    @Test
    public void test_getValues_withBadIndex(){
        final Table table = new Table();

        table.addCol(new IntColumn("Odd"));
        table.addCol(new IntColumn("Even"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(3, 4));
        table.addRow(new Row(5, 6));

    }



    @Test
    public void test_copy() {
        final Table tableA = new Table();

        tableA.addCol(new IntColumn("Odd"));
        tableA.addCol(new IntColumn("Even"));

        tableA.addRow(new Row(1, 2));
        tableA.addRow(new Row(3, 4));
        tableA.addRow(new Row(5, 6));

        final Table tableB = tableA.copy();
        assertNotEquals(tableA, tableB);

        assertEquals(tableA.getRowCount(), tableB.getRowCount());
        assertEquals(tableA.getColumnCount(), tableB.getColumnCount());

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
    public void test_addColumn_withNonEmptyTable() {
        final Table tableA = new Table();

        tableA.addCol(new IntColumn("Odd"));
        tableA.addCol(new IntColumn("Even"));

        tableA.addRow(new Row(1, 2));
        tableA.addRow(new Row(3, 4));
        tableA.addRow(new Row(5, 6));

        assertEquals(2, tableA.getColumnCount());
        assertEquals(2, tableA.getRow(0).size());
        assertEquals(2, tableA.getRow(1).size());
        assertEquals(2, tableA.getRow(2).size());

        tableA.addCol(new StrColumn("New"));
        assertEquals(3, tableA.getColumnCount());
        assertEquals(3, tableA.getRow(0).size());
        assertEquals(3, tableA.getRow(1).size());
        assertEquals(3, tableA.getRow(2).size());
    }



}