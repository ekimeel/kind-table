package kind.table.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.cols.IntCol;
import org.junit.Test;

import static org.junit.Assert.*;

public class ConvertTest {

    @Test
    public void test_eval_StrColToIntCol() {

        final Table table = new TableBuilder()
                .withStrCol("str_col")
                .build();

        table.addRow("1");
        table.addRow("2");
        table.addRow("3");

        final Table result = table.eval(Convert.from("str_col", IntCol.class));
        assertEquals((Integer)6, result.eval(Sum.from("str_col")));


    }

}