var gridParam = {
    url: "../invoice/list",
    postData: {
        status: 1
    },
    datatype: "json",
    autowidth: true,
    height: 400,
    colModel: [
        {
            label: "开票公司",
            name: "payerName",
            width: 80
        }, {
            label: "内容",
            width: 100,
            formatter: function (grid, row, state) {
                var contents = state.pays;
                var content = [];
                for (let i = 0; i < contents.length; i++) {
                    var newContents = contents[i].payContents;
                    for (let j = 0; j < newContents.length; j++) {
                        content.push(newContents[j].period);
                    }
                }
                var html = "";
                var str = "";
                for (var k = 0; k < content.length; k++) {
                    str = content[k];
                    html += '<span style="display: block;padding: 2px 0;"> ' + str + ' </span>'
                }
                return html;
            }
        }, {
            label: "金额",
            width: 100,
            formatter: function (grid, row, state) {
                var amount = state.amount;
                return amount;
            }
        }, {
            label: "支付日期",
            name: "payTime",
            width: 50
        }, {
            label: "操作",
            width: 100,
            formatter: function (value, grid, rows, state) {
                // console.log(grid);
                // console.log(rows)
                return '<button type = "button" status="' + rows.status + '" orderId="' + rows.orderId + '" id="seeBtn" class = "btn btn-success td-btn" >查看</button> ';
            },
            align: "center"
        }
    ],
    viewrecords: true,
    rowNum: 10,
    pager: "#jqGridPager",
    rowList: [
        10, 30, 50
    ],
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
        selected: {
            default: false
        }
    },
    template: myTab,
    data: function () {
        return {
            isActive: false,
            copyGridParam: null
        };
    },
    methods: {
        toggleTab: function () {
            vm.statusInVm = this.status;
            vm.q = {};
            var copyGridParam = $.extend(true, {}, gridParam);
            copyGridParam.postData = {
                'status': this.status
            };
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
    el: "#Invoice",
    data() {
        return {
            showList: true,
            showDetailInfo: true,
            q: {
                payerCompanyName: "",
                payDate: "",
            }
        }
    },
    methods: {
        query: function () {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            var postData = {
                payerCompanyName: vm.q.payerCompanyName,
                payDate: vm.q.payDate,
            };
            console.log(postData);
            $("#jqGrid").jqGrid('setGridParam', {
                postData: postData,
                page: page
            }).trigger("reloadGrid");
        },
        empty: function () {
            this.q = {
                mobile: "",
                name: "",
                contract: "",
                status: "",
                startTime: "",
                endTime: ""
            };
            window.location.reload()
        },
        reload: function () {

            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {page: page}).trigger("reloadGrid");
        },
    },
    mounted() {
        //选择时间
        var self = this
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
        $('#date-start').on('changeDate', function (ev) {
            self.q.payDate = getNowFormatDate(ev.date);;
        });
        //表格
        $("#jqGrid").jqGrid(gridParam)
    },
    created: function () {

    }
});


$('#Invoice').on('click','#seeBtn',function () {
    var orderId = $(this).attr("orderId")
    var status = $(this).attr("status")
    sessionStorage.setItem('orderId', orderId)
    vm.showList = !vm.showList;
    if (status == 1) {
        vm.showDetailInfo = false;
    } else {
        vm.showDetailInfo = true;
    }
    $.ajax({
        url: "../invoice/detail/" + orderId,
        type: "GET",
        success: function (res) {
            var data = res.invoice;
            $("#payOrderId").text(data.orderId);
            $("#payerName").text(data.payerName);
            $("#payTime").text(data.payTime);
            $("#companyName").text(data.companyName);
            $("#taxpayerId").text(data.taxpayerId);
            $("#contactNumber").text(data.contactNumber);
            $("#amount").text(data.amount);
            var contents = res.invoice.pays;
            var content = [];
            var fee = [];
            for (let i = 0; i < contents.length; i++) {
                var newContents = contents[i].payContents;
                for (let j = 0; j < newContents.length; j++) {
                    content.push(newContents[j].period);
                    fee.push(newContents[j].fee);
                }
            }
            var period = "";
            var perIod = "";
            var feehtml = "";
            var feeHtml = "";
            for (var i = 0; i < content.length; i++) {
                period = content[i];
                perIod += '<span style="display: block;">' + period + '</span>'
            }
            for (var i = 0; i < fee.length; i++) {
                feehtml = fee[i];
                feeHtml += '<span style="display: block;">' + feehtml + '</span>'
            }
            $("#period").html(perIod);
            $("#fee").html(feeHtml);
        }
    })
})

$('#ticketopening').on('click', function () {
    var orderId = sessionStorage.getItem('orderId')
    $.ajax({
        url: "../invoice/confirm/" + orderId,
        type: "GET",
        success: function (res) {
            if (res.code == 0) {
                alert('操作成功',function () {
                    vm.reload()
                });
                vm.showList = !vm.showList
            }
        }
    })
});