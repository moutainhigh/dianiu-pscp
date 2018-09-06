//生成菜单
var menuItem = Vue.extend({
    name: 'menu-item',
    props: {
        item: {}
    },
    template: [
        '<li>',
        '<a v-if="item.type === 0" href="javascript:;" class="directory">',
        '<i class="fa fa-angle-left pull-right"></i>',
        '<i v-if="item.icon != null" :class="item.icon"></i>',
        '<span>{{item.name}}</span>',
        '</a>',
        '<ul v-if="item.type === 0" class="treeview-menu">',
        '<menu-item :item="item" v-show="item.name!=\'客户告警\'" v-for="(item,index) in item.list" :key="index"></menu-item>',
        '</ul>',
        '<a v-show="item.name!=\'智能监控\'" v-if="item.type === 1" :href="\'#\'+item.url" @click="refreshMyself" :class="{\'smart\':item.name==\'客户列表\' }"><i v-if="item.icon != null" :class="item.icon"></i><i v-else class="fa fa-circle-o"></i> {{item.name}}</a>',
        '</li>'
    ].join(''),
    methods: {
        //点击当前tab刷新
        refreshMyself: function (e) {
            if (location.href == e.target.href) {
                var url = e.target.hash;
                document.getElementById('setIframe').contentWindow.location.replace(url.replace('#', ''));
            } else {
                return;
            }
        }
    }
});

//iframe自适应
$(window).on('resize', function () {
    var $content = $('.content');
    $content.height($(this).height() - 120);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();

//注册菜单组件
Vue.component('menuItem', menuItem);

var vm = new Vue({
    el: '#rrapp',
    data: {
        showCustomMenu: false,
        user: {},
        menuList: {},
        main: "sys/main.html",
        passwd: '',
        newPasswd: '',
        navTitle: "控制台",
        havingUnread: false,
        powerFeeMenu: false,
        enlargeChildPage: true,
        toggleTitle: true
    },
    methods: {
        getMenuList: function (event) {
            /* $.getJSON("sys/menu/user?_" + $.now(), function(r) {
                      vm.menuList = r.menuList;
                  }); */
            $.ajax({
                type: 'get',
                url: "sys/menu/user?_" + $.now(),
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        vm.menuList = r.menuList;
                    } else if (r.code == 500) {
                        alert('登录超时,请重新登录!', function () {
                            location.href = "login.html"
                        })
                    }
                }
            })
        },
        getUser: function () {
            $.getJSON("sys/user/info?_" + $.now(), function (r) {
                vm.user = r.user;
                var companyId = vm.user.companyId;
                sessionStorage.setItem('companyId',companyId);
                var isFacilitator = vm.user.isFacilitator; 
                sessionStorage.setItem('isFacilitator',isFacilitator);
            });
        },
        updatePassword: function () {
            var self = this;
            layer.open({
                type: 1,
                title: "修改密码",
                area: [
                    '550px', '270px'
                ],
                shadeClose: false,
                content: jQuery("#passwordLayer"),
                btn: [
                    '修改', '取消'
                ],
                yes: function (index) {
                    var data = "passwd=" + self.$refs.passwd.value + "&newPasswd=" + self.$refs.newPasswd.value;
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
                        },
                        complete: function () {
                            self.$refs.passwd.value = '';
                            self.$refs.newPasswd.value = '';
                        }
                    });
                },
                btn2: function () {
                    self.$refs.passwd.value = '';
                    self.$refs.newPasswd.value = '';
                },
                cancel: function () {
                    self.$refs.passwd.value = '';
                    self.$refs.newPasswd.value = '';
                }
            });
        },
        toMessagePage: function () {
            var self = this;
            $.ajax({
                url: 'message/unread',
                type: 'post',
                dataType: 'json',
                success: function (r) {
                    if (r.code == 0) {
                        self.havingUnread = Boolean(r.page.list.length);
                        var url = window.top.location.href.split('#')[0] + '#cp/message.html';
                        window.top.location.href = url;
                    } else {
                        console(r.msg);
                    }
                }
            });

        },
        toAlarm: function () {
        	var baseUrl = window.top.location.href.split('#')[0];
        	var isFacilitator = sessionStorage.getItem('isFacilitator');
        	if(isFacilitator != 1) {
        		sessionStorage.setItem('customer#warning#flag', 1);
        	}
        	var url = isFacilitator==1 ? 'st/smart-alarm-all.html' : 'cus_st/smart-main.html';
        	url = baseUrl + "#" +url;
        	if(url == window.top.location.href) { //如果要跳转的地址与当前地址一样，则重新加载
        		window.top.location.reload();
        		return;
        	}
            window.top.location.href = url;
        },
        toRequirementMarket: function () {
            window.top.location.href = 'news/index.html?requirement';
        },
        showPowerFeeMenu: function () {
            this.powerFeeMenu = true;
        },
        hidePowerFeeMenu: function () {
            this.hidePowerFeeMenu = false;
        },
        enlargePage: function () {
            if (vm.enlargeChildPage) {
                document.getElementById('mainHeader').style.display = 'none';
                document.getElementById('mainSidebar').style.display = 'none';
                $('#enlargeIcon').addClass('enlarge-icon-color');
                $('#contentWrapper').addClass('enlarge-div');
            } else {
                document.getElementById('mainHeader').style.display = 'block';
                document.getElementById('mainSidebar').style.display = 'block';
                $('#enlargeIcon').removeClass('enlarge-icon-color');
                $('#contentWrapper').removeClass('enlarge-div');
            }
            this.$nextTick(function () {
                this.enlargeChildPage = this.enlargeChildPage == false;
                if (vm.enlargeChildPage) {
                    //document.getElementById('sectionTitle').style.display = 'block';
                    $('#sectionTitle').slideDown(1000);
                } else {
                    //document.getElementById('sectionTitle').style.display = 'none';
                    $('#sectionTitle').slideUp(1000);
                }

                var $content = $('.content');
                $content.height($(window).height() - 35);
                $content.find('iframe').each(function () {
                    $(this).height($content.height());
                });
            })

        }
    },
    created: function () {
        var getLoginStatus = this.getMenuList();
        this.getUser();
        var self = this;
        $.ajax({
            url: 'message/unread',
            type: 'post',
            dataType: 'json',
            success: function (r) {
                if (r.code == 0) {
                    self.havingUnread = Boolean(r.page.totalCount);
                } else {
                    console.log(r.msg);
                }
            }
        });
    },
    updated: function () {
        $('.smart').parent().removeClass("active");
        //路由
        var router = new Router();
        routerList(router, vm.menuList);
        router.start();
    }
});

function routerList(router, menuList) {
    for (var key in menuList) {
        var menu = menuList[key];
        if (menu.type == 0) {
            routerList(router, menu.list);
        } else if (menu.type == 1) {
            router.add('#' + menu.url, function () {
                var url = window.location.hash;
                //替换iframe的url
                /* vm.main = url.replace('#', ''); */
                document.getElementById("navThird").style.display = 'none';
                document.getElementById('setIframe').contentWindow.location.replace(url.replace('#', ''));

                //导航菜单展开
                $(".treeview-menu li").removeClass("active");
                $("a[href='" + url + "']").parents("li").addClass("active");
                vm.navTitle = $("a[href='" + url + "']").text();

                if (url == '#st/smart-main.html') {
                    $(".treeview-menu li").find('.smart').parent().addClass("active");
                }
            });
        }
    }
}

function showMenu() {
    $('#navbarNcustomMenu').css('display', 'block');
}

function hideMenu() {
    $('#navbarNcustomMenu').css('display', 'none');
}

var t;

function titleToTrue() {
    clearTimeout(t);
    if ($('#sectionTitle').css('display') == 'none') {
        $('#sectionTitle').slideDown(100);
    } else {
        t = setTimeout(function () {
            if (vm.enlargeChildPage == false) {
                $('#sectionTitle').slideUp(100);
            }
        }, 2000);
    }

}

var throttledTitleTrue = _.throttle(titleToTrue, 500);
