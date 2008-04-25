from django.conf.urls.defaults import *

urlpatterns = patterns('',
    # Example:
    # (r'^mushup/', include('mushup.foo.urls')),
    (r'^test/$', 'mushup.test.views.index'),

    # Uncomment this for admin:
#     (r'^admin/', include('django.contrib.admin.urls')),
)
