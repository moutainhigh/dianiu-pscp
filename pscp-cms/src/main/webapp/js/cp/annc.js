$(function() {
    $("#jqGrid").jqGrid({
        url: $ctx + '/news/notice/list',
        datatype: "json",
        colModel: [
            { label: 'id', name: 'id', width: 50, key: true },
            { label: '标题', name: 'title' },
            { label: '排序号', name: 'orderNum' },
            {
                label: '状态',
                name: 'status',
                formatter: function(value, options, row) {
                    var label = '';
                    if (value == 0) {
                        label = '<span class="label label-danger">已下线</span>';
                    }
                    if (value == 1) {
                        label = '<span class="label label-success">已上线</span>';
                    }
                    return label;
                }
            },
            { label: '创建时间', name: 'createTime' },
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function() {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x": "hidden" });
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        editor: null,
        news: {
            id: null,
            type: null,
            title: '',
            description: '',
            coverPic: {
                fid: ''
            },
            status: 1,
            orderNum: 0,
            content: ''
        }
    },
    methods: {
        add: function() {
            this.showList = false;
            this.title = '新增';
            this.news.id = null;
            this.news.type = 2;
            this.errors.clear();
        },
        update: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            this.title = '修改';
            this.showList = false;
            this.news.id = id;
            this.errors.clear();
            var data = { id: id };
            var self = this;
            $.ajax({
                url: $ctx + '/news/details',
                type: 'post',
                data: JSON.stringify(data),
                success: function(r) {
                    if (r.code == 0) {
                        self.news = r.news;
                        self.editor.txt.html(r.news.content);
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },
        save: function(e) {
            this.preventRepeat(e);
            this.news.content = this.editor.txt.html();
            var self = this;
            this.$validator.validateAll().then(function() {
                $.ajax({
                    url: $ctx + '/news/save',
                    type: 'post',
                    data: JSON.stringify(self.news),
                    success: function(r) {
                        if (r.code == 0) {
                            alert('保存成功!', function() {
                                self.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                })
            }, function() {
                alert('信息填写不完整或不规范');
            });
        },
        changeStatus: function() {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            var self = this;
            var data = { id: id };
            $.ajax({
                url: $ctx + '/news/switch/status',
                type: 'post',
                data: JSON.stringify(data),
                success: function(r) {
                    if (r.code == 0) {
                        alert('切换状态成功', function() {
                            self.reload();
                        })
                    } else {
                        alert(r.msg);
                    }
                }
            })
        },

        reload: function() {
            this.showList = true;
            this.news = {
                id: null,
                type: null,
                title: '',
                description: '',
                coverPic: {
                    fid: ''
                },
                status: 1,
                orderNum: 0,
                content: ''
            }
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    },
    mounted: function() {
        //初始化富文本编辑器
        var E = window.wangEditor
        var editor = new E('#editor')
        this.editor = editor;
        editor.customConfig.menus = [
            'head', // 标题
            'bold', // 粗体
            'italic', // 斜体
            'underline', // 下划线
            'strikeThrough', // 删除线
            'foreColor', // 文字颜色
            'backColor', // 背景颜色
            'link', // 插入链接
            'list', // 列表
            'justify', // 对齐方式
            'quote', // 引用
            'image', // 插入图片
            'table', // 表格
        ]
        editor.customConfig.uploadImgMaxSize = 5 * 1024 * 1024;
        editor.customConfig.uploadImgMaxLength = 10;
        editor.customConfig.uploadFileName = 'file';
        editor.customConfig.uploadImgServer = $ctx + '/news/upload';
        editor.create();
    }
});