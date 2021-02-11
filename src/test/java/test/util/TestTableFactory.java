package test.util;

import kind.table.Row;
import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.cols.TsCol;
import kind.table.cols.IntCol;
import kind.table.funcs.Convert;

import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;

public class TestTableFactory {

    public static Table hw_25000() {
        return new TableBuilder()
                .withCsvFile(Paths.get("./test-data/pub/","hw_25000.csv"))
                .build()
                .eval(Convert.toDblCol("Height(Inches)"))
                .eval(Convert.toDblCol("Weight(Pounds)"));
    }

    public static Table timeSeriesData() {

        final Table tableA = new Table();

        tableA.addCol(new TsCol("timestamp"));
        tableA.addCol(new IntCol("value"));

        Instant now = Instant.now();

        for (int i = 0; i < 525600; i++) {
            tableA.addRow(Row.from(now, i));
            now = now.plus(Duration.ofMinutes(1));
        }
        return tableA;
    }
}
