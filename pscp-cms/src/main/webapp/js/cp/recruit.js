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
    '       <v-select multiple :options="getOptions" v-model="getMydata.qualifications" placeholder="请选择电工资质" label="name" track-by="name" :allow-empty="false" :class="{\'is-danger\': errors.has(\'电工资质\')}" ></v-select>',
    '      <input type="hidden" v-model="getMydata.qualifications" data-vv-name="电工资质" v-validate="\'required\'" />',
    '    </div>',
    '    <div class="col-sm-2 control-label"><span style="color: red;">*</span>人数(人):</div>',
    '    <div class="col-sm-1">',
    '       <input type="text" @input="pushQuantity" class="form-control" placeholder="请输入人数" v-model="getMydata.quantity" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')" onafterpaste="this.value=this.value.replace(/[^0-9]/g,\'\')" maxlength="2" data-vv-name="人数" v-validate="\'required\'" :class="{\'is-danger\': errors.has(\'人数\')}" />',
    '    </div>',
    '    <div class="col-sm-1 control-label"><span style="color: red;">*</span>费用(元/人/天):</div>',
    '    <div class="col-sm-2">',
    '        <input type="text" @input="pushFee" class="form-control" v-model="getMydata.fee" placeholder="请输入费用" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')" onafterpaste="this.value=this.value.replace(/[^0-9]/g,\'\')" maxlength="4" data-vv-name="费用" v-validate="\'required\'" :class="{\'is-danger\': errors.has(\'费用\')}" />',
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
    '        <input class="form-control form_datetime tem-picker start" size="16" type="text" data-date-format="yyyy-mm-dd" readonly placeholder="工作开始日期" :class="{\'is-danger\': errors.has(\'工作开始日期\')}" >',
    '        <input type="hidden" v-model="getMydata.beginWorkTime" @change="pushStartTime" data-vv-name="工作开始日期" v-validate="\'required\'" >',
    '    </div>',
    '    <div class="col-sm-2">',
    '        <input class="form-control form_datetime tem-picker end" size="16" type="text" data-date-format="yyyy-mm-dd" readonly  placeholder="工作结束日期" :class="{\'is-danger\': errors.has(\'工作结束日期\')}" >',
    '        <input type="hidden" v-model="getMydata.endWorkTime" @change="pushEndTime" data-vv-name="工作结束日期" v-validate="\'required\'" >',
    '    </div>',
    '    <div class="col-sm-2">',
    '        <a v-if="getindex == 0" @click="add"><i class="fa fa-plus-square-o"></i></a>',
    '        <a v-if="getindex != 0" @click="remove"><i class="fa fa-minus-square-o"></i></a>',
    '    </div>',
    '</div>',
    '<div class="form-group">',
    '    <div class="col-sm-2 control-label"><span style="color: red;">*</span>派单截止时间:</div>',
    '    <div class="col-sm-2" >',
    '        <input class="form-control form_datetime tem-picker cutOff" size="16" type="text" data-date-format="yyyy-mm-dd" readonly placeholder="派单截止日期" :class="{\'is-danger\': errors.has(\'派单截止日期\')}">',
    '        <input type="hidden" v-model="getMydata.expiryTime" data-vv-name="派单截止日期" v-validate="\'required\'" >',
    '    </div>',
    '</div>',
    '</div>'
].join("");

Vue.component('electricanInfo', {
    template: myElectrican,
    props: ['options', 'mydata', 'index'],
    data: function() {
        return {
            getOptions: [],
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
            $('.tem-picker.start').on('changeDate', function(ev) {
                if (ev.date) {
                    var x = new Date(getNowFormatDate(ev.date)).getTime();
                    var y = new Date(getNowFormatDate(self.getMydata.endWorkTime)).getTime();
                    if (y < x) {
                        alert('结束时间必须晚于开始时间');
                        $(this).val('');
                        return;
                    } else {
                        self.getMydata.beginWorkTime = getNowFormatDate(ev.date);
                    }
                } else {
                    return;
                }

            });
            $('.tem-picker.end').on('changeDate', function(ev) {
                if (ev.date) {
                    var x = new Date(getNowFormatDate(self.getMydata.beginWorkTime)).getTime();
                    var y = new Date(getNowFormatDate(ev.date)).getTime();
                    if (y < x) {
                        alert('结束时间必须晚于开始时间');
                        $(this).val('');
                        return;
                    } else {
                        self.getMydata.endWorkTime = getNowFormatDate(ev.date);
                    }
                } else {
                    return;
                }

            });
            $('.tem-picker.cutOff').on('changeDate', function(ev) {
                if (!self.getMydata.beginWorkTime) {
                    alert('请先输入工作开始日期!');
                    $(this).val('');
                } else {
                    var x = new Date(getNowFormatDate(self.getMydata.beginWorkTime)).getTime();
                    var y = new Date(getNowFormatDate(ev.date)).getTime();
                    if (y > x) {
                        alert('招募截止日期必须小于工作开始日期！');
                        $(this).val('');
                    } else {
                        self.getMydata.expiryTime = ev.date;
                    }
                }
            })
        }
    },
    created: function() {
        var self = this;
        setTimeout(function() {
            self.getOptions = self.options;
        }, 50)
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

var vm = new Vue({
    el: '#rrapp',
    data: {
        orderId: null,
        certificateOptions: [],
        recruitList: [{
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
        }],
        payType: 0,
        amount: 0
    },
    methods: {
        listenAddElectrican: function() {
            this.recruitList.push({
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
        listenRemoveElectrican: function(data) {
            var obj = data[0];
            var index = data[1];
            this.recruitList.splice(index, 1);
        },
        getElectricanData: function(data) {
            var obj = data[0];
            var index = data[1];
            this.recruitList[index].component = obj;
        },
        listenQuantity: function(data) {
            var obj = data[0];
            var index = data[1];
            this.recruitList[index].quantity = obj.quantity;

        },
        listenFee: function(data) {
            var obj = data[0];
            var index = data[1];
            this.recruitList[index].fee = obj.fee;
        },
        listenStart: function(data) {
            var obj = data[0];
            var index = data[1];
            this.recruitList[index].beginWorkTime = obj.beginWorkTime;
        },
        listenEnd: function(data) {
            var obj = data[0];
            var index = data[1];
            this.recruitList[index].endWorkTime = obj.endWorkTime;
        },
        oneValidate: function(allComponent, i, length) {
            if (i < length) {
                allComponent[i].$validator.validateAll().then(function() {
                    i++;
                    if (i == length) {
                        $('.electrician-data').trigger('click');
                        var data = { 'orderId': vm.orderId, 'recruitList': vm.recruitList };
                        $.ajax({
                            type: "POST",
                            url: '../cp/workorder/chieforder/recruit',
                            data: JSON.stringify(data),
                            success: function(r) {
                                if (r.code == 0) {
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
                                            $.ajax({
                                                type: 'post',
                                                url: '../cp/workorder/chieforder/payment',
                                                data: JSON.stringify(data),
                                                async: false,
                                                success: function(r) {
                                                    if (r.code == 0) {
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
                                                                '           <button type="button" class="btn btn-primary" onclick="location.reload();window.parent.closeEditPage();">支付成功</button>&nbsp;&nbsp;&nbsp;',
                                                                '           <button type="button" class="b tn btn-warning" onclick="location.reload();window.parent.closeEditPage();">支付失败</button>',
                                                                '       </div>',
                                                                '   </div>'
                                                            ].join("");
                                                            var div = $(myDiv);
                                                            $(document.body).append(div);
                                                        } else {
                                                            alert('支付成功！', function() {
                                                                layer.close(index);
                                                                window.parent.closeEditPage();
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
                                } else {
                                    alert(r.msg);
                                }
                            }
                        });
                    }
                    vm.oneValidate(allComponent, i, length);
                }, function() {
                    alert('信息填写不完整或不规范');
                    return false;
                });

            }
        },
        save: function() {
            var allComponent = vm.$children;
            var length = allComponent.length;
            var i = 0;
            vm.oneValidate(allComponent, i, length);
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
    },
    computed: {
        allFee: function() {
            var arr = this.recruitList;
            var quantity = 0;
            var fee = 0;
            var allFee = 0;
            var day = 0;
            for (var i = 0; i < arr.length; i++) {
                quantity = arr[i].component.quantity;
                fee = arr[i].component.fee;
                if (arr[i].component.beginWorkTime && arr[i].component.endWorkTime) {
                    day = (new Date(arr[i].component.endWorkTime).getTime() - new Date(arr[i].component.beginWorkTime).getTime()) / (24 * 60 * 60 * 1000);
                    day ++;
                } else {
                    day = 0;
                }
                allFee += fee * quantity * day;
            }
            return allFee;
        }
    },
    created: function() {
        var self = this;
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
    },
    mounted: function() {
        var href = window.location.href;
        if (href.split('?')[1]) {
            this.orderId = href.split('?')[1];
        }
    }
});
