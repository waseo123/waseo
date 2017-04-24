package com.waseo.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by qiuhuan on 2017/4/13.
 * 对象序列化，反序列化工具类
 */
public class SerializeUtils {

    private static Logger logger = Logger.getLogger(SerializeUtils.class);

    /**
     * 对象序列化
     *
     * @param obj 序列化对象
     * @return
     */
    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("对象序列化异常。" + e.getMessage());
        } finally {
            try {
                if (oos != null)
                    oos.close();
                if (baos != null)
                    baos.close();
            } catch (Exception e) {
                logger.error("对象序列化,关闭流异常。" + e.getMessage());
            }
        }
        return bytes;
    }

    /**
     * 对象反序列化
     *
     * @param bytes
     * @return
     */
    public static Object unSerialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        Object obj = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            obj = ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("对象反序列化异常。" + e.getMessage());
        } finally {
            try {
                if (ois != null)
                    ois.close();
                if (bais != null)
                    bais.close();
            } catch (Exception e) {
                logger.error("对象反序列化,关闭流异常。" + e.getMessage());
            }
        }
        return obj;
    }
}
