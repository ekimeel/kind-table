package kind.table.cols;

public final class ColFactory {

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
     *             <td>kind.table.cols.DblCol</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Integer</td>
     *             <td>kind.table.cols.IntCol</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Boolean</td>
     *             <td>kind.table.cols.BoolCol</td>
     *         </tr>
     *         <tr>
     *             <td>java.lang.Long</td>
     *             <td>kind.table.cols.LngCol</td>
     *         </tr>
     *     </tbody>
     * </table>
     * @param val
     * @return
     */
    public static Col from(String name, Object val) {
        if (val == null) { return null; }
        else if (val instanceof Double) { return DblCol.of(name); }
        else if (val instanceof Integer) { return IntCol.of(name); }
        else if (val instanceof Long) { return LngCol.of(name); }
        else if (val instanceof Boolean) { return BoolCol.of(name); }
        else if (val instanceof String) { return StrCol.of(name); }
        else if (val instanceof java.util.Date) { return DateCol.of(name); }
        else if (val instanceof java.time.Instant) { return TsCol.of(name); }
        else { return null; }
    }
}
