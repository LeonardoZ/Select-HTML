package com.leonardoz.select_html.parser;

import com.leonardoz.select_html.engine.QueryExecutor;
import com.leonardoz.select_html.model.Query;
import com.leonardoz.select_html.model.filters.QueryFilters;
import com.leonardoz.select_html.model.projection.Projection;
import com.leonardoz.select_html.model.projection.ProjectionResult;
import com.leonardoz.select_html.model.source.HtmlDocumentSource;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.List;

public class SelectHTML {

    private static QueryExecutor executor = new QueryExecutor();

    public static List<ProjectionResult> parseAsResult(String textSource) {
        SelectHtmlVisitorImpl selectHtmlVisitor = setupAntlr(textSource);
        Query query = generateQuery(selectHtmlVisitor);
        return executor.getAsProjection(query);
    }

    public static List<List<String>> parseAsList(String textSource) {
        SelectHtmlVisitorImpl selectHtmlVisitor = setupAntlr(textSource);
        Query query = generateQuery(selectHtmlVisitor);
        return executor.getAsList(query);
    }

    private static SelectHtmlVisitorImpl setupAntlr(String textSource) {
        CodePointCharStream select = CharStreams.fromString(textSource);
        com.leonardoz.select_html.parser.SelectHtmlParserLexer lexer = new com.leonardoz.select_html.parser.SelectHtmlParserLexer(select);
        com.leonardoz.select_html.parser.SelectHtmlParserParser parser =
                new com.leonardoz.select_html.parser.SelectHtmlParserParser(new CommonTokenStream(lexer));
        SelectHtmlVisitorImpl visitor = new SelectHtmlVisitorImpl();
        com.leonardoz.select_html.parser.SelectHtmlParserParser.StatementContext visited = parser.statement();
        visitor.visit(visited);
        return visitor;
    }

    private static Query generateQuery(SelectHtmlVisitorImpl visitor) {
        Projection projection = new Projection(visitor.getProjections());
        HtmlDocumentSource source = new HtmlDocumentSource(visitor.getLink(), false);
        QueryFilters queryFilters = new QueryFilters(visitor.getFilter());
        return new Query(projection, source, queryFilters);
    }

}
