package com.android.puy.puymvpjava.net;


public class NetError extends Exception {
    private Throwable exception;
    private int type = NoConnectError;
    private int httpType;

    public static final int ParseError = 650;   //数据解析异常
    public static final int NoConnectError = 651;   //无连接异常
    public static final int AuthError = 652;   //用户验证异常
    public static final int NoDataError = 653;   //无数据返回异常
    public static final int BusinessError = 654;   //业务异常
    public static final int OtherError = 655;   //其他异常
    public static final int HttpError = 656;   //Http异常

    public NetError(Throwable exception, int type, int httpType) {
        this.exception = exception;
        this.type = type;
        this.httpType = httpType;
    }

    public NetError(Throwable exception, int type) {
        this.exception = exception;
        this.type = type;
    }

    public NetError(String detailMessage, int type) {
        super(detailMessage);
        this.type = type;
    }

    @Override
    public String getMessage() {
        if (exception != null) return exception.getMessage();
        return super.getMessage();
    }

    public int getType() {
        return type;
    }

    public int getHttpType() {
        return httpType;
    }

    public void setHttpType(int httpType) {
        this.httpType = httpType;
    }

    @Override
    public String toString() {
        return "NetError{" +
                "exception=" + exception +
                ", type=" + type +
                ", httpType=" + httpType +
                '}';
    }
}
