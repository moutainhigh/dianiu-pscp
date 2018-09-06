'use strict';
var dataBackup = null;
var mapDialog, recruitDialog;
var gridParam = {
    url: '../cp/workorder/chieforder/list',
    postData: {
        status: 'unconfirm'
    },
    datatype: "json",
    colModel: [{
        label: 'id',
        name: 'id',
        key: true,
        width: 55
    }, {
        label: '工单名称',
        name: 'name'
    }, {
        label: '所属项目',
        name: 'projectName'
    }, {
        label: '业主单位',
        name: 'customerName'
    }, {
        label: '发布日期',
        name: 'publishTime',
        formatter: 'date',
    }, {
        label: '专职电工',
        name: 'enterpriseElectrician',
    }, {
        label: '社会电工',
        name: 'socialElectrician',
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
        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
            "overflow-x": "hidden"
        });
    }
}
var myTabs = ['<div>',
    '   <ul class="nav nav-tabs">',
    '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTab">',
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

var myOverhaul = [
    '<div class="form-group">',
    '   <input type="hidden" class="overhaul-data" @click="pushMyData">',
    '   <div class="col-sm-2 control-label"><span style="color: red;">*</span>检修项目:</div>',
    '   <div class="col-sm-2">',
    '       <input type="text" class="form-control" placeholder="请输入检修项目名称" v-model="getMydata.projectName" data-vv-name="检修项目名称" v-validate="\'required|max:32\'" :class="{\'is-danger\': errors.has(\'检修项目名称\')}" />',
    '       <span v-show="errors.has(\'检修项目名称\')" class="help is-danger">{{ errors.first(\'检修项目名称\') }}</span>',
    '   </div>',
    '   <div class="col-sm-2 control-label"><span style="color: red;">*</span>检修人员姓名:</div>',
    '   <div class="col-sm-2" >',
    '   <v-select multiple :options="getoptions" v-model="getMydata.electrician" placeholder="请选择检修人员" label="name" :class="{\'is-danger\': errors.has(\'检修人员姓名\')}" ></v-select>',
    '   <input type="hidden" v-model="getMydata.electrician" data-vv-name="检修人员姓名" v-validate="\'required\'" > ',
    '       <span v-show="errors.has(\'检修人员姓名\')" class="help is-danger">{{ errors.first(\'检修人员姓名\') }}</span>',
    '   </div>',
    '   <div class="col-sm-2">',
    '       <a v-if="getindex == 0" @click="add"><i class="fa fa-plus-square-o"></i></a>',
    '       <a v-if="getindex != 0" @click="remove"><i class="fa fa-minus-square-o"></i></a>',
    '   </div>',
    '</div>'
].join("");

var myElectrican = [
    '<div>',
    '<hr />',
    '   <input type="hidden" class="electrician-data" @click="pushMyData">',
    '   <div class="form-group">',
    '        <div class="col-sm-2 control-label"><span style="color: red;">*</span>标题:</div>',
    '        <div class="col-sm-2">',
    '           <input type="text" class="form-control" placeholder="请输入标题" v-model="getMydata.title" ',
    '                  data-vv-name="标题" v-validate="\'required|max:32\'" :class="{\'is-danger\': errors.has(\'标题\')}" />',
    '            <span v-show="errors.has(\'标题\')" class="help is-danger">{{ errors.first(\'标题\') }}</span>',
    '        </div>',
    '    </div>',
    '   <div class="form-group">',
    '    <div class="col-sm-2 control-label"><span style="color: red;">*</span>电工资质:</div>',
    '    <div class="col-sm-2">',
    '       <v-select multiple :options="getOptions" v-model="getMydata.qualifications" label="name" track-by="name" :allow-empty="false" placeholder="请选择电工资质" :class="{\'is-danger\': errors.has(\'电工资质\')}"></v-select>',
    '      <input type="hidden" v-model="getMydata.qualifications" data-vv-name="电工资质" v-validate="\'required\'" />',
    '    </div>',
    '    <div class="col-sm-2 control-label"><span style="color: red;">*</span>人数(人):</div>',
    '    <div class="col-sm-1">',
    '       <input type="text" class="form-control" @input="pushQuantity" placeholder="请输入人数" v-model="getMydata.quantity" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')"',
    'onafterpaste="this.value=this.value.replace(/[^0-9]/g,\'\')" maxlength="2" data-vv-name="人数" v-validate="\'required\'" :class="{\'is-danger\': errors.has(\'人数\')}" />',
    '    </div>',
    '    <div class="col-sm-1 control-label"><span style="color: red;">*</span>费用(元/人/天):</div>',
    '    <div class="col-sm-2">',
    '        <input type="text" class="form-control" @input="pushFee" v-model="getMydata.fee" placeholder="请输入费用" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')"',
    'onafterpaste="this.value=this.value.replace(/[^0-9]/g,\'\')" maxlength="4" data-vv-name="费用" v-validate="\'required\'" :class="{\'is-danger\': errors.has(\'费用\')}" />',
    '    </div>',
    '</div>',
    '<div class="form-group">',
    '    <div class="col-sm-2 control-label">',
    '        <span style="color: red;">*</span>需求描述:',
    '    </div>',
    '    <div class="col-sm-2">',
    '        <textarea class="form-control" v-model="getMydata.content" rows="3" placeholder="请输入需求描述" data-vv-name="需求描述" v-validate="\'required|max:128\'" :class="{\'is-danger\': errors.has(\'需求描述\')}" ></textarea>',
    '    </div>',
    '    <div class="col-sm-2 control-label">',
    '        <span style="color: red;">*</span>工作时间:',
    '    </div>',
    '    <div class="col-sm-2">',
    '        <input class="form-control form_datetime tem-picker start" size="16" type="text" readonly data-date-format="yyyy-mm-dd" placeholder="工作开始日期" :class="{\'is-danger\': errors.has(\'工作开始日期\')}" >',
    '        <input type="hidden" v-model="getMydata.beginWorkTime" data-vv-name="工作开始日期" v-validate="\'required\'" @change="pushStartTime">',
    '    </div>',
    '    <div class="col-sm-2" >',
    '        <input class="form-control form_datetime tem-picker end" size="16" type="text" readonly data-date-format="yyyy-mm-dd" placeholder="工作结束日期" :class="{\'is-danger\': errors.has(\'工作结束日期\')}">',
    '        <input type="hidden" v-model="getMydata.endWorkTime" data-vv-name="工作结束日期" v-validate="\'required\'" @change="pushEndTime" >',
    '    </div>',
    '    <div class="col-sm-2">',
    '        <a v-if="getindex == 0" @click="add"><i class="fa fa-plus-square-o"></i></a>',
    '        <a v-if="getindex != 0" @click="remove"><i class="fa fa-minus-square-o"></i></a>',
    '    </div>',
    '</div>',
    '<div class="form-group">',
    '    <div class="col-sm-2 control-label"><span style="color: red;">*</span>派单截止日期:</div>',
    '    <div class="col-sm-2">',
    '        <input class="form-control form_datetime tem-picker cutOff" size="16" type="text" data-date-format="yyyy-mm-dd" placeholder="派单截止日期" readonly :class="{\'is-danger\': errors.has(\'派单截止日期\')}">',
    '        <input type="hidden" v-model="getMydata.expiryTime" data-vv-name="派单截止日期" v-validate="\'required\'">',
    '    </div>',
    '</div>',
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
        toggleTab: function() {
            vm.statusInVm = this.status;
            vm.q = {};
            var copyGridParam = $.extend(true, {}, gridParam);
            copyGridParam.postData = {
                'status': this.status
            }
            $.jgrid.gridUnload('#jqGrid');
            if (this.status === 'started' || this.status === 'completed') {
                copyGridParam.colModel.push({
                    label: '开始时间',
                    name: 'startTime'
                });
                copyGridParam.colModel.push({
                    label: '结束时间',
                    name: 'endTime'
                });
            } else {}
            $("#jqGrid").jqGrid(copyGridParam);
        }
    },
    mounted: function() {
        this.isActive = this.selected;
    }
})

Vue.component('overhaul', {
    template: myOverhaul,
    props: ['mydata', 'index'],
    data: function() {
        return {
            getoptions: [],
            getMydata: [],
            getindex: null
        }
    },
    methods: {
        add: function() {
            this.$emit('add-overhaul');
        },
        remove: function() {
            this.getindex === -1;
            this.$emit('remove-overhaul', [this.getMydata, this.getindex]);
        },
        pushMyData: function() {
            this.$emit('push-overhaul-data', [this.getMydata, this.getindex]);
        }
    },
    created: function() {
        var self = this;
        $.ajax({
            url: '../cp/workorder/chieforder/electrician',
            type: 'post',
            dataType: 'json',
            success: function(r) {
                if (r.code == 0) {
                    self.getoptions = r.electrician;
                } else {
                    alert(r.msg);
                }
            }
        });
        this.getMydata = this.mydata;
        this.getindex = this.index;
    }
});
Vue.component('electricanInfo', {
    template: myElectrican,
    props: ['options', 'mydata', 'index'],
    data: function() {
        return {
            getOptions: null,
            getMydata: [],
            getindex: null
        }
    },
    methods: {
        add: function() {
            this.$emit('add-electrican');
        },
        remove: function() {
            this.getindex === -1;
            this.$emit('remove-electrican', [this.getMydata, this.getindex]);
        },
        pushMyData: function() {
            this.$emit('push-electrican-data', [this.getMydata, this.getindex]);
        },
        pushQuantity: function() {
            this.$emit('push-quantity', [this.getMydata, this.getindex]);
        },
        pushFee: function() {
            this.$emit('push-fee', [this.getMydata, this.getindex]);
        },
        pushStartTime: function() {
            this.$emit('push-start', [this.getMydata, this.getindex]);
        },
        pushEndTime: function() {
            this.$emit('push-end', [this.getMydata, this.getindex]);
        },
        onChangeDatePicker: function() {
            var self = this;
            $('.tem-picker').off('changeDate');
            $('.tem-picker.start').on('changeDate', function(ev) {
                var x = new Date(ev.date).getTime();
                var y = new Date(self.getMydata.endWorkTime).getTime();
                if (y < x) {
                    alert('结束时间必须晚于开始时间');
                    $(this).val('');
                    return;
                } else {
                    self.getMydata.beginWorkTime = ev.date;
                }
            });
            $('.tem-picker.end').on('changeDate', function(ev) {
                var x = new Date(self.getMydata.beginWorkTime).getTime();
                var y = new Date(ev.date).getTime();
                if (y < x) {
                    alert('结束时间必须晚于开始时间');
                    $(this).val('');
                    return;
                } else {
                    self.getMydata.endWorkTime = ev.date;
                }
            });
            $('.tem-picker.cutOff').on('changeDate', function(ev) {
                if (!self.getMydata.beginWorkTime) {
                    alert('请先输入工作开始日期!');
                    $(this).find('input[type="text"]').val('');
                } else {
                    var x = new Date(self.getMydata.beginWorkTime).getTime();
                    var y = new Date(ev.date).getTime();
                    if (y > x) {
                        alert('招募截止日期必须小于工作开始日期！');
                        $(this).val('');
                    } else {
                        self.getMydata.expiryTime = ev.date;
                    }
                }
            });
        }
    },
    created: function() {
        this.getOptions = this.options;
        this.getMydata = this.mydata;
        this.getindex = this.index;
    },
    mounted: function() {
        this.onChangeDatePicker();
        $('.tem-picker').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            minView: 2,
            startDate: new Date
        });
        $('.tem-picker').datetimepicker('update');
    }
});
Vue.component("page", {
    template: "#page",
    data: function() {
        return {
            current: 1,
            showItem: 5,
            allpage: 13
        }
    },
    computed: {
        pages: function() {
            var pag = [];
            if (this.current < this.showItem) { //如果当前的激活的项 小于要显示的条数
                //总页数和要显示的条数那个大就显示多少条
                var i = Math.min(this.showItem, this.allpage);
                while (i) {
                    pag.unshift(i--);
                }
            } else { //当前页数大于显示页数了
                var middle = this.current - Math.floor(this.showItem / 2), //从哪里开始
                    i = this.showItem;
                if (middle > (this.allpage - this.showItem)) {
                    middle = (this.allpage - this.showItem) + 1
                }
                while (i--) {
                    pag.push(middle++);
                }
            }
            return pag
        }
    },
    methods: {
        goto: function(index) {
            if (index == this.current) return;
            this.current = index;
            var data = 'page='+index+'&limit=3';
            $.ajax({
                type: 'get',
                url: '../cp/electrician/worklog/' + vm.presentId + '?' + data,
                datatype: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        if (r.page.totalPage > 0) {
                            vm.showPage = true;
                            self.allpage = r.page.totalPage;
                            vm.workRecord = r.page.list;
                        }
                    } else {
                        r.msg;
                    }
                }
            })
        }
    },
    created: function() {
        var self = this;
        var data = 'page=1&limit=3';
        $.ajax({
            type: 'get',
            url: '../cp/electrician/worklog/' + vm.presentId + '?' + data,
            datatype: 'json',
            success: function(r) {
                if (r.code == 0) {
                    if (r.page.totalPage > 0) {
                        self.allpage = r.page.totalPage;
                        vm.workRecord = r.page.list;
                    }
                } else {
                    r.msg;
                }
            }
        })
    }
})
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showListRecord: true,
        showEvaluation: true,
        showDetailInfo: true,
        updateReadonly: false,
        havingCustomer: false,
        presentId: null,
        isHaving: false,
        imgIp: 'http://192.168.1.251:8091/',
        statusInVm: 'unconfirm',
        leaderIdLabel: '请选择',
        title: '',
        q: {
            name: '',
            projectName: '',
            customerName: '',
            startTime: null,
            endTime: null
        },
        customers: [],
        projects: [],
        electricianOptions: [],
        eleOptions: [],
        certificateOptions: [],
        toolequipmentOptions: [],
        workorder: {
            id: null,
            /*工单信息*/
            name: null,
            startTime: '',
            endTime: '',
            address: null,
            content: null,
            leaderId: null,
            orderId: null,
            longitude: null,
            latitude: null,
            /*客户*/
            customerInfo: {
                'name':null,
                'address': null,
                'id': null,
                'contact':'请选择' ,
                'phone': null,
                'customerId':null
            },
            /*项目*/
            projectInfo: {
                'name': '请选择',
                'id': null
            },
            /*安全措施*/
            safetyMeasures: [],
            measureText: '',
            /*危险因素*/
            identifications: [],
            identificationText: '',
            /*机械设备*/
            toolequipmentInfo: null,
            /*承修信息、工单编号*/
            facilitator: {
                'name': null,
                'phone': null,
                'contacts': null
            },
            /*检修信息*/
            electricianWorkOrderList: [{
                'component': {
                    'componentName': 'overhaul',
                    'projectName': '',
                    'electrician': null
                }
            }],
            /*招募信息*/
            recruitList: []
        },
        recordImgListLength: 0,
        workRecord: [],
        evaluationImgListLength: 0,
        evaluation: {
            serviceQuality: 2,
            serviceSpeed: 3,
            content: '测试数据',
            evaluationImgList: []
        },
        payType: 0,
        amount: null,
        showSocialWorkOrder: true,
        socialWorkOrderView: [],
        orderDetail: {
            workOrder: {},
            workOrderLeader: {},
            customer: {},
            facilitator: {},
            projectVO: {},
            companyWorkOrder: {},
            hazardFactorIdentifications: {},
            safetyMeasures: {},
            carryingTools: {}
        },
        showPage: false
    },
    computed: {
        allFee: function() {
            var arr = this.workorder.recruitList;
            var quantity = 0;
            var fee = 0;
            var allFee = 0;
            var day = 0;
            for (var i = 0; i < arr.length; i++) {
                quantity = arr[i].component.quantity;
                fee = arr[i].component.fee;
                if (arr[i].component.beginWorkTime && arr[i].component.endWorkTime) {
                    day = (new Date(arr[i].component.endWorkTime).getTime() - new Date(arr[i].component.beginWorkTime).getTime()) / (24 * 60 * 60 * 1000);
                    day++;
                } else {
                    day = 0;
                }
                allFee += fee * quantity * day;
            }
            return allFee;
        }
    },
    methods: {
        add: function() {
            var self = this;
            self.workorder.electricianWorkOrderList.splice(0, 1);
            setTimeout(function() {
                self.workorder.electricianWorkOrderList.push({
                    'component': {
                        'componentName': 'overhaul',
                        'projectName': '',
                        'electrician': null
                    }
                });
            }, 50);
            $.ajax({
                url: '../cp/workorder/chieforder/facilitator',
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        self.workorder.orderId = r.facilitator.orderId;
                        self.workorder.facilitator.name = r.facilitator.name;
                        self.workorder.facilitator.phone = r.facilitator.phone;
                        self.workorder.facilitator.contacts = r.facilitator.contacts;
                    } else {
                        alert(r.msg);
                    }
                }
            });
            $.ajax({
                url: '../cp/workorder/chieforder/safetymeasures',
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        self.workorder.safetyMeasures = r.safetyMeasures;
                    } else {
                        alert(r.msg);
                    }
                }
            });
            $.ajax({
                url: '../cp/workorder/chieforder/identifications',
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        self.workorder.identifications = r.identifications;
                    } else {
                        alert(r.msg);
                    }
                }
            });
            this.showList = false;
        },
        update: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.workorder.id = id;
            vm.updateReadonly = true;
            vm.showList = false;
            $.ajax({
                type: 'post',
                url: '../cp/workorder/chieforder/view/' + id,
                success: function(r) {
                    if (r.code == 0) {
                        console.log(r);
                        if (r.result.socialWorkOrderList.length > 0 ) {
                            vm.showSocialWorkOrder = false;
                            vm.socialWorkOrderView = r.result.socialWorkOrderList;
                            var list = r.result.socialWorkOrderList;
                            for (var i = 0; i < list.length; i++) {
                                vm.socialWorkOrderView[i].qualifications = list[i].qualifications.split(',');
                            }
                        }
                        vm.workorder.customerInfo = r.result.customer;
                        vm.workorder.customerInfo.contact = r.result.customer.contacts;
                        vm.workorder.customerInfo.phone = r.result.customer.contactNumber;
                        vm.workorder.facilitator = r.result.facilitator;
                        vm.workorder.facilitator.phone = r.result.facilitator.contactNumber;
                        vm.workorder.orderId = r.result.workOrder.orderId;
                        vm.workorder.name = r.result.workOrder.name;
                        vm.workorder.startTime = r.result.workOrder.startTime;
                        vm.workorder.address = r.result.workOrder.address;
                        vm.leaderIdLabel = r.result.workOrderLeader.name;
                        vm.workorder.leaderId = r.result.workOrderLeader.uid;
                        vm.workorder.content = r.result.workOrder.content;
                        vm.workorder.projectInfo.name = r.result.projectVO.name;
                        vm.workorder.projectInfo.id = r.result.projectVO.id;
                        vm.workorder.endTime = r.result.workOrder.endTime;
                        vm.workorder.latitude = r.result.workOrder.latitude;
                        vm.workorder.longitude = r.result.workOrder.longitude;
                        vm.workorder.identifications = r.result.hazardFactorIdentifications;
                        vm.workorder.identificationText = r.result.identificationText;
                        vm.workorder.safetyMeasures = r.result.safetyMeasures;
                        vm.workorder.measureText = r.result.measureText;
                        vm.workorder.toolequipmentInfo = r.result.carryingTools;
                        var recruitList = r.result.companyWorkOrder;
                        var updateRecruitList = [];
                        vm.workorder.electricianWorkOrderList.splice(0, 1);
                        setTimeout(function() {
                            for (var i = 0; i < recruitList.length; i++) {
                                vm.workorder.electricianWorkOrderList.push({
                                    component: {
                                        'componentName': 'overhaul',
                                        'projectName': recruitList[i].projectName,
                                        'electrician': recruitList[i].electrician,
                                        'projectId': recruitList[i].projectId
                                    }
                                });
                            }
                        }, 50);

                        console.log(vm.workorder.electricianWorkOrderList);

                    } else {

                    }
                }
            });
            /*vm.getInfo(id);*/
        },
        reload: function() {
            vm.workorder.id = null;
            vm.updateReadonly = false;
            vm.isHaving = false;
            vm.showList = true;
            vm.showEvaluation = true;
            vm.showListRecord = true;
            vm.showSocialWorkOrder = true;
            vm.showDetailInfo = true;
            vm.leaderIdLabel = '请选择';
            vm.workorder = $.extend(true, {}, dataBackup);
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        getOrderDetailInfo: function(id) {
            vm.showDetailInfo = false;
            $.ajax({
                type: 'post',
                url: '../cp/workorder/chieforder/view/' + id,
                success: function(r) {
                    if (r.code == 0) {
                        console.log(r);
                        vm.orderDetail = r.result;
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getRecord: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.presentId = id;
            vm.title = '工作记录';
            vm.getOrderDetailInfo(id);
            vm.showListRecord = false;
            $.ajax({
                type: 'post',
                url: '../cp/electrician/worklog/' + id,
                datatype: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        vm.showPage = true;
                    } else {
                        r.msg;
                    }
                }
            })
        },
        evaluate: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.title = '工作评价';
            vm.getOrderDetailInfo(id);
            vm.showEvaluation = false;
            $.ajax({
                type: 'post',
                url: '../cp/workorder/evaluate/' + id,
                datatype: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        if (r.result.workOrderEvaluateInfoList[0]) {
                            vm.evaluation = r.result.workOrderEvaluateInfoList[0];
                            vm.evaluation.evaluationImgList = r.result.workOrderEvaluateInfoList[0].attachmentList;
                        }
                    } else {
                        r.msg;
                    }
                }
            })
        },
        recruit: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            recruitDialog = layer.open({
                type: 2,
                title: '招募社会电工',
                skin: 'layui-layer-molv',
                area: ['98%', '98%'],
                closeBtn: 1,
                shadeClose: false,
                content: 'recruit.html?' + id,
            });
        },
        checkMeasure: function(index, length) {
            var checked = vm.workorder.safetyMeasures[index].checked;
            if (checked === 0) {
                vm.workorder.safetyMeasures[index].checked = 1;
            } else if (checked === 1) {
                vm.workorder.safetyMeasures[index].checked = 0;
            }
            if (index === length - 1) {
                vm.workorder.measureText = '';
            }
        },
        checkIdentification: function(index, length) {
            var checked = vm.workorder.identifications[index].checked;
            if (checked === 0) {
                vm.workorder.identifications[index].checked = 1;
            } else if (checked === 1) {
                vm.workorder.identifications[index].checked = 0;
            }
            if (index === length - 1) {
                vm.workorder.identificationText = '';
            }
        },
        getCustomers: function() {
            $.ajax({
                url: '../cp/workorder/chieforder/customer',
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        vm.customers = r.customer;
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        changeCustomerInfo: function(index) {
            vm.workorder.customerInfo = vm.customers[index];
            vm.workorder.projectInfo = {
                name: '请选择',
                id: null
            };
        },
        getProjects: function() {
            console.log(vm.workorder.customerInfo);
            var id = vm.workorder.customerInfo.id;
            var url = '../cp/workorder/chieforder/project/' + id;
            if (id) {
                $.ajax({
                    url: url,
                    type: 'post',
                    dataType: 'json',
                    success: function(r) {
                        if (r.code == 0) {
                            vm.projects = r.project;
                        } else {
                            alert(r.msg);
                        }
                    }
                })
            } else {
                alert('请先选择客户！');
            }
        },
        changeProjectInfo: function(index) {
            vm.workorder.projectInfo = vm.projects[index];
        },
        chooseLeader: function(index) {
            vm.workorder.leaderId = vm.electricianOptions[index].id;
            vm.leaderIdLabel = vm.electricianOptions[index].name;
            console.log(vm.workorder.leaderId);
        },
        chooseBalance: function() {
            vm.payType = 0;
        },
        chooseAlipay: function() {
            vm.payType = 1;
        },
        chooseWeChat: function() {
            vm.payType = 2;
        },
        listenAdd: function() {
            this.workorder.electricianWorkOrderList.push({
                'component': {
                    'componentName': 'overhaul',
                    'projectName': '',
                    'electrician': [],
                }
            });
        },
        listenAddElectrican: function() {
            this.workorder.recruitList.push({
                'component': {
                    'componentName': 'electricanInfo',
                    'qualifications': [],
                    'beginWorkTime': '',
                    'endWorkTime': '',
                    'expiryTime': '',
                    'quantity': null,
                    'fee': null,
                    'content': ''
                }
            });
        },
        listenRemove: function(data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.electricianWorkOrderList.splice(index, 1);
        },
        listenRemoveElectrican: function(data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList.splice(index, 1);
        },
        listenQuantity: function(data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].quantity = obj.quantity;

        },
        listenFee: function(data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].fee = obj.fee;
        },
        listenStart: function(data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].beginWorkTime = obj.beginWorkTime;
        },
        listenEnd: function(data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].endWorkTime = obj.endWorkTime;
        },
        getOverhaulData: function(data) {
            var obj = data[0];
            var index = data[1];
            var self = this;
            self.workorder.electricianWorkOrderList[index].component.projectName = obj.projectName;
            self.workorder.electricianWorkOrderList[index].component.electrician = obj.electrician;
        },
        getElectricanData: function(data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].component = obj;
        },
        oneValidate: function(allComponent, i, length) {
            if (i < length) {
                allComponent[i].$validator.validateAll().then(function() {
                    i++;
                    if (i === length) {
                        vm.$validator.validateAll('form-1').then(function() {
                            $('.overhaul-data').trigger('click');
                            $('.electrician-data').trigger('click');
                            if (!vm.isHaving) {
                                vm.workorder.needSocialElectrician = 0;
                            } else {
                                vm.workorder.needSocialElectrician = 1;
                            }
                            
                            $.ajax({
                                url: '../cp/workorder/chieforder/save',
                                type: 'post',
                                data: JSON.stringify(vm.workorder),
                                dataType: 'json',
                                success: function(r) {
                                    console.log(r);
                                    if (r.code == 0) {
                                        if (vm.isHaving === false) {
                                            alert('保存成功！', function() {
                                                vm.reload();
                                            })
                                        } else {
                                            var index = layer.open({
                                                type: 1,
                                                title: '选择付款方式',
                                                skin: 'layui-layer-molv',
                                                area: ['300px', '270px'],
                                                closeBtn: 2,
                                                shadeClose: false,
                                                btn: ['确认支付', '取消'],
                                                yes: function() {
                                                    var data = {};
                                                    data.socialWorkOrderIds = r.socialWorkOrderIds;
                                                    data.payType = vm.payType;
                                                    console.log(data);
                                                    $.ajax({
                                                        type: 'post',
                                                        url: '../cp/workorder/chieforder/payment',
                                                        data: JSON.stringify(data),
                                                        async: false,
                                                        success: function(r) {
                                                            if (r.code == 0) {
                                                                console.log(r);
                                                                if (r.result.payType === 1 || r.result.payType === 2) {
                                                                    if ($('#myform')) {
                                                                        $('#myform').remove();
                                                                    }
                                                                    var str = r.result.thirdPartyPaymentInfo;
                                                                    var newUrl = str.replace(/\"/g, "'");
                                                                    var myForm = ['<form id="myform" name="punchout_form" method="post" action="' + r.result.redirectUrl + '" target="_blank">',
                                                                        '    <input type="hidden" name="amount" value="' + r.result.amount + '">',
                                                                        '    <input type="hidden" name="orderId" value="' + r.result.orderId + '">',
                                                                        '    <input type="hidden" name="orderType" value="' + r.result.orderType + '">',
                                                                        '    <input type="hidden" name="payType" value="' + r.result.payType + '">',
                                                                        '    <input type="hidden" name="thirdPartyPaymentInfo" value="' + newUrl + '">',
                                                                        '</form>'
                                                                    ].join("");
                                                                    var form = $(myForm);
                                                                    $(document.body).append(form);
                                                                    layer.close(index);
                                                                    $('#myform')[0].submit();
                                                                    var myDiv = ['<div style="position: fixed;width: 100%;height: 100%;z-index: 9999;background: rgba(0,0,0,.3);top: 0;left: 0;text-align: center;">',
                                                                        '       <div style="margin: 25% auto;">',
                                                                        '           <button type="button" class="btn btn-primary" onclick="location.reload();">支付成功</button>&nbsp;&nbsp;&nbsp;',
                                                                        '           <button type="button" class="b tn btn-warning" onclick="location.reload();">支付失败</button>',
                                                                        '       </div>',
                                                                        '   </div>'
                                                                    ].join("");
                                                                    var div = $(myDiv);
                                                                    $(document.body).append(div);
                                                                } else {
                                                                    alert('余额支付成功！', function() {
                                                                        layer.close(index);
                                                                        vm.reload();
                                                                    });
                                                                }
                                                            } else {
                                                                alert(r.msg);
                                                            }
                                                        }
                                                    });
                                                },
                                                content: jQuery('#payType')
                                            });

                                        }

                                    } else {
                                        alert(r.msg);
                                    }
                                }
                            });

                        }, function() {
                            alert('信息填写不完整或不规范');
                            return;
                        });
                    }
                    vm.oneValidate(allComponent, i, length);
                }, function() {
                    alert('信息填写不完整或不规范');
                    return false;
                });
            }
        },
        save: function(e) {
            var allComponent = vm.$children;
            var length = allComponent.length;
            var i = 0;
            vm.oneValidate(allComponent, i, length);
        },
        query: function() {
            var x = new Date(this.q.startTime).getTime();
            var y = new Date(this.q.endTime).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
            } else {
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                var postData = {
                    'status': vm.statusInVm,
                    'name': vm.q.name,
                    'projectName': vm.q.projectName,
                    'customerName': vm.q.customerName,
                    'startTime': vm.q.startTime,
                    'endTime': vm.q.endTime
                }
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: postData,
                    page: page
                }).trigger("reloadGrid");
            }
        },
        chooseMap: function() {
            mapDialog = layer.open({
                type: 1,
                title: '选择地点',
                skin: 'layui-layer-molv',
                area: ['85%', '75%'],
                closeBtn: 2,
                shadeClose: false,
                content: jQuery('#mapChoose')
            });
        },
        havingToTrue: function() {
            vm.workorder.recruitList = [{
                'component': {
                    'componentName': 'electricanInfo',
                    'qualifications': [],
                    'beginWorkTime': '',
                    'endWorkTime': '',
                    'expiryTime': '',
                    'quantity': null,
                    'fee': null,
                    'content': ''
                }
            }];
            vm.isHaving = true;
        },
        havingToFalse: function() {
            vm.workorder.recruitList = [];
            vm.isHaving = false;
        }
    },
    created: function() {
        var self = this;
        $.ajax({
            type: 'post',
            url: '../cp/workorder/payment/balance',
            success: function(r) {
                if (r.code == 0) {
                    self.amount = r.result.amount;
                } else {
                    alert(r.msg);
                }
            }
        });
        $.ajax({
            url: '../cp/workorder/chieforder/toolequipment',
            type: 'post',
            dataType: 'json',
            success: function(r) {
                if (r.code == 0) {
                    self.toolequipmentOptions = r.toolequipment;
                } else {
                    alert(r.msg);
                }
            }
        });

        $.ajax({
            url: '../cp/workorder/chieforder/certificate',
            type: 'post',
            dataType: 'json',
            success: function(r) {
                if (r.code == 0) {
                    self.certificateOptions = r.certificate;
                } else {
                    alert(r.msg);
                }
            }
        });
        $.ajax({
            url: '../cp/workorder/chieforder/electrician',
            type: 'post',
            dataType: 'json',
            success: function(r) {
                if (r.code == 0) {
                    self.electricianOptions = r.electrician;
                } else {
                    alert(r.msg);
                }
            }
        });
    },
    mounted: function() {
        var self = this;
        $("#jqGrid").jqGrid(gridParam);
        $('.form_datetime.query').datetimepicker({
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
        $('.form_datetime.fix').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            minView: 2,
            startDate: new Date
        });
        $('#date-start').on('changeDate', function(ev) {
            self.q.startTime = getNowFormatDate(ev.date);
        });
        $('#date-start-remove').on('click', function() {
            self.q.startTime = '';
        });
        $('#date-end').on('changeDate', function(ev) {
            self.q.endTime = getNowFormatDate(ev.date);
        });
        $('#date-end-remove').on('click', function() {
            self.q.endTime = '';
        });
        $('#fixStartDate').on('changeDate', function(ev) {
            var x = new Date(getNowFormatDate(ev.date)).getTime();
            var y = new Date(getNowFormatDate(vm.workorder.endTime)).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
                $(this).find('input[type="text"]').val('');
                return;
            } else {
                vm.workorder.startTime = getNowFormatDate(ev.date);
            }
        });
        $('#fixEndDate').on('changeDate', function(ev) {
            var x = new Date(getNowFormatDate(vm.workorder.startTime)).getTime();
            var y = new Date(getNowFormatDate(ev.date)).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
                $(this).find('input[type="text"]').val('');
                return;
            } else {
                vm.workorder.endTime = getNowFormatDate(ev.date);
            }
        });
        dataBackup = $.extend(true, {}, this.workorder);

        var projectData = JSON.parse(localStorage.getItem('projectData'));
        if(projectData){
            self.add();
            self.workorder.customerInfo.contact = projectData.customerName;
            self.workorder.projectInfo.name = projectData.name;
            self.workorder.projectInfo.id = projectData.id;
            self.havingCustomer = true;
            $.ajax({
                url: '../cp/workorder/chieforder/customer',
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        self.customers = r.customer;
                        for(var i=0;i<self.customers.length;i++){
                            if(projectData.customerId == self.customers[i].customerId){
                                self.workorder.customerInfo = self.customers[i];
                            }
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            })
            localStorage.removeItem('projectData');
        }
    }
});
//初始化地图配置
var map = new BMap.Map("allmap");
map.centerAndZoom(new BMap.Point(120.1452077295, 30.2495737566), 16);
map.enableScrollWheelZoom();
map.addControl(new BMap.NavigationControl());
map.addControl(new BMap.ScaleControl());
map.addControl(new BMap.OverviewMapControl({
    isOpen: true
}));

function showInfo(e) {
    layer.confirm(e.point.lng + ", " + e.point.lat + '<br>点击确认保存地点经纬度', {
        btn: ['确认', '取消'],
        yes: function(index) {
            vm.workorder.longitude = e.point.lng;
            vm.workorder.latitude = e.point.lat;
            layer.close(index);
            layer.close(mapDialog);
        },
        btn2: function() {
            layer.close(mapDialog);
        }
    });
}
map.addEventListener("click", showInfo);

function closeEditPage() {
    layer.close(recruitDialog);
    vm.reload();
}
