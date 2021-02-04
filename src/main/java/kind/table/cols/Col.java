package kind.table.cols;

import kind.table.Copyable;

import java.io.Serializable;
import java.util.Objects;

public abstract class Col<T> implements Copyable<Col>, Serializable {

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
