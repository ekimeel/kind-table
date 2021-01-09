package kind.table;

public interface Copyable<T extends Copyable> {
    T copy();
}
