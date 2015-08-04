package org.leon.dev.util.redis;

import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by LeonWong on 15/6/21.
 */
public class SerializeUtil {
    public static Logger log = Logger.getLogger(SerializeUtil.class);
    /**
     * 序列化，将对象存储为二进制数据，这里返回字节数组
     *
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        try {
            // 序列化
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            byte[] bytes = baos.toByteArray();
            return bytes;
        } catch (Exception e) {
            log.error("序列化对象出错，详见日志");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反序列化，将二进制数据转化为对象
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            log.error("反序列化对象出错，详见日志");
            e.printStackTrace();
        }
        return null;
    }
}
