package kind.table.time;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.InstantColumn;
import kind.table.cols.IntColumn;
import kind.table.cols.StrColumn;
import org.junit.Test;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

public class TableTest {

    @Test
    public void test_() {

        final Table tableA = new Table();

        tableA.addCol(new InstantColumn("timestamp"));
        tableA.addCol(new IntColumn("value"));

        Instant now = Instant.now();

        for (int i = 0; i < 525600; i++) {
            tableA.addRow(new Row(now, i));
            now = now.plus(Duration.ofMinutes(1));
        }


        tableA.addCol(new StrColumn("weekday"), row -> {
            final Instant instant = row.get(0);

            final String weekday = instant.atZone(ZoneId.of("UTC")).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            return row.append(weekday);
        });



    }
}
