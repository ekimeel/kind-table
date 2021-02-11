package kind.table.analyzers;

import kind.table.Table;

public class Interpetation {

    private Session session;
    private String id;
    private String name;
    private String message;
    private String params;
    private String status;
    private int code;



    public Interpetation(Session session) {
        this.session = session;
    }

    Interpetation withId(String id) {
        this.id = id;
        return this;
    }

    Interpetation withName(String name) {
        this.name = name;
        return this;
    }

    Interpetation withMessage(String message) {
        this.message = message;
        return this;
    }

    Interpetation withParams(String params) {
        this.params = params;
        return this;
    }

    Interpetation withStatus(ResponseStatus status) {
        this.status = status.name();
        this.code = status.getId();
        return this;
    }

    void submit() {
        final Table table = this.session.getResponse().getTable();

        table.addRow()
                .set("id", this.id)
                .set("name", this.name)
                .set("message", this.message)
                .set("params", this.params)
                .set("time", null)
                .set("code", this.code)
                .set("status", this.status)
                .add();

    }

}
