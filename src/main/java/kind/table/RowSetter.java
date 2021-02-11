package kind.table;

import java.util.Map;
import java.util.TreeMap;

/**
 * The type Row setter.
 */
public final class RowSetter {

    private final Table table;
    private Map<Integer, Object> ask;

    /**
     * Instantiates a new Row setter.
     *
     * @param table the table
     */
    RowSetter(Table table) {
        this.table = table;
        this.ask = new TreeMap<>();
    }

    /**
     * Set row setter.
     *
     * @param col the col
     * @param val the val
     * @return the row setter
     */
    public RowSetter set(String col, Object val) {
        ask.put(table.getColIndex(col), val);
        return this;
    }

    /**
     * Add.
     */
    public void add() {
        table.addRow(Row.fromC(ask.values()));
    }
}
