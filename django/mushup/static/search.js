function initSearch() {
    YAHOO.util.Event.addListener("searchform", "submit", search);
}

function responseSuccess(o) {
    
}

function responseFailure(o) {
    alert("failure: " + o.status);
}

function search(e) {
    var artist = e.target.artistsearch.value;
    var url = "/search/artist/" + encodeURI(artist);
    var callback = {
	success:responseSuccess,
	failure:responseFailure,
    };
    
    var req = YAHOO.util.Connect.asyncRequest('GET', url, callback);
}

YAHOO.util.Event.onDOMReady(initSearch);
