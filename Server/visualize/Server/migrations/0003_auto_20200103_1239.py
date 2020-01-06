# Generated by Django 2.2.2 on 2020-01-03 12:39

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('Server', '0002_remove_user_fullname'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='chart',
            name='y_axis',
        ),
        migrations.AddField(
            model_name='chart',
            name='label',
            field=models.CharField(default='', max_length=20),
            preserve_default=False,
        ),
    ]