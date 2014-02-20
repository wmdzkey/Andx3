package com.palm.epalm.modules.system.api;

import android.content.res.AXmlResourceParser;
import android.util.TypedValue;
import com.palm.epalm.base.repository.MatchType;
import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.modules.system.entity.ApkVersion;
import com.palm.epalm.modules.system.service.ApkVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xmlpull.v1.XmlPullParser;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Apk版本
 */
@Controller
@RequestMapping("/api/apk")
public class ApkVersionApi {

    @Autowired
    private ApkVersionService apkVersionService;


    @ResponseBody
    @RequestMapping(value="version_get", method = RequestMethod.GET)
    public String version_check_get(HttpServletRequest request, String vcode, String hashcode){
        String path = request.getServletContext().getRealPath("/apk/umkphoto.apk");
        String []info=unZip(path); //0//1//2
        if(vcode!= null && !vcode.equals("") && vcode.equals(info[1])){
            return "-1";
        }else{
            List<QueryFilter> filters = new ArrayList<QueryFilter>();
            filters.add(new QueryFilter("versionCode", info[1], MatchType.LE));
            filters.add(new QueryFilter("versionCode", vcode, MatchType.GT));
            List<ApkVersion> apkVersions = apkVersionService.findAll(filters);
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<apkVersions.size();i++){
                sb.append((i+1) + "、" +apkVersions.get(i).getVersionInfo()+"<br>");
            }
            String patchFileName = "apkp"+vcode+"-"+info[1];
            String patchPath =request.getServletContext().getRealPath("/patches/"+patchFileName+".patch");
            File file = new File(patchPath);
            File nFile = new File(path);
            long tlen = nFile.length();
            long len = file.length();
            return "{\"versionCode\":\""+info[1]+"\"," +
                    "\"patchSize\":\""+len+""+"\"," +
                    "\"newFeatures\":\""+sb.toString()+"\"," +
                    "\"patchFileName\":\""+patchFileName+"\"," +
                    "\"totalSize\":\""+tlen+"\"}";
        }
    }

    @ResponseBody
    @RequestMapping("v")
    public String v(String vcode){
            List<QueryFilter> filters = new ArrayList<QueryFilter>();
            filters.add(new QueryFilter("versionCode", "5", MatchType.LE));
            filters.add(new QueryFilter("versionCode", vcode, MatchType.GT));
            List<ApkVersion> apkVersions = apkVersionService.findAll(filters);
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<apkVersions.size();i++){
                sb.append((i+1) + "、" +apkVersions.get(i).getVersionInfo()+"<br>");
            }
            return sb.toString();
        }
       @ResponseBody
       @RequestMapping(value="version",method = RequestMethod.POST)
       public String version_check(HttpServletRequest request,String vcode,String hashcode){
        String path = request.getServletContext().getRealPath("/apk/umkphoto.apk");
        String []info=unZip(path); //0//1//2
        if(vcode!= null && !vcode.equals("") && vcode.equals(info[1])){
            return "-1";
        }else{
            List<QueryFilter> filters = new ArrayList<QueryFilter>();
            filters.add(new QueryFilter("versionCode", info[1], MatchType.LE));
            filters.add(new QueryFilter("versionCode", vcode, MatchType.GT));
            List<ApkVersion> apkVersions = apkVersionService.findAll(filters);
            StringBuilder sb = new StringBuilder();

            String patchFileName = "patch"+vcode+"-"+info[1];
            String patchPath =request.getServletContext().getRealPath("/patches/"+patchFileName+".patch");
            File file = new File(patchPath);
            File nFile = new File(path);
            long tlen = nFile.length();
            long len = file.length();
            double save = tlen - len;

            sb.append("将下载"+String.format("%.2f",((double)len/1024))+"KB,为您节省"+String.format("%.2f",save/(1024*1024))+"M流量。<br>");
            for(int i=0;i<apkVersions.size();i++){
                sb.append((i+1) + "、" +apkVersions.get(i).getVersionInfo()+"<br>");
            }
            return "{\"versionCode\":\""+info[1]+"\"," +
                    "\"patchSize\":\""+len+""+"\"," +
                    "\"newFeatures\":\""+sb.toString()+"\"," +
                    "\"patchFileName\":\""+patchFileName+"\"," +
                    "\"totalSize\":\""+tlen+"\"}";
        }
    }
    @ResponseBody
    @RequestMapping("file")
    public ResponseEntity<Resource> download(HttpServletRequest request,String pf){
        String patchPath =request.getServletContext().getRealPath("/patches/"+pf+".patch");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", pf+".patch");
        return new ResponseEntity<Resource>(new FileSystemResource(patchPath),headers, HttpStatus.CREATED);
    }
    /**
     * 解压 zip 文件(apk可以当成一个zip文件)，注意不能解压 rar 文件哦，只能解压 zip 文件 解压 rar 文件 会出现
     * java.io.IOException: Negative seek offset 异常 create date:2009- 6- 9
     * author:Administrator
     *
     * @param apkUrl
     *            zip 文件，注意要是正宗的 zip 文件哦，不能是把 rar 的直接改为 zip 这样会出现
     *            java.io.IOException: Negative seek offset 异常
     * @throws java.io.IOException
     */
    public static String[] unZip(String apkUrl) {
        // [0]:版本号;[1]版本 [2]包名
        String[] st = new String[3];
        byte b[] = new byte[1024];
        int length;
        ZipFile zipFile;
        try {
            zipFile = new ZipFile(new File(apkUrl));
            Enumeration<?> enumeration = zipFile.entries();
            ZipEntry zipEntry = null;
            while (enumeration.hasMoreElements()) {
                zipEntry = (ZipEntry) enumeration.nextElement();
                if (zipEntry.isDirectory()) {

                } else {
                    if ("AndroidManifest.xml".equals(zipEntry.getName())) {
                        try {
                            AXmlResourceParser parser = new AXmlResourceParser();
                            parser.open(zipFile.getInputStream(zipEntry));
                            while (true) {
                                int type = parser.next();
                                if (type == XmlPullParser.END_DOCUMENT) {
                                    break;
                                }
                                switch (type) {
                                    case XmlPullParser.START_TAG: {
                                        for (int i = 0; i != parser.getAttributeCount(); ++i) {
                                            if ("versionName".equals(parser.getAttributeName(i))) {
                                                st[0] = getAttributeValue(parser, i);
                                            }else if("versionCode".equals(parser.getAttributeName(i))){
                                                st[1] =getAttributeValue(parser,i);
                                            }
                                            else if ("package".equals(parser.getAttributeName(i))) {
                                                st[2] = getAttributeValue(parser, i);
                                            }
                                        }
                                    }
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
        }
        return st;
    }

    private static String getAttributeValue(AXmlResourceParser parser, int index) {
        int type = parser.getAttributeValueType(index);
        int data = parser.getAttributeValueData(index);
        if (type == TypedValue.TYPE_STRING) {
            return parser.getAttributeValue(index);
        }
        if (type == TypedValue.TYPE_ATTRIBUTE) {
            return String.format("?%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_REFERENCE) {
            return String.format("@%s%08X", getPackage(data), data);
        }
        if (type == TypedValue.TYPE_FLOAT) {
            return String.valueOf(Float.intBitsToFloat(data));
        }
        if (type == TypedValue.TYPE_INT_HEX) {
            return String.format("0x%08X", data);
        }
        if (type == TypedValue.TYPE_INT_BOOLEAN) {
            return data != 0 ? "true" : "false";
        }
        if (type == TypedValue.TYPE_DIMENSION) {
            return Float.toString(complexToFloat(data)) + DIMENSION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type == TypedValue.TYPE_FRACTION) {
            return Float.toString(complexToFloat(data)) + FRACTION_UNITS[data & TypedValue.COMPLEX_UNIT_MASK];
        }
        if (type >= TypedValue.TYPE_FIRST_COLOR_INT && type <= TypedValue.TYPE_LAST_COLOR_INT) {
            return String.format("#%08X", data);
        }
        if (type >= TypedValue.TYPE_FIRST_INT && type <= TypedValue.TYPE_LAST_INT) {
            return String.valueOf(data);
        }
        return String.format("<0x%X, type 0x%02X>", data, type);
    }

    private static String getPackage(int id) {
        if (id >>> 24 == 1) {
            return "android:";
        }
        return "";
    }

    // ///////////////////////////////// ILLEGAL STUFF, DONT LOOK :)
    public static float complexToFloat(int complex) {
        return (float) (complex & 0xFFFFFF00) * RADIX_MULTS[(complex >> 4) & 3];
    }

    private static final float RADIX_MULTS[] = { 0.00390625F, 3.051758E-005F, 1.192093E-007F, 4.656613E-010F };
    private static final String DIMENSION_UNITS[] = { "px", "dip", "sp", "pt", "in", "mm", "", "" };
    private static final String FRACTION_UNITS[] = { "%", "%p", "", "", "", "", "", "" };
}
