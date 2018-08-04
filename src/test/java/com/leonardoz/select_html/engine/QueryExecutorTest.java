package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.model.Query;
import com.leonardoz.select_html.model.filters.*;
import com.leonardoz.select_html.model.projection.Projection;
import com.leonardoz.select_html.model.projection.ProjectionResult;
import com.leonardoz.select_html.model.projection.ProjectionType;
import com.leonardoz.select_html.model.source.HtmlDocumentSource;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class QueryExecutorTest {

    @Test
    public void shouldReadFile() {
        String file = getTestFile();
        Projection projection =
                new Projection(ProjectionType.TAG, ProjectionType.CLASSES, ProjectionType.ID);
        HtmlDocumentSource source = new HtmlDocumentSource(file, true);
        QueryFilters queryFilters = new QueryFilters();
        Query query = new Query(projection, source, queryFilters);
        QueryExecutor queryExecutor = new QueryExecutor();
        List<ProjectionResult> results = queryExecutor.getAsProjection(query);
        assertNotNull(results);
    }

    @Test
    public void shouldGet_DivById() {
        String file = getTestFile();
        Projection projection = new Projection(ProjectionType.TAG);
        HtmlDocumentSource source = new HtmlDocumentSource(file, true);
        KeyValueFilter div = new KeyValueFilter(FilterType.ID, "id", "a_div");
        QueryFilters queryFilters = new QueryFilters(div);

        Query query = new Query(projection, source, queryFilters);
        QueryExecutor queryExecutor = new QueryExecutor();
        List<ProjectionResult> results = queryExecutor.getAsProjection(query);

        assertNotNull(results);
        assertEquals(1, results.size());

        ProjectionResult result = results.get(0);
        assertTrue(result.getTag().isPresent());
        assertEquals("div", result.getTag().get().getContent());
    }

    @Test
    public void shouldGet_NextSibling() {
        String file = getTestFile();

        Projection projection = new Projection(ProjectionType.TAG, ProjectionType.TEXT);
        HtmlDocumentSource source = new HtmlDocumentSource(file, true);

        KeyValueFilter anotherDiv = new KeyValueFilter(FilterType.ID, "id", "another");
        KeyValueFilter span = new KeyValueFilter(FilterType.TAG, "tag", "span");
        ComposedFilter adjacentSibling =
                new ComposedFilter(FilterType.ADJACENT_SIBLING, anotherDiv, span);
        QueryFilters queryFilters = new QueryFilters(adjacentSibling);

        Query query = new Query(projection, source, queryFilters);
        QueryExecutor queryExecutor = new QueryExecutor();
        List<ProjectionResult> results = queryExecutor.getAsProjection(query);

        assertEquals(1, results.size());

        ProjectionResult result = results.get(0);
        assertTrue(result.getTag().isPresent());
        assertTrue(result.getTagText().isPresent());

        assertEquals("span", result.getTag().get().getContent());
        assertEquals("By now", result.getTagText().get().getTextContent());
    }

    @Test
    public void shouldGet_NameAttributes() {
        String file = getTestFile();

        Projection projection = new Projection(ProjectionType.TAG, ProjectionType.ATTRIBUTES);
        HtmlDocumentSource source = new HtmlDocumentSource(file, true);

        AttributeValueFilter filter =
                new AttributeValueFilter(
                        "name", AttributeOperatorType.START_WITH, "logi");

        QueryFilters queryFilters = new QueryFilters(filter);

        Query query = new Query(projection, source, queryFilters);
        QueryExecutor queryExecutor = new QueryExecutor();
        List<ProjectionResult> results = queryExecutor.getAsProjection(query);

        assertEquals(2, results.size());

        ProjectionResult result1 = results.get(0);
        assertTrue(result1.getTag().isPresent());
        assertTrue(result1.getTagAttributes().isPresent());

        ProjectionResult result2 = results.get(1);
        assertTrue(result2.getTag().isPresent());
        assertTrue(result2.getTagAttributes().isPresent());

        assertEquals("input", result2.getTag().get().getContent());
        assertEquals("input", result1.getTag().get().getContent());

        assertEquals("login", result1.getTagAttributes().get().getByKey("name"));
        assertEquals("login_captcha", result2.getTagAttributes().get().getByKey("name"));
    }

    private String getTestFile() {
        return getClass().getClassLoader().getResource("test.html").getFile();
    }

}