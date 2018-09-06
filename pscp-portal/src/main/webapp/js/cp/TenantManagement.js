var gridParam = {
    url: "../renter/list",
    datatype: "json",
    autowidth: true,
    height: 400,
    colModel: [
        {
            label: "手机号码",
            name: "mobile",
            width: 80
        }, {
            label: "企业名称",
            name: "name",
            width: 100
        }, {
            label: "联系人",
            name: "contract",
            width: 100
        }, {
            label: "创建日期",
            name: "createTime",
            width: 100
        }, {
            label: "操作",
            formatter: function (value, grid, rows, state) {
                var tenantId = rows.renterId;
                var configId = rows.configId;
                return '<button type = "button"  onclick = "seeFn(' + tenantId + ')" class = "btn btn-success td-btn" >查看</button> ' + ' <button type = "button" onclick = "editFn(' + tenantId + ',' + configId + ')" class = "btn btn-primary td-btn" >编辑</button> ';
            },
            width: 150,
            align: "center"
        }
    ],
    viewrecords: true,
    rowNum: 10,
    pager: "#jqGridPager",
    rowList: [
        10, 30, 50
    ],
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
};

var vm = new Vue({
    el: "#tenantManagement",
    data() {
        return {
            value6: '',
            tenantList: [],
            q: {
                mobile: "",
                name: "",
                contract: "",
                status: "",
                startTime: "",
                endTime: ""
            },
            tenant: {
                name: "",
                mobile: "",
                contract: "",
                pwd: "",
                address: "",
            },
            configInfo: {
                buildingId: "",
                name: "",
                rate: ""
            },
            flag: true,
            showList: true, //租客列表
            showadd: true, //新建租客
            detailsPage: true, //租客详情页
            equipmentPage: true, //设备详情页
            seeDetails: true //所有信息展示
        };
    },
    computed: {},
    methods: {
        newTenant() {
            this.showList = !this.showList;
            this.tenant.name = "";
            this.tenant.mobile = "";
            this.tenant.contract = "";
            this.tenant.pwd = "";
            this.tenant.address = "";
            $(".existing").removeAttr('disabled');
        },
        existing() {
            var mobile = this.tenant.mobile;
            $.ajax({
                url: "../renter/isUserExist?mobile=" + mobile,
                type: "GET",
                // data: mobile,
                success(res) {
                    if (res.exist == true) {
                        $(".existing").attr('disabled', 'disabled');
                    } else {
                        $(".existing").removeAttr('disabled');
                    }
                }
            })
        },
        addInfoList() {
            var self = this;

            if (self.tenant.name == '' && self.tenant.mobile == '' && self.tenant.contract == '' && self.tenant.pwd == '') {
                alert("请填写内容");
                return;
            }

            var formData = self.tenant;
            if (self.flag == true) {
                $.ajax({
                    url: "../renter/save",
                    type: "POST",
                    data: JSON.stringify(formData),
                    success(res) {
                        var renterId = res.renterId;
                        sessionStorage.setItem("renterId", renterId);
                        if (res.code == 0) {
                            self.flag = false;
                            setTimeout(function () {
                                self.showList = !self.showList;
                                self.showadd = false;
                                self.flag = true
                            }, 1000);
                        } else {
                            alert(res.msg)
                        }
                    },
                    error() {
                    }
                });
            } else if (self.flag == false) {
                alert("请勿重复提交")
            }
        },
        query: function () {
            var x = new Date(this.q.startTime).getTime();
            var y = new Date(this.q.endTime).getTime();
            if (y < x) {
                alert('结束时间必须晚于开始时间');
            } else {
                var page = $("#jqGrid").jqGrid('getGridParam', 'page');
                var postData = {
                    'mobile': vm.q.mobile,
                    'companyName': vm.q.name,
                    'contract': vm.q.contract,
                    'status': vm.q.status,
                    'sdate': vm.q.startTime,
                    'bdate': vm.q.endTime
                };
                $("#jqGrid").jqGrid('setGridParam', {
                    postData: postData,
                    page: page
                }).trigger("reloadGrid");
            }
        },
        empty: function () {
            this.q = {
                mobile: "",
                name: "",
                contract: "",
                status: "",
                startTime: "",
                endTime: ""
            };
            window.location.reload()
        },
        grid: function () {
            $("#jqGrid").jqGrid(gridParam) //加载表格
        },
        reload: function () {
            vm.value6 = '',
                vm.tenantList = [],
                vm.q = {
                    mobile: "",
                    name: "",
                    contract: "",
                    status: "",
                    startTime: "",
                    endTime: "",
                },
                vm.tenant = {
                    name: "",
                    mobile: "",
                    contract: "",
                    pwd: "",
                    address: "",
                },
                vm.configInfo = {
                    buildingId: "",
                    name: "",
                    rate: ""
                },
                vm.flag = true,
                vm.showList = true, //租客列表
                vm.showadd = true, //新建租客
                vm.detailsPage = true, //租客详情页
                vm.equipmentPage = true, //设备详情页
                vm.seeDetails = true //所有信息展示
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {page: page}).trigger("reloadGrid");
            this.grid();
        },
    },
    created: function () {

    },
    mounted() {
        var self = this
        //选择时间
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
        //表格
        this.grid();
    }
});

///查看租客信息
var tenid;
var conFigId;

function seeFn(tenantId) {
    $('input,select,textarea', $('form[name="my_form"]')).attr('readonly', true);
    vm.seeDetails = !vm.seeDetails;
    tenid = tenantId;
    $.ajax({
        url: "../renter/detail/" + tenid,
        type: "GET",
        success: function (res) {
            $('#seename').val(res.renter.name);
            $('#seemobile').val(res.renter.mobile);
            $('#seecontract').val(res.renter.contract);
            $('#seeaddress').val(res.renter.address);
        }
    });
    $.ajax({
        url: "../renter/renterConfig/" + tenid,
        type: "GET",
        success: function (result) {
            var res = result.renterConfig;
            var seechargeMode = res.chargeMode;
            var seemeterList = res.meterList;
            if (seechargeMode == 1) {
                $(".seebeforehand").find('i').addClass('active');
                $(".seemonthlyKnot").find('i').removeClass('active');
                $("#seegiveanAlarm").css("display", "block");
            } else {
                $(".seemonthlyKnot").find('i').addClass('active');
                $(".seebeforehand").find('i').removeClass('active');
                $("#seegiveanAlarm").css("display", "none")
            }
            var seesubChargeMode = sessionStorage.getItem("subChargeMode");
            if (seesubChargeMode == 0) {
                $('#seeunitPrice').css("display", "block");
            } else if (seesubChargeMode == 1) {
                $('#seetimeSharing').css("display", "block");
            }
            $("#seeamountLimit").val(res.amountLimit);
            $("#seestartTime").val(res.startTime);
            $("#seeendTime").val(res.endTime);
            $('#seebasePrice').val(res.basePrice);
            $('#seeapexPrice').val(res.apexPrice);
            $('#seepeakPrice').val(res.peakPrice);
            $('#seeflatPrice').val(res.flatPrice);
            $('#seevalleyPrice').val(res.valleyPrice);
            var html = "";
            $(".seehtmllist").empty();
            for (var i = 0; i < seemeterList.length; i++) {
                html += '<div class="clearfix" style="padding:20px 0;">\n' +
                    '                <div class="col-lg-4">\n' +
                    '                    <div class="input-group">\n' +
                    '                        <span class="input-group-addon">所属楼宇</span>\n' +
                    '                        <input type="text" readonly id="seebuildingNum" class="form-control" value="' + seemeterList[i].buildingName + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" autocomplete="off" style="background:none;">\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '                <div class="col-lg-4">\n' +
                    '                    <div class="input-group">\n' +
                    '                        <span class="input-group-addon">选择设备</span>\n' +
                    '                        <input type="text" readonly id="seeequipmentName" class="form-control" value="' + seemeterList[i].name + '" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" autocomplete="off" style="background:none;">\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '                <div class="col-lg-3">\n' +
                    '                    <div class="input-group">\n' +
                    '                        <span class="input-group-addon">电费占比</span>\n' +
                    '                        <input type="text" readonly id="seeratio" class="form-control" value="' + seemeterList[i].rate + '" style="background:none;">\n' +
                    '                        <span class="input-group-addon">%</span>\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '            </div>';
            }
            $(".seehtmllist").append(html);
        }
    });
}

//查看返回
$("#back").on('click', function () {
    vm.seeDetails = !vm.seeDetails
});

function editFn(tenantId, configId) {
    vm.detailsPage = !vm.detailsPage;
    tenid = tenantId;
    conFigId = configId;
    sessionStorage.setItem("tenid", tenid);
    sessionStorage.setItem("conFigId", conFigId);
    $.ajax({
        url: "../renter/detail/" + tenid,
        type: "GET",
        success: function (res) {
            $('#name').val(res.renter.name);
            $('#mobile').val(res.renter.mobile);
            $('#contract').val(res.renter.contract);
            $('#address').val(res.renter.address);
        }
    });
}

function backshowlist() {
    vm.showList = true
    vm.detailsPage = true
}

//选择缴费方式
var chargeMode;
$('.addpaymethod').on('click', 'div', function () {
    $(this).find('i').addClass('active');
    $(this).siblings().find('i').removeClass('active');
    chargeMode = $(this).find('span').attr('id');
    if (chargeMode == 1) {
        $(this).parent().next('#giveanAlarm').css('display', 'block');
    } else {
        $(this).parent().next('#giveanAlarm').css('display', 'none')
    }
});
//计时方法
var companyId = sessionStorage.getItem('companyId');
var subChargeMode = ""; //计时方法
$.ajax({
    url: "../renter/getChargeMode/" + companyId,
    type: "GET",
    ContentType: 'application/json',
    success: function (res) {
        var code = res.subChargeMode;
        subChargeMode = code;
        sessionStorage.setItem("subChargeMode", subChargeMode);
        if (code == 0) {
            $('#unitPrice').css("display", "block");
        } else if (code == 1) {
            $('#timeSharing').css("display", "block");
        }
    }
});

var meterList = [{id: "", name: "", rate: "", buildingId: ""}];  //楼宇设备列表
//楼宇列表
$('.htmllist').on('click', '#buildingNum', function () {
    $.ajax({
        url: "../renter/buildingList",
        type: "POST",
        success: function (res) {
            var data = res.buildingList;
            var list = '';
            var buildId = [];
            var buildName = '';
            for (var i = 0; i < data.length; i++) {
                buildId = data[i].id;
                buildName = data[i].name;
                list += '<li class="buList" name="' + buildName + '" buildId="' + buildId + '">' + buildName + '</li>'
            }
            $('.buildinglist').empty();
            $('.buildinglist').append(list);
        }
    });
});
//选择楼宇方法
$('.htmllist').on('click', '.buList', function () {
    $(this).parent().parent().parent().next().children().find('input').val('');
    $(this).parent().parent().parent().next().next().children().find('input').val('');
    var index = $(this).parent().parent().parent().parent().index();
    var id = $(this).attr("buildId");
    var name = $(this).attr("name");
    var self = $(this);
    $(this).parent().prev().val(name);
    self.parent().prev().val(name);
    meterList[index].buildingId = id
    var buidIds = [];
    buidIds.push(id);
    $.ajax({
        url: "../renter/meterList",
        type: "POST",
        data: JSON.stringify(buidIds),
        success: function (res) {
            var list = res.meterList;
            var html = '';
            var equipmentName = '';
            var equipmentId = '';
            for (var i = 0; i < list.length; i++) {
                equipmentName = list[i].name;
                equipmentId = list[i].id;
                html += '<li class="eqList" name="' + equipmentName + '" id="' + equipmentId + '">' + equipmentName + '</li>'
            }
            $('.equipmentList').empty();
            $('.equipmentList').append(html);
        }
    })
});
//选择设备的方法
$(".htmllist").on('click', '.eqList', function () {
    var name = $(this).attr('name');
    var eqId = $(this).attr('id');
    var that = $(this);
    var index = $(this).parent().parent().parent().parent().index();

    that.parent().prev().val(name);
    meterList[index].name = name;
    meterList[index].id = eqId;
});
//电费占比
$(".htmllist").on('blur', '#ratio', function () {
    var ratio = $(this).val();
    var index = $(this).parent().parent().parent().index();
    meterList[index].rate = ratio
});
//添加一条
$('.htmllist').on('click', '.addHtml', function () {
    var html = '<div class="clearfix" style="padding:20px 0;">\n' +
        '<div class="col-lg-3" style="padding:0;">\n' +
        '     <div class="input-group">\n' +
        '         <span class="input-group-addon">所属楼宇</span>\n' +
        '         <input type="text" class="form-control" id="buildingNum" value="" type="button"\n' +
        '                   data-toggle="dropdown"\n' +
        '                                   aria-haspopup="true" aria-expanded="false" autocomplete="off">\n' +
        '            <ul class="dropdown-menu buildinglist" style="left:80px;right:0;">\n' +
        '\n' +
        '            </ul>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <div class="col-lg-3" style="padding:0;">\n' +
        '        <div class="input-group">\n' +
        '            <span class="input-group-addon">选择设备</span>\n' +
        '            <input type="text" class="form-control" id="equipmentName" type="button"\n' +
        '                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" autocomplete="off">\n' +
        '            <ul class="dropdown-menu equipmentList" style="left:80px;right:0;">\n' +
        '\n' +
        '            </ul>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <div class="col-lg-3" style="padding:0;">\n' +
        '        <div class="input-group">\n' +
        '            <span class="input-group-addon">电费占比</span>\n' +
        '            <input type="text" class="form-control" id="ratio" autocomplete="off">\n' +
        '                        <span class="input-group-addon">%</span>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <div class="col-sm-2">\n' +
        '        <a class="removeHtml"><i class="fa fa-minus-square-o"></i></a>\n' +
        '    </div>\n' +
        '</div>';
    $('.htmllist').append(html);
    meterList.push({
        id: "", name: "", rate: "", buildingId: ""
    });
});
//移除一条
$('.htmllist').on('click', '.removeHtml', function () {
    var index = $(this).parent().parent().index();
    meterList.splice(index, 1);
    $(this).parent().parent().remove();
});

//////提交租客配置
var formData = {
    chargeMode: "",
    amountLimit: "",
    startTime: "",
    endTime: "",
    subChargeMode: "",
    basePrice: "",
    apexPrice: "",
    peakPrice: "",
    flatPrice: "",
    valleyPrice: "",
    meterList: []
};

$('#configInfo').on('click', function () {
    var _that = vm;
    var tenAntId = sessionStorage.getItem("renterId");
    formData.chargeMode = chargeMode;
    formData.amountLimit = $("#amountLimit").val();
    formData.startTime = $("#startTime").val();
    formData.endTime = $("#endTime").val();
    formData.subChargeMode = subChargeMode;
    formData.basePrice = $('#basePrice').val();
    formData.apexPrice = $('#apexPrice').val();
    formData.peakPrice = $('#peakPrice').val();
    formData.flatPrice = $('#flatPrice').val();
    formData.valleyPrice = $('#valleyPrice').val();
    formData.meterList = meterList;
    $.ajax({
        url: "../renter/renterConfig/save/" + tenAntId,
        type: "POST",
        data: JSON.stringify(formData),
        success: function (res) {
            // console.log(res);
            if (res.code == 0) {
                _that.showList = true;
                _that.showadd = true;
                alert("配置成功");
            } else {
                alert("提交失败" + res.msg)
            }
        }
    })
});

$('#backshowList').on('click', function () {
    vm.showList = true;
    vm.showadd = true;
});


var edchargeMode; //编辑页缴费方式


function next() {
    var renterid = sessionStorage.getItem("tenid");
    var name = $("#name").val();
    var mobile = $("#mobile").val();
    var contract = $("#contract").val();
    var pwd = $("#pwd").val();
    var address = $("#address").val();
    var data = {id: renterid, name: name, mobile: mobile, contract: contract, pwd: pwd, address: address};
    $.ajax({
        url: "../renter/save",
        type: "POST",
        data: JSON.stringify(data),
        success: function (res) {
            if (res.code == 0) {
                vm.detailsPage = !vm.detailsPage;
                vm.equipmentPage = !vm.equipmentPage;
                shebei()
            } else {
                alert(res.msg)
            }
        }
    });
}

function shebei() {
    $.ajax({
        url: "../renter/renterConfig/" + tenid,
        type: "GET",
        success: function (result) {
            var res = result.renterConfig;
            edchargeMode = res.chargeMode;
            edmeterList = res.meterList;
            var n = edmeterList.length;
            if (edchargeMode == 1) {
                $(".beforehand").find('i').addClass('active');
                $(".monthlyKnot").find('i').removeClass('active');
                $("#edgiveanAlarm").css("display", "block");
                $("#edamountLimit").val();
            } else if (edchargeMode == 2) {
                $(".monthlyKnot").find('i').addClass('active');
                $(".beforehand").find('i').removeClass('active');
                $("#edgiveanAlarm").css("display", "none")
            } else {
                $(".monthlyKnot").find('i').removeClass('active');
                $(".beforehand").find('i').removeClass('active');
                $("#edgiveanAlarm").css("display", "none")
            }
            var edsubChargeMode = sessionStorage.getItem("subChargeMode");
            if (edsubChargeMode == 0) {
                $('#edunitPrice').css("display", "block");
            } else if (edsubChargeMode == 1) {
                $('#edtimeSharing').css("display", "block");
            }
            $("#edamountLimit").val(res.amountLimit);
            $("#edstartTime").val(res.startTime);
            $("#edendTime").val(res.endTime);
            $('#edbasePrice').val(res.basePrice);
            $('#edapexPrice').val(res.apexPrice);
            $('#edpeakPrice').val(res.peakPrice);
            $('#edflatPrice').val(res.flatPrice);
            $('#edvalleyPrice').val(res.valleyPrice);
            var html = "";
            if (n == 0) {
                var edmeterList = [{id: "", name: "", rate: "", buildingId: ""}];
                sessionStorage.setItem('edmeterList', JSON.stringify(edmeterList));
                html += '<div class="clearfix" style="padding:20px 0;">\n' +
                    '                <div class="col-lg-4">\n' +
                    '                    <div class="input-group">\n' +
                    '                        <span class="input-group-addon" style=" border:none;">所属楼宇</span>\n' +
                    '                        <input type="text" class="form-control" id="edbuildingNum" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" autocomplete="off">\n' +
                    '                        <ul class="dropdown-menu buildinglist" style="left:80px;right:0;">\n' +
                    '                        </ul>\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '                <div class="col-lg-4">\n' +
                    '                    <div class="input-group">\n' +
                    '                        <span class="input-group-addon" style=" border:none;">选择设备</span>\n' +
                    '                        <input type="text" class="form-control" id="edequipmentName" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" autocomplete="off">\n' +
                    '                        <ul class="dropdown-menu equipmentList" style="left:80px;right:0;">\n' +
                    '                        </ul>\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '                <div class="col-lg-4">\n' +
                    '                    <div class="input-group">\n' +
                    '                        <span class="input-group-addon" style=" border:none;">电费占比</span>\n' +
                    '                        <input type="text" id="edratio" class="form-control">\n' +
                    '                        <span class="input-group-addon">%</span>\n' +
                    '                    </div>\n' +
                    '                </div>\n' +
                    '                <div class="col-sm-2">\n' +
                    '                    <a class="addHtml"><i class="fa fa-plus-square-o"></i></a>\n' +
                    '                </div>\n' +
                    '            </div>';
                $(".edhtmllist").empty();
                $(".edhtmllist").append(html);
            } else {
                for (var i = 0; i < edmeterList.length; i++) {
                    sessionStorage.setItem('edmeterList', JSON.stringify(edmeterList));
                    html += '<div class="clearfix" style="padding:20px 0;">\n' +
                        '                <div class="col-lg-4">\n' +
                        '                    <div class="input-group">\n' +
                        '                        <span class="input-group-addon">所属楼宇</span>\n' +
                        '                        <input type="text" class="form-control" id="edbuildingNum" value="' + edmeterList[i].buildingName + '" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" autocomplete="off">\n' +
                        '                        <ul class="dropdown-menu buildinglist" style="left:80px;right:0;">\n' +
                        '\n' +
                        '                        </ul>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '                <div class="col-lg-4">\n' +
                        '                    <div class="input-group">\n' +
                        '                        <span class="input-group-addon">选择设备</span>\n' +
                        '                        <input type="text" class="form-control" id="edequipmentName" value="' + edmeterList[i].name + '" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" autocomplete="off">\n' +
                        '                        <ul class="dropdown-menu equipmentList" style="left:80px;right:0;">\n' +
                        '                        </ul>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '                <div class="col-lg-4">\n' +
                        '                    <div class="input-group">\n' +
                        '                        <span class="input-group-addon">电费占比</span>\n' +
                        '                        <input type="text" id="edratio" value="' + edmeterList[i].rate + '" class="form-control">\n' +
                        '                        <span class="input-group-addon">%</span>\n' +
                        '                    </div>\n' +
                        '                </div>\n' +
                        '                <div class="col-sm-2">\n' +
                        '                    <a class="' + (i == 0 ? "addHtml" : "removeHtml") + '"><i class="fa ' + (i == 0 ? "fa-plus-square-o" : "fa-minus-square-o") + '"></i></a>\n' +
                        '                </div>\n' +
                        '            </div>';
                    $(".edhtmllist").empty();
                    $(".edhtmllist").append(html);
                }
            }
        }
    })
}

//编辑计费方式
$('.paymethod').on('click', 'div', function () {
    console.log(conFigId)
    if (edchargeMode == null || conFigId == null) {
        $(this).find('i').addClass('active');
        $(this).siblings().find('i').removeClass('active');
        edchargeMode = $(this).find('span').attr('id');
        if (edchargeMode == 1) {
            $(this).parent().next('#edgiveanAlarm').css('display', 'block');
        } else {
            $('#edamountLimit').val('');
            $(this).parent().next('#edgiveanAlarm').css('display', 'none');
        }
    } else {
        alert("不可更改缴费方式")
    }
});

//点击获取到楼宇
$('.edhtmllist').on('click', '#edbuildingNum', function () {
    $.ajax({
        url: "../renter/buildingList",
        type: "POST",
        success: function (res) {
            var data = res.buildingList;
            var list = '';
            var buildId = [];
            var buildName = '';
            for (var i = 0; i < data.length; i++) {
                buildId = data[i].id;
                buildName = data[i].name;
                list += '<li class="buList" name="' + buildName + '" buildId="' + buildId + '">' + buildName + '</li>'
            }
            $('.buildinglist').empty();
            $('.buildinglist').append(list);
        }
    });
});
/////////编辑选择楼宇的方法
$('.edhtmllist').on('click', '.buList', function () {
    var edmeterList = JSON.parse(sessionStorage.getItem('edmeterList'));
    $(this).parent().parent().parent().next().children().find('input').val('');
    $(this).parent().parent().parent().next().next().children().find('input').val('');
    var id = $(this).attr("buildId");
    var buidIds = [];
    buidIds.push(id);
    var index = $(this).parent().parent().parent().parent().index();
    var name = $(this).attr("name");
    var self = $(this);

    self.parent().prev().val(name);
    edmeterList[index].buildingId = id;

    $.ajax({
        url: "../renter/meterList",
        type: "POST",
        data: JSON.stringify(buidIds),
        success: function (res) {
            var list = res.meterList;
            var html = '';
            var equipmentName = '';
            var equipmentId = '';
            for (var i = 0; i < list.length; i++) {
                equipmentName = list[i].name;
                equipmentId = list[i].id;
                html += '<li class="eqList" name="' + equipmentName + '" id="' + equipmentId + '">' + equipmentName + '</li>'
            }
            $('.equipmentList').empty();
            $('.equipmentList').append(html);
        }
    });
    sessionStorage.setItem('edmeterList', JSON.stringify(edmeterList))
});
/////编辑选择设备的方法
$(".edhtmllist").on('click', '.eqList', function () {
    var edmeterList = JSON.parse(sessionStorage.getItem('edmeterList'));

    var index = $(this).parent().parent().parent().parent().index();
    var name = $(this).attr('name');
    var eqId = $(this).attr('id');
    var that = $(this);

    that.parent().prev().val(name);
    edmeterList[index].name = name;
    edmeterList[index].id = eqId;

    sessionStorage.setItem('edmeterList', JSON.stringify(edmeterList))
});
////编辑电费占比
$(".edhtmllist").on('blur', '#edratio', function () {
    var edmeterList = JSON.parse(sessionStorage.getItem('edmeterList'));
    var index = $(this).parent().parent().parent().index();
    var ratio = $(this).val();
    edmeterList[index].rate = ratio
    sessionStorage.setItem('edmeterList', JSON.stringify(edmeterList))
});
////编辑添加一条的方法
$('.edhtmllist').on('click', '.addHtml', function () {
    var edmeterList = JSON.parse(sessionStorage.getItem('edmeterList'));
    var html = '<div class="clearfix" style="padding:20px 0;">\n' +
        '<div class="col-lg-3" style="padding:0;">\n' +
        '     <div class="input-group">\n' +
        '         <span class="input-group-addon">所属楼宇</span>\n' +
        '         <input type="text" class="form-control" id="edbuildingNum" value="" type="button"\n' +
        '                   data-toggle="dropdown"\n' +
        '                                   aria-haspopup="true" aria-expanded="false" autocomplete="off">\n' +
        '            <ul class="dropdown-menu buildinglist" style="left:80px;right:0;">\n' +
        '\n' +
        '            </ul>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <div class="col-lg-3" style="padding:0;">\n' +
        '        <div class="input-group">\n' +
        '            <span class="input-group-addon">选择设备</span>\n' +
        '            <input type="text" class="form-control" id="edequipmentName" type="button"\n' +
        '                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" autocomplete="off">\n' +
        '            <ul class="dropdown-menu equipmentList" style="left:80px;right:0;">\n' +
        '\n' +
        '            </ul>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <div class="col-lg-3" style="padding:0;">\n' +
        '        <div class="input-group">\n' +
        '            <span class="input-group-addon">电费占比</span>\n' +
        '            <input type="text" class="form-control" id="edratio" autocomplete="off">\n' +
        '                        <span class="input-group-addon">%</span>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <div class="col-sm-2">\n' +
        '        <a class="removeHtml"><i class="fa fa-minus-square-o"></i></a>\n' +
        '    </div>\n' +
        '</div>';
    $('.edhtmllist').append(html);
    edmeterList.push({
        id: "", name: "", rate: "", buildingId: ""
    });

    sessionStorage.setItem('edmeterList', JSON.stringify(edmeterList))
});
////编辑移除一条
$('.edhtmllist').on('click', '.removeHtml', function () {
    var edmeterList = JSON.parse(sessionStorage.getItem('edmeterList'));
    var index = $(this).parent().parent().index();
    edmeterList.splice(index, 1);
    $(this).parent().parent().remove();
    sessionStorage.setItem('edmeterList', JSON.stringify(edmeterList))
});
////编辑提交租客配置
var edformData = {
    id: "",
    chargeMode: "",
    amountLimit: "",
    startTime: "",
    endTime: "",
    subChargeMode: "",
    basePrice: "",
    apexPrice: "",
    peakPrice: "",
    flatPrice: "",
    valleyPrice: "",
    meterList: []
};
$('#edconfigInfo').on('click', function () {
    var self = vm
    var subchargemode = sessionStorage.getItem("subChargeMode");
    var tenAntId = sessionStorage.getItem("tenid");
    var configid = sessionStorage.getItem("conFigId");
    edformData.id = configid;
    edformData.chargeMode = edchargeMode;
    edformData.amountLimit = $("#edamountLimit").val();
    edformData.startTime = $("#edstartTime").val();
    edformData.endTime = $("#edendTime").val();
    edformData.subChargeMode = subchargemode;
    edformData.basePrice = $('#edbasePrice').val();
    edformData.apexPrice = $('#edapexPrice').val();
    edformData.peakPrice = $('#edpeakPrice').val();
    edformData.flatPrice = $('#edflatPrice').val();
    edformData.valleyPrice = $('#edvalleyPrice').val();
    edformData.meterList = JSON.parse(sessionStorage.getItem('edmeterList'));
    $.ajax({
        url: "../renter/renterConfig/save/" + tenAntId,
        type: "POST",
        data: JSON.stringify(edformData),
        success: function (res) {
            if (res.code == 0) {
                sessionStorage.removeItem('edmeterList');
                alert("修改成功", function () {
                    vm.reload()
                });
                self.showList = true;
                self.equipmentPage = true;
            } else {
                alert(res.msg)
            }
        }
    })
});

$('#edbackshowList').on('click', function () {
    sessionStorage.removeItem('edmeterList');
    vm.showList = true;
    vm.equipmentPage = true;
});