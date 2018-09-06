'use strict';

var vm = new Vue({
    el: '#app',
    data: {
        needsTopList: [],
        industryTopList: [],
        noticeTopList: []
    },
    methods: {
        os: function os() {
            var ua = navigator.userAgent,
                isQB = /(?:MQQBrowser|QQ)/.test(ua),
                isWindowsPhone = /(?:Windows Phone)/.test(ua),
                isSymbian = /(?:SymbianOS)/.test(ua) || isWindowsPhone,
                isAndroid = /(?:Android)/.test(ua),
                isFireFox = /(?:Firefox)/.test(ua),
                isChrome = /(?:Chrome|CriOS)/.test(ua),
                isIpad = /(?:iPad|PlayBook)/.test(ua),
                isTablet = /(?:iPad|PlayBook)/.test(ua) || isFireFox && /(?:Tablet)/.test(ua),
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
        },
        setViewport: function setViewport() {
            //DPR
            var dpr = 0,
                scale = 0;
            if (!dpr && !scale) {
                var isIPhone = window.navigator.appVersion.match(/iphone/gi);
                var devicePixelRatio = window.devicePixelRatio;
                var isRegularDpr = devicePixelRatio.toString().match(/^[1-9]\d*$/);
                if (isIPhone) {
                    if (devicePixelRatio >= 3 && (!dpr || dpr >= 3)) {
                        dpr = 3;
                    } else if (devicePixelRatio >= 2 && (!dpr || dpr >= 2)) {
                        dpr = 2;
                    } else {
                        dpr = 1;
                    }
                } else {
                    dpr = 1;
                }
            }
            // document.getElementById("vMeta").setAttribute('content', 'initial-scale=' + 1 / dpr + ', maximum-scale=' + 1 / dpr + ', minimum-scale=' + 1 / dpr + ', user-scalable=no');
            document.getElementById("sHtml").setAttribute('data-dpr', dpr);
        }
    },
    created: function created() {
        var _this = this;

        //PC端打开的话跳转PC页面
        if (this.os().isPc) {
            window.location.href = 'index.html';
            return;
        }
        var self = this;
        axios.post('wap/index').then(function (r) {
            if (r.data.code == 0) {
                self.needsTopList = JSON.parse(r.data.result).needsTopList;
                self.industryTopList = JSON.parse(r.data.result).industryTopList;
                self.noticeTopList = JSON.parse(r.data.result).noticeTopList;
            }
        });
        // $.post({
        //     url: 'wap/index',
        //     dataType: 'json',
        //     success: function(r){
        //         if (r.code == 0) {
        //             self.needsTopList = JSON.parse(r.result).needsTopList;
        //             self.industryTopList = JSON.parse(r.result).industryTopList;
        //             self.noticeTopList = JSON.parse(r.result).noticeTopList;
        //         }
        //     }
        // })
        if ('addEventListener' in document) {
            if (FastClick){
                FastClick.attach(document.body);
            }
            
        }
        this.setViewport();
        document.getElementById("sHtml").style.fontSize = window.innerWidth / 10 + "px";
        document.addEventListener('touchend', function (e) {
            if (!_this.$el.contains(e.target)) _this.showNavbar = false;
        });
        //console.log(this.os().isPc);
    }
});