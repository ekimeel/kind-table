package kind.table.cols;

import kind.table.funcs.Func;
import kind.table.funcs.UnsupportedColException;

import java.io.Serializable;

public final class GroupCol extends Col implements Serializable {

    /**
     * returns a new [[GroupCol]] with the provided name
     *
     * @param name a valid name
     * @return a new [[GroupCol]] with the provided name
     */
    public static GroupCol of(String name, Func func) {
        return new GroupCol(name, func);
    }

    public GroupCol(String name, Func func) {
        super(name);
        this.func = func;
    }

    private final Func func;

    public Func getFunc() {
        return func;
    }

    @Override
    public String cast(Object value) {
        throw new UnsupportedColException("cannot cast");
    }

    @Override
    public Col copy() {
        throw new UnsupportedColException("cannot copy");
    }
}
