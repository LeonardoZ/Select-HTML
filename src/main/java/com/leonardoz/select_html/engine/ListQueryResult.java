package com.leonardoz.select_html.engine;

import org.jsoup.select.Elements;

import java.util.List;
import java.util.stream.Collectors;

public class ListQueryResult implements QueryResult<List<List<String>>> {

    @Override
    public List<List<String>> getResult(Elements elements, List<Extractors.Extractor> extractors) {
        return elements.stream().map(element -> extractors
                .stream()
                .map(extractor ->
                        extractor.project(element, DefaultExtractorListener.instance()).toString())
                    .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

}
