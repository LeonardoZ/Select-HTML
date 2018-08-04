package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.model.projection.*;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;


public class Extractors {

    interface Extractor {
        TagResult project(Element element, ExtractorListener visitor);
    }

    private static final Extractor tagExtractor = (element, visitor) -> {
        Tag tag = new Tag(element.tag().toString());
        visitor.onTagCreated(tag);
        return tag;
    };

    private static final Extractor attributesExtractor = (element, visitor) -> {
        List<TagAttribute> attrs = element.attributes()
                .asList()
                .stream()
                .map(attr -> new TagAttribute(attr.getKey(), attr.getValue()))
                .collect(Collectors.toList());
        TagAttributes tagAttributes = new TagAttributes(attrs);
        visitor.onTagAttributesCreated(tagAttributes);
        return tagAttributes;

    };

    private static final Extractor idExtractor = (element, visitor) -> {
        TagId tagId = new TagId(element.id());
        visitor.onTagIdCreated(tagId);
        return tagId;
    };

    private static final Extractor classExtractor =
            (element, visitor) -> {
                List<TagClass> classes = element.classNames()
                        .stream()
                        .map(className -> new TagClass(className))
                        .collect(Collectors.toList());
                TagClasses tagClasses = new TagClasses(classes);
                visitor.onTagClassesCreated(tagClasses);
                return tagClasses;
            };

    private static final Extractor htmlExtractor = (element, visitor) -> {
        TagHTML tagHTML = new TagHTML(element.html());
        visitor.onTagHTMLCreated(tagHTML);
        return tagHTML;
    };

    private static final Extractor textExtractor = (element, visitor) -> {
        TagText tagText = new TagText(element.text());
        visitor.onTagTextCreated(tagText);
        return tagText;
    };

    public static Extractor factory(ProjectionType type) {
        switch (type) {
            case TAG:
                return tagExtractor;
            case ATTRIBUTES:
                return attributesExtractor;
            case CLASSES:
                return classExtractor;
            case ID:
                return idExtractor;
            case HTML:
                return htmlExtractor;
            case TEXT:
                return textExtractor;
            default:
                return tagExtractor;
        }
    }
}

