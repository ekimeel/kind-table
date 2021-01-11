package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Column;

import java.util.*;

public final class KeepCols implements Func<Table> {

    private final List<Integer> cols = new ArrayList();

    public KeepCols(int... cols) {
        Arrays.stream(cols).forEach( (i) -> this.cols.add(i));
    }

    @Override
    public boolean acceptColumn(Column column) {
        return true;
    }

    private boolean containsIndex(int colIndex) {
        boolean result = false;
        for(int i : this.cols) {
            if (i == colIndex) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public Table eval(Table table) {
        final Table result = table.copy();
        final List<Column> removable = new ArrayList(cols.size());

        for(Column col : result.getCols()) {
            if (!this.cols.contains(col.getIndex())) {
                removable.add(col);
            }
        }

        for(Column col : removable) {
            result.removeCol(col);
        }

        return result;

    }


}
