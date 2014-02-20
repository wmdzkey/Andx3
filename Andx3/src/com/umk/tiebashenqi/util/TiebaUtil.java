package com.umk.tiebashenqi.util;

import android.util.Log;
import com.google.gson.internal.LinkedTreeMap;
import com.lidroid.xutils.util.LogUtils;
import com.umk.andx3.lib.util.Base64CoderUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Winnid
 * @title获得贴吧页面地址和图片
 * @version:1.0
 * @since：13-12-14
 */
public class TiebaUtil {


    public final static String TIEBA_NAME = "TiebaName";
    public final static String TIEBA_LOGO = "TiebaLogo";
    public final static String TIEBA_EXCEPTION = "Exception";


    /**
     * 获得贴吧主页所有帖子的页面地址
     * @return ArrayList<String 地址链接>
     * @throws IOException
     */
    public static Map<String, String> getHomePageName(String homePage) {

        Map<String, String> tiebaMap = new HashMap<String, String>();
        try
        {
            Document doc = Jsoup.connect(homePage).userAgent("Mozilla").get();//模拟PC-IE
            //Document doc = Jsoup.connect(homePage).userAgent("Mozilla/5.0").get();//模拟Android
            //Document doc = Jsoup.connect(homePage).get();
            String homePageName = doc.title();
            tiebaMap.put(TIEBA_NAME, homePageName.substring(0, homePageName.length() - 6));

            //获得贴吧Logo
            Elements logoSpan = doc.select("span.common_forum_img");
            for(Element link : logoSpan){
                if(logoSpan.attr("style") != null && !link.attr("style").equals("") ){
                    String logoImgUrl = link.attr("style");
                    tiebaMap.put(TIEBA_LOGO, logoImgUrl.substring(logoImgUrl.indexOf("(")+1, logoImgUrl.length()-1) );
                    LogUtils.e("开始解析:" + link.text() + " - " + tiebaMap.get(TIEBA_LOGO));
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return tiebaMap;
    }

    /**
     * 获得贴吧主页所有帖子的页面地址
     * @return ArrayList<String 地址链接>
     * @throws IOException
     */
    public static LinkedTreeMap<String,String> getHomePageHashMap(String homePage) {

        LinkedTreeMap<String,String> hs = new LinkedTreeMap<String,String>();

        try
        {
            LogUtils.e("开始解析:" + homePage);
            Document doc = Jsoup.connect(homePage).get();
            Elements links = doc.select("a[href*=/m?kz=]");//手机版贴吧帖子开头链接
            //注意这里是Elements不是Element。同理getElementById返回Element，getElementsByClass返回时Elements
            for(Element link : links){
                if(link.attr("href") != null && !link.attr("href").equals("")) {
                    String url = link.attr("abs:href");
                    String title = link.text();
                    url = url.substring(0, url.indexOf("/mo/q---")+3) + url.substring(url.indexOf("/m?kz="), url.length());
                    title = title.substring(title.indexOf(".")+1, title.length());
                    hs.put(url, title.substring(title.indexOf(".")+1, title.length()));
                    LogUtils.e("开始解析:" + link.text() + " - " + url);
                }
            }
        }
        catch (Exception e)
        {
            hs.put("Exception", e.getMessage());
        }

        return hs;
    }

    /**
     * 获得明细页内所有的图片
     * @param detailsPage
     * @return
     * @throws IOException
     */
    public static HashSet<String> getDetailsPageImageList(String detailsPage) {

        HashSet<String> set = null;
        try {
            //读取第一页，查看一共有多少页
            detailsPage = detailsPage + "&see_lz=1";
            Document doc = Jsoup.connect(detailsPage).get();
            LogUtils.e("开始解析:" + detailsPage);

            //获得本帖子共有多少页
            Elements totalPage = doc.select("div.h");
            int pageNumber = 1;//默认就有一页
            for (Element src : totalPage) {
                set = new HashSet<String>();
                if(src.text().contains("第") && src.text().contains("/")  && src.text().contains("页")) {
                    try{
                        int startIndex = src.text().lastIndexOf("/");
                        int endIndex = src.text().lastIndexOf("页");
                        String pageText = src.text().substring(startIndex + 1, endIndex);
                        pageNumber = Integer.parseInt(pageText);
                    }catch(Exception e){
                        LogUtils.e("总页码数转换失败");
                    }
                }
            }
            LogUtils.e("解析子图片页数: " + pageNumber);

            for(int i = 0; i < pageNumber; i++){
                LogUtils.e(detailsPage + "&pn=" + i + "0");//手机的页码下标从00开始
                doc = Jsoup.connect(detailsPage + "&pn=" + i + "0").get();
                Elements image = doc.select("a[href^=http://m.tiebaimg.com/]");
                int j = 0;
                for (Element src : image) {
                    LogUtils.e("解析子图片内容:" + src.attr("href"));
                    set.add(src.attr("href"));
                    j++;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            LogUtils.e("图片宽度和高度转换失败");
        }
        return set;

    }

    public static String getOriginalImageUrl(String imageUrl) {
        if (imageUrl != null && imageUrl.startsWith("http://m.tiebaimg.com/")) {
            imageUrl = imageUrl.substring(imageUrl.indexOf("&src=") + 5);
            LogUtils.e("imageUrl : " + imageUrl);
            LogUtils.e("imageUrl : " + Base64CoderUtil.urlDecode(imageUrl));
            return Base64CoderUtil.urlDecode(imageUrl);
        }
        return "";
    }

}