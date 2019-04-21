;var jqxTreeGridUtil = (function () {
    "use strict";

    var $id, $form;
    var cellsRendererFunction = function (row, dataField, cellValue, rowData, cellText) {
        var cellValue = rowData[dataField];
        var cell = $($id).jqxTreeGrid('getColumn', dataField);
        if (cell.cellsFormat === "formatString") {
            return cellValue;
        }
        //百分比格式化
        if (cell.cellsFormat === "formatRate") {
            return $common.formatRate(cellValue, true);
        }
        if (cell.cellsFormat === "formatRateArrow") {
            var cellValueNum = new Number(cellValue);
            if (cellValueNum > 0) {
                return "<span>" + $common.formatRate(cellValue, true) + "</span><span class='fa fa-long-arrow-up' style='color: #7ACE4C;'></span>";
            } else if (cellValueNum < 0) {
                return "<span>" + $common.formatRate(cellValue, true) + "</span><span class='fa fa-long-arrow-down' style='color: #FF7872;'></span>";
            }
            return $common.formatRate(cellValue, true);
        }
        //千分位和格式化 ，还可以扩展 $common.formatFloat 可以设置精度
        if (cell.cellsFormat === "formatFloat") {
            return $common.formatFloat(cellValue);
        }//千分位和格式化 ，还可以扩展 $common.formatFloat 可以设置精度
        if (cell.cellsFormat === "formatFloatTo2") {
            return $common.formatFloat(cellValue, 2);
        }
    };

    var init = function () {
        var parentDom = $($id).parent();
        var id = $($id).attr("id");
        $($id).jqxTreeGrid('destroy');
        parentDom.html("<div id='"+ id +"'></div>")
        $.ajax({
            type: $form.attr("method"),
            url: $common.rootPath + $form.attr("action") + "?typeEnum=HEAD",
            dataType: "json",
            data: $form.serialize(),
            success: function (datas) {
                var source = {
                    dataType: "json",
                    dataFields: datas.data.dataFields,
                    hierarchy:
                        {
                            keyDataField: {name: datas.data.id},
                            parentDataField: {name: datas.data.parentId}
                        },
                    id: datas.data.id,
                    type: $form.attr("method"),
                    url: $common.rootPath + $form.attr("action") + "?typeEnum=BODY"
                };

                var columns = datas.data.columns;
                $.each(columns, function (index, val) {
                    val.cellsRenderer = cellsRendererFunction;
                });
                $($id).jqxTreeGrid({
                    width: "100%",
                    rendered: function () {
                        $($id).jqxTreeGrid('expandRow', datas.data.totalId);
                    },
                    virtualModeCreateRecords: function (expandedRecord, done) {
                        var dataAdapter = new $.jqx.dataAdapter(source, {
                                formatData: function (data) {
                                    if (expandedRecord == null) {
                                        data["level"] = "";
                                        data["treeValue"] = "";
                                    }
                                    else {
                                        data["level"] = expandedRecord.level;
                                        data["treeValue"] = expandedRecord.uid;
                                    }
                                    $("input[name]", $form).each(function (index, val) {
                                        data[$(val).attr("name")] = $(val).val();
                                    })
                                    return data;
                                },
                                loadComplete: function () {
                                    done(dataAdapter.records);
                                },
                                loadError: function (xhr, status, error) {
                                    done(false);
                                    throw new Error($common.rootPath + error.toString());
                                }
                            }
                        );
                        dataAdapter.dataBind();
                    },
                    virtualModeRecordCreating: function (record) {
                        if (record.level == datas.data.maxLevel) {
                            record.leaf = true;
                        }
                    },
                    columns: columns,
                    columnGroups: datas.data.columnGroups.length > 0 ? datas.data.columnGroups : null
                });
            },
            error: function (data) {
                if (data.status == 401) {
                    window.location.href = "401.html";
                }

                if (data.status == 403) {
                    window.location.href = "403.html";
                }
            }
        })
    };

    return {
        init: function (id) {
            $id = id;
            $form = $($id).parents("form");
            return init();
        },
        getId: function () {
            return id;
        }
    }
})();