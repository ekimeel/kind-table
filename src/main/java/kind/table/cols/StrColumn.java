package kind.table.cols;

import java.io.Serializable;

public final class StrColumn extends Column<String> implements Serializable {

    /**
     * returns a new [[StringColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[StringColumn]] with the provided name
     */
    public static StrColumn of(String name) {
        return new StrColumn(name);
    }

    public StrColumn(String name) {
        super(name);
    }
    public StrColumn(String name, int index) {
        super(name, index);
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
        return new StrColumn(getName(), getIndex());
    }
}
