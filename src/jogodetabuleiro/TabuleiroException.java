package jogodetabuleiro;

import java.io.Serial;

public class TabuleiroException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public TabuleiroException(String msg) {
        super(msg);
    }
}
