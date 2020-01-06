from django.core.exceptions import ObjectDoesNotExist
from django.db import IntegrityError
from django.http import JsonResponse
from Server.status_code import StatusCode
from Server.models import *

import json


def get(request, username):
    response_data = {"statusCode": StatusCode.SUCCESS}
    try:
        user = User.objects.get(username=username)

        response_data["content"] = {
            "username": user.username,
        }
    except KeyError:
        response_data["statusCode"] = StatusCode.INSUFFICIENT_ARGS
    except ObjectDoesNotExist:
        response_data["statusCode"] = StatusCode.NOT_REGISTERED
    return JsonResponse(response_data)


def register(request):
    response_data = {"statusCode": StatusCode.SUCCESS}

    try:
        req_body = json.loads(request.body.decode("utf-8"))
        username = req_body["username"]
        password = req_body["password"]

        User.objects.create(
            username=username,
            password=password,
        )
    except KeyError:
        response_data["statusCode"] = StatusCode.INSUFFICIENT_ARGS
    except IntegrityError:
        response_data["statusCode"] = StatusCode.ALREADY_REGISTERED
    return JsonResponse(response_data)


def login(request):
    response_data = {"statusCode": StatusCode.SUCCESS}

    try:
        req_body = json.loads(request.body.decode("utf-8"))
        username = req_body["username"]
        password = req_body["password"]

        user = User.objects.get(username=username, password=password)
    except KeyError:
        response_data["statusCode"] = StatusCode.INSUFFICIENT_ARGS
    except ObjectDoesNotExist:
        response_data["statusCode"] = StatusCode.VALIDATION_ERR
    return JsonResponse(response_data)


def update(request):
    response_data = {"statusCode": StatusCode.SUCCESS}

    try:
        req_body = json.loads(request.body.decode("utf-8"))
        username = req_body["username"]
        password = req_body["password"]

        user = User.objects.get(username=username)
        user.password = password
        user.save()
    except KeyError:
        response_data["statusCode"] = StatusCode.INSUFFICIENT_ARGS
    except ObjectDoesNotExist:
        response_data["statusCode"] = StatusCode.NOT_REGISTERED
    return JsonResponse(response_data)
