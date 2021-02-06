package kind.table;

import kind.table.cols.SummaryCol;
import kind.table.funcs.*;
import kind.table.writers.Markdown;
import kind.table.writers.TableWriterBuilder;
import org.junit.Test;

import java.time.Instant;

import static java.util.stream.IntStream.range;
import static org.junit.Assert.assertEquals;

public class PerformanceTest {

    @Test
    public void test_1m_rows() {
        final Table table = new TableBuilder()
                .withStrCol("colA").withIntCol("colB").withIntCol("colC")
                .withIntCol("colD").withIntCol("colE").withIntCol("colF")
                .withIntCol("colG").withIntCol("colH").withIntCol("colI")
                .withIntCol("J")
                .build();
        table.ensureRowCapacity(1000000);

        final long start = Instant.now().toEpochMilli();
        for (int i = 0; i < 1000000; i++) {
            table.addRow(i, (i+1), (i+2), (i+3), (i+4), (i+5), (i+6), (i+7), (i+8), (i+9));
        }
        System.out.println(String.format("Time to add %s to %s column table: %s ms",
                table.getRowCount(),
                table.getColCount(), (Instant.now().toEpochMilli() - start)));

        assertEquals(1000000, table.getRowCount());
        assertEquals(10, table.getColCount());
    }
    @Test
    public void test_1m_rows_copy() {
        final Table table = new TableBuilder()
                .withStrCol("colA").withIntCol("colB").withIntCol("colC")
                .withIntCol("colD").withIntCol("colE").withIntCol("colF")
                .withIntCol("colG").withIntCol("colH").withIntCol("colI")
                .withIntCol("J")
                .build();
        //table.ensureRowCapacity(1000000);

        for (int i = 0; i < 1000000; i++) {
            table.addRow(i, (i+1), (i+2), (i+3), (i+4), (i+5), (i+6), (i+7), (i+8), (i+9));
        }

        final long start = Instant.now().toEpochMilli();
        Table copy = table.copy();
        System.out.println(String.format("Time to copy %s to %s column table: %s ms",
                table.getRowCount(),
                table.getColCount(), (Instant.now().toEpochMilli() - start)));

        assertEquals(1000000, table.getRowCount());
        assertEquals(10, table.getColCount());
    }
    @Test
    public void testParallel() {

        final TableSettings parallel50000 = new TableSettingsBuilder()
                .withAllowParallelProcessingAfterRow(5000)
                .build();

        final Table parallelTable = new TableBuilder()
                .withSettings(parallel50000)
                .withIntCol("val").build();

        TableSettings single50000 = new TableSettingsBuilder()
                .withAllowParallelProcessingAfterRow(-1)
                .build();

        final Table singleTable = new TableBuilder()
                .withSettings(single50000)
                .withIntCol("val").build();

        parallelTable.ensureRowCapacity(500000);
        singleTable.ensureRowCapacity(500000);
        range(0, 500000).forEach( i -> {
            parallelTable.addRow(i);
            singleTable.addRow(i);
        });

        final Table times = new TableBuilder()
                .withStrCol("table")
                .withLngCol("time")
                .withIntCol("rows").build();

        range(0, 1000).forEach( i -> {
            long start = Instant.now().toEpochMilli();
            parallelTable.eval(Sum.of(0));
            times.addRow("parallel",
                    (Instant.now().toEpochMilli() - start),
                    parallelTable.getRowCount());
        });

        range(0, 1000).forEach( i -> {
            long start = Instant.now().toEpochMilli();
            singleTable.eval(Sum.of(0));
            times.addRow("single",
                    (Instant.now().toEpochMilli() - start),
                    singleTable.getRowCount());
        });

        final Table results = times.eval(GroupBy.of("table",
                SummaryCol.of("avg_time", Mean.of("time")),
                SummaryCol.of("count", Count.of("time")),
                SummaryCol.of("rows", Max.of("rows"))
        ));

        results.writeTo(new TableWriterBuilder().Markdown().usingStream(System.out).build());


    }
}
