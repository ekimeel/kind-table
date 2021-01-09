package kind.table.cols;

import java.io.Serializable;

public class StringColumn extends Column<String> implements Serializable {

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
