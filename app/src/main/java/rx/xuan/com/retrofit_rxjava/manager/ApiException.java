package rx.xuan.com.retrofit_rxjava.manager;

/**
 * Created by liukun on 16/3/10.
 */
public class ApiException extends RuntimeException {


    /**
     * 请求成功
     */
    public static final int SUCCESSS_REQUEST = 200;
    /**
     * 非法请求
     */
    public static final int ILLEGAL_REQUEST = 400;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     *
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code) {
        String message = "";
        switch (code) {
            case ILLEGAL_REQUEST:
                message = "非法请求";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}

