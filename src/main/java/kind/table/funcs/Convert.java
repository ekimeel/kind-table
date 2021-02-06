package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.*;
import java.util.UUID;

/**
 * The type Convert.
 */
public final class Convert extends AbstractFunc<Table> {
    /**
     * To date col convert.
     *
     * @param col    the col
     * @param format the format
     * @return the convert
     */
    public static Convert toDateCol(String col, String format) { return new Convert(ColRef.of(col), DateCol.class, format); }

    /**
     * To date col convert.
     *
     * @param col    the col
     * @param format the format
     * @return the convert
     */
    public static Convert toDateCol(int col, String format) { return new Convert(ColRef.of(col), DateCol.class, format); }

    /**
     * To int col convert.
     *
     * @param col the col
     * @return the convert
     */
    public static Convert toIntCol(String col) { return new Convert(ColRef.of(col), IntCol.class); }

    /**
     * To int col convert.
     *
     * @param col the col
     * @return the convert
     */
    public static Convert toIntCol(int col) { return new Convert(ColRef.of(col), IntCol.class); }

    /**
     * To dbl col convert.
     *
     * @param col the col
     * @return the convert
     */
    public static Convert toDblCol(String col) { return new Convert(ColRef.of(col), DblCol.class); }

    /**
     * To dbl col convert.
     *
     * @param col the col
     * @return the convert
     */
    public static Convert toDblCol(int col) { return new Convert(ColRef.of(col), DblCol.class); }

    /**
     * To lng col convert.
     *
     * @param col the col
     * @return the convert
     */
    public static Convert toLngCol(String col) { return new Convert(ColRef.of(col), LngCol.class); }

    /**
     * To lng col convert.
     *
     * @param col the col
     * @return the convert
     */
    public static Convert toLngCol(int col) { return new Convert(ColRef.of(col), LngCol.class); }

    /**
     * To str col convert.
     *
     * @param col the col
     * @return the convert
     */
    public static Convert toStrCol(String col) { return new Convert(ColRef.of(col), StrCol.class); }

    /**
     * To str col convert.
     *
     * @param col the col
     * @return the convert
     */
    public static Convert toStrCol(int col) { return new Convert(ColRef.of(col), StrCol.class); }

    /**
     * From convert.
     *
     * @param col  the col
     * @param type the type
     * @return the convert
     */
    public static Convert from(String col, Class<? extends Col> type) { return new Convert(ColRef.of(col), type); }

    /**
     * From convert.
     *
     * @param col  the col
     * @param type the type
     * @return the convert
     */
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
