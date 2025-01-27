package org.framework.router.annotations;

import org.framework.http.utils.HttpMethod;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@HttpMapping
public @interface HttpPOST {
    String value();
    final HttpMethod httpMethod = HttpMethod.POST;
}

