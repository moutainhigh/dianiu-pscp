'use strict';
Vue.prototype.gridParam = {
    url: '../engineeringproject/projectList',
    postData: {
        'status': 'progressing'
    },
    datatype: "json",
    colModel: [
        {
            label: 'id',
            name: 'id',
            width: 50,
            key: true,
            hidden: true
        }, {
            label: '项目编号',
            name: 'projectNo',
            width: 100
        }, {
            label: '项目名称',
            name: 'name',
            width: 90
        }, {
            label: '服务商',
            name: 'companyName',
            width: 90
        }, {
            label: '工作时间',
            name: 'workTime',
            width: 130,
            formatter: function (value, options, row) {
                return row.startTime + ' 至 ' + row.endTime
            }
        }, {
            label: '提交时间',
            name: 'showTime',
            width: 80
        }, {
            label: '项目状态',
            name: 'status',
            width: 60,
            formatter: function (value, options, row) {
                var showValue;
                switch (value) {
                    case -1:
                        showValue = '已取消';
                        break;
                    case 0:
                        showValue = '未启动';
                        break;
                    case 1:
                        showValue = '进行中';
                        break;
                    case 2:
                        showValue = '费用确认';
                        break;
                    case 3:
                        showValue = '已结算';
                        break;
                }
                return '<span class="label label-info">' + showValue + '</span>';
            }
        }
    ],
    viewrecords: true,
    height: 385,
    rowNum: 10,
    rowList: [
        10, 30, 50
    ],
    rownumbers: false,
    rownumWidth: 25,
    autowidth: true,
    multiselect: true,
    pager: "#jqGridPager",
    jsonReader: {
        root: "pageUtils.list",
        page: "pageUtils.currPage",
        total: "pageUtils.totalPage",
        records: "pageUtils.totalCount"
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
};

var myTabs = [
    '<div>',
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
    data: function () {
        return {isActive: false, copyGridParam: null};
    },
    methods: {
        toggleTap: function () {
            vm.status = this.status;
            vm.initStatus();
            var copyGridParam = $.extend(true, {}, this.gridParam);
            copyGridParam.postData = {
                'status': this.status
            }
            $.jgrid.gridUnload('#jqGrid');
            $("#jqGrid").jqGrid(copyGridParam);
        }
    },
    mounted: function () {
        this.isActive = this.selected;
    }
})

var vm = new Vue({
    el: '#rrapp',
    data: {
        status: 'progressing',
        showList: true,
        projectdetails: true,
        title: null,
        q: {
            projectNo: '',
            name: '',
            companyName: '',
            workTime: '',
            createTime: '',
            subStatus: ''
        },
        detail: {
            id: '',
            name: '',
            projectNo: '',
        },
        subStatus: [],
        projectVO: {},
        actual: {},
        needsVO: {},
        distributionRooms: ""
    },

    methods: {
        initStatus: function () {
            var self = this;
            var data = {"status": this.status};
            $.ajax({
                type: "POST",
                url: "../engineeringproject/subStatus",
                data: data,
                contentType: "application/x-www-form-urlencoded",
                success: function (r) {
                    if (r.code == 0) {
                        self.subStatus = r.result;
                    }
                }
            });
        },
        query: function () {
            var postData = {
                'projectNo': vm.q.projectNo,
                'name': vm.q.name,
                'companyName': vm.q.companyName,
                'workTime': vm.q.workTime,
                'createTime': vm.q.createTime,
                'subStatus': vm.q.subStatus
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: postData,
                page: page
            }).trigger("reloadGrid");
        },
        info: function () {
            var projectNo = this.getSelectedRowProjectNo();
            if (!projectNo) {
                alert('请选择一条记录!');
                return;
            }
            this.showList = false;
            this.detail.projectNo = projectNo;
            var self = this;
            $.ajax({
                url: '../engineeringproject/projectDetail/' + projectNo,
                type: 'post',
                dataType: 'json',
                success: function (result) {
                    var res = result.detail.projectVO;
                    var res2 = result.detail.needsVO;
                    console.log(res2)
                    self.projectVO = res;
                    self.needsVO = res2;
                    self.actual = res.actualPriceInfo
                    var distributionRooms = JSON.parse(res2.distributionRooms)
                    var distriButionRooms = []
                    for (var i = 0; i < distributionRooms.length; i++) {
                        distriButionRooms.push(distributionRooms[i].name)
                    }
                    self.distributionRooms = distriButionRooms.join(",")
                }
            });
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
            return rowData.projectNo;
        },
        reload: function () {
            this.projectVO = {}
            this.distributionRooms = ""
            this.showList = true
            $("#jqGrid").trigger("reloadGrid");
        }
    },

    created: function () {
        this.initStatus();
    },

    mounted: function () {
        $("#jqGrid").jqGrid(this.gridParam);
        $('.form_datetime').datetimepicker({
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
        $('#workTime').on('changeDate', function (ev) {
            if (ev.date) {
                vm.q.workTime = getNowFormatDate(ev.date);
            }
        });
        $('#workTime-remove').on('click', function () {
            vm.q.workTime = '';
        });
        $('#createTime').on('changeDate', function (ev) {
            if (ev.date) {
                vm.q.createTime = getNowFormatDate(ev.date);
            }
        });
        $('#createTime-remove').on('click', function () {
            vm.q.createTime = '';
        });
    }
});
