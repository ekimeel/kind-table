package kind.table.funcs.writers;

import kind.table.Table;


public interface TableWriter {
    static final String NULL_VALUE = "{null}";
    void write(Table table);
}
