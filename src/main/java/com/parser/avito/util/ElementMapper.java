package com.parser.avito.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parser.avito.config.ParserProperties;
import com.parser.avito.model.Item;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Slf4j
@UtilityClass
public class ElementMapper {

    private static final String P_TAG = "p";

    private static ObjectMapper mapper = new ObjectMapper();
    private static ParserProperties parserProperties;

    public static void setParserProperties(ParserProperties parserProperties) {
        ElementMapper.parserProperties = parserProperties;
    }

    public static Item getItem(Element element) {
        return Item.builder()
                .link(getElementLink(element))
                .title(getElementTitle(element))
                .price(getElementPrice(element))
                .dateTime(getElementDateTime(element))
                .category(getElementCategory(element))
                .location(getElementLocation(element))
                .isUpped(checkElementIsUpped(element))
                .build();
    }

    private static boolean checkElementIsUpped(Element element) {
        String dataConfig = element
                .getElementsByClass(parserProperties.getItemVipListClass())
                .attr(parserProperties.getItemVipListAttr());
        try {
            return !StringUtils.isEmpty(dataConfig) && mapper.readTree(dataConfig).get(parserProperties.getItemIsUppedProp()).asBoolean();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private static String getElementLocation(Element element) {
        Elements data = element.getElementsByClass(parserProperties.getItemDataClass())
                .first()
                .getElementsByTag(P_TAG);
        return data.size() >= 2 ? data.get(1).text() : EMPTY;
    }

    private static String getElementCategory(Element element) {
        return element.getElementsByClass(parserProperties.getItemDataClass())
                .first()
                .getElementsByTag(P_TAG)
                .first()
                .text();
    }

    private static String getElementDateTime(Element element) {
        return element
                .getElementsByClass(parserProperties.getItemDateClass())
                .attr(parserProperties.getItemDateAttr())
                .trim();
    }

    private static String getElementPrice(Element element) {
        return element
                .getElementsByClass(parserProperties.getItemPriceClass())
                .text();
    }

    private static String getElementTitle(Element element) {
        return element
                .getElementsByClass(parserProperties.getItemLinkClass())
                .text();
    }

    private static String getElementLink(Element element) {
        return element
                .getElementsByClass(parserProperties.getItemLinkClass())
                .attr(parserProperties.getItemLinkAttr());
    }


}
