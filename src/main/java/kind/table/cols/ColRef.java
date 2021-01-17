package kind.table.cols;

/**
 *
 */
public final class ColRef {

    public static ColRef of(Object any) { return new ColRef(any); }
    private final String strRef;
    private final Integer intRef;

    public ColRef(Object any) {
        this.strRef = (any instanceof String)? (String) any : null;
        this.intRef = (any instanceof Integer)? (Integer) any : null;;
    }

    public ColRef(String strRef, Integer intRef) {
        this.strRef = strRef;
        this.intRef = intRef;
    }

    public boolean isInt() {
        return intRef != null;
    }

    public int asInt() {
        return intRef;
    }

    public boolean isStr() {
        return strRef != null;
    }

    public String asStr() {
        return strRef;
    }

    public Object getRef() {
        return (isInt())? intRef : strRef;
    }
}
