package org.framework.http.utils;

public enum HttpStatus {
    OK_200(200),
    CREATED_201(201),
    NOT_ALLOWED_405(405),
    NOT_FOUND_404(404),
    INTERNAL_SERVER_ERROR_500(500),
    CONFLICT_409(409);

    private final int statusCode;

    HttpStatus(int i) {
        this.statusCode = i;
    }

    public int getStatusCode() {
        return this.statusCode;
    }
}
