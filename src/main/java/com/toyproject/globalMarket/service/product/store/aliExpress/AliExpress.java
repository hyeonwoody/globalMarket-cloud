package com.toyproject.globalMarket.service.product.store.aliExpress;

import com.toyproject.globalMarket.VO.ProductVO;
import com.toyproject.globalMarket.service.product.store.Store;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class AliExpress implements Store {
    @Override
    public void parseHtml(ProductVO productVO, String html) {
        Document doc = Jsoup.parse(html);
        Elements metaTags = doc.select("head meta");

        for (Element metaTag : metaTags){
            String property = metaTag.attr("property");
            String content = metaTag.attr("content");
            if ("og:title".equals(property)){
                System.out.println ("Content: " + content);
                productVO.originProduct.setName(content);
                break;
            }
        }
    }

    @Override
    public void getProductInfo(ProductVO productVO, String url){
        String html = getHtml(url);
        parseHtml(productVO, html);
    }

}
