package com.github.dreamroute.excel.helper.exception;

/**
 * 
 * @author 342252328@qq.com
 *
 */
public class ExcelHelperException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 5738373981580752355L;

    public ExcelHelperException() {
        super();
    }

    public ExcelHelperException(String message) {
        super(message);
    }

    public ExcelHelperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelHelperException(Throwable cause) {
        super(cause);
    }
}
