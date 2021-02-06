package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;
import kind.table.util.TableUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Join implements Func<Table> {

    public static Join of(int col, Table table) { return new Join(ColRef.of(col), table); }
    public static Join of(String col, Table table) { return new Join(ColRef.of(col), table); }
    public static Join of(ColRef colRef, Table table) { return new Join(colRef, table); }
    /**/
    private final ColRef colRef;
    private final Table b;
    public Join(ColRef colRef, Table b) {
        this.colRef = colRef;
        this.b = b;
    }

    @Override
    public boolean acceptCol(Col col) {
        return true;
    }


    @Override
    public Table eval(Table table) {

        //todo: this join logic is a full scan each join, find a better impl
        final Table a = table.copy();
        final Col joinCol = a.getColByRef(colRef);
        final ColRef joinColRef = joinCol.toColRef();

        final Map<Integer, Integer> fromToColIndexMap = mapJoinCols(a, b, joinCol);
        final List<Comparable> keyVals = a.getVals(this.colRef);

        for (int i = 0; i < keyVals.size(); i++) {
            final Comparable keyVal = keyVals.get(i);
            final Row row = a.getRow(i);
            final List<Row> joinRows = b.getRowsEq(joinColRef, keyVal);

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

        return a;

    }

    private Map<Integer, Integer> mapJoinCols(Table a, Table b, Col joinCol) {

        final Map<Integer, Integer> fromToColIndexMap = new HashMap<>(b.getColCount());
        b.getCols().forEach( (i) -> {
            if (!i.getName().equals(joinCol.getName())) {
                final Col c = (Col) i.copy();
                final int fromIndex = c.getIndex();
                a.renameCol(c.getName(), TableUtils.enumerateColName(a, c.getName()));
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
