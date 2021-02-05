package kind.table.cols;

import java.io.Serializable;

/**
 * The type Dbl col.
 */
public final class DblCol extends NumCol<Double> implements Serializable {

    /**
     * Of dbl col.
     *
     * @param name the name
     * @return the dbl col
     */
    public static DblCol of(String name) {
        return new DblCol(name);
    }

    /**
     * Instantiates a new Dbl col.
     *
     * @param name the name
     */
    public DblCol(String name) {
        super(name);
    }

    /**
     * Instantiates a new Dbl col.
     *
     * @param name  the name
     * @param index the index
     */
    public DblCol(String name, int index) {
        super(name, index);
    }

    @Override
    public Double convert(Object value, String format) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number)value).doubleValue();
        } else if (value instanceof String) {
            final String str = (String)value;
            return (str.equals(NULL_STRING))? null : Double.parseDouble(str);
        }

        return (Double) value;
    }

    @Override
    public Col copy() {
        return new DblCol(getName(), getIndex());
    }
}
