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
    private static final String ABS_HREF_ATTR = "abs:href";

    private static ObjectMapper mapper;
    private static ParserProperties parserProperties;

    public static void setParserProperties(ParserProperties parserProperties) {
        ElementMapper.parserProperties = parserProperties;
    }

    public static void setMapper(ObjectMapper mapper) {
        ElementMapper.mapper = mapper;
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
                .getElementsByClass(parserProperties.getVipListClass())
                .attr(parserProperties.getVipListAttr());
        try {
            return !StringUtils.isEmpty(dataConfig) && mapper.readTree(dataConfig).get(parserProperties.getIsUppedProp()).asBoolean();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    private static String getElementLocation(Element element) {
        Elements data = element.getElementsByClass(parserProperties.getDataClass())
                .first()
                .getElementsByTag(P_TAG);
        return data.size() >= 2 ? data.get(1).text() : EMPTY;
    }

    private static String getElementCategory(Element element) {
        return element.getElementsByClass(parserProperties.getDataClass())
                .first()
                .getElementsByTag(P_TAG)
                .first()
                .text();
    }

    private static String getElementDateTime(Element element) {
        return element
                .getElementsByClass(parserProperties.getDateClass())
                .attr(parserProperties.getDateAttr())
                .trim();
    }

    private static String getElementPrice(Element element) {
        return element
                .getElementsByClass(parserProperties.getPriceClass())
                .text();
    }

    private static String getElementTitle(Element element) {
        return element
                .getElementsByClass(parserProperties.getLinkClass())
                .text();
    }

    private static String getElementLink(Element element) {
        return element
                .getElementsByClass(parserProperties.getLinkClass())
                .attr(ABS_HREF_ATTR);
    }


}
