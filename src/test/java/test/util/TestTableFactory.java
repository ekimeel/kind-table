package test.util;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.InstantColumn;
import kind.table.cols.IntColumn;

import java.time.Duration;
import java.time.Instant;

public class TestTableFactory {


    public static Table timeSeriesData() {

        final Table tableA = new Table();

        tableA.addCol(new InstantColumn("timestamp"));
        tableA.addCol(new IntColumn("value"));

        Instant now = Instant.now();

        for (int i = 0; i < 525600; i++) {
            tableA.addRow(new Row(now, i));
            now = now.plus(Duration.ofMinutes(1));
        }
        return tableA;
    }
}
