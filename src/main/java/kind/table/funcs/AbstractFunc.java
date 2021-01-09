package kind.table.funcs;

import kind.table.Table;

public abstract class AbstractFunc<T> implements Func<T> {

    protected Table table;

    protected Table getTable(){
        return table;
    }

    protected void setTable(Table table){
        this.table = table;
    }




}
