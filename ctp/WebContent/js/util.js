 //get root url,eg: http://localhost:8083/uimcardprj
    var util={
    		getRootPath:function(){
    			//get current url, eg: http://localhost:8083/uimcardprj/share/meun.jsp
    	        var curWwwPath=window.document.location.href;
    	        //get directory, eg: uimcardprj/share/meun.jsp
    	        var pathName=window.document.location.pathname;
    	        var pos=curWwwPath.indexOf(pathName);
    	        //get hostname, eg:http://localhost:8083
    	        var localhostPaht=curWwwPath.substring(0,pos);
    	        //get project name with "/", eg: /uimcardprj
    	        var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    	        if(projectName=="/ctp"){
    	        	return (localhostPaht+projectName);
    	        }else{
    	        	return localhostPaht;
    	        }
    	        
    		}(),
    		getUrlParam:function(name){
    			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
                var r = window.location.search.substr(1).match(reg);
                if (r != null) return unescape(r[2]); return null;
    		}
    }
    /**
     * alert("ios terminal: "+browser.versions.ios);
     * alert("android terminal: "+browser.versions.android);
     * alert(" if it is iPhone: "+browser.versions.iPhone);
     * alert(" if it is iPad: "+browser.versions.iPad);
     * alert(" if it is mobile terminal: "+browser.versions.mobile);
     */
    var browser={
    	    versions:function(){
	            var u = navigator.userAgent, app = navigator.appVersion;
	            
	            return { //version information of mobility
	            	ie11:u.indexOf('like Gecko') > -1 && u.indexOf('rv:11.0') > -1,
	            	ie10:function(){	            		
	                	if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE10.0")
	                	{ return true; 
	                	}else{ 
	                		return false; 
	                	}
                	}(),
	                ie9:function(){
		                	if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE9.0")
		                	{ return true; 
		                	}else{ 
		                		return false; 
		                	}
	                	}(),
	                ie8:function(){
		                	if(navigator.appName == "Microsoft Internet Explorer" && navigator.appVersion .split(";")[1].replace(/[ ]/g,"")=="MSIE8.0")
		                	{ 
		                		return true;
		                	}else{
		                		return false;
		                	}
	                	}(),
	            	trident: u.indexOf('Trident') > -1, //IE core
	                presto: u.indexOf('Presto') > -1, //opera core
	                webKit: u.indexOf('AppleWebKit') > -1, //apple, google core
	                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //ff core
	                mobile: !!u.match(/AppleWebKit.*Mobile.*/), //if it is mobile terminal
	                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios terminal
	                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android terminal或uc browser
	                iPhone: u.indexOf('iPhone') > -1 , //if it is iPhone or QQHD browser
	                iPad: u.indexOf('iPad') > -1, //if it is iPad
	                webApp: u.indexOf('Safari') == -1 //if it is web application without header and footer.
	           };
    	    }(),
    	    language:(navigator.browserLanguage || navigator.language).toLowerCase()
    	};

    function isValidURL(url){
        var RegExp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;

        if(RegExp.test(url)){
            return true;
        }else{
            return false;
        }
    } 