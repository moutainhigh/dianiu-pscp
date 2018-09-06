//ctx
var js = document.scripts;
var url = js[js.length - 1].src;

var $ctx = getQueryString(url, 'ctx');

function getQueryString(url, name) {
    var reg = new RegExp("(\\?|&)" + name + "=([^&]*)(&|$)");
    var r = url.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}
//jqGrid的配置信息
$.jgrid.defaults.width = 1000;
$.jgrid.defaults.responsive = true;
$.jgrid.defaults.styleUI = 'Bootstrap';
//工具集合Tools
window.T = {};

// 获取请求参数
// 使用示例
// location.href = http://localhost:8080/index.html?id=123
// T.p('id') --> 123;
var url = function(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
T.p = url;

function gotoCompanyAuth() {
    var timestamp = new Date().getTime();
    top.location.href = '../main.html?t=' + timestamp + '#cp/company.html';
}
//全局配置
$.ajaxSetup({
    dataType: "json",
    contentType: "application/json",
    cache: false,
    complete: function(XMLHttpRequest, textStatus) { //ajax登录超时或者请求超时统一处理
        if (textStatus == "parsererror") {
            alert("登陆超时！请重新登陆！", function() {
                top.location.href = 'login.html';
            });
        } else if (textStatus == "error") {
            var index = parent.layer.alert('请求超时!');
        }
    }
});
//重写alert
window.alert = function(msg, callback) {
    parent.layer.alert(msg, function(index) {
        parent.layer.close(index);
        if (typeof(callback) === "function") {
            callback("ok");
        }
    });
}

//重写confirm式样框
window.confirm = function(msg, callback) {
    parent.layer.confirm(msg, {
            btn: ['确定', '取消']
        },
        function() { //确定事件
            if (typeof(callback) === "function") {
                callback("ok");
            }
        });
}

//选择一条记录
function getSelectedRow() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("只能选择一条记录");
        return;
    }

    return selectedIDs[0];
}

//选择多条记录
function getSelectedRows() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }

    return grid.getGridParam("selarrrow");
}
//获取记录的订单编号
function getSelectedRowOrderId() {
    var grid = $("#jqGrid");
    var rowKey = grid.getGridParam("selrow");
    if (!rowKey) {
        alert("请选择一条记录");
        return;
    }
    var selectedIDs = grid.getGridParam("selarrrow");
    if (selectedIDs.length > 1) {
        alert("只能选择一条记录");
        return;
    }
    var rowData = grid.jqGrid('getRowData', selectedIDs[0]);
    return rowData.orderId;
}
//时间戳转换
function getNowFormatDate(mydate) {
    var date = new Date(mydate);
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var strHours = date.getHours();
    var minutes = date.getMinutes();
    var seconds = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (strHours >= 0 && strHours <= 9) {
        strHours = "0" + strHours;
    }
    if (minutes / 10 < 1) {
        minutes = '0' + minutes;
    }
    if (seconds / 10 < 1) {
        seconds = '0' + seconds;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
    /*var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate +
        " " + strHours + seperator2 + minutes + seperator2 + seconds;*/
    return currentdate;
}
//图片放大
Vue.prototype.imgEnlarge = function(e) {
    var afterSrc = e.target.src;
    var cont = '';
    var last = afterSrc.lastIndexOf('_');
    var imgName = afterSrc.substring(0, last);
    var imgFormat = afterSrc.substring(last + 1, afterSrc.length).split('.')[1];
    var size = afterSrc.substring(last + 1, afterSrc.length).split('.')[0];
    var xSize = size.split('x')[0];
    var ySize = size.split('x')[1];
    if (xSize == ySize) {
        afterSrc = imgName + '.' + imgFormat;
    }
    cont = '<div class="enlargeDiv"><img style="max-width: 700px;max-height: 450px;padding:15px;" src="' + afterSrc + '"></div>';
    layer.open({
        type: 1,
        title: false,
        closeBtn: 2,
        area: ['700px', '450px'],
        content: cont
    })
}
//图片错误
Vue.prototype.setImgError = function(e){
    e.target.src = $ctx + '/statics/img/imgUnlink.png';
}
//防止重复提交
Vue.prototype.preventRepeat = function (e) {
    e.target.disabled = true;
    setTimeout(function(){
        e.target.disabled = false;
    },2000);
}