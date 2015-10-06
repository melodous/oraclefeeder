package oraclefeeder.exception;

import oraclefeeder.core.logs.L4j;

public class OracleFeederException extends Exception {

    private static final long serialVersionUID = 1;

    public OracleFeederException(String message, Throwable cause) {
        L4j.getL4j().error(message, cause);
    }

    public OracleFeederException(String message) {
        L4j.getL4j().error(message);
    }

    public OracleFeederException(Throwable cause) {
        L4j.getL4j().error("UNKNOWN ERROR", cause);
    }
}
