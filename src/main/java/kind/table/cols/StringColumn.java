package kind.table.cols;

import java.io.Serializable;

public class StringColumn extends Column<String> implements Serializable {

    /**
     * returns a new [[StringColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[StringColumn]] with the provided name
     */
    public static StringColumn of(String name) {
        return new StringColumn(name);
    }

    public StringColumn(String name) {
        super(name);
    }

    @Override
    public String cast(Object value) {
        if (value == null) {
            return null;
        }

        return String.valueOf(value);
    }

    @Override
    public Column copy() {
        return new StringColumn(getName());
    }
}
