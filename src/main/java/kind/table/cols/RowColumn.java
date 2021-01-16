package kind.table.cols;

import kind.table.Row;

import java.io.Serializable;
import java.util.List;

public final class RowColumn extends Column<List<Row>> implements Serializable {

    /**
     * returns a new [[ListColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[ListColumn]] with the provided name
     */
    public static RowColumn of(String name) {
        return new RowColumn(name);
    }

    public RowColumn(String name) {
        super(name);
    }

    @Override
    public Column copy() {
        return null;
    }

    @Override
    public List cast(Object value) {
        return null;
    }
}
