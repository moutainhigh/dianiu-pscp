//memberType
var js = document.scripts;
var url = js[js.length - 1].src;

var $memberType = getQueryString(url, 'memberType');
Vue.prototype.gridParam = {
    url: '../company/list',
    postData: { 'status': "1",'memberType':$memberType},
    datatype: "json",
    colModel: [
        { label: 'id', name: 'id', width: 50, key: true },
        { label: '手机号', name: 'memberMobile', width: 80 },
        { label: '公司名', name: 'name', width: 80 },
        { label: '联系人', name: 'contacts', width: 80 },
        { label: '联系人手机号', name: 'mobile', width: 80 },
        { label: '提交时间', name: 'createTime', width: 80 }
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
            vm.q.memberType=$memberType;
            var copyGridParam = $.extend(true, {}, this.gridParam);
            copyGridParam.postData = {
                'status': this.status,
                'memberType':$memberType
            }
            if (this.status == '2' || this.status == '-1') {
                copyGridParam.colModel.push({
                    label: '审核日期',
                    name: 'auditTime'
                },{
                    label: '审核用户',
                    name: 'auditUser'
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
        showList: true,
        statusInVm: 1,
        memberTypeVm:$memberType,
        enterpriseQualificationImgListLength: 0,
        province: {
            id: 0,
            name: '请选择省份'
        },
        city: {
            id: 0,
            name: '请选择城市'
        },
        area: {
            id: 0,
            name: '请选择地区'
        },
        company: {
            businessLicenceImgDo: {
                status: 0,
                imgId: null,
                imgUrl: null
            },
            organizationCodeImgDo: {
                status: 0,
                imgId: null,
                imgUrl: null
            },
            applicationLetterFidImgDo: {
                status: 0,
                imgId: null,
                imgUrl: null
            },
            idCardFrontImgDo: {
                status: 0,
                imgId: null,
                imgUrl: null
            },
            idCardBackImgDo: {
                status: 0,
                imgId: null,
                imgUrl: null
            },
            enterpriseQualificationImgList: []
        },
        q: {
            mobile: '',
            name: '',
            contacts: '',
            memberMobile: '',
            loginName: '',
            memberType: ''
        }
    },
    methods: {
        del: function() {
            console.log(1);
        },
        audit: function(num) {
            if(num == -1){
                if(!vm.company.remark){
                    alert('请填写审核失败原因！');
                    return;
                }
            }
            if(num == 2){
            	if(!vm.company.idCardNo){
            		alert('请填写委托人/法人身份证号码！');
                    return;
            	}
            }
            vm.company.status = num;

            $.ajax({
                type: "POST",
                url: "../company/audit",
                data: JSON.stringify(vm.company),
                success: function(r) {
                    if (r.code==0) {
                        alert('操作成功', function(index) {
                            vm.readonly = vm.readonly === false;
                            vm.certificateImgList = [];
                            vm.idCardFrontImgDo = null;
                            vm.idCardBackImgDo = null;
                            vm.applicationLetterFidImg = null;
                            vm.organizationCode = null;
                            vm.businessLicenceImg = null;
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        companyInfo: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            var self = this;
            $.ajax({
                url: '../company/info/' + id,
                type: 'post',
                async: false,
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        self.company = r.company;
                        if (r.company.provinceName) {
                            self.province.name = r.company.provinceName;
                        }
                        if (r.company.cityName) {
                            self.city.name = r.company.cityName;
                        }
                        if (r.company.areaName) {
                            self.area.name = r.company.areaName;
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        query: function() {
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
                postData: { 'memberType':vm.q.memberType,'loginName': vm.q.loginName, 'mobile': vm.q.mobile, 'name': vm.q.name, 'memberMobile': vm.q.memberMobile, 'contacts': vm.q.contacts }
            }).trigger("reloadGrid");
        },
        reload: function() {
            vm.showList = true;
            vm.q = {
            	mobile: '',
                name: '',
                contacts: '',
                memberMobile: '',
                loginName: ''
            }
            //query();
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page,
            }).trigger("reloadGrid");
        }
    },
    mounted: function() {
        $("#jqGrid").jqGrid(this.gridParam);
    }
});
