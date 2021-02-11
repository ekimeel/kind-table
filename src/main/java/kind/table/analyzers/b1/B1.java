package kind.table.analyzers.b1;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.analyzers.Interpreter;
import kind.table.analyzers.Session;
import kind.table.cols.Col;
import kind.table.cols.NumCol;
import kind.table.funcs.*;

import java.util.Set;

public class B1 extends Interpreter {

    public static final String NAME = "B1";
    public static final String COL_COLUMN = "column";
    public static final String COL_MAX = "max";
    public static final String COL_MIN = "min";
    public static final String COL_MEAN = "mean";
    public static final String COL_STD_DEV = "std_dev";
    public static final String COL_COUNT = "count";
    public static final String COL_MAX_OCCURS = "max_occurs";
    public static final String COL_MIN_OCCURS = "min_occurs";


    public B1() {
        super(NAME);
    }

    @Override
    public Object call() throws Exception {



        final Table table = getSession().getResponse().getTable();

        final Set<Col> numericCols = table.getColsOfType(NumCol.class);

        final Table result = new TableBuilder()
                .withStrCol("column")
                .withDblCol("max")
                .withDblCol("min")
                .withDblCol("mean")
                .withDblCol("std_dev")
                .withIntCol("count")
                .withIntCol("max_occurs")
                .withIntCol("min_occurs")
                .build();

        for(Col c : numericCols) {
            final String name  = c.getName();
            final Number max = table.eval(Max.of(c.getIndex()));
            final Number min = table.eval(Min.of(c.getIndex()));
            final Number mean = table.eval(Mean.of(c.getIndex()));
            final Number std_dev = table.eval(StdDevPop.of(c.getIndex()));
            final Number count = table.eval(Count.of(c.getIndex()));
            final Number max_occurs = table.eval(CountIf.of(c.getIndex(), (val) -> val.equals(max)));
            final Number min_occurs = table.eval(CountIf.of(c.getIndex(), (val) -> val.equals(min)));
            result.addRow(name, max, min, mean, std_dev, count, max_occurs, min_occurs);
        }

        getData().put("b1.table", result);

        return null;

    }
}
