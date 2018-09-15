package com.leonardoz.select_html.parser;

import com.leonardoz.select_html.parser.ast.projection.ProjectionResult;
import com.leonardoz.select_html.parser.ast.projection.Tag;
import org.junit.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class SelectHTMLTest {

    @Test
    public void shouldSelect_OnlyDivTags() {
        String query = "SELECT tag, class FROM 'https://br.lipsum.com' WHERE tag = 'div'";
        List<ProjectionResult> results = SelectHTML.parseAsResult(query);
        Optional<Tag> anyNotADivTag = results.stream()
                .filter(opt -> opt.getTag().isPresent()&& opt.getTagClass().isPresent())
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
        String query = "SELECT tag, attrs FROM 'https://br.lipsum.com' " +
                "WHERE tag='a' and (attr='target' start with '_bl')";
        List<ProjectionResult> results = SelectHTML.parseAsResult(query);
        List<String> allHref_BlankAttributes = results.stream()
                .filter(opt -> opt.getTag().isPresent() && opt.getTagAttributes().isPresent())
                .map(tag -> tag.getTagAttributes().get())
                .filter(tag -> tag.getByKey("target").equalsIgnoreCase("_blank"))
                .map(attrs -> attrs.getByKey("target"))
                .collect(Collectors.toList());

        assertEquals(13, allHref_BlankAttributes.size());
    }

    @Test
    public void shouldSelect_a_h2_tag() {
        String query = "SELECT tag FROM 'https://br.lipsum.com' " +
                "WHERE tag='div' parent of (tag='div' parent of (tag='h2' child of tag='div'))";
        List<ProjectionResult> results = SelectHTML.parseAsResult(query);
        Optional<ProjectionResult> h2Result = results.stream()
                .filter(opt -> opt.getTag().isPresent())
                .findFirst();
        assertTrue(h2Result.isPresent());
        assertEquals("h2", h2Result.get().getTag().get().getContent());
    }

    @Test
    public void shouldSelect_a_boxed_divs() {
        //hr + div.boxed
        String query = "SELECT tag, classes FROM 'https://br.lipsum.com' " +
                "WHERE tag='hr' next sibling (tag='div' and class = 'boxed')";
        List<ProjectionResult> results = SelectHTML.parseAsResult(query);
        List<ProjectionResult> divs = results.stream()
                .peek(System.out::println)
                .filter(opt -> opt.getTag().isPresent() && opt.getTagClass().isPresent())
                .collect(Collectors.toList());

        assertEquals(6, divs.size());
    }

    @Test
    public void shouldSelect_AllProjections() {
        String query = "SELECT * FROM 'https://br.lipsum.com' WHERE tag = 'div'";
        List<ProjectionResult> results = SelectHTML.parseAsResult(query);
        Optional<ProjectionResult> anyNotADivTag = results.stream()
                .filter(opt ->
                        opt.getTag().isPresent() &&
                        opt.getTagClass().isPresent() &&
                        opt.getTagAttributes().isPresent() &&
                        opt.getTagText().isPresent() &&
                        opt.getTagData().isPresent() &&
                        opt.getTagId().isPresent())
                .findAny();
        assertFalse(anyNotADivTag.isPresent());
    }

}