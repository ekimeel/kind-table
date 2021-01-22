package kind.table.cols;

import java.io.Serializable;

public final class BoolColumn extends Column<Boolean> implements Serializable {

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
    public BoolColumn(String name, int index) {
        super(name, index);
    }

    @Override
    public Boolean cast(Object value) {
        if (value == null) return null;

        return (Boolean)value;
    }

    @Override
    public BoolColumn copy() {
        return new BoolColumn(getName(), getIndex());
    }
}
