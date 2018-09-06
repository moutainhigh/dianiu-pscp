'use strict';
var dataBackup = null;
var mapDialog,
    recruitDialog;
var gridParam = {
    url: '../cp/workorder/chieforder/list',
    postData: {
        status: 'unconfirm'
    },
    datatype: "json",
    colModel: [
        {
            label: 'id',
            name: 'id',
            key: true,
            hidden: true
        }, {
            label: '订单编号',
            name: 'orderId',
            hidden: true
        }, {
            label: '工单名称',
            name: 'name'
        }, {
            label: '工单类型',
            name: 'typeName',
            width: 75
        }, {
            label: '所属项目',
            name: 'projectName'
        }, {
            label: '业主单位',
            name: 'customerName'
        }, {
            label: '发布日期',
            name: 'publishTime',
            width: 100
        }, {
            label: '企业电工',
            name: 'enterpriseElectrician',
            width: 80
        }, {
            label: '社会电工',
            name: 'socialElectrician',
            width: 80
        }, {
            label: '负责人',
            name: 'leaderName',
            width: 75
        }, {
            label: '派单人',
            name: 'dispatchPersonnel',
            width: 75
        }, {
            label: '最后修改',
            width: 200,
            formatter: function (value, options, row) {
                var name = row.lastAmendment;
                var time = row.lastModifiedTime;
                if (!row.lastAmendment || row.lastAmendment == 'null') {
                    name = null;
                }
                if (!row.lastModifiedTime || row.lastModifiedTime == 'null') {
                    time = null;
                }
                if (name == null && time == null) {
                    return '';
                } else if (name == null) {
                    return time;
                } else if (time == null) {
                    return name;
                } else {
                    return name + '-' + time;
                }
            }
        }
    ],
    viewrecords: true,
    height: 385,
    rowNum: 10,
    rowList: [
        10, 30, 50
    ],
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
    gridComplete: function () {
        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
    }
}
var myTabs = [
    '<div>',
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

var myTab = ['<div>', '</div>'].join("");

var myOverhaul = [
    '<div class="form-group">',
    '   <input type="hidden" class="overhaul-data" @click="pushMyData">',
    '   <div class="col-sm-2 control-label"><span style="color: red;">*</span>{{ getShowTypeName }}项目:</div>',
    '   <div class="col-sm-2">',
    '       <input type="text" class="form-control" placeholder="请输入项目名称" v-model="getMydata.projectName" data-vv-name="项目名称" v-validate="\'required|max:50\'" :class="{\'is-danger\': errors.has(\'项目名称\')}" />',
    '       <span v-show="errors.has(\'项目名称\')" class="help is-danger">{{ errors.first(\'项目名称\') }}</span>',
    '   </div>',
    '   <div class="col-sm-2 control-label"><span style="color: red;">*</span>{{ getShowTypeName }}人员姓名:</div>',
    '   <div class="col-sm-2" >',
    '   <v-select multiple :options="getoptions" v-model="getMydata.electrician" placeholder="请选择人员" label="name" :class="{\'is-danger\': errors.has(\'人员姓名\')}" ></v-select>',
    '   <input type="hidden" v-model="getMydata.electrician" data-vv-name="人员姓名" v-validate="\'required\'" > ',
    '       <span v-show="errors.has(\'人员姓名\')" class="help is-danger">{{ errors.first(\'人员姓名\') }}</span>',
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
    '                  data-vv-name="标题" v-validate="\'required|max:50\'" :class="{\'is-danger\': errors.has(\'标题\')}" />',
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
    '<div class="col-sm-1">',
    '  <input type="text" class="form-control" @input="pushQuantity" placeholder="请输入人数" v-model="getMydata.quantity"',
    ' maxlength="2" data-vv-name="人数" v-validate="\'required|myint\'" :class="{\'is-danger\': errors.has(\'人数\')}" />',
    '    </div>',
    '    <div class="col-sm-1 control-label"><span style="color: red;">*</span>费用(元/人/天):</div>',
    '    <div class="col-sm-2">',
    '        <input type="text" class="form-control" @input="pushFee" v-model="getMydata.fee" placeholder="请输入费用"',
    ' maxlength="5" data-vv-name="费用" v-validate="\'required|money\'" :class="{\'is-danger\': errors.has(\'费用\')}" />',
    '    </div>',
    '</div>',
    '<div class="form-group">',
    '    <div class="col-sm-2 control-label">',
    '        <span style="color: red;">*</span>需求描述:',
    '    </div>',
    '    <div class="col-sm-2">',
    '        <textarea class="form-control" v-model="getMydata.content" rows="3" placeholder="请输入需求描述" data-vv-name="需求描述" v-validate="\'required|max:1000\'" :class="{\'is-danger\': errors.has(\'需求描述\')}" ></textarea>',
    '    </div>',
    '    <div class="col-sm-2 control-label">',
    '        <span style="color: red;">*</span>工作时间:',
    '    </div>',
    '    <div class="col-sm-2">',
    '        <input :id="startTimeId" class="form-control form_datetime tem-picker" size="16" type="text" readonly data-date-format="yyyy-mm-dd" placeholder="工作开始日期" :class="{\'is-danger\': errors.has(\'工作开始日期\')}" >',
    '        <input type="hidden" v-model="getMydata.beginWorkTime" data-vv-name="工作开始日期" v-validate="\'required\'" @change="pushStartTime">',
    '    </div>',
    '    <div class="col-sm-2" >',
    '        <input :id="endTimeId" class="form-control form_datetime tem-picker" size="16" type="text" readonly data-date-format="yyyy-mm-dd" placeholder="工作结束日期" :class="{\'is-danger\': errors.has(\'工作结束日期\')}">',
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
    '        <input :id="expiryTimeId" class="form-control form_datetime tem-picker-cut" size="16" type="text" data-date-format="yyyy-mm-dd" placeholder="派单截止日期" readonly :class="{\'is-danger\': errors.has(\'派单截止日期\')}">',
    '        <input type="hidden" v-model="getMydata.expiryTime" data-vv-name="派单截止日期" v-validate="\'required\'">',
    '    </div>',
    '</div>',
    '</div>'
].join("");
Vue.component('tabs', {
    template: myTabs,
    data: function () {
        return {tabs: []};
    },
    created: function () {
        this.tabs = this.$children;
    },
    methods: {
        selectedTab: function (_selectedTab) {
            this.tabs.forEach(function (tab) {
                tab.isActive = tab.name === _selectedTab.name;
            });
        }
    }
});

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
    data: function () {
        return {isActive: false, copyGridParam: null};
    },
    methods: {
        toggleTab: function () {
            vm.statusInVm = this.status;
            vm.q = {};
            var copyGridParam = $.extend(true, {}, gridParam);
            copyGridParam.postData = {
                'status': this.status
            }
            $.jgrid.gridUnload('#jqGrid');
            if (this.status === 'started' || this.status === 'completed') {
                copyGridParam.colModel.push({label: '开始时间', name: 'startTime'});
                copyGridParam.colModel.push({label: '结束时间', name: 'endTime'});
            } else {
            }
            $("#jqGrid").jqGrid(copyGridParam);
        }
    },
    mounted: function () {
        this.isActive = this.selected;
    }
});

Vue.component('overhaul', {
    template: myOverhaul,
    props: [
        'mydata', 'index', 'detailtypename'
    ],
    data: function () {
        return {getoptions: [], getMydata: [], getindex: null, getShowTypeName: ''}
    },
    methods: {
        add: function () {
            this.$emit('add-overhaul');
        },
        remove: function () {
            this.getindex === -1;
            this.$emit('remove-overhaul', [this.getMydata, this.getindex]);
        },
        pushMyData: function () {
            this.$emit('push-overhaul-data', [this.getMydata, this.getindex]);
        }
    },
    created: function () {
        var self = this;
        $.ajax({
            url: '../cp/workorder/chieforder/electrician',
            type: 'post',
            dataType: 'json',
            success: function (r) {
                if (r.code == 0) {
                    if (r.electrician == null) {
                        self.getoptions = [];
                        return;
                    }
                    self.getoptions = r.electrician;
                } else {
                    alert(r.msg);
                }
            }
        });
        this.getMydata = this.mydata;
        this.getindex = this.index;
        this.getShowTypeName = this.detailtypename;
    }
});
Vue.component('electricanInfo', {
    template: myElectrican,
    props: [
        'options', 'mydata', 'index'
    ],
    data: function () {
        return {getOptions: null, getMydata: [], getindex: null}
    },
    methods: {
        add: function () {
            this.$emit('add-electrican');
        },
        remove: function () {
            this.getindex === -1;
            this.$emit('remove-electrican', [this.getMydata, this.getindex]);
        },
        pushMyData: function () {
            this.$emit('push-electrican-data', [this.getMydata, this.getindex]);
        },
        pushQuantity: function () {
            this.$emit('push-quantity', [this.getMydata, this.getindex]);
        },
        pushFee: function () {
            this.$emit('push-fee', [this.getMydata, this.getindex]);
        },
        pushStartTime: function () {
            this.$emit('push-start', [this.getMydata, this.getindex]);
        },
        pushEndTime: function () {
            this.$emit('push-end', [this.getMydata, this.getindex]);
        },
        onChangeDatePicker: function () {
            var self = this;
            $('#' + self.startTimeId).on('changeDate', function (ev) {
                var x = new Date(ev.date).getTime();
                var y = new Date(self.getMydata.endWorkTime).getTime();
                var z = new Date(self.getMydata.expiryTime).getTime();
                if (y < x) {
                    alert('结束时间必须晚于开始时间!');
                    $(this).val('');
                    self.getMydata.beginWorkTime = '';
                    return;
                } else if (x < z) {
                    alert('截止时间不能晚于开始时间!');
                    $(this).val('');
                    self.getMydata.beginWorkTime = '';
                    return;
                } else {
                    self.getMydata.beginWorkTime = ev.date;
                }
            });
            $('#' + self.endTimeId).on('changeDate', function (ev) {
                var x = new Date(self.getMydata.beginWorkTime).getTime();
                var y = new Date(ev.date).getTime();
                if (y < x) {
                    alert('结束时间必须晚于开始时间');
                    $(this).val('');
                    self.getMydata.endWorkTime = '';
                    return;
                } else {
                    self.getMydata.endWorkTime = ev.date;
                }
            });
            $('#' + self.expiryTimeId).on('changeDate', function (ev) {
                if (!self.getMydata.beginWorkTime) {
                    alert('请先输入工作开始日期!');
                    $(this).val('');
                    self.getMydata.expiryTime = '';
                    return;
                } else {
                    var x = new Date(self.getMydata.beginWorkTime).getTime();
                    var y = new Date(ev.date).getTime();
                    if (y >= x) {
                        alert('招募截止日期必须小于工作开始日期！');
                        $(this).val('');
                        self.getMydata.expiryTime = '';
                        return;
                    } else {
                        self.getMydata.expiryTime = ev.date;
                    }
                }
            });
        }
    },
    created: function () {
        this.getOptions = this.options;
        this.getMydata = this.mydata;
        this.getindex = this.index;
        this.startTimeId = 'startTime' + this.index;
        this.endTimeId = 'endTime' + this.index;
        this.expiryTimeId = 'expiryTime' + this.index;
    },
    mounted: function () {

        var nowDate = new Date(getNowFormatDate(new Date()));
        var mydate = new Date(nowDate.getTime() + 24 * 60 * 60 * 1000);
        $('.tem-picker').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: false,
            autoclose: 1,
            todayHighlight: false,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            minView: 2,
            startDate: mydate,
            initialDate: mydate
        });
        $('.tem-picker').datetimepicker('update');
        $('.tem-picker-cut').datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: false,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            minView: 2,
            startDate: new Date(nowDate)
        });
        $('.tem-picker-cut').datetimepicker('update');
        this.onChangeDatePicker();
    }
});
Vue.component("page", {
    template: "#page",
    data: function () {
        return {current: 1, showItem: 5, allpage: 13}
    },
    computed: {
        pages: function () {
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
        goto: function (index) {
            var self = this;
            if (index == this.current)
                return;
            this.current = index;
            var data = 'page=' + index + '&limit=3';
            $.ajax({
                type: 'get',
                url: '../cp/electrician/worklog/' + vm.recordOrderId + '?' + data,
                datatype: 'json',
                success: function (r) {
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
    },
    created: function () {
        var self = this;
        var data = 'page=1&limit=3';
        $.ajax({
            type: 'get',
            url: '../cp/electrician/worklog/' + vm.recordOrderId + '?' + data,
            datatype: 'json',
            success: function (r) {
                if (r.code == 0) {
                    if (r.page.totalPage > 0) {
                        self.allpage = r.page.totalPage;
                        vm.workRecord = r.page.list;
                    } else {
                    }
                } else {
                    r.msg;
                }
            }
        })
    }
})
//详情分页开始
//缺陷记录
Vue.component("page-defect-report", {
    template: "#page",
    data: function () {
        return {current: 1, showItem: 5, allpage: 13}
    },
    computed: {
        pages: function () {
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
        goto: function (index) {
            var self = this;
            if (index == this.current)
                return;
            this.current = index;
            var data = 'page=' + index + '&limit=10';
            $.ajax({
                type: 'get',
                url: '../cp/workorder/chieforder/defectrecord?orderId=' + vm.recordOrderId + '&' + data,
                datatype: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.page.totalPage > 0) {
                            self.allpage = r.page.totalPage;
                            vm.defectReport = r.page.list;
                        }
                    } else {
                        r.msg;
                    }
                }
            })
        }
    },
    created: function () {
        var self = this;
        var data = 'page=1&limit=10';
        $.ajax({
            type: 'get',
            url: '../cp/workorder/chieforder/defectrecord?orderId=' + vm.recordOrderId + '&' + data,
            datatype: 'json',
            success: function (r) {
                if (r.code == 0) {
                    if (r.page.totalPage > 0) {
                        self.allpage = r.page.totalPage;
                        vm.defectReport = r.page.list;
                    }
                } else {
                    r.msg;
                }
            }
        })
    }
})
//修试记录
Vue.component("page-fix-report", {
    template: "#page",
    data: function () {
        return {current: 1, showItem: 5, allpage: 13}
    },
    computed: {
        pages: function () {
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
        goto: function (index) {
            var self = this;
            if (index == this.current)
                return;
            this.current = index;
            var data = 'page=' + index + '&limit=10';
            $.ajax({
                type: 'get',
                url: '../cp/workorder/chieforder/repairtest?orderId=' + vm.recordOrderId + '&' + data,
                datatype: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.page.totalPage > 0) {
                            self.allpage = r.page.totalPage;
                            vm.fixReport = r.page.list;
                        }
                    } else {
                        r.msg;
                    }
                }
            })
        }
    },
    created: function () {
        var self = this;
        var data = 'page=1&limit=10';
        $.ajax({
            type: 'get',
            url: '../cp/workorder/chieforder/repairtest?orderId=' + vm.recordOrderId + '&' + data,
            datatype: 'json',
            success: function (r) {
                if (r.code == 0) {
                    if (r.page.totalPage > 0) {
                        self.allpage = r.page.totalPage;
                        vm.fixReport = r.page.list;
                    } else {
                        vm.imFix = false;
                    }
                } else {
                    r.msg;
                }
            }
        })
    }
})
//巡视记录
Vue.component("page-patrol-report", {
    template: "#page",
    data: function () {
        return {current: 1, showItem: 5, allpage: 13}
    },
    computed: {
        pages: function () {
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
        goto: function (index) {
            var self = this;
            if (index == this.current)
                return;
            this.current = index;
            var data = 'page=' + index + '&limit=10';
            $.ajax({
                type: 'get',
                url: '../cp/workorder/chieforder/patrol?orderId=' + vm.recordOrderId + '&' + data,
                datatype: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.page.totalPage > 0) {
                            self.allpage = r.page.totalPage;
                            vm.patrolReport = r.page.list;
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            })
        }
    },
    created: function () {
        var self = this;
        var data = 'page=1&limit=10';
        $.ajax({
            type: 'get',
            url: '../cp/workorder/chieforder/patrol?orderId=' + vm.recordOrderId + '&' + data,
            datatype: 'json',
            success: function (r) {
                if (r.code == 0) {
                    if (r.page.totalPage > 0) {
                        self.allpage = r.page.totalPage;
                        vm.patrolReport = r.page.list;

                    } else {
                        vm.imPatrol = false;
                    }
                } else {
                    alert(r.msg);
                }
            }
        })
    }
})

//勘查记录
Vue.component("page-exploration-report", {
    template: "#page",
    data: function () {
        return {current: 1, showItem: 5, allpage: 13}
    },
    computed: {
        pages: function () {
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
        goto: function (index) {
            var self = this;
            if (index == this.current)
                return;
            this.current = index;
            var data = 'page=' + index + '&limit=10';
            $.ajax({
                type: 'get',
                url: '../cp/workorder/chieforder/survey?orderId=' + vm.recordOrderId + '&' + data,
                datatype: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.page.totalPage > 0) {
                            self.allpage = r.page.totalPage;
                            vm.explorationReport = r.page.list;
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            })
        }
    },
    created: function () {
        var self = this;
        var data = 'page=1&limit=10';
        $.ajax({
            type: 'get',
            url: '../cp/workorder/chieforder/survey?orderId=' + vm.recordOrderId + '&' + data,
            datatype: 'json',
            success: function (r) {
                if (r.code == 0) {
                    if (r.page.totalPage > 0) {
                        self.allpage = r.page.totalPage;
                        vm.explorationReport = r.page.list;

                    } else {
                        vm.imExploration = false;
                    }
                } else {
                    r.msg;
                }
            }
        })
    }
})

var infotabs = [
    '<div class="main-tab">',
    '   <div class="tabs">',
    '     <ul class="nav nav-tabs info-tab" style="padding: 10px 15px;">',
    '       <li v-for="tab in tabs" :class="{\'is-active active\': tab.isActive}" @click="tab.toggleTap" style="display: inline-block;" role="presentation">',
    '           <a @click="selectedTab(tab)" :id="tab.id">{{ tab.name }}</a>',
    '       </li>',
    '     </ul>',
    '   </div>',
    '   <div class="tab-detail">',
    '       <slot></slot>',
    '   </div>',
    '</div>'
].join("");

var infotab = ['<div v-show="isActive">', '   <slot></slot>', '</div>'].join("");
//设置
Vue.component('infotabs', {
    template: infotabs,
    data: function data() {
        return {tabs: []};
    },
    methods: {
        selectedTab: function (_selectedTab) {
            this.tabs.forEach(function (tab) {
                tab.isActive = tab.name === _selectedTab.name;
            });
        }
    },
    created: function () {
        this.tabs = this.$children;
    }
});
Vue.component('infotab', {
    props: {
        name: {
            required: true
        },
        selected: {
            default: false
        }
    },
    template: infotab,
    data: function () {
        return {isActive: false};
    },
    methods: {
        toggleTap: function () {
            if (this.name === '我是服务商') {
            } else {
            }
        }
    },
    mounted: function () {
        this.isActive = this.selected;
    }
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showListRecord: true,
        showEvaluation: true,
        showDetailInfo: true,
        showHf: false,
        showSf: false,
        updateReadonly: false,
        havingCustomer: false,
        presentId: null,
        isHaving: false,
        statusInVm: 'unconfirm',
        leaderIdLabel: '请选择',
        title: '',
        presentOrderId: null,
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
        typeOptions: [],
        defectOptions: [],
        workorder: {
            id: null,
            type: 1,
            typeName: '检修工单',
            /* 工单信息 */
            name: null,
            startTime: '',
            endTime: '',
            address: null,
            content: null,
            leaderId: null,
            orderId: null,
            longitude: null,
            latitude: null,
            /* 客户 */
            customerInfo: {
                'name': '请选择',
                'address': null,
                'id': null,
                'contact': null,
                'phone': null,
                'customerId': null
            },
            /* 项目 */
            projectInfo: {
                'name': '请选择',
                'id': null
            },
            /* 安全措施 */
            safetyMeasures: [],
            measureText: '',
            /* 危险因素 */
            identifications: [],
            identificationText: '',
            /* 机械设备 */
            toolequipmentInfo: [],
            /* 承修信息、工单编号 */
            facilitator: {
                'name': null,
                'phone': null,
                'contacts': null
            },
            /* 检修信息 */
            electricianWorkOrderList: [
                {
                    'component': {
                        'componentName': 'overhaul',
                        'projectName': '',
                        'electrician': null
                    }
                }
            ],
            /* 招募信息 */
            recruitList: [],
            /* 缺陷报告 */
            defect: [],
            defectRecords: ''
        },
        recordImgListLength: 0,
        workRecord: [],
        evaluationImgListLength: 0,
        evaluation: {
            serviceQuality: 0,
            serviceSpeed: 0,
            content: '',
            evaluationImgList: []
        },
        payType: null,
        amount: 0,
        payTypeList: [],
        payTypeCheckedId: null,
        payAmount: 0,
        walletAmount: 0,
        showSocialWorkOrder: true,
        socialWorkOrderView: [],
        orderDetail: {
            workOrder: {},
            workOrderLeader: {},
            customerInfo: {},
            companyInfo: {},
            projectVO: {},
            companyWorkOrder: {},
            hazardFactorIdentifications: {},
            safetyMeasures: {},
            carryingTools: [],
            socialWorkOrderList: []
        },
        fixReport: null,
        patrolReport: null,
        defectReport: null,
        explorationReport: null,
        imSocial: true,
        imFix: true,
        imDefect: true,
        imPatrol: true,
        imExploration: true,
        showTypeName: '检修',
        toolequipmentInfoShow: '',
        defectShow: '',
        defectDetailInfo: {},
        fixDetailInfo: {},
        patrolDetailInfo: {},
        workLogDetailInfo: {},
        mapAddress: '',
        flag: ''
    },
    computed: {
        allFee: function () {
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
        addBefore: function () {
            this.flag = 1
            var self = this;
            var chooseOrderType = layer.open({
                type: '1',
                title: '选择工单',
                area: [
                    '500px', '300px'
                ],
                btn: [
                    '确认', '取消'
                ],
                yes: function () {
                    vm.add();
                    layer.close(chooseOrderType);
                },
                cancel: function () {
                    vm.workorder.type = 1;
                },
                btn2: function () {
                    vm.workorder.type = 1;
                },
                content: jQuery('#orderType')
            });

        },
        add: function () {
            var self = this;
            self.errors.clear();
            self.errors.clear('form-1');
            self.workorder.electricianWorkOrderList.splice(0, 1);
            setTimeout(function () {
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
                success: function (r) {
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
                success: function (r) {
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
                success: function (r) {
                    if (r.code == 0) {
                        self.workorder.identifications = r.identifications;
                    } else {
                        alert(r.msg);
                    }
                }
            });
            $.ajax({
                url: '../cp/workorder/chieforder/toolequipment',
                type: 'post',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.toolequipment) {
                            self.toolequipmentOptions = r.toolequipment;
                            for (var i = 0; i < self.toolequipmentOptions.length; i++) {
                                self.toolequipmentOptions[i].num = 1;
                            }
                        } else {
                            self.toolequipmentOptions = [];
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            });
            //layer.close(chooseOrderType);
            self.showList = false;
        },
        update: function () {
            this.errors.clear();
            this.errors.clear('form-1');
            this.flag = 2
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var orderId = getSelectedRowOrderId();
            vm.presentOrderId = orderId;
            vm.workorder.id = id;
            vm.updateReadonly = true;
            vm.showList = false;
            $.ajax({
                url: '../cp/workorder/chieforder/toolequipment',
                type: 'post',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.toolequipment) {
                            vm.toolequipmentOptions = r.toolequipment;
                            for (var i = 0; i < vm.toolequipmentOptions.length; i++) {
                                vm.toolequipmentOptions[i].num = 1;
                            }
                        } else {
                            vm.toolequipmentOptions = [];
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            });
            $.ajax({
                type: 'post',
                url: '../cp/workorder/chieforder/view/' + orderId,
                success: function (r) {
                    if (r.code == 0) {
                        if (r.result.socialWorkOrderList && r.result.socialWorkOrderList.length > 0) {
                            vm.showSocialWorkOrder = false;
                            vm.socialWorkOrderView = r.result.socialWorkOrderList;
                            var list = r.result.socialWorkOrderList;
                            for (var i = 0; i < list.length; i++) {
                                vm.socialWorkOrderView[i].qualifications = list[i].qualifications.split(',');
                            }
                        }
                        vm.workorder.customerInfo = r.result.customerInfo;
                        vm.workorder.customerInfo.contact = r.result.customerInfo.contacts;
                        vm.workorder.customerInfo.phone = r.result.customerInfo.contactNumber;
                        vm.workorder.facilitator = r.result.companyInfo;
                        vm.workorder.facilitator.phone = r.result.companyInfo.contactNumber;
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
                        // 经纬度转地址
                        var url = 'http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=' + vm.workorder.latitude + ',' + vm.workorder.longitude + '&output=json&pois=1&ak=fNvowSBXwhZgg7YkLsP1PHFy';
                        $.ajax({
                            type: 'get',
                            url: url,
                            dataType: 'JSONP',
                            success: function (r) {
                                if (r.status == 0) {
                                    vm.mapAddress = '<span style="font-size: 12px;">地点:&nbsp;&nbsp;' + r.result.formatted_address + r.result.sematic_description + '</span>';
                                } else {
                                    return;
                                }
                            }
                        });
                        vm.workorder.identifications = r.result.hazardFactorIdentifications;
                        vm.workorder.identificationText = r.result.identificationText;
                        vm.workorder.safetyMeasures = r.result.safetyMeasures;
                        vm.workorder.measureText = r.result.measureText;
                        vm.workorder.toolequipmentInfo = r.result.carryingTools;
                        //对比携带工具
                        var fiArr = [];
                        var arr = vm.workorder.toolequipmentInfo;
                        if (arr && arr.length > 0) {
                            for (var i = 0; i < arr.length; i++) {
                                fiArr.push('<span class="tool-item">' + arr[i].name + '*' + arr[i].num + '</span>');
                                for (var j = 0; j < vm.toolequipmentOptions.length; j++) {
                                    if (vm.toolequipmentOptions[j].name == arr[i].name) {
                                        vm.toolequipmentOptions[j].checked = 1;
                                        vm.toolequipmentOptions[j].num = arr[i].num;
                                    }
                                }
                            }
                        }
                        vm.toolequipmentInfoShow = '已添加:&nbsp;&nbsp;' + fiArr.join('&nbsp;');

                        vm.workorder.typeName = r.result.workOrder.typeName;
                        vm.workorder.defect = r.result.defectRepairList;
                        var recruitList = r.result.companyWorkOrder;
                        var updateRecruitList = [];
                        vm.workorder.electricianWorkOrderList.splice(0, 1);
                        vm.workorder.type = r.result.workOrder.type;
                        switch (vm.workorder.type) {
                            case 1:
                                vm.showTypeName = '检修';
                                break;
                            case 2:
                                vm.showTypeName = '巡检';
                                break;
                            case 3:
                                vm.showTypeName = '勘查';
                                break;
                        }
                        vm.$nextTick(function () {
                            if (recruitList && recruitList.length > 0) {
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
                            } else {
                                vm.workorder.electricianWorkOrderList.push({
                                    'component': {
                                        'componentName': 'overhaul',
                                        'projectName': '',
                                        'electrician': []
                                    }
                                });
                            }
                        })

                        $.ajax({
                            url: '../cp/workorder/chieforder/defectrecord/selectdata?projectId=' + vm.workorder.projectInfo.id,
                            type: 'get',
                            dataType: 'json',
                            success: function (r) {
                                if (r.code == 0) {
                                    //获取缺陷配置
                                    vm.defectOptions = r.defectRecordList;
                                    //对比缺陷报告
                                    var defectFiArr = [];
                                    var defectArr = vm.workorder.defect;
                                    if (defectArr && defectArr.length > 0) {
                                        for (var i = 0; i < defectArr.length; i++) {
                                            for (var j = 0; j < vm.defectOptions.length; j++) {
                                                if (vm.defectOptions[j].id == defectArr[i].id) {
                                                    defectFiArr.push('<span class="defect-item">' + defectArr[i].deviceName + '</span>');
                                                    vm.defectOptions[j].checked = true;
                                                }
                                            }
                                        }
                                    }

                                    vm.defectShow = '已添加:&nbsp;&nbsp;' + defectFiArr.join('&nbsp;');
                                } else {
                                    alert(r.msg);
                                }
                            }
                        });
                    } else {
                    }
                }
            });
            /* vm.getInfo(id); */
        },
        reload: function () {
            vm.presentOrderId = null;
            vm.workorder.id = null;
            vm.updateReadonly = false;
            vm.isHaving = false;
            vm.showList = true;
            vm.showEvaluation = true;
            vm.showListRecord = true;
            vm.showSocialWorkOrder = true;
            vm.showDetailInfo = true;
            vm.showHf = false;
            vm.showSf = false;
            vm.havingCustomer = false;
            vm.leaderIdLabel = '请选择';
            vm.workRecord = [];
            vm.imSocial = true;
            vm.imFix = true;
            vm.imDefect = true;
            vm.imPatrol = true;
            vm.imExploration = true;
            vm.fixReport = null;
            vm.defectReport = null;
            vm.patrolReport = null;
            vm.explorationReport = null;
            vm.showTypeName = '检修';
            vm.toolequipmentInfoShow = '';
            vm.defectShow = '';
            vm.workorder = $.extend(true, {}, dataBackup);
            vm.evaluation = {
                serviceQuality: 0,
                serviceSpeed: 0,
                content: '',
                evaluationImgList: []
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {page: page}).trigger("reloadGrid");
        },
        getOrderDetailInfo: function (orderId) {
            vm.showDetailInfo = false;
            $.ajax({
                type: 'post',
                url: '../cp/workorder/chieforder/view/' + orderId,
                success: function (r) {
                    if (r.code == 0) {
                        vm.orderDetail = r.result;
                        if (vm.orderDetail.socialWorkOrderList == null || vm.orderDetail.socialWorkOrderList.length == 0) {
                            vm.imSocial = false;
                        }
                        var hf = vm.orderDetail.hazardFactorIdentifications;
                        var sf = vm.orderDetail.safetyMeasures;
                        if (hf != null && hf.length > 0) {
                            for (var i = 0; i < hf.length; i++) {
                                if (hf[i].checked == 1) {
                                    vm.showHf = true;
                                }
                            }
                        }
                        if (sf != null && sf.length > 0) {
                            for (var i = 0; i < sf.length; i++) {
                                if (sf[i].checked == 1) {
                                    vm.showSf = true;
                                }
                            }
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getRecord: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var orderId = getSelectedRowOrderId();
            vm.recordOrderId = orderId;

            var grid = $("#jqGrid");
            var selectedIDs = grid.getGridParam("selarrrow");
            var rowData = grid.jqGrid('getRowData', selectedIDs[0]);
            var myTypeName = rowData.typeName;
            vm.presentId = id;
            vm.title = '工作详情';
            vm.getOrderDetailInfo(orderId);
            $.ajax({
                type: 'post',
                url: '../cp/electrician/worklog/' + orderId,
                datatype: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.page.list == null || r.page.list.length == 0) {
                            vm.showListRecord = false;
                        }
                    } else {
                        r.msg;
                    }
                }
            });
            $.ajax({
                type: 'get',
                url: '../cp/workorder/chieforder/defectrecord?orderId=' + orderId,
                datatype: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.page.list == null || r.page.list.length == 0) {
                            vm.imDefect = false;
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            });
            if (myTypeName == '检修工单') {
                $.ajax({
                    type: 'get',
                    url: '../cp/workorder/chieforder/repairtest?orderId=' + orderId,
                    datatype: 'json',
                    success: function (r) {
                        if (r.code == 0) {
                            vm.imPatrol = false;
                            vm.imExploration = false;
                            if (r.page.list == null || r.page.list.length == 0) {
                                vm.imFix = false;
                            }
                        } else {
                            alert(r.msg);
                        }
                    }
                })
            } else if (myTypeName == '巡检工单') {
                $.ajax({
                    type: 'get',
                    url: '../cp/workorder/chieforder/patrol?orderId=' + orderId,
                    datatype: 'json',
                    success: function (r) {
                        if (r.code == 0) {
                            vm.imFix = false;
                            vm.imExploration = false;
                            if (r.page.list == null || r.page.list.length == 0) {
                                vm.imPatrol = false;
                            }
                        } else {
                            alert(r.msg);
                        }
                    }
                })
            } else if (myTypeName == '勘查工单') {
                $.ajax({
                    url: '../cp/workorder/chieforder/survey?orderId=' + orderId,
                    type: 'get',
                    dataType: 'JSON',
                    success: function (r) {
                        if (r.code == 0) {
                            vm.imPatrol = false;
                            vm.imFix = false;
                            if (r.page.list == null || r.page.list.length == 0) {
                                vm.imExploration = false;
                            }
                        } else {
                        }
                    }
                })
                vm.imPatrol = false;
                vm.imFix = false;
            }
            $.ajax({
                type: 'post',
                url: '../cp/workorder/evaluate/' + id,
                datatype: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        if (r.result.customerId) {
                            vm.evaluation = r.result;
                            vm.evaluation.evaluationImgList = r.result.attachmentList;
                        } else {
                            vm.showEvaluation = false;
                            return;
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        recruit: function () {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var orderId = getSelectedRowOrderId();
            recruitDialog = layer.open({
                type: 2,
                title: '招募社会电工',
                area: [
                    '98%', '98%'
                ],
                closeBtn: 1,
                shadeClose: false,
                content: 'recruit.html?' + orderId
            });
        },
        checkMeasure: function (index, length) {
            var checked = vm.workorder.safetyMeasures[index].checked;
            if (checked === 0) {
                vm.workorder.safetyMeasures[index].checked = 1;
            } else if (checked === 1) {
                vm.workorder.safetyMeasures[index].checked = 0;
            }
        },
        checkIdentification: function (index, length) {
            var checked = vm.workorder.identifications[index].checked;
            if (checked === 0) {
                vm.workorder.identifications[index].checked = 1;
            } else if (checked === 1) {
                vm.workorder.identifications[index].checked = 0;
            }
        },
        getCustomers: function () {
            $.ajax({
                url: '../cp/workorder/chieforder/customer',
                type: 'post',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        vm.customers = r.customer;
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        changeCustomerInfo: function (index) {
            vm.workorder.customerInfo = vm.customers[index];
            vm.workorder.projectInfo = {
                name: '请选择',
                id: null
            };
            this.$nextTick(function () {
                var id = vm.workorder.customerInfo.id;
                var url = '../cp/workorder/chieforder/project/' + id;
                $.ajax({
                    url: url,
                    type: 'post',
                    dataType: 'json',
                    success: function (r) {
                        if (r.code == 0) {
                            vm.projects = r.project;
                            if (!vm.projects || vm.projects.length == 0) {
                                alert('该客户暂无项目');
                            }
                        } else {
                            alert(r.msg);
                        }
                    }
                })
            })
        },
        getProjects: function () {
            if (!vm.workorder.customerInfo.id) {
                alert('请先选择客户!');
            }
        },
        changeProjectInfo: function (index) {
            vm.workorder.projectInfo = vm.projects[index];
            $.ajax({
                url: '../cp/workorder/chieforder/defectrecord/selectdata?projectId=' + vm.projects[index].id,
                type: 'get',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        vm.defectOptions = r.defectRecordList;

                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        chooseLeader: function (index) {
            vm.workorder.leaderId = vm.electricianOptions[index].id;
            vm.leaderIdLabel = vm.electricianOptions[index].name;
        },
        choosePayType: function (id) {
            vm.payTypeCheckedId = id;
            switch (id) {
                case 1003:
                    vm.payType = 0;
                    break;
                case 1000:
                    vm.payType = 1;
                    break;
                case 1001:
                    vm.payType = 2;
                    break;
            }
        },
        listenAdd: function () {
            this.workorder.electricianWorkOrderList.push({
                'component': {
                    'componentName': 'overhaul',
                    'projectName': '',
                    'electrician': []
                }
            });
        },
        listenAddElectrican: function () {
            this.workorder.recruitList.push({
                'component': {
                    'componentName': 'electricanInfo',
                    'qualifications': null,
                    'beginWorkTime': '',
                    'endWorkTime': '',
                    'expiryTime': '',
                    'quantity': null,
                    'fee': null,
                    'content': ''
                }
            });
        },
        listenRemove: function (data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.electricianWorkOrderList.splice(index, 1);
        },
        listenRemoveElectrican: function (data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList.splice(index, 1);
        },
        listenQuantity: function (data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].quantity = obj.quantity;

        },
        listenFee: function (data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].fee = obj.fee;
        },
        listenStart: function (data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].beginWorkTime = obj.beginWorkTime;
        },
        listenEnd: function (data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].endWorkTime = obj.endWorkTime;
        },
        getOverhaulData: function (data) {
            var obj = data[0];
            var index = data[1];
            var self = this;
            self.workorder.electricianWorkOrderList[index].component.projectName = obj.projectName;
            self.workorder.electricianWorkOrderList[index].component.electrician = obj.electrician;
        },
        getElectricanData: function (data) {
            var obj = data[0];
            var index = data[1];
            this.workorder.recruitList[index].component = obj;
        },
        oneValidate: function (allComponent, i, length) {
            var self = this;
            if (i < length) {
                allComponent[i].$validator.validateAll().then(function () {
                    i++;
                    if (i === length) {
                        vm.$validator.validateAll('form-1').then(function () {
                            //从子组件获取数据
                            $('.overhaul-data').trigger('click');
                            $('.electrician-data').trigger('click');
                            if (!vm.isHaving) {
                                vm.workorder.needSocialElectrician = 0;
                            } else {
                                vm.workorder.needSocialElectrician = 1;
                            }
                            //缺陷记录信息修改
                            if (vm.workorder.defect && vm.workorder.defect.length > 0) {
                                var arr = vm.workorder.defect;
                                var finalArr = [];
                                for (var i = 0; i < arr.length; i++) {
                                    finalArr.push(arr[i].id);
                                }
                                vm.workorder.defectRecords = finalArr.join();
                            }
                            $.ajax({
                                url: '../cp/workorder/chieforder/save',
                                type: 'post',
                                data: JSON.stringify(vm.workorder),
                                dataType: 'json',
                                success: function (r) {
                                    if (r.code == 0) {
                                        if (vm.isHaving === false) {
                                            alert('保存成功！', function () {
                                                vm.reload();
                                            })
                                        } else {
                                            var data = {};
                                            data.type = 'getSocialWorkorder';
                                            data.orderType = 2;
                                            data.orderIds = r.result.socialOrderIdStr;
                                            data.socialWorkOrderIds = r.result.socialWorkOrderIds;
                                            self.socialWorkorderPay(data);
                                        }

                                    } else {
                                        alert(r.msg);
                                    }
                                }
                            });

                        }, function () {
                            alert('信息填写不完整或不规范');
                            return;
                        });
                    }
                    vm.oneValidate(allComponent, i, length);
                }, function () {
                    alert('信息填写不完整或不规范');
                    return false;
                });
            }
        },
        save: function (e) {
            this.preventRepeat(e);
            var allComponent = vm.$children;
            var length = allComponent.length;
            var i = 0;
            vm.oneValidate(allComponent, i, length);
        },
        query: function () {
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
        chooseMap: function () {
            if (vm.workorder.id) {
                return;
            }
            mapDialog = layer.open({
                type: 1,
                title: '选择地点',
                area: [
                    '85%', '75%'
                ],
                closeBtn: 2,
                content: jQuery('#mapChoose')
            });
        },
        havingToTrue: function () {
            vm.workorder.recruitList = [
                {
                    'component': {
                        'componentName': 'electricanInfo',
                        'title': '',
                        'qualifications': [],
                        'beginWorkTime': '',
                        'endWorkTime': '',
                        'expiryTime': '',
                        'quantity': null,
                        'fee': null,
                        'content': ''
                    }
                }
            ];
            vm.isHaving = true;
        },
        havingToFalse: function () {
            vm.workorder.recruitList = [];
            vm.isHaving = false;
        },
        showDefectDetail: function (id) {
            $.ajax({
                type: 'post',
                url: '../cp/workorder/chieforder/defectrecord/' + id,
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        vm.defectDetailInfo = r.defectrecord;
                        var index = layer.open({
                            type: 1,
                            title: '缺陷报告详情',
                            closeBtn: 2,
                            area: [
                                '700px', '500px'
                            ],
                            shadeClose: true,
                            btn: '返回',
                            yes: function () {
                                vm.defectDetailInfo = {};
                                layer.close(index);
                            },
                            content: jQuery('#defectDetail')
                        })
                    } else {
                        alert(r.msg);
                    }
                }
            });

        },
        showTrainDetail: function (id) {
            $.ajax({
                type: 'post',
                url: '../cp/workorder/chieforder/repairtest/' + id,
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        vm.fixDetailInfo = r.repairtest;
                        var index = layer.open({
                            type: 1,
                            title: '修试报告详情',
                            closeBtn: 2,
                            area: [
                                '700px', '500px'
                            ],
                            shadeClose: true,
                            btn: '返回',
                            yes: function () {
                                vm.fixDetailInfo = {};
                                layer.close(index);
                            },
                            content: jQuery('#trainDetail')
                        })
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        showInspectionDetail: function (id) {
            $.ajax({
                type: 'post',
                url: '../cp/workorder/chieforder/patrol/' + id,
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        vm.patrolDetailInfo = r.patrolRecord;
                        var index = layer.open({
                            type: 1,
                            title: '巡检报告详情',
                            closeBtn: 2,
                            area: [
                                '700px', '500px'
                            ],
                            shadeClose: true,
                            btn: '返回',
                            yes: function () {
                                vm.patrolDetailInfo = {};
                                layer.close(index);
                            },
                            content: jQuery('#inspectionDetail')
                        })
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        showWorkLogDetail: function (id) {
            $.ajax({
                type: 'post',
                url: '../cp/electrician/worklog/detail/' + id,
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        vm.workLogDetailInfo = r.detail;
                        var index = layer.open({
                            type: 1,
                            title: '工作记录详情',
                            closeBtn: 2,
                            area: [
                                '700px', '500px'
                            ],
                            shadeClose: true,
                            btn: '返回',
                            yes: function () {
                                vm.workLogDetailInfo = {};
                                layer.close(index);
                            },
                            content: jQuery('#workLogDetail')
                        })
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        chooseType: function (type, name) {
            this.workorder.type = type;
            this.workorder.typeName = name;
            switch (type) {
                case 1:
                    this.showTypeName = '检修';
                    break;
                case 2:
                    this.showTypeName = '巡检';
                    break;
                case 3:
                    this.showTypeName = '勘查';
                    break;
            }
        },
        addToolequipment: function () {
            //备份选项
            var toolBackup = JSON.parse(JSON.stringify(this.toolequipmentOptions));
            var index = layer.open({
                type: 1,
                title: '添加携带机械或设备',
                closeBtn: 2,
                btn: ['确认'],
                area: [
                    '75%', '80%'
                ],
                yes: function () {
                    var arrOptions = vm.toolequipmentOptions;
                    if (vm.workorder.toolequipmentInfo) {
                        vm.workorder.toolequipmentInfo.splice(0, vm.workorder.toolequipmentInfo.length);
                    } else {
                        return;
                    }

                    var arr = vm.workorder.toolequipmentInfo;
                    var fiArr = [];
                    for (var i = 0; i < arrOptions.length; i++) {
                        if (arrOptions[i].checked == 1) {
                            arr.push(arrOptions[i]);
                        }
                    }
                    if (arr && arr.length > 0) {
                        for (var i = 0; i < arr.length; i++) {
                            if (arr[i].checked == 1) {
                                fiArr.push('<span class="tool-item">' + arr[i].name + '*' + arr[i].num + '</span>');
                            }
                        }
                    }
                    vm.toolequipmentInfoShow = '已添加:&nbsp;&nbsp;' + fiArr.join('&nbsp;');
                    layer.close(index);
                },
                cancel: function () {
                    vm.toolequipmentOptions = toolBackup;
                },
                content: $('#addToolequipment')
            });
        },
        addDefectRecords: function () {
            if (!vm.workorder.projectInfo.id) {
                alert('请先选择项目');
                return;
            }
            if (!vm.defectOptions || vm.defectOptions.length == 0) {
                alert('暂无缺陷记录');
                return;
            }
            //备份选项
            var defectBackup = JSON.parse(JSON.stringify(this.defectOptions));
            var index = layer.open({
                type: 1,
                title: '添加缺陷记录',
                closeBtn: 2,
                btn: ['确认'],
                area: [
                    '75%', '80%'
                ],
                scrollbar: false,
                yes: function () {
                    vm.defect = [];
                    var arr = vm.defectOptions;
                    var arrShow = [];
                    for (var i = 0; i < arr.length; i++) {
                        if (arr[i].checked) {
                            vm.workorder.defect.push(arr[i]);
                            arrShow.push('<span class="defect-item">' + arr[i].deviceName + '</span>');
                        }
                    }

                    vm.defectShow = '已添加:&nbsp;&nbsp;' + arrShow.join('&nbsp;');
                    layer.close(index);
                },
                cancel: function () {
                    vm.defectOptions = defectBackup;
                },
                content: $('#addDefectRecords')
            });
        },
        chooseToolequipment: function (index) {
            var checked = vm.toolequipmentOptions[index].checked;
            if (!checked || checked == 0) {
                vm.toolequipmentOptions[index].checked = 1;
            } else if (checked === 1) {
                vm.toolequipmentOptions[index].checked = 0;
            }
        },
        showEvaluationImg: function (num) {
            var index = layer.open({
                type: 1,
                title: '巡检报告详情',
                closeBtn: 2,
                area: [
                    '700px', '500px'
                ],
                shadeClose: true,
                btn: '返回',
                yes: function () {
                    layer.close(index);
                },
                content: jQuery('#evaluationImg' + num)
            })
        }
    },
    created: function () {
        var self = this;
        $.ajax({
            type: 'post',
            url: '../cp/workorder/payment/balance',
            success: function (r) {
                if (r.code == 0) {
                    self.amount = r.result.amount;
                } else {
                    alert(r.msg);
                }
            }
        });

        $.ajax({
            url: '../cp/workorder/chieforder/certificate',
            type: 'post',
            dataType: 'json',
            success: function (r) {
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
            success: function (r) {
                if (r.code == 0) {
                    if (r.electrician == null) {
                        self.getoptions = [];
                        return;
                    }
                    self.electricianOptions = r.electrician;
                } else {
                    alert(r.msg);
                }
            }
        });
        $.ajax({
            url: '../cp/workorder/chieforder/types',
            type: 'post',
            dataType: 'json',
            success: function (r) {
                if (r.code == 0) {
                    self.typeOptions = r.types;
                } else {
                    alert(r.msg);
                }
            }
        });
    },
    mounted: function () {
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
        $('#date-start').on('changeDate', function (ev) {
            self.q.startTime = getNowFormatDate(ev.date);
        });
        $('#date-start-remove').on('click', function () {
            self.q.startTime = '';
        });
        $('#date-end').on('changeDate', function (ev) {
            self.q.endTime = getNowFormatDate(ev.date);
        });
        $('#date-end-remove').on('click', function () {
            self.q.endTime = '';
        });
        $('#fixStartDate').on('changeDate', function (ev) {
            var x = new Date(getNowFormatDate(ev.date)).getTime();
            var y = new Date(getNowFormatDate(vm.workorder.endTime)).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
                $(this).val('');
                return;
            } else {
                vm.workorder.startTime = getNowFormatDate(ev.date);
            }
        });
        $('#fixEndDate').on('changeDate', function (ev) {
            var x = new Date(getNowFormatDate(vm.workorder.startTime)).getTime();
            var y = new Date(getNowFormatDate(ev.date)).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
                $(this).val('');
                return;
            } else {
                vm.workorder.endTime = getNowFormatDate(ev.date);
            }
        });
        dataBackup = $.extend(true, {}, this.workorder);

        var projectData = JSON.parse(sessionStorage.getItem('projectData'));
        if (projectData) {
            self.workorder.type = 3;
            self.workorder.typeName = '现场勘查';
            self.showTypeName = '勘查';
            self.add();
            self.workorder.customerInfo.name = projectData.customerName;
            self.workorder.projectInfo.name = projectData.name;
            self.workorder.projectInfo.id = projectData.id;
            self.havingCustomer = true;
            $.ajax({
                url: '../cp/workorder/chieforder/customer',
                type: 'post',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        self.customers = r.customer;
                        for (var i = 0; i < self.customers.length; i++) {
                            if (projectData.customerId == self.customers[i].id) {
                                //self.workorder.customerInfo = self.customers[i];
                                self.workorder.customerInfo.id = self.customers[i].id;
                                self.workorder.customerInfo.name = self.customers[i].name;
                                self.workorder.customerInfo.contact = self.customers[i].contact;
                                self.workorder.customerInfo.address = self.customers[i].address;
                                self.workorder.customerInfo.phone = self.customers[i].phone;
                                self.workorder.customerInfo.contactMobile = self.customers[i].contactMobile;
                            }
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            })
            sessionStorage.removeItem('projectData');
        }
    }
});
//初始化地图配置
var map = new BMap.Map("allmap");
map.centerAndZoom(new BMap.Point(120.1452077295, 30.2495737566), 16);
map.enableScrollWheelZoom();
map.addControl(new BMap.NavigationControl());
map.addControl(new BMap.ScaleControl());
map.addControl(new BMap.OverviewMapControl({isOpen: true}));

function showInfo(e) {
    var url = 'http://api.map.baidu.com/geocoder/v2/?callback=renderReverse&location=' + e.point.lat + ',' + e.point.lng + '&output=json&pois=1&ak=fNvowSBXwhZgg7YkLsP1PHFy';
    var addressInfo = '';
    $.ajax({
        type: 'get',
        url: url,
        dataType: 'JSONP',
        success: function (r) {
            if (r.status == 0) {
                addressInfo = '<span style="font-size: 12px;">地点:&nbsp;&nbsp;' + r.result.formatted_address + r.result.sematic_description + '</span>';
                vm.mapAddress = addressInfo;
                layer.confirm(addressInfo + '<br>点击确认保存地点', {
                    btn: [
                        '确认', '取消'
                    ],
                    area: [
                        '600px', '200px'
                    ],
                    yes: function (index) {
                        vm.workorder.longitude = e.point.lng;
                        vm.workorder.latitude = e.point.lat;
                        layer.close(index);
                        layer.close(mapDialog);
                    },
                    btn2: function () {
                        layer.close(mapDialog);
                    }
                });
            } else {
                return;
            }
        }
    });

}

map.addEventListener("click", showInfo);

function closeEditPage() {
    layer.close(recruitDialog);
    vm.reload();
}
