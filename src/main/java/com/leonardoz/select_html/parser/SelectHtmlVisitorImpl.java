package com.leonardoz.select_html.parser;

import com.leonardoz.select_html.engine.Replace;
import com.leonardoz.select_html.parser.ast.expressions.*;
import com.leonardoz.select_html.parser.ast.ProjectionType;
import com.leonardoz.select_html.parser.SelectHtmlParserParser.*;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.stream.Collectors;

public class SelectHtmlVisitorImpl extends com.leonardoz.select_html.parser.SelectHtmlParserBaseVisitor<String> {

    private static Logger logger = LoggerFactory.getLogger(SelectHtmlVisitorImpl.class);

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
            } else if (text.equals("*")) {
                return ProjectionType.ALL;
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
        logger.debug("visitComposedFilterExpression");
        String operator = ctx.composedOperators().getText();
        WhereExpressionContext left = ctx.whereExpression(0);
        WhereExpressionContext right = ctx.whereExpression(1);
        logger.debug(left.getText());
        logger.debug(right.getText());
        FilterType filterType = FilterType.of(operator);
        BaseFilter leftFilter = filterExpression(left);
        BaseFilter rightFilter = filterExpression(right);
        logger.debug("Left " + leftFilter);
        logger.debug("Right " + rightFilter);
        ComposedFilter composedFilter = new ComposedFilter(filterType, leftFilter, rightFilter);
        queue.offer(composedFilter);
        logger.debug("Composed: " + composedFilter);
        logger.debug(" ");
        return super.visitComposedFilterExpression(ctx);
    }

    @Override
    public String visitAttributeValueFilterExpression(AttributeValueFilterExpressionContext ctx) {

        logger.debug("visitAttributeValueFilterExpression");
        AttributeValueFilterContext filterCo = ctx.attributeValueFilter();
        String left = Replace.quotes(filterCo.STRING(0).getText());
        String operator = filterCo.attributeValueOperators().getText();
        String right = Replace.quotes(filterCo.STRING(1).getText());
        AttributeOperatorType type = AttributeOperatorType.of(operator);
        AttributeValueFilter filter = new AttributeValueFilter(left, type, right);
        logger.debug("Attribute/Value: " + filter);
        queue.offer(filter);
        logger.debug(" ");
        return super.visitAttributeValueFilterExpression(ctx);
    }

    @Override
    public String visitKeyValueFilterExpression(KeyValueFilterExpressionContext ctx) {
        logger.debug("visitKeyValueFilterExpression");
        String key = Replace.quotes(ctx.keyValueFilter().filter().getText());
        String value = Replace.quotes(ctx.keyValueFilter().STRING().getText());
        KeyValueFilter filter = new KeyValueFilter(FilterType.of(key), key, value);
        logger.debug("Key/Value: " + filter);
        queue.offer(filter);
        logger.debug(" ");
        return super.visitKeyValueFilterExpression(ctx);
    }

    @Override
    public String visitAndOrFilterExpression(AndOrFilterExpressionContext ctx) {
        logger.debug("visitAndOrFilterExpression");
        String operator = ctx.andOrOperators().getText();
        WhereExpressionContext left = ctx.whereExpression(0);
        WhereExpressionContext right = ctx.whereExpression(1);
        BaseFilter leftFilter = filterExpression(left);
        BaseFilter rightFilter = filterExpression(right);
        logger.debug("Left " + leftFilter);
        logger.debug("Right " + rightFilter);

        BaseFilter result = null;
        if (operator.equalsIgnoreCase("AND")) {
            result = new AndOperatorFilter(leftFilter, rightFilter);
            logger.debug("AND: " + result);
        } else if (operator.equalsIgnoreCase("OR")) {
            result = new OrOperatorFilter(leftFilter, rightFilter);
            logger.debug("OR: " + result);
        }
        queue.offer(result);
        logger.debug(" ");
        return super.visitAndOrFilterExpression(ctx);
    }

    @Override
    public String visitParenthesisExpression(ParenthesisExpressionContext ctx) {
        logger.debug("visitParenthesisExpression");
        logger.debug(" ");
        return super.visitParenthesisExpression(ctx);
    }

    public BaseFilter filterExpression(WhereExpressionContext context) {
        visit(context);
        return queue.poll();
    }

    @Override
    public String visit(ParseTree tree) {
        return super.visit(tree);
    }
}
