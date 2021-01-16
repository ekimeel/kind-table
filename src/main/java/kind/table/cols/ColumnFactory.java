package kind.table.cols;

public final class ColumnFactory {

    /**
     * Attempts to return a matching column type for the provided value
     *
     * <table>
     *     <thead>
     *         <tr>
     *             <td>Given</td>
     *             <td>Returned</td>
     *         </tr>
     *     </thead>
     *     <tbody>
     *         <tr>
     *             <td>java.lang.Double</td>
     *             <td>kind.table.cols.DoubleColumn</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Integer</td>
     *             <td>kind.table.cols.IntegerColumn</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Boolean</td>
     *             <td>kind.table.cols.BooleanColumn</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Long</td>
     *             <td>kind.table.cols.LongColumn</td>
     *         </tr>
     *     </tbody>
     * </table>
     * @param val
     * @return
     */
    public static Column from(String name, Object val) {
        if (val == null) { return null; }
        else if (val instanceof Double) { return DoubleColumn.of(name); }
        else if (val instanceof Integer) { return IntegerColumn.of(name); }
        else if (val instanceof Long) { return LongColumn.of(name); }
        else if (val instanceof Boolean) { return BooleanColumn.of(name); }
        else if (val instanceof String) { return StringColumn.of(name); }
        else if (val instanceof java.util.Date) { return DateColumn.of(name); }
        else if (val instanceof java.time.Instant) { return InstantColumn.of(name); }
        else { return null; }
    }
}
