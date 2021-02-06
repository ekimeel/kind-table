package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;

/**
 * The interface Func.
 *
 * @param <T> the type parameter
 */
public interface Func<T> {

     /**
      * Eval t.
      *
      * @param table the table
      * @return the t
      */
     T eval(Table table);

}
