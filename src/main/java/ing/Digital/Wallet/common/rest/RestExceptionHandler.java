package ing.Digital.Wallet.common.rest;

import ing.Digital.Wallet.common.exception.WalletApiBusinessException;
import ing.Digital.Wallet.wallet.controller.response.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends BaseController{

    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(WalletApiBusinessException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorResponse> handleWalletApiBusiness(WalletApiBusinessException ex, Locale locale) {
        log.error("An business exception error occurred! Details: ", ex);
        List<String> messageList = retrieveMessage(ex.getKey(), locale, ex.getArgs());
        return new ResponseEntity<>(new ErrorResponse(messageList.getFirst(), messageList.get(1)), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, Locale locale) {
        log.error("An error occurred! Details: ", ex);
        List<String> messageList = retrieveMessage("wallet-api.general.error", locale);
        return new ResponseEntity<>(new ErrorResponse(messageList.getFirst(),messageList.get(1)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private List<String> retrieveMessage(String key, Locale locale, String... args) {
        String message = messageSource.getMessage(key, args, locale);
        return Pattern.compile(";").splitAsStream(message).toList();
    }

}
