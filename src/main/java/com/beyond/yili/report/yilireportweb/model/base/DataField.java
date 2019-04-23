package com.beyond.yili.report.yilireportweb.model.base;

/**
 * @author vipliliping
 * @create 2019/4/22 09:09
 * @desc
 **/
public class DataField {
    private String name;
    private String type;

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof DataField)) {
            return false;
        }
        DataField other = (DataField) o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$name = getName();
        Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) {
            return false;
        }
        Object this$type = getType();
        Object other$type = other.getType();
        return this$type == null ? other$type == null : this$type.equals(other$type);
    }

    protected boolean canEqual(Object other) {
        return other instanceof DataField;
    }

    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        Object $name = getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        Object $type = getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        return result;
    }

    public String toString() {
        return "DataField(name=" + getName() + ", type=" + getType() + ")";
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }
}