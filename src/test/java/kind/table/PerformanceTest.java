package kind.table;

import kind.table.cols.SummaryCol;
import kind.table.funcs.*;
import kind.table.funcs.writers.Markdown;
import org.junit.Test;

import java.time.Instant;

import static java.util.stream.IntStream.range;

public class PerformanceTest {

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

        System.out.println("1");

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
            parallelTable.eval(Sum.from(0));
            times.addRow("parallel",
                    (Instant.now().toEpochMilli() - start),
                    parallelTable.getRowCount());
        });

        range(0, 1000).forEach( i -> {
            long start = Instant.now().toEpochMilli();
            singleTable.eval(Sum.from(0));
            times.addRow("single",
                    (Instant.now().toEpochMilli() - start),
                    singleTable.getRowCount());
        });

        final Table results = times.eval(GroupBy.from("table",
                SummaryCol.of("avg_time", Mean.from("time")),
                SummaryCol.of("count", Count.from("time")),
                SummaryCol.of("rows", Max.from("rows"))
        ));

        results.writeTo(new Markdown(System.out));

    }
}
