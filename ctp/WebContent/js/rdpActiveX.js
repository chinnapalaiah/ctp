function OnControlLoadError() {
    window.open('" + SPUtility.GetFullUrl(_spContext.Site, spFile.ServerRelativeUrl) + @"?Download=1');
    history.go(-1);
    return true;
}

function IsWebAccessControlPresent() {
    var retval = false;
    try {
        var WebAccessControl = new ActiveXObject('MsRdpWebAccess.MsRdpClientShell');
        if (WebAccessControl) {
            retval = true;
        }
    }
    catch (e) {
        retval = false;
    }
    return retval;
}
var textContent="screen mode id:i:2" +"\r\n" +
    "use multimon:i:0" +"\r\n" +
    "desktopwidth:i:1600" +"\r\n" +
    "desktopheight:i:900" +"\r\n" +
    "session bpp:i:32" +"\r\n" +
    "winposstr:s:0,3,0,0,800,600" +"\r\n" +
    "compression:i:1" +"\r\n" +
    "keyboardhook:i:2" +"\r\n" +
    "audiocapturemode:i:0" +"\r\n" +
    "videoplaybackmode:i:1" +"\r\n" +
    "connection type:i:7" +"\r\n" +
    "networkautodetect:i:1" +"\r\n" +
    "bandwidthautodetect:i:1" +"\r\n" +
    "displayconnectionbar:i:1" +"\r\n" +
    "enableworkspacereconnect:i:0" +"\r\n" +
    "disable wallpaper:i:0" +"\r\n" +
    "allow font smoothing:i:0" +"\r\n" +
    "allow desktop composition:i:0" +"\r\n" +
    "disable full window drag:i:1" +"\r\n" +
    "disable menu anims:i:1" +"\r\n" +
    "disable themes:i:0" +"\r\n" +
    "disable cursor setting:i:0" +"\r\n" +
    "bitmapcachepersistenable:i:1" +"\r\n" +
    "full address:s:aaaaa" +"\r\n" +
    "audiomode:i:0" +"\r\n" +
    "redirectprinters:i:1" +"\r\n" +
    "redirectcomports:i:0" +"\r\n" +
    "redirectsmartcards:i:1" +"\r\n" +
    "redirectclipboard:i:1" +"\r\n" +
    "redirectposdevices:i:0" +"\r\n" +
    "autoreconnection enabled:i:1" +"\r\n" +
    "authentication level:i:2" +"\r\n" +
    "prompt for credentials:i:0" +"\r\n" +
    "negotiate security layer:i:1" +"\r\n" +
    "remoteapplicationmode:i:0" +"\r\n" +
    "alternate shell:s:" +"\r\n" +
    "shell working directory:s:" +"\r\n" +
    "gatewayhostname:s:" +"\r\n" +
    "gatewayusagemethod:i:4" +"\r\n" +
    "gatewaycredentialssource:i:4" +"\r\n" +
    "gatewayprofileusagemethod:i:0" +"\r\n" +
    "promptcredentialonce:i:0" +"\r\n" +
    "use redirection server name:i:0" +"\r\n" +
    "rdgiskdcproxy:i:0" +"\r\n" +
    "kdcproxyname:s:" +"\r\n" +
    "username:s:jiong.mei@hp.com";



function openRdpfromIE(rdpString){
	
	var WebAccessControlPresent = IsWebAccessControlPresent();
	var objectHtml="<object type='application/x-oleobject' id='MsRdpClient' name='MsRdpClient' onerror='OnControlLoadError' height='0' width='0'";
	try {

	    if (WebAccessControlPresent) {
	    	objectHtml=objectHtml+"classid='CLSID:6A5B0C7C-5CCB-4F10-A043-B8DE007E1952'>";
	    } else {
	        objectHtml=objectHtml+"classid='CLSID:4eb89ff4-7f78-4a0f-8b8d-2bf02e94e4b2'>";
	    }
	    $("#rdpObject").html(objectHtml);
	}
	catch (e) {
	    throw e;
	}

	var MsRdpClient = document.getElementById('MsRdpClient');
	var MsRdpClientShell;
	if (WebAccessControlPresent) {
	    MsRdpClientShell = MsRdpClient;
	}
	else {
	    MsRdpClientShell = MsRdpClient.MsRdpClientShell;
	}
	MsRdpClientShell.PublicMode = true;
	MsRdpClientShell.RdpFileContents =unescape(rdpString);
	
	try {  
        MsRdpClientShell.Launch();  
    }  
    catch (e) {  
        throw e;  
    }  
	
}

