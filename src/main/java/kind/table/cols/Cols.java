package kind.table.cols;

import kind.table.cols.Col;

import java.io.Serializable;
import java.util.*;

/**
 * The type Cols.
 */
public final class Cols implements Serializable {

    private final TreeMap<Integer, Col> imap = new TreeMap();
    private final TreeMap<String, Integer> nmap = new TreeMap();

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
        cols.forEach( (i) -> this.imap.put(size(), i));
        indexCols();
    }

    /**
     * Size integer.
     *
     * @return the integer
     */
    public Integer size() {
        return this.imap.size();
    }

    /**
     * Add boolean.
     *
     * @param col the col
     * @return the boolean
     */
    public boolean add(Col col) {
        col.setIndex(size());
        this.imap.put(size(), col);
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
        this.imap.remove(col.getIndex());
        indexCols();
    }

    /**
     * Values collection.
     *
     * @return the collection
     */
    public Collection<Col> values() {
        return this.imap.values();
    }

    /**
     * Clear.
     */
    public void clear() {
        this.imap.clear();
    }

    /**
     * Get col.
     *
     * @param index the index
     * @return the col
     */
    public Col get(Integer index) {
        return (index == null)? null : this.imap.get(index);
    }

    /**
     * Get col.
     *
     * @param name the name
     * @return the col
     */
    public Col get(String name) {
        return get(this.nmap.get(name));
    }

    /**
     * Has index boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean hasIndex(Integer index) {
        return this.imap.containsKey(index);
    }

    /**
     * Rename col boolean.
     *
     * @param from the from
     * @param to   the to
     * @return the boolean
     */
    public boolean renameCol(String from, String to) {
        if (this.nmap.containsKey(to)) {
            throw new RuntimeException(String.format("col with name [%s] already exists", to));
        }

        get(from).setName(to);
        this.nmap.put(to, this.nmap.remove(from));
        return this.nmap.containsKey(to);
    }

    /**
     * Index cols.
     */
    private synchronized void indexCols() {
        //todo: shift around instead of collect, clear, add
        final Collection<Col> cols = new ArrayList<>(size());
        this.imap.forEach( (k, v) -> cols.add(v));
        this.imap.clear();
        this.nmap.clear();
        cols.forEach( (i) -> {
            i.setIndex(this.size());
            this.imap.put(this.imap.size(), i);
            this.nmap.put(i.getName(), i.getIndex());
        });

        if (imap.size() != nmap.size()) {
            throw new IllegalStateException("corrupt index state.");
        }
    }

}
