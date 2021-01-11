package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Column;

public class Copy implements Func<Table> {

    public Copy() {
    }

    @Override
    public boolean acceptColumn(Column column) {
        return true;
    }

    @Override
    public Table eval(Table table) {
        return table.copy();
    }

}
