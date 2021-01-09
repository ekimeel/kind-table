package kind.table.time;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.InstantColumn;
import kind.table.cols.IntegerColumn;
import kind.table.cols.StringColumn;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;

public class TableTest {

    @Test
    public void test_() {

        final Table tableA = new Table();

        tableA.addColumn(new InstantColumn("timestamp"));
        tableA.addColumn(new IntegerColumn("value"));

        Instant now = Instant.now();

        for (int i = 0; i < 525600; i++) {
            tableA.addRow(new Row(now, i));
            now = now.plus(Duration.ofMinutes(1));
        }


        tableA.addColumn(new StringColumn("weekday"), row -> {
            final Instant instant = row.get(0);

            final String weekday = instant.atZone(ZoneId.of("UTC")).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            return row.append(weekday);
        });

        tableA.print(System.out);


    }
}
