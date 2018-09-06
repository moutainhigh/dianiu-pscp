Vue.prototype.gridParam = {
    url: '../needs/list',
    postData: {
        'status': ''
    },
    datatype: "json",
    colModel: [{
        label: '需求编号',
        name: 'orderId',
        hidden: true
    }, {
        label: '响应订单编号编号',
        name: 'responsedOrderId',
        hidden: true
    }, {
        label: '需求名称',
        name: 'name'
    }, {
        label: '需求发布时间',
        name: 'publishTime'
    }, {
        label: '状态',
        name: 'status',
        hidden: true
    }, {
        label: '状态',
        name: 'showStatus',
        formatter: function (value, options, row) {
            return '<label class="label label-info">' + value + '</label>';
        }
    }, {
        label: '支付状态',
        name: 'payStatus',
        formatter: function (value, options, row) {
            var showPayStatus;
            switch (row.payStatus) {
                case 0:
                    showPayStatus = '未支付';
                    break;
                case 1:
                    showPayStatus = '支付确认';
                    break;
                case 2:
                    showPayStatus = '支付成功';
                    break;
                case 3:
                    showPayStatus = '支付失败';
                    break;
                case 4:
                    showPayStatus = '取消支付';
                    break;
            }
            return '<label class="label label-info">' + showPayStatus + '</label>';
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

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showList2nd: true,
        title: '需求详情',
        status: 'not',
        payType: 1,
        payTypeList: [],
        payAmount: 0,
        q: {
            name: ''
        },
        detail: {
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
        payInfo: {
            payStatus: null,
            orderId: '',
            status: null
        },
        orderId: null,
        orderStatus: null,
        showOrderStatus: null,
        paySuccess: false
    },
    methods: {
        query: function () {
            var postData = {
                'name': vm.q.name
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: postData,
                page: page
            }).trigger("reloadGrid");
        },
        viewDetail: function () {
            var orderId;
            var responsedOrderId;
            if (this.orderId) {
                orderId = this.orderId;
                sessionStorage.removeItem('imneeds');
                this.showList = false;
                this.orderStatus = 0;
                responsedOrderId = -1;
            } else {
                orderId = getSelectedRowOrderId();
                if (!orderId) {
                    alert('请选择一条记录!');
                    return;
                }
                this.detail.orderId = orderId;
                var grid = $("#jqGrid");
                var selectedIDs = grid.getGridParam("selarrrow");
                var rowData = grid.jqGrid('getRowData', selectedIDs[0]);
                responsedOrderId = rowData.responsedOrderId;
                var status = rowData.status;
                var payStatus = jQuery(rowData.payStatus).text();
                this.showOrderStatus = jQuery(rowData.showStatus).text();
                this.orderStatus = status;
                if (status == 0 || (status == 1 && (payStatus == '未支付' || payStatus == '取消支付'))) {
                    this.showList = false;
                } else {
                    this.showList2nd = false;
                }
            }
            this.orderId = orderId;
            var self = this;
            $.ajax({
                url: '../needs/info/' + orderId,
                type: 'post',
                data: JSON.stringify(responsedOrderId),
                datatype: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        self.detail = r.bean.needsVO;
                        self.payInfo = r.bean.needsOrderInfo;
                        self.detail.distributionRooms = JSON.parse(r.bean.needsVO.distributionRooms);
                        if (!self.detail.quotedInfo) {
                            self.detail.quotedInfo = {};
                            self.detail.quotedInfo.quotedPrice = 0;
                            self.detail.quotedInfo.attachmentList = [];
                        } else if (!self.detail.quotedInfo.attachmentList) {
                            self.detail.quotedInfo.attachmentList = [];
                        }
                        if (!self.cooperationInfo) {
                            self.cooperationInfo = [];
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        save: function () {
            var self = this;
            $.ajax({
                url: '../needs/order/respond/' + vm.orderId,
                type: 'post',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        var data = {};
                        data.type = 'requirementPay';
                        data.orderType = 4;
                        data.orderIds = r.result.orderId;
                        self.socialWorkorderPay(data);
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        pay: function () {
            var data = {};
            data.type = 'requirementPay';
            data.orderType = 4;
            data.orderIds = vm.payInfo.orderId;
            if (data.orderIds) {
                this.socialWorkorderPay(data);
            }
        },
        newOrder: function () {
            $.ajax({
                url: '../needs/survey/initdata/' + vm.orderId,
                type: 'post',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        var data = {
                            'customerId': r.result.customer.id,
                            'customerName': r.result.customer.name,
                            'id': r.result.project.id,
                            'name': r.result.project.name
                        }
                        sessionStorage.setItem('projectData', JSON.stringify(data));
                        window.top.location.href = '../main.html#cp/workorder.html';
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        quotes: function () {
            //检验金额
            var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
            if (!reg.test(vm.detail.quotedInfo.quotedPrice)) {
                alert('输入金额有误!');
                return;
            }
            if (vm.detail.quotedInfo.quotedPrice == 0) {
                alert('金额不能为零!');
                return;
            }
            //校验附件是否为空
            /*if (vm.detail.quotedInfo.attachmentList) {
                if (vm.detail.quotedInfo.attachmentList.length < 1) {
                    alert('请选择合作附件');
                    return;
                }
            } else {
                alert('请选择合作附件');
                return;
            }*/
            var data = {};
            data = this.detail.quotedInfo;
            $.ajax({
                url: '../needs/order/quote/' + this.payInfo.orderId,
                type: 'post',
                datatype: 'json',
                data: JSON.stringify(data),
                success: function (r) {
                    if (r.code == 0) {
                        alert('报价成功', function () {
                            vm.reload();
                        })
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        reload: function () {
            this.showList = true;
            this.showList2nd = true;
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
            }
            this.payInfo = {
                payStatus: null,
                orderId: '',
                status: null
            }
            $("#jqGrid").trigger("reloadGrid");
        },
        cancel: function () {
            var self = this;
            confirm('确认取消', function () {
                $.ajax({
                    url: '../needs/order/' + self.payInfo.orderId + '/cancel',
                    type: 'post',
                    datatype: 'json',
                    success: function (r) {
                        if (r.code == 0) {
                            alert('取消成功', function () {
                                vm.reload();
                            })
                        } else {
                            alert(r.msg);
                        }
                    }
                })
            });
        },
        choosePayType: function (id) {
            vm.payTypeCheckedId = id;
            switch (id) {
                case 1003:
                    vm.payType = 0;
                    break;
                case 1000:
                    vm.payType = 1;
                    break;
                case 1001:
                    vm.payType = 2;
                    break;
            }
        }
    },
    mounted: function () {
        $("#jqGrid").jqGrid(this.gridParam);
        this.orderId = sessionStorage.getItem('imneeds');
        if (this.orderId) {
            this.viewDetail();
        }
    }
});