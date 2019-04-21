function initYiliToken() {
    var yiliToken = $common.getUrlParam('yili-token')
    $("#yili-token").val(yiliToken);
}
$(document).ready(function () {
    var $root_path = $common.rootPath + 'gmv'
    initYiliToken();

    //查询按钮
    $("#search").on("click",function(){
        jqxTreeGridUtil.init("#treegrid");
    })
    $("#dataEndTime").html($common.formatDate("yyyy年MM月dd日", new Date(new Date() - 24 * 60 * 60 * 1000)));

    var pathUrl = {
        'PLUrl': '/filter/catePL',
        'PPUrl': '/filter/catePP',
        'SKUUrl': '/filter/cateSKU',
        'DWUrl': '/filter/cateDW',
        'DPUrl': '/filter/cateDP',
        'PTUrl': '/filter/catePT',
    }

    var refreshSelectState = {// 记录刷新机制
        'date': [
            {init: 'PTSelect', isSingle: false, url: pathUrl['PTUrl'], params: ['date']},
            {init: 'DPSelect', isSingle: false, url: pathUrl['DPUrl'], params: ['date', 'PTSelect']},
            {init: 'PLSelect', isSingle: false, url: pathUrl['PLUrl'], params: ['date']},
            {init: 'PPSelect', isSingle: false, url: pathUrl['PPUrl'], params: ['date', 'PLSelect']},
            {init: 'SKUSelect', isSingle: false, url: pathUrl['SKUUrl'], params: ['date', 'PLSelect', 'PPSelect']},
            {
                init: 'DWSelect',
                isSingle: false,
                url: pathUrl['DWUrl'],
                params: ['date', 'PLSelect', 'PPSelect', 'SKUSelect']
            },
        ],
        'PLSelect': [
            {init: 'PPSelect', isSingle: false, url: pathUrl.PPUrl, params: ['date', 'PLSelect']},
            {init: 'SKUSelect', isSingle: false, url: pathUrl.SKUUrl, params: ['date', 'PLSelect', 'PPSelect']},
            {
                init: 'DWSelect',
                isSingle: false,
                notIncludeDisable:'婴儿粉',
                url: pathUrl.DWUrl,
                params: ['date', 'PLSelect', 'PPSelect', 'SKUSelect']
            }
        ],
        'PPSelect': [
            {init: 'SKUSelect', isSingle: false, url: pathUrl.SKUUrl, params: ['date', 'PLSelect', 'PPSelect']},
            {
                init: 'DWSelect',
                isSingle: false,
                url: pathUrl.DWUrl,
                params: ['date', 'PLSelect', 'PPSelect', 'SKUSelect']
            }
        ],
        'SKUSelect': [
            {
                init: 'DWSelect',
                isSingle: false,
                url: pathUrl.DWUrl,
                params: ['date', 'PLSelect', 'PPSelect', 'SKUSelect']
            }
        ],
        'DWSelect': null,
        'PTSelect': [
            {
                init: 'DPSelect',
                isSingle: false,
                url: pathUrl.DPUrl,
                params: ['date','PTSelect']
            }
        ],
        'DPSelect': null,
        'capsule': [
            {init: 'select2', isSingle: true, url: '', params: ['date', 'select']},
            {init: 'select3', isSingle: true, url: '', params: ['select', 'select2']}
        ]
    }
    var g_params = {// 定义参数
        'dateKey': 'date',
        'dateValue': '',
        'dateFreeze': '',
        'PLSelectKey': 'pl',
        'PLSelectValue': ['all'],
        'PLSelectFreeze': ['all'],
        'PPSelectKey': 'pp',
        'PPSelectValue': ['all'],
        'PPSelectFreeze': ['all'],
        'SKUSelectKey': 'sku',
        'SKUSelectValue': ['all'],
        'SKUSelectFreeze': ['all'],
        'DWSelectKey': 'dw',
        'DWSelectValue': ['all'],
        'DWSelectFreeze': ['all'],
        'PTSelectKey': 'pt',
        'PTSelectValue': ['all'],
        'PTSelectFreeze': ['all'],
        'DPSelectKey': 'dp',
        'DPSelectValue': ['all'],
        'DPSelectFreeze': ['all']
    }

    var capsule = {
        '年': {format: 'yyyy', startView: 2, maxViewMode: 2, minViewMode: 2},
        '月': {format: 'yyyy-mm', startView: 2, maxViewMode: 1, minViewMode: 1},
        '日': {format: 'yyyy-mm-dd', startView: 0, maxViewMode: 4, minViewMode: 0}
    }


    function initDatePicker(config) {
        var nowDate = new Date()
        var strYear = nowDate.getFullYear() - 3;
        var strDay = nowDate.getDate();
        var strMonth = nowDate.getMonth() + 1;
        if (strMonth < 10) {
            strMonth = "0" + strMonth;
        }
        if (strDay < 10) {
            strDay = "0" + strDay;
        }

        var starterDate = new Date(strYear + '-' + strMonth + '-' + strDay)

        var format = 'yyyy-mm-dd', startView = 0, maxViewMode = 4, minViewMode = 0
        if (config) {
            format = config.format
            startView = config.startView
            maxViewMode = config.maxViewMode
            minViewMode = config.minViewMode
        }
        var temp_date = (new Date().getTime()-24*60*60*1000)
        $("#date").val($common.formatDate("yyyy-MM-dd", temp_date))
        g_params['dateValue'] = $common.formatDate("yyyy-MM-dd", new Date())
        g_params['dateFreeze'] = $common.formatDate("yyyy-MM-dd", new Date())

        $('#date').datepicker({
            // default: nowDate,
            format: format,
            language: 'zh-CN',
            startDate: starterDate,
            autoclose: true,
            startView: startView,
            maxViewMode: maxViewMode,
            minViewMode: minViewMode
        }).on('hide', function (ev) {// 隐藏选择日期框执行的回调
            var freeze = g_params['dateFreeze']
            var date = new Date($('#date').val())
            g_params['dateValue'] = $common.formatDate("yyyy-MM-dd", date)

            if(freeze !== g_params['dateValue']) {
                refreshSelectState['date'].forEach(function (v) {
                    var params = {}
                    v.params.forEach(function (t) {
                        if (g_params[t + 'Key']) {
                            params[g_params[t + 'Key']] = g_params[t + 'Value'] instanceof Array ? g_params[t + 'Value'].join(",") : g_params[t + 'Value']
                        }
                    })
                    params['yili-token'] = $("#yili-token").val()
                    $.ajax({
                        url: $root_path + v.url,
                        type: "post",
                        data: params,
                        success: function (data) {

                            if (data.code == 200) {
                                var options = data.data
                            }
                            refreshSelects(v.init, refreshSelectState[v.init], false, options)
                        }
                    })
                })
                jqxTreeGridUtil.init("#treegrid");
            }
            g_params['dateFreeze'] = g_params['dateValue']
        });
        initSelect(new Array())
    }

    function initSelect(data) {
        var selectOption = data
        refreshSelects('PTSelect', refreshSelectState['PTSelect'], false, selectOption)
        refreshSelects('DPSelect', refreshSelectState['DPSelect'], false, selectOption)
        refreshSelects('PLSelect', refreshSelectState['PLSelect'], false, selectOption)
        refreshSelects('PPSelect', refreshSelectState['PPSelect'], false, selectOption)
        refreshSelects('SKUSelect', refreshSelectState['SKUSelect'], false, selectOption)
        refreshSelects('DWSelect', refreshSelectState['DWSelect'], false, selectOption)
        g_params['PTSelectValue'] =['all']
        g_params['DPSelectValue'] =['all']
        g_params['PLSelectValue'] =['all']
        g_params['PPSelectValue'] =['all']
        g_params['SKUSelectValue'] =['all']
        g_params['DWSelectValue'] =['all']
        $('#PTSelectHidden').val('all')
        $('#DPSelectHidden').val('all')
        $('#PLSelectHidden').val('all')
        $('#PPSelectHidden').val('all')
        $('#SKUSelectHidden').val('all')
        $('#DWSelectHidden').val('all')

        refreshSelectState['date'].forEach(function (v) {
            var params = {}
            v.params.forEach(function (t) {
                if (g_params[t + 'Key']) {
                    params[g_params[t + 'Key']] = g_params[t + 'Value'] instanceof Array ? g_params[t + 'Value'].join(",") : g_params[t + 'Value']
                }
            })
            params['yili-token'] = $("#yili-token").val()
            $.ajax({
                url: $root_path + v.url,
                type: "post",
                data: params,
                success: function (data) {

                    if (data.code == 200) {
                        var options = data.data
                    }
                    refreshSelects(v.init, refreshSelectState[v.init], false, options)
                }
            })
        })
        jqxTreeGridUtil.init("#treegrid");
    }

    /**
     * @params
     * initId:要变成多选框的ID，
     * refreshId:如果一个多选框关闭后需要加载其他的多选框ID，例如，A筛选器要初始化，选择A筛选器中一项要刷新B筛选器，则initId=A，refreshId=B
     * isSingle:当前要加载的多选框是否是单选{true,false}
     * options:要动态放入当前要初始化筛选器中的值，如A筛选器，即option
     * **/
    function refreshSelects(initId, refreshIds, isSingle, options) {
        var o = '', $id = '#' + initId
        options.forEach(function (v) {
            o += '<option value="' + v + '">' + v + '</option>'
        })
        $($id).empty()
        $($id).append(o)
        $($id).multipleSelect({
            single: isSingle,// true,false
            selectAllText: '全部',
            onClick: function (view) {// 选中一个后触发
                var value = g_params[initId + 'Value']
                var selectOptions = $('#'+initId+' option')
                if (view.checked) { //如果是选中
                    g_params[initId + 'Value'].push(view.value)
                } else {
                    if(value.length === 1 && value[0] === 'all') {
                        g_params[initId + 'Value'] = []
                        for(var so=0;so<selectOptions.length;so++) {
                            g_params[initId + 'Value'].push($(selectOptions[so]).val())
                        }
                    }
                    var index = g_params[initId + 'Value'].indexOf(view.value)
                    g_params[initId + 'Value'].splice(index, 1)
                }
            },
            onCheckAll: function () {// 如果是全选，全选后触发
                g_params[initId + 'Value'] = ['all']
            },
            onUncheckAll: function(){
                var index = g_params[initId + 'Value'].indexOf('all')
                g_params[initId + 'Value'].splice(index, 1)
            },
            onClose: function () {// 关闭筛选器后触发
                var isChange = false, // 判断是否改变了值
                    freeze = g_params[initId + 'Freeze'],// 上一次筛选器选择的值
                    value = g_params[initId + 'Value'],// 当前筛选器选择的值
                    setValue = ''

                if(typeof freeze === 'object' && freeze.length !== undefined) {
                    isChange = freeze.length !== value.length
                    if(!isChange) {
                        freeze.forEach(function (f) {
                            value.forEach(function (v) {
                                if (f !== v) {
                                    isChange = true
                                }
                            })
                        })
                    }
                    if(isChange) {
                        value.forEach(function(vv, i) {
                            setValue += vv
                            if(i < value.length - 1) {
                                setValue += ','
                            }
                        })
                    }
                } else if (typeof freeze === 'string') {
                    isChange = freeze !== value
                    if(isChange) {
                        setValue = value
                    }
                }

                if (refreshIds && isChange) {
                    refreshIds.forEach(function (v) {
                        var params = {}
                        v.params.forEach(function (t) {
                            if (g_params[t + 'Key']) {
                                params[g_params[t + 'Key']] = g_params[t + 'Value'] instanceof Array ? g_params[t + 'Value'].join(",") : g_params[t + 'Value']
                            }
                        })
                        params['yili-token'] = $("#yili-token").val()
                        $.ajax({
                            url: $root_path + v.url,
                            type: "post",
                            data: params,
                            success: function (data) {
                                if (data.code == 200) {
                                    var options = data.data
                                }
                                refreshSelects(v.init, refreshSelectState[v.init], v.isSingle, options)
                                if(!!v.notIncludeDisable){
                                    if(g_params[initId + 'Value']&&g_params[initId + 'Value'].indexOf('all')>-1){
                                        $('#'+v.init).multipleSelect('enable')
                                    }else {
                                        if(g_params[initId + 'Value'].indexOf(v.notIncludeDisable)>-1){
                                            $('#'+v.init).multipleSelect('enable')
                                        }else{
                                            $('#'+v.init).multipleSelect('disable')
                                        }
                                    }
                                }
                            }
                        })
                    })
                }

                if(setValue.length) $('#' + initId + 'Hidden').val(setValue)
                g_params[initId + 'Freeze'] = $.extend([], g_params[initId + 'Value'])
            }
        })
        $($id).multipleSelect('checkAll');
        g_params[initId + 'Freeze'] = ['all']
    }

    // 获取传入日期的月份的第一天,用做月报
    function getFirstDay(date) {
        date.setDate(1);
        var month = parseInt(date.getMonth() + 1);
        var day = date.getDate();
        if (month < 10) {
            month = '0' + month
        }
        if (day < 10) {
            day = '0' + day
        }
        return day;
    }

    // 获取传入日期的月份的最后一天,用做月报
    function getLastDay(date) {
        var currentMonth = date.getMonth();
        var nextMonth = ++currentMonth;
        var nextMonthFirstDay = new Date(date.getFullYear(), nextMonth, 1);
        var oneDay = 1000 * 60 * 60 * 24 - 1000;
        var lastTime = new Date(nextMonthFirstDay - oneDay);
        var month = parseInt(lastTime.getMonth() + 1);
        var day = lastTime.getDate();
        if (month < 10) {
            month = '0' + month
        }
        if (day < 10) {
            day = '0' + day
        }
        return day
    }

    $('#search').on('click', function () {
    })

    $('.dmy_btn').on('click', function () {
        var btnList = $('.capsule_btn.dmy_btn')
        for (var c = 0; c < btnList.length; c++) {
            $(btnList[c]).removeClass('active')
        }

        $(this).addClass('active')
        $('#timeWD').val($(this).attr("data-value"))
        jqxTreeGridUtil.init("#treegrid");
    })

    $('.gmv_si_btn').on('click', function () {

        var btnList = $('.capsule_btn.gmv_si_btn')
        var value = $(this).attr('data-value')
        $root_path = $common.rootPath + value
        for (var c = 0; c < btnList.length; c++) {
            $(btnList[c]).removeClass('active')
        }

        $(this).addClass('active')
        var url = value=='gmv'?"gmv/cate/detail":"si/cate/detail"

        $("#titleSpan").html(value=='gmv'?"GMV数据：品类视角明细表":"SI数据：品类视角明细表");
        $("#treegrid").parents("form").attr("action",url)
        initSelect(new Array())
    })
    $('#returnBtn').on('click',function (e) {
        e.preventDefault()
        var baseurl = window.location.href.indexOf('84:8081')>-1?'/yili1/preview.html?yili-token=':'/default/preview.html?yili-token='
        var siUrl = baseurl + $("#yili-token").val()+'#/nv/dashboard//e0162b18714a43c98dbc77ba36a41dbf'
        var gmvUrl = baseurl + $("#yili-token").val()+'#/nv/dashboard//0a5af10e36e74da9893f9524c0becde4'
        window.location.href=$('.gmv_si_btn.active').attr('data-value')=='gmv'?gmvUrl:siUrl
    })
    initDatePicker()
});