var myTabs = ['<div class="layui-tab layui-tab-brief">',
    '   <ul class="layui-tab-title">',
    '       <li v-for="tab in tabs" :class="{\'layui-this\': tab.isActive}" @click="tab.toggleTap();selectedTab(tab);">',
    '           <span v-html="tab.name"></span>',
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
})

Vue.component('tab', {
    props: {
        status: {
            required: true
        },
        name: {
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
            if (this.status === 'all') {
                vm.statusVm = 'all';
                layui.use('laypage', function() {
                    var laypage = layui.laypage;
                    $.ajax({
                        url: '../message/read?page=1&limit=10',
                        type: 'get',
                        dataType: 'json',
                        success: function(r) {
                            if (r.code == 0) {
                                vm.list = r.page.list;
                                laypage.render({
                                    elem: 'page',
                                    count: r.page.totalPage,
                                    limit: 1,
                                    layout: ['prev', 'page', 'next'],
                                    jump: function(obj, first) {
                                        vm.showPage = false;
                                        var page = obj.curr;
                                        $.ajax({
                                            url: '../message/read?page=' + page + '&limit=10',
                                            type: 'get',
                                            dataType: 'json',
                                            success: function(r) {
                                                if (r.code == 0) {
                                                    vm.showPage = true;
                                                    vm.list = r.page.list;
                                                } else {
                                                    alert(r.msg);
                                                }
                                            }
                                        })
                                    }
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                });
            } else if (this.status == 'not') {
                vm.statusVm = 'not';
                //vm.showPage = false;
                layui.use('laypage', function() {
                    var laypage = layui.laypage;
                    $.ajax({
                        url: '../message/unread?page=1&limit=10',
                        type: 'get',
                        dataType: 'json',
                        success: function(r) {
                            if (r.code == 0) {
                                vm.showPage = false;
                                vm.list = r.page.list;
                                laypage.render({
                                    elem: 'page',
                                    count: r.page.totalPage,
                                    limit: 1,
                                    layout: ['prev', 'page', 'next'],
                                    jump: function(obj, first) {
                                        //vm.showPage = false;
                                        var page = obj.curr;
                                        $.ajax({
                                            url: '../message/unread?page=' + page + '&limit=10',
                                            type: 'get',
                                            dataType: 'json',
                                            success: function(r) {
                                                if (r.code == 0) {
                                                    vm.showPage = true;
                                                    vm.list = r.page.list;
                                                } else {
                                                    alert(r.msg);
                                                }
                                            }
                                        })
                                    }
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                });
                /*$.ajax({
                    url: '../message/unread',
                    type: 'post',
                    dataType: 'json',
                    success: function(r) {
                        if (r.code == 0) {
                            vm.showPage = true;
                            vm.list = r.messageList;
                            if (r.messageList && r.messageList.length > 0) {
                                vm.unreadMsgCount = r.messageList.length;
                            }
                        } else {
                            alert(r.msg);
                        }
                    }
                });*/
            }
        }
    },
    mounted: function() {
        this.isActive = this.selected;
    }
})

var msgStr = ['<div class="layui-colla-item" @click="changeDotStatus(getdata.id)">',
    '    <h2 class="layui-colla-title">{{ getdata.title}}',
    '        <span class="layui-badge-dot" v-show="!getdata.isRead"></span>',
    '        <span class="msg-right">{{ getdata.pushTime }}</span>',
    '    </h2>',
    '    <div class="layui-colla-content">{{ getdata.content }}</div>',
    '</div>'
].join("");
Vue.component('message', {
    template: msgStr,
    props: ['getdata'],
    methods: {
        changeDotStatus: function(id) {
            var self = this;
            if (this.getdata.isRead) {
                return;
            } else {
                $.ajax({
                    url: '../message/read/' + id,
                    type: 'post',
                    dataType: 'json',
                    success: function(r) {
                        if (r.code == 0) {
                            self.getdata.isRead = 1;
                            vm.unreadMsgCount--;
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            }
        }
    },
    created: function() {
        layui.use('element', function() {
            var element = layui.element;
            setTimeout(function() {
                element.init();
            }, 50)
        });
    }
})

var vm = new Vue({
    el: '#rrapp',
    data: {
        unreadMsgCount: 0,
        list: [],
        //销毁页面 重新初始化
        showPage: true,
        statusVm: 'not'
    },
    computed: {
        unreadMsgHtml: function() {
            var unreadMsgCountStr = '';
            if (this.unreadMsgCount > 99) {
                unreadMsgCountStr = '<span class="layui-badge">99+</span>';
            } else if (this.unreadMsgCount == 0) {
                unreadMsgCountStr = '';
            } else {
                unreadMsgCountStr = '<span class="layui-badge">' + this.unreadMsgCount + '</span>';
            }
            return '未读消息' + unreadMsgCountStr;
        }
    },
    methods: {
        clearMsg: function() {
            var index = layer.confirm('确认清空所有消息?', function() {
                $.ajax({
                    url: '../message/clear',
                    type: 'post',
                    dataType: 'json',
                    success: function(r) {
                        if (r.code == 0) {
                            layer.close(index);
                            alert('消息已清空!', function() {
                                location.reload();
                            })
                        } else {
                            layer.close(index);
                            alert(r.msg);
                        }

                    }
                })
            });
        }
    },
    mounted: function() {
        var self = this;
        //self.showPage = false;
        layui.use('laypage', function() {
            var laypage = layui.laypage;
            $.ajax({
                url: '../message/unread?page=1&limit=10',
                type: 'get',
                dataType: 'json',
                success: function(r) {
                    if (r.code == 0) {
                        vm.list = r.page.list;
                        vm.unreadMsgCount = r.page.totalCount;
                        laypage.render({
                            elem: 'page',
                            count: r.page.totalPage,
                            limit: 1,
                            layout: ['prev', 'page', 'next'],
                            jump: function(obj, first) {
                                //vm.showPage = false;
                                var page = obj.curr;
                                $.ajax({
                                    url: '../message/unread?page=' + page + '&limit=10',
                                    type: 'get',
                                    dataType: 'json',
                                    success: function(r) {
                                        if (r.code == 0) {
                                            vm.showPage = true;
                                            vm.list = r.page.list;
                                        } else {
                                            alert(r.msg);
                                        }
                                    }
                                })
                            }
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        });
    }
})