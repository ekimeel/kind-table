package kind.table.analyzers;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public abstract class Interpreter implements Callable {

    private final String name;
    private Session session;
    private final Map<String, Object> data = new HashMap<>();


    protected Interpreter(String name) {
        this.name = name;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getData() {
        return data;
    }


    protected String format(String template, Map<String, String> params) {
        String message = template;
        for (Map.Entry<String, String> e : params.entrySet()) {
            message = message.replace(e.getKey(), e.getValue());
        }
        return message;
    }
}
