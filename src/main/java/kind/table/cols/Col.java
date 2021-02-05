package kind.table.cols;

import kind.table.Copyable;

import java.io.Serializable;
import java.util.Objects;

/**
 * An abstract Column for {@link kind.table.Table}
 *
 * @param <T> The expected datatype stored in the column
 */
public abstract class Col<T extends Serializable> implements Copyable<Col>, Serializable {
    protected static String NULL_STRING = "null";
    private int index;
    private String name;

    protected Col(String name) {
        this.name = name;
    }

    protected Col(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Attempts to convert the provided value to the column's type
     *
     * @throws ClassCastException
     * @param value The value to try to cast
     * @return The casted value
     */
    public abstract T convert(Object value, String format);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Col<?> col = (Col<?>) o;
        return index == col.index &&
                Objects.equals(name, col.name);
    }

    public ColRef toColRef() {
        return ColRef.of(this.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, name);
    }

    @Override
    public String toString() {
        return "column: {\"name=\"" + name + "\", \"index\"=" + index + " }";
    }


}
