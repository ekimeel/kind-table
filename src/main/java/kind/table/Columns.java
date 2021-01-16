package kind.table;

import kind.table.cols.Column;

import java.io.Serializable;
import java.util.*;

public final class Columns implements Serializable {

    private TreeMap<Integer, Column> values = new TreeMap();

    public Columns() {
    }

    public Columns(Collection<Column> cols) {
        super();
        cols.forEach( (i) -> this.values.put(size(), i));
    }

    public Integer size() {
        return this.values.size();
    }

    public boolean add(Column col) {
        col.setIndex(size());
        this.values.put(size(), col);
        return true;
    }

    public void addAll(Collection<Column> cols) {
        if (cols == null) return;
        cols.forEach( i -> add(i));
    }

    public void remove(Column col){
        this.values.remove(col.getIndex());
        indexCols();
    }

    public Collection<Column> values() {
        return this.values.values();
    }

    public void clear() {
        this.values.clear();
    }

    public Column get(int index) {
        return this.values.get(index);
    }

    public boolean hasIndex(Integer index) {
        return this.values.containsKey(index);
    }

    protected synchronized void indexCols() {
        //todo: shift around instead of collect, clear, add
        final Collection<Column> cols = new ArrayList<>(size());
        this.values.forEach( (k, v) -> cols.add(v));
        this.values.clear();
        cols.forEach( c -> this.add(c));
    }

}
