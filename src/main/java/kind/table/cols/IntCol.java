package kind.table.cols;

import java.io.Serializable;

public final class IntCol extends NumCol<Integer> implements Serializable {

    /**
     * returns a new [[IntegerCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[IntegerColumn]] with the provided name
     */
    public static IntCol of(String name) {
        return new IntCol(name);
    }

    public IntCol(String name) {
        super(name);
    }
    public IntCol(String name, int index) {
        super(name, index);
    }

    @Override
    public Integer cast(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number)value).intValue();
        } else if (value instanceof String) {
            return Integer.parseInt((String)value);
        }

        return (Integer) value;
    }

    @Override
    public Col copy() {
        return new IntCol(getName(), getIndex());
    }

}
