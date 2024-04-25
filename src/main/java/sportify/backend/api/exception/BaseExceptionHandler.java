package sportify.backend.api.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import sportify.backend.api.config.Constants;
import sportify.backend.api.exception.response.ResponseStatusDTO;
import sportify.backend.api.pagination.CustomPage;
import sportify.backend.api.util.CommonUtil;

@ControllerAdvice
public class BaseExceptionHandler {

//    private final Logger LOGGER = LoggerFactory.getLogger(BaseExceptionHandler.class);

//    @Autowired
//    private LogManager logManager;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomPage<String>> handleErrors(Exception ex, WebRequest request) throws Exception {
        ResponseStatusDTO responseStatus = getErrorResponseByCode(ex.getMessage(),ex.getClass().getName(),ex);
        HttpStatus httpStatus = CommonUtil.getHttpStatusByException(ex);
        //Sending Error Message To Kafka
//        logManager.publishLogToKafka(responseStatus.getUic(),responseStatus.getCode(),responseStatus.toString());
        return new ResponseEntity<>(new CustomPage<>(null, responseStatus), httpStatus);
    }

    private ResponseStatusDTO getErrorResponseByCode(String errorCode, String exceptionClassName, Exception ex){
        String errorMessage= ex.getMessage() ;
        ResponseStatusDTO responseStatusDTO = new ResponseStatusDTO(CommonUtil.getRandomUUID(16), Constants.DEFAULT_ERROR_MESSAGE_CODE,errorMessage,null) ;
        try{
            return CommonUtil.getStatusObject(errorCode,ex);
        }catch(Exception exp){
            return responseStatusDTO;
        }
    }
}
