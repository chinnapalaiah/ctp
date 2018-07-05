 function detectOS() {
        var sUserAgent = navigator.userAgent;
      
        var isWin = (navigator.platform === "Win32") || (navigator.platform === "Windows");
        var isMac = (navigator.platform === "Mac68K") || (navigator.platform === "MacPPC") || (navigator.platform === "Macintosh") || (navigator.platform === "MacIntel");
        var bIsIpad = sUserAgent.indexOf('iPad') > -1;
        var bIsIphoneOs = sUserAgent.indexOf('iPhone') > -1 ;
        var isUnix = (navigator.platform === "X11") && !isWin && !isMac;
        var isLinux = (String(navigator.platform).indexOf("Linux") > -1);
        var bIsAndroid = (String(sUserAgent).indexOf("Android") > -1);
        var bIsCE = sUserAgent.match(/windows ce/i) === "windows ce";
        var bIsWM = sUserAgent.match(/windows mobile/i) === "windows mobile";
        if (isMac)
            return "macos";
        if (isUnix)
            return "Unix";
        if (isLinux) {
            if (bIsAndroid)
                return "android";
            else
                return "Linux";
        }
        if(bIsCE || bIsWM){
            return 'wm';
        }
        if(bIsIphoneOs||bIsIpad){
        	return "ios";
        }
        if (isWin) {
            var isWin2K = sUserAgent.indexOf("Windows NT 5.0") > -1 || sUserAgent.indexOf("Windows 2000") > -1;
            if (isWin2K)
                return "Win2000";
            var isWinXP = sUserAgent.indexOf("Windows NT 5.1") > -1 ||
                    sUserAgent.indexOf("Windows XP") > -1;
            if (isWinXP)
                return "WinXP";
            var isWin2003 = sUserAgent.indexOf("Windows NT 5.2") > -1 || sUserAgent.indexOf("Windows 2003") > -1;
            if (isWin2003)
                return "Win2003";
            var isWinVista = sUserAgent.indexOf("Windows NT 6.0") > -1 || sUserAgent.indexOf("Windows Vista") > -1;
            if (isWinVista)
                return "WinVista";
            var isWin7 = sUserAgent.indexOf("Windows NT 6.1") > -1 || sUserAgent.indexOf("Windows 7") > -1;
            if (isWin7)
                return "Win7";
            var isWin8 = sUserAgent.indexOf("Windows NT 6.2") > -1 || sUserAgent.indexOf("Windows 8") > -1;
            if (isWin8)
                return "Win8";
        }
        return "other";
    }
