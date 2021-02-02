package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColFactory;
import kind.table.cols.ColRef;

import java.util.UUID;


public class CastCol implements Func<Table> {

    public static CastCol from(String col, Class<? extends Col> type) { return new CastCol(ColRef.of(col), type); }
    public static CastCol from(int col, Class<? extends Col> type) { return new CastCol(ColRef.of(col), type); }

    /**/
    private final ColRef colRef;
    private final Class<? extends Col> type;


    public CastCol(ColRef colRef, Class<? extends Col> type) {
        this.colRef = colRef;
        this.type = type;
    }

    @Override
    public boolean acceptCol(Col col) {
        return true;
    }

    @Override
    public Table eval(Table table) {
        final Table copy = table.copy();
        final Col oldCol = copy.getColByRef(this.colRef);
        final Col newCol = ColFactory.from(oldCol.getName(), this.type);
        oldCol.setName(UUID.randomUUID().toString());

        copy.addCol(newCol, (i) -> {
            return i.append( newCol.cast(i.get(oldCol.getIndex())));
        });

        copy.removeCol(oldCol);

        return copy;

    }

}
