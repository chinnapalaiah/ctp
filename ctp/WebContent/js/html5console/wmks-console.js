var wmks = null;
var fitToGuest=null;
function MksConsole(vappName,vmName, vmId) {
    var busyArea = $(".busyArea");
    

    var setStatus = function(message) {
        busyArea.html(message);
    };

    setStatus("Loading...".localize());

    var connected = false;
    wmks = $("#console").wmks().bind("wmksconnected", function() {
        vmware.log("TRACE", "mks-connection", "Connected");
        $("#console > canvas").css("opacity", "");
        wmks.wmks('option', 'allowMobileKeyboardInput', true);
        wmks.wmks('option', 'fitToParent', true);
        connected = true;
        setStatus("Connected");
        
        pluginButtonManager.setState("fullscreen", "connected");
        pluginButtonManager.setState("ctrl-alt-del", "connected");
        pluginButtonManager.setState("fit-to-guest", "connected");
        /*pluginButtonManager.setState("fit-to-browser", "connected");*/
        
        if (browser.versions.mobile||browser.versions.android) {
	        pluginButtonManager.setState("showMobileKeyboard", "connected");
	        pluginButtonManager.setState("hideMobileKeyboard", "connected");
	        
        }else{
        	pluginButtonManager.setState("virtual-keyboard", "connected");
        }
        fitToGuest();
    }).bind("wmksconnecting", function() {
        vmware.log("TRACE", "mks-connection", "Connecting...");
        setStatus("Connecting...".localize());
  
        // changing canvas position to be relative to containing div
        $("#console > canvas").css('position', 'relative');
    }).bind("wmksdisconnected", function(event) {
        vmware.log("TRACE", "mks-connection", "Disconnected {0}".format(event));
        connected = false;
        setStatus("Disconnected".localize());
        

        $("#console > canvas").fadeTo("slow", 0.5);
        $("#console > canvas").css("cursor", "default");
        
    }).bind("wmksresolutionchanged", function() {
        vmware.log("TRACE", "mks-console", "Resolution changed");
        fitToGuest();
    }).bind("wmkserror", function(event, error) {
        vmware.log("ERROR", "mks-console", "Error occurred: {0}".format(error));
    }).bind("wmksprotocolError", function(event) {
        vmware.log("ERROR", "mks-console", "Protocol error occurred: {0}".format(event));
    }).bind("wmksauthenticationFailed", function(event) {
        vmware.log("ERROR", "mks-console", "Authentication failure: {0}".format(event));
    });
    


    $(window).resize(function() {
        // resetting overflow handling (set during resize to prevent errant scrollbars in Chrome)
        $(document.body).css("overflow", "");
    });
    
    

    fitToGuest = function() {
        var canvas = $("#console > canvas");

        // width and height of console
        var guestWidth = canvas.width();
        var guestHeight = canvas.height();

        // estimated width and height consumed by other elements
        var cssWidth = 0;
        var cssHeight = 40;

        // guess of width and height used by browser
        var guessedBrowserWidth = 20;
        var guessedBrowserHeight = 60;

        // values to pass to resize
        var computedWidth = guestWidth + cssWidth + guessedBrowserWidth;
        var computedHeight = guestHeight + cssHeight + guessedBrowserHeight;
        vmware.log("TRACE", "wrapper", "Attempting to resize to {0}x{1} for guest size {2}x{3}"
            .format(computedWidth, computedHeight, guestWidth, guestHeight));
        $("#console").removeClass("full");
        $("#console").addClass("standard");
        $("#console").width($("#console > canvas").width());
        $("#console").height($("#console > canvas").height());
        $(document.body).css("overflow", "hidden");
        window.resizeTo(guestWidth, guestHeight);
        //wmks.wmks("rescale");  
    };

    var getFullScreenElement = function() {
        if (document.fullscreenElement) {
            return document.fullscreenElement;
        } else if (document.mozFullScreenElement) {
            return document.mozFullScreenElement;
        } else if (document.webkitFullscreenElement) {
            return document.webkitFullscreenElement;
        }

        return null;
    };

    var onFullScreenChange = function() {
        var element = getFullScreenElement();
        if (element) {
            return;
        }

        vmware.log("TRACE", "fullscreen", "Exiting fullscreen mode");
        fitToGuest();
    };

    $("#console").bind("webkitfullscreenchange mozfullscreenchange fullscreenchange", onFullScreenChange);
    $(document).bind("mozfullscreenchange", onFullScreenChange);

    MksConsole.prototype.setState = function(state) {
        setStatus(state);
    };

    MksConsole.prototype.isConnected = function() {
        return connected;
    };

    MksConsole.prototype.fitToGuest = function() {
        fitToGuest();
    };

    MksConsole.prototype.fullScreen = function() {
        var containerElement = document.getElementById("console");
        $("#console").removeClass("standard");
        $("#console").addClass("full");
        if(containerElement.requestFullScreen) {
            containerElement.requestFullScreen();
        } else if(containerElement.mozRequestFullScreen) {
            containerElement.mozRequestFullScreen();
        } else if(containerElement.webkitRequestFullScreen) {
        	if(window.navigator.userAgent.toUpperCase().indexOf('CHROME')>=0){
        		containerElement.webkitRequestFullScreen(Element.ALLOW_KEYBOARD_INPUT);
        	}else{
        		containerElement.webkitRequestFullScreen();
        	}
        } else if(containerElement.msRequestFullscreen){ //IE 10+
          		containerElement.msRequestFullscreen();
        } else if(typeof window.ActiveXObject != "undefined"){//Older IE 10/10-
        	var wscript = new window.ActiveXObject("WScript.Shell");
        	if(wscript!=null){
        		wscript.SendKeys("{F11}");
        	}
        	
        }
    };

    MksConsole.prototype.sendCtrlAltDelete = function() {
        // There is a sendKeyCodes method, but it is causing an infinite trigger loop
        // for some reason
        wmks.wmks("sendKeyCode", 17, "keydown");
        wmks.wmks("sendKeyCode", 18, "keydown");
        wmks.wmks("sendKeyCode", 46, "keydown");
        wmks.wmks("sendKeyCode", 46, "keyup");
        wmks.wmks("sendKeyCode", 18, "keyup");
        wmks.wmks("sendKeyCode", 17, "keyup");
    };
    
    MksConsole.prototype.showMobileKyeboard = function() {
        // There is a showKeyboard method, but it is show the keyboard
        // for some reason
        wmks.wmks("showKeyboard");       
    };
    MksConsole.prototype.hideMobileKyeboard = function() {
        // There is a showKeyboard method, but it is show the keyboard
        // for some reason
        wmks.wmks("hideKeyboard");       
    };
    MksConsole.prototype.fitToBrowser = function() {
        // There is a showKeyboard method, but it is show the keyboard
        // for some reason
        wmks.wmks("rescale");       
    };
    MksConsole.prototype.connect = function(host, port, vmx, ticket) {
        var url = "wss://{0}/{1};{2}".format(host, port, ticket);
        vmware.log("TRACE", "mks-connection", "Connecting to {0}".format(url));
        wmks.wmks("connect", url, vmx);
    };
    
}
var mks = null;

function buildConsoleChrome(vappName,vmName,vmId) {
    vmware.log("TRACE", "layout", "Adding/configuring console UI controls");

    $(document).attr("title",  vappName + " - " + vmName);
    $("#vmName").text(vmName);

    mks = new MksConsole(vappName,vmName, vmId);
    
    span = $('#plugin-buttons');
    pluginButtonManager = vmware.buttonManager({container: span})
        .createButton("ctrl-alt-del", {defaultState: {image: "img/ctrl-alt-del-16x16-disabled.png", style: "cursor:not-allowed;margin-right: 5px;", text: "Send Ctrl-Alt-Del (disabled)".localize()}, connected: {image: "img/ctrl-alt-del-16x16.png", style: "cursor:pointer;margin-right: 5px;", text: "Send Ctrl-Alt-Del".localize()}})
        .registerHandler("ctrl-alt-del", function(event) {
            if (mks.isConnected()) {
                mks.sendCtrlAltDelete();
            }
        })
        .createButton("fullscreen", {defaultState: {image: "img/full-screen-16x16-disabled.png", style: "cursor:not-allowed;margin-right: 5px;", text: "Full Screen (disabled)".localize()}, connected: {image: "img/full-screen-16x16.png", style: "cursor:pointer;margin-right: 5px;", text: "Full Screen".localize()}})
        .registerHandler("fullscreen", function(event) {
            if (mks.isConnected()) {
                mks.fullScreen();
            }
        })
        .createButton("fit-to-guest", {defaultState: {image: "img/fit-to-guest-16x16-disabled.png", style: "cursor:not-allowed;margin-right: 5px;", text: "Fit Window to Console (disabled)".localize()}, connected: {image: "img/fit-to-guest-16x16.png", style: "cursor:pointer;margin-right: 5px;", text: "Fit Window to Console".localize()}})
        .registerHandler("fit-to-guest", function(event) {
            if (mks.isConnected()) {
                mks.fitToGuest();
               
            }
        })
        
        /*.createButton("fit-to-browser", {defaultState: {image: "img/hide-keyboard-16x16-disabled.png", style: "cursor:not-allowed;margin-right: 5px;", text: "Hide Keyboard (disabled)".localize()}, connected: {image: "img/hide-keyboard-16x16.png", style: "cursor:pointer;margin-right: 5px;", text: "Hide Keyboard".localize()}})
        .registerHandler("fit-to-browser", function(event) {
            if (mks.isConnected()) {
                mks.fitToBrowser();
            }
        });*/
    if (browser.versions.mobile||browser.versions.android) {   				
    	pluginButtonManager
        .createButton("showMobileKeyboard", {defaultState: {image: "img/show-keyboard-16x16-disabled.png", style: "cursor:not-allowed;margin-right: 5px;", text: "Show Keyboard (disabled)".localize()}, connected: {image: "img/show-keyboard-16x16.png", style: "cursor:pointer;margin-right: 5px;", text: "Show Keyboard".localize()}})
        .registerHandler("showMobileKeyboard", function(event) {
            if (mks.isConnected()) {
                mks.showMobileKyeboard();
            }
        })
        .createButton("hideMobileKeyboard", {defaultState: {image: "img/hide-keyboard-16x16-disabled.png", style: "cursor:not-allowed", text: "Hide Keyboard (disabled)".localize()}, connected: {image: "img/hide-keyboard-16x16.png", style: "cursor:pointer", text: "Hide Keyboard".localize()}})
        .registerHandler("hideMobileKeyboard", function(event) {
            if (mks.isConnected()) {
                mks.hideMobileKyeboard();
            }
        });       
	} else{
		pluginButtonManager
		.createButton("virtual-keyboard", {defaultState: {image: "img/virtual-keyboard-16x16-disabled.png", style: "cursor:not-allowed;margin-right: 5px;width:15px;height:15px;", text: "Virtual Keyboard (disabled)".localize()}, connected: {image: "img/virtual-keyboard-16x16.png", style: "cursor:pointer;margin-right: 26px;width:15px;height:15px;", text: "Virtual Keyboard".localize()}})
        .registerHandler("virtual-keyboard", function(event) {
            if (mks.isConnected()) {            	
            	var keyboard=$('#hidden').keyboard().getkeyboard();
            	if(keyboard.isVisible()){
            		keyboard.close();            		
            	}else{
            		keyboard.reveal();
            	} 			
            }
        });
	}
}

function acquireTicket(vappUrl,vmname) {
    vmware.log("TRACE", "init", "attempting ticket acquisition for vm {0}".format(vmname));
    
    $.ajax({
		type : "GET",
		url : "trainingmgm/htmlConsole.do",
		cache : false,
		data : {
			vappUrl:vappUrl,
			vmName:vmname			
		},
		error :  function(msg){
			isConnecting=false;
			$(".busyArea").html("disconnected");
			
		},
		cache:false,
		success : function(msg) {		
			host = msg.host;
			ticket = msg.ticket;			
			vmx =msg.vmx;
			port=msg.port;
			//alert("vmx:"+vmx);
			connectControl(host, port, vmx, ticket);
		}
	});

}

function connectControl(host, port, vmx, ticket) {
    vmware.log("TRACE", "plugin", "Connecting vm");
    mks.connect(host, port, vmx, ticket);
};

$(window).on('orientationchange', function() {
	 var canvas = $("#console > canvas");

     // width and height of console
     var guestWidth = canvas.width();
     var guestHeight = canvas.height();
     $("#console").width($("#console > canvas").width());
     $("#console").height($("#console > canvas").height());
     $(document.body).css("overflow", "hidden");
     window.resizeTo(guestWidth, guestHeight);
 //   mks.fitToGuest();
});
