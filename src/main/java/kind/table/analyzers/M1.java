package kind.table.analyzers;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.cols.Col;
import kind.table.cols.NumCol;
import kind.table.funcs.*;

import java.util.Set;

public final class M1 extends AbstractAnalyzer {

    public M1() {
        super(M1.class.getSimpleName());
    }

    @Override
    protected void beforeExec(Request request, Response response) {
        if (request.getTable() == null) {
            stop();
            response.setErr(new NullPointerException("No table provided"));
        }
    }

    @Override
    protected void execTemplate(Request request, Response response) {
       final Table table = request.getTable();

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

       response.setTable(result);






       //"The maximum $column is $max.";
       //"The minimum "
       //"The maximum $column is $max and occured $occurs time(s).";
       //"The maximum $column is $value and occured $occurs time(s). The max";

    }


}
