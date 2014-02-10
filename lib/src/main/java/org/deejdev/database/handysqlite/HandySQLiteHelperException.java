package org.deejdev.database.handysqlite;

public class HandySQLiteHelperException extends RuntimeException {
    public HandySQLiteHelperException() {
        super();
    }

    public HandySQLiteHelperException(String detailMessage) {
        super(detailMessage);
    }

    public HandySQLiteHelperException(String detailMessage, Throwable cause) {
        super(detailMessage, cause);
    }

    public HandySQLiteHelperException(Throwable cause) {
        super(cause);
    }
}
