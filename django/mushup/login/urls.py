from django.conf.urls.defaults import *

urlpatterns = patterns(
    'mushup.login.views',
    (r'^$', 'startOpenID'),
    (r'^finish/$', 'finishOpenID'),
    (r'^xrds/$', 'rpXRDS'),
)
