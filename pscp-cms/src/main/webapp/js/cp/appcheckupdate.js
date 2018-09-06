$(function() {
    $("#jqGrid").jqGrid({
        url: '../appcheckupdate/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true }, {
                label: '客户端类型',
                name: 'appType',
                width: 80,
                formatter: function(value, options, row) {
                    var lable = "";
                    if (value == 1) {
                        label = "iPhone";
                    }
                    if (value == 2) {
                        label = "Android";
                    }
                    if (value == 3) {
                        label = "iPad";
                    }
                    return label;
                }
            },
            { label: '应用包名', name: 'appPkg', width: 80 },
            { label: '版本号', name: 'appLatestShowVer', width: 80 }, {
                label: '更新类型',
                name: 'updateType',
                width: 80,
                formatter: function(value, options, row) {
                    var lable = "";
                    if (value == 0) {
                        label = "不更新";
                    }

                    if (value == 1) {
                        label = "建议更新";
                    }
                    if (value == 2) {
                        label = "强制更新";
                    }
                    if (value == 3) {
                        label = "静默更新";
                    }
                    return label;
                }
            },
            
            
            { label: '更新地址', name: 'appDownloadUrl', width: 80 },
            { label: '时间', name: 'createTime', width: 80 }, {
                label: '状态',
                name: 'status',
                width: 80,
                formatter: function(value, options, row) {
                    var lable = "";
                    if (value == 0) {
                        label = '<span class="label label-danger">下线</span>';
                    }
                    if (value == 1) {
                        label = '<span class="label label-success">上线</span>';
                    }
                    return label;
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
        persent: 0,
        clientType: [
            { 'name': 'iPhone', 'id': 1 },
            { 'name': 'Android', 'id': 2 },
            { 'name': 'iPad', 'id': 3 }
        ],
        updateType: [
            { 'name': '不更新', 'id': 0 },
            { 'name': '建议更新', 'id': 1 },
            { 'name': '强制更新', 'id': 2 },
            { 'name': '静默更新', 'id': 3 }
        ],
        statusType: [
            { 'name': '上线', 'id': 1 },
            { 'name': '下线', 'id': 0 }
        ],
        uploadType: [
            { 'name': '上传Apk', 'id': 1 },
            { 'name': '更新地址', 'id': 2 }
        ],
        showList: true,
        title: null,
        apkUploadImg: false,
        apkUploadText: false,
        ifUpdate: false,
        q: {
            clientTypeId: '',
            updateTypeId: '',
            statusTypeId: '',
            startTimeConvert: '',
            endTimeConvert: ''
        },
        appCheckUpdate: {
            appLatestVer: '',
            appLatestShowVer: '',
            appPkg: '',
            appLatestSize: '',
            appDownloadUrl: '',
            appDownloadMd5: '',
            updateDesc: '',
            appDownloadType: 1,
            appType: 1,
            updateType: 0
        }
    },

    methods: {
        query: function() {
            var x = new Date(vm.q.startTimeConvert).getTime();
            var y = new Date(vm.q.endTimeConvert).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
                return;
            } else {
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                $("#jqGrid").jqGrid('setGridParam', {
                    page: page,
                    postData: {
                        'appType': vm.q.clientTypeId,
                        'updateType': vm.q.updateTypeId,
                        'status': vm.q.statusTypeId,
                        'startTime': vm.q.startTimeConvert,
                        'endTime': vm.q.endTimeConvert
                    }
                }).trigger("reloadGrid");
            }
        },
        add: function() {
            vm.showList = false;
            vm.title = "新增";
            this.errors.clear('form-1');
        },
        update: function(event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";
            this.errors.clear('form-1');
            vm.ifUpdate = true;
            vm.getInfo(id);
        },
        saveOrUpdate: function(event) {
            this.preventRepeat(event);
            var url = vm.appCheckUpdate.id == null ? "../appcheckupdate/save" : "../appcheckupdate/update";
            vm.$validator.validateAll('form-1').then(function() {
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.appCheckUpdate),
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
                    url: "../appcheckupdate/delete",
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
            $.get("../appcheckupdate/info/" + id, function(r) {
                vm.appCheckUpdate = r.appCheckUpdate;
            });
        },
        changeStatus: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            confirm('确定选中的记录？', function() {
                $.ajax({
                    type: "POST",
                    url: "../appcheckupdate/changeStatus",
                    data: JSON.stringify({ "id": id }),
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
        onFileChange: function(e) {
            var id = e.target.id;
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            var startSub = files[0].name.lastIndexOf('.');
            var endSub = files[0].name.length;
            if (files[0].name.substring(startSub + 1, endSub) == 'apk') {
                this.createApk(id, files);
            } else {
                e.target.value = null;
                alert('文件只支持APK格式');
                return;
            }
        },
        clearFile: function(id) {
            var file = $("#" + id);
            file.after(file.clone().val(""));
            file.remove();
        },
        onprogress: function(evt){
            var loaded = evt.loaded;     //已经上传大小情况 
            var tot = evt.total;      //附件总大小 
            var per = Math.floor(100*loaded/tot);  //已经上传的百分比 
            this.persent = per;
        },
        createApk: function(id, files) {
            if (typeof FileReader === 'undefined') {
                alert('您的浏览器不支持图片上传，请升级您的浏览器');
                return false;
            }
            var fd = new FormData();
            fd.append("type", 'apk');
            fd.append("file", files[0]);
            vm.apkUploadImg = true;
            vm.apkUploadText = false;
            vm.persent = 0;
            var self = this;
            $.ajax({
                url: "../appcheckupdate/apkUpload",
                type: "POST",
                processData: false,
                contentType: false,
                data: fd,
                xhr: function(){
                    var xhr = $.ajaxSettings.xhr();
        　　　　　　if(xhr.upload) {
        　　　　　　　　xhr.upload.addEventListener("progress" , vm.onprogress, false);
        　　　　　　　　return xhr;
        　　　　　　}
                },
                success: function(r) {
                    if (r.code == 0) {
                        self.appCheckUpdate.appLatestSize = r.apkSize;
                        self.appCheckUpdate.appDownloadMd5 = r.apkMd5;
                        self.appCheckUpdate.appDownloadUrl = r.fileUrl;
                        vm.apkUploadImg = false;
                        vm.apkUploadText = true;
                    } else {
                        self.clearFile(id);
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function(event) {
            vm.showList = true;
            vm.q = {
                clientTypeId: '',
                updateTypeId: '',
                statusTypeId: '',
                startTimeConvert: '',
                endTimeConvert: ''
            }
            vm.appCheckUpdate = {
                appLatestVer: '',
                appLatestShowVer: '',
                appPkg: '',
                appLatestSize: '',
                appDownloadUrl: '',
                appDownloadMd5: '',
                updateDesc: '',
                appDownloadType: 1,
                appType: 1,
                updateType: 0
            }
            vm.apkUploadImg = false;
            vm.apkUploadText = false;
            vm.ifUpdate = false;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
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
                vm.q.startTimeConvert = getNowFormatDate(ev.date);
            }
        });
        $('#date-start-remove').on('click', function() {
            vm.q.startTimeConvert = '';
        });
        $('#date-end').on('changeDate', function(ev) {
            if (ev.date) {
                vm.q.endTimeConvert = getNowFormatDate(ev.date);
            }
        });
        $('#date-end-remove').on('click', function() {
            vm.q.endTimeConvert = '';
        });
    }
});