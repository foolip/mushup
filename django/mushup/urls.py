from django.conf.urls.defaults import *

urlpatterns = patterns('',
    # Example:
    (r'^$', 'mushup.views.index'),
    (r'^login/', include('mushup.login.urls')),
    (r'^logout/', 'mushup.views.logout'),

    # Uncomment this for admin:
#     (r'^admin/', include('django.contrib.admin.urls')),
)
