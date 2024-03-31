package com.b2b.code.Exception;

import lombok.experimental.StandardException;

public class ApplicationExceptions {

    @StandardException
    public static class DeviceNotAvailableException extends RuntimeException { }
    @StandardException
    public static class DeviceAlreadyBlockedException extends RuntimeException { }
    @StandardException
    public static class DeviceNeverBlockedException extends RuntimeException { }
    @StandardException
    public static class UnauthorizedException extends RuntimeException { }
}

