package kind.table.cols;

import kind.table.Copyable;

import java.io.Serializable;
import java.util.Objects;

public abstract class Column<T> implements Copyable<Column>, Serializable {

    private int index;
    private String name;

    public Column(String name) {
        this.name = name;
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
     * Attempts to cast the provided value to the column's type
     *
     * @throws ClassCastException
     * @param value The value to try to cast
     * @return The casted value
     */
    public abstract T cast(Object value);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Column<?> column = (Column<?>) o;
        return index == column.index &&
                Objects.equals(name, column.name);
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
