package kind.table.time;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntCol;
import kind.table.cols.TsCol;
import kind.table.cols.funcs.Weekday;
import org.junit.Test;
import java.time.Duration;
import java.time.Instant;

public class TableTest {

    @Test
    public void test_() {

        final Table tableA = new Table();

        tableA.addCol(new TsCol("timestamp"));
        tableA.addCol(new IntCol("value"));

        Instant now = Instant.now();

        for (int i = 0; i < 10; i++) {
            tableA.addRow(Row.from(now, i));
            now = now.plus(Duration.ofMinutes(1));
        }

        tableA.addCol(Weekday.from("weekday", "timestamp"));

    }
}
