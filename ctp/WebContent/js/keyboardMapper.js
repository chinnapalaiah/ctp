
var keyboardMapper = function(){};


keyboardMapper.prototype.sendMsg = function(chars){
	
	var code;
	for(var i=0; i<chars.length; i++){
	    code = this.tableStrToUnicode[chars[i]];
	    if(code!='undefined'&&code != undefined&&code!=''){
	    	wmks.wmks("sendKeyCode", code, "keypress");
	    }else{
	    	code = this.tableFunToUnicode[chars[i]];
	    	for(var i=0; i<chars.length; i++){	        	
	        	wmks.wmks("sendKeyCode", code, "keydown");
	        }
	        for(var i=chars.length-1; i>0; i--){	        	
	        	wmks.wmks("sendKeyCode", code, "keyup");
	        }
	    }
	}
};

keyboardMapper.prototype.splitStr = function(str){
	
	var chars = str.split("");
	return chars;
};


keyboardMapper.prototype.tableStrToUnicode = {
   // Space, enter, backspace
   '&nbsp;' : 32, //32 : 0x39,
   'Enter' : 13, //13 : 0x1c,
   //'Bksp' : 8, ////8 : 0x0e,

   // Keys a-z
   a : 97,  //97  : 0x1e,
   b : 98,  //98  : 0x30,
   c : 99,  //99  : 0x2e,
   d : 100, //100 : 0x20,
   e : 101, //101 : 0x12,
   f : 102, //102 : 0x21,
   g : 103, //103 : 0x22,
   h : 104, //104 : 0x23,
   i : 105, //105 : 0x17,
   j : 106, //106 : 0x24,
   k : 107, //107 : 0x25,
   l : 108, //108 : 0x26,
   m : 109, //109 : 0x32,
   n : 110, //110 : 0x31,
   o : 111, //111 : 0x18,
   p : 112, //112 : 0x19,
   q : 113, //113 : 0x10,
   r : 114, //114 : 0x13,
   s : 115, //115 : 0x1f,
   t : 116, //116 : 0x14,
   u : 117, //117 : 0x16,
   v : 118, //118 : 0x2f,
   w : 119, //119 : 0x11,
   x : 120, //120 : 0x2d,
   y : 121, //121 : 0x15,
   z : 122, //122 : 0x2c,

   // keyboard number keys (across the top) 1,2,3... -> 0
   1 : 49, //49 : 0x02,
   2 : 50, //50 : 0x03,
   3 : 51, //51 : 0x04,
   4 : 52, //52 : 0x05,
   5 : 53, //53 : 0x06,
   6 : 54, //54 : 0x07,
   7 : 55, //55 : 0x08,
   8 : 56, //56 : 0x09,
   9 : 57, //57 : 0x0a,
   0 : 48, //48 : 0x0b,

   // Symbol keys ; = , - . / ` [ \ ] '
   ';' : 59, //59 : 0x27, // ;
   '=' : 61, //61 : 0x0d, // =
   ',' : 44, //44 : 0x33, // ,
   '-' : 45, //45 : 0x0c, // -
   '.' : 46, //46 : 0x34, // .
   '/' : 47, //47 : 0x35, // /
   '`' : 96, //96 : 0x29, // `
   '[' : 91, //91 : 0x1a, // [
   '\'' : 92, //92 : 0x2b, // \
   ']' : 93, //93 : 0x1b, // ]
   '' : 39, //39 : 0x28,  // '


     // Keys A-Z
   A : 65, //65 : 0x001e,
   B : 66, //66 : 0x0030,
   C : 67, //67 : 0x002e,
   D : 68, //68 : 0x0020,
   E : 69, //69 : 0x0012,
   F : 70, //70 : 0x0021,
   G : 71, //71 : 0x0022,
   H : 72, //72 : 0x0023,
   I : 73, //73 : 0x0017,
   J : 74, //74 : 0x0024,
   K : 75, //75 : 0x0025,
   L : 76, //76 : 0x0026,
   M : 77, //77 : 0x0032,
   N : 78, //78 : 0x0031,
   O : 79, //79 : 0x0018,
   P : 80, //80 : 0x0019,
   Q : 81, //81 : 0x0010,
   R : 82, //82 : 0x0013,
   S : 83, //83 : 0x001f,
   T : 84, //84 : 0x0014,
   U : 85, //85 : 0x0016,
   V : 86, //86 : 0x002f,
   W : 87, //87 : 0x0011,
   X : 88, //88 : 0x002d,
   Y : 89, //89 : 0x0015,
   Z : 90, //90 : 0x002c,

   '!' : 33, //33 : 0x0002, // !
   '@' : 64, //64 : 0x0003, // @
   '#' : 35, //35 : 0x0004, // #
   '$' : 36, //36 : 0x0005, // $
   '%' : 37, //37 : 0x0006, // %
   '^' : 94, //94 : 0x0007, // ^
   '&' : 38, //38 : 0x0008, // &
   '*' : 42, //42 : 0x0009, // *
   '(' : 40, //40 : 0x000a, // (
   ')' : 41, //41 : 0x000b, // )

   ':' : 58, //58  : 0x0027, // :
   '+' : 43, //43  : 0x000d, // +
   '<' : 60, //60  : 0x0033, // <
   '_' : 95, //95  : 0x000c, // _
   '>' : 62, //62  : 0x0034, // >
   '?' : 63, //63  : 0x0035, // ?
   '~' : 126, //126 : 0x0029, // ~
   '{' : 123, //123 : 0x001a, // {
   '|' : 124, //124 : 0x002b, // |
   '}' : 125, //125 : 0x001b, // }
   '"' : 34, //34  : 0x0028, // "*/
};

keyboardMapper.prototype.tableFunToUnicode = {
   // Space, enter, tab, escape, backspace
   ////32 : 0x039,
   ////13 : 0x01c,
   '⇥ Tab' : 9, //9 : 0x00f,
   //27 : 0x001,
   'Bksp' : 8,//8 : 0x00e,

   // shift, control, alt, Caps Lock, Num Lock
   //16 : 0x02a,     // left shift
   //17 : 0x01d,     // left control
   //18 : 0x038,     // left alt
   //20 : 0x03a,
   //144 : 0x045,

   // Arrow keys (left, up, right, down)
   //37 : 0x14b,
   //38 : 0x148,
   //39 : 0x14d,
   //40 : 0x150,

   // Special keys (Insert, delete, home, end, page up, page down, F1 - F12)
   //45 : 0x152,
   //46 : 0x153,
   //36 : 0x147,
   //35 : 0x14f,
   //33 : 0x149,
   //34 : 0x151,
   //112 : 0x03b,
   //113 : 0x03c,
   //114 : 0x03d,
   //115 : 0x03e,
   //116 : 0x03f,
   //117 : 0x040,
   //118 : 0x041,
   //119 : 0x042,
   //120 : 0x043,
   //121 : 0x044,
   //122 : 0x057,
   //123 : 0x058,

   // Special Keys (Left Apple/Command, Right Apple/Command, Left Windows, Right Windows, Menu)
   //224 : 0x038,
   //// ? : 0x138,
   //91 : 0x15b,
   //92 : 0x15c,
   //93 : 0, //?

   //42 : 0x054,  // PrintScreen / SysRq
   //19 : 0x100,  // Pause / Break
};
