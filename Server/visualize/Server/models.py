from django.db import models
from django.utils import timezone


class User(models.Model):
    username = models.CharField(max_length=20, primary_key=True)
    password = models.CharField(max_length=64)

    create_time = models.DateTimeField(editable=False)
    update_time = models.DateTimeField()

    def save(self, *args, **kwargs):
        if not self.create_time:
            self.create_time = timezone.now()
        self.update_time = timezone.now()
        super().save(*args, **kwargs)

    def __str__(self):
        return self.username


class Chart(models.Model):
    chart_name = models.CharField(max_length=20)
    x_axis = models.CharField(max_length=64)
    y_axis = models.CharField(max_length=64)
    chart_style = models.CharField(max_length=20, blank=True)
    source_user = models.ForeignKey('User', related_name="source_user", on_delete=models.CASCADE)

    create_time = models.DateTimeField(editable=False)

    def save(self, *args, **kwargs):
        if not self.create_time:
            self.create_time = timezone.now()
        super().save(*args, **kwargs)

    def __str__(self):
        return "{}".format(self.source_user)
