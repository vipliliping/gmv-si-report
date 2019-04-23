package com.beyond.yili.report.yilireportweb.model.base;

/**
 * @author vipliliping
 * @create 2019/4/22 09:08
 * @desc
 **/
public class ColumnGroups {
    private String text;
    private String name;

    public void setText(String text) {
        this.text = text;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ColumnGroups)) {
            return false;
        }
        ColumnGroups other = (ColumnGroups) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$text = getText();
        Object other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) {
            return false;
        }
        Object this$name = getName();
        Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        Object this$align = getAlign();
        Object other$align = other.getAlign();
        return this$align == null ? other$align == null : this$align.equals(other$align);
    }

    protected boolean canEqual(Object other) {
        return other instanceof ColumnGroups;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $text = getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        Object $name = getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $align = getAlign();
        result = result * 59 + ($align == null ? 43 : $align.hashCode());
        return result;
    }

    public String toString() {
        return "ColumnGroups(text=" + getText() + ", name=" + getName() + ", align=" + getAlign() + ")";
    }

    public String getText() {
        return this.text;
    }

    public String getName() {
        return this.name;
    }

    public String getAlign() {
        return this.align;
    }

    private String align = "center";
}
