//ctx获取项目绝对路径
function getQueryString(url, name) {
    var reg = new RegExp("(\\?|&)" + name + "=([^&]*)(&|$)");
    var r = url.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

var js = document.scripts;
var url = js[js.length - 1].src;
var $ctx = getQueryString(url, 'ctx');
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
var url = function (name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
};
T.p = url;

function gotoCompanyAuth(memberType) {
    var timestamp = new Date().getTime();
    sessionStorage.setItem('memberType', memberType);
    top.location.href = '../main.html?t=' + timestamp + '#cp/company.html';
}

//全局配置
$.ajaxSetup({
    dataType: "json",
    contentType: "application/json",
    cache: false,
    complete: function (XMLHttpRequest, textStatus) { //ajax登录超时或者请求超时统一处理
        if (textStatus == "parsererror") {
            alert("登陆超时！请重新登陆！", function () {
                top.location.href = $ctx + '/login.html';
            });
        } else if (textStatus == "error") {
            //var index = parent.layer.alert('请求超时!');
        }
    }
});

axios.interceptors.response.use(function (response) {
    //返回login.html
    if (typeof (response.data) == 'string' && response.data.indexOf('html') != -1) {
        alert('登录超时', function () {
            top.location.href = $ctx + '/login.html';
        })
    }
    return response;
}, function (error) {
    // Do something with response error
    //发生错误后直接返回登录页
    alert('请求超时', function () {
        top.location.href = $ctx + '/login.html';
    });
    return Promise.reject(error);
});


//重写toFixed方法
Number.prototype.toFixed = function (d) {
    var s = this + "";
    if (!d) d = 0;
    if (s.indexOf(".") == -1) s += ".";
    s += new Array(d + 1).join("0");
    if (new RegExp("^(-|\\+)?(\\d+(\\.\\d{0," + (d + 1) + "})?)\\d*$").test(s)) {
        var s = "0" + RegExp.$2,
            pm = RegExp.$1,
            a = RegExp.$3.length,
            b = true;
        if (a == d + 2) {
            a = s.match(/\d/g);
            if (parseInt(a[a.length - 1]) > 4) {
                for (var i = a.length - 2; i >= 0; i--) {
                    a[i] = parseInt(a[i]) + 1;
                    if (a[i] == 10) {
                        a[i] = 0;
                        b = i != 1;
                    } else break;
                }
            }
            s = a.join("").replace(new RegExp("(\\d+)(\\d{" + d + "})\\d$"), "$1.$2");

        }
        if (b) s = s.substr(1);
        return (pm + s).replace(/\.$/, "");
    }
    return this + "";
};
//重写alert
window.alert = function (msg, callback) {
    parent.layer.alert(msg, function (index) {
        parent.layer.close(index);
        if (typeof (callback) === "function") {
            callback("ok");
        }
    });
}

//重写confirm式样框
window.confirm = function (msg, callback) {
    parent.layer.confirm(msg, {
            btn: ['确定', '取消']
        },
        function () { //确定事件
            if (typeof (callback) === "function") {
                callback("ok");
            }
        });
}
//layer全局配置 浏览器不滚动
layer.config({
    scrollbar: false
});

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

//获取记录的订单编号
function getSelectedRowMemberId() {
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
    return rowData.memberId;
}

//获取记录的电工编号
function getSelectedRowElectricianId() {
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
    return rowData.electricianId;
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

//时间戳转换
function getNowFormatDate(mydate) {
    if (typeof (mydate) == 'string') {
        mydate = mydate.replace(/-/g, '/');
    }
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

//金额格式
function formatCurrency(num) {
    num = num.toString().replace(/\$|\,/g, '');
    if (isNaN(num))
        num = "0";
    sign = (num == (num = Math.abs(num)));
    num = Math.floor(num * 100 + 0.50000000001);
    cents = num % 100;
    num = Math.floor(num / 100).toString();
    if (cents < 10)
        cents = "0" + cents;
    for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
        num = num.substring(0, num.length - (4 * i + 3)) + ',' +
            num.substring(num.length - (4 * i + 3));
    return (((sign) ? '' : '-') + num + '.' + cents);
}

//图片放大
Vue.prototype.imgEnlarge = function (e) {
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
        scrollbar: false,
        closeBtn: 2,
        area: ['700px', '450px'],
        content: cont
    })
}
//防止重复提交
Vue.prototype.preventRepeat = function (e) {
    e.target.disabled = true;
    setTimeout(function () {
        e.target.disabled = false;
    }, 2000);
}
//封装社会工单支付
Vue.prototype.socialWorkorderPay = function (data) {
    $.ajax({
        url: $ctx + '/cp/payment/start',
        type: 'post',
        data: JSON.stringify(data),
        success: function (r) {
            if (r.code == 0) {
                vm.walletAmount = r.result.walletAmount;
                vm.payTypeList = r.result.payTypeInfos;
                vm.payAmount = r.result.payAmount;
                for (var i = 0; i < vm.payTypeList.length; i++) {
                    if (vm.payTypeList[i].status == 1 && vm.payTypeList[i].disabled == 0) {
                        vm.choosePayType(vm.payTypeList[i].id);
                        break;
                    }
                }
                switch (data.type) {
                    case 'socialWorkorderPay':
                        data.socialWorkOrderIds = data.ids;
                        break;
                }
            } else {
                alert(r.msg, function () {
                    vm.reload();
                });
                return;
            }
            //请求的Url
            var paymentUrl = '';
            var cont = '';
            switch (data.type) {
                case 'socialWorkorderPay':
                    paymentUrl = '/cp/workorder/social/payment';
                    cont = '#payTypeList';
                    break;
                case 'getSocialWorkorder':
                    paymentUrl = '/cp/workorder/chieforder/payment';
                    cont = '#payType';
                    break;
                case 'socialWorkorderBill':
                    paymentUrl = '/cp/workorder/social/payment';
                    cont = '#payType';
                    break;
                case 'requirementPay':
                    paymentUrl = '/cp/needs/order/payment';
                    cont = '#payType';
                    break;
            }
            //加锁，防止重复提交
            var lock = false;
            //弹出支付类型
            var index = layer.open({
                type: 1,
                title: '选择付款方式',
                area: ['300px', '270px'],
                closeBtn: 2,
                btn: ['确认支付', '取消'],
                yes: function (index, layero) {
                    var load = layer.load(0, {
                        shade: [0.3, '#fff']
                    });
                    //解锁
                    if (!lock) {
                        lock = true;
                        data.payType = vm.payType;
                        $.ajax({
                            type: 'post',
                            url: $ctx + paymentUrl,
                            data: JSON.stringify(data),
                            complete: function () {
                                lock = false;
                                layer.close(load);
                            },
                            success: function (r) {
                                if (r.code == 0) {
                                    if (r.result.payType === 1 || r.result.payType === 2) {
                                        if ($('#myform')) {
                                            $('#myform').remove();
                                        }
                                        var str = r.result.thirdPartyPaymentInfo;
                                        var newUrl = str.replace(/\"/g, "'");
                                        var myForm = ['<form id="myform" name="punchout_form" method="post" action="' + r.result.redirectUrl + '" target="_blank">',
                                            '    <input type="hidden" name="amount" value="' + r.result.amount + '">',
                                            '    <input type="hidden" name="orderId" value="' + r.result.orderId + '">',
                                            '    <input type="hidden" name="orderType" value="' + r.result.orderType + '">',
                                            '    <input type="hidden" name="payType" value="' + r.result.payType + '">',
                                            '    <input type="hidden" name="thirdPartyPaymentInfo" value="' + newUrl + '">',
                                            '</form>'
                                        ].join("");

                                        var form = $(myForm);
                                        $(document.body).append(form);
                                        layer.close(index);
                                        $('#myform')[0].submit();
                                        var myDiv = ['<div style="position: fixed;width: 100%;height: 100%;z-index: 9999;background: rgba(0,0,0,.3);top: 0;left: 0;text-align: center;">',
                                            '       <div style="margin: 25% auto;">',
                                            '           <button type="button" class="btn btn-primary" onclick="location.reload();">支付成功</button>&nbsp;&nbsp;&nbsp;',
                                            '           <button type="button" class="b tn btn-warning" onclick="location.reload();">支付失败</button>',
                                            '       </div>',
                                            '   </div>'
                                        ].join("");

                                        switch (data.type) {
                                            case 'socialWorkorderPay':
                                                break;
                                            case 'getSocialWorkorder':
                                                myDiv = ['<div style="position: fixed;width: 100%;height: 100%;z-index: 9999;background: rgba(0,0,0,.3);top: 0;left: 0;text-align: center;">',
                                                    '       <div style="margin: 25% auto;">',
                                                    '           <button type="button" class="btn btn-primary" onclick="window.top.location.href = $ctx + \'/main.html#cp/workorder/social/index.html\'">支付成功</button>&nbsp;&nbsp;&nbsp;',
                                                    '           <button type="button" class="btn btn-warning" onclick="window.top.location.href = $ctx + \'/main.html#cp/workorder/social/index.html\'">支付失败</button>',
                                                    '       </div>',
                                                    '   </div>'
                                                ].join("");
                                                break;
                                            case 'socialWorkorderBill':
                                                break;
                                            case 'requirementPay':
                                                break;
                                        }
                                        var div = $(myDiv);
                                        $(document.body).append(div);
                                    } else {
                                        alert('支付成功！', function () {
                                            switch (data.type) {
                                                case 'socialWorkorderPay':
                                                    layer.close(index);
                                                    vm.reload();
                                                    break;
                                                case 'getSocialWorkorder':
                                                    sessionStorage.setItem('fromPay', 1);
                                                    window.top.location.href = $ctx + '/main.html#cp/workorder/social/index.html';
                                                    break;
                                                case 'socialWorkorderBill':
                                                    layer.close(index);
                                                    vm.reload();
                                                    break;
                                                case 'requirementPay':
                                                    layer.close(index);
                                                    vm.reload();
                                                    //vm.paySuccess = true;
                                                    break;
                                            }
                                        });
                                    }
                                } else {
                                    alert(r.msg, function () {
                                        switch (data.type) {
                                            case 'socialWorkorderPay':
                                                layer.close(index);
                                                vm.reload();
                                                break;
                                            case 'getSocialWorkorder':
                                                window.top.location.href = $ctx + '/main.html#cp/workorder/social/index.html';
                                                break;
                                            case 'socialWorkorderBill':
                                                vm.reload();
                                                break;
                                            case 'requirementPay':
                                                vm.reload();
                                                break;
                                        }
                                    });
                                    return;
                                }
                            }
                        });
                    }

                },
                cancel: function () {
                    switch (data.type) {
                        case 'socialWorkorderPay':
                            vm.payType = 0;
                            break;
                        case 'getSocialWorkorder':
                            alert('招募工单已保存，请到工单未支付页面支付', function () {
                                window.top.location.href = $ctx + '/main.html#cp/workorder/social/index.html';
                            });
                            break;
                        case 'socialWorkorderBill':
                            alert('本次支付未完成', function () {
                                vm.reload();
                            })
                            break;
                        case 'requirementPay':
                            alert('本次支付未完成', function () {
                                vm.reload();
                            })
                            break;
                    }
                },
                btn2: function () {
                    switch (data.type) {
                        case 'socialWorkorderPay':
                            vm.payType = 0;
                            break;
                        case 'getSocialWorkorder':
                            alert('招募工单已保存，请到工单未支付页面支付', function () {
                                window.top.location.href = $ctx + '/main.html#cp/workorder/social/index.html';
                            });
                            break;
                        case 'socialWorkorderBill':
                            alert('本次支付未完成', function () {
                                vm.reload();
                            })
                            break;
                        case 'requirementPay':
                            alert('本次支付未完成', function () {
                                vm.reload();
                            })
                            break;
                    }
                },
                content: jQuery(cont)
            });
        }
    });
}

//图片组件
var imgstr = ['<div>',
    '    <div>',
    '        <ul class="upload-ul" v-show="showImg" style="display:inline-block;">',
    '            <li class="upload-li" v-for="(item,index) in data">',
    '                <img :src="item.fid" class="upload-img" @click="imgEnlarge" />',
    '                <a class="img-remove-btn" @click="delImg(index)">',
    '                <span class="glyphicon glyphicon-remove"></span>',
    '            </a>',
    '            </li>',
    '        </ul>',
    '        <button v-show="uploadAble" type="button" class="upload-btn" @click="addImg"></button>',
    '        <input :id="id" type="file" @change="onFileChange" v-show="false" ref="file" />',
    '    </div>',
    '</div>'
].join("");

Vue.component('img-upload', {
    template: imgstr,
    props: ['id', 'data', 'length'],
    computed: {
        uploadAble: function () {
            if (this.isArray(this.data)) {
                return this.data.length < this.length;
            } else {
                return false;
            }
        },
        showImg: function () {
            if (this.isArray(this.data)) {
                return this.data.length > 0;
            } else {
                return false;
            }
        }
    },
    methods: {
        addImg: function () {
            this.$refs.file.click();
        },
        onFileChange: function (e) {
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            this.createImage(files);
        },
        createImage: function (files) {

            var self = this;
            if (typeof FileReader === 'undefined') {
                alert('您的浏览器不支持图片上传，请升级您的浏览器');
                return;
            }
            if (files[0].type.indexOf('image') == -1) {
                alert('仅支持图片上传');
                return;
            }
            var size = files[0].size / 1024 / 1024;
            if (size > 2) {
                alert('图片不能超过2MB!')
                return;
            }
            var leng = files.length;
            if (leng > 0) {
                var reader = new FileReader();
                reader.readAsDataURL(files[0]);
                reader.onload = function (e) {
                    self.data.push({
                        'fid': e.target.result
                    });
                };
            } else {
                return;
            }
            this.$refs.file.value = '';
        },
        delImg: function (index) {
            this.data.splice(index, 1);
            this.$refs.file.value = '';
        },
        isArray: function (o) {
            return Object.prototype.toString.call(o) == '[object Array]';
        }
    }
});


var imgstrX = ['<div>',
    '    <div>',
    '        <ul class="upload-ul" v-show="showImgX" style="display:inline-block;">',
    '            <li class="upload-li" v-for="(item,index) in data">',
    '                <img :src="item.fid" class="upload-img" @click="imgEnlarge" />',
    '                <a class="img-remove-btn" @click="delImgX(index)">',
    '                <span class="glyphicon glyphicon-remove"></span>',
    '            </a>',
    '            </li>',
    '        </ul>',
    '        <button v-show="uploadAbleX" type="button" class="upload-btn" @click="addImgX"></button>',
    '        <input :id="id" type="file" @change="onFileChangeX" v-show="false" ref="file" />',
    '    </div>',
    '</div>'
].join("");

Vue.component('img-uploadx', {
    template: imgstrX,
    props: ['id', 'data', 'length'],
    computed: {
        uploadAbleX: function () {
            if (this.isArrayX(this.data)) {
                return this.data.length < this.length;
            } else {
                return false;
            }
        },
        showImgX: function () {
            if (this.isArrayX(this.data)) {
                return this.data.length > 0;
            } else {
                return false;
            }
        }
    },
    methods: {
        addImgX: function () {
            this.$refs.file.click();
        },
        onFileChangeX: function (e) {
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            this.createImageX(files);
        },
        createImageX: function (files) {
            var self = this;
            if (typeof FileReader === 'undefined') {
                alert('您的浏览器不支持图片上传，请升级您的浏览器');
                return;
            }
            if (files[0].type.indexOf('image') == -1) {
                alert('仅支持图片上传');
                return;
            }
            var size = files[0].size / 1024 / 1024;
            if (size > 2) {
                alert('图片不能超过2MB!')
                return;
            }
            var leng = files.length;
            if (leng > 0) {
                var reader = new FileReader();
                reader.readAsDataURL(files[0]);
                reader.onload = function (e) {
                    self.data.push({
                        'fid': e.target.result
                    });
                    // console.log(self.data)
                };
            } else {
                return;
            }
            this.$refs.file.value = '';
        },
        delImgX: function (index) {
            this.data.splice(index, 1);
            this.$refs.file.value = '';
        },
        isArrayX: function (o) {
            return Object.prototype.toString.call(o) == '[object Array]';
        }
    }
});