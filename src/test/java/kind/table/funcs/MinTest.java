package kind.table.funcs;

import kind.table.*;
import kind.table.cols.DblCol;
import kind.table.cols.IntCol;
import kind.table.cols.LngCol;
import org.junit.Test;
import static org.junit.Assert.*;

public class MinTest {

    @Test
    public void test_eval_withIntegerColumn(){
        final Table table = new Table();

        table.addCol(new IntCol("Odd"));
        table.addCol(new IntCol("Even"));

        table.addRow(Row.from(1, 2));
        table.addRow(Row.from(0, 0));
        table.addRow(Row.from(5, 6));

        final Integer min = (Integer) table.eval(Min.of(0));
        assertEquals((Integer)0, min);
    }

    @Test
    public void test_eval_withDoubleColumn(){
        final Table table = new Table();

        table.addCol(new DblCol(("Odd")));
        table.addCol(new DblCol("Even"));

        table.addRow(Row.from(3.3, 2.2));
        table.addRow(Row.from(5.5, 4.4));
        table.addRow(Row.from(1.1, 6.6));

        final Double sum = table.eval(Min.of(0));
        assertEquals((Double) 1.1, sum);
    }

    @Test
    public void test_eval_withLongColumn(){
        final Table table = new Table();

        table.addCol(new LngCol(("Odd")));
        table.addCol(new LngCol("Even"));

        table.addRow(Row.from(10L, 20L));
        table.addRow(Row.from(30L, 40L));
        table.addRow(Row.from(50L, 60L));

        final Long min = table.eval(Min.of("Odd"));
        assertEquals((Long) 10L, min);
    }
}
