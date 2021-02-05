package kind.table.cols;

import kind.table.Row;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A column containing inner rows
 *
 */
public final class RowCol extends Col<ArrayList<Row>> implements Serializable {

    /**
     * returns a new RowCol with the provided name
     *
     * @param name a valid name
     * @return a new RowCol with the provided name
     */
    public static RowCol of(String name) {
        return new RowCol(name);
    }

    public RowCol(String name) {
        super(name);
    }

    @Override
    public Col copy() {
        return null;
    }

    @Override
    public ArrayList convert(Object value, String format) {
        return null;
    }
}
