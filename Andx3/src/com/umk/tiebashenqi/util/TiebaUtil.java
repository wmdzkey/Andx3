package com.umk.tiebashenqi.util;

import android.util.Log;
import com.google.gson.internal.LinkedTreeMap;
import com.lidroid.xutils.util.LogUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Winnid
 * @title获得贴吧页面地址和图片
 * @version:1.0
 * @since：13-12-14
 */
public class TiebaUtil {

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
                if(link.attr("href") != null && !link.attr("href").equals("") ){
                    hs.put(link.text(), link.attr("abs:href") );
                    //LogUtils.e("开始解析:" + link.text() + " - " + link.attr("abs:href"));
                }
            }
        }
        catch (Exception e)
        {
            hs.put("Exception", e.getMessage());
            e.printStackTrace();
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

        HashSet<String> set = new HashSet<String>();
        try {
            //读取第一页，查看一共有多少页
            detailsPage = detailsPage + "&see_lz=1";
            Document doc = Jsoup.connect(detailsPage).get();
            LogUtils.e("开始解析:" + detailsPage);

            //获得本帖子共有多少页
            Elements totalPage = doc.select("div.h");
            LogUtils.e("解析子内容:" + totalPage.text());
            int pageNumber = 1;//默认就有一页
            for (Element src : totalPage) {
                if(src.text().contains("第") && src.text().contains("/")  && src.text().contains("页")) {
                    LogUtils.e("解析子src内容:" + src.text());
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

            for(int i = 0; i < pageNumber; i++){
                LogUtils.e(detailsPage + "&pn=" + i + "0");//手机的页码下标从00开始
                doc = Jsoup.connect(detailsPage + "&pn=" + i + "0").get();
                Elements image = doc.select("a[href^=http://m.tiebaimg.com/]");
                int j = 0;
                for (Element src : image) {
                    LogUtils.e("解析子图片内容:" + src.html());
                    set.add(src.attr("href"));
                    j++;
                }
                LogUtils.e("解析子图片内容:第" + i + "页总数：" + j);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            LogUtils.e("图片宽度和高度转换失败");
        }
        return set;

    }

}