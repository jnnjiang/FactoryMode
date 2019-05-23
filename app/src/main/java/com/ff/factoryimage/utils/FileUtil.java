package com.ff.factoryimage.utils;

import java.io.File;

public class FileUtil {
    public static void createFileIfNotExsist(String path){
        File file = new File(path);
        if(file == null || !file.exists()){
            file.mkdirs();
        }
    }

    public static boolean pathExist(String path){
        File file = new File(path);
        if(file == null || !file.exists()){
            return false;
        }else {
            return true;
        }
    }
}
