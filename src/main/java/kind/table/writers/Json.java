package kind.table.writers;

import kind.table.Table;
import kind.table.cols.ColRef;

import java.io.PrintStream;
import java.util.Map;

public final class Json extends AbstractWriter {



    public Json(PrintStream stream, Map<ColRef, String> formats) {
        super(stream, formats);
    }

    @Override
    protected void writeTemplate(Table table) {

    }
}
