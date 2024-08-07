package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaXadrez {
    private Tabuleiro tabuleiro;

    public PartidaXadrez() {
        this.tabuleiro = new Tabuleiro(8 , 8);
        configuracaoInicial();
    }

    public PecaXadrez[][] getPecas() {
        PecaXadrez[][] mat = new PecaXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];

        for(int i = 0; i < tabuleiro.getLinhas();i++) {
            for (int j = 0;j < tabuleiro.getColunas();j++) {
                mat[i][j] = (PecaXadrez) tabuleiro.peca(i, j);
            }
        }
        return mat;
    }

    public boolean[][] movimentosPossiveis(XadrezPosicao posicaoOrigem) {
        Posicao posicao = posicaoOrigem.posicionar();
        validarPosicaoOrigem(posicao);
        return tabuleiro.peca(posicao).movimentosPossiveis();
    }

    public PecaXadrez executarMovimento(XadrezPosicao posicaoOrigem, XadrezPosicao localDestino) {
        Posicao origem = posicaoOrigem.posicionar();
        Posicao destino = localDestino.posicionar();
        validarPosicaoOrigem(origem);
        validarPosicaoDestino(origem, destino);
        Peca pecaCapturada = mover(origem, destino);
        return (PecaXadrez) pecaCapturada;
    }

    private Peca mover(Posicao origem, Posicao destino) {
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.lugarPeca(p, destino);
        return pecaCapturada;
    }

    private void validarPosicaoOrigem(Posicao posicao) {
        if(!tabuleiro.haUmaPeca(posicao)) {
            throw new XadrezException("Não existe peça na posição de origem");
        }
        if(!tabuleiro.peca(posicao).haUmMovimentoPossivel()) {
            throw new XadrezException("Não existe movimentos possíveis para a peça escolhida");
        }
    }

    public void validarPosicaoDestino(Posicao origem, Posicao destino) {
        if(!tabuleiro.peca(origem).movimentoPossivel(destino)) {
            throw new XadrezException("A peça escolhida não pode se mover para a posição de destino");
        }
    }

    private void novoLugarPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).posicionar());
    }

    private void configuracaoInicial() {
        novoLugarPeca('c', 1, new Torre(tabuleiro, Cor.BRANCO));
        novoLugarPeca('c', 2, new Torre(tabuleiro, Cor.BRANCO));
        novoLugarPeca('d', 2, new Torre(tabuleiro, Cor.BRANCO));
        novoLugarPeca('e', 2, new Torre(tabuleiro, Cor.BRANCO));
        novoLugarPeca('e', 1, new Torre(tabuleiro, Cor.BRANCO));
        novoLugarPeca('d', 1, new Rei(tabuleiro, Cor.BRANCO));
        novoLugarPeca('c', 7, new Torre(tabuleiro, Cor.PRETO));
        novoLugarPeca('c', 8, new Torre(tabuleiro, Cor.PRETO));
        novoLugarPeca('d', 7, new Torre(tabuleiro, Cor.PRETO));
        novoLugarPeca('e', 7, new Torre(tabuleiro, Cor.PRETO));
        novoLugarPeca('e', 8, new Torre(tabuleiro, Cor.PRETO));
        novoLugarPeca('d', 8, new Rei(tabuleiro, Cor.PRETO));
    }
}
