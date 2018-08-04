package com.leonardoz.select_html.model.projection;

public class Tag extends TagResult {

    private String content;

    public Tag(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return content;
    }
}
