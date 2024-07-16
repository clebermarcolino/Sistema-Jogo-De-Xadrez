package xadrez;

import jogodetabuleiro.TabuleiroException;

import java.io.Serial;

public class XadrezException extends TabuleiroException {
    @Serial
    private static final long serialVersionUID = 1L;

    public XadrezException(String msg) {
        super(msg);
    }
}
