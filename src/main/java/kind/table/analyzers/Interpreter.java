package kind.table.analyzers;

import java.util.concurrent.Callable;

public interface Interpreter extends Callable {

    String[] getRequirements();

    String getName();

    void setSession(Session session);

    Session getSession();

}
