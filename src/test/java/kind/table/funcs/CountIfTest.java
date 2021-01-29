package kind.table.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.*;

public class CountIfTest {

    @Test
    public void test_eval() {
        final Table table = new TableBuilder()
                .withStrCol("col1")
                .withIntCol("val")
                .build();

        range(0, 100).forEach( i -> {
            table.addRow(("a" + i), i);
        });

        assertEquals(100, table.getRowCount());
        assertEquals((Integer) 49, table.eval(CountIf.from("val", i -> ((Integer)i > 50) ))); // don't count nulls

    }
}