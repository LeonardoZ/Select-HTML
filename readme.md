# Select HTML
## DSL with SQL-like syntax for data scraping implemented with Java.

Search for HTML elements and extract results with a single query. 

# Why?

I wanted to develop a DSL primarily for the goal to study more about parsers, parsers generators 
and simple language implementations. Doing something that can be useful (with the right amount of work) was the easiest way
to do it. This project, at this point in time, is a personal pet project. And I'm very happy to be able to learn more about the topics
that I wanted.

# Usage

To scrap data you must write a query. Query syntax is based on the well-known SQL syntax.
The query syntax can be summarized as:

```
SELECT
    [tag|attributes|attrs|class|id|text|html|] or * (all)
FROM
    'http://your-link.com'
[
WHERE
     [
        [([tag|class|id|text|html] = 'value']) | 
     
        [([attribute|attr] 'value' [=|contains|start with|end with] 'value'])]
     
        [and|or|child of|parent of|descendant of|next sibling|siblings of]
     
        [(above options again)]    
     ]    

```

String values should be wrapped into single 'quotes';
The language is **CASE INSENSITIVE**.

# Java Client

The Java Client syntax looks like:

```java
  String query = "SELECT tag, class FROM 'https://br.lipsum.com' WHERE tag = 'div'";
  List<ProjectionResult> results = SelectHTML.parseAsResult(query);
```

`SelectHTML` is the DSL start point. The `ProjectionResult` class has the following methods:


```java
class ProjectionResult {
    Optional<String> getTag();
    Optional<String> getTagAttributes();
    Optional<String> getTagClass();
    Optional<String> getTagData();
    Optional<String> getTagHtml();
    Optional<String> getTagId();
    Optional<String> getTagText();
}
```

They're optional because only the specified fields in projection 
will be filled with data (if the query finds something).
 
**Extracting results**

**Projection**
```
    List<ProjectionResult> results = SelectHTML.parseAsResult(query);
```
**List of Strings**
```
    List<List<String>> results = SelectHTML.parseAsList(query);
```

## Select HTML syntax: Projection, From and Where clauses

### Projection Clause (required)

List all values that can be scraped in HTML elements:
+ tag: e.g. div, p, h2;
+ text: all text content inside element;
+ html: the html content;
+ class: all classes inside an element;
+ id: element id; 
+ attrs or attributes: all attributes found in the element;
+ the * operator: all options above;

At least one is necessary, and more than one must be separated with a comma.

Usage: `SELECT tag, text, html`

### FROM clause (required)

Defines a website link, starting with http://, as the source that you would like to scrap.

Usage: `FROM 'http://website'`

### WHERE filters clause (optional)

Based on CSS Query Selectors, filter content using a more verbose but easy to read language. 
It'll generate **css selector** in the end of the operation. Can be wrapped with parenthesis ()

### Base Filter

Simple key = value filters. 

####  tag

HTML tags like div, p, h1.

**Syntax:** `tag = 'div'`

**Generates:** `div`

#### class

HTML element class attribute.

**Syntax**: `class = 'name_of_class'`

**Generated:**: `.name_of_class`

#### id

HTML element id attribute.

**Syntax**: `id = 'an_id'`

**Generated:**: `#an_id`

#### attribute or attr

Any HTML element attribute. Can be used as `attr` to.

**Syntax:** `attribute = 'href'`

**Generates:** `[href]`

### Attribute Value Filter

Queries can search for attribute content with several operators.

#### contains

Will find elements which the specified attribute contains some part of the search value.

**Syntax:** `attribute = 'href' contains 'blank'`

**Generates:** `[href*=blank]`

#### start with

Will find elements which the specified attribute start with the search value.

**Syntax:** `attribute = 'href' start with '_bl'`

**Generates:** `[href^=_bl]`

#### end with

Will find elements which the specified attribute end with the search value.

**Syntax:** `attribute = 'href' end with 'nk'`

**Generates:** `[href$=nk]`

#### regex

Will find elements which the specified attribute matches the search regex value.

**Syntax:** `attribute = 'href' regex 'blank'`

**Generates:** `[href~=(?i)\.(gif|jpe?g)]`


### Composed Filters

Composed filters are recognized from left to right. Any other filter can be used in both sides.

#### Child of

HTML direct child elements, specified using `>` operator.

**Syntax:** `tag = 'h2' child of tag = 'p'`

**Generates:** `p > h2`

#### Parent of

HTML direct child elements, but with a reversed notation. Specified using `>` operator.

**Syntax:** `class = 'products' parent of attr = 'name' start with 'product_'] `

**Generates:** `.products > [name^=product_]`

#### Descendant of

HTML descendant elements, searching for any child in the tree., specified using ` ` (space). 

**Syntax:** `id = 'logo' descendant of tag = 'div'`

**Generates:** `div #logo`

#### Siblings of

General sibling operator, uses two selectors and matches the second if it follows 
the first element, being childes of the same father but not necessary sequential.

**Syntax:** `tag = 'div' siblings of tag = 'p'`

**Generates:** `div ~ p`

#### Next Sibling

Adjacent sibling operator, uses two selectors and matches the second if it is immediately 
preceded by the first.

**Syntax:** `tag = 'div' next sibling tag = 'p'`

**Generates:** `div + p`

### Filter combinator

And and Or can be combined multiple times with Base, Attribute Value and Combined filters.

e.g. `tag = 'a' and attr = 'href' and class = 'highlight'` generates `a[href].highlight`

also, it'll be resolved in the following order:

`(tag = 'a' and (attr = 'href' and class = 'highlight'))`
  //(left, right(left, right))
  
#### And

Combines selectors by appending them, e,g.: element.a_class means element with class a_class.

**Syntax:** `tag = div and class = 'prods'`

**Generates:** `div.prods`

#### Or

Group multiple selectors, separating them with comma, finding any different element that matches a group. 

**Syntax:** `tag = div or class = 'prods'`

**Generates:** `div, p`

# Architecture

Select HTML is a DSL, a Domain Specific Language, or just a simple language inspired in SQL select queries, 
with the goal to simplify data scraping from HTML web pages.

There are two important parts in this architecture: ANTLR and jsoup.

## ANTLR 4

ANTLR is a **Parser Generator**, which generates a Lexer, Parser and a Visitor (for parse tree walking), 
all of them coming from a grammar file, localized in the resources folder. 

The **grammar** files specifies rules for the lexer and the parser to be generated, alongside metadata for the 
walking process. It uses an EBNF-like syntax.

The **lexer** will take the text as a stream and create a token stream based on the lexer rules.

The **parser** will process the generated token stream by matching the tokens against the parser rules. 
It'll use a Recursive Descent algorithm to do so.

The result for a certain query is a Parse Tree. A Parse tree represents the result of the parse process in a tree structure. 

In this project, the Visitor Pattern, which is ANTLR generated, is used to walk by the tree nodes (which represents our rules) and to fill 
the model.

## Model

A representation of the language in memory, using simple POJOs to match the rules specified in the grammar. Known as the
semantic model, it's filled with the parse tree visiting process. It's divided in **projection, source and filters**. 

## Parser

Implements the generated ANTLR Visitor class. The most complicated part is when the filters expressions must be evaluated. 

A queue structure is used to keep track of the last evaluated filter expression. Filters whose operators are binary (like and, or or attribute/values ones) needs this extra care.

The model is filled/generated here and used by the "engine".

## Engine

Will take the model, generate a query, evaluating it with jsoup library. The specified projections will be used to create projections objects. It's fairly simple. 

jsoup is used to evaluate the CSS-like select query, to walk through the HTML and 
to create the projections by extracting that from the HTML tags.

# Developer notes

All the parser files are generated by the **antlr4-maven-plugin**. Following a convention that all files that can be generated shouldn't be part of the git source tree, you must generate them to use in development. 

The files are generated  at the  **target/generated-sources/antlr4** folder. In order to develop you must do:

1) Run `mvn clean antlr4:antlr4`
This will generate the parser files with the package `com.leonardoz.select_html.parser` in the 
**target/generated-sources/antlr4** folder;

2) Configure your IDE to see the generated files in **target/generated-sources/antlr4** folder as a source folder; 

# Using it

This project isn't ready to be released yet, it's very experimental. Although, it's possible to build it as a jar file and 
include it whatever you like.

# Support

Use with Java 8+ or later.

# Build

Use the command `mvn clean package install` to generate the JAR file. 

# Logging

Select-HTML uses SLF4J, so you can bind any log framework compatible with SLF4J. In test, Log4j2 is used.

# TO-DO


+ Code Validations and exception error handling;
+ Ancestor filter;
+ Pure CSS query filters;
+ Pseudo selectors;
+ Page download cache for multiple queries on the same page;
+ Variables support;
+ More result extractors;
+ Expand test suite.

# License

MIT


