package xadrez;

import java.io.Serial;

public class XadrezException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public XadrezException(String msg) {
        super(msg);
    }
}
