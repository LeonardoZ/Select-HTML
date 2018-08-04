package com.leonardoz.select_html.model.projection;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Projection {

    private List<ProjectionType> whatToProject = new LinkedList<>();

    public Projection(List<ProjectionType> whatToProject) {
        this.whatToProject = whatToProject;
    }

    public Projection(ProjectionType... whatToProject) {
        this.whatToProject.addAll(Arrays.asList(whatToProject));
    }

    public List<ProjectionType> getWhatToProject() {
        return whatToProject;
    }
}
