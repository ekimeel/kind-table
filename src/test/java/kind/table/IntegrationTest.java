package kind.table;

import kind.table.cols.GroupCol;
import kind.table.cols.IntCol;
import kind.table.cols.StrCol;
import kind.table.funcs.GroupBy;
import kind.table.funcs.KeepCols;
import kind.table.funcs.Mean;
import kind.table.funcs.Sum;
import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

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
                eval(new GroupBy(0,
                        GroupCol.of("Total", Sum.from(1))));

        //result.print(System.out);

    }

    @Test
    public void testParalle() {

        final TableSettings parallel50000 = new TableSettingsBuilder()
                .withAllowParallelProcessingAfterRow(5000)
                .build();

        final Table parallelTable = new TableBuilder()
                .withSettings(parallel50000)
                .withIntCol("val").build();

        TableSettings single50000 = new TableSettingsBuilder()
                .withAllowParallelProcessingAfterRow(5000)
                .build();

        final Table singleTable = new TableBuilder()
                .withSettings(single50000)
                .withIntCol("val").build();

        range(0, 50000).forEach( i -> {
            parallelTable.addRow(i);
            singleTable.addRow(i);
        });

        final Table results = new TableBuilder().withStrCol("table").withLngCol("time").build();
        range(0, 100).forEach( i -> {
            long start = Instant.now().toEpochMilli();
            parallelTable.eval(Sum.from(0));
            results.addRow("parallel", (Instant.now().toEpochMilli() - start));
        });

        range(0, 100).forEach( i -> {
            long start = Instant.now().toEpochMilli();
            parallelTable.eval(Sum.from(0));
            results.addRow("single", (Instant.now().toEpochMilli() - start));
        });

        results.eval(GroupBy.from(0,
                GroupCol.of("avg_time", Mean.from("time")) ));


        System.out.println(String.format("Parallel time = %s ms", results.eval(Mean.from("time"))));


    }
}
