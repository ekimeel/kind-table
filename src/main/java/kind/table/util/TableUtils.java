package kind.table.util;

import kind.table.Table;

public class TableUtils {

    /**
     * Provides a safe name for a column that is required to be added to a table.
     * @param table
     * @param name
     * @return A safe name column name to added to the table.
     */
    public static String enumerateColName(Table table, String name) {
        if (!table.hasCol(name)) {
            return name;
        } else {
            String nName = name;
            int i = 1;
            while(table.hasCol(nName)) {
                nName = name + "_" + (i++);
            }
            return nName;
        }
    }
}
