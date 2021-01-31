package kind.table;

import kind.table.cols.*;
import kind.table.cols.DblCol;
import kind.table.cols.IntCol;
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

        table.addCol(new IntCol("ColA"));
        table.addCol(new IntCol("ColB"));

        for (int i = 0; i < 100000; i++) {
            table.addRow(Row.of(i, i + 1));
        }

        assertEquals((Integer)704982704, table.eval(Sum.from(0)));
        assertEquals((Integer)705082704, table.eval(Sum.from(1)));
    }

    @Test
    public void test_1M(){

        final Table table = new Table();
        table.ensureRowCapacity(1000000);
        table.addCol(new IntCol("ColA"));
        for (int i = 0; i < 1000000; i++) {
            table.addRow(Row.of(i));
        }

        assertEquals((Integer)999999, table.eval(Max.from(0)));
        assertEquals((Integer)0, table.eval(Min.from(0)));
        assertEquals(499999.5, table.eval(Mean.from("ColA")), 0.0001);
    }

    @Test
    public void test_1M_copy(){
        final Table tableA = new Table();

        tableA.addCol(new IntCol("ColA"));
        tableA.addCol(new DblCol("ColB"));
        tableA.addCol(new StrCol("ColC"));

        for (int i = 0; i < 1000000; i++) {
            tableA.addRow(Row.of(i, i * 0.1, "val_" + i ));
        }

        final Table tableB = tableA.copy();

        assertNotEquals(tableA, tableB);
        assertEquals(tableA.getColCount(), tableB.getColCount());
        assertEquals(tableA.getRowCount(), tableB.getRowCount());

        final Integer sumColA_TableA = tableA.eval(Sum.from("ColA"));
        final Integer sumColA_TableB = tableB.eval(Sum.from("ColA"));
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
    public void test_1M_addCol() {
        final Table tableA = new Table();

        tableA.addCol(new IntCol("ColA"));
        tableA.addCol(new DblCol("ColB"));
        tableA.addCol(new StrCol("ColC"));

        for (int i = 0; i < 1000000; i++) {
            tableA.addRow(Row.of(i, i * 0.1, "val_" + i));
        }

        assertEquals(3, tableA.getColCount());

        tableA.addCol(new LngCol("ColD"));

        assertEquals(4, tableA.getColCount());
        assertNull(tableA.getFirstRow().get(3));
        assertNull(tableA.getLastRow().get(3));

    }
}
