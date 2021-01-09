package kind.table.cols;

public abstract class NumberColumn<T extends Number> extends Column<T>{

    public NumberColumn(String name) {
        super(name);
    }
}
