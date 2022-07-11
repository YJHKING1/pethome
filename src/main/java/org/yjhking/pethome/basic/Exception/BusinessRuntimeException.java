package org.yjhking.pethome.basic.Exception;

/**
 * 业务类异常
 *
 * @author YJH
 */
public class BusinessRuntimeException extends RuntimeException {
    public BusinessRuntimeException() {
    }
    
    public BusinessRuntimeException(String message) {
        super(message);
    }
}
