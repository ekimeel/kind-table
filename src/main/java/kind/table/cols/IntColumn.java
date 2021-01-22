package kind.table.cols;

import java.io.Serializable;

public final class IntColumn extends NumberColumn<Integer> implements Serializable {

    /**
     * returns a new [[IntegerColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[IntegerColumn]] with the provided name
     */
    public static IntColumn of(String name) {
        return new IntColumn(name);
    }

    public IntColumn(String name) {
        super(name);
    }
    public IntColumn(String name, int index) {
        super(name, index);
    }

    @Override
    public Integer cast(Object value) {
        if (value == null) {
            return null;
        }else if (value instanceof Number) {
            return ((Number)value).intValue();
        }

        return (Integer) value;
    }

    @Override
    public Column copy() {
        return new IntColumn(getName(), getIndex());
    }

}
