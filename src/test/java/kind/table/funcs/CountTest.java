package kind.table.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.*;

public class CountTest {

    @Test
    public void test_eval() {
        final Table table = new TableBuilder()
                .withStrCol("col1")
                .withIntCol("val")
                .build();

        range(0, 100).forEach( i -> {
            table.addRow(("a" + i), (i % 20 == 0)? null : i);
        });

        assertEquals(100, table.getRowCount());
        assertEquals((Integer) 95, table.eval(Count.from("val"))); // don't count nulls

    }
}