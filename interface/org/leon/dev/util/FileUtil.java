package org.leon.dev.util;

import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by LeonWong on 15/7/20.
 */
public class FileUtil {

    static Logger log = Logger.getLogger(FileUtil.class);
    /**
     * 将文件转为字节数组
     * @param file
     * @return
     */
    public static byte[] getBytesFromFile(File file) throws NullPointerException {

        byte[] ret = null;
        FileInputStream in = null;
        ByteArrayOutputStream out = null;
        try{
            if (file == null) {
                log.error("文件为空");
                return null;
            }
            in = new FileInputStream(file);
            out = new ByteArrayOutputStream(4096);
            byte[] b = new byte[4096];
            int n;
            while ((n = in.read(b)) != -1) {
                out.write(b, 0, n);
            }
            ret = out.toByteArray();
        }catch (IOException e){
            log.error("IO异常");
            e.printStackTrace();
        }finally {
            try{
                in.close();
                out.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return ret;
    }


    /**
     *
     * @param bytes
     * @param outputFile
     * @return
     */
    public static File getFileFromBytes(byte[] bytes, String outputFile) {
        File ret = null;
        BufferedOutputStream stream = null;
        try {
            ret = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(ret);
            stream = new BufferedOutputStream(fstream);
            stream.write(bytes);
        } catch (IOException e) {
            log.error("IO异常");
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    log.error("IO异常");
                    e.printStackTrace();
                }
            }
        }
        return ret;
    }
}
