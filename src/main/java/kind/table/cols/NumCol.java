package kind.table.cols;

public abstract class NumCol<T extends Number> extends Col<T> {

    protected NumCol(String name) {
        super(name);
    }

    protected NumCol(String name, int index) {
        super(name, index);
    }
}
