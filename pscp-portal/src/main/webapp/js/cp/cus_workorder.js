'use strict';
var gridParam = {
    url: '../cp/workorder/chieforder/list',
    postData: {
        status: 'ongoing'
    },
    datatype: "json",
    colModel: [
        {
            label: 'orderId',
            name: 'orderId',
            hidden: true
        },
        {
            label: 'id',
            name: 'id',
            key: true,
            hidden: true
        }, {
            label: '工单类型',
            name: 'typeName',
            width: 75
        }, {
            label: '工单名称',
            name: 'name'
        }, {
            label: '所属项目',
            name: 'projectName'
        }, {
            label: '服务商',
            name: 'companyName'
        }, {
            label: '日期',
            name: 'publishTime',
            width: 100
        }, {
            label: '状态',
            name: 'status',
            width: 75,
            formatter: function (value, options, row) {
                var showStatus;
                switch (row.status) {
                    case -1:
                        showStatus = '取消';
                        break;
                    case 0:
                        showStatus = '未确认';
                        break;
                    case 1:
                        showStatus = '已确认';
                        break;
                    case 2:
                        showStatus = '进行中';
                        break;
                    case 3:
                        showStatus = '未评价';
                        break;
                    case 4:
                        showStatus = '已评价';
                        break;
                }
                return showStatus;
            }
        }],
    viewrecords: true,
    height: 385,
    rowNum: 10,
    rowList: [
        10, 30, 50
    ],
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
        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
    }
}
var myTabs = [
    '<div>',
    '   <ul class="nav nav-tabs">',
    '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTab">',
    '           <a @click="selectedTab(tab)"><i :class="\'fa \' + tab.icon"></i>{{ tab.name }}</a>',
    '       </li>',
    '   </ul>',
    '   <div class="tab-detail">',
    '       <slot></slot>',
    '   </div>',
    '</div>'
].join("");
var myTab = ['<div>', '</div>'].join("");

Vue.component('tabs', {
    template: myTabs,
    data: function () {
        return {tabs: []};
    },
    created: function () {
        this.tabs = this.$children;
    },
    methods: {
        selectedTab: function (_selectedTab) {
            this.tabs.forEach(function (tab) {
                tab.isActive = tab.name === _selectedTab.name;
            });
        }
    }
});

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
    data: function () {
        return {isActive: false, copyGridParam: null};
    },
    methods: {
        toggleTab: function () {
            vm.status = this.status;
            vm.ininSubStatus();
            vm.q = {};
            var copyGridParam = $.extend(true, {}, gridParam);
            copyGridParam.postData = {
                'status': this.status
            }
            $.jgrid.gridUnload('#jqGrid');
            if (this.status === 'started' || this.status === 'completed') {
                copyGridParam.colModel.push({label: '开始时间', name: 'startTime'});
                copyGridParam.colModel.push({label: '结束时间', name: 'endTime'});
            } else {
            }
            $("#jqGrid").jqGrid(copyGridParam);
        }
    },
    mounted: function () {
        this.isActive = this.selected;
    }
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        status: 'ongoing',
        subStatus: '',
        q: {
            name: '',
            companyName: '',
            subStatus: '',
            startTime: null,
            endTime: null
        },
        showListRecord: true,
        showEvaluation: true,
        showDetailInfo: true,
        showHf: false,
        showSf: false,
        updateReadonly: false,
        havingCustomer: false,
        presentId: null,
        isHaving: false,
        statusInVm: 'unconfirm',
        leaderIdLabel: '请选择',
        title: '',
        presentOrderId: null,
        customers: [],
        projects: [],
        electricianOptions: [],
        eleOptions: [],
        certificateOptions: [],
        toolequipmentOptions: [],
        typeOptions: [],
        defectOptions: [],
        workorder: {
            id: null,
            type: 1,
            typeName: '检修工单',
            /* 工单信息 */
            name: null,
            startTime: '',
            endTime: '',
            address: null,
            content: null,
            leaderId: null,
            orderId: null,
            longitude: null,
            latitude: null,
            /* 客户 */
            customerInfo: {
                'name': '请选择',
                'address': null,
                'id': null,
                'contact': null,
                'phone': null,
                'customerId': null
            },
            /* 项目 */
            projectInfo: {
                'name': '请选择',
                'id': null
            },
            /* 安全措施 */
            safetyMeasures: [],
            measureText: '',
            /* 危险因素 */
            identifications: [],
            identificationText: '',
            /* 机械设备 */
            toolequipmentInfo: [],
            /* 承修信息、工单编号 */
            facilitator: {
                'name': null,
                'phone': null,
                'contacts': null
            },
            /* 检修信息 */
            electricianWorkOrderList: [
                {
                    'component': {
                        'componentName': 'overhaul',
                        'projectName': '',
                        'electrician': null
                    }
                }
            ],
            /* 招募信息 */
            recruitList: [],
            /* 缺陷报告 */
            defect: [],
            defectRecords: ''
        },
        recordImgListLength: 0,
        workRecord: [],
        evaluationImgListLength: 0,
        evaluation: {
            serviceQuality: 0,
            serviceSpeed: 0,
            content: '',
            evaluationImgList: []
        },
        payType: null,
        amount: 0,
        payTypeList: [],
        payTypeCheckedId: null,
        payAmount: 0,
        walletAmount: 0,
        showSocialWorkOrder: true,
        socialWorkOrderView: [],
        orderDetail: {
            workOrder: {},
            workOrderLeader: {},
            customerInfo: {},
            companyInfo: {},
            projectVO: {},
            companyWorkOrder: {},
            hazardFactorIdentifications: {},
            safetyMeasures: {},
            carryingTools: [],
            socialWorkOrderList: []
        },
        fixReport: null,
        patrolReport: null,
        defectReport: null,
        explorationReport: null,
        imSocial: true,
        imFix: true,
        imDefect: true,
        imPatrol: true,
        imExploration: true,
        showTypeName: '检修',
        toolequipmentInfoShow: '',
        defectShow: '',
        defectDetailInfo: {},
        fixDetailInfo: {},
        patrolDetailInfo: {},
        workLogDetailInfo: {},
        mapAddress: ''
    },
    created: function () {
        this.ininSubStatus();
    },
    methods: {
        ininSubStatus: function () {
            var self = this;
            var data = {"status": this.status};
            $.ajax({
                type: 'POST',
                url: '../cp/workorder/chieforder/subStatus',
                data: data,
                contentType: "application/x-www-form-urlencoded",
                success: function (r) {
                    if (r.code == 0)
                        self.subStatus = r.result;
                }
            });
        },
        query: function () {
            var x = new Date(this.q.startTime).getTime();
            var y = new Date(this.q.endTime).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
            } else {
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                var postData = {
                    'status': vm.status,
                    'name': vm.q.name,
                    'companyName': vm.q.companyName,
                    'subStatus': vm.q.subStatus,
                    'startTime': vm.q.startTime,
                    'endTime': vm.q.endTime
                }
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: postData,
                    page: page
                }).trigger("reloadGrid");
            }
        },
        viewDetail: function () {
            var orderId = this.getSelectedRowProjectNo();
            if (!orderId) {
                alert('请选择一条记录')
                return;
            }
            this.showList = false
            $.ajax({
                url: "../cp/workorder/chieforder/view/" + orderId,
                type: "GET",
                success: function (result) {
                    console.log(result)
                    if(result.code == 0){

                    }
                }
            })
        },
        //获取项目编号
        getSelectedRowProjectNo: function () {
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
        },
    },
    mounted: function () {
        var self = this;
        $("#jqGrid").jqGrid(gridParam);
        $('.form_datetime.query').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            minView: 2
        });
        $('.form_datetime.fix').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            minView: 2,
            startDate: new Date
        });
        $('#date-start').on('changeDate', function (ev) {
            self.q.startTime = getNowFormatDate(ev.date);
        });
        $('#date-start-remove').on('click', function () {
            self.q.startTime = '';
        });
        $('#date-end').on('changeDate', function (ev) {
            self.q.endTime = getNowFormatDate(ev.date);
        });
        $('#date-end-remove').on('click', function () {
            self.q.endTime = '';
        });
        $('#fixStartDate').on('changeDate', function (ev) {
            var x = new Date(getNowFormatDate(ev.date)).getTime();
            var y = new Date(getNowFormatDate(vm.workorder.endTime)).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
                $(this).val('');
                return;
            } else {
                vm.workorder.startTime = getNowFormatDate(ev.date);
            }
        });
        $('#fixEndDate').on('changeDate', function (ev) {
            var x = new Date(getNowFormatDate(vm.workorder.startTime)).getTime();
            var y = new Date(getNowFormatDate(ev.date)).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
                $(this).val('');
                return;
            } else {
                vm.workorder.endTime = getNowFormatDate(ev.date);
            }
        });
    }
});





