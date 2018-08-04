package com.leonardoz.select_html.parser;

import com.leonardoz.select_html.engine.Replace;
import com.leonardoz.select_html.model.filters.*;
import com.leonardoz.select_html.model.projection.ProjectionType;
import com.leonardoz.select_html.parser.SelectHtmlParserParser.*;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class SelectHtmlVisitorImpl extends com.leonardoz.select_html.parser.SelectHtmlParserBaseVisitor<String> {

    private Queue<BaseFilter> queue = new LinkedList<>();
    private List<ProjectionType> projections = new LinkedList<>();
    private String link;

    public Optional<BaseFilter> getFilter() {
        return queue.isEmpty() ? Optional.empty() : Optional.of(queue.poll());
    }

    public List<ProjectionType> getProjections() {
        return projections;
    }

    public String getLink() {
        return link;
    }

    @Override
    public String visitProjection(ProjectionContext ctx) {
        projections = ctx.projectionTypes().stream().map(pt -> {
            String text = pt.getText();
            if (text.equalsIgnoreCase("ATTR")) {
                return ProjectionType.ATTRIBUTE;
            } else if (text.equalsIgnoreCase("ATTRS")) {
                return ProjectionType.ATTRIBUTES;
            }
            return ProjectionType.of(text);
        }).collect(Collectors.toList());
        return super.visitProjection(ctx);
    }

    @Override
    public String visitFromClause(FromClauseContext ctx) {
        link = Replace.quotes(ctx.STRING().getText());
        return super.visitFromClause(ctx);
    }

    @Override
    public String visitComposedFilterExpression(ComposedFilterExpressionContext ctx) {
        String visited = super.visitComposedFilterExpression(ctx);
        String operator = ctx.composedOperators().getText();
        WhereExpressionContext left = ctx.whereExpression(0);
        WhereExpressionContext right = ctx.whereExpression(1);
        FilterType filterType = FilterType.of(operator);
        BaseFilter leftFilter = filterExpression(left);
        BaseFilter rightFilter = filterExpression(right);
        ComposedFilter composedFilter = new ComposedFilter(filterType, leftFilter, rightFilter);
        queue.offer(composedFilter);
        return visited;
    }

    @Override
    public String visitAttributeValueFilterExpression(AttributeValueFilterExpressionContext ctx) {
        String visited = super.visitAttributeValueFilterExpression(ctx);
        AttributeValueFilterContext filterCo = ctx.attributeValueFilter();
        String left = Replace.quotes(filterCo.STRING(0).getText());
        String operator = filterCo.attributeValueOperators().getText();
        String right = Replace.quotes(filterCo.STRING(1).getText());
        AttributeOperatorType type = AttributeOperatorType.of(operator);
        AttributeValueFilter filter = new AttributeValueFilter(left, type, right);
        queue.offer(filter);
        return visited;
    }

    @Override
    public String visitKeyValueFilterExpression(KeyValueFilterExpressionContext ctx) {
        String visited = super.visitKeyValueFilterExpression(ctx);
        String key = Replace.quotes(ctx.keyValueFilter().filter().getText());
        String value = Replace.quotes(ctx.keyValueFilter().STRING().getText());
        KeyValueFilter filter = new KeyValueFilter(FilterType.of(key), key, value);
        queue.offer(filter);
        return visited;
    }

    @Override
    public String visitAndOrFilterExpression(AndOrFilterExpressionContext ctx) {
        String visited = super.visitAndOrFilterExpression(ctx);
        String operator = ctx.andOrOperators().getText();
        WhereExpressionContext left = ctx.whereExpression(0);
        WhereExpressionContext right = ctx.whereExpression(1);
        BaseFilter leftFilter = filterExpression(left);
        BaseFilter rightFilter = filterExpression(right);
        BaseFilter result = null;
        if (operator.equalsIgnoreCase("AND")) {
            result = new AndOperatorFilter(leftFilter, rightFilter);
        } else if (operator.equalsIgnoreCase("OR")) {
            result = new OrOperatorFilter(leftFilter, rightFilter);
        }
        queue.offer(result);
        return visited;
    }

    public BaseFilter filterExpression(WhereExpressionContext context) {
        switch (context.getRuleIndex()) {
            case 0:
                visitComposedFilterExpression((ComposedFilterExpressionContext) context);
                break;
            case 1:
                visitAndOrFilterExpression((AndOrFilterExpressionContext) context);
                break;
            case 2:
                visitAttributeValueFilterExpression((AttributeValueFilterExpressionContext) context);
                break;
            case 3:
                visitKeyValueFilterExpression((KeyValueFilterExpressionContext) context);
                break;
        }
        return queue.poll();
    }


    @Override
    public String visit(ParseTree tree) {
        return super.visit(tree);
    }
}
