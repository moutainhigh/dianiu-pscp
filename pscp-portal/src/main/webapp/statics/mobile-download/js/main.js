var vm = new Vue({
    el: '#app',
    data: {
        myRole: 'ele',
        myDevice: 'iPhone'
    },
    methods: {
        os: function() {
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
    created: function() {
        if ('addEventListener' in document) {
            FastClick.attach(document.body);
        }
        document.getElementById("sHtml").style.fontSize = window.innerWidth / 10 + "px";
        if (this.os().isPhone) {
            this.myDevice = 'iPhone'
        } else {
            this.myDevice = 'android'
        }
        var type = window.location.href.split('?')[1];
        switch (type) {
            case 'fromEle':
                this.myRole = 'ele';
                break;
            case 'fromSur':
                this.myRole = 'sur';
                break;
            case 'fromCus':
                this.myRole = 'cus';
                break;
        }
    }
})