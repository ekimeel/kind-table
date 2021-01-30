package kind.table;

import kind.table.cols.SummaryCol;
import kind.table.cols.IntCol;
import kind.table.cols.StrCol;
import kind.table.funcs.*;
import org.junit.Test;

import java.time.Instant;

import static java.util.stream.IntStream.range;

public class IntegrationTest {

    @Test
    public void test(){
        final Table table = new Table();

        table.addCol(StrCol.of("team"));
        table.addCol(StrCol.of("player"));
        table.addCol(IntCol.of("points"));

        table.addRow(new Row("a-team", "player-1", 1));
        table.addRow(new Row("a-team", "player-1", 2));
        table.addRow(new Row("a-team", "player-1", 1));

        table.addRow(new Row("a-team", "player-2", 4));
        table.addRow(new Row("a-team", "player-2", 10));

        table.addRow(new Row("b-team", "player-1", 4));
        table.addRow(new Row("b-team", "player-1", 1));

        table.addRow(new Row("c-team", "player-1", 10));
        table.addRow(new Row("c-team", "player-1", 8));
        table.addRow(new Row("c-team", "player-2", 15));
        table.addRow(new Row("c-team", "player-2", 12));


        Table result = table.
                eval(KeepCols.from(0, 2)).
                eval(GroupBy.from(0,
                        SummaryCol.of("Total", Sum.from(1))));

        //result.print(System.out);

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

        results.print(System.out);



    }
}
