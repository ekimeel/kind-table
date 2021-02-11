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

        table.addRow(Row.from(1, 2));
        table.addRow(Row.from(3, 4));
        table.addRow(Row.from(5, 6));
        table.addRow(Row.from(7, null));
        table.addRow(Row.from(9, null));
        table.addRow(Row.from(11, null));
        table.addRow(Row.from(13, null));
        table.addRow(Row.from(15, 16));
        table.addRow(Row.from(17, 18));

        final Table table2 = table.eval(new LinearInterpolation(1));

    }

}