Vue.prototype.gridParam = {
    url: '../electrician/list',
    postData: { authStatus: "1" },
    datatype: "json",
    colModel: [{
        label: 'id',
        name: 'id',
        width: 50,
        key: true
    }, {
        label: '名字',
        name: 'userName',
        width: 80
    }, {
        label: '手机号码',
        name: 'mobile',
        width: 80
    }, {
        label: '提交时间',
        name: 'createTime',
        width: 80
    }],
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
}
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
                'authStatus': this.status
            }
            if (this.status == '2' || this.status == '-1') {
                copyGridParam.colModel.push({
                    label: '审核日期',
                    name: 'auditTime'
                }, 
                {
                    label: '审核用户',
                    name: 'auditUser'
                }, 
                {
                    label: '电工简历',
                    name: 'memberId',
                    formatter: function(value, options, row) {
                        var btn = '<span class="btn btn-primary btn-xs" onclick="getResumeInfo(\'' + value + '\')">查看</span>';
                        return btn;
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
        q: {
            mobile: null,
            userName: null
        },
        statusInVm: 1,
        showList: true,
        title: '电工详情',
        roleList: {},
        electrician: {
            status: 1,
            sex: 0,
            idCardFrontImgDo:{},
        	idCardBackImgDo:{},
            roleIdList: [],
            certificateImgList: [],
            certificates: []
        },
        resumeInfo: {
            electricianInfo: {},
            personalInfo: {},
            workExperiences: []
        }

    },
    methods: {
        query: function() {
            vm.reload();
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
        audit: function(authStatus) {
            vm.electrician.authStatus = authStatus;
            var url = "../electrician/audit";
            if(authStatus == -1 ){
                if(vm.electrician.remark == null||!vm.electrician.remark){
                    alert('请输入失败原因！');
                    return;
                }
            }
            if(authStatus==2){
            	var allCer = vm.electrician.certificates;
                for(var i=0;i<allCer.length;i++){
                    if(allCer[i].selected){
                        break;
                    }else{
                        if(i==allCer.length-1){
                            alert('请选择最少一个资质');
                            return;
                        }
                    }
                }
            }
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.electrician),
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
                    url: "../electrician/delete",
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
        getInfo: function(event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            $.get("../electrician/info/" + id, function(r) {
                vm.electrician = r.electrician;
            });
        },
        setCertificate: function(index) {
            vm.electrician.certificates[index].selected = vm.electrician.certificates[index].selected == false;
        },
        reload: function(event) {
            this.errors.clear();
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
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: { 'mobile': vm.q.mobile, 'userName': vm.q.userName },
                page: page
            }).trigger("reloadGrid");
        }
    },
    mounted: function() {
        $("#jqGrid").jqGrid(this.gridParam);
    }
});

function getResumeInfo(id) {
    $.ajax({
        type: 'post',
        url: '../electrician/resume',
        data: JSON.stringify({ id: id }),
        dataType: 'json',
        success: function(r) {
            if (r.success) {
                var obj = r.object;
                vm.resumeInfo.personalInfo = obj.resumeInfo;
                vm.resumeInfo.electricianInfo = obj.electricianInfo;
                vm.resumeInfo.certificateImageList = obj.certificateImageList;
                vm.resumeInfo.workExperiences = obj.experiences;
            } else {
                alert(r.msg);
            }
        }
    });
    var index = layer.open({
        type: 1,
        title: '简历信息',
        area: ['80%', '75%'],
        closeBtn: 2,
        content: jQuery('#resume')
    });
}
