package com.android.puy.puymvpjava.net;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import io.reactivex.subscribers.ResourceSubscriber;
import okhttp3.OkHttpClient;
import org.json.JSONException;
import retrofit2.HttpException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;


public abstract class ApiSubscriber<T extends IModel> extends ResourceSubscriber<T> {


    @Override
    public void onError(Throwable e) {
        NetError error = null;
        if (e != null) {
            if (!(e instanceof NetError)) {
                if (e instanceof UnknownHostException) {
                    error = new NetError(e, NetError.NoConnectError);
                } else if (e instanceof JSONException
                        || e instanceof JsonParseException
                        || e instanceof JsonSyntaxException) {
                    error = new NetError(e, NetError.ParseError);
                } else {
                    if (e instanceof SocketTimeoutException) {
                        for (OkHttpClient client : XApi.getInstance().getClientMap().values()) {
                            client.connectionPool().evictAll();
                        }
                    }
                    if (e instanceof HttpException) {
                        error = new NetError(e, NetError.HttpError, ((HttpException) e).code());
                    } else {
                        error = new NetError(e, NetError.OtherError);
                    }
                }
            } else {
                error = (NetError) e;
            }

            if (useCommonErrorHandler()
                    && XApi.getCommonProvider() != null) {
                if (XApi.getCommonProvider().handleError(error)) {        //使用通用异常处理
                    return;
                }
            }

            if (e instanceof HttpException) {
                error.setHttpType(((HttpException) e).code());
                error.setResponse(((HttpException) e).response());
            }
            onFail(error);
        }

    }

    protected abstract void onFail(NetError error);

    @Override
    public void onComplete() {

    }


    protected boolean useCommonErrorHandler() {
        return true;
    }


}
