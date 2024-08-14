package org.framework.router.annotations;

import org.framework.http.utils.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;

@Retention(RetentionPolicy.RUNTIME)
@HttpMapping
public @interface HttpGET {
    String value();
    final HttpMethod httpMethod = HttpMethod.GET;
}
