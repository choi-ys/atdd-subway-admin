package nextstep.subway.common.exception.application;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import nextstep.subway.common.exception.dto.ErrorResponse;
import nextstep.subway.line.exception.LineNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(HttpServletRequest request,
		DataIntegrityViolationException e) {
		final String message = "기본 키 또는 유니크 제약조건에 위배됩니다.";
		return ResponseEntity
			.badRequest()
			.body(new ErrorResponse(message, e.getMessage(), extractRequestedPath(request)));
	}

	@ExceptionHandler(LineNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleLineNotFoundException(HttpServletRequest request,
		LineNotFoundException e) {
		return ResponseEntity
			.status(HttpStatus.NOT_FOUND)
			.body(new ErrorResponse(e.getMessage(), extractRequestedPath(request)));
	}

	private String extractRequestedPath(HttpServletRequest request) {
		return request.getServletPath();
	}

}