package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.parser.ast.projection.*;

public class ProjectionResultExtractorListener implements ExtractorListener {

    private ProjectionResult result;

    public ProjectionResultExtractorListener() {
        result = new ProjectionResult();
    }

    @Override
    public void onTagCreated(Tag tag) {
        result.setTag(tag);
    }

    @Override
    public void onTagAttributesCreated(TagAttributes tagAttributes) {
        result.setTagAttributes(tagAttributes);
    }

    @Override
    public void onTagClassesCreated(TagClasses tagClasses) {
        result.setTagClasses(tagClasses);
    }

    @Override
    public void onTagIdCreated(TagId tagId) {
        result.setTagId(tagId);
    }

    @Override
    public void onTagHTMLCreated(TagHTML tagHTML) {
        result.setTagHtml(tagHTML);
    }

    @Override
    public void onTagTextCreated(TagText tagText) {
        result.setTagText(tagText);
    }

    public ProjectionResult getResult() {
        return result;
    }
}
