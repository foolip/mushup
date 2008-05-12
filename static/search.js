/*
 * main search
 */

function initSearch() {
    YAHOO.util.Event.addListener("search", "submit", search);
}
YAHOO.util.Event.onDOMReady(initSearch);

function search(e) {
    // show "loading" thingy
    var c = getClearContent();
    c.appendChild(createLoader("Searching..."));

    // do actual search
    var searchQuery = e.target.query.value;
    var searchType = e.target.type.value;
    var url = ROOT + "search/";
    if (searchType != "all")
	url += searchType + "/";
    url += encodeURI(searchQuery) + "?format=json";
    var callback = {
	success: searchSuccess,
	failure: searchFailure,
	argument: {url: url}
    };

    YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function searchSuccess(o) {
    try {
	var artists = YAHOO.lang.JSON.parse(unescape(o.responseText));
    } catch (e) {
	alert("Invalid JSON from " + o.argument['url'] + " : " + unescape(o.responseText));
	return;
    }

    var c = getClearContent();
    var ids = [];
    for (var i=0; i<artists.length; i++) {
	var artist = artists[i];
	c.appendChild(createArtistResult(artist));
	ids.push(artist.id);
    }

    addInfo(ids);
}

function searchFailure(o) {
    alert(o.argument.url + " failure: " + o.status);
}

/*
 * search augmentation
 */

function addInfo(ids) {
    var url = ROOT + "info?format=json";
    for (var i=0; i<ids.length; i++) {
	var elem = document.getElementById(ids[i]);
	YAHOO.util.Dom.addClass(elem, "loading");
	if (YAHOO.util.Dom.hasClass(elem, "artist")) {
	    url += "&artist=" + elem.id;
	}
    }

    var callback = {
	success: addInfoSuccess,
	failure: addInfoFailure,
	argument: {requested: ids,
		   url: url}
    };
    
    var o = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

function addInfoSuccess(o) {
    try {
	var infos = YAHOO.lang.JSON.parse(unescape(o.responseText));
    } catch (e) {
	alert("Invalid JSON: " + unescape(o.responseText));
	return;
    }

    var requested = o.argument.requested;
    // index returned result by id
    var returned = {};
    for (var i=0; i<infos.length; i++) {
	returned[infos[i].id] = infos[i];
    }
    var missing = [];

    for (var i=0; i<requested.length; i++) {
	var id = requested[i];
	if (returned[id]) {
	    var info = returned[id];
	    var elem = document.getElementById(info.id);
	    if (elem) {
		YAHOO.util.Dom.removeClass(elem, "loading");
		if (info.wikipedia) {
		    var links = YAHOO.util.Dom.getElementsByClassName("links", null, elem)[0];
		    var wpLink = createLinkIcon(info.wikipedia.url,
						"http://en.wikipedia.org/favicon.ico",
						"Wikipedia");
		    links.appendChild(wpLink);

		    if (info.wikipedia.blurb) {
			var blurb = document.createElement("div");
			YAHOO.util.Dom.addClass(blurb, "blurb");
			blurb.appendChild(document.createTextNode(info.wikipedia.blurb));
			blurb.appendChild(document.createTextNode(" \u2026 ["));

			var a = document.createElement("a");
			a.setAttribute("href", info.wikipedia.url);
			a.appendChild(document.createTextNode("more"));
			blurb.appendChild(a);

			blurb.appendChild(document.createTextNode("]"));
			elem.appendChild(blurb);
		    }
		}
	    }
	} else {
	    missing.push(id);
	}
    }

    if (missing.length > 0) {
	addInfo(missing);
    }
}

function addInfoFailure(o) {
    alert(o.argument.url + " failure: " + o.status + ": " + o.statusText);
}

/*
 * helper functions
 */

/* check if the sort name actually adds any information */
function needsSortName(name, sortName) {
    name = name.toLowerCase();
    sortName = sortName.toLowerCase();
    if (name == sortName)
	return false;

    var sortNameParts = sortName.split(/[\s,&]+/);
    for(i in sortNameParts) {
	if (name.indexOf(sortNameParts[i]) == -1) {
	    return true;
	}
    }

    return false;
}

function createLinkIcon(linkUrl, iconUrl, tooltip) {
    var a = document.createElement("a");
    a.setAttribute("href", linkUrl);
    var img = document.createElement("img");
    img.setAttribute("src", iconUrl);
    img.setAttribute("width", 16);
    img.setAttribute("height", 16);
    if (tooltip)
	a.setAttribute("title", tooltip);
    a.appendChild(img);
    return a;
}

function createArtistResult(artist) {
    var div = document.createElement("div");
    div.setAttribute("id", artist.id);
    YAHOO.util.Dom.addClass(div, "searchResult");
    YAHOO.util.Dom.addClass(div, "artist");

    var top = document.createElement("div");
    YAHOO.util.Dom.addClass(top, "top");

    var name = document.createElement("a");
    YAHOO.util.Dom.addClass(name, "name");
    name.setAttribute("href", ROOT + "artist/" + artist.id);
    name.appendChild(document.createTextNode(artist.name));
    top.appendChild(name);

    var hints = [];
    if (needsSortName(artist.name, artist.sortName)) {
	hints.push(artist.sortName);
    }
    if (artist.disambiguation) {
	hints.push(artist.disambiguation);
    }
    if (hints.length > 0) {
	var nameHint = document.createElement("span");
	YAHOO.util.Dom.addClass(nameHint, "nameHint");
	nameHint.appendChild(document.createTextNode(" (" + hints.join("; ") + ")"));
	top.appendChild(nameHint);
    }

    var links = document.createElement("span");
    YAHOO.util.Dom.addClass(links, "links");
    top.appendChild(links);

    var mbLink = createLinkIcon("http://musicbrainz.org/artist/" + artist.id + ".html",
				"/static/musicbrainz.png", "MusicBrainz");
    links.appendChild(mbLink);

    var img = document.createElement("img");
    YAHOO.util.Dom.addClass(img, "loading-indicator");
    img.setAttribute("src", "/static/ajax-loader.gif");
    img.setAttribute("width", 16);
    img.setAttribute("height", 16);
    top.appendChild(img);

    div.appendChild(top);

    return div;
}

function getClearContent() {
    var c = document.getElementById("content");
    while (c.hasChildNodes()) {
	c.removeChild(c.firstChild);
    }
    return c;
}

function createLoader(msg) {
    var load = document.createElement("div");
    YAHOO.util.Dom.addClass(load, "loader"); 
    load.appendChild(document.createTextNode(msg));
    return load;
}
