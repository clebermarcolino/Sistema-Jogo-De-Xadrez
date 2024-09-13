package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;

public abstract class PecaXadrez extends Peca {
    private Cor cor;
    private int contadorMovimentos;

    public PecaXadrez(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro);
        this.cor = cor;
    }

    protected boolean haUmaPecaOponente(Posicao posicao) {
        PecaXadrez p = (PecaXadrez) getTabuleiro().peca(posicao);
        return p != null && p.getCor() != cor;
    }

    public XadrezPosicao getXadrezPosicao() {
        return XadrezPosicao.daPosicao(posicao);
    }

    public Cor getCor() {
        return cor;
    }

    public int getContadorMovimentos() {
        return contadorMovimentos;
    }

    public void incrementarMovimento() {
        contadorMovimentos++;
    }

    public void decrementarMovimento() {
        contadorMovimentos--;
    }
}
