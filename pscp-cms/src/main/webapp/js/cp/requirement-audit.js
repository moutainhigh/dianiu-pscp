var gridParam = {
    url: '../needs/list',
    postData: { 'status': "not_audit", },
    datatype: "json",
    colModel: [{
            label: '订单编号',
            name: 'orderId',
            hidden: true
        }, {
            label: '需求名称',
            name: 'needsName'
        }, {
            label: '手机号',
            name: 'loginId'
        }, {
            label: '客户名称',
            name: 'memberName',
        }, {
            label: '提交日期',
            name: 'publishTime'
        },
        {
            label: '截止日期',
            name: 'publishCutoffTime'
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
            var copyGridParam = $.extend(true, {}, gridParam);
            copyGridParam.postData = {
                'status': this.status
            }
            if (this.status == 'audit_failed') {
                copyGridParam.colModel.push({
                    label: '失败原因',
                    name: 'failReason'
                });
            }
            $.jgrid.gridUnload('#jqGrid');
            $("#jqGrid").jqGrid(copyGridParam);
            vm.status = this.status;
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
        title: '详情',
        status: 'not_audit',
        //当前查看记录的orderId
        orderId: '',
        q: {
            memberName: '',
            loginId: '',
            needsName: ''
        },
        detailInfo: {
            memberName: '',
            loginId: '',
            name: '',
            describe: '',
            attachmentList: [],
            publishCutoffTime: '',
            workingStartTime: '',
            workingEndTime: '',
            contactPerson: '',
            contactNumber: '',
            contactAddr: '',
            distributionRooms: '',
            maskString: '',
            failReason: '',
            removedImgs: ''
        },
        errorImgSrc: 'this.src="eee"'
    },
    methods: {
        getDetail: function() {
            var orderId = getSelectedRowOrderId();
            if (orderId == null) {
                return;
            }
            this.orderId = orderId;
            this.showList = false;
            var self = this;
            $.ajax({
                url: '../needs/details/' + orderId,
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        self.detailInfo = r.needsInfo;
                        self.detailInfo.distributionRooms = JSON.parse(r.needsInfo.distributionRooms);
                        var grid = $("#jqGrid");
                        var selectedIDs = grid.getGridParam("selarrrow");
                        var rowData = grid.jqGrid('getRowData', selectedIDs[0]);
                        self.detailInfo.memberName = rowData.memberName;
                        self.detailInfo.loginId = rowData.loginId;
                        if (rowData.failReason) {
                            self.detailInfo.failReason = rowData.failReason;
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        audit: function(status) {
            if (status == -1) {
                if (!this.detailInfo.failReason) {
                    alert('请填入审核不通过原因!');
                    return;
                }
            }
            var data = {};
            data.status = status;
            data.maskString = this.detailInfo.maskString;
            data.failReason = this.detailInfo.failReason;
            var arr = this.detailInfo.attachmentList;
            var newArr = [];
            for (var i = 0; i < arr.length; i++) {
                if (arr[i].isOpen == 1) {
                    newArr.push(arr[i].id);
                }
            }
            data.removedImgs = newArr.join();
            $.ajax({
                url: '../needs/audit/' + vm.orderId,
                type: 'post',
                dataType: 'json',
                data: JSON.stringify(data),
                success: function(r) {
                    if (r.code == 0) {
                        alert('提交成功!', function() {
                            vm.reload();
                        })
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        query: function() {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: { 'status': vm.status, 'memberName': vm.q.memberName, 'loginId': vm.q.loginId, 'needsName': vm.q.needsName }
            }).trigger("reloadGrid");
        },
        reload: function() {
            this.showList = true;
            this.detailInfo = {
                memberName: '',
                loginId: '',
                companyName: '',
                describe: '',
                attachmentList: [],
                publishCutoffTime: '',
                workingStartTime: '',
                workingEndTime: '',
                contactPerson: '',
                contactNumber: '',
                contactAddr: '',
                distributionRooms: '',
                maskString: '',
                failReason: '',
                removedImgs: ''
            }
            this.q = {
                memberName: '',
                loginId: '',
                needsName: ''
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: { 'status': vm.status }
            }).trigger("reloadGrid");
        },
        setImgStatusY: function(index) {
            vm.detailInfo.attachmentList[index].isOpen = 0;
        },
        setImgStatusN: function(index) {
            vm.detailInfo.attachmentList[index].isOpen = 1;
        }

    },
    mounted: function() {
        $("#jqGrid").jqGrid(gridParam);
    }
});