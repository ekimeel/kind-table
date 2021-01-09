package kind.table.cols;

import java.io.Serializable;

public class BooleanColumn extends Column<Boolean> implements Serializable {

    public BooleanColumn(String name) {
        super(name);
    }

    @Override
    public Boolean cast(Object value) {
        if (value == null) return null;

        return (Boolean)value;
    }

    @Override
    public Column copy() {
        return new BooleanColumn(getName());
    }
}
