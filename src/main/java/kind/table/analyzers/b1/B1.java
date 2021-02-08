package kind.table.analyzers.b1;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.analyzers.Interpreter;
import kind.table.analyzers.Session;
import kind.table.cols.Col;
import kind.table.cols.NumCol;
import kind.table.funcs.*;

import java.util.Set;

public class B1 implements Interpreter {

    public static final String NAME = "B1";
    public static final String[] REQUIREMENTS = new String[0];

    private Session session;

    @Override
    public String[] getRequirements() {
        return REQUIREMENTS;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Session getSession() {
        return null;
    }


    @Override
    public Object call() throws Exception {


        final Table table = session.getResponse().getTable();

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

        return null;

    }
}
