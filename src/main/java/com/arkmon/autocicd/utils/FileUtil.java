package com.arkmon.autocicd.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * author     : X.J
 * date       : 2021/3/15-4:10 下午
 * description:
 */
@Slf4j
public class FileUtil {
    /**
     * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
     *
     * @param res            原字符串
     * @param filePath 文件路径
     * @return 成功标记
     */
    public static boolean string2File(String res, String filePath) {
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {
            File distFile = new File(filePath);
//            if (!distFile.getParentFile().exists()) distFile.getParentFile().mkdirs();
            bufferedReader = new BufferedReader(new StringReader(res));
            bufferedWriter = new BufferedWriter(new FileWriter(distFile));
            char buf[] = new char[1024];         //字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) {
            log.error("{}", e);
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    log.error("{}", e);
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean string2ZipFile(String filePath, String zipPath) {
        /**
         *
         * @author admin
         * @date 5:31 下午 2021/3/15
         * @param filePath 需要被压缩的文件名
         * @param zipPath 压缩的文件名
         * @return boolean 成功true，失败false
         */
        try {
            File file = new File(filePath);
            File zipFile = new File(zipPath);
            InputStream input = new FileInputStream(file);
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
            zipOut.putNextEntry(new ZipEntry(file.getName()));
            int temp = 0;
            while((temp = input.read()) != -1){
                zipOut.write(temp);
            }
            input.close();
            zipOut.close();
        } catch (Exception e) {
            log.error("{}", e);
            return false;
        }
        return true;
    }
}
