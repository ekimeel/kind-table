package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.*;
import java.util.UUID;

public final class Convert extends AbstractFunc<Table> {
    public static Convert toDateCol(String col, String format) { return new Convert(ColRef.of(col), DateCol.class, format); }
    public static Convert toDateCol(int col, String format) { return new Convert(ColRef.of(col), DateCol.class, format); }
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
    private final String format;


    private Convert(ColRef colRef, Class<? extends Col> type) {
        this.colRef = colRef;
        this.type = type;
        this.format = null;
    }

    private Convert(ColRef colRef, Class<? extends Col> type, String format) {
        this.colRef = colRef;
        this.type = type;
        this.format = format;
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

        copy.renameCol(oldCol.getName(), UUID.randomUUID().toString());

        copy.addCol(newCol, (i) -> i.append( newCol.convert(i.get(oldCol.getIndex()), format)) );

        copy.removeCol(oldCol);

        return copy;
    }


}
