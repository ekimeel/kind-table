package kind.table;

import kind.table.cols.Col;
import kind.table.cols.ColRef;
import kind.table.cols.Cols;
import kind.table.cols.funcs.ColFunc;
import kind.table.funcs.Func;
import kind.table.writers.Writer;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Table.
 */
public class Table implements Copyable<Table>{

    private final Cols cols;
    private ArrayList<Row> rows;
    private final TableSettings settings;

    /**
     * Instantiates a new Table.
     */
    public Table() {
        this.cols = new Cols();
        this.settings = TableSettings.DEFAULT_SETTINGS;
        this.rows = new ArrayList<>(TableSettings.DEFAULT_ROW_CAPACITY);
    }

    /**
     * Create a new instance of a table with the provided settings. If the settings provided is null, setting will be
     * reassigned to TableSettings.DEFAULT_SETTINGS
     *
     * @param settings the settings
     */
    public Table(TableSettings settings){
        if (settings == null) throw new NullPointerException("Table Settings cannot be null.");

        this.cols = new Cols();
        this.rows = new ArrayList<>(settings.getDefaultRowCapacity());
        this.settings = settings;
    }

    /**
     * Instantiates a new Table.
     *
     * @param cols the cols
     */
    public Table(Collection<Col> cols) {
        this.cols = new Cols(cols);
        this.rows = new ArrayList<>(TableSettings.DEFAULT_ROW_CAPACITY);
        this.settings = TableSettings.DEFAULT_SETTINGS;
    }

    /**
     * Instantiates a new Table.
     *
     * @param cols     the cols
     * @param settings the settings
     */
    public Table(Collection<Col> cols, TableSettings settings) {
        this.cols = new Cols(cols);
        this.rows = new ArrayList<>(settings.getDefaultRowCapacity());
        this.settings = settings;
    }

    /**
     * Instantiates a new Table.
     *
     * @param cols the cols
     * @param rows the rows
     */
    public Table(Collection<Col> cols, ArrayList<Row> rows) {
        this.cols = new Cols(cols);
        this.rows = rows;
        this.settings = TableSettings.DEFAULT_SETTINGS;
    }

    /**
     * Instantiates a new Table.
     *
     * @param cols     the cols
     * @param rows     the rows
     * @param settings the settings
     */
    public Table(Collection<Col> cols, ArrayList<Row> rows, TableSettings settings) {
        this.cols = new Cols(cols);
        this.rows = rows;
        this.settings = settings;
    }

    /**
     * Returns the current table settings
     *
     * @return table settings
     */
    public TableSettings getSettings(){
        return this.settings;
    }

    /**
     * Returns true if the current table has a non-null settings instance, otherwise false.
     *
     * @return Returns true if a non-null settings exist, otherwise false.
     */
    public boolean hasSettings(){
        return this.settings != null;
    }

    /**
     * Returns the total size of columns in the current table.
     *
     * @return The number of columns
     */
    public int getColCount(){
        return this.cols.size();
    }

    /**
     * Returns an array of column names
     *
     * @return An array of column names
     */
    public String[] getColNames() {
        final String[] cols = new String[this.cols.size()];
        int i = 0;
        for (Col col : getCols()) {
            cols[i] = col.getName();
            i++;
        }

        return cols;
    }

    /**
     * Returns all the columns that are assignable from the provided column class
     *
     * @param type the type
     * @return cols of type
     */
    public Set<Col> getColsOfType(Class<? extends Col> type) {
        return this.cols.values()
                .stream()
                .filter( i -> ( type.isAssignableFrom(i.getClass()) ) )
                .collect(Collectors.toSet());
    }

    /**
     * Returns the total size of rows in the current table.
     *
     * @return The number of rows
     */
    public int getRowCount(){
        return this.rows.size();
    }

    /**
     * Gets col.
     *
     * @param any the any
     * @return the col
     */
    @Deprecated
    public Col getCol(Object any) {
        if (any instanceof Integer) {
            return getColByIndex((Integer) any);
        } else if (any instanceof ColRef ) {
            return getColByRef((ColRef)any);
        } else {
            return getColByName((String)any);
        }
    }

    /**
     * Gets col by ref.
     *
     * @param ref the ref
     * @return the col by ref
     */
    public Col getColByRef(ColRef ref) {
        if (ref.isInt()) return getColByIndex(ref.asInt());

        return getColByName(ref.asStr());
    }

    /**
     * Returns the column of the provided name if available, otherwise null
     *
     * @param name The column name
     * @return The column if found, otherwise null
     */
    public Col getColByName(String name){
        return this.cols.get(name);
    }

    /**
     * Returns the column of the provided index if available, otherwise null
     *
     * @param index The column index
     * @return The column if found, otherwise null
     */
    public Col getColByIndex(Integer index) {
        return this.cols.get(index);
    }

    /**
     * Returns the column index of the provided name if available, otherwise null
     *
     * @param name The column name
     * @return The column index if found, otherwise null
     */
    public Integer getColIndex(String name){
        final Col col = getColByName(name);
        return (col == null)? null : col.getIndex();
    }

    /**
     * Returns the column index of the provided a ColRef if available, otherwise null
     *
     * @param colRef The column ref
     * @return The column index if found, otherwise null
     */
    public Integer getColIndex(ColRef colRef){
        final Col col = getColByRef(colRef);
        return (col == null)? null : col.getIndex();
    }

    /**
     * Returns true if the current table has an existing column with the same name, otherwise false
     *
     * @param name the name
     * @return Returns true if the current table as a column with the same name, otherwise false
     */
    public boolean hasCol(String name) {
        return this.getColByName(name) != null;
    }

    /**
     * Has col boolean.
     *
     * @param index the index
     * @return the boolean
     */
    public boolean hasCol(int index) {
        return this.cols.hasIndex(index);
    }

    /**
     * Attempts to add the provided col to the table and returns the index if the col was added, otherwise null.
     * The col will not be added if one of the same name already exists.
     * <p>
     * If the current table contains rows a null value will be appended to each row
     *
     * @param col the col
     * @return the newly added col index
     */
    public synchronized Integer addCol(Col col) {
        if (!acceptCol(col)) return null;

        if (!isEmpty()) {
            addCol(col, (row) -> row.append(null) );
        } else {
            this.cols.add(col);
        }
        return getColIndex(col.getName());
    }

    /**
     * Add cols.
     *
     * @param cols the cols
     */
    public void addCols(Collection<Col> cols) {
        cols.forEach( (c) -> this.addCol(c) );
    }

    /**
     * Add col.
     *
     * @param colFunc the col func
     */
    public void addCol(ColFunc colFunc) {
        colFunc.eval(this);
    }

    private boolean acceptCol(Col col) {
        if (col == null) return false;
        if (hasCol(col.getName())) return false;

        return true;
    }

    /**
     * Add col.
     *
     * @param col the col
     * @param map the map
     */
    public synchronized void addCol(Col col, Function<Row, Row> map) {
        if (!acceptCol(col) || map == null) return;

        this.cols.add(col);

        final Stream<Row> stream = (allowParallelProcessing())?
                this.rows.parallelStream() :
                this.rows.stream();

        this.rows = stream.map(map)
                .collect(Collectors.toCollection(() -> new ArrayList<>(getRowCount())));

        indexRows();

    }

    /**
     * Returns if the current row count of the table allows parallel processing. See [[TableSettings]]
     *
     * @return Returns true if the current table has enough rows to allow parallel processing, otherwise false.
     */
    public boolean allowParallelProcessing() {
        return (settings.getAllowParallelProcessingAfterRow() < 0)? false :
                getRowCount() > settings.getAllowParallelProcessingAfterRow();
    }

    /**
     * Clears all existing cols and adds the provided cols to the table.
     *
     * @param cols A list of cols
     */
    public void setCols(Collection<Col> cols){
        this.cols.clear();
        this.cols.addAll(cols);
    }

    /**
     * Adds the provided row to the current rows and returns true if added, otherwise false
     *
     * @param row The row to add to the current table
     * @return Return true if added, otherwise false
     */
    public synchronized boolean addRow(Row row){
        if (acceptRow(row)) {
            final boolean added = this.rows.add(row);
            if (added) {
                row.setIndex(rows.size()-1);
            }
            return added;
        }
        return false;
    }

    /**
     * Add row row setter.
     *
     * @return the row setter
     */
    public RowSetter addRow() {
        return new RowSetter(this);
    }

    /**
     * Creates a row from the provided values and returns true if added, otherwise false
     *
     * @param vals the vals
     * @return boolean
     */
    public boolean addRow(Object... vals) {
        return addRow(Row.from(vals));
    }

    /**
     * Adds all the provided rows to the current table and returns the total row size
     *
     * @param rows Rows to add
     * @return count of all rows
     */
    public int addRows(Collection<Row> rows){
        rows.forEach( (i) -> this.addRow(i));
        return rows.size();
    }


    /**
     * Evaluates if the provided row can be added to the current table and returns true if so, otherwise false.
     * @param row The row to evaluate
     * @return Return true if the row can be added, otherwise false.
     */
    private boolean acceptRow(Row row){
        if (row.size() != getColCount()) {
            throw new IllegalArgumentException(String.format("col mismatch error, expected [%s] values but was [%s].",
                    getColCount(), row.size()));
        }
        return true;
    }

    /**
     * Returns the value of the provide row and column index
     *
     * @param <T> The return type
     * @param row The row index of the value
     * @param col The column index of the value
     * @return Returns the value at the provided row and column index
     * @throws IndexOutOfBoundsException
     */
    public <T> T get(int row, int col){
        return this.rows.get(row).get(col);
    }

    /**
     * Sets the value at the provided row and column with the provided value and returns
     *
     * @param row the row
     * @param col the col
     * @param val the val
     * @return object
     */
    public Object set(int row, int col, Object val) {
        return getRow(row).set(col, val);
    }

    /**
     * Returns the row for the given row index, otherwise null
     *
     * @param row The row index to return
     * @return Returns the row for the provided index, otherwise null
     */
    public Row getRow(int row) {
        return this.rows.get(row);
    }

    /**
     * Returns the rows that are equal to the provided ref and object.
     *
     * @param ref a valid column reference
     * @param eq  a value ot compare equals to
     * @return rows eq
     */
    public List<Row> getRowsEq(ColRef ref, Comparable eq) {
        final int index = getColIndex(ref);
        final Stream<Row> stream = (allowParallelProcessing())?
                this.rows.parallelStream() :
                this.rows.stream();

        return stream
                .filter( (r) -> r.get(index).equals(eq) )
                .collect(Collectors.toCollection(() -> new ArrayList<>(getRowCount())));
    }


    /**
     * Evaluates if the current table has a value at the provided row and col indexes by evaluating row and column
     * sizes. If the indexes are within each row and column sizes it will return true, otherwise false.
     *
     * @param row The row index to evaluate
     * @param col The column index to evaluate
     * @return Returns true if the row and column indexes are within the current tables range, otherwise false
     */
    public boolean has(int row, int col) {
        if (row < 0 || col < 0) {
            return false;
        }

        if (row >= getRowCount() && col >= getColCount()){
            return false;
        }

        return true;
    }

    /**
     * Returns all the values in the provided column index
     *
     * @param <T> The return type
     * @param col A valid column index in the current table
     * @return A list of typed values in the provided col
     * @throws IndexOutOfBoundsException
     */
    public <T> List<T> getVals(int col){

        final List results = this.rows.stream()
                .map(i -> i.get(col))
                .collect(Collectors.toCollection(() -> new ArrayList<>(getRowCount())));

        return results;
    }

    /**
     * Returns all values in the provided column
     *
     * @param <T> the type parameter
     * @param col the col
     * @return vals
     */
    public <T> List<T> getVals(Col col) {
        return getVals(col.getIndex());
    }

    /**
     * Gets vals.
     *
     * @param <T>  the type parameter
     * @param name the name
     * @return the vals
     */
    public <T> List<T> getVals(String name) {
        return getVals(getColIndex(name));
    }

    /**
     * Gets vals.
     *
     * @param <T> the type parameter
     * @param ref the ref
     * @return the vals
     */
    public <T> List<T> getVals(ColRef ref) {
        return (ref.isInt())?
                getVals(ref.asInt()) :
                getVals(ref.asStr());
    }

    /**
     * Returns all the values in the provided column index as a Double
     *
     * @param col A valid column index in the current table
     * @return A list of Double values in the provided col
     * @throws IndexOutOfBoundsException
     */
    public List<Double> valuesToDoubles(int col){
        return getVals(col)
                .stream()
                .mapToDouble(x -> ((Number)x).doubleValue())
                .boxed()
                .collect(Collectors.toCollection(() -> new ArrayList<>(getRowCount())));
    }

    /**
     * Returns all the values in the provided column index as a Integer
     *
     * @param col A valid column index in the current table
     * @return A list of Integer values in the provided col
     * @throws IndexOutOfBoundsException
     */
    public List<Integer> valuesToInt(int col){
        return getVals(col)
                .stream()
                .mapToInt(x -> ((Number)x).intValue())
                .boxed()
                .collect(Collectors.toCollection(() -> new ArrayList<>(getRowCount())));
    }

    /**
     * Returns all the values in the provided column index as a Long
     *
     * @param col A valid column index in the current table
     * @return A list of Long values in the provided col
     * @throws IndexOutOfBoundsException
     */
    public List<Long> valuesToLong(int col){
        return getVals(col)
                .stream()
                .mapToLong(x -> ((Number)x).longValue())
                .boxed()
                .collect(Collectors.toCollection(() -> new ArrayList<>(getRowCount())));
    }

    /**
     * Row iterator iterator.
     *
     * @return the iterator
     */
    @Deprecated
    public Iterator<Row> rowIterator() {
        return rows.iterator();
    }

    /**
     * Row spliterator spliterator.
     *
     * @return the spliterator
     */
    public Spliterator<Row> rowSpliterator() {
        return (this.allowParallelProcessing())?
                this.getRows().parallelStream().spliterator() :
                this.getRows().stream().spliterator();
    }

    /**
     * Row spliterator spliterator.
     *
     * @param filter the filter
     * @return the spliterator
     */
    public Spliterator<Row> rowSpliterator(Predicate<Row> filter) {
        return (this.allowParallelProcessing())?
                this.getRows().parallelStream().filter(filter).spliterator() :
                this.getRows().stream().filter(filter).spliterator();
    }


    /**
     * Column iterator iterator.
     *
     * @return the iterator
     */
    public Iterator<Col> columnIterator() {
        return cols.values().iterator();
    }

    /**
     * Eval t.
     *
     * @param <T>  the type parameter
     * @param func the func
     * @return the t
     */
    public <T> T eval(Func<T> func) {
        return func.eval(this);
    }

    /**
     * Returns an unmodifiable list of current rows
     *
     * @return list
     */
    public List<Row> getRows(){
        return Collections.unmodifiableList(this.rows);
    }

    /**
     * Returns an unmodifiable list of current cols
     *
     * @return collection
     */
    public Collection<Col> getCols(){
        return Collections.unmodifiableCollection(this.cols.values());
    }


    /**
     * Returns the first row in the current table or null if the table is empty.
     *
     * @return Returns the first row or null if the table is empty.
     */
    public Row getFirstRow(){
        if (isEmpty()) {
            return null;
        }
        return this.rows.get(0);
    }

    /**
     * Returns the last row in the current table or null if the table is empty.
     *
     * @return Returns the last row or null if the table is empty.
     */
    public Row getLastRow(){
        if (isEmpty()) {
            return null;
        }
        return this.rows.get(getRowCount()-1);
    }

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty(){
        return this.rows.isEmpty();
    }

    /**
     * Creates a deep copy of the current Table
     * @return Returns a new table instance
     */
    @Override
    public Table copy() {
        final Table copy = new Table(
                (this.settings == null)?
                        TableSettings.DEFAULT_SETTINGS :
                        this.settings.copy()
        );

        for (Col col : cols.values()) {
            copy.addCol((Col) col.copy());
        }

        final RowCopier rowCopier = new RowCopier();
        rowCopier.copyRows(this, copy);
        indexRows();

        return copy;
    }

    /**
     * Sort table.
     *
     * @param col the column name to sort.
     * @return new table with simple comparator sorting
     */
    public Table sort(final String col) {
        return sort(getColIndex(col));
    }

    /**
     * Sortr table.
     *
     * @param col the column name to sort.
     * @return new table with simple comparator sorting in reverse
     */
    public Table sortr(final String col) {
        return sortr(getColIndex(col));
    }

    /**
     * Sort table.
     *
     * @param col the column index to sort.
     * @return new table with simple comparator sorting.
     */
    public Table sort(final int col) {
        return sort(new ColComparator(col, false));
    }

    /**
     * Sort table.
     *
     * @param comparator IColComparator
     * @return new table sorted by provided comparator
     */
    public Table sort(ColComparator comparator) {
        final Table copy = copy();
        Collections.sort(copy.rows, comparator);
        return copy;
    }

    /**
     * Sortr table.
     *
     * @param col the column index to sort.
     * @return new table with simple comparator sorting in reverse.
     */
    public Table sortr(final int col) {
        return sort(new ColComparator(col, true));
    }

    private synchronized void indexRows() {
        this.rows.sort(Comparator.comparingInt(Row::getIndex));
        for (int i = 0; i < getRowCount(); i++) {
            rows.get(i).setIndex(i);
        }

    }

    /**
     * Removes the values and the column from the table, re-indexing of columns is called subsequently.
     *
     * @param col a valid column
     * @return Returns true if the column was removed an no longer exists in the current table
     */
    public boolean removeCol(Col col) {
        return removeCol(col.getName());
    }

    /**
     * Rename col boolean.
     *
     * @param from the from
     * @param to   the to
     * @return the boolean
     */
    public boolean renameCol(String from, String to) {
        return this.cols.renameCol(from, to);
    }

    /**
     * Removes the values and the column from the table and returns the removed
     * values.
     *
     * @param col the col
     * @return the removed values
     */
    public synchronized boolean removeCol(String col) {
        if (!hasCol(col)) {
            return false;
        }

        if (isEmpty()) {
            this.cols.remove(getColByName(col));
            return !this.hasCol(col);
        }

        final Col column = getColByName(col);

        final Spliterator<Row> spliterator = (allowParallelProcessing()) ?
                this.rows.parallelStream().spliterator() :
                this.rows.stream().spliterator();

        spliterator.forEachRemaining( r -> {
            r.values().remove(column.getIndex());
        });

        this.cols.remove(column);

        return !this.hasCol(col);

    }

    /**
     * Write to.
     *
     * @param writer the writer
     */
    public void writeTo(Writer writer) {
        writer.write(this);
    }

    /**
     * Increases the capacity of the rows
     *
     * @param minCapacity the min capacity
     */
    public void ensureRowCapacity(int minCapacity) {
        this.rows.ensureCapacity(minCapacity);
    }


}
