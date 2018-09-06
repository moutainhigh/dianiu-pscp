'use strict';
Vue.prototype.presentStatus = 'ongoing';
Vue.prototype.gridParam = {
    url: '../engineeringproject/list',
    postData: {
        'goStatus': 'ongoing'
    },
    datatype: "json",
    colModel: [{ label: 'id', name: 'id', width: 50, key: true },
        { label: '项目名称', name: 'name', width: 70 },
        { label: '业主单位', name: 'cpName', width: 70 },
        { label: '联系人', name: 'cpContact', width: 70 },
        { label: '手机号码', name: 'cpContactMobile', width: 70 },
        { label: '联系地址', name: 'cpAddress', width: 70 }, {
            label: '项目日期',
            name: 'startTime',
            width: 120,
            formatter: function(value, options, row) {
                return getNowFormatDate(row.startTime) + '至' + getNowFormatDate(row.endTime)

            }
        }, 
        {
            label: '勘查状态',
            name: 'sceneInvestigation',
            width: 80,
            formatter: function(value, options, row) {
            	var label='';
            	if(value === 0){
            		label='<span class="label label-warning">无需勘察</span>';
            	}
            	if(value===1){
            		label='<span class="label label-warning">未勘查</span>';
            	}
            	if(value===2){
            		label='<span class="label label-warning">勘查中</span>';
            	}
            	if(value===3){
            		label='<span class="label label-warning">已勘查</span>';
            	}
            	
                return label;
            }
        },

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
};

var myTabs = ['<div>',
    '   <ul class="nav nav-tabs">',
    '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTap">',
    '           <a @click="selectedTab(tab)"><i :class="\'fa \' + tab.icon"></i>{{ tab.name }}</a>',
    '       </li>',
    '   </ul>',
    '   <div class="tab-detail">',
    '       <slot></slot>',
    '   </div>',
    '</div>'
].join("");

var myTab = ['<div>',
    '</div>'
].join("");

Vue.component('tabs', {
    template: myTabs,
    data: function() {
        return {
            tabs: []
        };
    },
    created: function() {
        this.tabs = this.$children;
    },
    methods: {
        selectedTab: function(_selectedTab) {
            this.tabs.forEach(function(tab) {
                tab.isActive = tab.name === _selectedTab.name;
            });
        }
    }
})

Vue.component('tab', {
    props: {
        status: {
            required: true
        },
        name: {
            required: true
        },
        icon: {
            required: true
        },
        selected: {
            default: false
        }
    },
    template: myTab,
    data: function() {
        return {
            isActive: false,
            copyGridParam: null
        };
    },
    methods: {
        toggleTap: function() {
            vm.statusInVm = this.status;
            var copyGridParam = $.extend(true, {}, this.gridParam);
            copyGridParam.postData = {
                'goStatus': this.status
            }
            $.jgrid.gridUnload('#jqGrid');
            if (this.status === 'end') {
                copyGridParam.colModel.push({
                    label: '项目状态',
                    name: 'createTime',
                    width: 80,
                    formatter: function(value, options, row) {
                        var label = "";
                        if (row.isCancle != null && row.isCancle == 1) {
                            label = '<span class="label label-warning">已取消</span>'
                        } else {
                            label = '<span class="label label-success">已结束</span>';
                        }
                        return label;
                    }
                });
            }
            $.jgrid.gridUnload('#jqGrid');
            $("#jqGrid").jqGrid(copyGridParam);
        }
    },
    mounted: function() {
        this.isActive = this.selected;
    }
})

var vm = new Vue({
    el: '#rrapp',
    data: {
        statusInVm: 'ongoing',
        q: {
            name: '',
            mobile: '',
            contactPerson: '',
            companyname: '',
            startTime: '',
            endTime: ''
        },
        showList: true,
        title: null,
        customerLabel: '请选择',
        engineeringProject: {
            name: null,
            description: '',
            contractFileIdImgDo: {
                status: 0,
                imgId: null,
                imgUrl: null
            },
            startTimeConvert: '',
            endTimeConvert: '',
            sceneInvestigation: 1,
            customerId: null,
            customerName: ''
        },
        customers: [],
        companyCustomer: {

        }
    },
    methods: {
        query: function() {
            var x = new Date(this.q.startTime).getTime();
            var y = new Date(this.q.endTime).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
            } else {
                var postData = {
                    'goStatus': vm.statusInVm,
                    'cpContact': vm.q.cpContact,
                    'name': vm.q.name,
                    'cpName': vm.q.cpName,
                    'cpContactMobile': vm.q.cpContactMobile,
                    'startTimeConvert': vm.q.startTime,
                    'endTimeConvert': vm.q.endTime
                }
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: postData,
                    page: page
                }).trigger("reloadGrid");
            }
        },
        add: function() {
            vm.showList = false;
            vm.title = "新增";
            /*vm.engineeringProject = {};*/
        },
        addCustomer: function() {
            layer.open({
                type: 1,
                title: '新增客户',
                skin: 'layui-layer-molv',
                area: ['75%', '65%'],
                closeBtn: 2,
                btn: ['保存', '取消'],
                yes: function(index, layero) {
                    var url = "../companycustomer/save";
                    vm.$validator.validateAll('form-2').then(function() {
                        $.ajax({
                            type: "POST",
                            url: url,
                            data: JSON.stringify(vm.companyCustomer),
                            success: function(r) {
                                if (r.code == 0) {
                                    alert('操作成功', function() {
                                        layer.close(index);
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
                btn2: function(index, layero) {
                    vm.companyCustomer = {};
                    layer.close(index);
                },
                shadeClose: false,
                content: jQuery('#addCustomer')
            });
        },
        btnCustomer: function() {
            $.ajax({
                type: "POST",
                url: "../companycustomer/getall",
                async: false,
                success: function(r) {
                    if (r.code == 0) {
                        vm.customers = r.companyCustomers;
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        chooseCustomer: function(index) {
            vm.engineeringProject.customerId = vm.customers[index].id;
            vm.customerLabel = vm.customers[index].cpContact;
        },
        sceneToTrue: function() {
            vm.engineeringProject.sceneInvestigation = 1;
        },
        sceneToFalse: function() {
            vm.engineeringProject.sceneInvestigation = 0;
        },
        update: function(event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";
            vm.getInfo(id)
        },
        saveOrUpdate: function() {
            var url = vm.engineeringProject.id == null ? "../engineeringproject/save" : "../engineeringproject/update";
            vm.$validator.validateAll('form-1').then(function() {
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.engineeringProject),
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
                    url: "../engineeringproject/delete",
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
            $.get("../engineeringproject/info/" + id, function(r) {
                vm.engineeringProject = r.bean;
                vm.engineeringProject.startTime = getNowFormatDate(r.bean.startTime);
                vm.engineeringProject.endTime = getNowFormatDate(r.bean.endTime);
                vm.engineeringProject.startTimeConvert = getNowFormatDate(r.bean.startTime);
                vm.engineeringProject.endTimeConvert = getNowFormatDate(r.bean.endTime);
                vm.customerLabel = r.bean.cpName;
            });
        },
        reload: function(event) {
            vm.engineeringProject = {
                name: null,
                description: '',
                contractFileIdImgDo: {
                    status: 0,
                    imgId: null,
                    imgUrl: null
                },
                startTimeConvert: '',
                endTimeConvert: '',
                sceneInvestigation: 1,
                customerId: null,
                customerName: ''
            }
            vm.q = {};
            vm.customers = [];
            vm.customerLabel = '请选择';
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        addImg: function(imgId) {
            $('#' + imgId).trigger('click');
            return false;
        },
        delImg: function() {
            var imgId = arguments[0];
            var length = imgId + 'Length';
            vm.engineeringProject[imgId].status = -1;
            vm.engineeringProject[imgId].imgUrl = '';
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
            if (leng > 0) {
                var reader = new FileReader();
                reader.readAsDataURL(files[0]);
                reader.onload = function(e) {
                    vm.engineeringProject[id].id = null;
                    vm.engineeringProject[id].status = 1;
                    vm.engineeringProject[id].imgUrl = e.target.result;
                };
            }
        },
        jumpOrder: function(){
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var customerId;
            $.get("../engineeringproject/info/" + id, function(r) {
                /*var data = r.bean.customerId + ',' + r.bean.customerName + ',' + r.bean.id + ',' + r.bean.name;*/
                var data = {
                    'customerId' : r.bean.customerId,
                    'customerName' : r.bean.contacts,
                    'id' : r.bean.id,
                    'name' : r.bean.name
                }
                console.log(r);
                localStorage.setItem('projectData', JSON.stringify(data));
                window.top.location.href = '../main.html#cp/workorder.html';
            });
            
            
        }
    },
    mounted: function() {
        $("#jqGrid").jqGrid(this.gridParam);
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
                vm.q.startTime = getNowFormatDate(ev.date);
            }
        });
        $('#date-start-remove').on('click', function() {
            vm.q.startTime = '';
        });
        $('#date-end').on('changeDate', function(ev) {
            if (ev.date) {
                vm.q.endTime = getNowFormatDate(ev.date);
            }
        });
        $('#date-end-remove').on('click', function() {
            vm.q.endTime = '';
        });
        $('#project-start').on('changeDate', function(ev) {
            if (vm.engineeringProject.endTimeConvert) {
                var x = new Date(ev.date).getTime();
                var y = new Date(vm.engineeringProject.endTimeConvert).getTime();
                if (y < x) {
                    alert('结束时间必须晚于开始时间');
                    $(this).find('input[type="text"]').val('');
                    return;
                }
            }
            if (ev.date) {
                vm.engineeringProject.startTimeConvert = getNowFormatDate(ev.date);
                vm.engineeringProject.startTime = getNowFormatDate(ev.date);
            }
        });
        $('#project-end').on('changeDate', function(ev) {
            if (vm.engineeringProject.startTimeConvert) {
                var x = new Date(vm.engineeringProject.startTimeConvert).getTime();
                var y = new Date(ev.date).getTime();
                if (y < x) {
                    alert('结束时间必须晚于开始时间');
                    $(this).find('input[type="text"]').val('');
                    return;
                }
            }
            if (ev.date) {
                vm.engineeringProject.endTimeConvert = getNowFormatDate(ev.date);
                vm.engineeringProject.endTime = getNowFormatDate(ev.date);
            }
        });

    }
});
