var vm = new Vue({
    el: '#rrapp',
    data: {
        readOnly: true,
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

        bank: {
            id: 0,
            name: '请选择银行'
        },
        bankCard: {
            bankId: '',
            bankName: '',
            bankBranchName: '',
            account: '',
            banks: [],
            companyName: '',
            provinceId: '',
            cityId: '',
            id: ''
        }

    },
    created: function() {
        var self = this;
        $.ajax({
            url: '../companybankcard/info',
            type: 'post',
            dataType: 'json',
            async: false,
            success: function(r) {
                if (r.code == 0) {
                    self.bankCard = r.companyBankCard;
                    if(r.companyBankCard.cityName){
                        self.city.name = r.companyBankCard.cityName;
                    }
                    if(r.companyBankCard.provinceName){
                        self.province.name = r.companyBankCard.provinceName;
                    }
                    if(r.companyBankCard.bankName){
                        self.bank.name = r.companyBankCard.bankName;
                    }
                } else {
                    alert(r.msg);
                }
            }
        });
        $.ajax({
            url: '../basearea/provinces',
            type: 'post',
            dataType: 'json',
            async: false,
            success: function(r) {
                if (r.code == 0) {
                    self.provinces = r.provinces;
                } else {

                }
            }
        });
    },
    methods: {
        getProvince: function(name, id) {
            if (id != 0) {
                vm.province.name = name;
                vm.province.id = id;
                vm.bankCard.provinceId = id;
                vm.city.name = '请选择城市';
                vm.bankCard.cityId = null;
                var self = this;
                $.ajax({
                    url: '../basearea/citys',
                    type: 'post',
                    data: { 'provinceId': id },
                    dataType: 'json',
                    contentType: "application/x-www-form-urlencoded",
                    async: false,
                    success: function(r) {
                        if (r.code == 0) {
                            self.citys = r.citys;
                        } else {

                        }
                    }
                });

            } else {

            }

        },
        getCity: function(name, id) {
            if (id != 0) {
                vm.city.name = name;
                vm.city.id = id;
                vm.bankCard.cityId = id;
            }
        },

        getBank: function(name, id) {
            if (id != 0) {
                vm.bank.name = name;
                vm.bank.d = id;
                vm.bankCard.bankName = name;
                vm.bankCard.bankId = id;
            }
        },

        toggleReadOnly: function() {
            vm.readOnly = vm.readOnly === false;
            if (vm.readOnly === false) {
                vm.changeInfoBtn = '取消修改';
            } else {
                vm.reload();
                vm.changeInfoBtn = '修改信息';
            }
        },
        saveOrUpdate: function() {
            var id = this.bankCard.id;
            var url = "";
            if (id == null || id == "") {
                url = "../companybankcard/save";
            } else {
                url = "../companybankcard/update";
            }
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.bankCard),
                dataType: "json",
                success: function(r) {
                    if (r.code === 0) {
                        alert('操作成功', function(index) {
                            vm.readOnly = vm.readOnly === false;
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        reload: function() {
            var self = this;
            $.ajax({
                url: '../companybankcard/info',
                type: 'post',
                dataType: 'json',
                async: false,
                success: function(r) {
                    if (r.code == 0) {
                        self.changeInfoBtn = '修改信息';
                        self.bankCard = r.companyBankCard;
                        self.city.name = r.companyBankCard.cityName;
                        self.province.name = r.companyBankCard.provinceName;
                        self.bank.name = r.companyBankCard.bankName;
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }
    }

})
