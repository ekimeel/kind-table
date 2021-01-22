package kind.table;

import kind.table.cols.DblColumn;
import kind.table.cols.IntColumn;
import kind.table.cols.LngColumn;
import kind.table.cols.StrColumn;
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

        table.addCol(new IntColumn("ColA"));
        table.addCol(new IntColumn("ColB"));

        for (int i = 0; i < 100000; i++) {
            table.addRow(new Row(i, i + 1));
        }

        assertEquals((Integer)704982704, table.eval(Sum.of(0)));
        assertEquals((Integer)705082704, table.eval(Sum.of(1)));
    }

    @Test
    public void test_1M(){

        final Table table = new Table();

        table.addCol(new IntColumn("ColA"));
        for (int i = 0; i < 1000000; i++) {
            table.addRow(new Row(i));
        }

        assertEquals((Integer)999999, table.eval(Max.of(0)));
        assertEquals((Integer)0, table.eval(Min.of(0)));
        assertEquals(499999.5, table.eval(Mean.of("ColA")), 0.0001);
    }

    @Test
    public void test_1M_copy(){
        final Table tableA = new Table();

        tableA.addCol(new IntColumn("ColA"));
        tableA.addCol(new DblColumn("ColB"));
        tableA.addCol(new StrColumn("ColC"));

        for (int i = 0; i < 1000000; i++) {
            tableA.addRow(new Row(i, i * 0.1, "val_" + i ));
        }

        final Table tableB = tableA.copy();

        assertNotEquals(tableA, tableB);
        assertEquals(tableA.getColCount(), tableB.getColCount());
        assertEquals(tableA.getRowCount(), tableB.getRowCount());

        final Integer sumColA_TableA = tableA.eval(Sum.of("ColA"));
        final Integer sumColA_TableB = tableB.eval(Sum.of("ColA"));
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

        tableA.addCol(new IntColumn("ColA"));
        tableA.addCol(new DblColumn("ColB"));
        tableA.addCol(new StrColumn("ColC"));

        for (int i = 0; i < 1000000; i++) {
            tableA.addRow(new Row(i, i * 0.1, "val_" + i));
        }

        assertEquals(3, tableA.getColCount());

        tableA.addCol(new LngColumn("ColD"));

        assertEquals(4, tableA.getColCount());
        assertNull(tableA.getFirstRow().get(3));
        assertNull(tableA.getLastRow().get(3));

    }
}
