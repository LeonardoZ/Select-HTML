package com.leonardoz.select_html.model.source;

public class HtmlDocumentSource {

    private String uri;
    private boolean isFile = false;

    public HtmlDocumentSource(String uri, boolean isFile) {
        this.uri = uri;
        this.isFile = isFile;
    }

    public String getUri() {
        return uri;
    }

    public boolean isFile() {
        return isFile;
    }
}
