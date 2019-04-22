package com.beyond.yili.report.yilireportweb.model.base;

/**
 * @author vipliliping
 * @create 2019/4/21 22:01
 * @desc
 **/

import com.beyond.yili.common.util.FormatEnum;

public class Column {
    private String text;
    private String columnGroup;
    private String dataField;
    private String width = "auto";
    private String align = "center";
    private String cellsAlign = "center";
    private String cellsFormat;

    public Column() {
        this.cellsFormat = FormatEnum.FORMAT_STRING.code;
    }

    public String getText() {
        return this.text;
    }

    public String getColumnGroup() {
        return this.columnGroup;
    }

    public String getDataField() {
        return this.dataField;
    }

    public String getWidth() {
        return this.width;
    }

    public String getAlign() {
        return this.align;
    }

    public String getCellsAlign() {
        return this.cellsAlign;
    }

    public String getCellsFormat() {
        return this.cellsFormat;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColumnGroup(String columnGroup) {
        this.columnGroup = columnGroup;
    }

    public void setDataField(String dataField) {
        this.dataField = dataField;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setCellsAlign(String cellsAlign) {
        this.cellsAlign = cellsAlign;
    }

    public void setCellsFormat(String cellsFormat) {
        this.cellsFormat = cellsFormat;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Column)) {
            return false;
        } else {
            Column other = (Column) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label95:
                {
                    Object this$text = this.getText();
                    Object other$text = other.getText();
                    if (this$text == null) {
                        if (other$text == null) {
                            break label95;
                        }
                    } else if (this$text.equals(other$text)) {
                        break label95;
                    }

                    return false;
                }

                Object this$columnGroup = this.getColumnGroup();
                Object other$columnGroup = other.getColumnGroup();
                if (this$columnGroup == null) {
                    if (other$columnGroup != null) {
                        return false;
                    }
                } else if (!this$columnGroup.equals(other$columnGroup)) {
                    return false;
                }

                Object this$dataField = this.getDataField();
                Object other$dataField = other.getDataField();
                if (this$dataField == null) {
                    if (other$dataField != null) {
                        return false;
                    }
                } else if (!this$dataField.equals(other$dataField)) {
                    return false;
                }

                label74:
                {
                    Object this$width = this.getWidth();
                    Object other$width = other.getWidth();
                    if (this$width == null) {
                        if (other$width == null) {
                            break label74;
                        }
                    } else if (this$width.equals(other$width)) {
                        break label74;
                    }

                    return false;
                }

                label67:
                {
                    Object this$align = this.getAlign();
                    Object other$align = other.getAlign();
                    if (this$align == null) {
                        if (other$align == null) {
                            break label67;
                        }
                    } else if (this$align.equals(other$align)) {
                        break label67;
                    }

                    return false;
                }

                Object this$cellsAlign = this.getCellsAlign();
                Object other$cellsAlign = other.getCellsAlign();
                if (this$cellsAlign == null) {
                    if (other$cellsAlign != null) {
                        return false;
                    }
                } else if (!this$cellsAlign.equals(other$cellsAlign)) {
                    return false;
                }

                Object this$cellsFormat = this.getCellsFormat();
                Object other$cellsFormat = other.getCellsFormat();
                if (this$cellsFormat == null) {
                    if (other$cellsFormat != null) {
                        return false;
                    }
                } else if (!this$cellsFormat.equals(other$cellsFormat)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Column;
    }


    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        String $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        String $columnGroup = this.getColumnGroup();
        result = result * 59 + ($columnGroup == null ? 43 : $columnGroup.hashCode());
        String $dataField = this.getDataField();
        result = result * 59 + ($dataField == null ? 43 : $dataField.hashCode());
        String $width = this.getWidth();
        result = result * 59 + ($width == null ? 43 : $width.hashCode());
        String $align = this.getAlign();
        result = result * 59 + ($align == null ? 43 : $align.hashCode());
        String $cellsAlign = this.getCellsAlign();
        result = result * 59 + ($cellsAlign == null ? 43 : $cellsAlign.hashCode());
        String $cellsFormat = this.getCellsFormat();
        result = result * 59 + ($cellsFormat == null ? 43 : $cellsFormat.hashCode());
        return result;
    }

    public String toString() {
        return "Column(text=" + this.getText() + ", columnGroup=" + this.getColumnGroup() + ", dataField=" + this.getDataField() + ", width=" + this.getWidth() + ", align=" + this.getAlign() + ", cellsAlign=" + this.getCellsAlign() + ", cellsFormat=" + this.getCellsFormat() + ")";
    }
}