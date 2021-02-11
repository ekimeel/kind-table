package kind.table.analyzers.b1;

import com.google.common.base.Joiner;
import kind.table.Row;
import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.analyzers.Interpreter;
import kind.table.cols.Col;

import java.util.HashMap;
import java.util.Map;
import java.util.Spliterator;

public final class B1i1 extends Interpreter {

    public static final String NAME = "B1.i1";
    private final String MESSAGE_TEMPLATE = "The max of $name is $max.";
    private final String VAR_NAME = "$name";
    private final String VAR_MAX = "$max";
    /**/
    public B1i1() {
        super(NAME);
    }

    public boolean accept() {
        return getSession().has("b1");
    }


    @Override
    public Object call() throws Exception {

        final Table b1 = (Table) getData().get("b1");
        final Table response = getSession().getResponse().getTable();



        final Col maxCol = b1.getColByName(B1.COL_MAX);
        final Col nameCol = b1.getColByName(B1.COL_COLUMN);
        final Spliterator<Row> spliterator = b1.rowSpliterator();

        spliterator.forEachRemaining( (r) -> {
            final String name = r.get(nameCol.getIndex());
            final Number max = r.get(maxCol.getIndex());

            final Map<String, String> params = new HashMap<>(5);
            params.put(VAR_NAME, name);
            params.put(VAR_MAX, String.format("%d", max));

            final String message = format(MESSAGE_TEMPLATE, params);
            final String param = Joiner.on(",").withKeyValueSeparator(":").join(params);


        });

        return null;

    }


}
