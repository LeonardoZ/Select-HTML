package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.model.projection.*;

public class DefaultExtractorListener implements ExtractorListener {

    private DefaultExtractorListener() { }

    @Override
    public void onTagCreated(Tag tag) {

    }

    @Override
    public void onTagAttributesCreated(TagAttributes tagAttributes) {

    }

    @Override
    public void onTagClassesCreated(TagClasses tagClasses) {

    }

    @Override
    public void onTagIdCreated(TagId tagId) {

    }

    @Override
    public void onTagHTMLCreated(TagHTML tagHTML) {

    }

    @Override
    public void onTagTextCreated(TagText tagText) {

    }

    public static DefaultExtractorListener instance() {
        return new DefaultExtractorListener();
    }
}
