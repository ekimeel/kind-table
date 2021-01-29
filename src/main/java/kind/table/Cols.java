package kind.table;

import kind.table.cols.Col;

import java.io.Serializable;
import java.util.*;

public final class Cols implements Serializable {

    private TreeMap<Integer, Col> values = new TreeMap();

    public Cols() {
    }

    public Cols(Collection<Col> cols) {
        super();
        cols.forEach( (i) -> this.values.put(size(), i));
        indexCols();
    }

    public Integer size() {
        return this.values.size();
    }

    public boolean add(Col col) {
        col.setIndex(size());
        this.values.put(size(), col);
        indexCols();
        return true;
    }

    public void addAll(Collection<Col> cols) {
        if (cols == null) return;
        cols.forEach( i -> add(i));
    }

    public void remove(Col col){
        this.values.remove(col.getIndex());
        indexCols();
    }

    public Collection<Col> values() {
        return this.values.values();
    }

    public void clear() {
        this.values.clear();
    }

    public Col get(int index) {
        return this.values.get(index);
    }

    public boolean hasIndex(Integer index) {
        return this.values.containsKey(index);
    }

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
