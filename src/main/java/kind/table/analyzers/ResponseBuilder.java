package kind.table.analyzers;

import kind.table.Table;
import kind.table.TableBuilder;

import java.util.UUID;

public class ResponseBuilder {

    private Table table;
    private String id;

    public ResponseBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public ResponseBuilder withRandomId() {
        this.id = UUID.randomUUID().toString();
        return this;
    }

    public ResponseBuilder withDefaultTable() {
        this.table = new TableBuilder()
                .withStrCol("id")
                .withStrCol("name")
                .withStrCol("message")
                .withStrCol("params")
                .withLngCol("time")
                .withIntCol("code")
                .withStrCol("status")
                .build();

        return this;
    }

    public Response build() {
        final Response response = new Response();
        response.setTable(this.table);
        response.setErr(null);
        return response;
    }


}
