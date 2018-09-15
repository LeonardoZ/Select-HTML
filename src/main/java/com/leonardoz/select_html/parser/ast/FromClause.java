package com.leonardoz.select_html.parser.ast;

public class FromClause {

    private String uri;
    private boolean isFile = false;

    public FromClause(String uri, boolean isFile) {
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
