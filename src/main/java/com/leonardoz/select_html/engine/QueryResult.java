package com.leonardoz.select_html.engine;

import org.jsoup.select.Elements;

import java.util.List;

public interface QueryResult<T> {

    T getResult(Elements elements, List<Extractors.Extractor> extractors);

}
