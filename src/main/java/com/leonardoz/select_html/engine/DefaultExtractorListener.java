package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.parser.ast.projection.*;

public class DefaultExtractorListener implements ExtractorListener {

    private DefaultExtractorListener() { }
    private TagResult actualTagResult;

    @Override
    public void onTagCreated(Tag tag) {
        actualTagResult = tag;
    }

    @Override
    public void onTagAttributesCreated(TagAttributes tagAttributes) {
        actualTagResult = tagAttributes;
    }

    @Override
    public void onTagClassesCreated(TagClasses tagClasses) {
        actualTagResult = tagClasses;
    }

    @Override
    public void onTagIdCreated(TagId tagId) {
        actualTagResult = tagId;
    }

    @Override
    public void onTagHTMLCreated(TagHTML tagHTML) {
        actualTagResult = tagHTML;
    }

    @Override
    public void onTagTextCreated(TagText tagText) {
        actualTagResult = tagText;
    }

    public TagResult getActualTagResult() {
        return actualTagResult;
    }

    public static DefaultExtractorListener instance() {
        return new DefaultExtractorListener();
    }
}
