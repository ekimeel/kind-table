package kind.table.cols;

import kind.table.Row;

import java.io.Serializable;
import java.util.List;

public final class RowCol extends Col<List<Row>> implements Serializable {

    /**
     * returns a new [[ListCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[ListColumn]] with the provided name
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
    public List convert(Object value, String format) {
        return null;
    }
}
