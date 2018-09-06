'use strict';
$(function() {
    $("#jqGrid").jqGrid({
        url: '../companycustomer/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true, hidden: true },
            { label: '手机号码', name: 'mobile', width: 80 },
            { label: '企业名称', name: 'companyName', width: 80 },
            {
                label: '认证状态',
                name: 'authStatus',
                width: 40,
                formatter: function(value, options, row) {
                    if (value === 1) {
                        return '<span class="label label-warning">认证中</span>'
                    } else if (value === 2) {
                        return '<span class="label label-success">认证成功</span>'
                    } else if (value === -1) {
                        return '<span class="label label-danger">认证失败</span>'
                    } else if (value == 0) {
                        return '<span class="label label-warning">未认证</span>'
                    } else {
                        return '<span class="label label-warning">未认证</span>'
                    }
                },
                hidden: true
            },
            {
                label: '会员状态',
                name: 'memberStatus',
                width: 40,
                formatter: function(value, options, row) {

                    if (value === 0) {
                        return '<span class="label label-danger">禁用</span>';
                    }
                    if (value === 1) {
                        return '<span class="label label-success">正常</span>';
                    } else {
                        return '<span class="label label-success">未注册</span>';
                    }
                },
                hidden: true
            },
            {
                label: '邀请时间',
                name: 'invitationTime',
                width: 80
            },
            {
                label: '邀请状态',
                name: 'status',
                width: 40,
                formatter: function(value, options, row) {
                    if (value === 0) {
                        return '<span class="label label-warning">邀请中</span>'
                    } else if (value === 1) {
                        return '<span class="label label-success">已绑定</span>'
                    } else if (value === 2) {
                        return '<span class="label label-danger">申请解绑(企业)</span>'
                    } else if (value === 3) {
                        return '<span class="label label-danger">申请解绑(电工)</span>'
                    } else if (value === -1) {
                        return '<span class="label label-danger">拒绝</span>'
                    } else if (value === -2) {
                        return '<span class="label label-danger">已解绑</span>'
                    } else {
                        return '<span class="label label-warning">未知</span>'
                    }
                }
            },
            {
                label: '操作',
                name: 'id',
                width: 80,
                formatter: function(value, options, row) {
                    if(row.status == 1){
                        return '<span class="btn btn-primary btn-xs" onclick="getDetail(\'' + value + '\')">查看详情</span>';
                    }else{
                        return '';
                    }
                    
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
            companyName: '',
            companyContact: ''
        },
        showList: true,
        isInvitation: false,
        title: null,
        startTime: '',
        endTime: '',
        companyInvitationReq: {
            mobile: '', //Y 手机号码
            companyName: '' //N 企业名字

        },
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
                        'companyName': vm.q.companyName,
                        'mobile': vm.q.mobile,
                        'companyContact': vm.q.companyContact,
                        'createTimeStartConvert': vm.startTime,
                        'createTimeEndConvert': vm.endTime
                    }
                }).trigger("reloadGrid");
            }
        },
        invitation: function() {
            vm.errors.clear();
            vm.showList = false;
            vm.isInvitation = true;
            vm.readonly = false;
            vm.title = "邀请";
        },
        update: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.errors.clear();
            vm.showList = false;
            vm.isInvitation = false;
            vm.readonly = true;
            vm.title = "修改";
            vm.getInfo(id)
        },
        invitationCompany: function(event) {
            this.preventRepeat(event);
            var url = "../companycustomer/save";
            vm.$validator.validateAll().then(function() {
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.companyInvitationReq),
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
            vm.companyInvitationReq = {};
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
                vm.startTime = getNowFormatDate(ev.date);
            }
        });
        $('#date-start-remove').on('click', function() {
            vm.startTime = '';
        });
        $('#date-end').on('changeDate', function(ev) {
            if (ev.date) {
                vm.endTime = getNowFormatDate(ev.date);
            }
        });
        $('#date-end-remove').on('click', function() {
            vm.endTime = '';
        });
    }
});

function getDetail(id) {
    vm.errors.clear();
    vm.showList = false;
    vm.isInvitation = false;
    vm.readonly = true;
    vm.title = "修改";
    $.get("../companycustomer/info/" + id, function(r) {
        vm.companyCustomer = r.companyCustomer;
    });
}