$(function () {
    $("#jqGrid").jqGrid({
        url: '../companyBuilding/list',
        datatype: "json",
        colModel: [{
            label: 'id',
            name: 'id',
            width: 50,
            key: true,
            hidden: true
        }, {
            label: '楼宇名称',
            name: 'name'
        }, {
            label: '客户名称',
            name: 'companyName'
        }, {
            label: '联系人',
            name: 'contacts'
        }, {
            label: '手机号码',
            name: 'mobile'
        }, {
            label: '操作',
            name: 'id',
            formatter: function (value, options, row) {
                var updateOperate = '<span class="btn btn-primary btn-xs" onclick="getDetail(\'' + value + '\')">编辑</span>';
                var deleteOperate = '<span class="btn btn-primary btn-xs" onclick="listDelete(\'' + value + '\')">删除</span>';
                return updateOperate + '&nbsp;&nbsp;' + deleteOperate;
            }
        }],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: false,
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
                "overflow-x": "hidden"
            });
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showAdd: false,
        showUpdate: false,
        title: '',
        companyNameOptions: [],
        detail: {
            name: '',
            companyId: '',
            companyName: '',
            mobile: ''
        },
        q: {
            companyName: '',
            mobile: ''
        },
        saveLock: false
    },
    methods: {
        add: function () {
            this.errors.clear('form-1');
            this.title = '新增';
            this.showAdd = true;
        },
        query: function () {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: {
                    'mobile': vm.q.mobile,
                    'companyName': vm.q.companyName
                }
            }).trigger("reloadGrid");
        },
        save: function () {
            this.$validator.validateAll('form-1').then(function () {
                vm.saveLock = true;
                $.ajax({
                    type: 'post',
                    url: '../companyBuilding/save',
                    dataType: 'json',
                    data: JSON.stringify(vm.detail),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('保存成功!', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    },
                    complete: function(){
                        vm.saveLock = false;
                    }
                })
            }, function () {
                alert('信息填写不完整!');
            });
        },
        update: function () {
            this.$validator.validateAll('form-2').then(function () {
                vm.saveLock = true;
                $.ajax({
                    type: 'post',
                    url: '../companyBuilding/update',
                    dataType: 'json',
                    data: JSON.stringify(vm.detail),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('保存成功!', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    },
                    complete: function(){
                        vm.saveLock = false
                    }
                })
            }, function () {
                alert('信息填写不完整!');
            })
        },
        reload: function () {
            vm.showAdd = false;
            vm.showUpdate = false;
            vm.title = '';
            vm.q = {
                companyName: '',
                mobile: ''
            }
            vm.detail = {
                name: '',
                companyId: '',
                companyName: '',
                mobile: ''
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    },
    created: function () {
        var self = this;
        $.ajax({
            type: 'post',
            url: '../companyCustomer/list',
            dataType: 'json',
            success: function (r) {
                if (r.code == 0) {
                    self.companyNameOptions = r.companyList;
                }
            }
        })
    }
})

function getDetail(id) {
    vm.errors.clear('form-2');
    vm.showUpdate = true;
    vm.title = '编辑';
    $.ajax({
        type: 'post',
        url: '../companyBuilding/info/' + id,
        dataType: 'json',
        success: function (r) {
            if (r.code == 0) {
                vm.detail = r.companyBuilding;
            }
        }
    })
}

function listDelete(id) {
    var data = JSON.stringify([parseInt(id)]);
    confirm('确认删除?', function () {
        $.ajax({
            type: 'post',
            url: '../companyBuilding/delete',
            dataType: 'json',
            data: data,
            success: function (r) {
                if (r.code == 0) {
                    alert('删除成功!', function () {
                        vm.reload();
                    });
                }else{
                    alert(r.msg);
                }
            }
        })
    })
}