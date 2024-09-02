package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidaXadrez {
    private int turno;
    private Tabuleiro tabuleiro;
    private Cor jogadorAtual;
    private List<Peca> pecasNoTabuleiro = new ArrayList<Peca>();
    private List<Peca> pecasCapturadas = new ArrayList<Peca>();
    private boolean check;

    public PartidaXadrez() {
        this.tabuleiro = new Tabuleiro(8 , 8);
        turno = 1;
        jogadorAtual = Cor.BRANCO;
        configuracaoInicial();
    }

    public int getTurno() {
        return turno;
    }



    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    public Cor getJogadorAtual() {
        return jogadorAtual;
    }

    public boolean getCheck() {
        return check;
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

        if(testCheck(jogadorAtual)) {
            desfazerMovimento(origem, destino, pecaCapturada);
            throw new XadrezException("Você não pode se colocar em xeque!");
        }

        // check = (testCheck(oponente(jogadorAtual))) ? true : false;
        check = testCheck(oponente(jogadorAtual));

        proximoTurno();
        return (PecaXadrez) pecaCapturada;
    }

    private Peca mover(Posicao origem, Posicao destino) {
        Peca p = tabuleiro.removerPeca(origem);
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.lugarPeca(p, destino);
        if(pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        Peca p = tabuleiro.removerPeca(destino);
        tabuleiro.lugarPeca(p, origem);

        if(pecaCapturada != null) {
            tabuleiro.lugarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }
    }

    private void validarPosicaoOrigem(Posicao posicao) {
        if(!tabuleiro.haUmaPeca(posicao)) {
            throw new XadrezException("Não existe peça na posição de origem");
        }
        if(jogadorAtual != ((PecaXadrez) tabuleiro.peca(posicao)).getCor()) {
            throw new XadrezException("A peça escolhida não é sua");
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

    public void proximoTurno() {
        turno++;
        jogadorAtual = (jogadorAtual == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private Cor oponente(Cor cor) {
        return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
    }

    private PecaXadrez rei(Cor cor) {
        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).toList();
        for(Peca p : lista) {
            if(p instanceof  Rei) {
                return (PecaXadrez) p;
            }
        }
        throw new IllegalStateException("Não existe o rei da cor " + cor + " no tabuleiro");
    }

    private boolean testCheck(Cor cor) {
        Posicao posicaoRei = rei(cor).getXadrezPosicao().posicionar();
        List<Peca> pecasOponentes = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == oponente(cor)).toList();
        for(Peca p : pecasOponentes) {
            boolean[][] mat = p.movimentosPossiveis();
            if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
                return true;
            }
        }
        return false;
    }


    private void novoLugarPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).posicionar());
        pecasNoTabuleiro.add(peca);
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
