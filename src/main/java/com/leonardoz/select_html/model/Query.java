package com.leonardoz.select_html.model;

import com.leonardoz.select_html.model.filters.QueryFilters;
import com.leonardoz.select_html.model.projection.Projection;
import com.leonardoz.select_html.model.source.HtmlDocumentSource;

public class Query {

    private Projection projection;
    private HtmlDocumentSource htmlDocumentSource;
    private QueryFilters filters;

    public Query(Projection projection, HtmlDocumentSource htmlDocumentSource, QueryFilters filters) {
        this.projection = projection;
        this.htmlDocumentSource = htmlDocumentSource;
        this.filters = filters;
    }

    public Projection getProjection() {
        return projection;
    }

    public HtmlDocumentSource getHtmlDocumentSource() {
        return htmlDocumentSource;
    }

    public QueryFilters getFilters() {
        return filters;
    }
}
