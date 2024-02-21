package com.toyproject.globalMarket.libs;

import org.jsoup.nodes.Element;

public class HtmlBuilder {

    public Element startElement(String name) {
        return new Element(name);
    }

    public Element addAttribute(Element element, String name, String value) {
        element.attr(name, value);
        return element;
    }

    public Element addText(Element element, String text) {
        element.text(text);
        return element;
    }

    // ... more methods for other element operations

}