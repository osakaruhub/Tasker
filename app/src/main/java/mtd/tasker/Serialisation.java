package mtd.tasker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.lang.RuntimeException;
import java.io.IOException;

public class Serialisation {
public static byte[] serialize(Object object) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
         ObjectOutputStream out = new ObjectOutputStream(bos)) {
        out.writeObject(object);
        return bos.toByteArray();
    } 
}

public static Object deserialize(byte[] bytes) {
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

    try (ObjectInput in = new ObjectInputStream(bis)) {
        return in.readObject();
    } catch (Exception ex) {
        throw new RuntimeException(ex);
    }
}
}
