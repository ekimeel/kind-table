package kind.table;

import java.io.*;

public class Serialization {

    public static Serializable clone(final Serializable obj) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(out);
        oout.writeObject(obj);
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));
        return Serializable.class.cast(in.readObject());
    }
}
