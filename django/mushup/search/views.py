#from django.shortcuts import render_to_response
from django.http import HttpResponse
from django.template import Context, loader

import musicbrainz2.webservice as ws
import time

def search(request, entity, name):
    start = time.time()

    name = unicode(name,'utf-8')
    q = ws.Query()

    af = ws.ArtistFilter(name=name, limit=10)

    results = q.getArtists(af)

    # View code here...
    t = loader.get_template("search.txt")
    c = Context({'name': name,
		 'results': results,
		 'time': time.time() - start})
    return HttpResponse(t.render(c), mimetype="text/plain; charset=utf-8")
