package kind.table.cols;

public abstract class NumberColumn<T extends Number> extends Column<T>{

    protected NumberColumn(String name) {
        super(name);
    }

    protected NumberColumn(String name, int index) {
        super(name, index);
    }
}
