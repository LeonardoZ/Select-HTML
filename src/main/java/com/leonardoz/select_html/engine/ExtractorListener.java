package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.parser.ast.projection.*;

public interface ExtractorListener {

    void onTagCreated(Tag tag);
    void onTagAttributesCreated(TagAttributes tagAttributes);
    void onTagClassesCreated(TagClasses tagClasses);
    void onTagIdCreated(TagId tagId);
    void onTagHTMLCreated(TagHTML tagHTML);
    void onTagTextCreated(TagText tagText);

}
