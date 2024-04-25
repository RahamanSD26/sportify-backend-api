package sportify.backend.api.util;

import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.expression.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import sportify.backend.api.config.Constants;
import sportify.backend.api.domain.log.LogOperation;
import sportify.backend.api.dto.BaseEntityDTO;
import sportify.backend.api.exception.response.ResponseStatusDTO;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommonUtil {

    private static MessageSource errorMessageSource ;

    private static MessageSource successMessageSource ;

    @Autowired
    public CommonUtil(MessageSource errorMessageSource,MessageSource successMessageSource) {
        CommonUtil.errorMessageSource = errorMessageSource;
        CommonUtil.successMessageSource = successMessageSource;
    }

    public String validateEntityId(String id) throws Exception {
        if(id==null){
            throw new Exception("ERR_ADMIN_0017") ;
        }
        id = id.trim() ;
        if(id.length()<4){
            throw new Exception("ERR_ADMIN_0017") ;
        }
        return id ;
    }

    public Boolean validateBaseDto(BaseEntityDTO dto) throws Exception {
        if(dto.getId()!=null){
            throw new Exception("ERR_ADMIN_0052") ;
        }
        if(dto.getCreatedOn()!=null){
            throw new Exception("ERR_ADMIN_0053") ;
        }
        if(dto.getLastUpdatedOn()!=null){
            throw new Exception("ERR_ADMIN_0054") ;
        }
        if(dto.getCreatedBy()!=null){
            throw new Exception("ERR_ADMIN_0055") ;
        }
        if(dto.getLastUpdatedBy()!=null){
            throw new Exception("ERR_ADMIN_0056") ;
        }
        if(dto.getIsActive()!=null){
            throw new Exception("ERR_ADMIN_0057") ;
        }
        if(dto.getGuid()!=null){
            throw new Exception("ERR_ADMIN_0063") ;
        }
        return true ;
    }

    public Boolean validateBaseDto(BaseEntityDTO dto, Map<String,String> httpHeadersMap) throws Exception {
        if(dto.getId()!=null){
            throw new Exception("ERR_ADMIN_0052") ;
        }
        if(dto.getCreatedOn()!=null){
            throw new Exception("ERR_ADMIN_0053") ;
        }
        if(dto.getLastUpdatedOn()!=null){
            throw new Exception("ERR_ADMIN_0054") ;
        }
        if(dto.getCreatedBy()!=null){
            throw new Exception("ERR_ADMIN_0055") ;
        }
        if(dto.getLastUpdatedBy()!=null){
            throw new Exception("ERR_ADMIN_0056") ;
        }
        if(dto.getIsActive()!=null){
            throw new Exception("ERR_ADMIN_0057") ;
        }
        if(dto.getGuid()!=null){
            throw new Exception("ERR_ADMIN_0063") ;
        }
        validateDtoOperation(dto,null,false,httpHeadersMap);
        return true ;
    }

    public Map<String,String> getHeaderMap(MultiValueMap<String, String> httpHeaders){
        Map<String,String> headerMap = new HashMap<>() ;
        httpHeaders.forEach((key, value) -> {
            headerMap.put(key,value.get(0).trim()) ;
            //System.out.println("Key="+key+", Value="+value.get(0).trim());
        });
        headerMap.put("LogOperation", LogOperation.NOTHING.name()) ;
        return headerMap;
    }

    public static String getCustomGeneratedId(String prefix, Integer partNumberDigitCount, Long serialNumber) throws Exception {
        String generatedId=null ;
        Integer serialNumberDigitCount=getDigitsCountFromInteger(serialNumber) ;
        if(prefix!=null){
            prefix=prefix.trim();
            if(prefix.length()==2){
                if(partNumberDigitCount>2){
                    generatedId=prefix ;
                    for(int i=1; i<=partNumberDigitCount-serialNumberDigitCount; i++){
                        generatedId = generatedId + Constants.NUMBER_CONSTANT_ZERO;
                    }
                    generatedId=generatedId+serialNumber ;
                    return generatedId;
                }else{
                    throw new Exception("ERR_ADMIN_0060");
                }
            }else{
                throw new Exception("ERR_ADMIN_0059");
            }
        }else{
            throw new Exception("ERR_ADMIN_0058");
        }
    }

    public static String getCustomGeneratedYearMonthId(String prefix, Integer partNumberDigitCount, Long serialNumber) throws Exception {
        String generatedId=null ;
        Integer serialNumberDigitCount=getDigitsCountFromInteger(serialNumber) ;
        if(prefix!=null){
            prefix=prefix.trim();
            if(prefix.length()>=2){
                if(partNumberDigitCount>2){
                    generatedId=prefix ;//PrefixYearMonth
                    for(int i=1; i<=partNumberDigitCount-serialNumberDigitCount; i++){
                        generatedId = generatedId + Constants.NUMBER_CONSTANT_ZERO;
                    }
                    generatedId=generatedId+serialNumber ;
                    return generatedId;
                }else{
                    throw new Exception("ERR_ADMIN_0060");
                }
            }else{
                throw new Exception("ERR_ADMIN_0059");
            }
        }else{
            throw new Exception("ERR_ADMIN_0058");
        }
    }

    public static Integer getDigitsCountFromInteger(Long number){
        Integer count = 0 ;
        while (number != 0) {
            number /= 10; ++count;
        }
        return count;
    }

    public static Integer getLimitForIdByPartNumberCount(Integer idPartNumberCount) throws Exception {
        Integer limit=1;
        if(idPartNumberCount<1){
            throw new Exception("ERR_ADMIN_0094");
        }
        for(int i=1; i<=idPartNumberCount; i++){
            limit = limit*10;
        }
        return limit-1;
    }

    public static String validateStringByRegexWithErrorAndReturn(String text, String regex, String errorCode) throws Exception {
        if(text!=null){
            text = text.trim() ;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(text);
            if(matcher.find()){
                return text ;
            }else{
                throw new Exception(errorCode);
            }
        }
        throw new Exception(errorCode);
    }

    public static String getYearMonthIdFromCurrentDate(String prefix){
        LocalDate currentDate = LocalDate.now();
        String currentYear=String.valueOf(currentDate.getYear());
        String currentMonth=String.valueOf(currentDate.getMonth().getValue());
        if(currentMonth.length()==1){
            currentMonth= Constants.NUMBER_CONSTANT_ZERO+currentMonth;
        }
        return prefix+currentYear+currentMonth ;
    }

    public static ResponseStatusDTO getStatusObject(String statusCode, Exception ex){
        ResponseStatusDTO responseDTO = new ResponseStatusDTO();
        String statusMessage = Constants.BLANK_CONSTANT;
        boolean isKnownMessage=true;
        statusCode=getAdjustedStatusCode(statusCode.trim());

        //Setting Default Messages
        if(statusCode.startsWith(Constants.SUCCESS_CODE_PREFIX)){
            statusMessage = successMessageSource.getMessage(Constants.DEFAULT_SUCCESS_MESSAGE_CODE,null, getPreferredLocale());
            responseDTO = new ResponseStatusDTO(Constants.DEFAULT_SUCCESS_MESSAGE_CODE,statusMessage,null) ;
        }else if(statusCode.startsWith(Constants.ERROR_CODE_PREFIX)){
            statusMessage = errorMessageSource.getMessage(Constants.DEFAULT_ERROR_MESSAGE_CODE,null, getPreferredLocale());
            responseDTO = new ResponseStatusDTO(Constants.DEFAULT_ERROR_MESSAGE_CODE,statusMessage,null) ;
        }else{
            isKnownMessage=false;
            statusMessage = errorMessageSource.getMessage(Constants.DEFAULT_ERROR_MESSAGE_CODE,null, getPreferredLocale());
            responseDTO = new ResponseStatusDTO(Constants.DEFAULT_ERROR_MESSAGE_CODE,statusMessage,statusCode) ;
        }
        //Setting StatusCode Message
        if(isKnownMessage){
            //Getting Error Code By exception
            if(ex!=null){
                statusCode = getAdjustedStatusCode(getExceptionSpecificErrorCode(ex)) ;
            }
            //Checking Error Code
            if(statusCode!=null){
                statusCode = statusCode.trim() ;
                if(statusCode.length()>3){
                    //Extracting Data
                    String extraData=null;
                    if(statusCode.contains(Constants.COLON_CONSTANT)){
                        String[] dataArray=statusCode.split(Constants.COLON_CONSTANT);
                        statusCode=dataArray[0];
                        extraData=dataArray[1];
                    }
                    //Getting Error & Success Code Messages
                    if(statusCode.startsWith(Constants.ERROR_CODE_PREFIX)){
                        //Getting Specific Error Message By Code
                        statusMessage = errorMessageSource.getMessage(statusCode,null, getPreferredLocale()) ;
                    }else if(statusCode.startsWith(Constants.SUCCESS_CODE_PREFIX)){
                        //Getting Specific Success Message By Code
                        statusMessage = successMessageSource.getMessage(statusCode,null, getPreferredLocale()) ;
                    }else{
                        responseDTO.setActualMessage(statusCode);
                    }
                    //Setting Final Message & Code
                    responseDTO.setCode(statusCode);
                    if(extraData!=null){
                        statusMessage=statusMessage+extraData;
                    }
                    responseDTO.setMessage(statusMessage);
                }
            }
        }
        return responseDTO;
    }

    private static String getAdjustedStatusCode(String statusCode){
        //Adjusting StatusCode
        String subCode = statusCode;
        if(statusCode.contains(Constants.ERROR_CODE_PREFIX)){
            subCode = statusCode.substring(statusCode.indexOf(Constants.ERROR_CODE_PREFIX)+1);
            statusCode="E"+subCode.trim();
        }else{
            subCode = statusCode.substring(statusCode.indexOf(Constants.SUCCESS_CODE_PREFIX)+1);
            statusCode="S"+subCode.trim();
        }
        return statusCode;
    }

    private static String getExceptionSpecificErrorCode(Exception ex){
        String errorCode = ex.getMessage() ;
        if(ex instanceof MethodArgumentNotValidException){
            errorCode = getErrorCodeMessageFromMANVException((MethodArgumentNotValidException)ex) ;
        }
        return errorCode ;
    }

    private static String getErrorCodeMessageFromMANVException(MethodArgumentNotValidException exception){
        final Optional<ObjectError> firstError = exception.getBindingResult().getAllErrors().stream().findFirst();
        if (firstError.isPresent()) {
            return firstError.get().getDefaultMessage();
        }
        return Constants.DEFAULT_ERROR_MESSAGE_CODE ;
    }

    public static HttpStatus getHttpStatusByException(Exception ex){
        return HttpStatus.BAD_REQUEST ;
    }

    public static Locale getPreferredLocale(){
        return Locale.ENGLISH ;
    }

    public static void validateDtoOperation(BaseEntityDTO dto, String data, boolean isOperationReq, Map<String,String> httpHeadersMap) throws Exception {
        String operation = dto.getOperation();
        if(StringUtils.isNotBlank(operation)){
            operation = operation.trim();
            if(!Arrays.stream(Operation.values()).map(pt -> pt.name()).toList().contains(operation)){
                if(StringUtils.isNotBlank(data)){
                    throw new Exception("ERR_ADMIN_0178"+Constants.COLON_CONSTANT+data);
                }
                throw new Exception("ERR_ADMIN_0178");
            }
            //Checking If ADD Operation Is There For Create
            if(LogOperation.valueOf(httpHeadersMap.get(Constants.LOG_OPERATION_HEADER)).equals(LogOperation.CREATE) &&
                    !Operation.valueOf(operation).equals(Operation.ADD)){
                throw new Exception("ERR_ADMIN_0181");
            }
        }else if(isOperationReq){
            if(StringUtils.isNotBlank(data)){
                throw new Exception("ERR_ADMIN_0177"+Constants.COLON_CONSTANT+data);
            }
            throw new Exception("ERR_ADMIN_0177");
        }
        dto.setOperation(operation);
    }

    public static void validateGuid(String guid, String errorCode, String data) throws Exception {
        //Checking Guid
        if(StringUtils.isBlank(guid)){
            if(StringUtils.isNotBlank(data)){
                throw new Exception(errorCode+Constants.COLON_CONSTANT+data);
            }
            throw new Exception(errorCode);
        }
        guid.trim();
    }

    public static String getRandomUUID(Integer digits){
        return UUID.randomUUID().toString().toUpperCase();
    }
    public static Date getFormattedDate(String format,String date){
        try {
            if(StringUtils.isBlank(format)){
                format = Constants.DEFAULT_DATE_FORMAT;
            }
            DateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            if(StringUtils.isNotBlank(date)){
                return sdf.parse(date);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    public static String getFormattedDateString(String format,Date date){
        try {
            if(StringUtils.isBlank(format)){
                format = Constants.DEFAULT_DATE_FORMAT;
            }
            DateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(false);
            if(date!=null){
                return sdf.format(date);
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
