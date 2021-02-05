package kind.table.cols;

import java.io.Serializable;

/**
 * A column containing {@link java.lang.String}
 *
 */
public final class StrCol extends Col<String> implements Serializable {

    /**
     * returns a new [[StringCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[StringColumn]] with the provided name
     */
    public static StrCol of(String name) {
        return new StrCol(name);
    }

    public StrCol(String name) {
        super(name);
    }
    public StrCol(String name, int index) {
        super(name, index);
    }

    @Override
    public String convert(Object value, String format) {
        if (value == null) {
            return null;
        }

        return String.valueOf(value);
    }

    @Override
    public Col copy() {
        return new StrCol(getName(), getIndex());
    }
}
