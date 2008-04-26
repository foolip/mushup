function initInput() {
    var elems = YAHOO.util.Dom.getElementsByClassName("cleardefault", "input"); 
    for (var i=0; i<elems.length; i++) {
	if (elems[i].type == "text") {
	    elems[i].defaultValue = elems[i].value;
	    YAHOO.util.Dom.setStyle(elems[i], "color", "#ccc");
	    YAHOO.util.Event.addListener(elems[i], "focus", focusInput);
	    YAHOO.util.Event.addListener(elems[i], "blur", blurInput);
	}
    }
}

function focusInput(e) {
    var obj = e.target;
    if (obj.value == obj.defaultValue) {
	obj.value = "";
    }
    YAHOO.util.Dom.setStyle(obj, "color", "black");
}

function blurInput(e) {
    var obj = e.target;
    if (obj.value == "") {
	obj.value = obj.defaultValue;
	YAHOO.util.Dom.setStyle(obj, "color", "#ccc");
    }
}

YAHOO.util.Event.onDOMReady(initInput);
