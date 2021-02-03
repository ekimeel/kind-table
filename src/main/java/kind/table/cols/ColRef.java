package kind.table.cols;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColRef colRef = (ColRef) o;
        return Objects.equals(strRef, colRef.strRef) && Objects.equals(intRef, colRef.intRef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strRef, intRef);
    }

    @Override
    public String toString() {
        return String.format("ColRef name=%s (or) index=%s", strRef, intRef);

    }
}
