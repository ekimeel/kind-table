package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;
import kind.table.cols.Col;
import kind.table.cols.NumCol;

public final class Mean implements Func<Double> {

    public static Mean from(String col) { return new Mean(ColRef.of(col)); }
    public static Mean from(int col) { return new Mean(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private Mean(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public Double eval(Table table) {
        final Col col = table.getColByRef(this.colRef);
        final Number sum = table.eval(Sum.from(col.getIndex()));
        return sum.doubleValue() / table.getRowCount();
    }


}
