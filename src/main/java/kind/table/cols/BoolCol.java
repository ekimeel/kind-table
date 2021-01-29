package kind.table.cols;

import java.io.Serializable;

public final class BoolCol extends Col<Boolean> implements Serializable {

    /**
     * returns a new [[BoolCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[BoolCol]] with the provided name
     */
    public static BoolCol of(String name) {
        return new BoolCol(name);
    }

    public BoolCol(String name) {
        super(name);
    }
    public BoolCol(String name, int index) {
        super(name, index);
    }

    @Override
    public Boolean cast(Object value) {
        if (value == null) return null;

        return (Boolean)value;
    }

    @Override
    public BoolCol copy() {
        return new BoolCol(getName(), getIndex());
    }
}
