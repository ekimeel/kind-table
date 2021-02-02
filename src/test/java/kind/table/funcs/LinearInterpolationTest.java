package kind.table.funcs;

import kind.table.cols.IntCol;
import kind.table.Row;
import kind.table.Table;
import org.junit.Test;

public class LinearInterpolationTest {

    @Test
    public void test_eval_withIntegerCol(){
        final Table table = new Table();

        table.addCol(new IntCol("ColA"));
        table.addCol(new IntCol("ColB"));

        table.addRow(Row.of(1, 2));
        table.addRow(Row.of(3, 4));
        table.addRow(Row.of(5, 6));
        table.addRow(Row.of(7, null));
        table.addRow(Row.of(9, null));
        table.addRow(Row.of(11, null));
        table.addRow(Row.of(13, null));
        table.addRow(Row.of(15, 16));
        table.addRow(Row.of(17, 18));

        final Table table2 = table.eval(new LinearInterpolation(1));

    }

}