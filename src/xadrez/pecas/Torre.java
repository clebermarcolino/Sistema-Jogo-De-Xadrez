package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaXadrez;

public class Torre extends PecaXadrez {

    public Torre(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }

    @Override
    public String toString() {
        return "T";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // above
        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() - 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // left
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() - 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // right
        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setColuna(p.getColuna() + 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // below
        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        while (getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
            p.setLinha(p.getLinha() + 1);
        }
        if(getTabuleiro().existePosicao(p) && haUmaPecaOponente(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }
        return mat;
    }
}
