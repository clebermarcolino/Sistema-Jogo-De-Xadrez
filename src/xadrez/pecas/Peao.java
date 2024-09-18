package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.PecaXadrez;
import xadrez.Cor;

public class Peao extends PecaXadrez {
    public Peao(Tabuleiro tabuleiro, Cor cor) {
        super(tabuleiro, cor);
    }


    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        if(getCor() == Cor.BRANCO) {
            p.setValores(posicao.getLinha() - 1, posicao.getColuna());
            if(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
            if(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().haUmaPeca(p2) && getContadorMovimentos() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
            if(getTabuleiro().existePosicao(p) && haUmaPecaOponente(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
            if(getTabuleiro().existePosicao(p) && haUmaPecaOponente(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }
        else {
            p.setValores(posicao.getLinha() + 1, posicao.getColuna());
            if(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 2, posicao.getColuna());
            Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
            if(getTabuleiro().existePosicao(p) && !getTabuleiro().haUmaPeca(p) && getTabuleiro().existePosicao(p2) && !getTabuleiro().haUmaPeca(p2) && getContadorMovimentos() == 0) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
            if(getTabuleiro().existePosicao(p) && haUmaPecaOponente(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
            p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
            if(getTabuleiro().existePosicao(p) && haUmaPecaOponente(p)) {
                mat[p.getLinha()][p.getColuna()] = true;
            }
        }
    }
}
