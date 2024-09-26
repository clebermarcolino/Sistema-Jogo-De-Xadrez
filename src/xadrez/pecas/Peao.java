package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.Cor;

public class Peao extends PecaXadrez {
    private PartidaXadrez partidaXadrez;
    public Peao(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
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

            // movimento especial en passant branco

            if(posicao.getLinha() == 3) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if(getTabuleiro().existePosicao(esquerda) && haUmaPecaOponente(esquerda) &&
                        getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulnerable()) {
                    mat[esquerda.getLinha() - 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);

                if(getTabuleiro().existePosicao(direita) && haUmaPecaOponente(direita) &&
                        getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulnerable()) {
                    mat[direita.getLinha() - 1][direita.getColuna()] = true;
                }
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

            // movimento especial en passant preto

            if(posicao.getLinha() == 4) {
                Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                if(getTabuleiro().existePosicao(esquerda) && haUmaPecaOponente(esquerda) &&
                        getTabuleiro().peca(esquerda) == partidaXadrez.getEnPassantVulnerable()) {
                    mat[esquerda.getLinha() + 1][esquerda.getColuna()] = true;
                }

                Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);

                if(getTabuleiro().existePosicao(direita) && haUmaPecaOponente(direita) &&
                        getTabuleiro().peca(direita) == partidaXadrez.getEnPassantVulnerable()) {
                    mat[direita.getLinha() + 1][direita.getColuna()] = true;
                }
            }
        }

        return mat;
    }

    @Override
    public String toString() {
        return "P";
    }
}
