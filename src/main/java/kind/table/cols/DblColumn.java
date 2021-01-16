package kind.table.cols;

import java.io.Serializable;

public final class DblColumn extends NumberColumn<Double> implements Serializable {

    /**
     * returns a new [[DblColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[DblColumn]] with the provided name
     */
    public static DblColumn of(String name) {
        return new DblColumn(name);
    }

    public DblColumn(String name) {
        super(name);
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
    public Column copy() {
        return new DblColumn(getName());
    }
}
