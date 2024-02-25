package com.toyproject.globalMarket.libs;

import com.toyproject.globalMarket.VO.ProductRegisterVO;
import com.toyproject.globalMarket.configuration.platform.APIGithub;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlManager {

    public String addTitleandMetaDescription (String html){
        Document doc = Jsoup.parse(html);
        String[] lines = doc.html().split("\n");
        StringBuilder cleanedHtml = new StringBuilder();
        for (String line : lines) {
            if (line.toLowerCase().startsWith("<head>") || line.toLowerCase().startsWith(" <head>")) {
                cleanedHtml.append("<head>\n");
                cleanedHtml.append("<title>");
                cleanedHtml.append("temporary title");
                cleanedHtml.append("</title>\n");
                cleanedHtml.append("<meta name=\"description\" content=");
                cleanedHtml.append("\"temporary description\">\n");
                cleanedHtml.append("</head>\n");
            } else {
                cleanedHtml.append(line).append("\n");
            }
        }
        return cleanedHtml.toString();
    }
    public String addTitleandMetaDescription(ProductRegisterVO productSource, Document doc) {
        String[] lines = doc.html().split("\n");
        StringBuilder cleanedHtml = new StringBuilder();
        for (String line : lines) {
                if (line.toLowerCase().startsWith("<head>") || line.toLowerCase().startsWith(" <head>")) {
                    cleanedHtml.append("<head>\n");
                    cleanedHtml.append("<title>");
                    cleanedHtml.append(productSource.getPageTitle());
                    cleanedHtml.append("</title>\n");
                    cleanedHtml.append("<meta name=\"description\" content=");
                    cleanedHtml.append("\"" + productSource.getMetaDescription() + "\">\n");
                    cleanedHtml.append("</head>\n");
                } else {
                    cleanedHtml.append(line).append("\n");
                }
            }
        return cleanedHtml.toString();
    }
    private String removeHtmlBodyTags(Document doc){
        String[] lines = doc.body().outerHtml().split("\n");
        StringBuilder cleanedHtml = new StringBuilder();
        for (String line : lines) {
            if (!line.trim().matches("^<\\/?body[^>]*>$")) {
                cleanedHtml.append(line).append("\n");
            }
        }
        return cleanedHtml.toString();
    }
    public String cleanHtml (String html){
        Document doc = Jsoup.parse(html);
        List<Element> divList = doc.select("div")
                .stream()
                .filter(div -> div.attr("class").contains("detailmodule") || div.attr("class").contains("detail-desc-decorate") || div.attr("class").contains("ai-description") || div.attr("class").contains("origin-part"))
                .collect(Collectors.toList());
        for (Element div : divList){
            String name = div.attr("class");
            if (name.contains("detailmodule")){
                name = name.replace("detailmodule", "information");
            }
            else if (name.contains("detail-desc")){
                name = name.replace("detail-desc", "detail-information");
            }
            else if (name.contains("ai")){
                name = "description";
            }
            else if (name.contains("origin")){
                name = "box-sizing";
            }
            div.attr("class", name);
        }
        doc.select("script").remove();
        doc.select("link").remove();
        String ret = removeHtmlBodyTags(doc);
        return ret;
    }
    public void replaceDetailContnetImages(ProductRegisterVO productSource) {
        Document doc = Jsoup.parse(productSource.getDetailContent());
        List<Element> imgList = doc.select("img")
                .stream()
                .filter(img -> !img.attr("src").contains("raw.githubusercontent"))
                .collect(Collectors.toList());


        APIGithub github = new APIGithub();

        int i = 1;
        for (Element img : imgList){
            System.out.println(img.attr("src"));
            if (img.hasAttr("style")) {
                img.removeAttr("style");
            }
            if (img.hasAttr("width")) {
                img.removeAttr("width");
            }
            if (img.hasAttr("height")) {
                img.removeAttr("height");
            }

            if (img.hasAttr("slate-data-type")) {
                img.removeAttr("slate-data-type");
            }
            String githubURL = github.URL + productSource.getDBId() + github.pageURL + "image" + String.valueOf(i++) + ".jpg";
            img.attr("src", githubURL);
            String alt = img.attr("alt");
             img.attr("alt", alt == "" ? "상품 이미지" : alt);
        }
        //String ret = removeHtmlBodyTags(doc);
        //String ret = addTitleandMetaDescription(productSource, doc);
        String ret = doc.toString();
        productSource.setDetailContent(ret);
    }


}
