package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

import java.util.function.Predicate;
import java.util.stream.Stream;

public final class CountIf implements Func<Integer> {

    public static CountIf from(String col, Predicate predicate) { return new CountIf(ColRef.of(col), predicate); }
    public static CountIf from(int col, Predicate predicate) { return new CountIf(ColRef.of(col), predicate); }

    /**/
    private final ColRef colRef;
    private final Predicate predicate;


    public CountIf(ColRef colRef, Predicate predicate) {
        this.colRef = colRef;
        this.predicate = predicate;
    }

    @Override
    public boolean acceptCol(Col col) {
        return true;
    }

    @Override
    public Integer eval(Table table) {
        if (table == null) {
            return null;
        }

        final Stream<Object> stream = (table.allowParallelProcessing())?
                table.getVals(this.colRef).parallelStream() :
                table.getVals(this.colRef).stream();

        return (int) stream.filter(predicate).count();

    }

}
