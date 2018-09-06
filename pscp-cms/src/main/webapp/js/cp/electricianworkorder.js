'use strict';
Vue.prototype.presentStatus = 'unconfirm';
var colModel = [
    { label: 'id', name: 'id', key: true,width: 80 },
    { label: '电工名称', name: 'userName' },
    { label: '手机号', name: 'mobile' }, {
        label: '电工类型',
        name: 'electriciantype',
        formatter: function(value, options, row) {
            return value === 1 ?
                '<span class="label label-success">企业电工</span>' :
                '<span class="label label-warning">社会电工</span>';
        }
    }, {
        label: '电工资质',
        name: 'memberId',
        formatter: function(value, options, row) {
            return '<a class="btn btn-primary btn-xs" onclick="electricianInfo(' + value + ')">查看</a>';
        }
    },
    { label: '工单名称', name: 'name' },
    { label: '所属项目', name: 'projectName' },
    { label: '业主单位', name: 'customerName' }, {
        label: '费用(元)',
        name: 'actualFee',
        formatter: function(value, options, row) {
            var status = row['status'];
            if (status === 0 || status === 1 || status === 3) {
                return '<span class="label label-danger">' + row['preFee'] + '</span>';
            }
            return '<span class="label label-danger">' + value + '</span>';
        }
    },
    { label: '发布日期', name: 'createTime',width: 250, }
    ,
    {
        label: '操作',
        name: 'orderId',
        width: 80,
        formatter: function(value, options, row) {
            return '<span class="btn btn-primary btn-xs" onclick="orderDetail(\'' + value + '\')">查看</span>';
        }
    }
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
            if (this.status === 'started' || this.status === 'completed') {
                /*copyGridParam.colModel.push({
                    label: '开始时间',
                    name: 'startTime'
                });
                copyGridParam.colModel.push({
                    label: '结束时间',
                    name: 'endTime'
                });*/

            } else {

            }
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
        showElectricianInfo: false,
        title: null,
        electricianInfo: {},
        companyCustomers: [],
        companyProjects: [],
        orderDetail:{},
    	companyInfo:{},
    	evaluateInfo:{},
    	customerInfo:{},
    	settlementInfo:{}
    },
    created: function() {
        //获取客户信息
        var self = this;
        $.ajax({
            url: '../../../companycustomer/getall',
            type: 'post',
            dataType: 'json',
            async: false,
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
        viewOrderDetail: function(orderId) {
            vm.showList = false;
            vm.showOrderDetail = true;
            vm.showElectricianInfo = false;
            vm.title = "订单详情";
            vm.orderDetail={};
        	vm.companyInfo={};
        	vm.evaluateInfo={};
        	vm.customerInfo={};
        	vm.settlementInfo={};
            vm.getOrderDetail(orderId)
        },
        viewElectricianInfo: function(electricianId) {
            vm.showList = false;
            vm.showOrderDetail = false;
            vm.showElectricianInfo = true;
            vm.title = "电工信息";
            vm.electricianInfo={};
            vm.getElectricianInfo(electricianId)
        },
        getOrderDetail: function(orderId) {
            $.get("detail/" + orderId, function(r) {
                console.log(r);
                vm.orderDetail = r.detail.orderDetail;
                vm.orderDetail.hazardFactor=JSON.parse(r.detail.orderDetail.hazardFactor);
                vm.orderDetail.safetyMeasures=JSON.parse(r.detail.orderDetail.safetyMeasures);
                vm.orderDetail.carryingTools=JSON.parse(r.detail.orderDetail.carryingTools);
                vm.companyInfo =  r.detail.companyInfo;
                vm.evaluateInfo =  r.detail.evaluateInfo;
                vm.customerInfo =  r.detail.customerInfo;
                vm.settlementInfo =  r.detail.settlementInfo;
                //console.log(vm.orderDetail);
                
            });
        },
        getElectricianInfo: function(electricianId) {
            $.get("electricianinfo/" + electricianId, function(r) {
            	console.log(r);
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
                    vm.companyProjects = r.companyProjects;
                });
            }
        },
        reload: function(event) {
            vm.showList = true;
            vm.showOrderDetail = false;
            vm.showElectricianInfo = false;
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

function orderDetail(orderId) {
    vm.viewOrderDetail(orderId);
}

function electricianInfo(electricianId) {

    vm.viewElectricianInfo(electricianId);
}
