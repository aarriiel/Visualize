from enum import IntEnum


class StatusCode(IntEnum):
    SUCCESS = 0
    NETWORK_ERR = 1
    JSON_PARSE_ERR = 2
    VALIDATION_ERR = 3
    INSUFFICIENT_ARGS = 4
    NOT_REGISTERED = 5
    ALREADY_REGISTERED = 6
