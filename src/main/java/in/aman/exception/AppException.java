package in.aman.exception;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class AppException {

	private String exCode;
	
	private String exDesc;
	
	private LocalDateTime date;
	
	
}
