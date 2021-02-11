package kind.table.analyzers;

import kind.table.Table;
import kind.table.TableBuilder;

import java.util.concurrent.Future;

public class ExecutionPlanner {


    public Future<Response> submit(Request request) {

        Response response = new ResponseBuilder()
                .withRandomId()
                .withDefaultTable()
                .build();

        return null;
    }

}
