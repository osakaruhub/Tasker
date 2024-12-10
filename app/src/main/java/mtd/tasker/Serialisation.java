package mtd.tasker;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectInput;
import java.io.ObjectOutputStream;
import java.lang.RuntimeException;
import java.io.IOException;

public class Serialisation {
/**
 * serialize an object for sending.
 *
 * @param object the Object to be serialized
 * @return byte[] the Serialized version 
 * @throws IOException
 */
public static byte[] serialize(Object object) throws IOException {
    try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
         ObjectOutputStream out = new ObjectOutputStream(bos)) {
        out.writeObject(object);
        return bos.toByteArray();
    } 
}

/**
 * deserialize an object to cast it
 *
 * @param bytes 
 * @return Object the abstract Object of the bytes given
 * @throws throw new RuntimeException(ex); 
 */
public static Object deserialize(byte[] bytes) {
    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

    try (ObjectInput in = new ObjectInputStream(bis)) {
        return in.readObject();
    } catch (Exception ex) {
        throw new RuntimeException(ex);
    }
}
}
