package kind.table.funcs;

import kind.table.Table;
import kind.table.funcs.writers.Markdown;
import org.junit.Test;
import test.util.TestTableFactory;

import static org.junit.Assert.*;

public class HistogramTest {

    @Test
    public void test_eval() {
        final Table table = TestTableFactory.hw_25000();

        final Integer bins = 5;
        final Table result = table.eval(Histogram.of("Height(Inches)", bins));

        final Integer binSize = result.eval(Sum.of("bin_size"));
        final Integer totalRows = table.eval(Count.of("Height(Inches)"));

        assertEquals(5, result.getRowCount());
        assertEquals(totalRows, binSize);






        result.writeTo(new Markdown(System.out));

    }

}