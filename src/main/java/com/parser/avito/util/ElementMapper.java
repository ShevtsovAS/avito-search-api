package com.parser.avito.util;

import com.parser.avito.model.Item;
import lombok.experimental.UtilityClass;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@UtilityClass
public class ElementMapper {

    public static Item getItem(Element element) {
        return Item.builder()
                .link(getElementLink(element))
                .title(getElementTitle(element))
                .price(getElementPrice(element))
                .dateTime(getElementDateTime(element))
                .category(getElementCategory(element))
                .location(getElementLocation(element))
                .build();
    }

    private static String getElementLocation(Element element) {
        Elements data = element.getElementsByClass("data")
                .first()
                .getElementsByTag("p");
        return data.size() >= 2 ? data.get(1).text() : "";
    }

    private static String getElementCategory(Element element) {
        return element.getElementsByClass("data")
                .first()
                .getElementsByTag("p")
                .first()
                .text();
    }

    private static String getElementDateTime(Element element) {
        return element.getElementsByClass("js-item-date").attr("data-absolute-date").trim();
    }

    private static String getElementPrice(Element element) {
        return element.getElementsByClass("price").text();
    }

    private static String getElementTitle(Element element) {
        return element.getElementsByClass("item-description-title-link").text();
    }

    private static String getElementLink(Element element) {
        return element.getElementsByClass("item-description-title-link").attr("abs:href");
    }


}
