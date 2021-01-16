package kind.table.cols;

import java.io.Serializable;

public class BoolColumn extends Column<Boolean> implements Serializable {

    /**
     * returns a new [[BoolColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[BoolColumn]] with the provided name
     */
    public static BoolColumn of(String name) {
        return new BoolColumn(name);
    }

    public BoolColumn(String name) {
        super(name);
    }

    @Override
    public Boolean cast(Object value) {
        if (value == null) return null;

        return (Boolean)value;
    }

    @Override
    public Column copy() {
        return new BoolColumn(getName());
    }
}
