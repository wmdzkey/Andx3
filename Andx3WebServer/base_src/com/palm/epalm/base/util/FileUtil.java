package com.palm.epalm.base.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 * Date: 13-3-19
 * Time: 下午2:39
 *
 * @author desire
 * @version 1.0
 */
public class FileUtil {

    public static String getFileType(Part p) {
        String name = p.getHeader("content-disposition");
        String fileNameTmp = name.substring(name.indexOf("filename=") + 10);
        String type = fileNameTmp.substring(fileNameTmp.indexOf(".") + 1, fileNameTmp.indexOf("\""));
        return type;
    }

    public static String getFileName(Part p) {
        String name = p.getHeader("content-disposition");
        String fileNameTmp = name.substring(name.indexOf("filename=") + 10);
        String fileName = fileNameTmp.substring(0, fileNameTmp.indexOf("\""));
        return fileName;
    }

}
