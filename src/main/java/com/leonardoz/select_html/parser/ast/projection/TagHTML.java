package com.leonardoz.select_html.parser.ast.projection;

public class TagHTML extends TagResult {

    private String htmlContent;

    public TagHTML(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    @Override
    public String toString() {
        return htmlContent;
    }
}
