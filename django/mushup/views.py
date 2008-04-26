from django.http import HttpResponseRedirect

from django.shortcuts import render_to_response

def index(request):
    args = {'openid': request.session.get('openid')}
    return render_to_response('index.html', args)

def logout(request):
    try:
        del request.session['openid']
    except KeyError:
        pass
    return HttpResponseRedirect('/')
