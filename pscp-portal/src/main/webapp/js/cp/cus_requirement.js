Vue.prototype.gridParam = {
    url: '../needs/cus/list',
    postData: {
        'status': 'verifying'
    },
    datatype: "json",
    colModel: [{
        label: 'id',
        name: 'id',
        hidden: true
    }, {
        label: '需求编号',
        name: 'orderId'
    }, {
        label: '需求名称',
        name: 'name'
    }, {
        label: '发布时间',
        name: 'publishTime'
    }, {
        label: '状态',
        name: 'status',
        formatter: function (value, options, row) {
            var showStatus;
            switch (row.status) {
                case -3:
                    showStatus = '已超时';
                    break;
                case -2:
                    showStatus = '已取消';
                    break;
                case -1:
                    showStatus = '审核不通过';
                    break;
                case 0:
                    showStatus = '审核中';
                    break;
                case 1:
                    showStatus = '响应中';
                    break;
                case 2:
                    showStatus = '报价中';
                    break;
                case 3:
                    showStatus = '已报价';
                    break;
                case 4:
                    showStatus = '已合作';
                    break;
            }
            return showStatus;
        }
    }],
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
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        canEdit: true,
        showDetail: true,
        showSave: true,
        status: '',
        payType: 1,
        payTypeList: [],
        payAmount: 0,
        q: {
            orderId: '',
            name: '',
            publishTime: ''
        },
        detail: {
            id: '',
            orderId: '',
            publishTime: '',
            name: '',
            describe: '',
            publishCutoffTime: '',
            workingEndTime: '',
            workingStartTime: '',
            responsedCompanys: [],
            attachmentList: [],
            contactPerson: '',
            contactNumber: '',
            contactAddr: '',
            companyName: '',
            distributionRooms: [],
            status: 0,
            cooperationInfo: []
        },
        detailsave: {
            id: '',
            orderId: '',
            publishTime: '',
            name: '',
            describe: '',
            publishCutoffTime: '',
            workingEndTime: '',
            workingStartTime: '',
            responsedCompanys: [],
            attachmentList: [],
            contactPerson: '',
            contactNumber: '',
            contactAddr: '',
            companyName: '',
            distributionRoomIds: "",
            status: 0,
            cooperationInfo: []
        },
        payInfo: {
            payStatus: null,
            orderId: '',
            status: null
        },
        orderId: null,
        orderStatus: null,
        showOrderStatus: null,
        paySuccess: false,
        page: [],
        pageList: '',
        seeOrderId: '',
        inquiryOrderId: [],
    },
    methods: {
        query: function () {
            var postData = {
                'orderId': vm.q.orderId,
                'name': vm.q.name,
                'publishTime': vm.q.publishTime
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: postData,
                page: page
            }).trigger("reloadGrid");
        },
        viewDetail: function () {
            var self = this;
            var id = getSelectedRow();
            if (!id) {
                alert('请选择一条记录!');
                return;
            }
            self.showDetail = false;
            self.showList = false;
            self.detail.id = id;
            $.ajax({

                url: '../needs/cus/needsDetails/' + id,
                type: 'post',
                datatype: 'json',
                success: function (r) {
                    console.log(r)
                    if (r.code == 0) {
                        self.detail = r.details.needs;
                        self.detail.responsedCompanys = r.details.responsedCompanys;
                        for (var i = 0; i < self.detail.responsedCompanys.length; i++) {
                            self.detail.responsedCompanys[i].selected = false
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        add: function () {
            var self = this;
            self.showList = true;
            self.showSave = false;
        },
        save: function () {
            var self = this;
            var needs = self.detailsave;
            var distribution = []
            var distributionRoomsLength = needs.distributionRooms
            for (var i = 0; i < distributionRoomsLength.length; i++) {
                distribution.push(distributionRoomsLength[i].id)
            }
            var distribuionRooms = distribution.toString()
            needs.distributionRoomIds = distribuionRooms
            $.ajax({
                url: '../needs/cus/saveNeeds',
                type: 'POST',
                data: JSON.stringify(needs),
                dataType: 'json',
                success: function (res) {
                    if (res.code == 0) {
                        alert("发布成功")
                        self.showDetail = true;
                        self.showList = true;
                        self.showSave = true;
                        vm.reload()
                    } else {
                        alert(res.msg)
                    }
                }
            })
        },
        reload: function () {
            this.showList = true;
            this.showSave = true;
            this.showDetail = true;
            this.orderId = null;
            this.detail = {
                orderId: '',
                publishTime: '',
                name: '',
                describe: '',
                publishCutoffTime: '',
                workingEndTime: '',
                workingStartTime: '',
                quotedInfo: {
                    quotedPrice: 0,
                    attachmentList: []
                },
                attachmentList: [],
                contactPerson: '',
                contactNumber: '',
                contactAddr: '',
                companyName: '',
                distributionRooms: '',
                status: 0,
                cooperationInfo: []
            },
                this.detailsave = {
                    id: '',
                    orderId: '',
                    publishTime: '',
                    name: '',
                    describe: '',
                    publishCutoffTime: '',
                    workingEndTime: '',
                    workingStartTime: '',
                    responsedCompanys: [],
                    attachmentList: [],
                    contactPerson: '',
                    contactNumber: '',
                    contactAddr: '',
                    companyName: '',
                    distributionRoomIds: "",
                    status: 0,
                    cooperationInfo: []
                },
                this.payInfo = {
                    payStatus: null,
                    orderId: '',
                    status: null
                },
                $("#jqGrid").trigger("reloadGrid");
        },
        cancelrelease: function () {
            var self = this
            var orderId = getSelectedRowOrderId();
            $.ajax({
                url: '../needs/cus/cancelNeeds/' + orderId,
                type: 'GET',
                success: function (res) {
                    if (res.code === 0) {
                        alert('取消发布成功')
                        self.showDetail = true
                        self.showList = true;
                        vm.reload()
                    }
                }
            })
        },
        Choice: function (item) {
            item.selected = !item.selected
            if (this.inquiryOrderId.indexOf(item.orderId) == -1) {
                this.inquiryOrderId.push(item.orderId)
            } else {
                this.inquiryOrderId.splice(this.inquiryOrderId.indexOf(item.orderId), 1)
            }
        },
        inquiry: function () {
            var responsedOrderIds = this.inquiryOrderId.toString()
            var orderId = getSelectedRowOrderId();
            if (responsedOrderIds == '') {
                alert("请选择服务商")
                return
            }
            console.log(responsedOrderIds)
            $.ajax({
                url: '../needs/cus/needsQuote/' + orderId,
                contentType: 'application/x-www-form-urlencoded',
                type: 'post',
                data: {responsedOrderIds: responsedOrderIds},
                success: function (res) {
                    if (res.code === 0) {
                        alert('询价成功')
                    } else {
                        alert(res.msg)
                    }
                }
            })
        }
    },
    created: function () {
        //配电房列表
        var self = this
        $.ajax({
            url: '../room/list',
            type: 'POST',
            success: function (result) {
                var res = result.page
                if (result.code === 0) {
                    var pagelist = res.list
                    for (var i = 0; i < pagelist.length; i++) {
                        self.page.push(pagelist[i])
                    }
                }
            }
        })
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
        $('#publishTime').on('changeDate', function (ev) {
            if (ev.date) {
                vm.q.publishTime = getNowFormatDate(ev.date);
            }
        });
        $('#publishTime-remove').on('click', function () {
            vm.q.publishTime = '';
        });
        $('#publish-cutoff').on('changeDate', function (ev) {
            if (ev.date) {
                vm.detailsave.publishCutoffTime = getNowFormatDate(ev.date);
            }
        })
        $('#work-start').on('changeDate', function (ev) {
            if (ev.date) {
                vm.detailsave.workingStartTime = getNowFormatDate(ev.date);
            }
        })
        $('#work-end').on('changeDate', function (ev) {
            if (ev.date) {
                vm.detailsave.workingEndTime = getNowFormatDate(ev.date);
            }
        })
    }
});
