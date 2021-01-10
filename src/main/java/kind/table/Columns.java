package kind.table;

import kind.table.cols.Column;

import java.io.Serializable;
import java.util.*;

public final class Columns implements Serializable {

    private TreeMap<Integer, Column> values = new TreeMap();

    public Columns() {
    }

    public Columns(List<Column> cols) {
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
        final Iterator<Map.Entry<Integer, Column>> i = this.values.entrySet().iterator();
        while(i.hasNext()) {
            final Map.Entry<Integer, Column> entry = i.next();
            entry.getValue().setIndex(size());
            this.values.put(size(), entry.getValue());
        }
    }

}
