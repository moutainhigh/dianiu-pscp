'use strict';
Vue.prototype.startTime = null;
Vue.prototype.endTime = null;
$(function() {
    $("#jqGrid").jqGrid({
        url: '../companycustomer/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true },
            { label: '手机号码', name: 'mobile', width: 80 },
            { label: '客户姓名', name: 'cpContact', width: 80 },
            { label: '业主单位', name: 'cpName', width: 80 },
            { label: '联系地址', name: 'cpAddress', width: 80 },
            { label: '创建日期', name: 'createTime', width: 80 }, {
                label: '关联项目',
                name: 'count',
                width: 80,
                formatter: function(value, options, row) {
                    var num = 0;
                    if (value != null) {
                        num = value;
                    }
                    return num;

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
        readonly: false,
        q: {
            mobile: '',
            cpName: '',
            cpContact: ''
        },
        showList: true,
        title: null,
        companyCustomer: {
            mobile: '',
            cpContact: '',
            cpName: '',
            cpPhone: '',
            cpAddress: '',
            cpContactMobile: ''
        }
    },
    methods: {
        query: function() {
            var x = new Date(this.startTime).getTime();
            var y = new Date(this.endTime).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
                return;
            } else {
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                $("#jqGrid").jqGrid('setGridParam', {
                    page: page,
                    postData: {
                        'cpName': vm.q.cpName,
                        'mobile': vm.q.mobile,
                        'cpContact': vm.q.cpContact,
                        'createTimeStartConvert': vm.startTime,
                        'createTimeEndConvert': vm.endTime
                    }
                }).trigger("reloadGrid");
            }
        },
        add: function() {
            vm.showList = false;
            vm.readonly = false;
            vm.title = "新增";
        },
        update: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.readonly = true;
            vm.title = "修改";
            vm.getInfo(id)
        },
        saveOrUpdate: function(event) {
            var url = vm.companyCustomer.id == null ? "../companycustomer/save" : "../companycustomer/update";
            vm.$validator.validateAll().then(function() {
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.companyCustomer),
                    success: function(r) {
                        if (r.code == 0) {
                            alert('操作成功', function() {
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
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function() {
                $.ajax({
                    type: "POST",
                    url: "../companycustomer/delete",
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
            $.get("../companycustomer/info/" + id, function(r) {
                vm.companyCustomer = r.companyCustomer;
            });

        },
        reload: function() {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
            vm.companyCustomer = {};
            vm.q = {};
            vm.startTime = '';
            vm.endTime = '';
        }
    },
    mounted: function() {
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
        $('#date-start').on('changeDate', function(ev) {
            if (ev.date) {
                Vue.prototype.startTime = getNowFormatDate(ev.date);
            }
        });
        $('#date-start-remove').on('click', function() {
            Vue.prototype.startTime = '';
        });
        $('#date-end').on('changeDate', function(ev) {
            if (ev.date) {
                Vue.prototype.endTime = getNowFormatDate(ev.date);
            }
        });
        $('#date-end-remove').on('click', function() {
            Vue.prototype.endTime = '';
        });
    }
});
