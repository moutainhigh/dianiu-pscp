var myTabs = ['<div>',
    '   <ul class="nav nav-tabs">',
    '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTap(tab);selectedTab(tab);">',
    '           <a><i :class="\'fa \' + tab.icon"></i>{{ tab.name }}</a>',
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
    data: function () {
        return {
            tabs: []
        };
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
    data: function () {
        return {
            isActive: false,
            copyGridParam: null
        };
    },
    methods: {
        toggleTap: function (e) {
            vm.title = e.name;
            vm.tabStatus = e.status;
            if (vm.companyId) {
                vm.getDetail();
            }
            //切换tab放一个解锁
            /* if(vm.saveLock){
                vm.saveLock = false;
            } */
        }
    },
    mounted: function () {
        this.isActive = this.selected;
    }
})

/* <div class="fee-price-input">
    尖时段单价
    <el-input size="small" placeholder="例如0.9" suffix-icon="el-icon-edit" v-model="priceall.input1">
    </el-input>
    峰时段单价
    <el-input size="small" placeholder="例如0.9" suffix-icon="el-icon-edit" v-model="priceall.input2">
    </el-input>
    <br />
    <br /> 
    平时段单价
    <el-input size="small" placeholder="例如0.9" suffix-icon="el-icon-edit" v-model="priceall.input3">
    </el-input>
    谷时段单价
    <el-input size="small" placeholder="例如0.9" suffix-icon="el-icon-edit" v-model="priceall.input4">
    </el-input>
</div> */
var priceStr = ['<div class="fee-price-input">',
'    尖时段单价',
'    <el-input size="small" placeholder="例如0.9" suffix-icon="el-icon-edit" v-model="priceall.input1">',
'    </el-input>',
'    峰时段单价',
'    <el-input size="small" placeholder="例如0.9" suffix-icon="el-icon-edit" v-model="priceall.input2">',
'    </el-input>',
'    <br />',
'    <br /> ',
'    平时段单价',
'    <el-input size="small" placeholder="例如0.9" suffix-icon="el-icon-edit" v-model="priceall.input3">',
'    </el-input>',
'    谷时段单价',
'    <el-input size="small" placeholder="例如0.9" suffix-icon="el-icon-edit" v-model="priceall.input4">',
'    </el-input>',
'</div>'].join("");

Vue.component('set-price', {
    template: priceStr,
    props: ['priceall']
})

/* <div style="margin-bottom: 15px;">
    <el-time-select placeholder="起始时间" v-model="comdata.startTime" readonly>
    </el-time-select>
    至
    <el-time-select placeholder="结束时间" v-model="comdata.endTime" :readonly="!isedit" :picker-options="comdata.options">
    </el-time-select>
    <el-select v-model="comdata.type" placeholder="区间类型(尖峰平谷)">
        <el-option label="尖时段" value="尖时段"></el-option>
        <el-option label="峰时段" value="峰时段"></el-option>
        <el-option label="平时段" value="平时段"></el-option>
        <el-option label="谷时段" value="谷时段"></el-option>
    </el-select>
    <a v-if="index == 0" @click="add">
        <i class="fa fa-plus-square-o"></i>
    </a>
    <a v-else @click="remove">
        <i class="fa fa-minus-square-o"></i>
    </a>
</div> */
var timePickerStr = ['<div style="margin-bottom: 15px;">',
'    <el-time-select placeholder="起始时间" v-model="comdata.startTime" readonly>',
'    </el-time-select>',
'    至',
'    <el-time-select placeholder="结束时间" v-model="comdata.endTime" :readonly="!isedit" :picker-options="comdata.options" :editable="false">',
'    </el-time-select>',
'    <el-select v-model="comdata.type" placeholder="区间类型(尖峰平谷)">',
'        <el-option label="尖时段" value="尖时段"></el-option>',
'        <el-option label="峰时段" value="峰时段"></el-option>',
'        <el-option label="平时段" value="平时段"></el-option>',
'        <el-option label="谷时段" value="谷时段"></el-option>',
'    </el-select>',
'    <a v-if="index==length-1&&comdata.endTime!=\'24:00\'" @click="add">',
'        <i class="fa fa-plus-square-o"></i>',
'    </a>',
'    <a v-if="index != 0&&index==length-1" @click="remove">',
'        <i class="fa fa-minus-square-o"></i>',
'    </a>',
'</div>'].join("");

Vue.component('time-picker', {
    template: timePickerStr,
    props: ['index', 'isedit', 'comdata','length'],
    data: function () {
        return {
            startTime: '',
            endTime: '',
            type: '尖时段'
        }
    },
    methods: {
        add: function () {
            //增加时段是 检查
            var arr = vm.dateComponents;
            for (var i = 0; i < arr.length; i++) {
                if (!arr[i].endTime) {
                    alert('已填写时段信息不完整!');
                    return
                }
            }
            if (arr[arr.length - 1].endTime == '24:00') {
                alert('已完成周期!');
            } else {
                this.$emit('add-fee-type', arr[arr.length - 1].endTime);
            }
        },
        remove: function () {
            this.$emit('remove-fee-type', this.index);
        }
    },
    created: function(){
        console.log(this.length);
    }
})


var vm = new Vue({
    el: '#rrapp',
    data: {
        title: '用电健康',
        tabStatus: 'health',
        companyNameOptions: [],
        companyId: '',
        saveLock: false,
        health: {
            up: '',
            down: ''
        },
        factor: {
            low: '',
            middle: '',
            high: ''
        },
        load: {
            low: '',
            middle: '',
            high: '',
            load: ''
        },
        fee: {
            chargeTimeInterval: '', //电费周期
            chargeMode: 0, //电度电费计费方式   1:分时  0:统一
            basePrice: '', //统一单价
            apexTimeInterval: [], //尖时间段
            apexPrice: '', //尖单价
            peakTimeInterval: [], //峰时间段
            peakPrice: '', //峰单价
            flatTimeInterval: [], //平时间段
            flatPrice: '', //平单价
            valleyTimeInterval: [], //谷时间段
            valleyPrice: '', //谷单价
            baseChargeMode: 1, //基本电费计费方式  1:变压器容量  2:最大需量
            transformerCapacity: '', //容量
            transformerCapacityPrice: '', //容量单价
            maxCapacity: '', //最大需量
            maxCapacityPrice: '', //最大需量单价
            standardAdjustRate: '' //力调标准
        },
        dateComponents: [{
            startTime: '00:00',
            endTime: '',
            type: '尖时段',
            options: {
                start: '00:00',
                end: '24:00',
                step: '01:00'
            }
        }],
        priceAll: [{
            input1: '',
            input2: '',
            input3: '',
            input4: ''
        }]
    },
    methods: {
        getDetail: function () {
            this.reload();
            var self = this;
            var companyId = this.companyId;
            var url = '';
            switch (this.tabStatus) {
                case 'health':
                    url = '../powerPriceConfig/otherConfig/info/' + companyId + '/' + 3;
                    $.ajax({
                        type: 'post',
                        url: url,
                        dataType: 'json',
                        success: function (r) {
                            if (r.code == 0 && r.list && r.list.length > 0) {
                                self.health.up = r.list[0].value;
                                self.health.down = r.list[1].value;
                            }
                        }
                    })
                    break;
                case 'factor':
                    url = '../powerPriceConfig/otherConfig/info/' + companyId + '/' + 2;
                    $.ajax({
                        type: 'post',
                        url: url,
                        dataType: 'json',
                        success: function (r) {
                            if (r.code == 0 && r.list && r.list.length > 0) {
                                self.factor.low = self.valueFormatter(r.list[0].value);
                                self.factor.middle = self.valueFormatter(r.list[1].value);
                                self.factor.high = self.valueFormatter(r.list[2].value);
                            }
                        }
                    })
                    break;
                case 'load':
                    url = '../powerPriceConfig/otherConfig/info/' + companyId + '/' + 1;
                    $.ajax({
                        type: 'post',
                        url: url,
                        dataType: 'json',
                        success: function (r) {
                            if (r.code == 0 && r.list && r.list.length > 0) {
                                self.load.low = self.valueFormatter(r.list[0].value);
                                self.load.middle = self.valueFormatter(r.list[1].value);
                                self.load.high = self.valueFormatter(r.list[2].value);
                                self.load.load = self.valueFormatter(r.list[3].value);
                            }
                        }
                    })
                    break;
                case 'fee':
                    url = '../powerPriceConfig//priceConfig/info/' + companyId;
                    $.ajax({
                        type: 'post',
                        url: url,
                        dataType: 'json',
                        success: function (r) {
                            if (r.code == 0) {
                                if (r.entity) {
                                    self.fee = r.entity;
                                    //如果分段计费  进行数据拆分
                                    if (self.fee.chargeMode == 1) {
                                        var dateOption = [];
                                        //尖峰平谷时段
                                        var arr1 = JSON.parse(r.entity.apexTimeInterval);
                                        for (var i = 0; i < arr1.length; i++) {
                                            dateOption.push({
                                                startTime: arr1[i].split('-')[0],
                                                endTime: arr1[i].split('-')[1],
                                                type: '尖时段'
                                            });
                                        }
                                        var arr2 = JSON.parse(r.entity.peakTimeInterval);
                                        for (var i = 0; i < arr2.length; i++) {
                                            dateOption.push({
                                                startTime: arr2[i].split('-')[0],
                                                endTime: arr2[i].split('-')[1],
                                                type: '峰时段'
                                            });
                                        }
                                        var arr3 = JSON.parse(r.entity.flatTimeInterval);
                                        for (var i = 0; i < arr3.length; i++) {
                                            dateOption.push({
                                                startTime: arr3[i].split('-')[0],
                                                endTime: arr3[i].split('-')[1],
                                                type: '平时段'
                                            });
                                        }
                                        var arr4 = JSON.parse(r.entity.valleyTimeInterval);
                                        for (var i = 0; i < arr4.length; i++) {
                                            dateOption.push({
                                                startTime: arr4[i].split('-')[0],
                                                endTime: arr4[i].split('-')[1],
                                                type: '谷时段'
                                            });
                                        }
                                        for (var i = 0; i < dateOption.length; i++) {
                                            for (var j = i + 1; j < dateOption.length; j++) {
                                                if (parseInt(dateOption[i].startTime.split(':')[0]) > parseInt(dateOption[j].startTime.split(':')[0])) {
                                                    var temp = dateOption[i];
                                                    dateOption[i] = dateOption[j];
                                                    dateOption[j] = temp;
                                                }
                                            }
                                        }
                                        var time = dateOption[dateOption.length - 1].startTime;
                                        var start = self.timeFormatter(parseInt(time.split(':')[0]) + 1 + ':00');
                                        for (var i = 0; i < dateOption.length; i++) {
                                            dateOption[i].options = {
                                                start: start,
                                                end: '24:00',
                                                step: '01:00'
                                            }
                                        }
                                        self.dateComponents.splice(self.dateComponents.length - 1, 1);
                                        self.priceAll.splice(0, 1);
                                        self.$nextTick(function () {
                                            self.dateComponents = dateOption;
                                            self.priceAll = [{
                                                input1: r.entity.apexPrice,
                                                input2: r.entity.peakPrice,
                                                input3: r.entity.flatPrice,
                                                input4: r.entity.valleyPrice
                                            }]
                                        })
                                    }
                                }


                            } else {
                                alert(r.msg);
                            }
                        }
                    })
                    break;
            }
        },
        healthSave: function () {
            var companyId = this.companyId;
            if (companyId) {
                var data = this.health;
                data.companyId = this.companyId;
                if (parseInt(data.up) < parseInt(data.down)) {
                    alert('上限不能小于下限!');
                    return;
                }
                this.$validator.validateAll('form-1').then(function () {
                    vm.saveLock = true;
                    $.ajax({
                        type: 'post',
                        url: '../powerPriceConfig/voltageConfig/save/' + companyId,
                        dataType: 'json',
                        data: JSON.stringify(data),
                        success: function (r) {
                            if (r.code == 0) {
                                alert('保存成功!');
                            } else {
                                alert(r.msg);
                            }

                        },
                        complete: function(){
                            vm.saveLock = false;
                        }
                    })
                }, function () {
                    alert('信息填写不完整');
                })
            } else {
                alert('请先选择客户!');
            }

        },
        factorSave: function () {
            var self = this;
            var companyId = this.companyId;
            if (companyId) {
                if (this.factor.middle > this.factor.low && this.factor.middle < this.factor.high) {
                    this.$validator.validateAll('form-2').then(function () {
                        var data = {};
                        data.serious = '0-' + self.factor.low;
                        data.low = self.factor.low + '-' + self.factor.middle;
                        data.normal = self.factor.middle + '-' + self.factor.high;
                        data.well = self.factor.high + '-1';
                        data.companyId = self.companyId;
                        vm.saveLock = true;
                        $.ajax({
                            type: 'post',
                            url: '../powerPriceConfig/powerFactor/save/' + companyId,
                            dataType: 'json',
                            data: JSON.stringify(data),
                            success: function (r) {
                                if (r.code == 0) {
                                    alert('保存成功!');
                                } else {
                                    alert(r.msg);
                                }
                            },
                            complete: function(){
                                vm.saveLock = false;
                            }
                        })
                    }, function () {
                        alert('信息填写不完整');
                    });
                } else {
                    alert('数据输入有误,请检查!');
                    return;
                }

            } else {
                alert('请先选择客户');
            }

        },
        loadSave: function () {
            var self = this;
            var companyId = this.companyId;
            if (companyId) {
                if (parseInt(this.load.load) > parseInt(this.load.high) && parseInt(this.load.high) > parseInt(this.load.middle) && parseInt(this.load.middle) > parseInt(this.load.low)) {
                    this.$validator.validateAll('form-3').then(function () {
                        var data = {};
                        data.low = '0-' + self.load.low;
                        data.economic = self.load.low + '-' + self.load.middle;
                        data.warn = self.load.middle + '-' + self.load.high;
                        data.over = self.load.high + '-' + self.load.load;
                        data.companyId = self.companyId;
                        vm.saveLock = true;
                        $.ajax({
                            type: 'post',
                            url: '../powerPriceConfig/load/save/' + companyId,
                            dataType: 'json',
                            data: JSON.stringify(data),
                            success: function (r) {
                                if (r.code == 0) {
                                    alert('保存成功!');
                                } else {
                                    alert(r.msg);
                                }
                            },
                            complete: function(r){
                                vm.saveLock = false;
                            }
                        })
                    }, function () {
                        alert('请填写必填信息!');
                    })
                } else {
                    alert('上限值填写有误!');
                }
            } else {
                alert('请先选择客户!');
            }

        },
        feeSave: function () {
            this.fee.apexPrice = this.priceAll[0].input1;
            this.fee.peakPrice = this.priceAll[0].input2;
            this.fee.flatPrice = this.priceAll[0].input3;
            this.fee.valleyPrice = this.priceAll[0].input4;
            this.apexTimeInterval = [];
            this.peakTimeInterval = [];
            this.flatTimeInterval = [];
            this.valleyTimeInterval = [];
            var arr = this.dateComponents;
            var self = this;
            for (var i = 0; i < arr.length; i++) {
                switch (arr[i].type) {
                    case '尖时段':
                        self.apexTimeInterval.push(arr[i].startTime + '-' + arr[i].endTime);
                        break;
                    case '峰时段':
                        self.peakTimeInterval.push(arr[i].startTime + '-' + arr[i].endTime);
                        break;
                    case '平时段':
                        self.flatTimeInterval.push(arr[i].startTime + '-' + arr[i].endTime);
                        break;
                    case '谷时段':
                        self.valleyTimeInterval.push(arr[i].startTime + '-' + arr[i].endTime);
                        break;
                }
            }
            this.fee.apexTimeInterval = this.apexTimeInterval;
            this.fee.peakTimeInterval = this.peakTimeInterval;
            this.fee.flatTimeInterval = this.flatTimeInterval;
            this.fee.valleyTimeInterval = this.valleyTimeInterval;
            this.fee.companyId = this.companyId;

            // var fieldAll = this.$validator.$scopes['form-4'];
            // var fieldArr = [];
            // for (i in fieldAll) {
            //     fieldArr.push(i);
            // }
            // self.feeValidate(fieldArr, 0, fieldArr.length);
            // console.log(this.$validator);
            // console.log(this.$validator.$scopes['form-4']);
            if (!this.fee.companyId) {
                alert('请先选择对应客户!');
                return;
            }
            var scopeArr = ['form-4'];
            if (self.fee.chargeMode == 0) {
                scopeArr.push('form-5');
            }
            if (self.fee.baseChargeMode == 1) {
                scopeArr.push('form-6');
            } else if (self.fee.baseChargeMode == 2) {
                scopeArr.push('form-7');
            }

            self.feeValidate(scopeArr, 0, scopeArr.length);
        },
        feeValidate: function (scopeArr, i, length) {
            var self = this;
            this.$validator.validateAll(scopeArr[i]).then(function () {
                i++;
                if (i == length) {
                    vm.saveLock = true;
                    $.ajax({
                        type: 'post',
                        url: '../powerPriceConfig/priceConfig/save',
                        dataType: 'json',
                        data: JSON.stringify(self.fee),
                        success: function (r) {
                            if (r.code == 0) {
                                alert('保存成功!')
                            } else {
                                alert(r.msg);
                            }
                        },
                        complete: function(){
                            vm.saveLock = false;
                        }
                    })
                } else {
                    self.feeValidate(scopeArr, i, length);
                }
            }, function () {
                alert('请填写必填信息');
            })
        },
        reload: function () {
            this.health = {
                up: '',
                down: ''
            }
            this.factor = {
                low: '',
                middle: '',
                high: ''
            }
            this.load = {
                low: '',
                middle: '',
                high: '',
                load: ''
            }
            this.fee = {
                chargeTimeInterval: '', //电费周期
                chargeMode: 0, //电度电费计费方式   1:分时  0:统一
                basePrice: '', //统一单价
                apexTimeInterval: [], //尖时间段
                apexPrice: '', //尖单价
                peakTimeInterval: [], //峰时间段
                peakPrice: '', //峰单价
                flatTimeInterval: [], //平时间段
                flatPrice: '', //平单价
                valleyTimeInterval: [], //谷时间段
                valleyPrice: '', //谷单价
                baseChargeMode: 1, //基本电费计费方式  1:变压器容量  2:最大需量
                transformerCapacity: '', //容量
                transformerCapacityPrice: '', //容量单价
                maxCapacity: '', //最大需量
                maxCapacityPrice: '', //最大需量单价
                standardAdjustRate: '' //力调标准
            }
            this.dateComponents = [{
                startTime: '00:00',
                endTime: '',
                type: '尖时段',
                options: {
                    start: '00:00',
                    end: '24:00',
                    step: '01:00'
                }
            }]
            this.priceAll = [{
                input1: '',
                input2: '',
                input3: '',
                input4: ''
            }]
            this.$nextTick(function () {
                vm.errors.clear('form-1');
                vm.errors.clear('form-2');
                vm.errors.clear('form-3');
                vm.errors.clear('form-4');
                vm.errors.clear('form-5');
                vm.errors.clear('form-6');
                vm.errors.clear('form-7');
            })
        },
        valueFormatter: function (value) {
            return value.split('-')[1];
        },
        listenAddFeeType: function (time) {
            var start = this.timeFormatter(parseInt(time.split(':')[0]) + 1 + ':00');
            this.dateComponents.push({
                startTime: time,
                endTime: '',
                type: '尖时段',
                options: {
                    start: start,
                    end: '24:00',
                    step: '01:00'
                }
            });
        },
        listenRemoveFeeType: function (index) {
            var self = this;
            this.dateComponents.splice(index, 1);
            //选择范围更新
            this.$nextTick(function () {
                var arr = self.dateComponents;
                var time = arr[arr.length - 1].startTime;
                var start = self.timeFormatter(parseInt(time.split(':')[0]) + 1 + ':00');
                self.dateComponents[self.dateComponents.length - 1].options = {
                    start: start,
                    end: '24:00',
                    step: '01:00'
                }
            })
        },
        timeFormatter: function (time) {
            if (parseInt(time.split(':')[0]) < 10) {
                return '0' + time;
            } else {
                return time;
            }
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