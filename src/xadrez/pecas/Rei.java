package xadrez.pecas;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;

public class Rei extends PecaXadrez {
    private PartidaXadrez partidaXadrez;

    public Rei(Tabuleiro tabuleiro, Cor cor, PartidaXadrez partidaXadrez) {
        super(tabuleiro, cor);
        this.partidaXadrez = partidaXadrez;
    }

    @Override
    public String toString() {
        return "R";
    }

    @Override
    public boolean[][] movimentosPossiveis() {
        boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

        Posicao p = new Posicao(0, 0);

        // acima

        p.setValores(posicao.getLinha() - 1, posicao.getColuna());
        if(getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // abaixo

        p.setValores(posicao.getLinha() + 1, posicao.getColuna());
        if(getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // esquerda
        p.setValores(posicao.getLinha(), posicao.getColuna() - 1);
        if(getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // direita

        p.setValores(posicao.getLinha(), posicao.getColuna() + 1);
        if(getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // noroeste

        p.setValores(posicao.getLinha() - 1, posicao.getColuna() - 1);
        if(getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // nordeste

        p.setValores(posicao.getLinha() - 1, posicao.getColuna() + 1);
        if(getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudoeste

        p.setValores(posicao.getLinha() + 1, posicao.getColuna() - 1);
        if(getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // sudeste

        p.setValores(posicao.getLinha() + 1, posicao.getColuna() + 1);
        if(getTabuleiro().existePosicao(p) && podeMover(p)) {
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // movimento especial castling

        if(getContadorMovimentos() == 0 && !partidaXadrez.getCheckMate()) {
            // movimento especial castling rei ao lado da torre
            Posicao posT1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 3);
            if(testRookCastling(posT1)) {
                Posicao posicao1 = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
                Posicao posicao2 = new Posicao(posicao.getLinha(), posicao.getColuna() + 2);
                if(getTabuleiro().peca(posicao1) == null && getTabuleiro().peca(posicao2) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() + 2] = true;
                }
            }

            // movimento especial castling rainha ao lado da torre
            Posicao posT2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 4);
            if(testRookCastling(posT2)) {
                Posicao posicao1 = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
                Posicao posicao2 = new Posicao(posicao.getLinha(), posicao.getColuna() - 2);
                Posicao posicao3 = new Posicao(posicao.getLinha(), posicao.getColuna() - 3);
                if(getTabuleiro().peca(posicao1) == null && getTabuleiro().peca(posicao2) == null && getTabuleiro().peca(posicao3) == null) {
                    mat[posicao.getLinha()][posicao.getColuna() - 2] = true;
                }
            }

        }

        return mat;
    }

    private boolean podeMover(Posicao posicao) {
        PecaXadrez p = (PecaXadrez)getTabuleiro().peca(posicao);
        return p == null || p.getCor() != getCor();
    }

    // Metódo que testa se a torre está apta para realizar o movimento Castling
    private boolean testRookCastling(Posicao posicao) {
        PecaXadrez pecaXadrez = (PecaXadrez) getTabuleiro().peca(posicao);
        return pecaXadrez instanceof Torre && pecaXadrez.getCor() == getCor() && pecaXadrez.getContadorMovimentos() == 0;
    }


}
