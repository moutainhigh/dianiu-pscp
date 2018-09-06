$(function() {
    $("#jqGrid").jqGrid({
        url: '../member/user/list',
        datatype: "json",
        colModel: [
            { label: 'ID', name: 'userId', width: 45, key: true },
            { label: '会员名', name: 'memberName', width: 45, key: true },
            { label: '登陆ID', name: 'mobile', width: 100 }, 
            { label: '角色', name: 'roleName', width: 100 }, 
            {
                label: '状态',
                name: 'status',
                width: 80,
                formatter: function(value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }
            },
            { label: '创建时间', name: 'createTime', width: 80 }, {
                label: '操作',
                name: 'userId',
                width: 80,
                formatter: function(value, options, row) {
                	var changeStatusBtn;
                	if(row.status == 1){
                		changeStatusBtn = '<span class="btn btn-warning btn-xs" onclick="statusToggle(\'' + row.userId + '\',0)">禁用</span>'
                	}else if(row.status == 0){
                		changeStatusBtn = '<span class="btn btn-primary btn-xs" onclick="statusToggle(\'' + row.userId + '\',1)">启用</span>'
                	}
                    return changeStatusBtn;
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
        q: {
            memberName: null,
            mobile: null,
            loginName:null,
            createTimeStart: null,
            createTimeEnd: null
        },
        showList: true,
        title: null,
        roleList: {},
        user: {
            status: 1,
            roleIdList: []
        }
    },
    methods: {
        query: function() {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: { 'loginName': vm.q.loginName,'memberName': vm.q.memberName, 'mobile': vm.q.mobile, 'createTimeStart': vm.q.createTimeStart, 'createTimeEnd': vm.q.createTimeEnd },
                page: page
            }).trigger("reloadGrid");
        },
        del: function() {
            var userIds = getSelectedRows();
            if (userIds == null) {
                return;
            }

        },
        getUser: function(userId) {
            $.get("../member/user/info/" + userId, function(r) {
                vm.user = r.user;
            });
        },
        getRoleList: function() {
            $.get("../member/role/select", function(r) {
                vm.roleList = r.list;
            });
        },
        reload: function() {
            vm.showList = true;
            vm.q = {
                loginName: null,
                mobile: null,
                memberName: null,
                createTimeStart: null,
                createTimeEnd: null
            };
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
            	postData: { 'loginName': vm.q.loginName,'memberName': vm.q.memberName, 'mobile': vm.q.mobile, 'createTimeStart': vm.q.createTimeStart, 'createTimeEnd': vm.q.createTimeEnd },
                page: page
            }).trigger("reloadGrid");
        }
    },
    mounted: function() {
        var self = this;
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
                self.q.createTimeStart = getNowFormatDate(ev.date);
            }
        });
        $('#date-start-remove').on('click', function() {
            self.q.createTimeStart = '';
        });
        $('#date-end').on('changeDate', function(ev) {
            if (ev.date) {
                self.q.createTimeEnd = getNowFormatDate(ev.date);
            }
        });
        $('#date-end-remove').on('click', function() {
            self.q.createTimeEnd = '';
        });
    }
});
function statusToggle(userId,status){
	var statusName=(status==0?"禁用":"启用");
	var self=this;
	confirm('确定'+statusName+'当前用户？', function() {
        $.ajax({
            type: "POST",
            url: "../member/user/changeStatus",
            data: JSON.stringify({'userId':userId,'status':status}),
            success: function(r) {
                if (r.code==0) {
                    alert('操作成功', function(index) {
                    	//self.reload();
                        $("#jqGrid").trigger("reloadGrid");
                    });
                } else {
                    alert(r.msg);
                }
            }
        });
    });
}