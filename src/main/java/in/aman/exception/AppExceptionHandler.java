package in.aman.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionHandler {
	
	public ResponseEntity<AppException> handlerSsaWebEx(SsaWebException ex){
		
		AppException appEx = new AppException();
		
		appEx.setExCode("Ex0001");
		appEx.setExDesc(ex.getMessage());
		
		return new ResponseEntity<AppException>(appEx, HttpStatus.INTERNAL_SERVER_ERROR);
		
	}

}
