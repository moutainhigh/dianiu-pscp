$(function() {
    $("#jqGrid").jqGrid({
        url: '../smssendlog/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 25, key: true },
            { label: '手机号码', name: 'mobile', width: 50 },
            { label: '发送内容', name: 'content', width: 200 },
            {
                label: '发送状态',
                name: 'status',
                width: 35,
                formatter: function(value, options, row) {
                    label = "";
                    if (value == 0) {
                        label = "成功";
                    }
                    if (value == 1) {
                        label = "发送中";
                    }
                    if (value == 2) {
                        label = "失败";
                    }
                    return label;
                }
            },
            { label: '发送时间', name: 'createTime', width: 60 },
            {
                label: '明细',
                name: 'id',
                width: 25,
                formatter: function(value, options, row) {
                    return '<span class="btn btn-primary btn-xs" onclick="viewDetails(\'' + value + '\')">查看</span>';
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
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        smsSendLog: {},
        q: {
            mobile: ''
        }
    },
    methods: {
        query: function() {
            vm.reload();
        },
        add: function() {
            vm.showList = false;
            vm.title = "";
            vm.smsSendLog = {};
        },
        info: function(event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "详情";
            vm.getInfo(id)
        },
        saveOrUpdate: function(event) {
            var url = "../smssendlog/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.smsSendLog),
                success: function(r) {
                    if (r.code === 0) {
                        alert('操作成功', function(index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function(event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function() {
                $.ajax({
                    type: "POST",
                    url: "../smssendlog/delete",
                    data: JSON.stringify(ids),
                    success: function(r) {
                        if (r.code == 0) {
                            alert('操作成功', function(index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function(id) {
            vm.showList = false;
            vm.title = "详情";
            $.get("../smssendlog/info/" + id, function(r) {
                vm.smsSendLog = r.smsSendLog;
            });
        },
        reload: function(event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: vm.q
            }).trigger("reloadGrid");
        }
    }
});

function viewDetails(id) {
    vm.getInfo(id);
}