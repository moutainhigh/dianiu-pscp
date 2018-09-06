//引入Vue-select组件
Vue.component('v-select', VueSelect.VueSelect);
/*function getRealPath() {
    var localObj = window.location;
    var contextPath = localObj.pathname.split("/")[1];
    var basePath = localObj.protocol + "//" + localObj.host + "/" + contextPath;
    return basePath;
}
var realPath = getRealPath();
var messagesZh = require([realPath + '/statics/libs/en.js']);*/
/*Vue.use(VeeValidate);*/
Vue.use(VeeValidate);
//添加身份证验证规则
var isIdCard = {
    messages: {
        en: function(field, args) {
            return field + ' 不符合规范';
        }
    },
    validate: function(value, args) {
        var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
        return regIdCard.test(value);
    }
}
VeeValidate.Validator.extend('idcard', isIdCard);

//添加手机号码验证规则
var isMobile = {
    messages: {
        en: function(field, args) {
            return field + ' 不符合规范';
        }
    },
    validate: function(value, args) {
        var regMobile = /^1[3|4|5|7|8][0-9]\d{8}$/;
        return regMobile.test(value);
    }
}
VeeValidate.Validator.extend('mobile', isMobile);
//添加银行账户验证规则
var isBankCard = {
    messages: {
        en: function(field, args) {
            return field + ' 不符合规范';
        }
    },
    validate: function(value, args) {
        var regBankCard = /^(\d{16}|\d{19})$/;
        return regBankCard.test(value);
    }
}
VeeValidate.Validator.extend('bankCard', isBankCard);

//添加金额验证规则
var isMoney = {
    messages: {
        en: function(field, args) {
            return field + ' 不符合规范';
        }
    },
    validate: function(value, args) {
        var result = false;
        var regMoney = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
        if(!regMoney.test(value)){
          return false;
        }else if(value == 0){
          return false;
        }else{
          return true;
        }
    }
}
VeeValidate.Validator.extend('money',isMoney);

// 0~1两位小数正则(不包含0、1)
var isFactor = {
    messages: {
        en: function(field, args) {
            return field + ' 不符合规范';
        }
    },
    validate: function(value, args) {
        var regFactor = /\b0(\.\d{1,2})\b/;
        return regFactor.test(value);
    }
}
VeeValidate.Validator.extend('factor', isFactor);

//1~31号
var dayPeriod = {
    messages: {
        en: function(field, args) {
            return '请输入1~31之中的数字';
        }
    },
    validate: function(value, args) {
        return 31>=parseInt(value);
    }
}
VeeValidate.Validator.extend('dayperiod', dayPeriod);