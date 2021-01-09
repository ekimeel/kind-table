package kind.table.cols;

import java.io.Serializable;

public class IntegerColumn extends NumberColumn<Integer> implements Serializable {

    /**
     * returns a new [[IntegerColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[IntegerColumn]] with the provided name
     */
    public static IntegerColumn of(String name) {
        return new IntegerColumn(name);
    }

    public IntegerColumn(String name) {
        super(name);
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
        return new IntegerColumn(getName());
    }

}
