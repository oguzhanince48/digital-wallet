package ing.Digital.Wallet.common.rest;

import ing.Digital.Wallet.wallet.controller.response.model.DataResponse;
import ing.Digital.Wallet.wallet.controller.response.model.ErrorResponse;
import ing.Digital.Wallet.wallet.controller.response.model.Response;
import ing.Digital.Wallet.wallet.controller.response.model.ResponseBuilder;

import java.util.List;

public class BaseController {

    public <T> Response<DataResponse<T>> respond(List<T> items) {
        return ResponseBuilder.build(items);
    }

    public <T> Response<DataResponse<T>> respond(List<T> items, int page, int size, Long totalSize) {
        return ResponseBuilder.build(items, page, size, totalSize);
    }

    protected <T> Response<T> respond(T item) {
        return ResponseBuilder.build(item);
    }

    protected Response respond(ErrorResponse errorResponse) {
        return ResponseBuilder.build(errorResponse);
    }
}
