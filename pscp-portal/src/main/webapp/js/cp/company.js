var vm = new Vue({
    el: '#rrapp',
    data: {
        id: '',
        status: 0,
        memberType:1,
        isAuthSuccess: false,
        notAuth: false,
        enterpriseQualificationImgListLength: null,
        readonly: true,
        changeInfoBtn: '修改信息',
        province: {
            id: 0,
            name: '请选择省份'
        },
        provinces: [],
        city: {
            id: 0,
            name: '请选择城市'
        },
        citys: [],
        area: {
            id: 0,
            name: '请选择地区'
        },
        areas: [],
        company: {
            name: '',
            userName: '',
            businessLicence: '',
            address: '',
            website: '',
            contacts: '',
            mobile: '',
            memberType:1,
            phone: '',
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
        wait: 60,
        newMobile: {
            mobile: '',
            msgCode: '',
            msgId: ''
        }
    },
    methods: {
        getProvince: function (name, id) {
            if (id != 0) {
                vm.province.name = name;
                vm.province.id = id;
                vm.company.provinceId = id;
                vm.city.name = '请选择城市';
                vm.area.name = '请选择地区';
                vm.company.areaId = null;
                var self = this;
                data = {
                    'provinceId': id
                };
                $.ajax({
                    url: '../basearea/citys',
                    type: 'post',
                    data: data,
                    dataType: 'json',
                    contentType: "application/x-www-form-urlencoded",
                    success: function (r) {
                        if (r.code == 0) {
                            self.citys = r.citys;
                        } else {

                        }
                    }
                });

            } else {

            }

        },
        getCity: function (name, id) {
            if (id != 0) {
                vm.city.name = name;
                vm.city.id = id;
                vm.company.cityId = id;
                vm.area.name = '请选择地区';
                vm.company.areaId = null;
                var self = this;
                $.ajax({
                    url: '../basearea/areas',
                    type: 'post',
                    data: {
                        'cityId': id
                    },
                    dataType: 'json',
                    contentType: "application/x-www-form-urlencoded",
                    success: function (r) {
                        if (r.code == 0) {
                            self.areas = r.areas;
                        } else {

                        }
                    }
                });
            }
        },
        getArea: function (name, id) {
            vm.area.name = name;
            vm.company.areaId = id;
        },
        addImg: function (imgId) {
            $('#' + imgId).trigger('click');
            return false;
        },
        delImg: function () {
            var imgId = arguments[0];
            var index = arguments[1];
            var length = imgId + 'Length';
            if (index != null) {
                if (vm.isBase64(vm.company[imgId][index].imgUrl)) {
                    vm.company[imgId].splice(index, 1);
                } else {
                    vm.company[imgId][index].status = -1;
                }
                vm.getListLength(imgId);
            } else {
                vm.company[imgId].status = -1;
                vm.company[imgId].imgUrl = '';
            }
            $('#' + imgId).val('');
        },
        onFileChange: function (e) {
            var id = e.target.id;
            var files = e.target.files || e.dataTransfer.files;
            if (!files.length) return;
            this.createImage(id, files);
        },
        createImage: function (id, files) {
            if (typeof FileReader === 'undefined') {
                alert('您的浏览器不支持图片上传，请升级您的浏览器');
                return false;
            }
            var size = files[0].size / 1024 / 1024;
            if (size > 2) {
                alert('图片不能超过2MB!')
                return;
            }
            var leng = files.length;
            var img = vm.company[id];
            if (this.isArray(img)) {
                for (var i = 0; i < leng; i++) {
                    var reader = new FileReader();
                    reader.readAsDataURL(files[i]);
                    reader.onload = function (e) {
                        vm.company[id].push({
                            status: 1,
                            imgId: '',
                            imgUrl: e.target.result
                        });
                        vm.getListLength(id);
                    };
                }
            } else {
                if (leng > 0) {
                    var reader = new FileReader();
                    reader.readAsDataURL(files[0]);
                    reader.onload = function (e) {
                        vm.company[id].status = 1;
                        vm.company[id].imgUrl = e.target.result;
                    };
                }
            }
        },
        isArray: function (o) {
            return Object.prototype.toString.call(o) == '[object Array]';
        },
        isBase64: function (o) {
            return o != null && (o.substring(0, 4) === 'data');
        },
        getListLength: function (imgId) {
            var com = this.company;
            var list = this.company[imgId];
            var length = imgId + 'Length';
            this[length] = 0;
            for (i in list) {
                if (list[i].status != -1) {
                    this[length]++;
                }
            }
            if (this[length] == 0) {
                this[length] = null;
            }
        },
        toggleReadonly: function () {
            vm.readonly = vm.readonly === false;
            if (vm.readonly === false) {
                vm.changeInfoBtn = '取消修改';
            } else {
                vm.reload();
            }
        },
        saveOrUpdate: function (event) {
            this.preventRepeat(event);
            var id = this.company.id;
            var url = "";
            var alertInfo = '';
            if (id == null || id == "") {
                url = "../cp/facilitator/save";
                alertInfo = '提交成功,等待审核';
            } else {
                url = "../cp/facilitator/update";
                alertInfo = '修改成功';
            }
            vm.$validator.validateAll().then(function () {
                $.ajax({
                    type: "POST",
                    url: url,
                    data: JSON.stringify(vm.company),
                    success: function (r) {
                        if (r.code == 0) {
                            alert(alertInfo, function (index) {
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
            }, function () {
                alert('信息填写不完整或不规范');
            })

        },
        imgEnlargeTem: function (e) {
            var afterSrc = e.target.src;
            var cont = '';
            var last = afterSrc.lastIndexOf('_');
            var imgName = afterSrc.substring(0, last);
            var imgFormat = afterSrc.substring(last + 1, afterSrc.length).split('.')[1];
            var size = afterSrc.substring(last + 1, afterSrc.length).split('.')[0];
            var xSize = size.split('x')[0];
            var ySize = size.split('x')[1];
            if (xSize == ySize) {
                afterSrc = imgName + '.' + imgFormat;
            }
            cont = '<div class="enlargeDivTem"><img style="max-width: 850px;max-height: 1000px;padding:15px;" src="' + afterSrc + '"></div>';
            layer.open({
                type: 1,
                title: false,
                closeBtn: 1,
                area: ['850px', '600px'],
                scrollbar: false,
                content: cont
            })
        },
        reload: function () {
            var self = this;
            this.errors.clear();
            $.ajax({
                url: '../cp/facilitator/info',
                type: 'post',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        self.status = r.company.status;
                        if (self.status == 2) {
                            self.isAuthSuccess = true;
                        }
                        self.changeInfoBtn = '修改信息';
                        if (self.status == 0 || self.status == null) {
                            self.readonly = false;
                            self.notAuth = true;
                            self.changeInfoBtn = '新增信息';
                        }
                        self.company = r.company;
                        if(r.company!=null){
                        	self.memberType=r.company.memberType;
                        }
                        setTimeout(function () {
                            self.errors.clear();
                        }, 50)
                        if (r.company.provinceName) {
                            self.province.name = r.company.provinceName;
                            $.ajax({
                                url: '../basearea/citys',
                                type: 'post',
                                data: {
                                    'provinceId': r.company.provinceId
                                },
                                dataType: 'json',
                                contentType: "application/x-www-form-urlencoded",
                                success: function (r) {
                                    if (r.code == 0) {
                                        self.citys = r.citys;
                                    } else {

                                    }
                                }
                            });
                        }
                        if (r.company.cityName) {
                            self.city.name = r.company.cityName;
                            $.ajax({
                                url: '../basearea/areas',
                                type: 'post',
                                data: {
                                    'cityId': r.company.cityId
                                },
                                dataType: 'json',
                                contentType: "application/x-www-form-urlencoded",
                                success: function (r) {
                                    if (r.code == 0) {
                                        self.areas = r.areas;
                                    } else {

                                    }
                                }
                            });
                        }
                        if (r.company.areaName) {
                            self.area.name = r.company.areaName;
                        }
                        var imgId = 'enterpriseQualificationImgList';
                        self.getListLength(imgId);
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        time: function(e) {
            var o = e.target;
            if (vm.wait == 0) {
                o.removeAttribute('disabled');
                o.innerText = "获取验证码";
                vm.wait = 60;
            } else {
                o.setAttribute('disabled', true);
                o.innerText = '等待' + vm.wait + "秒后重新获取";
                vm.wait--;
                setTimeout(function() {
                        vm.time(e);
                    },
                    1000)
            }
        },
        getMsgCode: function(e){
            var regMobile = /^1[3|4|5|7|8][0-9]\d{8}$/;
            if(regMobile.test(vm.newMobile.mobile)){
                var data = {
                    mobile:vm.newMobile.mobile,
                    type: 5
                }
                $.ajax({
                    url: '../sys/user/getMsgCode',
                    type: 'post',
                    dataType: 'json',
                    data: data,
                    contentType: "application/x-www-form-urlencoded",
                    success: function (r) {
                        if (r.success) {
                            vm.time(e);
                            vm.newMobile.msgId = r.msg;
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }else{
                alert('用户手机号不符合规范!');
            }

        },
        changeMobile: function(){
            var index = layer.open({
                type: 1,
                title: "修改平台用户名",
                area: ['550px', '320px'],
                shadeClose: false,
                content: jQuery("#changeMobile"),
                btn: ['修改', '取消'],
                yes: function() {
                    var data = {
                        userName: vm.company.userName,
                        newMobile: vm.newMobile.mobile,
                        msgCodeId: vm.newMobile.msgId,
                        msgCode: vm.newMobile.msgCode,
                        pwd:vm.newMobile.pwd
                    };
                    $.ajax({
                        type: "POST",
                        url: "../sys/user/changeMobile",
                        dataType: "json",
                        contentType: "application/x-www-form-urlencoded",
                        data: data,
                        success: function(result) {
                            if (result.success) {
                                layer.close(index);
                                layer.alert('修改成功,请用新号码登录!', function() {
                                    top.location.href = $ctx + '/login.html';
                                });
                            } else {
                                layer.alert(result.msg);
                            }
                        }
                    });
                },
                btn2: function(){
                    vm.newMobile = {
                        mobile: '',
                        msgCode: '',
                        msgId: '',
                        pwd:''
                    }
                },
                cancle: function(){
                    vm.newMobile = {
                        mobile: '',
                        msgCode: '',
                        msgId: '',
                        pwd:''
                    }
                }


            })
        }
    },
    created: function () {
        if (localStorage.getItem('fromWallet')) {
            localStorage.removeItem('fromWallet');
            window.location.href = '../companybankcard/companybankcard.html';
        }
        this.reload();
        var self = this;
        $.ajax({
            url: '../basearea/provinces',
            type: 'post',
            dataType: 'json',
            success: function (r) {
                if (r.code == 0) {
                    self.provinces = r.provinces;
                }
            }
        });
    },
    mounted: function () {
    	this.memberType =sessionStorage.getItem("memberType");
    }
});