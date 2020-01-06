from django.contrib import admin
from Server import models

# Create your models here.
admin.site.register(models.User)
admin.site.register(models.Chart)
