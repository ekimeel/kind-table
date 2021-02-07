package kind.table.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class CorrCoefTest {


    @Test
    public void test_eval_example01() {
        final Table table = new TableBuilder()
                .withIntCol("X")
                .withIntCol("Y")
                .build();
        table.addRow(10, 1);
        table.addRow(9, 2);
        table.addRow(8, 3);
        table.addRow(7, 4);
        table.addRow(6, 5);
        table.addRow(5, 6);
        table.addRow(4, 7);
        table.addRow(3, 8);
        table.addRow(2, 9);
        table.addRow(1, 10);

        final Double expected = -1.0;
        assertEquals(expected, table.eval(CorrCoef.of("X", "Y")));
    }

    @Test
    public void test_eval_example02() {
        final Table table = new TableBuilder()
                .withIntCol("X")
                .withIntCol("Y")
                .build();
        table.addRow(1, 100);
        table.addRow(2, 200);
        table.addRow(3, 300);
        table.addRow(4, 400);
        table.addRow(5, 500);
        table.addRow(6, 600);
        table.addRow(7, 700);
        table.addRow(8, 800);
        table.addRow(9, 900);
        table.addRow(10, 1000);

        final Double expected = 1.0;
        assertEquals(expected, table.eval(CorrCoef.of("X", "Y")));
    }

    @Test
    public void test_eval_example03() {
        final Table table = new TableBuilder()
                .withIntCol("X")
                .withIntCol("Y")
                .build();
        table.addRow(1, 100);
        table.addRow(2, 0);
        table.addRow(3, 300);
        table.addRow(4, 0);
        table.addRow(5, 500);
        table.addRow(6, 0);
        table.addRow(7, 700);
        table.addRow(8, 0);
        table.addRow(9, 900);
        table.addRow(10, 0);

        final Double expected = 0.299;
        assertEquals(expected, table.eval(CorrCoef.of("X", "Y")), 0.001);
    }
}
