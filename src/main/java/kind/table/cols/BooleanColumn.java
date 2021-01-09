package kind.table.cols;

import java.io.Serializable;

public class BooleanColumn extends Column<Boolean> implements Serializable {

    /**
     * returns a new [[BooleanColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[BooleanColumn]] with the provided name
     */
    public static BooleanColumn of(String name) {
        return new BooleanColumn(name);
    }

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
