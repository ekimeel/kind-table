package kind.table.cols;

import kind.table.funcs.Func;
import kind.table.funcs.UnsupportedColException;

import java.io.Serializable;

/**
 * The type Summary col.
 */
public final class SummaryCol extends Col implements Serializable {

    /**
     * returns a new [[SummaryCol]] with the provided name
     *
     * @param name a valid name
     * @param func the func
     * @return a new [[SummaryCol]] with the provided name
     */
    public static SummaryCol of(String name, Func func) {
        return new SummaryCol(name, func);
    }

    /**
     * Instantiates a new Summary col.
     *
     * @param name the name
     * @param func the func
     */
    public SummaryCol(String name, Func func) {
        super(name);
        this.func = func;
    }

    private final Func func;

    /**
     * Gets func.
     *
     * @return the func
     */
    public Func getFunc() {
        return func;
    }

    @Override
    public String convert(Object value, String format) {
        throw new UnsupportedColException("cannot cast");
    }

    @Override
    public Col copy() {
        throw new UnsupportedColException("cannot copy");
    }
}
