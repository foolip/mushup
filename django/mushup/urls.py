from django.conf.urls.defaults import *

urlpatterns = patterns('',
    # Example:
    (r'^$', 'mushup.views.index'),
    (r'^login/', include('mushup.login.urls')),
    (r'^logout/$', 'mushup.views.logout'),
    (r'^search/([^/]*)/([^/]*)/$', 'mushup.search.views.search'),

    # Uncomment this for admin:
#     (r'^admin/', include('django.contrib.admin.urls')),
)
