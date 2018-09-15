package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.parser.ast.projection.*;
import com.leonardoz.select_html.parser.ast.ProjectionType;
import org.jsoup.nodes.Element;

import java.util.List;
import java.util.stream.Collectors;


public class Extractors {

    interface Extractor {
        void project(Element element, ExtractorListener listener);
    }

    private static final Extractor allExtractor = (element, listener) -> {
        Extractors.tagExtractor.project(element, listener);
        Extractors.attributesExtractor.project(element, listener);
        Extractors.idExtractor.project(element, listener);
        Extractors.classExtractor.project(element, listener);
        Extractors.htmlExtractor.project(element, listener);
        Extractors.textExtractor.project(element, listener);
    };

    private static final Extractor tagExtractor = (element, listener) -> {
        Tag tag = new Tag(element.tag().toString());
        listener.onTagCreated(tag);
    };

    private static final Extractor attributesExtractor = (element, listener) -> {
        List<TagAttribute> attrs = element.attributes()
                .asList()
                .stream()
                .map(attr -> new TagAttribute(attr.getKey(), attr.getValue()))
                .collect(Collectors.toList());
        TagAttributes tagAttributes = new TagAttributes(attrs);
        listener.onTagAttributesCreated(tagAttributes);

    };

    private static final Extractor idExtractor = (element, listener) -> {
        TagId tagId = new TagId(element.id());
        listener.onTagIdCreated(tagId);
    };

    private static final Extractor classExtractor =
            (element, listener) -> {
                List<TagClass> classes = element.classNames()
                        .stream()
                        .map(className -> new TagClass(className))
                        .collect(Collectors.toList());
                TagClasses tagClasses = new TagClasses(classes);
                listener.onTagClassesCreated(tagClasses);
            };

    private static final Extractor htmlExtractor = (element, listener) -> {
        TagHTML tagHTML = new TagHTML(element.html());
        listener.onTagHTMLCreated(tagHTML);
    };

    private static final Extractor textExtractor = (element, listener) -> {
        TagText tagText = new TagText(element.text());
        listener.onTagTextCreated(tagText);
    };

    public static Extractor factory(ProjectionType type) {
        switch (type) {
            case ALL:
                return allExtractor;
            case TAG:
                return tagExtractor;
            case ATTRIBUTES:
                return attributesExtractor;
            case ATTRIBUTE:
                return attributesExtractor;
            case CLASS:
                return classExtractor;
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

