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
    public Boolean convert(Object value, String format) {
        if (value == null) {
            return null;
        } else if (value instanceof String) {
           return Boolean.parseBoolean((String) value);
        } else if (value instanceof Number) {
           int val =  ((Number)value).intValue();
           return (val > 0)? true : (val < 1);
        } else {
            return (Boolean)value;
        }

    }

    @Override
    public Col copy() {
        return new BoolCol(getName(), getIndex());
    }
}
