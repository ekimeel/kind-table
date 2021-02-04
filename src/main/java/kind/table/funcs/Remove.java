package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.*;

public final class Remove extends AbstractFunc<Table> {

    public static Remove from(String col) { return new Remove(ColRef.of(col)); }
    public static Remove from(int col) { return new Remove(ColRef.of(col)); }

    /**/
    private final ColRef colRef;

    public Remove(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return true;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfColRefNotFound(table, this.colRef);
    }

    @Override
    protected Table evalTemplate(Table table) {
        final Table copy = table.copy();
        final Col col = copy.getColByRef(this.colRef);

        copy.removeCol(col);

        return copy;
    }


}
