$(function () {
    $("#jqGrid").jqGrid({
        url: '../companyLine/list',
        datatype: "json",
        colModel: [{
            label: 'id',
            name: 'id',
            width: 50,
            key: true,
            hidden: true
        }, {
            label: '线路名称',
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
            label: '楼宇名称',
            name: 'buildingName'
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
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "id",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        showAdd: false,
        showUpdate: false,
        title: '',
        q: {
            companyName: '',
            mobile: '',
        },
        detail: {
            name: '',
            companyId: '',
            parentId: '',
            meterId: '',
            bindType: '',
            bindId: '',
            meterName: '',
            isReferRoom: 1
        },
        companyNameOptions: [],
        lineOptions: [],
        meterOptions: [],
        bindOptions: [],
        bindTypeReadonly: false,
        equipOrBuildOptions: [],
        saveLock: false
    },
    methods: {
        add: function () {
            this.showAdd = true;
            this.title = '新增';
            this.errors.clear('form-1');
        },
        getTree: function (id) {
            $.ajax({
                type: 'post',
                url: '../companyLine/customerLineList/' + id,
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        ztree = $.fn.zTree.init($("#lineTree"), setting, r.lineList);
                        var node = ztree.getNodeByParam("id", vm.detail.parentId);
                        ztree.selectNode(node);
                        if (!r.lineList || r.lineList.length == 0) {
                            alert('该客户下暂无线路!');
                        }
                    }
                }
            })
        },
        getOptions: function () {
            if (this.showAdd == false && this.showUpdate == false) {
                return;
            }
            var id = vm.detail.companyId;
            if (id) {
                vm.getTree(id);

                $.ajax({
                    type: 'post',
                    url: '../companyLine/customerMeterList/' + id,
                    dataType: 'json',
                    success: function (r) {
                        if (r.code == 0) {
                            vm.meterOptions = r.meterList;
                            if (!vm.meterOptions || vm.meterOptions.length == 0) {
                                alert('该客户下暂无采集点!');
                            }
                        }
                    }
                })

            } else {
                alert('请先选择客户!');
            }

        },
        getEquipOrBuildOptions: function () {
            if (this.showAdd == false && this.showUpdate == false) {
                return;
            }
            var id = vm.detail.companyId;

            if (id) {
                var bindType = vm.detail.bindType;
                if (bindType == 1) {
                    $.ajax({
                        type: 'post',
                        url: '../companyLine/customerBuildingList/' + id,
                        dataType: 'json',
                        success: function (r) {
                            if (r.code == 0) {
                                vm.equipOrBuildOptions = r.buildingList;
                                if (!vm.equipOrBuildOptions || vm.equipOrBuildOptions.length == 0) {
                                    alert('该客户下暂无楼宇!');
                                }
                            }
                        }
                    })
                } else if (bindType == 2) {
                    $.ajax({
                        type: 'post',
                        url: '../companyLine/customerEquipmentList/' + id,
                        dataType: 'json',
                        success: function (r) {
                            if (r.code == 0) {
                                vm.equipOrBuildOptions = r.equipmentList;
                                if (!vm.equipOrBuildOptions || vm.equipOrBuildOptions.length == 0) {
                                    alert('该客户下暂无设备!');
                                }
                            }
                        }
                    })
                }
            } else {
                alert('请先选择客户!');
            }
        },
        setBind: function () {
            var id = vm.detail.parentId;
            if (id == 0) {
                vm.bindTypeReadonly = true;
            } else {
                vm.bindTypeReadonly = false;
            }
        },
        lineTree: function () {
            if (!vm.detail.companyId) {
                alert('请先选择客户');
                return;
            }
            var btn;
            if (vm.showUpdate == true) {
                btn = null
            } else if (vm.showAdd == true) {
                btn = ['确定', '取消']
            }
            layer.open({
                type: 1,
                offset: '50px',
                title: "线路",
                area: ['300px', '350px'],
                content: jQuery("#lineLayer"),
                btn: btn,
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级菜单
                    vm.detail.parentId = node[0].id;
                    vm.detail.parentName = node[0].name;
                    if (vm.detail.parentId == 0) {
                        vm.detail.bindType = 0;
                        vm.detail.bindId = 0;
                        vm.bindTypeReadonly = true;
                    } else {
                        vm.detail.bindType = '';
                        vm.detail.bindId = '';
                        vm.bindTypeReadonly = false;
                    }
                    layer.close(index);
                }
            });
        },
        save: function () {
            this.$validator.validateAll('form-1').then(function () {
                vm.saveLock = true;
                $.ajax({
                    type: 'post',
                    url: '../companyLine/saveOrUpdate',
                    dataType: 'json',
                    data: JSON.stringify(vm.detail),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('保存成功!', function () {
                                vm.reload();
                            });
                        }
                    },
                    complete: function () {
                        vm.saveLock = false;
                    }
                })
            }, function () {
                alert('信息填写不完整!');
            })
        },
        update: function () {
            this.$validator.validateAll('form-2').then(function () {
                vm.saveLock = true;
                $.ajax({
                    type: 'post',
                    url: '../companyLine/saveOrUpdate',
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
                    complete: function () {
                        vm.saveLock = false;
                    }
                })
            }, function () {
                alert('信息填写不完整!');
            })
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
                parentId: '',
                meterId: '',
                bindType: '',
                bindId: '',
                meterName: '',
                isReferRoom: 1
            }
            vm.lineOptions = [];
            vm.meterOptions = [];
            vm.equipOrBuildOptions = [];
            vm.bindTypeReadonly = false;
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

        $.ajax({
            type: 'post',
            url: '../companyLine/bindTypeList',
            dataType: 'json',
            success: function (r) {
                if (r.code == 0) {
                    self.bindOptions = r.bindTypeList;
                }
            }
        })

    }
})

function getDetail(id) {
    vm.showUpdate = true;
    vm.title = '编辑';
    vm.errors.clear('form-2');

    $.ajax({
        type: 'post',
        url: '../companyLine/info/' + id,
        dataType: 'json',
        success: function (r) {
            vm.detail = r.companyLine;
            if (vm.detail.bindType == 0) {
                vm.bindTypeReadonly = true;
            }
            var id = r.companyLine.companyId;
            vm.getTree(id);
            if (vm.detail.meterName) {
                return;
            } else {
                $.ajax({
                    type: 'post',
                    url: '../companyLine/customerMeterList/' + id,
                    dataType: 'json',
                    success: function (r) {
                        if (r.code == 0) {
                            vm.meterOptions = r.meterList;
                            if (!vm.meterOptions || vm.meterOptions.length == 0) {
                                alert('该客户下暂无采集点!');
                            }
                        }
                    }
                })
            }
        }
    })

}

function listDelete(id) {
    var data = JSON.stringify([parseInt(id)]);
    confirm('确认删除?', function () {
        $.ajax({
            type: 'post',
            url: '../companyLine/delete',
            dataType: 'json',
            data: data,
            success: function (r) {
                if (r.code == 0) {
                    alert('删除成功!', function () {
                        vm.reload();
                    });
                } else {
                    alert(r.msg);
                }
            }
        })
    })
}