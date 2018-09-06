<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <title>电力服务云平台</title>
    <link rel="icon" href="statics/img/favicon.ico">
    <link rel="stylesheet" href="statics/css/bootstrap.min.css">
    <link rel="stylesheet" href="statics/css/font-awesome.min.css">
    <link rel="stylesheet" href="statics/css/AdminLTE.min.css">
    <link rel="stylesheet" href="statics/css/all-skins.min.css">
    <link rel="stylesheet" href="statics/css/swiper-4.2.0.min.css">
    <link rel="stylesheet" href="statics/css/main.css">
    <style>
        html {
            overflow-x: auto;
            min-width: 1250px;
        }

        html,
        body,
        .main-container {
            width: 100%;
        }
    </style>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <!--[if lte IE 8]>
    <script>window.location.href = 'http://cdn.dmeng.net/upgrade-your-browser.html?referrer=' + location.href;</script>
    <![endif]-->
    <!--[if lte IE 7]>
    <script>window.location.href = 'http://cdn.dmeng.net/upgrade-your-browser.html?referrer=' + location.href;</script>
    <![endif]-->
    <!--[if lte IE 6]>
    <script>window.location.href = 'http://cdn.dmeng.net/upgrade-your-browser.html?referrer=' + location.href;</script>
    <![endif]-->
</head>

<body class="hold-transition">
<div id="rrapp" class="main-container" v-cloak>
    <div class="main-cont">
        <div class="main-login">
            <div class="main-login-success">
                <a href="index.html" style="float:left;">
                    <img src="statics/img/logo-white.png"
                         style="max-width: 150px;vertical-align: middle;margin-left: 10%;">
                </a>
                <div class="top-swiper">
                    <div class="swiper-left">
                        <i></i>
                    </div>
                    <div class="swiper-right">
                        <!--top - swiper-->
                        <div class="swiper-container swiper-matter">
                            <div class="swiper-wrapper matter-box">
                                <div class="swiper-slide" style="line-height:60px;" v-for="(val,index) in orderInfo">
                                    <div>
                                        &nbsp;&nbsp;<span>{{val.memberType}}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>{{val.memberName}}</span>&nbsp;&nbsp;&nbsp;&nbsp;<span>{{val.title}}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div v-show="user.mobile == null" style="float: right;margin-top:9px;">
                    <a class="btn main-login-btn" @click="toggleLogin" v-if="showLoginBtn">登录</a>
                    <a class="btn btn-primary" href="toRegister.html"
                       style="background-color: #fff;color:#333;border:1px solid #fff;">注册</a>
                </div>
                <div v-show="user.mobile != null" class="login-info" style="float: right;margin-top:15px;">
                    <span @click="toCms" title="前往控制台">控制台</span>&nbsp;&nbsp;&nbsp;&nbsp; <span
                        @mouseover="showLoginMenu = true" @mouseout="showLoginMenu = false">{{ user.mobile }}</span>
                </div>
                <div v-show="showLoginMenu"
                     style="position: absolute;right: 9%;top:32px;background: rgba(75,88,125,.4);width: 105px;padding-top: 5px;"
                     @mouseover="showLoginMenu = true" @mouseout="showLoginMenu = false">
                    <ul class="login-list-btn">
                        <li @click="updatePassword">修改密码</li>
                        <li><a href="logout">退出</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="main-app-QRcode">
            <table style="text-align: center;">
                <tr>
                    <td><img src="statics/img/ele-qrcode.png" style="max-width: 175px"/></td>
                    <td><img src="statics/img/sur-qrcode.png" style="max-width: 175px"/></td>
                    <td><img src="statics/img/cus-qrcode.png" style="max-width: 175px;"></td>
                </tr>
                <tr>
                    <td>
                        <button class="button is-primary main-btn" @click="jumpToEle">
                            <img class="main-btn-icon" src="statics/img/toggle-icon-ele.png" width="35" height="23"/>
                            我是电工
                            <br/> {{ imElectriBtn }}
                        </button>
                    </td>
                    <td>
                        <button class="button is-primary main-btn" @click="jumpToSer">
                            <img class="main-btn-icon" src="statics/img/toggle-icon-sur.png" width="35" height="23"/>
                            我是服务商
                            <br/>{{ imServerBtn }}
                        </button>
                    </td>
                    <td>
                        <button class="button is-primary main-btn" @click="jumpToEle">
                            <img class="main-btn-icon" src="statics/img/toggle-icon-cus.png" width="35" height="23"/>
                            我是客户
                        </button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div class="main-toggle-type">
        <div class="toggle-cont move">
            <div class="toggle-part" @mouseover="showToggleEle = true" @mouseout="showToggleEle = false">
                <a href="toRegister.html" style="color:#fff;">
                    <div class="toggle-part-in">
                        <div class="toggle-title-part" :class="{'move':showToggleEle}">
                            <img src="statics/img/toggle-icon-ele.png">
                            <br/>
                            <span class="toggle-title">我是电工</span>
                        </div>
                        <div class="toggle-title-detail" :class="{'setOpacity':showToggleEle}">
                            <span>精准推送需求</span>
                            <br/>
                            <span>社电培训认证</span>
                            <br/>
                            <span>在线结算无风险</span>
                            <br/>
                            <button type="button" class="toggle-part-btn">免费入驻</button>
                        </div>
                    </div>
                </a>
            </div>
            <div class="toggle-part" @mouseover="showToggleSur = true" @mouseout="showToggleSur = false">
                <a href="toRegister.html" style="color:#fff;">
                    <div class="toggle-part-in">
                        <div class="toggle-title-part" :class="{'move':showToggleSur}">
                            <img src="statics/img/toggle-icon-sur.png">
                            <br/>
                            <span class="toggle-title">我是服务商</span>
                        </div>
                        <div class="toggle-title-detail" :class="{'setOpacity':showToggleSur}">
                            <span>线上派单招募</span>
                            <br/>
                            <span>合理招募社电</span>
                            <br/>
                            <span>实时监控警告</span>
                            <br/>
                            <button type="button" class="toggle-part-btn">免费入驻</button>
                        </div>
                    </div>
                </a>
            </div>
            <div class="toggle-part" @mouseover="showToggleCus = true" @mouseout="showToggleCus = false">
                <a href="toRegister.html" style="color:#fff;">
                    <div class="toggle-part-in">
                        <div class="toggle-title-part" :class="{'move':showToggleCus}">
                            <img src="statics/img/toggle-icon-cus.png">
                            <br/>
                            <span class="toggle-title">我是客户</span>
                        </div>
                        <div class="toggle-title-detail" :class="{'setOpacity':showToggleCus}">
                            <span>APP实时监控</span>
                            <br/>
                            <span>工单进度一手掌控</span>
                            <br/>
                            <button type="button" class="toggle-part-btn">免费入驻</button>
                        </div>
                    </div>
                </a>
            </div>
        </div>
    </div>
    <#if needsTopList?exists>
        <div class="main-requirement">
            <h1>需求市场<a href="news/index.html?requirement" target="_blank" class="main-requirement-href">查看更多>></a></h1>
            <div class="main-requirement-content">
                <#list needsTopList as needs>
                    <div class="main-requirement-part">
                        <table>
                            <tbody>
                            <tr>
                                <td><span class="main-requirement-title">${(needs.name)!}</span></td>
                                <td>发布日期&nbsp;&nbsp;${(needs.publishTime)!}</td>
                                <td rowspan="2" class="main-requirement-line" v-if="'${(needs.respondStatus)!}' == 0"
                                    style="min-width: 80px;">
                                    <span class="main-requirement-responsebtn"
                                          @click="toNeedsDetail('${(needs.orderId)!}')">响应</span>
                                </td>
                                <td rowspan="2" class="main-requirement-line responsed"
                                    v-else-if="'${(needs.respondStatus)!}' == 1" style="min-width: 80px;">
                                    <span class="main-requirement-responsebtn responsed"
                                          @click="toNeedsDetail('${(needs.orderId)!}')">已响应</span>
                                </td>
                            </tr>
                            <tr>
                                <td>响应人数&nbsp;&nbsp;&nbsp;&nbsp;
                                    <span class="main-requirement-count">${(needs.responseNumber)!}</span>
                                </td>
                                <td>截止日期&nbsp;&nbsp;${(needs.publishCutoffTime)!}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </#list>
            </div>
        </div>
    </#if>
    <div class="main-ad-info">
        <h1>
            <span>行业资讯</span>
            <a id="getMoreInfo" href="news/index.html?news" target="_blank">查看更多>></a>
        </h1>
        <table class="main-ad-cont">
            <#if industryTopList?exists>
                <tr>
                    <#list industryTopList as news>
                        <td class="main-ad-part">
                            <a href="news/${(news.id)?c }/details?news" target="_blank">
                                <#if news.coverPic?exists>
                                    <img src="${(news.coverPic)! }" class="main-ad-img">
                                    <#else>
                                        <img src="statics/img/ad-test-1.png" class="main-ad-img">
                                </#if>
                                <p class="main-ad-part-text">
                                    ${(news.description)! }
                                </p>
                                <span class="main-ad-part-date"> ${(news.publishTime)! }</span>
                            </a>
                        </td>
                    </#list>
                </tr>
            </#if>
        </table>
    </div>
    <div class="main-announcement-info">
        <table style="width: 100%;">
            <tr>
                <td class="main-announcement-part part1"></td>
                <td class="main-announcement-part part2">
                    <h1 style="width: 500px;">
                        <span>公告消息</span>
                        <a id="getMoreAnnc" href="news/index.html?annc" target="_blank">查看更多>></a>
                    </h1>
                    <table style="border-collapse:separate; border-spacing:0px 10px;text-align: left;">
                        <#if noticeTopList?exists>
                            <#list noticeTopList as news>
                                <tr>
                                    <td>
                                        <a href="news/${(news.id)?c }/details?annc" target="_blank"
                                           style="color: #333;">${(news.title)! }</a>
                                    </td>
                                    <td><a href="news/${(news.id)?c }/details?annc" target="_blank"
                                           style="color: #333;">${(news.publishTime)! }</a></td>
                                </tr>
                            </#list>
                        </#if>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div class="main-aboutus-info">
        <h1>关于我们</h1>
        <p>
            杭州电牛科技有限公司总部位于杭州，是电力服务云平台理念的先驱者。公司旗下的产品“电牛运维”是基于地理位置的电工现场管理工具和电力服务需求对接平台。我们为电力服务单位提供专业的电工调度、现场作业指导、安全管控等服务。平台整合了不同细分工种的电工资源，以就近原则，为电网公司、工商专变用户、电力设备制造商、物业公司、充电桩业主、光伏电站等需求方，提供快速、高效、经济、安全的电工服务。公司技术团队由全国知名的两院院士专家领衔，科技研发团队实力超强，经验丰富。公司计划尽快完成高新技术企业的申报和审核，争取在科技转化为生产力的研发实践以及各级各类科技项目申报中获得国家级、省级、市级、以及高新区政府的大力支持与帮助。
        </p>
    </div>
    <footer class="main-footer">
        <span>电牛科技版权所有 2004-2017 ICP证： 浙B2-20150087</span>
    </footer>
    <div class="login-div" v-if="showLogin">
        <div class="login-box" v-cloak>
            <!--<div class="login-logo">
                <b><i class="fa fa-window-close"></i></b>
                </div>-->
            <!-- /.login-logo -->
            <div class="login-box-body" style="position: relative;">
                <p class="login-box-msg"><i @click="toggleLogin" class="fa fa-times login-close-btn"></i>会员登录</p>
                <div v-if="error" class="alert alert-danger alert-dismissible">
                    <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-triangle"></i> {{errorMsg}}</h4>
                </div>
                <div class="form-group has-feedback">
                    <input type="text" class="form-control" v-model="username" placeholder="公司名称/手机号码">
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control" v-model="password" placeholder="密码">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="text" class="form-control" v-model="captcha" @keyup.enter="login" placeholder="验证码"
                           style="width: 110px;display: inline-block;">
                    <img alt="如果看不清楚，请单击图片刷新！" class="pointer" :src="src" @click="refreshCode"
                         style="max-width: 120px;height: 34px;margin-bottom: 2px;">
                </div>
                <div class="row">
                    <!-- /.col -->
                    <div class="col-xs-6">
                    </div>
                    <div class="col-xs-6" style="text-align: right;padding-right: 1em;">
                        <span class="main-forget" @click="toggleForget();toggleLogin();">忘记密码?</span>
                    </div>
                    <div class="col-xs-2">
                    </div>
                    <div class="col-xs-5">
                        <a class="btn btn-default btn-block btn-flat" href="toRegister.html">注册</a>
                    </div>
                    <div class="col-xs-5">
                        <button type="button" class="btn btn-primary btn-block btn-flat" @click="login">登录</button>
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.social-auth-links -->
            </div>
            <!-- /.login-box-body -->
        </div>
    </div>
    <!--忘记密码-->
    <div class="forget-div" v-if="showForget">
        <div class="forget-box" v-cloak>
            <div style="position: relative;" class="forget-box-body">
                <p class="forget-box-msg"><i class="fa fa-times login-close-btn" @click="toggleForget"></i>忘记密码</p>
                <div class="alert alert-danger alert-dismissible" v-if="forgetPass.error">
                    <h4 style="margin-bottom: 0px;"><i class="fa fa-exclamation-triangle"></i> {{forgetPass.errorMsg}}
                    </h4>
                </div>
                <div class="form-group has-feedback">
                    <input type="text" class="form-control" placeholder="请输入您的手机号" v-model="forgetPass.userName">
                    <span class="glyphicon glyphicon-user form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="text" class="form-control" placeholder="验证码" style="width: 50%;display: inline-block;"
                           v-model="forgetPass.msgCode">
                    <button type="button" class="btn btn-primary btn-flat" @click="sendForgetMsg">发送验证码</button>
                    <span class="glyphicon glyphicon-warning-sign form-control-feedback" style="right: 50%;"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control" placeholder="请输入至少6位密码" v-model="forgetPass.password">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="form-group has-feedback">
                    <input type="password" class="form-control" placeholder="确认密码" v-model="forgetPass.passwordConfirm">
                    <span class="glyphicon glyphicon-lock form-control-feedback"></span>
                </div>
                <div class="row">
                    <div class="col-xs-12">
                        <button type="button" class="btn btn-primary btn-block btn-flat" @click="resetPass">重置密码
                        </button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 修改密码 -->
    <div id="passwordLayer" style="display: none;">
        <form class="form-horizontal">
            <div class="form-group">
                <div class="form-group">
                    <div class="col-sm-2 control-label">账号</div>
                    <span class="label label-success" style="vertical-align: bottom;display: initial;">{{user.loginName}}/{{user.mobile}}</span>
                </div>
                <div class="form-group">
                    <div class="col-sm-2 control-label">原密码</div>
                    <div class="col-sm-10">
                        <input type="password" class="form-control" v-model="passwd" placeholder="原密码"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2 control-label">新密码</div>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" v-model="newPasswd" placeholder="新密码"/>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<div id="mainOtherTel" class="main-other">
    <div class="main-other-part">
        <img src="statics/img/main-icon-tel.png"/>
        <span class="main-other-text">专家热线/客服电话</span>
    </div>
</div>
<div id="mainOtherHelp" class="main-other">
    <div class="main-other-part">
        <img src="statics/img/main-icon-help.png"/>
        <span class="main-other-text">帮助中心</span>
    </div>
</div>
<!-- <div id="mainOtherAbout" class="main-other">
<div class="main-other-part">
  <img src="statics/img/main-icon-about.png" />
  <span class="main-other-text">关于我们</span>
</div>
</div> -->
<div id="mainOtherTelWhite" class="main-other white">
    <div class="main-other-part">
        <span>专家热线/客服电话</span>
    </div>
</div>
<div id="mainOtherHelpWhite" class="main-other white">
    <div class="main-other-part">
        <span>帮助中心</span>
    </div>
</div>
<!-- /.login-box -->
<script src="statics/libs/jquery.min.js"></script>
<script src="statics/plugins/layer/layer.js"></script>
<script src="statics/libs/vue.min.js"></script>
<script src="statics/libs/bootstrap.min.js"></script>
<script src="statics/libs/swiper-4.2.0.min.js"></script>
<!-- <script src="statics/libs/jquery.slimscroll.min.js"></script> -->
<!-- <script src="statics/libs/fastclick.min.js"></script> -->
<script src="statics/libs/app.js"></script>
<script type="text/javascript">
    'use strict';
    var mytabs = ['<div class="main-tab">',
        '   <div class="tabs">',
        '   <figure  style="display: inline-block;width: 250px;text-align:center;">',
        '     <img src="statics/img/logo.png" style="max-width: 150px;">',
        '   </figure>',
        '     <ul style="border: none;">',
        '       <li v-for="tab in tabs" :class="{\'is-active\': tab.isActive}" @click="tab.toggleTap">',
        '           <a @click="selectedTab(tab)" :id="tab.id">{{ tab.name }}</a>',
        '       </li>',
        '     </ul>',
        '   </div>',
        '   <div class="tab-detail">',
        '       <slot></slot>',
        '   </div>',
        '</div>'
    ].join("");

    var mytab = ['<div v-show="isActive">',
        '   <slot></slot>',
        '</div>'
    ].join("");
    //设置
    Vue.component('tabs', {
        template: mytabs,
        data: function data() {
            return {
                tabs: []
            };
        },
        created: function created() {
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
            name: {
                required: true
            },
            selected: {
                default: false
            },
            id: {
                required: true
            }
        },
        template: mytab,
        data: function () {
            return {
                isActive: false,
                /*href: '#' + this.name.replace(/ /g, '-').toLowerCase()*/
            };
        },
        methods: {
            toggleTap: function () {
                if (this.name === '我是服务商') {
                    vm.showLoginBtn = false;
                    vm.reload();

                } else {
                    vm.showLoginBtn = true
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
            isLogin: false,
            user: {
                mobile: null,
                loginName: null
            },
            showLoginBtn: true,
            showLoginMenu: false,
            showToggleEle: false,
            showToggleSur: false,
            showToggleCus: false,
            passwd: '',
            newPasswd: '',
            wait: 60,
            username: '',
            password: '',
            anchor: '',
            captcha: '',
            error: false,
            errorMsg: '',
            src: 'captcha.jpg',
            showLogin: false,
            showForget: false,
            QRcodeServer: true,
            QRcodeElectri: true,
            imServerBtn: '',
            imElectriBtn: '',
            forgetPass: {
                error: false,
                errorMsg: '',
                userName: '',
                password: '',
                passwordConfirm: '',
                msgId: '',
                msgCode: ''
            },
            orderInfo: []
        },
        beforeCreate: function () {
            if (self != top) {
                top.location.href = self.location.href;
            }
        },
        methods: {
            getOrderInfo: function () {
                var url = "index/getdmmsgs";
                var _this = this;
                $.ajax({
                    url,
                    type: "GET",
                    success: function (res) {
                        var list = JSON.parse(res);
                        var result = list.messages;
                        for (let i = 0; i < result.length; i++) {
                            _this.orderInfo.push(result[i])
                        }
                    }
                });
                setInterval(function () {
                    _this.orderInfo.splice(0, _this.orderInfo.length);
                    $.ajax({
                        url,
                        type: "GET",
                        success: function (res) {
                            var list = JSON.parse(res)
                            var result = list.messages
                            for (let i = 0; i < result.length; i++) {
                                _this.orderInfo.push(result[i])
                            }
                        }
                    })
                }, 30000)
            },
            refreshCode: function () {
                this.src = "captcha.jpg?t=" + $.now();
            },
            showImg: function () {
                this.QRcodeServer = false;
                this.imServerBtn = '10000名';
            },
            hideImg: function () {
                this.QRcodeServer = true;
                this.imServerBtn = '';
            },
            showImgElec: function () {
                this.QRcodeElectri = false;
                this.imElectriBtn = '100人';
            },
            hideImgElec: function () {
                this.QRcodeElectri = true;
                this.imElectriBtn = '';
            },
            login: function (event) {
                var anchor = window.location.hash;
                var data = "username=" + vm.username + "&password=" + vm.password + "&anchor=" + anchor + "&captcha=" + vm.captcha;
                $.ajax({
                    type: "POST",
                    url: "sys/login",
                    data: data,
                    dataType: "json",
                    success: function (result) {
                        if (result.code == 0) { //登录成功
                        //  sessionStorage.removeItem('companyId');
                        //  sessionStorage.setItem('companyId',companyId);
                            parent.location.href = result.redirectUrl;
                        } else {
                            vm.error = true;
                            vm.errorMsg = result.msg;
                            vm.refreshCode();
                        }
                    }
                });
            },
            updatePassword: function () {
                layer.open({
                    type: 1,
                    /* skin: 'layui-layer-molv',*/
                    title: "修改密码",
                    area: ['550px', '270px'],
                    shadeClose: false,
                    content: jQuery("#passwordLayer"),
                    btn: ['修改', '取消'],
                    btn1: function (index) {
                        var data = "passwd=" + vm.passwd + "&newPasswd=" + vm.newPasswd;
                        $.ajax({
                            type: "POST",
                            url: "sys/user/passwd",
                            data: data,
                            dataType: "json",
                            contentType: "application/x-www-form-urlencoded",
                            success: function (result) {
                                if (result.code == 0) {
                                    layer.close(index);
                                    layer.alert('修改成功', function (index) {
                                        location.reload();
                                    });
                                } else {
                                    layer.alert(result.msg);
                                }
                            }
                        });
                    }
                });
            },
            sendForgetMsg: function (e) {
                var getMobile = vm.forgetPass.userName;
                if (!(/^1[3|4|5|7|8]\d{9}$/.test(getMobile))) {
                    alert('手机号码有误！');
                } else {
                    var data = {
                        mobile: getMobile,
                        type: 1
                    };
                    $.ajax({
                        type: "Post",
                        url: "sys/user/getMsgCode",
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            if (result.success) {
                                vm.time(e);
                                vm.forgetPass.msgId = result.msg;
                            } else {
                                alert(result.msg);
                            }
                        }
                    });
                }
            },
            resetPass: function () {
                if (vm.forgetPass.passwordConfirm != vm.forgetPass.password) {
                    alert('确认密码与密码不一致');
                    return false;
                } else {
                    var data = {
                        userName: vm.forgetPass.userName,
                        password: vm.forgetPass.password,
                        msgCodeId: vm.forgetPass.msgId,
                        msgCode: vm.forgetPass.msgCode
                    }
                    $.ajax({
                        type: "POST",
                        url: "sys/user/resetPwd",
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            if (result.success) { //注册成功
                                parent.location.href = 'index.html';
                            } else {
                                vm.forgetPass.error = true;
                                vm.forgetPass.errorMsg = result.msg;
                            }
                        }
                    })
                }
            },
            toggleLogin: function () {
                vm.reload();
                this.showLogin = this.showLogin === false;
            },
            toggleForget: function () {
                vm.reload();
                this.showForget = this.showForget === false;
            },
            time: function (e) {
                var o = e.target;
                if (vm.wait == 0) {
                    o.removeAttribute('disabled');
                    o.innerText = "获取验证码";
                    vm.wait = 60;
                } else {
                    o.setAttribute('disabled', true);
                    o.innerText = '请等待' + vm.wait + "秒..";
                    vm.wait--;
                    setTimeout(function () {
                            vm.time(e);
                        },
                        1000)
                }
            },
            jumpToSer: function () {
                $('#imSer').trigger('click');
            },
            jumpToEle: function () {
                $('#imEle').trigger('click');
            },
            reload: function () {
                vm.username = '';
                vm.password = '';
                vm.captcha = '';
                vm.error = null;
                vm.errorMsg = '';
                vm.forgetPass.userName = '';
                vm.forgetPass.password = '';
                vm.forgetPass.passwordConfirm = '';
                vm.forgetPass.msgId = '';
                vm.forgetPass.msgCode = '';
                vm.forgetPass.error = null;
                vm.forgetPass.errorMsg = '';
            },
            getUser: function () {
                var self = this;
                $.getJSON("sys/user/info?_" + $.now(), function (r) {
                    console.log(r);
                    if (r.code == 0 && r.user) {
                        self.user = r.user;
                    } else {
                        return;
                    }

                });
            },
            toCms: function () {
                window.location.href = 'main.html';
            },
            toNeedsDetail: function (orderId) {
                sessionStorage.setItem('imneeds', orderId);
                window.top.location.href = 'main.html#cp/requirement.html';
            },
            //获取判断信息
            os: function () {
                var ua = navigator.userAgent,
                    isQB = /(?:MQQBrowser|QQ)/.test(ua),
                    isWindowsPhone = /(?:Windows Phone)/.test(ua),
                    isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,
                    isAndroid = /(?:Android)/.test(ua),
                    isFireFox = /(?:Firefox)/.test(ua),
                    isChrome = /(?:Chrome|CriOS)/.test(ua),
                    isIpad = /(?:iPad|PlayBook)/.test(ua),
                    isTablet = /(?:iPad|PlayBook)/.test(ua) || (isFireFox && /(?:Tablet)/.test(ua)),
                    isSafari = /(?:Safari)/.test(ua),
                    isPhone = /(?:iPhone)/.test(ua) && !isTablet,
                    isOpen = /(?:Opera Mini)/.test(ua),
                    isUC = /(?:UCWEB|UCBrowser)/.test(ua),
                    isPc = !isPhone && !isAndroid && !isSymbian;
                return {
                    isQB: isQB,
                    isTablet: isTablet,
                    isPhone: isPhone,
                    isAndroid: isAndroid,
                    isPc: isPc,
                    isOpen: isOpen,
                    isUC: isUC,
                    isIpad: isIpad
                };
            }
        },
        //判断终端
        created: function () {
            if (this.os().isPc) {
                this.getUser();
            } else {
                window.location.href = 'm_index.html';
            }
        },
        mounted: function () {
            this.getOrderInfo()
        }
    });


    // Create a safe reference to the Underscore object for use below.
    var _ = function (obj) {
        if (obj instanceof _) return obj;
        if (!(this instanceof _)) return new _(obj);
        this._wrapped = obj;
    };
    // A (possibly faster) way to get the current timestamp as an integer.
    _.now = Date.now || function () {
        return new Date().getTime();
    };
    _.throttle = function (func, wait, options) {
        var context, args, result;
        var timeout = null;
        var previous = 0;
        if (!options) options = {};
        var later = function () {
            previous = options.leading === false ? 0 : _.now();
            timeout = null;
            result = func.apply(context, args);
            if (!timeout) context = args = null;
        };
        return function () {
            var now = _.now();
            if (!previous && options.leading === false) previous = now;
            var remaining = wait - (now - previous);
            context = this;
            args = arguments;
            if (remaining <= 0 || remaining > wait) {
                if (timeout) {
                    clearTimeout(timeout);
                    timeout = null;
                }
                previous = now;
                result = func.apply(context, args);
                if (!timeout) context = args = null;
            } else if (!timeout && options.trailing !== false) {
                timeout = setTimeout(later, remaining);
            }
            return result;
        };
    };

    function setTop() {
        var winHeight = $(window).height();
        var eleHeight = winHeight / 2 - 25;
        var winWidth = $(window).width();
        var toggleContWidth = $('.toggle-cont').width();
        var left;
        if (winWidth > 1250) {
            left = (winWidth - toggleContWidth) / 2;
        } else {
            left = (1250 - toggleContWidth) / 2;
        }
        var EleInfoWidth = $('.main-ad-cont').width();
        $('#getMoreInfo').css('right', (winWidth - EleInfoWidth) / 2 + 30 + 'px');
        $('#getMoreAnnc').css('right', winWidth / 2 - 550 + 'px');
        $('#mainOtherHelp,#mainOtherHelpWhite').css('top', eleHeight + 'px');
        $('#mainOtherTel,#mainOtherTelWhite').css('top', eleHeight - 50 + 'px');
        $('#mainOtherAbout').css('top', eleHeight + 50 + 'px');
        $('.toggle-cont').css('left', left + 'px');
        $('.main-cont').css('height', winHeight + 'px');
    }

    function addOtherText() {
        $(this).find('.main-other-text').css('display', 'inline-block');
    }

    function removeOtherTest() {
        $(this).find('.main-other-text').css('display', 'none');
    }

    var throttledTop = _.throttle(setTop, 200);
    $(window).resize(throttledTop);
    $('.main-other').on('mouseover', addOtherText);
    $('.main-other').on('mouseout', removeOtherTest);
    var offsetTopRecord = 0;
    var offsetTop = function () {
        var winHeight = $(window).height();
        var docScroll = $(window).scrollTop();
        if (docScroll >= winHeight / 2) {
            /* $('.toggle-cont').removeClass('move animated slideInDown slideInUp').addClass('animated slideInDown');*/
            $('.toggle-cont').removeClass('move');
        } else {
            /*$('.toggle-cont').removeClass('animated slideInDown slideInUp').addClass('move animated slideInUp');*/
            $('.toggle-cont').addClass('move');
        }
        if (docScroll >= winHeight / 2) {
            $('#mainOtherHelp,#mainOtherTel').css('display', 'block');
            $('#mainOtherHelpWhite,#mainOtherTelWhite').css('display', 'none');
        } else {
            $('#mainOtherHelp,#mainOtherTel').css('display', 'none');
            $('#mainOtherHelpWhite,#mainOtherTelWhite').css('display', 'block');
        }
    }
    var throttledOffset = _.throttle(offsetTop, 100);
    $(window).scroll(throttledOffset);
    window.onresize = function () {
        setTop();
        offsetTop();
    }
    $(document).ready(function () {
        setTop();
        offsetTop();
    });
</script>
</body>
<script>
    var swiper = new Swiper('.swiper-container', {
        direction: 'vertical',
        loop: true,
        autoplay: true,
        autoplayDisableOnInteraction: false,
        pagination: {
            el: '.swiper-pagination',
        },
        observer: true, //修改swiper自己或子元素时，自动初始化swiper
        observeParents: false, //修改swiper的父元素时，自动初始化swiper
        onSlideChangeEnd: function (swiper) {
            swiper.update();
            mySwiper.startAutoplay();
            mySwiper.reLoop();
        }
    });
</script>

</html>
