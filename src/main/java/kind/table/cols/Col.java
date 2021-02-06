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
    /**
     * The constant NULL_STRING.
     */
    protected static String NULL_STRING = "null";
    private int index;
    private String name;

    /**
     * Instantiates a new Col.
     *
     * @param name the name
     */
    protected Col(String name) {
        this.name = name;
    }

    /**
     * Instantiates a new Col.
     *
     * @param name  the name
     * @param index the index
     */
    protected Col(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    void setName(String name) {
        this.name = name;
    }

    /**
     * Gets index.
     *
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Sets index.
     *
     * @param index the index
     */
    void setIndex(int index) {
        this.index = index;
    }

    /**
     * Attempts to convert the provided value to the column's type
     *
     * @param value  The value to try to cast
     * @param format the format
     * @return The casted value
     * @throws ClassCastException
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

    /**
     * To col ref col ref.
     *
     * @return the col ref
     */
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
