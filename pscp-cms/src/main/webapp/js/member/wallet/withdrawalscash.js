Vue.prototype.gridParam = {
    url: '../wallet/withdrawalscash/list',
    postData: { status: 0 },
    datatype: "json",
    colModel: [
        { label: 'id', name: 'id', key: true, width: 50,hidden: true },
        { label: '订单编号', name: 'orderId' },
        { label: '客户姓名', name: 'memberName', width: 60 },
        { label: '手机号码', name: 'mobile' }, 
        {   label: '支付方式',
            name: 'fundTarget',
            formatter: function(value, options, row) {
                var lable = "";
                if (value >= 1000) {
                    label = row.bankName;
                }
                if (value == 1) {
                    label = "支付宝";
                }
                return label;
            }
        }, 
        {
            label: '支付账号',
            name: 'bankAccount',
            formatter: function(value, options, row) {
                if (row.fundTarget == 1) {
                    return row.dealAccount;
                } else {
                    return value;
                }
            }
        },
        { label: '开户行', name: 'bankBranchName' },
        { label: '提现金额(元)', name: 'amount', width: 90,formatter: "number", formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:2}},
        { label: '账户余额(元)', name: 'availableAmount', width: 90,formatter: "number", formatoptions: {thousandsSeparator:",", defaulValue:"",decimalPlaces:2} },
        { label: '提现时间', name: 'createTime' }, 
        {
            label: '操作',
            name: 'id',
            width: 50,
            formatter: function(value, options, row) {
                return '<a class="btn btn-primary btn-xs" onclick="withdrawcashAudit(' + value + ')">审核</a>';
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
    gridComplete: function() {
        //隐藏grid底部滚动条
        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
    }
}
var myTabs = ['<div>',
    '   <ul class="nav nav-tabs">',
    '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTap">',
    '           <a @click="selectedTab(tab)"><i :class="\'fa \' + tab.icon"></i>{{ tab.name }}</a>',
    '       </li>',
    '   </ul>',
    '   <div class="tab-detail">',
    '       <slot></slot>',
    '   </div>',
    '</div>'
].join("");

var myTab = ['<div>',
    '</div>'
].join("");

Vue.component('tabs', {
    template: myTabs,
    data: function() {
        return {
            tabs: []
        };
    },
    created: function() {
        this.tabs = this.$children;
    },
    methods: {
        selectedTab: function(_selectedTab) {
            this.tabs.forEach(function(tab) {
                tab.isActive = tab.name === _selectedTab.name;
            });
        }
    }
})

Vue.component('tab', {
    props: {
        status: {
            required: true
        },
        name: {
            required: true
        },
        icon: {
            required: true
        },
        selected: {
            default: false
        }
    },
    template: myTab,
    data: function() {
        return {
            isActive: false,
            copyGridParam: null
        };
    },
    methods: {
        toggleTap: function() {
            vm.q.status = this.status;
            var copyGridParam = $.extend(true, {}, this.gridParam);
            copyGridParam.postData = {
                'status': this.status
            }
            switch (this.status) {
                case '1':
                    copyGridParam.colModel.splice(copyGridParam.colModel.length - 1, 1);
                    copyGridParam.colModel.push({
                        label: '操作时间',
                        name: 'checkTime'
                    });
                    copyGridParam.colModel.push({
                        label: '操作',
                        name: 'id',
                        formatter: function(value, options, row) {
                            return '<a class="btn btn-success btn-xs" onclick="withdrawcashPayConfirm(' + value + ')">打款</a>';
                        }
                    });
                    break;
                case '-1':
                    copyGridParam.colModel.splice(copyGridParam.colModel.length - 1, 1);
                    copyGridParam.colModel.push({
                        label: '操作时间',
                        name: 'checkTime'
                    });
                    copyGridParam.colModel.push({
                        label: '不通过原因',
                        name: 'checkMemo'
                    });
                    break;
                case '2':
                    copyGridParam.colModel.splice(copyGridParam.colModel.length - 1, 1);
                    copyGridParam.colModel.push({
                        label: '操作时间',
                        name: 'payTime'
                    });
                    copyGridParam.colModel.push({
                        label: '备注',
                        name: 'payMemo'
                    });
                    copyGridParam.colModel.push({
                        label: '交易号',
                        name: 'payTransactionId'
                    });
                    break;
            }
            $.jgrid.gridUnload('#jqGrid');
            $("#jqGrid").jqGrid(copyGridParam);
        }
    },
    mounted: function() {
        this.isActive = this.selected;
    }
})
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showListWithdraw: true,
        showListRecharge: true,
        showListPay: true,
        title: null,
        wait: 60,
        memberWallet: {},
        auditInfo: {
            id: null,
            status: null,
            mome: null,

        },
        payConfirmInfo: {
            id: null,
            status: null,
            mome: null,
            payTransactionId:null
        },
        q: {
            mobile: null,
            loginName:null,
            memberName: null,
            orderId: null,
            bankId: '',
            status: null
        },
        banks: null
    },
    methods: {
        query: function() {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: { "mobile": vm.q.mobile, "orderId": vm.q.orderId, "memberName": vm.q.memberName, "bankId": vm.q.bankId, "status": vm.q.status }
            }).trigger("reloadGrid");
        },
        getInfo: function(id) {
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = '我的账单';
            $.get("../walletDetail/info/" + id, function(r) {
                vm.memberWallet = r.memberWallet;
            });
        },
        reload: function(event) {
            vm.showList = true;
            vm.withdrawInfo = {};
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: { "mobile": vm.q.mobile, "orderId": vm.q.orderId, "memberName": vm.q.memberName, "bankId": vm.q.bankId, "status": vm.q.status }
            }).trigger("reloadGrid");
        },
        withdraw: function() {
            this.showListWithdraw = false;
            vm.title = '提现';
        },
        toRecharge: function() {
            this.showListRecharge = false;
            vm.title = '充值';
        },
        audit: function(status) {
            vm.auditInfo.status = status;
            $.ajax({
                url: '../wallet/withdrawalscash/audit',
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(vm.auditInfo),
                async: false,
                success: function(r) {
                    if (r.code==0) {
                        alert("操作成功", function() {
                            vm.reload();
                            layer.closeAll();
                        });
                    }
                    else{
                    	alert(r.msg);
                    }
                }
            });
        },
        payConfirm: function(status) {
            vm.payConfirmInfo.status = status;
            $.ajax({
                url: '../wallet/withdrawalscash/payconfirm',
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(vm.payConfirmInfo),
                async: false,
                success: function(r) {
                    if (r.code==0) {
                        alert("操作成功", function() {
                            vm.reload();
                            layer.closeAll();
                        });
                    }
                    else{
                    	alert(r.msg);
                    }
                }
            });
        }
    },
    created: function() {
        var self = this;
        self.q.status=0;
        $.ajax({
            type: "get",
            url: '../wallet/getbanks',
            success: function(r) {
                if (r.code === 0) {
                    self.banks = r.banks;
                } else {
                    alert(r.msg);
                }
            }
        });
    },
    mounted: function() {
        $("#jqGrid").jqGrid(this.gridParam);
    }
});

function withdrawcashAudit(id) {
    vm.auditInfo.id = id;
    layer.open({
        type: 1,
        title: '提现审核',
        closeBtn: 2,
        area: ['747px', '250px'],
        content: $('#withdrawcashAudit')
    });
}

function withdrawcashPayConfirm(id) {
    vm.payConfirmInfo.id = id;
    layer.open({
        type: 1,
        title: '提现打款确认',
        closeBtn: 2,
        area: ['747px', '300px'],
        content: $('#withdrawcashPayConfirm')
    });
}
