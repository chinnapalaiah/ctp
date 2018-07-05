/**
 *
 * @param addressList
 * @param callback
 * @return
 */
function LatencyProber(addressList, latencyThreshold, callback) {
    var aDelays = [];
    var pingCount = 4;
    var endCount = addressList.length * pingCount;
    var count = 0;

    var doSingleCall = function (dtd, url) {
        var startTime = new Date().getTime();

        var objIMG = new Image();
        objIMG.src = "http://" + url + "/" + startTime;

        objIMG.onload = objIMG.onerror = function () {
            var endTime = new Date().getTime();
            var latency = endTime - startTime;
            aDelays.push(latency);

            count++;
            if (count == endCount) {
                dtd.resolve();
            }
        }
    };

    var doPingCall = function (dtd) {
        for (var i = 0; i < addressList.length * pingCount; i++) {
            var url = addressList[Math.floor(i / pingCount)];
            doSingleCall(dtd, url);
        }

        return dtd.promise();
    };
    var errResult = "The test has Failed! Latency is too high! Please try again later or from another location.";
    var dtd = $.Deferred();
    setTimeout(function () {
        dtd.reject("hurry");
    }, latencyThreshold);
    $.when(doPingCall(dtd))
        .done(function () {
            var averageTime = 0;
            var sumTime = 0;
            for (var i = 0; i < aDelays.length; i++) {
                sumTime += aDelays[i];
            }
            averageTime = sumTime / aDelays.length;

            var result = "";
            if (averageTime > latencyThreshold) {
                result = errResult;
            } else {
                result = "The test successfully! Latency is " + averageTime + " milliseconds";
            }

            callback(result);
        })
        .fail(function () {
        	   bootbox.alert(result, function() {
				});
    
            $("#loading").hide();
            $("#test1").show();
        });
}