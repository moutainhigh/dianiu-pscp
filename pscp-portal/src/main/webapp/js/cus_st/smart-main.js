//iframe自适应
$(window).on('resize', function() {
    var $content = $('.content');
    $content.height($(this).height() - 80);
    $content.find('iframe').each(function() {
        $(this).height($content.height());
    });
}).resize();



var vm = new Vue({
    el: '#rrpc',
    /*data: {
        showList: true,
        customerList: []
    },*/
    methods: {
        changeNav: function(e) {
            window.parent.document.getElementById("navThird").style.display = 'inline-block';
            window.parent.document.getElementById("navThird").innerHTML = e.target.innerHTML;
            this.$refs.ifame.contentWindow.location.replace(e.target.getAttribute('data-url'));
        }
    }
})