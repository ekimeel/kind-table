package kind.table.cols;

/**
 * The type Col factory.
 */
public final class ColFactory {

    /**
     * <table>
     *     <caption>Attempts to return a matching column type for the provided valu</caption>
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
     *         <tr>
     *             <td>java.lang.String</td>
     *             <td>kind.table.cols.StrCol</td>
     *         </tr>
     *         <tr>
     *             <td>java.time.Instant</td>
     *             <td>kind.table.cols.TsCol</td>
     *         </tr>
     *     </tbody>
     * </table>
     *
     * @param name the name
     * @param val  the val
     * @return col
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

    /**
     * From col.
     *
     * @param name the name
     * @param type the type
     * @return the col
     */
    public static Col from(String name, Class<? extends Col> type) {
        if (type == null) { return null; }
        else if (DblCol.class.equals(type)) { return DblCol.of(name); }
        else if (IntCol.class.equals(type)) { return IntCol.of(name); }
        else if (LngCol.class.equals(type)) { return LngCol.of(name); }
        else if (BoolCol.class.equals(type)) { return BoolCol.of(name); }
        else if (StrCol.class.equals(type)) { return StrCol.of(name); }
        else if (DateCol.class.equals(type)) { return DateCol.of(name); }
        else if (TsCol.class.equals(type)) { return TsCol.of(name); }
        else { return null; }
    }
}
