package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

import java.util.Objects;
import java.util.stream.Stream;

public class Count implements Func<Integer> {

    public static Count from(String col) { return new Count(ColRef.of(col)); }
    public static Count from(int col) { return new Count(ColRef.of(col)); }

    /**/
    private final ColRef colRef;


    public Count(ColRef colRef) {
        this.colRef = colRef;
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

        return (int) stream.filter(Objects::nonNull).count();

    }

}
