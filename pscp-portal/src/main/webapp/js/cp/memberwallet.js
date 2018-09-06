$(function () {
    $("#jqGrid").jqGrid({
        url: '../walletDetail/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', width: 50, key: true, hidden: true},
            {label: '交易号', name: 'orderId', width: 80},
            {label: '金额(元)', name: 'amount', width: 80},
            {label: '类型', name: 'type', width: 80},
            {label: '发起时间', name: 'dealTime', width: 80}, {
                label: '状态',
                name: 'createUser',
                width: 80,
                formatter: function (value, options, row) {
                    var type = row.type;
                    var status = "";
                    var span = '<span class="label label-warning">';
                    if (type == '提现') {
                        if (row.payStatus == 0) {
                            status = "审核";
                        }
                        if (row.checkStatus == 0) {
                            status = "打款";
                        }
                        if (row.payStatus != 0 && row.checkStatus != 0) {
                            if (row.payStatus == 1) {
                                status = "提现成功";
                                span = '<span class="label label-success">';
                            } else {
                                status = "提现失败";
                                span = '<span class="label label-danger">';
                            }
                        }
                        if (row.payStatus == 0 && row.checkStatus != 0) {
                            if (row.checkStatus == 1) {
                                status = "审核成功";
                                span = '<span class="label label-success">';
                            } else {
                                status = "审核失败";
                                span = '<span class="label label-danger">';
                            }
                        }
                        if (row.payStatus == 0 && row.checkStatus == 0) {
                            status = "审核中";
                        }
                    } else {
                        status = "成功";
                        span = '<span class="label label-success">';
                    }
                    var label = span + status + '</span>';
                    return label;
                }
            }, {
                label: '明细',
                name: 'id',
                width: 80,
                formatter: function (value, options, row) {
                    return '<span class="btn btn-primary btn-xs" onclick="getDetail(\'' + value + '\')">查看</span>';
                }
            }
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showListWithdraw: true,
        showListRecharge: true,
        showListPay: true,
        title: null,
        wait: 60,
        memberWallet: {
            type: '',
            fundTarget: null,
            fundSource: null
        },
        amount: {
            amount: null,
            freezingAmount: null
        },
        withdrawInfo: {
            amount: null,
            mobile: null,
            msgCode: null,
            msgcodeid: null
        },
        rechargeInfo: {
            amount: null,
            payType: 1,
            orderType: 1,
            payMethod: 'PC'
        },
        payTypeList: [],
        payType: 'alipay'
    },
    methods: {
        query: function () {
            vm.reload();
        },
        getInfo: function (id) {
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = '我的账单';
            $.get("../walletDetail/info/" + id, function (r) {
                if (r.code == 0) {
                    vm.memberWallet = r.memberWallet;
                } else {
                    alert(r.msg);
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.showListWithdraw = true;
            vm.showListRecharge = true;
            vm.withdrawInfo.msgcodeid = null;
            vm.withdrawInfo.msgCode = null;
            vm.withdrawInfo.amount = null;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        withdraw: function () {
            this.showListWithdraw = false;
            vm.title = '提现';
        },
        toRecharge: function () {
            var data = {};
            data.orderType = 1;
            data.orderIds = '';
            $.ajax({
                url: $ctx + '/cp/payment/start',
                type: 'post',
                data: JSON.stringify(data),
                success: function (r) {
                    if (r.code == 0) {
                        vm.payTypeList = r.result.payTypeInfos;
                        vm.showListRecharge = false;
                        vm.title = '充值';
                    } else {

                    }
                }
            });
        },
        toPay: function () {
            var regular = /^[0-9]*(\.[0-9]{1,2})?$/;
            if (!vm.rechargeInfo.amount) {
                alert('金额不能为空!');
                return;
            } else if (!regular.test(vm.rechargeInfo.amount)) {
                alert('请输入正确的金额!');
                return;
            } else if (vm.rechargeInfo.amount <= 0) {
                alert('金额不能小于0!');
                return;
            }
            var data = vm.rechargeInfo;
            $.ajax({
                type: 'post',
                url: '../walletDetail/recharge',
                data: JSON.stringify(data),
                async: false,
                success: function (r) {
                    if (r.code == 0) {
                        if (r.payType === 1 || r.payType === 2) {
                            if ($('#myform')) {
                                $('#myform').remove();
                            }
                            var str = r.params;
                            var newUrl = str.replace(/\"/g, "'");
                            var myForm = ['<form id="myform" name="punchout_form" method="post" action="' + r.returnUrl + '" target="_blank">',
                                '    <input type="hidden" name="amount" value="' + r.amount + '">',
                                '    <input type="hidden" name="orderId" value="' + r.orderId + '">',
                                '    <input type="hidden" name="orderType" value="' + r.orderType + '">',
                                '    <input type="hidden" name="payType" value="' + r.payType + '">',
                                '    <input type="hidden" name="thirdPartyPaymentInfo" value="' + r.params + '">',
                                '</form>'
                            ].join("");
                            var form = $(myForm);
                            $(document.body).append(form);
                            $('#myform')[0].submit();
                            var myDiv = ['<div style="position: fixed;width: 100%;height: 100%;z-index: 9999;background: rgba(0,0,0,.3);top: 0;left: 0;text-align: center;">',
                                '       <div style="margin: 25% auto;">',
                                '           <button type="button" class="btn btn-primary" onclick="location.reload();">支付成功</button>&nbsp;&nbsp;&nbsp;',
                                '           <button type="button" class="b tn btn-warning" onclick="location.reload();">支付失败</button>',
                                '       </div>',
                                '   </div>'
                            ].join("");
                            var div = $(myDiv);
                            $(document.body).append(div);
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        ackwithdrawals: function () {
            var amount = vm.withdrawInfo.amount;
            var total = vm.amount.amount;
            var numOftotal = total.replace(new RegExp(',', "g"), '');//将所有的逗号替换成""
            if (amount > numOftotal) {
                alert("提现金额超过可用余额！");
                return;
            } else {
                $.ajax({
                    url: '../walletDetail/save',
                    type: 'post',
                    dataType: 'json',
                    data: JSON.stringify(vm.withdrawInfo),
                    async: false,
                    success: function (r) {
                        if (r.code == 0) {
                            alert("操作成功", function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }

        },
        getMsgCode: function (e) {
            e = e || window.event;
            var regMobile = /^1[3|4|5|7|8][0-9]\d{8}$/;
            if (regMobile.test(vm.withdrawInfo.mobile)) {
                $.ajax({
                    url: '../sys/user/getMsgCode',
                    type: 'post',
                    dataType: 'json',
                    contentType: "application/x-www-form-urlencoded",
                    data: {mobile: vm.withdrawInfo.mobile, type: 2},
                    async: false,
                    success: function (r) {
                        var obj = e.target;
                        if (r.success) {
                            vm.withdrawInfo.msgcodeid = r.msg;
                            vm.time(obj);
                        }
                    }
                });
            } else {
                alert('请输入正确的手机号', function () {
                    return false;
                });
                return;
            }

        },
        time: function (obj) {
            if (vm.wait == 0) {
                obj.removeAttribute('disabled');
                obj.innerText = "获取验证码";
                vm.wait = 60;
            } else {
                obj.setAttribute('disabled', true);
                obj.innerText = '请等待' + vm.wait + "秒..";
                vm.wait--;
                setTimeout(function () {
                        vm.time(obj);
                    },
                    1000);
            }
        },
        choosePayType: function (type) {
            vm.payType = type;
            switch (type) {
                case 'alipay':
                    vm.rechargeInfo.payType = 1;
                    break;
                case 'wxpay':
                    vm.rechargeInfo.payType = 2;
                    break;
            }
        }
    },
    created: function () {
        var self = this;
        $.ajax({
            url: '../walletDetail/preWithdrawals',
            type: 'post',
            dataType: 'json',
            success: function (r) {
                if (r.code == 0) {
                    self.amount = r.result;
                    if (r.result.amount != null && r.result.amount != '') {
                        self.amount.amount = formatCurrency(r.result.amount);
                    } else {
                        self.amount.amount = formatCurrency(0);
                    }
                    if (r.result.freezingAmount != null && r.result.amount != '') {
                        self.amount.freezingAmount = formatCurrency(r.result.freezingAmount);
                    } else {
                        self.amount.freezingAmount = formatCurrency(0);
                    }
                    self.withdrawInfo.mobile = r.mobile;
                }
            }
        });
    }
});

function getDetail(id) {
    vm.getInfo(id);
}