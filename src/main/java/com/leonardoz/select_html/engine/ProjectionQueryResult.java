package com.leonardoz.select_html.engine;

import com.leonardoz.select_html.parser.ast.projection.ProjectionResult;
import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectionQueryResult implements QueryResult<List<ProjectionResult>> {

    @Override
    public List<ProjectionResult> getResult(Elements elements, List<Extractors.Extractor> extractors) {
        return elements.stream().map(element -> {
            ProjectionResultExtractorListener listener = new ProjectionResultExtractorListener();
            extractors.forEach(extractor -> extractor.project(element, listener));
            return listener.getResult();
        }).collect(Collectors.toList());
    }


}
