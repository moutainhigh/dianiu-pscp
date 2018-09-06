//引入Vue-select组件
Vue.component('v-select', VueSelect.VueSelect);
//配置
var config = {
  errorBagName: 'errors', // change if property conflicts.
  fieldsBagName: 'fields', 
  delay: 0, 
  locale: 'en', 
  dictionary: null, 
  strict: true, 
  classes: false, 
  classNames: {
    touched: 'touched', // the control has been blurred
    untouched: 'untouched', // the control hasn't been blurred
    valid: 'valid', // model is valid
    invalid: 'invalid', // model is invalid
    pristine: 'pristine', // control has not been interacted with
    dirty: 'dirty' // control has been interacted with
  },
  events: 'blur|input',
  inject: true,
  validity: true,
  aria: true
};
Vue.use(VeeValidate,config);
//修改默认错误提示
var dictionary = {
  en: {
    messages: {
      email: function(){
        return '邮箱格式不正确哦'
      },
      required: function(field, args){
        return field + ' 不能为空哦！';
      },
      max: function(field, args){
        return field + ' 太长了哦！';
      }
    }
  }
};
VeeValidate.Validator.updateDictionary(dictionary);


//添加身份证验证规则
var isIdCard = {
    messages: {
        en: function(field, args) {
            return '身份证号码不正确哦!';
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
            return '手机号不正确哦!';
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

//添加正整数验证规则
var isInt = {
    messages: {
        en: function(field, args) {
            return field + ' 不符合规范';
        }
    },
    validate: function(value, args) {
        var regBankCard = /^[1-9]\d*$/;
        return regBankCard.test(value);
    }
}
VeeValidate.Validator.extend('myint', isInt);
