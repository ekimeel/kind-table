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
     *             <td>kind.table.cols.DblColumn</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Integer</td>
     *             <td>kind.table.cols.IntColumn</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Boolean</td>
     *             <td>kind.table.cols.BoolColumn</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Long</td>
     *             <td>kind.table.cols.LngColumn</td>
     *         </tr>
     *     </tbody>
     * </table>
     * @param val
     * @return
     */
    public static Column from(String name, Object val) {
        if (val == null) { return null; }
        else if (val instanceof Double) { return DblColumn.of(name); }
        else if (val instanceof Integer) { return IntColumn.of(name); }
        else if (val instanceof Long) { return LngColumn.of(name); }
        else if (val instanceof Boolean) { return BoolColumn.of(name); }
        else if (val instanceof String) { return StrColumn.of(name); }
        else if (val instanceof java.util.Date) { return DateColumn.of(name); }
        else if (val instanceof java.time.Instant) { return InstantColumn.of(name); }
        else { return null; }
    }
}
