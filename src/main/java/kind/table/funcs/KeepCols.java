package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;

import java.util.*;

public final class KeepCols implements Func<Table> {

    private final List<Integer> cols = new ArrayList();

    public static KeepCols of(int... cols) { return new KeepCols(cols); }
    public KeepCols(int... cols) {
        Arrays.stream(cols).forEach( (i) -> this.cols.add(i));
    }

    @Override
    public boolean acceptCol(Col col) {
        return true;
    }


    @Override
    public Table eval(Table table) {
        final Table result = table.copy();
        final List<Col> removable = new ArrayList(cols.size());

        for(Col col : result.getCols()) {
            if (!this.cols.contains(col.getIndex())) {
                removable.add(col);
            }
        }

        for(Col col : removable) {
            result.removeCol(col);
        }

        return result;

    }


}
