package com.leonardoz.select_html.parser;

import com.leonardoz.select_html.model.projection.ProjectionResult;
import com.leonardoz.select_html.model.projection.Tag;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SelectHTMLTest {

    @Test
    public void shouldSelect_OnlyDivTags() {
        String query = "SELECT tag, class FROM 'https://br.lipsum.com' WHERE tag = 'div'";
        List<ProjectionResult> results = SelectHTML.parseAsResult(query);
        Optional<Tag> anyNotADivTag = results.stream()
                .filter(opt -> opt.getTag().isPresent())
                .map(r -> r.getTag().get())
                .filter(tag -> !tag.getContent().equalsIgnoreCase("div"))
                .findAny();
        assertFalse(anyNotADivTag.isPresent());
    }

    @Test
    public void shouldSelect_h2_DescendantOf_Panes() {
        String query = "SELECT tag FROM 'https://br.lipsum.com' " +
                "WHERE tag = 'h2' descendant of id = 'Panes'";
        List<ProjectionResult> results = SelectHTML.parseAsResult(query);
        Optional<Tag> anyNotAh2Tag = results.stream()
                .filter(opt -> opt.getTag().isPresent())
                .map(r -> r.getTag().get())
                .filter(tag -> !tag.getContent().equalsIgnoreCase("h2"))
                .findAny();
        assertEquals(4, results.size());
        assertFalse(anyNotAh2Tag.isPresent());
    }

    @Test
    public void shouldSelect_a_WithTarget_StartWith_Blank() {
        //'a[target*=_blank
        String query = "SELECT tag, attrs FROM 'https://br.lipsum.com' " +
                "WHERE tag='a' and attr='target' start with '_bl'";
        List<ProjectionResult> results = SelectHTML.parseAsResult(query);
        List<String> allHref_BlankAttributes = results.stream()
                .filter(opt -> opt.getTag().isPresent() && opt.getTagAttributes().isPresent())
                .filter(r -> r.getTag().get().getContent().equalsIgnoreCase("a"))
                .map(tag -> tag.getTagAttributes().get())
                .filter(tag -> tag.getByKey("target").equalsIgnoreCase("_blank"))
                .map(attrs -> attrs.getByKey("target"))
                .collect(Collectors.toList());

        assertEquals(13, allHref_BlankAttributes.size());
    }

}