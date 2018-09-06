'use strict';
Vue.prototype.presentStatus = 'unconfirm';
var colModel = [
    { label: 'id', name: 'id', key: true, width: 50, hidden: true },
    {
        label: '订单编号',
        name: 'orderId',
        hidden: true
    },
    {
        label: '电工资质',
        name: 'memberId',
        hidden: true
    },
    { label: '电工姓名', name: 'userName', width: 80 },
    { label: '手机号', name: 'mobile', width: 100 }, {
        label: '电工类型',
        name: 'electriciantype',
        width: 80,
        formatter: function(value, options, row) {
            return value === 1 ?
                '<span class="label label-success">企业电工</span>' :
                '<span class="label label-warning">社会电工</span>';
        }
    },
    { label: '工单名称', name: 'name' },
    { label: '工单类型', name: 'typeName', width: 75 },
    { label: '所属项目', name: 'projectName' },
    { label: '业主单位', name: 'customerName' }
];
var gridParam = {
    url: 'list',
    datatype: "json",
    colModel: colModel,
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
    postData: {
        'status': 'unconfirm'
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
            this.presentStatus = this.status;
            var copyGridParam = $.extend(true, {}, gridParam);
            copyGridParam.postData = {
                'status': this.status
            }
            $.jgrid.gridUnload('#jqGrid');
            copyGridParam.colModel = colModel;
            vm.q = {
                userName: null,
                mobile: null,
                name: null,
                customerId: 0,
                projectId: 0,
                publishStartTime: null,
                publishEndTime: null,
                finishStartTime: null,
                finishEndTime: null
            };
            vm.companyProjects = [];
            $("#jqGrid").jqGrid(copyGridParam);
        }
    },
    mounted: function() {
        this.isActive = this.selected;
    }
})
$(function() {
    $("#jqGrid").jqGrid(gridParam);
});
var infotabs = ['<div class="main-tab">',
    '   <div class="tabs">',
    '     <ul class="nav nav-tabs info-tab" style="padding: 10px 15px;">',
    '       <li v-for="tab in tabs" :class="{\'is-active active\': tab.isActive}" @click="tab.toggleTap" style="display: inline-block;" role="presentation">',
    '           <a @click="selectedTab(tab)" :id="tab.id">{{ tab.name }}</a>',
    '       </li>',
    '     </ul>',
    '   </div>',
    '   <div class="tab-detail">',
    '       <slot></slot>',
    '   </div>',
    '</div>'
].join("");

var infotab = ['<div v-show="isActive">',
    '   <slot></slot>',
    '</div>'
].join("");
//设置
Vue.component('infotabs', {
    template: infotabs,
    data: function data() {
        return {
            tabs: []
        };
    },
    created: function created() {
        this.tabs = this.$children;
    },

    methods: {
        selectedTab: function(_selectedTab) {
            this.tabs.forEach(function(tab) {
                tab.isActive = tab.name === _selectedTab.name;
            });
        }
    }
});
Vue.component('infotab', {
    props: {
        name: {
            required: true
        },
        selected: {
            default: false
        }
    },
    template: infotab,
    data: function() {
        return {
            isActive: false,
        };
    },
    methods: {
        toggleTap: function() {
            if (this.name === '我是服务商') {
                vm.showLoginBtn = false;
                vm.reload();
            } else {
                vm.showLoginBtn = true
            }
        }
    },
    mounted: function() {
        this.isActive = this.selected;
    }
});




var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            userName: null,
            mobile: null,
            name: null,
            customerId: 0,
            projectId: 0,
            publishStartTime: null,
            publishEndTime: null,
            finishStartTime: null,
            finishEndTime: null
        },
        showList: true,
        showOrderDetail: false,
        showHf: false,
        showSf: false,
        showCt: false,
        showWorkLog: false,
        title: null,
        electricianInfo: {},
        companyCustomers: [],
        companyProjects: [],
        orderDetail: {
            carryingTools: [],
            hazardFactor: [],
            safetyMeasures: []
        },
        companyInfo: {},
        evaluateInfo: {},
        customerInfo: {},
        settlementInfo: {},
        workLogList: [],
        showTypeName: '',
        workLogDetailInfo: {}
    },
    created: function() {
        //获取客户信息
        var self = this;
        $.ajax({
            url: '../../../companycustomer/getall',
            type: 'post',
            dataType: 'json',
            success: function(r) {
                if (r.code == 0) {
                    self.companyCustomers = r.companyCustomers;
                } else {
                    alert(r.msg);
                }
            }
        });
    },
    methods: {
        query: function() {
            vm.reload();
        },
        viewOrderDetail: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var orderId = getSelectedRowOrderId();
            vm.showList = false;
            vm.showOrderDetail = true;
            vm.title = "订单详情";
            vm.orderDetail = {};
            vm.companyInfo = {};
            vm.evaluateInfo = {};
            vm.customerInfo = {};
            vm.settlementInfo = {};
            vm.workLogList = [];
            vm.getOrderDetail(orderId);
        },
        viewElectricianInfo: function(memberId) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
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
            if (rowData.electriciantype.indexOf('社会电工') != -1) {
                var memberId = getSelectedRowMemberId();
                vm.electricianInfo = {};
                vm.getElectricianInfo(memberId);
                var index = layer.open({
                    type: 1,
                    title: '电工资质',
                    area: ['500px', '300px'],
                    closeBtn: 2,
                    shadeClose: false,
                    btn: ['返回'],
                    yes: function(index) {
                        layer.close(index);
                    },
                    content: jQuery('#certificates')
                });
            } else {
                alert('社会电工才能查看资质！');
            }


        },
        getOrderDetail: function(orderId) {
            $.get("detail/" + orderId, function(r) {
                vm.orderDetail = r.detail.orderDetail;
                vm.orderDetail.hazardFactor = JSON.parse(r.detail.orderDetail.hazardFactor);
                vm.orderDetail.safetyMeasures = JSON.parse(r.detail.orderDetail.safetyMeasures);
                if(r.detail.orderDetail.carryingTools){
                    vm.orderDetail.carryingTools = JSON.parse(r.detail.orderDetail.carryingTools);
                }
                vm.companyInfo = r.detail.companyInfo;
                vm.evaluateInfo = r.detail.evaluateInfo;
                vm.customerInfo = r.detail.customerInfo;
                vm.settlementInfo = r.detail.settlementInfo;
                switch (vm.orderDetail.type) {
                    case 1:
                        vm.showTypeName = '检修';
                        break;
                    case 2:
                        vm.showTypeName = '巡检';
                        break;
                    case 3:
                        vm.showTypeName = '勘查';
                        break;
                }
                if (r.detail.workLogList && r.detail.workLogList.length > 0) {
                    vm.showWorkLog = true;
                }
                vm.workLogList = r.detail.workLogList;
                var hf = vm.orderDetail.hazardFactor;
                var sf = vm.orderDetail.safetyMeasures;
                var ct = vm.orderDetail.carryingTools;
                if (hf != null && hf.length > 0) {
                    for (var i = 0; i < hf.length; i++) {
                        if (hf[i].checked == 1) {
                            vm.showHf = true;
                        }
                    }
                }
                if (sf != null && sf.length > 0) {
                    for (var i = 0; i < sf.length; i++) {
                        if (sf[i].checked == 1) {
                            vm.showSf = true;
                        }
                    }
                }

                if (ct != null && ct.length > 0) {
                    vm.showCt = true;
                }
            });
        },
        getElectricianInfo: function(memberId) {
            $.get("electricianinfo/" + memberId, function(r) {
                vm.electricianInfo = r.electricianInfo;
            });
        },
        chooseCustomerId: function() {
            if (vm.q.customerId == 0) {
                vm.q.projectId = 0;
                vm.companyProjects = [];
            } else {
                //获取项目信息
                //var self = this;
                $.get("getprojects/" + vm.q.customerId, function(r) {
                    if (r.code == 0) {
                        vm.companyProjects = r.companyProjects;
                    }
                });
            }
        },
        showWorkLogDetail: function(id){
            $.ajax({
                type: 'post',
                url: '../../../cp/electrician/worklog/detail/' + id,
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        vm.workLogDetailInfo = r.detail;
                        var index = layer.open({
                            type: 1,
                            title: '工作记录详情',
                            closeBtn: 2,
                            area: ['700px', '500px'],
                            shadeClose: true,
                            btn: '返回',
                            yes: function() {
                                vm.workLogDetailInfo = {};
                                layer.close(index);
                            },
                            content: jQuery('#workLogDetail')
                        })
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        reload: function(event) {
            vm.showList = true;
            vm.showOrderDetail = false;
            vm.showHf = false;
            vm.showSf = false;
            vm.showCt = false;
            vm.showWorkLog = false;
            vm.showTypeName = '';
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'userName': vm.q.userName,
                    'mobile': vm.q.mobile,
                    'name': vm.q.name,
                    'projectId': vm.q.projectId,
                    'customerId': vm.q.customerId,
                    'publishStartTime': vm.q.pubStartTime,
                    'publistEndTime': vm.q.pubEndTime,
                    'finishStartTime': vm.q.finishStartTime,
                    'finishEndTime': vm.q.finishEndTime
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});