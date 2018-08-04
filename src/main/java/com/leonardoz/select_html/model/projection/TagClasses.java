package com.leonardoz.select_html.model.projection;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class TagClasses extends TagResult {

    private List<TagClass> classes = new LinkedList<>();

    public TagClasses(List<TagClass> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return classes.stream()
                .map(TagClass::toString)
                .collect(Collectors.joining(" "));
    }
}
