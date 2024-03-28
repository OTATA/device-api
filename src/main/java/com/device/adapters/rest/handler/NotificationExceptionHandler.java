package com.device.adapters.rest.handler;

import com.device.adapters.rest.dto.ErrorResponse;
import com.device.domain.exception.NotificationException;
import io.micronaut.context.annotation.Requires;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.server.exceptions.ExceptionHandler;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Singleton
@Requires(classes = {NotificationException.class, io.micronaut.core.exceptions.ExceptionHandler.class})
public class NotificationExceptionHandler implements ExceptionHandler<NotificationException, HttpResponse> {

    @Override
    public HttpResponse handle(HttpRequest request, NotificationException exception) {
        return HttpResponse.badRequest(new ErrorResponse(exception.getMessage(), exception.getMessages()));
    }
}
