package kind.table;

import kind.table.cols.DoubleColumn;
import kind.table.cols.IntegerColumn;
import kind.table.cols.LongColumn;
import kind.table.cols.StringColumn;
import kind.table.funcs.Max;
import kind.table.funcs.Mean;
import kind.table.funcs.Min;
import kind.table.funcs.Sum;
import org.junit.Test;

import static org.junit.Assert.*;


public class BigTableTest {

    @Test
    public void test_100k(){

        final Table table = new Table();

        table.addColumn(new IntegerColumn("ColA"));
        table.addColumn(new IntegerColumn("ColB"));

        for (int i = 0; i < 100000; i++) {
            table.addRow(new Row(i, i + 1));
        }

        assertEquals((Integer)704982704, table.eval(new Sum<>(0)));
        assertEquals((Integer)705082704, table.eval(new Sum<>(1)));
    }

    @Test
    public void test_1M(){

        final Table table = new Table();

        table.addColumn(new IntegerColumn("ColA"));
        for (int i = 0; i < 1000000; i++) {
            table.addRow(new Row(i));
        }

        assertEquals((Integer)999999, table.eval(new Max(0)));
        assertEquals((Integer)0, table.eval(new Min(0)));
        assertEquals(499999.5, table.eval(new Mean(0)), 0.0001);
    }

    @Test
    public void test_1M_copy(){
        final Table tableA = new Table();

        tableA.addColumn(new IntegerColumn("ColA"));
        tableA.addColumn(new DoubleColumn("ColB"));
        tableA.addColumn(new StringColumn("ColC"));

        for (int i = 0; i < 1000000; i++) {
            tableA.addRow(new Row(i, i * 0.1, "val_" + i ));
        }

        final Table tableB = tableA.copy();

        assertNotEquals(tableA, tableB);
        assertEquals(tableA.getColumnCount(), tableB.getColumnCount());
        assertEquals(tableA.getRowCount(), tableB.getRowCount());

        final Integer sumColA_TableA = tableA.eval(new Sum<>(0));
        final Integer sumColA_TableB = tableB.eval(new Sum<>(0));
        assertEquals(sumColA_TableA, sumColA_TableB);

        final Row lastRow_TableA = tableA.getLastRow();
        final Row lastRow_TableB = tableB.getLastRow();
        assertEquals(lastRow_TableA.getIndex(), lastRow_TableB.getIndex());
        assertEquals(lastRow_TableA.size(), lastRow_TableB.size());

        assertEquals(lastRow_TableA.values().get(0), lastRow_TableB.values().get(0));
        assertEquals(lastRow_TableA.values().get(1), lastRow_TableB.values().get(1));
        assertEquals(lastRow_TableA.values().get(2), lastRow_TableB.values().get(2));

    }


    @Test
    public void test_1M_addColumn() {
        final Table tableA = new Table();

        tableA.addColumn(new IntegerColumn("ColA"));
        tableA.addColumn(new DoubleColumn("ColB"));
        tableA.addColumn(new StringColumn("ColC"));

        for (int i = 0; i < 1000000; i++) {
            tableA.addRow(new Row(i, i * 0.1, "val_" + i));
        }

        assertEquals(3, tableA.getColumnCount());

        tableA.addColumn(new LongColumn("ColD"));

        assertEquals(4, tableA.getColumnCount());
        assertNull(tableA.getFirstRow().get(3));
        assertNull(tableA.getLastRow().get(3));

    }
}
