package kind.table;

import kind.table.cols.ColRef;
import kind.table.cols.Column;
import kind.table.funcs.Func;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Table implements Copyable<Table>{

    private final Columns columns;
    private List<Row> rows;
    private final TableSettings settings;

    public Table() {
        this.columns = new Columns();
        this.rows = new ArrayList<>();
        this.settings = TableSettings.DEFAULT_SETTINGS;
    }

    public Table(TableSettings settings){
        this.columns = new Columns();
        this.rows = new ArrayList<>();
        this.settings = settings;
    }

    public Table(Collection<Column> columns) {
        this.columns = new Columns(columns);
        this.rows = new ArrayList<>();
        this.settings = TableSettings.DEFAULT_SETTINGS;
    }

    public Table(Collection<Column> columns, TableSettings settings) {
        this.columns = new Columns(columns);
        this.rows = new ArrayList<>();
        this.settings = settings;
    }

    public Table(Collection<Column> columns, List<Row> rows) {
        this.columns = new Columns(columns);
        this.rows = rows;
        this.settings = TableSettings.DEFAULT_SETTINGS;
    }

    public Table(Collection<Column> columns, List<Row> rows, TableSettings settings) {
        this.columns = new Columns(columns);
        this.rows = rows;
        this.settings = settings;
    }

    /**
     * Returns the current table settings
     * @return
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
     * @return The number of columns
     */
    public int getColumnCount(){
        return this.columns.size();
    }

    /**
     * Returns an array of column names
     * @return An array of column names
     */
    public String[] getColumnNames() {
        final String[] cols = new String[columns.size()];
        int i = 0;
        for (Column col : getCols()) {
            cols[i] = col.getName();
            i++;
        }

        return cols;
    }

    /**
     * Returns the total size of rows in the current table.
     * @return The number of rows
     */
    public int getRowCount(){
        return this.rows.size();
    }

    public Column getCol(Object any) {
        if (any instanceof Integer) {
            return getColByIndex((Integer) any);
        } else {
            return getColByName((String)any);
        }
    }

    public Column getColByRef(ColRef ref) {
        return getCol(ref.getRef());
    }

    /**
     * Returns the column of the provided name if available, otherwise null
     * @param name The column name
     * @return The column if found, otherwise null
     */
    public Column getColByName(String name){

        final Optional<Column> result = getCols().stream().filter(t ->
                t.getName().equals(name)
        ).findFirst();

        return (!result.isPresent())? null : result.get();
    }

    /**
     * Returns the column of the provided index if available, otherwise null
     * @param index The column index
     * @return The column if found, otherwise null
     */
    public Column getColByIndex(Integer index) {
        return this.columns.get(index);
    }

    /**
     * Returns the column index of the provided name if available, otherwise null
     * @param name The column name
     * @return The column name if found, otherwise null
     */
    public Integer getColIndex(String name){
        final Column column = getColByName(name);
        return (column == null)? null : column.getIndex();
    }

    /**
     * Returns true if the current table has an existing column with the same name, otherwise false
     * @param name
     * @return Returns true if the current table as a column with the same name, otherwise false
     */
    public boolean hasCol(String name) {
        return this.getColByName(name) != null;
    }

    public boolean hasCol(int index) {
        return this.columns.hasIndex(index);
    }

    /**
     * Attempts to add the provided column to the table and returns true if the column was added, otherwise false. The
     * column will not be added if one of the same name already exists.
     *
     * If the current table contains rows a null value will be appended to each row
     *
     * @param column
     * @return
     */
    public synchronized boolean addCol(Column column) {
        if (!acceptCol(column)) return false;

        if (!isEmpty()) {
            addCol(column, (row) -> row.append(null) );
        } else {
            this.columns.add(column);
        }
        return hasCol(column.getName());
    }

    private boolean acceptCol(Column column) {
        if (column == null) return false;
        if (hasCol(column.getName())) return false;

        return true;
    }

    public synchronized void addCol(Column column, Function<Row, Row> map) {
        if (!acceptCol(column) || map == null) return;

        column.setIndex(this.columns.size() - 1);
        this.columns.add(column);

        final Stream<Row> stream = (allowParallelProcessing())?
                this.rows.parallelStream() :
                this.rows.stream();

        this.rows = stream.map(map).collect(Collectors.toList());

        if (allowParallelProcessing()) {
            indexRows();
        }
    }

    /**
     * Returns if the current row count of the table allows parallel processing. See [[TableSettings]]
     *
     * @return Returns true if the current table has enough rows to allow parallel processing, otherwise false.
     */
    public boolean allowParallelProcessing() {
        return getRowCount() > settings.getAllowParallelProcessingAfterRow();
    }

    /**
     * Clears all existing columns and adds the provided columns to the table.
     * @param columns A list of columns
     * @return Returns true if columns are added, otherwise false.
     */
    public void setColumns(Collection<Column> columns){
        this.columns.clear();
        this.columns.addAll(columns);
    }

    /**
     * Adds the provided row to the current rows and returns true if added, otherwise false
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
     * Creates a row from the provided values and returns true if added, otherwise false
     *
     * @param vals
     * @return
     */
    public boolean addRow(Object... vals) {
        return addRow(new Row(vals));
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
        if (row.size() != getColumnCount()) {
            return false;
        }
        return true;
    }

    /**
     * Returns the value of the provide row and column index
     * @param row The row index of the value
     * @param col The column index of the value
     * @param <T> The return type
     * @throws IndexOutOfBoundsException
     * @return Returns the value at the provided row and column index
     */
    public <T> T get(int row, int col){
        return this.rows.get(row).get(col);
    }

    /**
     * Sets the value at the provided row and column with the provided value and returns
     * @param row
     * @param col
     * @param val
     * @return
     */
    public Object set(int row, int col, Object val) {
        return getRow(row).set(col, val);
    }

    /**
     * Returns the row for the given row index, otherwise null
     * @param row The row index to return
     * @return Returns the row for the provided index, otherwise null
     */
    public Row getRow(int row) {
        return this.rows.get(row);
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

        if (row >= getRowCount() && col >= getColumnCount()){
            return false;
        }

        return true;
    }

    /**
     * Returns all the values in the provided column index
     * @param col A valid column index in the current table
     * @param <T> The return type
     * @throws IndexOutOfBoundsException
     * @return A list of typed values in the provided col
     */
    public <T> List<T> getVals(int col){

        final List results = this.rows.stream()
                .map(i -> i.get(col))
                .collect(Collectors.toCollection(ArrayList::new));

        return results;
    }

    /**
     * Returns all values in the provided column
     * @param col
     * @param <T>
     * @return
     */
    public <T> List<T> getVals(Column col) {
        return getVals(col.getIndex());
    }

    public <T> List<T> getVals(String name) {
        return getVals(getColIndex(name));
    }

    public <T> List<T> getVals(ColRef ref) {
        return (ref.isInt())?
                getVals(ref.asInt()) :
                getVals(ref.asStr());
    }

    /**
     * Returns all the values in the provided column index as a Double
     * @param col A valid column index in the current table
     * @throws IndexOutOfBoundsException
     * @return A list of Double values in the provided col
     */
    public List<Double> valuesToDoubles(int col){
        return getVals(col)
                .stream()
                .mapToDouble(x -> ((Number)x).doubleValue())
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Returns all the values in the provided column index as a Integer
     * @param col A valid column index in the current table
     * @throws IndexOutOfBoundsException
     * @return A list of Integer values in the provided col
     */
    public List<Integer> valuesToInt(int col){
        return getVals(col)
                .stream()
                .mapToInt(x -> ((Number)x).intValue())
                .boxed()
                .collect(Collectors.toList());
    }

    /**
     * Returns all the values in the provided column index as a Long
     * @param col A valid column index in the current table
     * @throws IndexOutOfBoundsException
     * @return A list of Long values in the provided col
     */
    public List<Long> valuesToLong(int col){
        return getVals(col)
                .stream()
                .mapToLong(x -> ((Number)x).longValue())
                .boxed()
                .collect(Collectors.toList());
    }

    public Iterator<Row> rowIterator() {
        return rows.iterator();
    }

    public Iterator<Column> columnIterator() {
        return columns.values().iterator();
    }

    public <T> T eval(Func<T> func) {
        return func.eval(this);
    }

    /**
     * Returns an unmodifiable list of current rows
     * @return
     */
    public List<Row> getRows(){
        return Collections.unmodifiableList(this.rows);
    }

    /**
     * Returns an unmodifiable list of current cols
     * @return
     */
    public Collection<Column> getCols(){
        return Collections.unmodifiableCollection(this.columns.values());
    }



    /**
     * Returns the first row in the current table or null if the table is empty.
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
     * @return Returns the last row or null if the table is empty.
     */
    public Row getLastRow(){
        if (isEmpty()) {
            return null;
        }
        return this.rows.get(getRowCount()-1);
    }

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

        for (Column col : columns.values()) {
            copy.addCol((Column) col.copy());
        }

        final RowCopier rowCopier = new RowCopier();
        rowCopier.copyRows(this, copy);
        indexRows();

        return copy;
    }

    /**
     * @param col the column name to sort.
     * @return new table with simple comparator sorting
     */
    public Table sort(final String col) {
        return sort(getColIndex(col));
    }

    /**
     * @param col the column name to sort.
     * @return new table with simple comparator sorting in reverse
     */
    public Table sortr(final String col) {
        return sortr(getColIndex(col));
    }

    /**
     * @param col the column index to sort.
     * @return new table with simple comparator sorting.
     */
    public Table sort(final int col) {
        return sort(new ColumnComparator(col, false));
    }

    /**
     *
     * @param comparator IColumnComparator
     * @return new table sorted by provided comparator
     */
    public Table sort(ColumnComparator comparator) {
        final Table copy = copy();
        Collections.sort(copy.rows, comparator);
        return copy;
    }

    /**
     * @param col the column index to sort.
     * @return new table with simple comparator sorting in reverse.
     */
    public Table sortr(final int col) {
        return sort(new ColumnComparator(col, true));
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
     *
     */
    public boolean removeCol(Column col) {
        return removeCol(col.getName());
    }

    /**
     * Removes the values and the column from the table and returns the removed
     * values.
     *
     * @param col
     * @return the removed values
     */
    public synchronized boolean removeCol(String col) {
        if (!hasCol(col)) {
            return false;
        }

        if (isEmpty()) {
            this.columns.remove(getColByName(col));
            return !this.hasCol(col);
        }

        final Column column = getColByName(col);


        //todo: evaluate if parallel processing for removal is worth it (allowParallelProcessing)
        final Iterator<Row> iterator = rowIterator();
        while (iterator.hasNext()) {
            final Row row = iterator.next();
            row.values().remove(column.getIndex());
        }

        this.columns.remove(column);

        return !this.hasCol(col);

    }

    public void print(PrintStream ps) {
        StringBuilder sb = new StringBuilder();

        for (String col : getColumnNames()) {
            sb.append(col).append("\t");
        }
        ps.println(sb.toString());

        final Iterator<Row> iterator = rowIterator();
        while (iterator.hasNext()) {

            sb = new StringBuilder();
            final Row row = iterator.next();

            for (Object data : row.values()) {
                sb.append(data).append("\t");
            }
            ps.println(sb.toString());

        }

    }
}
