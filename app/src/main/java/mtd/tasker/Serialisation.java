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
        ObjectOutputStream out = null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            return bos.toByteArray();
        } finally {
            if (out != null) out.close();
        }
    }

    /**
 * deserialize an object to cast it
 *
 * @param bytes 
 * @return Object the abstract Object of the bytes given
 * @throws throw new RuntimeException(ex); 
 */
    public static Object deserialize(byte[] bytes) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;

        try {
            in = new ObjectInputStream(bis);
            return (Object) in.readObject();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (in != null) in.close();
        }
    }
}
