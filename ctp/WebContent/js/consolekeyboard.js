jQuery(function($) {

	// Custom: Hidden Input
	// click on a link - add focus to hidden input
	// ********************
	/*$("[title='Virtual Keyboard']").click(function(){
		$('#hidden').trigger('focus.keyboard');
		return false;
	});*/
	// Initialize keyboard script on hidden input
	// set "position.of" to the same link as above
	$('#hidden').keyboard({ 
		stayOpen : true,
		layout: 'dvorak',
		display: {
			'shift':'Caps Lock: toggle between uppercase Letters and lowercase Letters',
		},
		position     : {
			of : $('#console'),
			my : 'center bottom',
			at : 'center bottom'
		},		
		initialized : function(e, keyboard, el){
			
		},
		change : function(e, keyboard, el){			
			var str = [];
			str[0] = keyboard.lastKey;
			
			var mapper = new keyboardMapper();			
			mapper.sendMsg(str);
			console.log("1");
		},
		/*accepted : function(e, keyboard, el){
			var str = $('#hidden').val();
			
			var mapper = new keyboardMapper();
			var chars = mapper.splitStr(str); 
			mapper.sendMsg(chars);
			
		},*/
		hidden : function(e, keyboard, el){
		},
		//autoAccept : true,
		lockInput : true 
	});
	document.onkeydown=function(e){
		var isie = (document.all) ? true:false;
		var key;
		var event;
		if(isie){
			key = window.event.keyCode;
			event = window.event;
		}else{
			key = e.which;
			event = e;
		} 
		if(key==16){//shift
			var keyboard=$('#hidden').keyboard().getkeyboard();
			if(keyboard.isVisible()){
        		keyboard.close();            		
        	}else{
        		keyboard.reveal();
        	}
		}

	};
});
