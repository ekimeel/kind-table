package kind.table.funcs;

import kind.table.cols.IntegerColumn;
import kind.table.Row;
import kind.table.Table;
import org.junit.Test;

public class LinearInterpolationTest {

    @Test
    public void test_eval_withIntegerColumn(){
        final Table table = new Table();

        table.addColumn(new IntegerColumn("ColA"));
        table.addColumn(new IntegerColumn("ColB"));

        table.addRow(new Row(1, 2));
        table.addRow(new Row(3, 4));
        table.addRow(new Row(5, 6));
        table.addRow(new Row(7, null));
        table.addRow(new Row(9, null));
        table.addRow(new Row(11, null));
        table.addRow(new Row(13, null));
        table.addRow(new Row(15, 16));
        table.addRow(new Row(17, 18));

        final Table table2 = table.eval(new LinearInterpolation(1));

    }

}