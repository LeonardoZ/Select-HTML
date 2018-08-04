package com.leonardoz.select_html.model.projection;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Optional;

public class ProjectionResult {

    private Optional<TagAttributes> tagAttributes = Optional.empty();
    private Optional<TagId> tagId = Optional.empty();
    private Optional<TagClasses> tagClasses = Optional.empty();
    private Optional<TagData> tagData = Optional.empty();
    private Optional<TagText> tagText = Optional.empty();
    private Optional<TagHTML> tagHtml = Optional.empty();
    private Optional<Tag> tag = Optional.empty();

    public Optional<TagAttributes> getTagAttributes() {
        return tagAttributes;
    }

    public void setTagAttributes(TagAttributes tagAttributes) {
        this.tagAttributes = Optional.ofNullable(tagAttributes);
    }

    public Optional<TagId> getTagId() {
        return tagId;
    }

    public void setTagId(TagId tagId) {
        this.tagId = Optional.ofNullable(tagId);
    }

    public Optional<TagClasses> getTagClass() {
        return tagClasses;
    }

    public void setTagClasses(TagClasses tagClasses) {
        this.tagClasses = Optional.ofNullable(tagClasses);
    }

    public Optional<TagData> getTagData() {
        return tagData;
    }

    public void setTagData(TagData tagData) {
        this.tagData = Optional.ofNullable(tagData);
    }

    public Optional<TagText> getTagText() {
        return tagText;
    }

    public void setTagText(TagText tagText) {
        this.tagText = Optional.ofNullable(tagText);
    }

    public Optional<TagHTML> getTagHtml() {
        return tagHtml;
    }

    public void setTagHtml(TagHTML tagHtml) {
        this.tagHtml = Optional.ofNullable(tagHtml);
    }

    public Optional<Tag> getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = Optional.ofNullable(tag);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("tagAttributes", tagAttributes)
                .append("tagId", tagId)
                .append("tagClasses", tagClasses)
                .append("tagData", tagData)
                .append("tagText", tagText)
                .append("tagHtml", tagHtml)
                .append("tag", tag)
                .toString();
    }
}
