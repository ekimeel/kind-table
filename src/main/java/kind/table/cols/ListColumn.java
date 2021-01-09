package kind.table.cols;

import java.io.Serializable;
import java.util.List;

public final class ListColumn extends Column<List> implements Serializable {

    public ListColumn(String name) {
        super(name);
    }

    @Override
    public Column copy() {
        return null;
    }

    @Override
    public List cast(Object value) {
        return null;
    }
}
