package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Column;
import kind.table.cols.NumberColumn;

import java.util.List;

/*
 * Last observation carried forward (Smear) is a method of imputing missing data. If a is non-null and
 * becomes null, then the last non-null value replaces the null values
 */
public final class Smear implements Func<Table> {

    private final Integer col;

    public Smear(int col) {
        this.col = col;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return (column instanceof NumberColumn);
    }

    @Override
    public Table eval(Table table) {
        final Table copy = table.copy();
        final Column column = copy.getColByIndex(this.col);

        if (!acceptColumn(column)) {
            throw new UnsupportedColumnException(column);
        }

        Object priorInstance = null;
        final List values = copy.getVals(this.col);

        for (int i = 0; i < values.size(); i++) {
            final Object value = values.get(i);

            if ((value == null) && priorInstance != null) {
                copy.set(i, this.col, priorInstance);
            } else if (value != null) {
                priorInstance = value;
            }
        }

        return copy;
    }

}