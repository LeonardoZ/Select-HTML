package com.leonardoz.select_html.parser.ast.expressions;

public interface  QueryString {

    String asOriginalFilter();

    String toQuery();

}
