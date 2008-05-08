function initSearch() {
    YAHOO.util.Event.addListener("search", "submit", search);
}

function responseSuccess(o) {
    try {
	var artists = YAHOO.lang.JSON.parse(unescape(o.responseText));
    }
    catch (e) {
	alert(unescape(o.responseText));
	return;
    }

    var c = document.getElementById("content");
    while (c.hasChildNodes()) {
	c.removeChild(c.firstChild);
    }

    for (var i=0; i<artists.length; i++) {
	var artist = artists[i];
	var a = document.createElement("a");
	a.setAttribute("href", "http://musicbrainz.org/artist/" + artist.id + ".html");
	a.appendChild(document.createTextNode(artist.name));
	var p = document.createElement("p");
	p.appendChild(a)
	c.appendChild(p);
    }
}

function responseFailure(o) {
    alert("failure: " + o.status);
}

function search(e) {
    var searchQuery = e.target.query.value;
    var searchType = e.target.type.value;
    var url = ROOT + "search/";
    if (searchType != "all")
	url += searchType + "/";
    url += encodeURI(searchQuery) + "?format=json";
    var callback = {
	success:responseSuccess,
	failure:responseFailure,
    };
    
    var req = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

YAHOO.util.Event.onDOMReady(initSearch);
