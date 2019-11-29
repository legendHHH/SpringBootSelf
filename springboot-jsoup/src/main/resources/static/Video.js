var http = require('../../utils/httputils.js');

Page({
    data: {
        voideUrl:''

    },

    onLoad: function (options) {

        var that = this;

        console.log("-------")
        var prams = {
            username: "1111",
            password: "123456"
        }
        http.getRequest("http://192.168.0.103:8081/video", prams,
            function (res) {
                console.log("----aa---"+res.obj.url)
                that.setData({
                    voideUrl:res.obj.url,
                })
            },
            function (err) {

            })

    },

    onReady() {

    }


});