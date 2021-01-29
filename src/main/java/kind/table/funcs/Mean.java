package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;
import kind.table.cols.Col;
import kind.table.cols.NumCol;

import java.util.stream.Stream;

public final class Mean implements Func<Double> {

    public static Mean from(String col) { return new Mean(ColRef.of(col)); }
    public static Mean from(int col) { return new Mean(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private Mean(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public Double eval(Table table) {
        if (table == null) {
            return null;
        }

        final Stream stream = (table.allowParallelProcessing())?
                table.getVals(this.colRef).parallelStream() :
                table.getVals(this.colRef).stream();

        return stream
                .mapToDouble( x -> ((Number)x).doubleValue())
                .average()
                .getAsDouble();
    }


}
