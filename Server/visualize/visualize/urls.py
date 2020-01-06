"""visualize URL Configuration

The `urlpatterns` list routes URLs to views. For more information please see:
    https://docs.djangoproject.com/en/2.2/topics/http/urls/
Examples:
Function views
    1. Add an import:  from my_app import views
    2. Add a URL to urlpatterns:  path('', views.home, name='home')
Class-based views
    1. Add an import:  from other_app.views import Home
    2. Add a URL to urlpatterns:  path('', Home.as_view(), name='home')
Including another URLconf
    1. Import the include() function: from django.urls import include, path
    2. Add a URL to urlpatterns:  path('blog/', include('blog.urls'))
"""
from django.conf.urls import url, include
from django.contrib import admin

from Server.api import user, chart

urlpatterns = [
    url(r'^admin/', admin.site.urls),

    url(r'^user/login$', user.login),
    url(r'^user/register$', user.register),
    url(r'^user/update$', user.update),

    url(r'^user/(?P<username>[\w.@+-]+)$', user.get),
    url(r'^user/(?P<source_username>[\w.@+-]+)/charts/create$', chart.create),
    url(r'^user/(?P<username>[\w.@+-]+)/charts$', chart.get),
]
