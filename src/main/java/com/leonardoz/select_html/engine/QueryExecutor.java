package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.model.Query;
import com.leonardoz.select_html.model.filters.QueryFilters;
import com.leonardoz.select_html.model.projection.Projection;
import com.leonardoz.select_html.model.projection.ProjectionResult;
import com.leonardoz.select_html.model.projection.ProjectionType;
import com.leonardoz.select_html.model.source.HtmlDocumentSource;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class QueryExecutor {

    private Logger logger = LoggerFactory.getLogger(QueryExecutor.class);
    private QueryResult listQueryResult = new ListQueryResult();
    private QueryResult projectionQueryResult = new ProjectionQueryResult();

    public List<ProjectionResult> getAsProjection(Query query) {
        return this.<List<ProjectionResult>>getAs(query, projectionQueryResult);
    }

    public List<List<String>> getAsList(Query query) {
        return this.<List<List<String>>>getAs(query, listQueryResult);
    }

    private <T> T getAs(Query query, QueryResult<T> result) {
        Elements elements = getElements(query);
        Projection projection = query.getProjection();
        List<Extractors.Extractor> extractors = chooseExtractors(projection);
        return result.getResult(elements, extractors);
    }

    private Elements getElements(Query query) {
        Document doc = getDocument(query);
        QueryFilters filters = query.getFilters();
        String queryStr = filters.generate();
        Elements select = doc.select(queryStr);
        return select;
    }

    private Document getDocument(Query query) {
        HtmlDocumentSource source = query.getHtmlDocumentSource();
        return getFromSource(source);
    }

    private Document getFromSource(HtmlDocumentSource source) {
        try {
            if (source.isFile()) {
                return Jsoup.parse(new File(source.getUri()), "UTF-8");
            } else {
                return Jsoup.connect(source.getUri()).get();
            }
        } catch (IOException e) {
            logger.error("Error: " + e.getMessage());
            throw new RuntimeException("Invalid URI");
        }
    }

    private List<Extractors.Extractor> chooseExtractors(Projection projection) {
        List<ProjectionType> whatToProject = projection.getWhatToProject();

        return whatToProject
                .stream()
                .map(Extractors::factory)
                .collect(Collectors.toList());
    }


}
