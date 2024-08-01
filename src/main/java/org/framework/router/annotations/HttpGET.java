package org.framework.router.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URI;

@Retention(RetentionPolicy.RUNTIME)
public @interface HttpGET {
    String value();
}
