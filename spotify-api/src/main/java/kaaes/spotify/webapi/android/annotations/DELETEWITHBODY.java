package kaaes.spotify.webapi.android.annotations;

/* @see https://github.com/square/retrofit/issues/458 */

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import retrofit2.http.HTTP;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface DELETEWITHBODY {
    @HTTP(method = "DELETE", hasBody = true)
    String value();
}
