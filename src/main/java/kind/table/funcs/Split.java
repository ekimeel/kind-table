package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.ColRef;
import kind.table.cols.Column;
import kind.table.cols.NumberColumn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Split implements Func<List<Table>> {

    public static Split of(int col) { return new Split(ColRef.of(col)); }
    public static Split of(String col) { return new Split(ColRef.of(col)); }
    public static Split of(ColRef colRef) { return new Split(colRef); }
    /**/
    private final ColRef colRef;
    public Split(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptColumn(Column column) { return true; }

    @Override
    public List<Table> eval(Table table) {
        final List<Table> results = new ArrayList<>();
        final Column splitCol = table.getColByRef(this.colRef);
        final Map<Object, List<Row>> splits = table.
                getRows().
                stream().
                collect(Collectors.groupingBy( (r) -> r.get(splitCol.getIndex())));

        splits.forEach( (i, rows) -> {
            final Table split = new Table(table.getSettings());
            table.getCols().forEach( (c) -> split.addCol((Column) c.copy()));
            rows.forEach( (r) -> split.addRow(r) );
            results.add(split);
        });

        return results;
    }



}
