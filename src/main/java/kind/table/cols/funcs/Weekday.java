package kind.table.cols.funcs;

import kind.table.Table;
import kind.table.cols.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * The type Weekday.
 */
public final class Weekday implements ColFunc {

    private static final String DEFAULT_NAME = "Weekday";

    /**
     * From weekday.
     *
     * @param name the name
     * @param col  the col
     * @return the weekday
     */
    public static Weekday from(String name, int col) { return new Weekday(name, ColRef.of(col)); }

    /**
     * From weekday.
     *
     * @param col the col
     * @return the weekday
     */
    public static Weekday from(int col) { return new Weekday(DEFAULT_NAME, ColRef.of(col)); }

    /**
     * From weekday.
     *
     * @param col the col
     * @return the weekday
     */
    /**/
    public static Weekday from(String col) { return new Weekday(DEFAULT_NAME, ColRef.of(col)); }

    /**
     * From weekday.
     *
     * @param name the name
     * @param col  the col
     * @return the weekday
     */
    public static Weekday from(String name, String col) { return new Weekday(name, ColRef.of(col)); }
    /**/

    private final String colName;
    private final ColRef colRef;

    private Weekday(String colName, ColRef colRef) {
        this.colName = colName;
        this.colRef = colRef;
    }

    private boolean acceptCol(Col col) {
        if (col instanceof TsCol) { return true; }
        else if (col instanceof DateCol) { return true; }
        else { return false; }
    }

    private void beforeRun(Table table) {
        if (table.hasCol(colName)) {
            throw new IllegalArgumentException(String.format("Col [%s] already exists.", colName));
        }

        final Col col = table.getColByRef(this.colRef);
        if (!acceptCol(col)) {
            throw new IllegalArgumentException(String.format("Col type [%] is not supported by this Col Function",
                    col.getClass().getSimpleName()));
        }
    }


    /**
     * Adds a [[StrCol]] with the weekday (Monday-Sunday) to the table.
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
            //todo: append null;
        }


    }

    private void evalWithDate(Table table, Col source) {
        final DateFormat format = new SimpleDateFormat("EEEE");

        table.addCol(new StrCol(this.colName), row -> {
            final java.util.Date date = row.get(source.getIndex());
            final String weekday = format.format(date);
            return row.append(weekday);
        });
    }

    private void evalWithInstant(Table table, Col source) {
        table.addCol(new StrCol(this.colName), row -> {
            final Instant instant = row.get(source.getIndex());

            final String weekday = instant.atZone(ZoneId.of("UTC"))
                    .getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.ENGLISH);

            return row.append(weekday);
        });
    }
}
