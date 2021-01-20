package kind.table.cols.funcs;

import kind.table.Table;
import kind.table.cols.*;

import java.time.Instant;
import java.time.ZoneId;

public final class HourOfDay implements ColumnFunc {

    private static final String DEFAULT_NAME = "HourOfDay";
    public static HourOfDay from(String name, int col) { return new HourOfDay(name, ColRef.from(col)); }
    public static HourOfDay from(int col) { return new HourOfDay(DEFAULT_NAME, ColRef.from(col)); }
    /**/
    public static HourOfDay from(String col) { return new HourOfDay(DEFAULT_NAME, ColRef.from(col)); }
    public static HourOfDay from(String name, String col) { return new HourOfDay(name, ColRef.from(col)); }
    /**/

    private final String colName;
    private final ColRef colRef;

    private HourOfDay(String colName, ColRef colRef) {
        this.colName = colName;
        this.colRef = colRef;
    }

    private boolean acceptColumn(Column column) {
        if (column instanceof InstantColumn) { return true; }
        else if (column instanceof DateColumn) { return true; }
        else { return false; }
    }


    /**
     * Adds a [[IntColumn]] with the hour-of-day (0-23) to the table.
     *
     * @throws IllegalArgumentException If column with name already exists
     * @throws IllegalArgumentException If column type is not supported
     * @param table The table to preform the function on
     */
    @Override
    public void eval(Table table) {
        if (table.hasCol(colName)) {
            throw new IllegalArgumentException(String.format("Column [%s] already exists.", colName));
        }

        final Column column = table.getColByRef(this.colRef);
        if (!acceptColumn(column)) {
            throw new IllegalArgumentException(String.format("Column type [%] is not supported by this Column Function",
                    column.getClass().getSimpleName()));
        }

        if (column instanceof DateColumn) {
            evalWithDate(table, column);
        } else if (column instanceof InstantColumn) {
            evalWithInstant(table, column);
        } else {
            //todo: append null
        }

    }

    private void evalWithDate(Table table, Column source) {
        table.addCol(new StrColumn(this.colName), row -> {
            final java.util.Date date = row.get(source.getIndex());
            final int weekday = (int)(date.getTime() % 86400000) / 3600000;
            return row.append(weekday);
        });
    }

    private void evalWithInstant(Table table, Column source) {
        table.addCol(new StrColumn(this.colName), row -> {
            final Instant instant = row.get(source.getIndex());
            final Integer hour = instant.atZone(ZoneId.of("UTC"))
                    .getHour();

            return row.append(hour);
        });
    }
}

