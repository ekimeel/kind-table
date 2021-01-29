package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;

public class Copy implements Func<Table> {

    public Copy() {
    }

    @Override
    public boolean acceptCol(Col col) {
        return true;
    }

    @Override
    public Table eval(Table table) {
        return table.copy();
    }

}
