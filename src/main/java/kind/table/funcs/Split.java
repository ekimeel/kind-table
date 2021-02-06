package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Split.
 */
public class Split extends AbstractFunc<List<Table>> {

    /**
     * Of split.
     *
     * @param col the col
     * @return the split
     */
    public static Split of(int col) { return new Split(ColRef.of(col)); }

    /**
     * Of split.
     *
     * @param col the col
     * @return the split
     */
    public static Split of(String col) { return new Split(ColRef.of(col)); }

    /**
     * Of split.
     *
     * @param colRef the col ref
     * @return the split
     */
    public static Split of(ColRef colRef) { return new Split(colRef); }
    /**/
    private final ColRef colRef;

    /**
     * Instantiates a new Split.
     *
     * @param colRef the col ref
     */
    public Split(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfColRefNotFound(table, this.colRef);
    }

    @Override
    public List<Table> evalTemplate(Table table) {
        final List<Table> results = new ArrayList<>();
        final Col splitCol = table.getColByRef(this.colRef);
        final Map<Object, List<Row>> splits = table.
                getRows().
                stream().
                collect(Collectors.groupingBy( (r) -> r.get(splitCol.getIndex())));

        splits.forEach( (i, rows) -> {
            final Table split = new Table(table.getSettings());
            table.getCols().forEach( (c) -> split.addCol((Col) c.copy()));
            rows.forEach( (r) -> split.addRow(r) );
            results.add(split);
        });

        return results;
    }



}
