package kind.table.cols;

/**
 * The type Num col.
 *
 * @param <T> the type parameter
 */
public abstract class NumCol<T extends Number> extends Col<T> {

    /**
     * Instantiates a new Num col.
     *
     * @param name the name
     */
    protected NumCol(String name) {
        super(name);
    }

    /**
     * Instantiates a new Num col.
     *
     * @param name  the name
     * @param index the index
     */
    protected NumCol(String name, int index) {
        super(name, index);
    }
}
