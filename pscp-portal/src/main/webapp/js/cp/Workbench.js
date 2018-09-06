//iframe自适应
$(window).on('resize', function () {
    var $content = $('.content');
    $content.width()
    $content.height($(this).height() - 80);
    $content.find('iframe').each(function () {
        $(this).height($content.height());
    });
}).resize();

var myTabs = [
    '<div>',
    '   <ul class="nav nav-tabs" style="margin-bottom: 0px;">',
    '       <li role="presentation" v-for="tab in tabs" :class="{\'active\': tab.isActive}" @click="tab.toggleTap">',
    '           <a @click="selectedTab(tab)"><i :class="\'fa \' + tab.icon"></i>{{ tab.name }}</a>',
    '       </li>',
    '   </ul>',
    '   <div class="tab-detail">',
    '       <slot></slot>',
    '   </div>',
    '</div>'
].join("");
var myTab = ['<div>', '</div>'].join("");

Vue.component('tabs', {
    template: myTabs,
    data: function () {
        return {tabs: []};
    },
    created: function () {
        this.tabs = this.$children;
    },
    methods: {
        selectedTab: function (_selectedTab) {
            this.tabs.forEach(function (tab) {
                tab.isActive = tab.name === _selectedTab.name;
            });
        }
    }
})

Vue.component('tab', {
    props: {
        name: {
            required: true
        },
        selected: {
            default: false
        },
        status: {
            required: true
        }
    },
    template: myTab,
    data: function () {
        return {isActive: false, copyGridParam: null};
    },
    methods: {
        toggleTap: function () {
            vm.status = this.status;
        }
    },
    mounted: function () {
        this.isActive = this.selected;
    }
});


var vm = new Vue({
    el: '#rrapp',
    data: {
        status: 'workbench_equipment.html',
        page: []
    },
    methods: {},
    created: function () {
        //配电房列表
        var self = this
        $.ajax({
            url: '../room/list',
            type: 'POST',
            success: function (result) {
                var res = result.page
                if (result.code === 0) {
                    var pagelist = res.list
                    for (var i = 0; i < pagelist.length; i++) {
                        self.page.push(pagelist[i].address)
                    }
                    console.log(self.page)
                }
            }
        })
    },
    mounted: function () {
        $('.form_datetime').datetimepicker({
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
    }
});