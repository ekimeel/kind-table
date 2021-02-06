package kind.table.cols;

import java.io.Serializable;

/**
 * The type Int col.
 */
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

    /**
     * Instantiates a new Int col.
     *
     * @param name the name
     */
    public IntCol(String name) {
        super(name);
    }

    /**
     * Instantiates a new Int col.
     *
     * @param name  the name
     * @param index the index
     */
    public IntCol(String name, int index) {
        super(name, index);
    }

    @Override
    public Integer convert(Object value, String format) {
        if (value == null) {
            return null;
        } else if (value instanceof Number) {
            return ((Number)value).intValue();
        } else if (value instanceof String) {
            final String str = (String)value;
            return (str.equals(NULL_STRING))? null : Integer.parseInt(str);
        }

        return (Integer) value;
    }

    @Override
    public Col copy() {
        return new IntCol(getName(), getIndex());
    }

}
