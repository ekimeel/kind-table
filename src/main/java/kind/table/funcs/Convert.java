package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.*;
import java.util.UUID;

public final class Convert extends AbstractFunc<Table> {

    public static Convert toIntCol(String col) { return new Convert(ColRef.of(col), IntCol.class); }
    public static Convert toIntCol(int col) { return new Convert(ColRef.of(col), IntCol.class); }
    public static Convert toDblCol(String col) { return new Convert(ColRef.of(col), DblCol.class); }
    public static Convert toDblCol(int col) { return new Convert(ColRef.of(col), DblCol.class); }
    public static Convert toLngCol(String col) { return new Convert(ColRef.of(col), LngCol.class); }
    public static Convert toLngCol(int col) { return new Convert(ColRef.of(col), LngCol.class); }
    public static Convert toStrCol(String col) { return new Convert(ColRef.of(col), StrCol.class); }
    public static Convert toStrCol(int col) { return new Convert(ColRef.of(col), StrCol.class); }
    public static Convert from(String col, Class<? extends Col> type) { return new Convert(ColRef.of(col), type); }
    public static Convert from(int col, Class<? extends Col> type) { return new Convert(ColRef.of(col), type); }

    /**/
    private final ColRef colRef;
    private final Class<? extends Col> type;


    public Convert(ColRef colRef, Class<? extends Col> type) {
        this.colRef = colRef;
        this.type = type;
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
        final Col oldCol = copy.getColByRef(this.colRef);
        final Col newCol = ColFactory.from(oldCol.getName(), this.type);
        oldCol.setName(UUID.randomUUID().toString());

        copy.addCol(newCol, (i) -> i.append( newCol.cast(i.get(oldCol.getIndex()))) );

        copy.removeCol(oldCol);

        return copy;
    }


}
