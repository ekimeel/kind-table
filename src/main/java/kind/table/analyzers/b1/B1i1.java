package kind.table.analyzers.b1;

import kind.table.analyzers.Interpreter;
import kind.table.analyzers.Session;

public final class B1i1 implements Interpreter {

    public static final String NAME = "B1.i1";
    public static final String[] REQUIREMENTS = new String[]{"B1"};
    private Session session;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String[] getRequirements() {
        return REQUIREMENTS;
    }

    @Override
    public void setSession(Session session) {

    }

    @Override
    public Session getSession() {
        return null;
    }


    @Override
    public Object call() throws Exception {
        return null;
    }
}
