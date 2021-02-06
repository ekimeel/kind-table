package kind.table.cols.funcs;

import kind.table.Table;
import kind.table.cols.*;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * The type Hour of day.
 */
public final class HourOfDay implements ColFunc {

    private static final String DEFAULT_NAME = "HourOfDay";

    /**
     * From hour of day.
     *
     * @param name the name
     * @param col  the col
     * @return the hour of day
     */
    public static HourOfDay from(String name, int col) { return new HourOfDay(name, ColRef.of(col)); }

    /**
     * From hour of day.
     *
     * @param col the col
     * @return the hour of day
     */
    public static HourOfDay from(int col) { return new HourOfDay(DEFAULT_NAME, ColRef.of(col)); }

    /**
     * From hour of day.
     *
     * @param col the col
     * @return the hour of day
     */
    /**/
    public static HourOfDay from(String col) { return new HourOfDay(DEFAULT_NAME, ColRef.of(col)); }

    /**
     * From hour of day.
     *
     * @param name the name
     * @param col  the col
     * @return the hour of day
     */
    public static HourOfDay from(String name, String col) { return new HourOfDay(name, ColRef.of(col)); }
    /**/

    private final String colName;
    private final ColRef colRef;

    private HourOfDay(String colName, ColRef colRef) {
        this.colName = colName;
        this.colRef = colRef;
    }

    private boolean acceptCol(Col col) {
        if (col instanceof TsCol) { return true; }
        else if (col instanceof DateCol) { return true; }
        else { return false; }
    }


    /**
     * Adds a [[IntCol]] with the hour-of-day (0-23) to the table.
     *
     * @throws IllegalArgumentException If column with name already exists
     * @throws IllegalArgumentException If column type is not supported
     * @param table The table to preform the function on
     */
    @Override
    public void eval(Table table) {
        if (table.hasCol(colName)) {
            throw new IllegalArgumentException(String.format("Col [%s] already exists.", colName));
        }

        final Col col = table.getColByRef(this.colRef);
        if (!acceptCol(col)) {
            throw new IllegalArgumentException(String.format("Col type [%] is not supported by this Col Function",
                    col.getClass().getSimpleName()));
        }

        if (col instanceof DateCol) {
            evalWithDate(table, col);
        } else if (col instanceof TsCol) {
            evalWithInstant(table, col);
        } else {
            //todo: append null
        }

    }

    private void evalWithDate(Table table, Col source) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone(table.getSettings().getTimeZone()));

        table.addCol(new StrCol(this.colName), row -> {
            final java.util.Date date = row.get(source.getIndex());
            calendar.setTime(date);
            int hours = calendar.get(Calendar.HOUR_OF_DAY);
            return row.append(hours);
        });
    }

    private void evalWithInstant(Table table, Col source) {
        table.addCol(new StrCol(this.colName), row -> {
            final Instant instant = row.get(source.getIndex());
            final String timeZone = table.getSettings().getTimeZone();

            final Integer hour = instant.atZone(ZoneId.of(timeZone)).getHour();

            return row.append(hour);
        });
    }
}

