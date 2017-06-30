package okhttp3;

import android.text.TextUtils;

import java.io.IOException;

/**
 * Description:
 * Author: WoodenTea
 * Date: 2017/3/27
 */
public class ParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();
        if ("GET".equalsIgnoreCase(method)) {
            request = request.newBuilder().url(wrapUrl(request.url())).build();
        } else if ("POST".equalsIgnoreCase(method)) {
            if (canWrapPost(request.body())) {
                request = request.newBuilder().post(wrapPost((FormBody) request.body())).build();
            }
        }

        return chain.proceed(request);
    }

    /**
     * re-wrap url to ignore null value
     *
     * @param httpUrl origin http url
     *
     * @return after wrap http url
     */
    private HttpUrl wrapUrl(HttpUrl httpUrl) {
        int size = httpUrl.querySize();

        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme(httpUrl.scheme())
                .host(httpUrl.host())
                .encodedPath(httpUrl.encodedPath())
                .port(httpUrl.port());
        for (int index = 0; index < size; index++) {
            String value = httpUrl.queryParameterValue(index);
            if (!TextUtils.isEmpty(value)) {
                builder.addEncodedQueryParameter(httpUrl.queryParameterName(index), value);
            }
        }

        return builder.build();
    }

    /**
     * re-wrap body to ignore null value
     *
     * @param body origin body
     *
     * @return after wrap body
     */
    private FormBody wrapPost(FormBody body) {
        int size = body.size();
        FormBody.Builder builder = new FormBody.Builder();
        for (int index = 0; index < size; index++) {
            String value = body.encodedValue(index);
            if (!TextUtils.isEmpty(value)) {
                builder.addEncoded(body.encodedName(index), value);
            }
        }

        return builder.build();
    }

    /**
     * check body is form body
     *
     * @param body origin body
     *
     * @return true is form body
     */
    private boolean canWrapPost(RequestBody body) {
        return body instanceof FormBody;
    }
}
