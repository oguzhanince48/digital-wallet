package ing.Digital.Wallet.wallet.controller.response.model;

public class Response<T> {

    private ErrorResponse errors;
    private T data;

    public Response() {
    }

    public Response(ErrorResponse errors) {
        this.errors = errors;
    }

    public Response(T data) {
        this.data = data;
    }

    public ErrorResponse getErrors() {
        return errors;
    }

    public T getData() {
        return data;
    }
}
