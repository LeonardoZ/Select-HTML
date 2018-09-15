package com.leonardoz.select_html.parser.ast;

public class Query {

    private Projection projection;
    private FromClause fromClause;
    private WhereClause whereClause;

    public Query(Projection projection, FromClause fromClause, WhereClause whereClause) {
        this.projection = projection;
        this.fromClause = fromClause;
        this.whereClause = whereClause;
    }

    public Projection getProjection() {
        return projection;
    }

    public FromClause getFromClause() {
        return fromClause;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }
}
