$.ping = function (option) {

    var ping, requestTime, responseTime;

    var getUrl = function (url) {    //保证url带http://

        var strReg = "^((https|http)?://){1}"

        var re = new RegExp(strReg);

        return re.test(url) ? url : "http://" + url;

    }

    $.ajax({

        url: getUrl(option.url) + '/' + (new Date()).getTime() + '.html',  //设置一个空的ajax请求

        type: 'GET',

        dataType: 'html',

        timeout: 10000,

        beforeSend: function () {

            if (option.beforePing) option.beforePing();

            requestTime = new Date().getTime();

        },

        complete: function () {

            responseTime = new Date().getTime();

            ping = Math.abs(requestTime - responseTime);

            if (option.afterPing) option.pingArray.push(ping);

        }

    });


    if (option.interval && option.interval > 0) {

        var interval = option.interval * 1000;
        if (option.pingArray.length > 3) {
            option.result.html(average(option.pingArray));
//            alert(average(option.pingArray));
            return;
        }
        setTimeout(function () {
            $.ping(option)
        }, interval);

        //      option.interval = 0;        // 阻止多重循环

        //      setInterval(function(){$.ping(option)}, interval);

    }

    function average(arr) {
        var sum = 0;
        for (var i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum / arr.length;
    }
};