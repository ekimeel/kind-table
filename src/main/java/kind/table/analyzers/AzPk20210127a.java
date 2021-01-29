package kind.table.analyzers;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.cols.Col;
import kind.table.cols.NumCol;
import kind.table.funcs.Max;

import java.util.Set;

public final class AzPk20210127a extends AbstractAnalyzer {



    public AzPk20210127a() {
        super(AzPk20210127a.class.getSimpleName());
    }

    @Override
    protected void beforeExec(AnalyzerRequest request, AnalyzerResponse response) {
        if (request.getTable() == null) {
            stop();
            response.setErr(new NullPointerException("No table provided"));
        }

    }

    @Override
    protected void execTemplate(AnalyzerRequest request, AnalyzerResponse response) {
       final Table table = request.getTable();

       final Set<Col> numericCols = table.getColsOfType(NumCol.class);

       final Table result = new TableBuilder()
               .withStrCol("column")
               .withDbCol("value")
               .withTsCol("ts")
               .withIntCol("occurs")
               .build();

       for(Col c : numericCols) {
           final String name  = c.getName();
           final Number number = table.eval(Max.from(c.getIndex()));










       }




    }
}
