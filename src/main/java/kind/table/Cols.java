package kind.table;

import kind.table.cols.Col;
import kind.table.cols.ColRef;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Cols.
 */
public final class Cols implements Serializable {

    private TreeMap<Integer, Col> values = new TreeMap();

    /**
     * Instantiates a new Cols.
     */
    public Cols() {
    }

    /**
     * Instantiates a new Cols.
     *
     * @param cols the cols
     */
    public Cols(Collection<Col> cols) {
        super();
        cols.forEach( (i) -> this.values.put(size(), i));
        indexCols();
    }

    /**
     * Size integer.
     *
     * @return the integer
     */
    public Integer size() {
        return this.values.size();
    }

    /**
     * Add boolean.
     *
     * @param col the col
     * @return the boolean
     */
    public boolean add(Col col) {
        col.setIndex(size());
        this.values.put(size(), col);
        indexCols();
        return true;
    }

    /**
     * Add all.
     *
     * @param cols the cols
     */
    public void addAll(Collection<Col> cols) {
        if (cols == null) return;
        cols.forEach( i -> add(i));
    }

    /**
     * Remove.
     *
     * @param col the col
     */
    public void remove(Col col){
        this.values.remove(col.getIndex());
        indexCols();
    }

    /**
     * Values collection.
     *
     * @return the collection
     */
    public Collection<Col> values() {
        return this.values.values();
    }

    /**
     * Clear.
     */
    public void clear() {
        this.values.clear();
    }

    /**
     * Get col.
     *
     * @param index the index
     * @return the col
     */
    public Col get(int index) {
        return this.values.get(index);
    }

    public Col get(String name) {
        final Optional<Col> option = this.values.values().stream().filter(c -> c.getName().equals(name)).findFirst();
        return (!option.isPresent())? null : option.get();
    }

    /**
     * Has index boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean hasIndex(Integer index) {
        return this.values.containsKey(index);
    }

    /**
     * Index cols.
     */
    protected synchronized void indexCols() {
        //todo: shift around instead of collect, clear, add
        final Collection<Col> cols = new ArrayList<>(size());
        this.values.forEach( (k, v) -> cols.add(v));
        this.values.clear();
        cols.forEach( (i) -> {
            i.setIndex(this.size());
            this.values.put(this.values.size(), i);
        });
    }

}
