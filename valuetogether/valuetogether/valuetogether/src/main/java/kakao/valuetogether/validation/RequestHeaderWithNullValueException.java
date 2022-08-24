package kakao.valuetogether.validation;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.server.ResponseStatusException;

public class RequestHeaderWithNullValueException extends ResponseStatusException {
    private static final long serialVersionUID = -1963867886339005041L;

    public RequestHeaderWithNullValueException(String headerName, MethodParameter parameter) {
        super(HttpStatus.BAD_REQUEST, new MissingRequestHeaderException(headerName, parameter).getMessage());
    }

}