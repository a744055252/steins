package com.guanhuan.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 用于对文件的处理
 *
 * @author liguanhuan_a@163.com
 * @since 2018-03-09 14:30
 **/
public class FileUtil {

    public static void main(String[] args){
        File source = new File("C://1.rar");
        File target = new File("C://1_copy.rar");
        FileUtil.copyFile(source, target);
//        FileUtils.copyFile();
//        Files.copy();
    }

    public static void copyFile(File source,File target){
        FileInputStream in = null;
        FileOutputStream out = null;
        if(!source.exists() || !source.isFile()){
            throw new IllegalArgumentException("文件不存在");
        }

        if(target.exists()){
            target.delete();
        }

        try{
            target.createNewFile();
            in = new FileInputStream(source);
            out = new FileOutputStream(target);
            byte[] buffer = new byte[1024*10];
            int size = 0;
            while((size = in.read(buffer))!=-1){
                out.write(buffer,0,size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
