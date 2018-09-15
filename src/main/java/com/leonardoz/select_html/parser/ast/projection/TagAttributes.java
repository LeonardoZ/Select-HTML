package com.leonardoz.select_html.parser.ast.projection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagAttributes extends TagResult {

    private Map<String, String> attributes = new HashMap<>();

    public TagAttributes(List<TagAttribute> attrs) {
        attrs.forEach(a -> this.attributes.put(a.getName(), a.getValue()));
    }

    public String getByKey(String key) {
        return attributes.get(key);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        attributes.forEach((k, v) -> {
            sb.append(k);
            sb.append(v.isEmpty() ? "" : "=");
            sb.append(v);
            sb.append(", ");
        });
        return sb.toString();
    }
}
