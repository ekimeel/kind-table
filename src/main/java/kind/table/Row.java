package kind.table;


import java.io.Serializable;
import java.util.*;

public class Row implements Copyable<Row> {

    private int index;
    private List values;

    public Row(List values) {
        this.values = values;
    }

    public Row(Object... values){
        this.values = new ArrayList(Arrays.asList(values));
    }

    public Row(){
        this.values = new ArrayList();
    }

    public List values(){
        return this.values;
    }

    public int size(){
        return this.values.size();
    }

    void setIndex(int index){
        this.index = index;
    }

    public int getIndex(){
        return this.index;
    }

    /**
     * Appends a value to the current values
     *
     * @param val A val
     * @return The row
     */
    public Row append(Object val) {
        this.values.add(val);
        return this;
    }

    /**
     * Returns the value at the column index
     * @param col The column index
     * @param <T> The return type
     * @return The value
     */
    public <T> T get(int col) {
        return (T) values.get(col);
    }

    /**
     * Returns a range of values between column indexes
     * @param startCol The start column index
     * @param endCol The end column index
     * @param <T> The return type
     * @return A sub list of values between the ranges
     */
    public <T> List<T> getValuesBetween(int startCol, int endCol) {
        return (List<T>) values.subList(startCol, endCol);
    }

    /**
     * Returns a range of values to the right of the provided column index (column index + count)
     * @param col The start column index
     * @param count The count of columns to the right
     * @param <T> The return type
     * @return A sub list of values between column ranges
     */
    public <T> List<T> getRightValues(int col, int count) {
        return (List<T>) values.subList(col, col + count);
    }


    /**
     * Returns a range of values to the left of the provided column index (column index - count)
     * @param col The start column index
     * @param count The count of columns to the left
     * @param <T> The return type
     * @return A sub list of values between column ranges
     */
    public <T> List<T> getLeftValues(int col, int count) {
        return (List<T>) values.subList(col - count, col);
    }

    /**
     * Returns all the column values to the right of the provided index
     * @param col The start column index
     * @param <T> The return type
     * @return A sub list of all values to the right of the provided column index
     */
    public <T> List<T> getRightValues(int col) {
        return (List<T>) values.subList(col, size() - 1);
    }

    /**
     * Returns all the column values to the left of the provided index
     * @param col The start column index
     * @param <T> The return type
     * @return A sub list of all values to the left of the provided column index
     */
    public <T> List<T> getLeftValues(int col) {
        return (List<T>) values.subList(0, col);
    }

    /**
     * Replaces the value at the specified position with the specified value and
     * returns the value (if any) previously at the specified position.
     *
     * @param col
     * @param value
     * @return
     */
    public Object set(int col, Object value) {
        return values.set(col, value);
    }


    /**
     *
     * @return
     */
    @Override
    public Row copy()  {

        final Row copy = new Row();

        for(Object value : values){

            if (value == null) {
                copy.values.add(null);
            } else {

                if (!(value instanceof Serializable)) {
                    throw new IllegalStateException(String.format("Value [%s] of type [%s] does not implement Serializable.", value, value.getClass()));
                }

                try {
                    copy.values.add(Serialization.clone((Serializable) value));
                } catch (Exception e) {
                    throw new RuntimeException(
                            String.format("Cannot clone value [%s] of class type [%s].", value, value.getClass()));
                }
            }
        }

        return copy;
    }

    @Override
    public String toString() {
        return "row: {\"index=\"" + index + "\", \"values\"=" + values().size() + " }";
    }


}
