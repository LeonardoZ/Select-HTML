package com.leonardoz.select_html.parser.ast;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Projection {

    private List<ProjectionType> projectionTypes = new LinkedList<>();

    public Projection(List<ProjectionType> projectionTypes) {
        this.projectionTypes = projectionTypes;
    }

    public Projection(ProjectionType... projectionTypes) {
        this.projectionTypes.addAll(Arrays.asList(projectionTypes));
    }

    public List<ProjectionType> getProjectionTypes() {
        return projectionTypes;
    }
}
