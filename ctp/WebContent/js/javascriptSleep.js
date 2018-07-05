var   commandList   =   [];
function   executeCommands(){
    if   (commandList.length>0){
        commandList.shift()();

    }
}

function   startNewTask(){
    var   resultTemp   =   document.createElement("span");
    document.body.insertBefore(resultTemp,document.body.lastChild);
    document.body.insertBefore(document.createElement("br"),document.body.lastChild);
    resultTemp.innerText   =   0;
    commandList.push(function(){simThread(resultTemp,0);});
}

function     simThread(temp,n){
    temp.innerText   =   temp.innerText-(-n);
    if   (n<1500)
        commandList.push(function(){simThread(temp,++n);});
    else{
        document.body.removeChild(temp.nextSibling);
        document.body.removeChild(temp);
    }
}

window.onload   =   function(){setInterval("executeCommands()",1);}
