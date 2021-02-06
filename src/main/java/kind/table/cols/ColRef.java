package kind.table.cols;

import java.io.Serializable;
import java.util.Objects;


/**
 * The type Col ref.
 */
public final class ColRef implements Serializable, Comparable<ColRef> {

    /**
     * Of col ref.
     *
     * @param name  the name
     * @param index the index
     * @return the col ref
     */
    public static ColRef of(String name, Integer index) { return new ColRef(name, index); }

    /**
     * Of col ref.
     *
     * @param any the any
     * @return the col ref
     */
    public static ColRef of(Object any) { return new ColRef(any); }
    /**/
    private final String strRef;
    private final Integer intRef;


    private ColRef(Object any) {
        this.strRef = (any instanceof String)? (String) any : null;
        this.intRef = (any instanceof Integer)? (Integer) any : null;
    }

    private ColRef(String strRef, Integer intRef) {
        this.strRef = strRef;
        this.intRef = intRef;
    }

    /**
     * Is int boolean.
     *
     * @return the boolean
     */
    public boolean isInt() {
        return intRef != null;
    }

    /**
     * As int int.
     *
     * @return the int
     */
    public int asInt() {
        return intRef;
    }

    /**
     * Is str boolean.
     *
     * @return the boolean
     */
    public boolean isStr() {
        return strRef != null;
    }

    /**
     * As str string.
     *
     * @return the string
     */
    public String asStr() {
        return strRef;
    }

    /**
     * Gets ref.
     *
     * @return the ref
     */
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

    @Override
    public int compareTo(ColRef other) {
        if (other == null) return -1;

        if (this.isInt() && other.isInt()) {
            return this.intRef.compareTo(other.intRef);
        }

        if (this.isStr() && this.isStr()) {
            return this.strRef.compareTo(other.strRef);
        }

        return -1;
    }
}
