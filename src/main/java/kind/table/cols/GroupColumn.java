package kind.table.cols;

import kind.table.funcs.Func;
import kind.table.funcs.UnsupportedColumnException;

import java.io.Serializable;

public final class GroupColumn extends Column implements Serializable {

    /**
     * returns a new [[GroupColumn]] with the provided name
     *
     * @param name a valid name
     * @return a new [[GroupColumn]] with the provided name
     */
    public static GroupColumn of(String name, Func func) {
        return new GroupColumn(name, func);
    }

    public GroupColumn(String name, Func func) {
        super(name);
        this.func = func;
    }

    private final Func func;

    public Func getFunc() {
        return func;
    }

    @Override
    public String cast(Object value) {
        throw new UnsupportedColumnException("cannot cast");
    }

    @Override
    public Column copy() {
        throw new UnsupportedColumnException("cannot copy");
    }
}
