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
    '            <th>支付状态</th>',
    '        </tr>',
    '        <tr>',
    '            <td>{{ mydata.electricianName }}</td>',
    '            <td>{{ mydata.certificate }}</td>',
    '            <td>{{ mydata.fee }}</td>',
    '            <td>',
    '                {{ mydata.beginWorkTime }}―――{{ mydata.endWorkTime }}',
    '            </td>',
    '            <td>',
    '                <input type="text" v-model="mydata.actualWorkTime" style="width: 50px;" onkeyup="this.value=this.value.replace(/[^0-9\.]/g,\'\')" onafterpaste="this.value=this.value.replace(/[^0-9\.]/g,\'\')" maxlength="4" :readonly="isReadonly" />',
    '            </td>',
    '            <td>{{ mydata.actualWorkTime * mydata.fee }} </td>',
    '            <td>{{ mydata.statusText }}</td>',
    '            <td><span v-if="mydata.settlementPayStatus==0">未支付</span><span v-if="mydata.settlementPayStatus==1">已支付</span></td>',
    '        </tr>',
    '        <tr>',
    '            <td colspan="8">',
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
    '                        <textarea class="form-control" row="2" v-model="mydata.evaluate.content" :readonly="mydata.status == 5||isReadonly"></textarea>',
    '                    </div>',
    '                </div>',
    '                <div class="form-group" style="margin-top: 5px;">',
    '                    <div class="col-sm-2 control-label">评价图片:</div>',
    '                    <div class="col-sm-10" style="padding-left: 15px;text-align: left;">',
    '                        <div v-show="mydata.evaluate.attachment&&mydata.evaluate.attachment.length > 0" style="display: inline-block;">',
    '                            <ul class="upload-ul">',
    '                                <li class="upload-li" v-for="(img,index) in mydata.evaluate.attachment">',
    '                                    <img :src="img.fid" class="upload-img" @click="imgEnlarge" />',
    '                                    <a class="img-remove-btn" @click="delImg(\'attachment\',index)">',
    '                                        <span class="glyphicon glyphicon-remove" v-show="mydata.status != 5"></span>',
    '                                    </a>',
    '                                </li>',
    '                            </ul>',
    '                        </div>',
    '                            <button type="button" class="upload-btn" v-show="mydata.status !=5 && (!mydata.evaluate.attachment||mydata.evaluate.attachment.length < 5)" @click="addImg"></button>',
    '                            <input type="file" class="attachment" @change="onFileChange" style="display: none;" accept="image/gif,image/jpeg,image/jpg,image/png" />',
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
            if (this.mydata.status == 5) {
                alert('工单已完成，无法修改评价！');
                return;
            }
            if (this.isReadonly) {
                alert('请先勾选！');
                return;
            }
            this.mydata.evaluate.serviceQuality = index + 1;
        },
        speedClick: function(index) {
            if (this.mydata.status == 5) {
                alert('工单已完成，无法修改评价！');
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
            if (this.mydata.status == 5) {
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
            if (this.mydata.status == 5) {
                alert('评价已完成，无法修改！');
                return;
            }
            if (this.isReadonly) {
                alert('请先勾选！');
                return;
            }
            var imgId = arguments[0];
            var index = arguments[1];
            this.mydata.evaluate.delImgList.push(this.mydata.evaluate[imgId][index].id);
            this.mydata.evaluate[imgId].splice(index, 1);
            this.mydata.evaluate.attachmentIds = this.mydata.evaluate.delImgList.join(',');
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
            var size = files[0].size/1024/1024;
            if(size>2){
                alert('图片不能超过2MB!')
                return;
            }
            for (var i = 0; i < leng; i++) {
                var reader = new FileReader();
                reader.readAsDataURL(files[i]);
                reader.onload = function(e) {
                    self.mydata.evaluate.attachment.push({ 'fid': e.target.result });
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
        if (this.mydata.evaluate.attachment == null) {
            this.mydata.evaluate.attachment = [];
        }
        this.mydata.evaluate.delImgList = [];
        this.mydata.checked = 0;
        this.myindex = this.index;
        this.isReadonly = true;
        if (this.mydata.status == 5) {
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
            label: '订单编号',
            name: 'orderId',
            hidden: true
        }, {
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
            label: '所需电工资质',
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
            name: 'fee',
            width: 110
        }, {
            label: '响应人数',
            name: 'responseNumber',
            width: 80
        }, {
            label: '招募人数',
            name: 'recruitNumber',
            width: 80
        }, {
            label: '发布日期',
            name: 'publishTime',
            formatter: 'date',
            width: 100
        }, {
            label: '截止日期',
            name: 'closingDate',
            formatter: 'date',
            width: 100
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
        $("#jqGrid").closest(".ui-jqgrid-bdiv").css({
            "overflow": "hidden"
        });
    }
}
var myTabs = ['<div>',
    '   <ul class="nav nav-tabs">',
    '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTap" ref="hehe">',
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
    created: function() {
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
            customerInfo: {},
            companyInfo: {},
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
            electricianInfo: {},
            certificateImageList: [],
            certificateList: [],
            workExperiences: []

        },
        payType: 0,
        amount: null,
        freezingAmount: null,
        payTypeList: [],
        payTypeCheckedId: null,
        payAmount: 0,
        walletAmount: 0,
        settlementInfo: {
            actualWorkTime: '',
            fee: '',
            totalFee: '',
            evaluateInfo: {
                attachment: [],
                content: '',
                createTime: '',
                serviceQuality: null,
                serviceSpeed: null
            }
        },
        showTypeName: ''
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
            var orderId = getSelectedRowOrderId();
            confirm('确定要取消选中的工单？', function() {
                $.ajax({
                    type: 'post',
                    url: 'cancel/' + orderId,
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
        viewDetail: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var orderId = getSelectedRowOrderId();
            vm.socialWorkorderId = orderId;
            $.ajax({
                url: 'details/' + orderId,
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        vm.orderDetail = r.result;
                        switch (vm.orderDetail.workOrder.type) {
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
                        //转换时间格式
                        vm.orderDetail.details.startTime = getNowFormatDate(r.result.details.startTime);
                        vm.orderDetail.details.endTime = getNowFormatDate(r.result.details.endTime);
                        //判断是否有未审核的社会电工
                        var list = vm.orderDetail.socialElectricianList;
                        for (var i = 0; i < list.length; i++) {
                            if (list[i].status == 0) {
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
            var orderId = getSelectedRowOrderId();
            vm.socialWorkorderId = orderId;
            //取消的工单无法结算
            var grid = $("#jqGrid");
            var selectedIDs = grid.getGridParam("selarrrow");
            var rowData = grid.jqGrid('getRowData', selectedIDs[0]);
            if(rowData.status == '取消'){
                alert('该工单已取消，不可结算!');
                return;
            }
            $.ajax({
                url: 'liquidate/' + orderId + '/details',
                type: 'post',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        vm.orderDetail = r.result;
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
            $.ajax({
                type: 'post',
                url: 'resume/' + id,
                dataType: 'json',
                success: function(r) {
                    if (r.code === 0) {
                        vm.resumeInfo.personalInfo = r.result.resumeInfo;
                        vm.resumeInfo.electricianInfo = r.result.electricianInfo;
                        vm.resumeInfo.certificateImageList = r.result.certificateImageList;
                        vm.resumeInfo.workExperiences = r.result.experiences;
                        vm.resumeInfo.certificateList = r.result.certificateList;
                    } else {
                        alert(r.msg);
                    }
                }
            });
            var index = layer.open({
                type: 1,
                title: '简历信息',
                scrollbar: false,
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
                customerInfo: {},
                companyInfo: {},
                workOrderLeader: {},
                details: {},
                project: {},
                socialElectricianList: []
            };
            vm.settlementInfo = {
                actualWorkTime: '',
                fee: '',
                totalFee: '',
                evaluateInfo: {
                    attachment: [],
                    content: '',
                    createTime: '',
                    serviceQuality: null,
                    serviceSpeed: null
                }
            }
            vm.showTypeName = '';
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
                    var data = {
                        'id': vm.orderDetail.details.id,
                        'socialElectricianList': vm.orderDetail.socialElectricianList
                    }
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
        getPayType: function() {
            var data = {};
            var orderArray = [];
            data.type = 'socialWorkorderBill';
            data.orderType = 3;
            data.electricianWorkOrderList = vm.orderDetail.electricianWorkOrderList;
            for (var i = 0; i < data.electricianWorkOrderList.length; i++) {
                orderArray.push(data.electricianWorkOrderList[i].orderId);
            }
            data.orderIds = orderArray.join(',');
            //调起支付
            this.socialWorkorderPay(data);
        },
        settlement: function(e) {
            this.preventRepeat(e);
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
                if (parseFloat(list[i].actualWorkTime) > 0) {} else {
                    alert('输入字符不规范！')
                    return;
                }
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
            //冻结金额支付
            var data = {};
            data.electricianWorkOrderList = vm.orderDetail.electricianWorkOrderList;
            var self = this;
            $.ajax({
                type: 'post',
                url: 'liquidate',
                data: JSON.stringify(data),
                success: function(r) {
                    if (r.code == 0) {
                        alert('结算成功', function() {
                            vm.reload();
                        });
                    } else if (r.code == 410) {
                        self.getPayType();
                    } else {
                        alert(r.msg, function() {
                            vm.reload();
                        });
                    }
                }
            });
        },
        getElectricanData: function(data) {
            var obj = data[0];
            var index = data[1];
            this.orderDetail.electricianWorkOrderList[index] = obj;
        },
        choosePayType: function(id) {
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
        listPay: function() {
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }
            var orderId = getSelectedRowOrderId();
            var data = {};
            data.type = 'socialWorkorderPay';
            data.ids = ids;
            data.orderType = 2;
            data.orderIds = orderId;
            this.socialWorkorderPay(data);
        },
        getSettlementInfo: function(electricianId, orderId) {
            var data = {
                orderId: orderId,
                electricianId: electricianId
            };
            $.ajax({
                type: 'post',
                url: 'liquidate/evaluate',
                data: JSON.stringify(data),
                success: function(r) {
                    if (r.code == 0) {
                        vm.settlementInfo = r.result;
                        var index = layer.open({
                            type: 1,
                            title: '结算信息',
                            scrollbar: false,
                            area: ['85%', '75%'],
                            closeBtn: 2,
                            content: jQuery('#settlementInfo')
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }
    },
    created: function() {
        var session = sessionStorage.getItem('fromPay');
        if (session == 1) {
            this.statusInVm = 'recruiting';
        }
        var self = this;
        $.ajax({
            type: 'post',
            url: '../payment/balance',
            success: function(r) {
                if (r.code == 0) {
                    self.amount = parseFloat(r.result.amount);
                    self.freezingAmount = parseFloat(r.result.freezingAmount);
                } else {
                    alert(r.msg);
                }
            }
        });
    },
    mounted: function() {
        var session = sessionStorage.getItem('fromPay');
        if (session == 1) {
            sessionStorage.removeItem('fromPay');
            this.statusInVm = 'recruiting';
            var copyGridParam = $.extend(true, {}, gridParam);
            copyGridParam.postData = {
                'status': this.statusInVm
            }
            $.jgrid.gridUnload('#jqGrid');
            //根据状态更换显示表格信息
            switch (this.statusInVm) {
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
            $("#jqGrid").jqGrid(copyGridParam);
        }else{
            $("#jqGrid").jqGrid(gridParam);
        }
        var self = this; 
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
        $('#startTimeRemove').on('click', function() {
            self.q.startTime = '';
        });
        $('#endTime').on('changeDate', function(ev) {
            self.q.endTime = getNowFormatDate(ev.date);
        });
        $('#endTimeRemove').on('click', function() {
            self.q.endTime = '';
        });
        $('#finishStartTime').on('changeDate', function(ev) {
            self.q.finishStartTime = getNowFormatDate(ev.date);
        });
        $('#finishStartTimeRemove').on('click', function() {
            self.q.finishStartTime = '';
        });
        $('#finishEndTime').on('changeDate', function(ev) {
            self.q.finishEndTime = getNowFormatDate(ev.date);
        });
        $('#finishEndTimeRemove').on('click', function() {
            self.q.finishEndTime = '';
        });
    }
});