package com.beyond.yili.report.yilireportweb.model.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author vipliliping
 * @create 2019/4/23 10:39
 * @desc
 **/
public class TreeGridHead {
    private static final Logger log = LoggerFactory.getLogger(TreeGridHead.class);
    private List<Column> columns = new ArrayList();
    private List<DataField> dataFields = new ArrayList();
    private List<ColumnGroups> columnGroups = new ArrayList();
    private String id = "id";
    private String parentId = "pId";
    private int maxLevel = 0;
    private String totalId = "总计";
    private String[] columnsArr;
    private String[] columnKeysArr;
    private String[] columnFormat;
    private String[] excludeColsArr;
    private String[][] columnGroupsArr;

    public TreeGridHead() {
    }

    public TreeGridHead(int maxLevel, String[] columns, String[] columnKeys, String[] excludeCols, String[] columnFormat) {
        this.maxLevel = maxLevel;
        this.columnsArr = columns;
        this.columnKeysArr = columnKeys;
        this.columnFormat = columnFormat;
        this.excludeColsArr = excludeCols;
        this.init();
    }

    public TreeGridHead(int maxLevel, String[] columns, String[] columnKeys, String[] excludeCols, String[] columnFormat, String[][] columnGroups) {
        this.maxLevel = maxLevel;
        this.columnsArr = columns;
        this.columnKeysArr = columnKeys;
        this.columnFormat = columnFormat;
        this.excludeColsArr = excludeCols;
        this.columnGroupsArr = columnGroups;
        this.init();
    }

    private void init() {
        if (this.columnsArr != null) {
            int i;
            if (this.columnGroupsArr != null) {
                String[][] var1 = this.columnGroupsArr;
                i = var1.length;

                for(int var3 = 0; var3 < i; ++var3) {
                    String[] tempArr = var1[var3];
                    ColumnGroups columnGroups1 = new ColumnGroups();
                    columnGroups1.setName(tempArr[2]);
                    columnGroups1.setText(tempArr[2]);
                    this.columnGroups.add(columnGroups1);
                }
            }

            List<String> excludeColsList = Arrays.asList(this.excludeColsArr);

            for(i = 0; i < this.columnKeysArr.length; ++i) {
                String colKey = this.columnKeysArr[i];
                if (!excludeColsList.contains(colKey)) {
                    Column column = new Column();
                    column.setText(this.columnsArr[i]);
                    column.setDataField(colKey);
                    if (this.columns.size() == 0) {
                        column.setCellsAlign("left");
                        column.setWidth("300");
                    }

                    try {
                        column.setCellsFormat(this.columnFormat[i]);
                    } catch (Exception e) {
                        log.error("TreeGridHead columnFormat 配置个数不正确！");
                    }
                    if (this.columnGroupsArr != null) {
                        for (String[] tempArr : this.columnGroupsArr) {
                            if ((Integer.parseInt(tempArr[0]) <= i) && (i < Integer.parseInt(tempArr[1]))) {
                                column.setColumnGroup(tempArr[2]);
                            }
                        }
                    }
                    this.columns.add(column);
                }

                DataField dataField = new DataField();
                dataField.setName(colKey);
                dataField.setType("string");
                this.dataFields.add(dataField);
            }

            if (!Arrays.asList(this.columnsArr).contains(this.parentId)) {
                DataField dataField = new DataField();
                dataField.setName(this.parentId);
                dataField.setType("String");
                this.dataFields.add(dataField);
            }

        }
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<DataField> getDataFields() {
        return this.dataFields;
    }

    public void setDataFields(List<DataField> dataFields) {
        this.dataFields = dataFields;
    }

    public List<ColumnGroups> getColumnGroups() {
        return this.columnGroups;
    }

    public void setColumnGroups(List<ColumnGroups> columnGroups) {
        this.columnGroups = columnGroups;
    }

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public String getTotalId() {
        return this.totalId;
    }

    public void setTotalId(String totalId) {
        this.totalId = totalId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
