package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.ColRef;
import kind.table.cols.Column;
import kind.table.util.TableUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Join implements Func<Table> {

    public static Join from(int col, Table table) { return new Join(ColRef.of(col), table); }
    public static Join from(String col, Table table) { return new Join(ColRef.of(col), table); }
    public static Join from(ColRef colRef, Table table) { return new Join(colRef, table); }
    /**/
    private final ColRef colRef;
    private final Table tableB;
    public Join(ColRef colRef, Table tableB) {
        this.colRef = colRef;
        this.tableB = tableB;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return true;
    }


    @Override
    public Table eval(Table table) {
        final Table result = table.copy();
        final Column joinCol = result.getColByRef(colRef);
        final ColRef joinColRef = joinCol.toColRef();

        final Map<Integer, Integer> fromToColIndexMap = mapJoinCols(result, table, joinCol);
        final List<Comparable> keyVals = result.getVals(this.colRef);

        for (int i = 0; i < keyVals.size(); i++) {
            final Comparable keyVal = keyVals.get(i);
            final Row row = result.getRow(i);
            final List<Row> joinRows = tableB.getRowsEq(joinColRef, keyVal);

            if (joinRows.size() > 1) {
                throwNonUniqueKeysException(keyVal);
            } else if (joinRows.size() == 1) {
                final Row joinRow = joinRows.get(0);
                for (int from = 0; from < joinRow.size(); from++) {
                    final Integer to = fromToColIndexMap.get(from);
                    if (to != null) {
                        row.set(to, joinRow.get(from));
                    }
                }
            }
        }

        return result;

    }

    private Map<Integer, Integer> mapJoinCols(Table a, Table b, Column joinCol) {

        final Map<Integer, Integer> fromToColIndexMap = new HashMap<>(b.getColCount());
        b.getCols().forEach( (i) -> {
            if (!i.getName().equals(joinCol.getName())) {
                final Column c = (Column) i.copy();
                final int fromIndex = c.getIndex();
                c.setName(TableUtils.enumerateColName(a, c.getName()));
                fromToColIndexMap.put(fromIndex, a.addCol(c));
            }
        });
        return fromToColIndexMap;
    }

    private void throwNonUniqueKeysException(Object keyVal) {
        throw new IllegalStateException(String.format("Cannot join tables that contain duplicate key values. " +
                "Key value [%s] is non-unique.", keyVal));
    }


}
