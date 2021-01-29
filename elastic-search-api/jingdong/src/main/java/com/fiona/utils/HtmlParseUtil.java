package com.fiona.utils;

import com.fiona.pojo.Item;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



@Component
public class HtmlParseUtil {

//
//    public static void main(String[] args) throws IOException {
//        HtmlParseUtil htmlParseUtil = new HtmlParseUtil();
//        htmlParseUtil.parseJson("hello").forEach(System.out::println);
//
//    }

        public List<Item> parseJson(String keyword) throws IOException {

            String url = "https://search.jd.com/Search?keyword=java";
            Document document = Jsoup.parse(new URL("https://search.jd.com/Search?keyword="+ keyword), 50000);
            Element elementById = document.getElementById("J_goodsList");

            //System.out.println(elementById);
            Elements elements = elementById.getElementsByTag("li");

            //封装成pojo，准备List
            ArrayList<Item> goods = new ArrayList<>();

            for (Element el : elements) {
               String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
               String price = el.getElementsByClass("p-price").eq(0).text();
               String title = el.getElementsByClass("p-name").eq(0).text();
               //封装一下pojo
                Item item = new Item();
                item.setImg(img);
                item.setPrice(price);
                item.setTitle(title);
                goods.add(item);
            }

            return goods;
        }

    }



