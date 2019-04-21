/**
 * 通用方法
 * 设置base标签根路径
 *
 */
;var $common = (function () {
    "use strict";

    //获取项目根目录
    function rootPath() {
        var strFullPath = window.document.location.href;
        var strPath = window.document.location.pathname;
        if (strPath === "/") {
            return (strFullPath);
        }
        var pos = strFullPath.indexOf(strPath);
        var prePath = strFullPath.substring(0, pos);
        var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
        return (prePath + postPath + "/");
    }

    $("base").attr("href", rootPath);

    //合并两个对象{name:value},{name1:value1} 结果{name:value,name1:value1}
    var merge = function (obj1, obj2) {
        var obj3 = {};
        for (var attrname in obj1) {
            obj3[attrname] = obj1[attrname];
        }
        for (var attrname in obj2) {
            obj3[attrname] = obj2[attrname];
        }
        return obj3;
    };
    var objValueToArr = function (obj) {
        var arr = [];
        for (var i in obj) {
            arr.push(obj[i]);
        }
        return arr;
    };
    //格式化后台返回数字类型,
    var formatFloat = function (src, pos) {
        try {
            //如果是空值或是null则返回--,
            if (src == null || src == "NULL" || src.length == 0) {
                return "";
            }
            src += "";
            //如果是0直接返回0,
            if (src == "0")
                return "0";
            if (src.indexOf('E') != -1) src = new Number(src);
            //如果是数字包括负数则根据条件保留指定位数小数并添加千分符号,默认不保留小数
            var re = /^[-]?[0-9]+.?[0-9]*$/;
            if (!re.test(src)) {
                return src;
            }
            else {
                if (pos) {
                    return ((parseFloat(src).toFixed(pos)).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,'));
                } else {
                    return ((parseFloat(src).toFixed(0)).replace(/\d{1,3}(?=(\d{3})+(\.\d*)?$)/g, '$&,'));
                }
            }
        } catch (e) {
            //如果出错返回原值
            return src;
        }
    }


    // 截取字符串的
    //重载截取字符串方法,一个汉字展2个字符,保证截取字符串长度大致等长
    function subString(str, len, hasDot) {
        if (!str)
            return "-";
        var newLength = 0;
        var newStr = "";
        var chineseRegex = /[^\x00-\xff]/g;
        var singleChar = "";
        var strLength = str.replace(chineseRegex, "**").length;
        for (var i = 0; i < strLength; i++) {
            singleChar = str.charAt(i).toString();
            if (singleChar.match(chineseRegex) != null) {
                newLength += 2;
            }
            else {
                newLength++;
            }
            if (newLength > len) {
                break;
            }
            newStr += singleChar;
        }

        if (hasDot && strLength > len) {
            newStr += "...";
        }
        return newStr;
    }

    // 判断某个对象 里是否有某个属性
    function isHave(obj, key) {
        if (obj && obj[key]) {
            return obj[key];
        } else {
            return "";
        }
    }

    function formatRate(data, isMul100) {//默认保留两位小数

        if (data == '0' || data == "NaN" || data == NaN) return (parseFloat(0).toFixed(2) + ' %');
        if (data == null || data == "") return '';
        if (isMul100) {
            return ((parseFloat(data) * 100).toFixed(2) + ' %');
        }

        return (parseFloat(data).toFixed(2) + ' %');
    }

    function formatURLByHttp(url, href) {
        if (url) {
            if (url.indexOf('http') == -1) {
                if (href && href.indexOf('brand.jd.com/pinpai') != -1) {
                    url = '//brand.jd.com/pinpai/' + url + '.html';
                } else {
                    url = '//' + url;
                }
            }
        }
        return url;
    }

    function formatNumToY(num, flag) {
        function isInt(number) {
            return parseInt(number) == number;
        }

        function isFixedX(num) {
            if (flag || (flag == 0) || (flag == '0')) {

                try {
                    return num.toFixed(Number(flag));
                } catch (e) {
                    return num;
                }
            }
            return num;
        }

        if (Math.abs(num) >= 1000) {
            return isInt(num / 1000) ? num / 1000 + " 千" : isFixedX(num);
        } else if (Math.abs(num) >= 100) {
            return isInt(num / 100) ? num / 100 + " 百" : isFixedX(num);
        } else if (Math.abs(num) >= 10) {
            return isInt(num / 10) ? num / 10 + " 十" : isFixedX(num);
        } else {
            return isFixedX(num);
        }
    }

    //格式化 y轴数值
    var formatY = function (value, isFixed) {
        function isFixedX(num) {
            if (isFixed || (isFixed == 0) || (isFixed == '0')) {
                return Number(num).toFixed(Number(isFixed));
            }
            return num;
        }

        if (Math.abs(value) >= 100000000) {
            return ((value / 100000000) > 100) ? (formatNumToY(value / 100000000, isFixed) + '亿') : (isFixedX(value / 100000000) + ' 亿');
        } else if (Math.abs(value) >= 10000) {
            return ((value / 10000) > 100) ? (formatNumToY(value / 10000, isFixed) + '万') : (isFixedX(value / 10000) + ' 万');
        } else if (Math.abs(value) < 10000) {
            return (Math.abs(value) >= 100) ? formatNumToY(value, isFixed) : isFixedX(value);
        } else return formatNumToY(value, isFixed);
    }
    //格式化 W
    var formatW = function (value, isFixed) {
        function isFixedX(num) {
            if (isFixed || (isFixed == 0) || (isFixed == '0')) {
                return Number(num).toFixed(Number(isFixed));
            }
            return num;
        }

        if (Math.abs(value) >= 10000) {
            return ((value / 10000) > 100) ? (formatFloat(formatNumToY(value / 10000, isFixed)) + '万') : (isFixedX(value / 10000) + ' 万');
        } else if (Math.abs(value) < 10000) {
            return (Math.abs(value) >= 100) ? formatFloat(formatNumToY(value, isFixed)) : isFixedX(value);
        } else return formatFloat(formatNumToY(value, isFixed));
    }

    //格式化 金额数值  value=123456789 isFixed=2 格式为 1.23亿
    var formatMoney = function (value, isFixed) {
        if ((!value) && (value != 0)) {
            return "-";
        }

        function isFixedX(num) {
            if (isFixed || (isFixed == 0) || (isFixed == '0')) {
                return num.toFixed(Number(isFixed));
            }
            return num;
        }

        if (Math.abs(value) >= 100000000) {
            return ((value / 100000000) > 100) ? (formatFloat(value / 100000000, isFixed) + '亿') : (isFixedX(value / 100000000) + '亿');
        } else if (Math.abs(value) >= 10000) {
            return ((value / 10000) > 100) ? (formatFloat(value / 10000, isFixed) + '万') : (isFixedX(value / 10000) + '万');
        } else if (Math.abs(value) < 10000) {
            return (Math.abs(value) >= 100) ? formatFloat(isFixedX(value), isFixed) : isFixedX(value);
        } else return (value, isFixed);
    }
    var getMaxByKey = function (data, key) {
        var max = 0;
        for (var i = 0; i < data.length; i++) {
            if (data[i][key] > max) {
                max = data[i][key];
            }
        }
        return max;
    }


    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    var formatDate = function (fmt, date) { //author:
        if (fmt) {
            var _this = date ? new Date(date) : new Date();
            var o = {
                "M+": _this.getMonth() + 1,                 //月份
                "d+": _this.getDate(),                    //日
                "h+": _this.getHours(),                   //小时
                "m+": _this.getMinutes(),                 //分
                "s+": _this.getSeconds(),                 //秒
                "q+": Math.floor((_this.getMonth() + 3) / 3), //季度
                "S": _this.getMilliseconds()             //毫秒
            };
            if (/(y+)/.test(fmt))
                fmt = fmt.replace(RegExp.$1, (_this.getFullYear() + "").substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(fmt))
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            return fmt;
        } else {
            return date;
        }

    }

    //data [{"x": 7, "y": 54481.0}]   xAxis=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15] x轴显示 opt 配置项 x y 在map里对应的key
    //格式化数据 补全数据 通过Key
    var formatDataByX = function (data, xAxis, opt) {

        //var xAxis=[1,2,3,4,5,6,7,8,9,10,11,12,13,14,15];
        //var data = [{"x": 7, "y": 54481.0}];

        var opts = {
            "x": "x",
            "y": "y",
            "str": ""
        };
        opts = $.extend(opts, opt);

        var getArrByLength = function (length) {
            var arr = [];
            for (var i = 0; i < length; i++) {
                arr.push('-');
            }
            return arr;
        }

        var str = opt.str ? opt.str : '';
        //生成series 配置数组
        var seriesData = getArrByLength(xAxis.length);
        if (data.length) {
            for (var k = 0; k < xAxis.length; k++) {
                $.each(data, function (idex, obj) {
                    if ($common.formatDate(str, (obj[opts.x])) == xAxis[k]) {
                        seriesData[k] = obj[opts.y];
                        return;
                    }
                });
            }
        }
        return seriesData;
    }

    var formatNumToUnit = function (num, fixed) {

        if (Math.abs(num) >= 100000000) {
            return (Number(num) / 100000000).toFixed(fixed) + "亿";
        } else if (Math.abs(num) >= 10000000) {
            return (Number(num) / 10000000).toFixed(fixed) + "千万";
        } else if (Math.abs(num) >= 1000000) {
            return (Number(num) / 1000000).toFixed(fixed) + "百万";
        } else if (Math.abs(num) >= 100000) {
            return (Number(num) / 100000).toFixed(fixed) + "十万";
        } else if (Math.abs(num) >= 10000) {
            return (Number(num) / 10000).toFixed(fixed) + "万";
        } else if (Math.abs(num) >= 1000) {
            return (Number(num) / 1000).toFixed(fixed) + "千";
        } else if (Math.abs(num) >= 100) {
            return (Number(num) / 100).toFixed(fixed) + "百";
        } else if (Math.abs(num) >= 10) {
            return Number(num);
        } else {
            return isFixedX(num);
        }
    }
    var formatNumToIntFixed = function (num, fixed) {

        if (Math.abs(num) >= 100000000) {
            return (Number(num) / 100000000).toFixed(fixed) * 100000000;
        } else if (Math.abs(num) >= 10000000) {
            return (Number(num) / 10000000).toFixed(fixed) * 10000000;
        } else if (Math.abs(num) >= 1000000) {
            return (Number(num) / 1000000).toFixed(fixed) * 1000000;
        } else if (Math.abs(num) >= 100000) {
            return (Number(num) / 100000).toFixed(fixed) * 100000;
        } else if (Math.abs(num) >= 10000) {
            return (Number(num) / 10000).toFixed(fixed) * 10000;
        } else if (Math.abs(num) >= 1000) {
            return (Number(num) / 1000).toFixed(fixed) * 1000;
        } else if (Math.abs(num) >= 100) {
            return (Number(num) / 100).toFixed(fixed) * 100;
        } else if (Math.abs(num) >= 10) {
            return Number(num);
        } else {
            return isFixedX(num);
        }
    }

    var formatRateToNum = function (num) {
        if (num == null || num == "NULL" || num.length == 0) {
            return "";
        } else {
            return new Number(num.replace(/%/, ""));
        }
    }


    var formatThousandsToNum = function (num) {


        num = num.replace(/,/gi, '');

        return new Number(num);

    }
    //获取上个月
    var getPreMonth = function (date) {
        var arr = date.split('-');
        var curYear = arr[0]; //获取当前日期的年份
        var curMonth = arr[1]; //获取当前日期的月份
        var curDay = arr[2]; //获取当前日期的日
        var days = new Date(curYear, curMonth, 0);
        days = days.getDate(); //获取当前日期中月的天数
        var stopYear = curYear;
        var stopMonth = parseInt(curMonth) - 1;
        if (stopMonth == 0) {
            stopYear = parseInt(stopYear) - 1;
            stopMonth = 12;
        }
        if (stopMonth < 10) {
            stopMonth = '0' + stopMonth;
        }
        var stopDate = stopYear + '-' + stopMonth;
        return stopDate;
    }

    var isEmptyObject = function (obj) {

        for (var key in obj) {
            return false
        }
        return true
    }

    var getUrlParam = function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var str = window.location.href.substr(window.location.href.indexOf('?') + 1)
        var r = decodeURI(str).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    }

    return {
        getPreMonth: getPreMonth,
        formatFloat: formatFloat,
        formatDate: formatDate,
        formatM: formatMoney,
        formatRate: formatRate,
        objValueToArr: objValueToArr,
        formatRateToNum: formatRateToNum,
        formatThousandsToNum: formatThousandsToNum,
        isEmptyObject: isEmptyObject,
        rootPath: rootPath(),
        getUrlParam: getUrlParam
    }
})();
