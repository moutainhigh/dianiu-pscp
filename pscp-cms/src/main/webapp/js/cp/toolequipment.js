$(function() {
    $("#jqGrid").jqGrid({
        url: "../toolEquipment/list",
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true },
            { label: '设备名称', name: 'name', widht: 120 },
            { label: '规格', name: 'model', width: 120 }
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
                "overflow-x": "hidden"
            });
        }

    });
});
var vm = new Vue({
    el: "#rrapp",
    data: {
        showList: true,
        title: null,
        toolEquipment: {}
    },
    methods: {
        query: function() {

        },
        add: function() {
            vm.showList = false;
            vm.title = "新增";
            vm.toolEquipment = {};
        },
        update: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            } else {
                vm.showList = false;
                vm.title = "编辑";
                vm.getInfo(id);
            }
        },
        getInfo: function(id) {
            $.get(
                "../toolEquipment/info/" + id,
                function(r) {
                    if (r.code == 0) {
                        vm.toolEquipment = r.bean
                    } else {
                        alert(r.msg);
                    }
                }
            );
        },
        saveOrUpdate: function(event) {
            var url = "";
            if (vm.toolEquipment.id == null) {
                url = "../toolEquipment/save";
            } else {
                url = "../toolEquipment/update";
            }
            vm.$validator.validateAll().then(function() {
                $.ajax({
                    url: url,
                    dataType: "json",
                    type: "POST",
                    data: JSON.stringify(vm.toolEquipment),
                    success: function(r) {
                        if (r.code == 0) {
                            alert('操作成功', function(index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }, function() {
                alert('信息填写不完整或不规范');
            });

        },
        del: function(event) {
            var id = getSelectedRows();
            if (id == null) {
                return;
            } else {
                $.ajax({
                    url: "../toolEquipment/delete",
                    type: "POST",
                    dataType: "json",
                    data: JSON.stringify(id),
                    success: function(r) {
                        if (r.code == 0) {
                            alert("操作成功", function(index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }
        },
        reload: function(event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});
