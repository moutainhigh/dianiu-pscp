$(function() {
    $("#jqGrid").jqGrid({
        url: '../companyelectrician/list',
        datatype: "json",
        colModel: [{
                label: 'id',
                name: 'id',
                width: 50,
                key: true,
                hidden: true
            },
            {
                label: 'electricianId',
                name: 'electricianId',
                width: 50,
                hidden: true
            },
            {
                label: 'mainAccount',
                name: 'mainAccount',
                width: 50,
                hidden: true
            },
            {
                label: '手机号码',
                name: 'mobile',
                width: 80,
                formatter: function(value, options, row) {
                    var myValue = '';
                    if (row.mainAccount == true) {
                        myValue = value + '(主)';
                    } else {
                        myValue = value;
                    }

                    return myValue;
                }
            },
            {
                label: '电工姓名',
                name: 'userName',
                width: 80
            }, {
                label: '身份证号码',
                name: 'idCardNo',
                width: 80,
                hidden: true
            }, {
                label: '会员状态',
                name: 'memberStatus',
                width: 80,
                formatter: function(value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                },
                hidden: true
            }, {
                label: '邀请时间',
                name: 'invitationTime',
                width: 80
            }, {
                label: '认证状态',
                name: 'authStatus',
                width: 80,
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

                }
            }, {
                label: '邀请状态',
                name: 'status',
                width: 80,
                formatter: function(value, options, row) {
                    if (value === 0) {
                        return '<span class="label label-warning">邀请中</span>'
                    } else if (value === 1) {
                        return '<span class="label label-success">已绑定</span>'
                    } else if (value === 2) {
                        return '<span class="label label-danger">解绑中</span>'
                    } else if (value === 3) {
                        //电工发起
                        return '<span class="label label-danger">确认解绑</span>'
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
                name: 'electricianId',
                width: 80,
                formatter: function(value, options, row) {
                    var myValue = '';
                    if (row.authStatus == 2) {
                        myValue += '<span class="btn btn-primary btn-xs" onclick="getDetail(\'' + value + '\')">修改</span>&nbsp;&nbsp;';
                    }
                    if (row.status == 1 && row.mainAccount == false) {
                        myValue += '<span class="btn btn-primary btn-xs" onclick="applyUnbind(\'' + row.id + '\')">申请解绑</span>&nbsp;&nbsp;';
                    }
                    if (row.status == 3) {
                        myValue += '<span class="btn btn-primary btn-xs" onclick="confirmBind(\'' + row.id + '\')">同意解绑</span>&nbsp;&nbsp;';
                    }
                    if (row.status == 3) {
                        myValue += '<span class="btn btn-primary btn-xs" onclick="confirmUnbind(\'' + row.id + '\')">拒绝解绑</span>&nbsp;&nbsp;';
                    }
                    if(row.status == -2){
                        myValue += '<span class="btn btn-primary btn-xs" onclick="invitationElectrician(\'' + row.mobile + ',' + row.userName + '\')">邀请</span>&nbsp;&nbsp;';
                    }
                    return myValue;
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
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
                "overflow-x": "hidden"
            });
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            loginName: null,
            mobile: null,
            userName: null
        },
        showList: true,
        isInvitation: true,
        title: null,
        roleList: {},
        idCardFrontImgDo: {
            status: 0,
            id: null,
            imgUrl: null,
            imgId: null
        },
        idCardBackImgDo: {
            status: 0,
            id: null,
            imgUrl: null,
            imgId: null
        },
        certificateImgList: [],
        electrician: {
            status: 1,
            sex: 0,
            roleIdList: [],
            certificateImgList: [],
            mobile: '',
            passwd: '',
            age: '',
            userName: '',
            idCardNo: ''
        },
        electricianInvitationReq: {
            mobile: '',
            userName: ''
        }
    },
    methods: {
        query: function() {
            vm.reload();
        },
        invitation: function() {
            vm.isInvitation = false;
            vm.title = "邀请";
            vm.errors.clear('form-1');
        },
        update: function(event) {
            var id = getSelectedRowElectricianId();
            if (id == null) {
                return;
            }
            if (id === '') {
                alert("未绑定，无法修改电工信息");
                return;
            }
            vm.errors.clear();
            vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id);
        },
        updateElectrician: function(event) {
            this.preventRepeat(event);
            vm.$validator.validateAll().then(function() {
                var btObj = event.target;
                btObj.disabled = true;
                var url = "../companyelectrician/update";
                /*校验电工证书start*/
                if (!vm.certificateImgList || vm.certificateImgList.length == 0) {
                    alert('电工证书不能为空!');
                    return;
                }else{
                    var arr = vm.certificateImgList;
                    var deleteImgLength = 0;
                    for(var i=0;i<arr.length;i++){
                        if(arr[i].status == -1){
                            deleteImgLength ++;
                        }
                    }
                    if(arr.length == deleteImgLength){
                        alert('电工证书不能为空!');
                        return;
                    }
                }
                /*校验电工证书end*/
                vm.electrician.certificateImgList = vm.certificateImgList;
                vm.electrician.idCardFrontImgDo = vm.idCardFrontImgDo;
                vm.electrician.idCardBackImgDo = vm.idCardBackImgDo;
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.electrician),
                    success: function(r) {
                        btObj.disabled = false;
                        if (r.code === 0) {
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
        invitationElectrician: function(event) {
            this.preventRepeat(event);
            vm.$validator.validateAll('form-1').then(function() {
                var btObj = event.target;
                btObj.disabled = true;
                var url = "../companyelectrician/invitation";
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.electricianInvitationReq),
                    success: function(r) {
                        btObj.disabled = false;
                        if (r.code === 0) {
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
        /*del: function(event) {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function() {
                $.ajax({
                    type: "POST",
                    url: "../companyelectrician/delete",
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
        },*/
        applyUnbind: function(event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            confirm('确定要申请解绑？', function() {
                $.ajax({
                    type: "POST",
                    url: "../companyelectrician/applyunbind",
                    data: JSON.stringify({ "invitationId": id }),
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
        confirmUnbind: function(event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            confirm('确定同意/拒绝解绑？', function() {
                $.ajax({
                    type: "POST",
                    url: "../companyelectrician/confirmunbind",
                    data: JSON.stringify({ "invitationId": id, "actionType": 'agree' }), //agree【同意】/reject【拒绝】
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
            $.get("../companyelectrician/info/" + id, function(r) {
                vm.electrician = r.electrician;
                if (vm.electrician.idCardFrontImgDo) {
                    vm.idCardFrontImgDo = vm.electrician.idCardFrontImgDo;
                }
                if (vm.electrician.idCardBackImgDo) {
                    vm.idCardBackImgDo = vm.electrician.idCardBackImgDo;
                }
                if (vm.electrician.certificateImgList) {
                    vm.certificateImgList = vm.electrician.certificateImgList;
                }
            });
        },
        reload: function(event) {
            vm.idCardFrontImgDo = {
                status: 0,
                id: null,
                imgUrl: null,
                imgId: null
            };
            vm.idCardBackImgDo = {
                status: 0,
                id: null,
                imgUrl: null,
                imgId: null
            };
            vm.certificateImgList = [];
            vm.electrician = {
                status: 1,
                sex: 0,
                roleIdList: [],
                certificateImgList: [],
                mobile: '',
                passwd: '',
                age: '',
                userName: '',
                idCardNo: ''
            }
            vm.electricianInvitationReq =  {};
            vm.showList = true;
            vm.isInvitation = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: { 'mobile': vm.q.mobile, 'userName': vm.q.userName },
                page: page
            }).trigger("reloadGrid");
            setTimeout(function() {
                vm.errors.clear();
            }, 50)
        },
        getImageSize: function(certificateImgList) {
            var realLength = 0;
            var len = certificateImgList.length;
            for (var i = 0; i < len; i++) {
                if (certificateImgList[i].status != -1) {
                    realLength++;
                }
            }
            if (realLength < 4) {
                return true;
            }
            return false;

        },
        //图片上传
        addImg: function(imgId) {
            $('#' + imgId).trigger('click');
            return false;
        },
        delImg: function() {
            var imgId = arguments[0];
            var index = arguments[1];
            if (index != null) {
                if (vm.isBase64(vm[imgId][index].imgUrl)) {
                    vm[imgId].splice(index, 1);
                } else {
                    vm[imgId][index].status = -1;
                }
            } else {
                vm[imgId].status = -1;
                vm[imgId].imgUrl = null;
            }
            $('#' + imgId).val('');
        },
        onFileChange: function(e) {
            var id = e.target.id;
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            this.createImage(id, files);
        },
        createImage: function(id, files) {
            if (typeof FileReader === 'undefined') {
                alert('您的浏览器不支持图片上传，请升级您的浏览器');
                return false;
            }
            var leng = files.length;
            var img = vm[id];
            if (this.isArray(img)) {
                for (var i = 0; i < leng; i++) {
                    var reader = new FileReader();
                    reader.readAsDataURL(files[i]);
                    reader.onload = function(e) {
                        vm[id].push({
                            imgUrl: e.target.result,
                            status: 1
                        });
                    };
                }
            } else {
                if (leng > 0) {
                    var reader = new FileReader();
                    reader.readAsDataURL(files[0]);
                    reader.onload = function(e) {
                        vm[id].id = null;
                        vm[id].status = 1;
                        vm[id].imgUrl = e.target.result;
                    };
                }
            }
        },
        isArray: function(o) {
            return Object.prototype.toString.call(o) == '[object Array]';
        },
        isBase64: function(o) {
            return o != null && (o.substring(0, 4) != 'http');
        }
    }
});

function getDetail(id) {
    if (id === '') {
        alert("未绑定，无法查看电工信息");
        return;
    }
    vm.errors.clear();
    vm.showList = false;
    vm.title = '查看';
    $.get("../companyelectrician/info/" + id, function(r) {
        vm.electrician = r.electrician;
        if (vm.electrician.idCardFrontImgDo) {
            vm.idCardFrontImgDo = vm.electrician.idCardFrontImgDo;
        }
        if (vm.electrician.idCardBackImgDo) {
            vm.idCardBackImgDo = vm.electrician.idCardBackImgDo;
        }
        if (vm.electrician.certificateImgList) {
            vm.certificateImgList = vm.electrician.certificateImgList;
        }
    });
}

function applyUnbind(id) {
    confirm('确定要申请解绑？', function() {
        $.ajax({
            type: "POST",
            url: "../companyelectrician/applyunbind",
            data: JSON.stringify({ "invitationId": id }),
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
}

function confirmBind(id) {
    confirm('确定同意/拒绝解绑？', function() {
        $.ajax({
            type: "POST",
            url: "../companyelectrician/confirmunbind",
            data: JSON.stringify({ "invitationId": id, "actionType": 'agree' }), //agree【同意】/reject【拒绝】
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
}

function confirmUnbind(id) {
    confirm('确定同意/拒绝解绑？', function() {
        $.ajax({
            type: "POST",
            url: "../companyelectrician/confirmunbind",
            data: JSON.stringify({ "invitationId": id, "actionType": 'reject' }), //agree【同意】/reject【拒绝】
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
}

function invitationElectrician(params) {
    var arr = params.split(',');
    if(arr&&arr.length > 1){
        var data = {}
        data.mobile = arr[0];
        data.userName = arr[1];
        var url = "../companyelectrician/invitation";
        $.ajax({ 
            type: "POST",
            url: url,
            data: JSON.stringify(data),
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
    }else{
        return;
    }
    
}