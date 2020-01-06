from django.core.exceptions import ObjectDoesNotExist
from django.http import JsonResponse
from Server.status_code import StatusCode
from Server.models import *
from rest_framework.utils import json


def create(request, source_username):
    response_data = {"statusCode": StatusCode.SUCCESS}

    try:
        req_body = json.loads(request.body.decode("utf-8"))
        chart_name = req_body["chartName"]
        x_axis = req_body["xAxis"]
        y_axis = req_body["yAxis"]
        source_user = User.objects.get(username=source_username)

        Chart.objects.create(
            source_user=source_user,
            chart_name=chart_name,
            x_axis=x_axis,
            y_axis=y_axis
        )
    except KeyError:
        response_data["statusCode"] = StatusCode.INSUFFICIENT_ARGS
    except Exception as e:
        print(e)
    return JsonResponse(response_data)


def get(request, username):
    response_data = {"statusCode": StatusCode.SUCCESS}

    try:
        charts = list(Chart.objects.filter(source_user=username))
        charts.sort(key=lambda m: m.create_time)

        response_data["content"] = {
            "charts": [{
                "chartName": chart.chart_name,
                "xAxis": chart.x_axis,
                "yAxis": chart.y_axis,
                "createTime": chart.create_time
            } for chart in charts]
        }
    except ObjectDoesNotExist:
        response_data["statusCode"] = StatusCode.NOT_REGISTERED
    return JsonResponse(response_data)
