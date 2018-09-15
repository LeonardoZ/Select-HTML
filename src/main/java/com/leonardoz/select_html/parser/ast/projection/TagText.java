package com.leonardoz.select_html.model.projection;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TagText extends TagResult {

    private String textContent;

    public TagText(String textContent) {
        this.textContent = textContent;
    }

    public String getTextContent() {
        return textContent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("textContent", textContent)
                .toString();
    }
}
