package kind.table.cols;

import java.io.Serializable;

public final class DblCol extends NumCol<Double> implements Serializable {

    /**
     * returns a new [[DblCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[DblCol]] with the provided name
     */
    public static DblCol of(String name) {
        return new DblCol(name);
    }

    public DblCol(String name) {
        super(name);
    }
    public DblCol(String name, int index) {
        super(name, index);
    }

    @Override
    public Double cast(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number)value).doubleValue();
        }

        return (Double) value;
    }

    @Override
    public Col copy() {
        return new DblCol(getName(), getIndex());
    }
}
