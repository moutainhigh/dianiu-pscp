'use strict';
var socialElectricianTem = [
    '   <div>',
    '   <table class="table table-bordered" style="margin-bottom: 15px;position: relative;">',
    '   <input type="hidden" class="electrician-data" @click="pushMyData">',
    '    <tbody>',
    '        <tr>',
    '            <th rowspan="3">',
    '                <input type="checkbox" style="width: 16px;height:16px;" :checked="mydata.checked" @click="checkInfo">',
    '            </th>',
    '            <th>姓名</th>',
    '            <th>电工资质</th>',
    '            <th>费用(元/人/天)</th>',
    '            <th>招募时间</th>',
    '            <th>实际工作时间(天)</th>',
    '            <th>费用小计</th>',
    '            <th>状态</th>',
    '        </tr>',
    '        <tr>',
    '            <td>{{ mydata.electricianname }}</td>',
    '            <td>{{ mydata.certificate }}</td>',
    '            <td>{{ mydata.fee }}</td>',
    '            <td>',
    '                {{ mydata.beginWorkTime }}―――{{ mydata.endWorkTime }}',
    '            </td>',
    '            <td>',
    '                <input type="text" v-model="mydata.actualWorkTime" style="width: 50px;" onkeyup="this.value=this.value.replace(/[^0-9]/g,\'\')" onafterpaste="this.value=this.value.replace(/[^0-9]/g,\'\')" maxlength="2" :readonly="isReadonly" />',
    '            </td>',
    '            <td>{{ mydata.actualWorkTime * mydata.fee }} </td>',
    '            <td>{{ mydata.statusText }}</td>',
    '        </tr>',
    '        <tr>',
    '            <td colspan="7">',
    '                <div class="form-group">',
    '                    <div class="col-sm-2 control-label"><span style="color: red;">*</span>服务态度:</div>',
    '                    <div class="col-sm-2 control-label" style="text-align: left;">',
    '                        <i class="fa red" v-for="(li,index) in 5" :class="{\'fa-star\': index < mydata.evaluate.serviceQuality,\'fa-star-o\': index >= mydata.evaluate.serviceQuality }" @click="qualityClick(index)"></i>',
    '                        <span v-show="mydata.evaluate.serviceQuality == 1" class="elspan red">失望</span>',
    '                        <span v-show="mydata.evaluate.serviceQuality == 2" class="elspan red">不满</span>',
    '                        <span v-show="mydata.evaluate.serviceQuality == 3" class="elspan red">一般</span>',
    '                        <span v-show="mydata.evaluate.serviceQuality == 4" class="elspan red">满意</span>',
    '                        <span v-show="mydata.evaluate.serviceQuality == 5" class="elspan red">惊喜</span>',
    '                    </div>',
    '                </div>',
    '                <div class="form-group">',
    '                    <div class="col-sm-2 control-label"><span style="color: red;">*</span>服务速度:</div>',
    '                    <div class="col-sm-2 control-label" style="text-align: left;">',
    '                        <i class="fa red" v-for="(li,index) in 5" :class="{\'fa-star\': index < mydata.evaluate.serviceSpeed, \'fa-star-o\': index >= mydata.evaluate.serviceSpeed }" @click="speedClick(index)"></i>',
    '                        <span v-show="mydata.evaluate.serviceSpeed == 1" class="elspan red">失望</span>',
    '                        <span v-show="mydata.evaluate.serviceSpeed == 2" class="elspan red">不满</span>',
    '                        <span v-show="mydata.evaluate.serviceSpeed == 3" class="elspan red">一般</span>',
    '                        <span v-show="mydata.evaluate.serviceSpeed == 4" class="elspan red">满意</span>',
    '                        <span v-show="mydata.evaluate.serviceSpeed == 5" class="elspan red">惊喜</span>',
    '                    </div>',
    '                </div>',
    '                <div class="form-group">',
    '                    <div class="col-sm-2 control-label">评价:</div>',
    '                    <div class="col-sm-4" style="padding-left: 15px;">',
    '                        <textarea class="form-control" row="2" v-model="mydata.evaluate.evaluateContent" :readonly="isUpdate||isReadonly"></textarea>',
    '                    </div>',
    '                </div>',
    '                <div class="form-group" style="margin-top: 5px;">',
    '                    <div class="col-sm-2 control-label">评价图片:</div>',
    '                    <div class="col-sm-10" style="padding-left: 15px;text-align: left;">',
    '                        <div v-show="mydata.evaluate.evaluateImageArray&&mydata.evaluate.evaluateImageArray.length > 0" style="display: inline-block;">',
    '                            <ul class="upload-ul">',
    '                                <li class="upload-li" v-for="(img,index) in mydata.evaluate.evaluateImageArray">',
    '                                    <img :src="img" class="upload-img" />',
    '                                    <a class="img-remove-btn" @click="delImg(\'evaluateImageArray\',index)">',
    '                                        <span class="glyphicon glyphicon-remove" v-show="mydata.evaluate.processed == 0"></span>',
    '                                    </a>',
    '                                </li>',
    '                            </ul>',
    '                        </div>',
    '                            <button type="button" class="upload-btn" v-show="mydata.evaluate.processed ==0 && (!mydata.evaluate.evaluateImageArray||mydata.evaluate.evaluateImageArray.length < 5)" @click="addImg"></button>',
    '                            <input type="file" class="evaluateImageArray" @change="onFileChange" style="display: none;" accept="image/gif,image/jpeg,image/jpg,image/png" />',
    '                    </div>',
    '                </div>',
    '            </td>',
    '        </tr>',
    '    </tbody>',
    '    </table>',
    '    </div>'
].join("");

Vue.component('socialWorkOrderList', {
    template: socialElectricianTem,
    props: ['vmdata', 'index'],
    data: function() {
        return {
            mydata: null,
            myindex: null,
            isReadonly: false,
            isUpdate: false
        }
    },
    methods: {
        mouseenter: function(index) {
            this.speed = index + 1;
        },
        mouseleave: function() {
            this.speed = 0;
        },
        qualityClick: function(index) {
            if (this.mydata.evaluate.processed === 1) {
                alert('评价已完成，无法修改！');
                return;
            }
            if (this.isReadonly) {
                alert('请先勾选！');
                return;
            }
            this.mydata.evaluate.serviceQuality = index + 1;
        },
        speedClick: function(index) {
            if (this.mydata.evaluate.processed === 1) {
                alert('评价已完成，无法修改！');
                return;
            }
            if (this.isReadonly) {
                alert('请先勾选！');
                return;
            }
            this.mydata.evaluate.serviceSpeed = index + 1;
        },
        pushMyData: function() {
            this.$emit('push-electrican-data', [this.mydata, this.myindex]);
        },
        addImg: function(e) {
            if (this.mydata.evaluate.processed === 1) {
                alert('评价已完成，无法修改！');
                return;
            }
            if (this.isReadonly) {
                alert('请先勾选！');
                return;
            }

            var o = e.target;
            $(o).siblings('input[type="file"]').trigger('click');
            return false;
        },
        delImg: function() {
            if (this.mydata.evaluate.processed === 1) {
                alert('评价已完成，无法修改！');
                return;
            }
            if (this.isReadonly) {
                alert('请先勾选！');
                return;
            }
            var imgId = arguments[0];
            var index = arguments[1];
            this.mydata.evaluate[imgId].splice(index, 1);
        },
        onFileChange: function(e) {
            var id = e.target.className;
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            this.createImage(id, files);
        },
        createImage: function(id, files) {
            var self = this;
            var leng = files.length;
            if (typeof FileReader === 'undefined') {
                alert('您的浏览器不支持图片上传，请升级您的浏览器');
                return false;
            }
            for (var i = 0; i < leng; i++) {
                var reader = new FileReader();
                reader.readAsDataURL(files[i]);
                reader.onload = function(e) {
                    self.mydata.evaluate.evaluateImageArray.push(e.target.result);
                };
            }
        },
        checkInfo: function() {
            if (!this.mydata.checked) {
                this.mydata.checked = 1;
                this.isReadonly = false;
            } else {
                this.mydata.checked = 0;
                this.isReadonly = true;
                var vmdata = this.vmdata;
                this.mydata = $.extend(true, {}, vmdata);
            }
        }
    },
    created: function() {
        var vmdata = this.vmdata;
        this.mydata = $.extend(true, {}, vmdata);
        if (!this.mydata.evaluate.evaluateImageArray) {
            this.mydata.evaluate.evaluateImageArray = [];
        }
        this.mydata.checked = 0;
        this.myindex = this.index;
        this.isReadonly = true;
        if (this.mydata.evaluate.processed === 1) {
            this.isUpdate = true;
        }
    }
});

var gridParam = {
    url: $ctx + '/cp/workorder/social/list',
    postData: {
        status: 'unpublish'
    },
    datatype: "json",
    colModel: [{
        label: '工单标题',
        name: 'title'
    },
    {
        label: '工单名称',
        name: 'name'
    }, {
        label: '所属项目',
        name: 'projectName'
    }, {
        label: '业主单位',
        name: 'customerName'
    }, {
        label: '电工资质',
        name: 'certificate',
        formatter: function(value, options, row) {
            var content = '';
            var certificates = row.certificate.split(',');
            for (var i = 0; i < certificates.length; i++) {
                content += '<span class="label label-success" style="margin: 0 5px;">' + certificates[i] + '</span>'
            }
            return content;
        }
    }, {
        label: '费用(元/人/天)',
        name: 'fee'
    }, {
        label: '响应人数',
        name: 'responseNumber'
    }, {
        label: '招募人数',
        name: 'recruitNumber'
    }, {
        label: '发布日期',
        name: 'publishDate',
        formatter: 'date'
    }, {
        label: '截止日期',
        name: 'closingDate',
        formatter: 'date'
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
            "overflow": "hidden"
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
    data: function() {
        return {
            isActive: false,
            copyGridParam: null
        };
    },
    methods: {
        toggleTap: function() {
            vm.statusInVm = this.status;
            var copyGridParam = $.extend(true, {}, gridParam);
            copyGridParam.postData = {
                'status': this.status
            }
            $.jgrid.gridUnload('#jqGrid');
            //根据状态更换显示表格信息
            switch (this.status) {
                case 'recruiting':
                    copyGridParam.colModel.push({
                        label: '支付日期',
                        name: 'payTime'
                    });
                    break;
                case 'recruitend':
                    copyGridParam.colModel.splice(5, 1);
                    copyGridParam.colModel.push({
                        label: '支付日期',
                        name: 'payTime'
                    });
                    break;
                case 'finished':
                    copyGridParam.colModel.splice(8, 1);
                    copyGridParam.colModel.splice(6, 1);
                    copyGridParam.colModel.splice(5, 1);
                    copyGridParam.colModel.push({
                        label: '支付日期',
                        name: 'payTime'
                    });
                    copyGridParam.colModel.push({
                        label: '接单人数',
                        name: 'recruitNumber'
                    });
                    copyGridParam.colModel.push({
                        label: '状态',
                        name: 'status'
                    });
                    copyGridParam.colModel.push({
                        label: '结算人数',
                        name: 'liquidateNumber'
                    });
                    break;
                case 'liquidated':
                    copyGridParam.colModel.splice(8, 1);
                    copyGridParam.colModel.splice(6, 1);
                    copyGridParam.colModel.splice(5, 1);
                    copyGridParam.colModel.push({
                        label: '支付日期',
                        name: 'payTime'
                    });
                    copyGridParam.colModel.push({
                        label: '状态',
                        name: 'status'
                    });
                    copyGridParam.colModel.push({
                        label: '接单人数',
                        name: 'recruitNumber'
                    });

            }
            //清除完成时间查询信息
            if (this.status === 'unpublish' || this.status === 'recruiting' || this.status === 'recruitend') {
                vm.q.finishStartTime = '';
                vm.q.finishEndTime = '';
                $('#finishStartTime').find('input[type="text"]').val('');
                $('#finishEndTime').find('input[type="text"]').val('');
            }
            $("#jqGrid").jqGrid(copyGridParam);
        }
    },
    mounted: function() {
        this.isActive = this.selected;
    }
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        showListRe: true,
        showListPay: true,
        statusInVm: 'unpublish',
        needAuth: false,
        title: '',
        imgIp: 'http://192.168.1.251:8091/',
        q: {
            name: '',
            projectName: '',
            customerName: '',
            startTime: '',
            endTime: '',
            finishStartTime: '',
            finishEndTime: ''
        },
        orderDetail: {
            workOrder: {},
            customer: {},
            facilitator: {},
            workOrderLeader: {},
            details: {},
            project: {},
            socialElectricianList: [],
            electricianWorkOrderList: []
        },
        resumeInfo: {
            personalInfo: {
                city: '',
                diploma: '',
                expectedFee: 0,
                synopsis: null,
                workingYears: 0
            },
            electricianInfo: {

            },
            certificateImageList: [],
            certificateList: [],
            workExperiences: []

        },
        payType: 0,
        amount: null,
        freezingAmount: null
    },
    methods: {
        query: function() {
            if (vm.q.startTime || vm.q.endTime) {
                if (new Date(vm.q.startTime).getTime() > new Date(vm.q.endTime).getTime()) {
                    alert('结束时间不能小于开始时间!');
                    return;
                }
            } else if (vm.q.finishStartTime || vm.q.finishEndTime) {
                if (new Date(vm.q.finishStartTime).getTime() > new Date(vm.q.finishEndTime).getTime()) {
                    alert('结束时间不能小于开始时间!');
                    return;
                }
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'status': vm.statusInVm,
                    'name': vm.q.name,
                    'projectName': vm.q.projectName,
                    'customerName': vm.q.customerName,
                    'startTime': vm.q.startTime,
                    'endTime': vm.q.endTime,
                    'finishStartTime': vm.q.finishStartTime,
                    'finishEndTime': vm.q.finishEndTime
                },
                page: page
            }).trigger("reloadGrid");
        },
        cancel: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            confirm('确定要取消选中的工单？', function() {
                $.ajax({
                    type: 'post',
                    url: 'cancel/' + id,
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
            $.ajax({
                url: 'details/' + id,
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        vm.orderDetail = r.result;
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        viewDetail: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            $.ajax({
                url: 'details/' + id,
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        console.log(r);
                        vm.orderDetail = r.result;
                        //转换时间格式
                        vm.orderDetail.details.startTime = getNowFormatDate(r.result.details.startTime);
                        vm.orderDetail.details.endTime = getNowFormatDate(r.result.details.endTime);
                        //判断是否有未审核的社会电工
                        var list = vm.orderDetail.socialElectricianList;
                        for(var i =0;i<list.length;i++){
                            if(list[i].status == 0 ){
                                vm.needAuth = true;
                            }
                        }
                    } else {
                        alert(r.msg);
                    }
                }
            })
            this.title = '工单详情';
            this.showList = false;
            this.showListRe = false;
        },
        orderPay: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            $.ajax({
                url: 'liquidate/' + id + '/details',
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        vm.orderDetail = r.result;
                        console.log(vm.orderDetail);
                        vm.orderDetail.details = {};
                        vm.title = '工单结算';
                        vm.showList = false;
                        vm.showListPay = false;
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        getResumeInfo: function(id) {
            console.log(id);
            $.ajax({
                type: 'post',
                url: 'resume/' + id,
                dataType: 'json',
                success: function(r) {
                    if (r.code === 0) {
                        console.log(r);
                        vm.resumeInfo.personalInfo = r.result.resumeInfo;
                        vm.resumeInfo.electricianInfo = r.result.electricianInfo;
                        vm.resumeInfo.certificateImageList = r.result.certificateImageList;
                        vm.resumeInfo.workExperiences = r.result.experiences;
                    } else {
                        alert(r.msg);
                    }
                }
            });
            var index = layer.open({
                type: 1,
                title: '简历信息',
                skin: 'layui-layer-molv',
                area: ['80%', '75%'],
                closeBtn: 2,
                shadeClose: false,
                btn: ['返回'],
                yes: function(index) {
                    layer.close(index);
                },
                content: jQuery('#resume')
            });
        },
        reload: function() {
            vm.q = {
                name: '',
                projectName: '',
                customerName: '',
                startTime: '',
                endTime: '',
                finishStartTime: '',
                finishEndTime: ''
            };
            vm.orderDetail = {
                workOrder: {},
                customer: {},
                facilitator: {},
                workOrderLeader: {},
                details: {},
                project: {},
                socialElectricianList: []
            };
            vm.showList = true;
            vm.showListRe = true;
            vm.showListPay = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        statusUnkown: function(item) {
            item.auditing = '';
        },
        statusPass: function(item) {
            item.auditing = 'pass';
        },
        statusFail: function(item) {
            item.auditing = 'fail';
        },
        confirmOprate: function() {
            layer.confirm('修改操作后不可更改,确认操作?', {
                btn: ['确认', '取消'],
                yes: function(index) {
                    layer.close(index);
                    var data = { 'id': vm.orderDetail.details.id, 'socialElectricianList': vm.orderDetail.socialElectricianList }
                    $.ajax({
                        type: 'post',
                        url: 'confirm',
                        data: JSON.stringify(data),
                        success: function(r) {
                            if (r.code == 0) {
                                alert('操作成功', function(index) {
                                    vm.reload();
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                },
                btn2: function() {}
            });
        },
        choosePayType: function() {
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
                    data.electricianWorkOrderList = vm.orderDetail.electricianWorkOrderList;
                    data.payType = vm.payType;
                    $.ajax({
                        type: 'post',
                        url: 'liquidate',
                        data: JSON.stringify(data),
                        async: false,
                        success: function(r) {
                            if (r.code == 0) {
                                console.log(r);
                                /*alert('操作成功', function(index) {
                                    layer.close(index);
                                });*/
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
                                    alert('支付成功!');
                                    vm.reload();
                                }
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                },
                content: jQuery('#payType')
            });
        },
        settlement: function() {
            $('.electrician-data').trigger('click');
            var sorList = vm.orderDetail.electricianWorkOrderList;
            var list = [];
            for (var i = 0; i < sorList.length; i++) {
                if (sorList[i].checked === 1) {
                    list.push(sorList[i]);
                }
            }
            if (!list || list.length == 0) {
                alert('未勾选任何订单，无法结算！');
                return;
            }
            var allFee = 0;

            for (var i = 0; i < list.length; i++) {
                if (!list[i].actualWorkTime) {
                    alert('实际工作时间不能为空或0！')
                    return;
                } else if (!list[i].evaluate.serviceQuality) {
                    alert('服务态度未填！')
                    return;
                } else if (!list[i].evaluate.serviceSpeed) {
                    alert('服务速度未填！')
                    return;
                }
                allFee += (list[i].actualWorkTime * list[i].fee)
            }
            if (allFee <= vm.freezingAmount) {
                var data = {};
                data.electricianWorkOrderList = vm.orderDetail.electricianWorkOrderList;
                $.ajax({
                    type: 'post',
                    url: 'liquidate',
                    data: JSON.stringify(data),
                    success: function(r) {
                        if (r.code == 0) {
                            alert('支付成功', function() {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            } else {
                vm.choosePayType();
            }

        },
        getElectricanData: function(data) {
            var obj = data[0];
            var index = data[1];
            this.orderDetail.electricianWorkOrderList[index] = obj;
        },
        chooseBalance: function() {
            vm.payType = 0;
            console.log(vm.payType);
        },
        chooseAlipay: function() {
            vm.payType = 1;
            console.log(vm.payType);
        },
        chooseWeChat: function() {
            vm.payType = 2;
            console.log(vm.payType);
        },
        listPay: function() {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            console.log(ids);
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
                    data.payType = vm.payType;
                    data.socialWorkOrderIds = ids;
                    $.ajax({
                        type: 'post',
                        url: 'payment',
                        async: false,
                        data: JSON.stringify(data),
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
                                        '           <button type="button" class="btn btn-primary" onclick="location.reload();">支付成功</button>&nbsp;&nbsp;&nbsp;',
                                        '           <button type="button" class="b tn btn-warning" onclick="location.reload();">支付失败</button>',
                                        '       </div>',
                                        '   </div>'
                                    ].join("");
                                    var div = $(myDiv);
                                    $(document.body).append(div);
                                } else {
                                    alert('支付成功！', function() {
                                        layer.close(index);
                                        vm.reload();
                                    });
                                }
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                    /*layer.close(index);*/
                },
                btn2: function() {
                    vm.payType = 0;
                },
                content: jQuery('#payTypeList')
            });
        }
    },
    created: function() {
        var self = this;
        $.ajax({
            type: 'post',
            url: '../payment/balance',
            success: function(r) {
                if (r.code == 0) {
                    self.amount = r.result.amount;
                    self.freezingAmount = r.result.freezingAmount;
                } else {
                    alert(r.msg);
                }
            }
        });
    },
    mounted: function() {
        var self = this;
        $("#jqGrid").jqGrid(gridParam);
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
        $('#startTime').on('changeDate', function(ev) {
            self.q.startTime = getNowFormatDate(ev.date);
        });
        $('#startTimeRemove').on('click',function(){
            self.q.startTime = '';
        });
        $('#endTime').on('changeDate', function(ev) {
            self.q.endTime = getNowFormatDate(ev.date);
        });
        $('#endTimeRemove').on('click',function(){
            self.q.endTime = '';
        });
        $('#finishStartTime').on('changeDate', function(ev) {
            self.q.finishStartTime = getNowFormatDate(ev.date);
        });
        $('#finishStartTimeRemove').on('click',function(){
            self.q.finishStartTime = '';
        });
        $('#finishEndTime').on('changeDate', function(ev) {
            self.q.finishEndTime = getNowFormatDate(ev.date);
        });
        $('#finishEndTimeRemove').on('click',function(){
            self.q.finishEndTime = '';
        });
    }
});
