package xadrez;

import jogodetabuleiro.Peca;
import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.pecas.*;

import java.security.InvalidParameterException;
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
    private boolean checkMate;
    private PecaXadrez enPassantVulnerable;
    private PecaXadrez promovida;

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

    public boolean getCheckMate() {
        return checkMate;
    }

    public PecaXadrez getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public PecaXadrez getPromovida() {
        return promovida;
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

        PecaXadrez pecaMovida = (PecaXadrez)tabuleiro.peca(destino);

        // movimento especial promoção

        promovida = null;
        if(pecaMovida instanceof Peao) {
            if(pecaMovida.getCor() == Cor.BRANCO && destino.getLinha() == 0 || pecaMovida.getCor() == Cor.PRETO && destino.getLinha() == 7) {
                promovida = (PecaXadrez) tabuleiro.peca(destino);
                promovida = substituirPecaPromovida("RA");
            }
        }

        // check = (testCheck(oponente(jogadorAtual))) ? true : false;

        check = testCheck(oponente(jogadorAtual));
        
        if(testCheckMate(oponente(jogadorAtual))) {
            checkMate = true;
        }
        else {
            proximoTurno();
        }

        // movimento especial en passant

        if(pecaMovida instanceof Peao && (destino.getLinha() == origem.getLinha() - 2 || destino.getLinha() == origem.getLinha() + 2)) {
            enPassantVulnerable = pecaMovida;
        }
        else {
            enPassantVulnerable = null;
        }
        return (PecaXadrez) pecaCapturada;
    }

    public PecaXadrez substituirPecaPromovida(String tipo) {
        if(promovida == null) {
            throw new IllegalStateException("Não há peça para ser promovida. ");
        }
        if(!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("RA")) {
            throw new InvalidParameterException("Tipo inválido para promoção. ");
        }

        Posicao pos = promovida.getXadrezPosicao().posicionar();
        Peca p = tabuleiro.removerPeca(pos);
        pecasNoTabuleiro.remove(p);
        PecaXadrez novaPeca = novaPeca(tipo, promovida.getCor());
        tabuleiro.lugarPeca(novaPeca, pos);
        pecasNoTabuleiro.add(novaPeca);

        return novaPeca;
    }

    private PecaXadrez novaPeca(String tipo, Cor cor) {
        if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
        if(tipo.equals("C")) return new Cavalo(tabuleiro, cor);
        if(tipo.equals("T")) return new Torre(tabuleiro, cor);
        return new Rainha(tabuleiro, cor);
    }

    private Peca mover(Posicao origem, Posicao destino) {
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(origem);
        p.incrementarMovimento();
        Peca pecaCapturada = tabuleiro.removerPeca(destino);
        tabuleiro.lugarPeca(p, destino);
        if(pecaCapturada != null) {
            pecasNoTabuleiro.remove(pecaCapturada);
            pecasCapturadas.add(pecaCapturada);
        }

        // movimento especial castling rei ao lado da torre

        if(p instanceof  Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
            tabuleiro.lugarPeca(torre, destinoT);
            torre.incrementarMovimento();
        }

        // movimento especial castling rainha ao lado da torre

        if(p instanceof  Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(origemT);
            tabuleiro.lugarPeca(torre, destinoT);
            torre.incrementarMovimento();
        }

        // movimento especial en passant

        if(p instanceof Peao) {
            if(origem.getColuna() != destino.getColuna() && pecaCapturada == null) {
                Posicao posicaoPeao;
                if(p.getCor() == Cor.BRANCO) {
                    posicaoPeao = new Posicao(destino.getLinha() + 1, destino.getColuna());
                }
                else {
                    posicaoPeao = new Posicao(destino.getLinha() - 1, destino.getColuna());
                }
                pecaCapturada = tabuleiro.removerPeca(posicaoPeao);
                pecasCapturadas.add(pecaCapturada);
                pecasNoTabuleiro.remove(pecaCapturada);
            }
        }
        return pecaCapturada;
    }

    private void desfazerMovimento(Posicao origem, Posicao destino, Peca pecaCapturada) {
        PecaXadrez p = (PecaXadrez) tabuleiro.removerPeca(destino);
        p.decrementarMovimento();
        tabuleiro.lugarPeca(p, origem);

        if(pecaCapturada != null) {
            tabuleiro.lugarPeca(pecaCapturada, destino);
            pecasCapturadas.remove(pecaCapturada);
            pecasNoTabuleiro.add(pecaCapturada);
        }

        // movimento especial castling rei ao lado da torre

        if(p instanceof  Rei && destino.getColuna() == origem.getColuna() + 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() + 3);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() + 1);
            PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
            tabuleiro.lugarPeca(torre, origemT);
            torre.decrementarMovimento();
        }

        // movimento especial castling rainha ao lado da torre

        if(p instanceof  Rei && destino.getColuna() == origem.getColuna() - 2) {
            Posicao origemT = new Posicao(origem.getLinha(), origem.getColuna() - 4);
            Posicao destinoT = new Posicao(origem.getLinha(), origem.getColuna() - 1);
            PecaXadrez torre = (PecaXadrez)tabuleiro.removerPeca(destinoT);
            tabuleiro.lugarPeca(torre, origemT);
            torre.decrementarMovimento();
        }

        // movimento especial en passant

        if(p instanceof Peao) {
            if(origem.getColuna() != destino.getColuna() && pecaCapturada == enPassantVulnerable) {
                Posicao posicaoPeao;
                PecaXadrez peao = (PecaXadrez) tabuleiro.removerPeca(destino);
                if(p.getCor() == Cor.BRANCO) {
                    posicaoPeao = new Posicao(3, destino.getColuna());
                }
                else {
                    posicaoPeao = new Posicao(4, destino.getColuna());
                }
                tabuleiro.lugarPeca(peao, posicaoPeao);
            }
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

    private boolean testCheckMate(Cor cor) {
        if(!testCheck(cor)) return false;

        List<Peca> lista = pecasNoTabuleiro.stream().filter(x -> ((PecaXadrez)x).getCor() == cor).toList();
        for(Peca p : lista) {
            boolean[][] mat = p.movimentosPossiveis();

            for(int i = 0;i < tabuleiro.getLinhas();i++) {
                for(int j = 0;j < tabuleiro.getColunas();j++) {
                    if(mat[i][j]) {
                        Posicao origem = ((PecaXadrez)p).getXadrezPosicao().posicionar();
                        Posicao destino = new Posicao(i, j);
                        Peca pecaCapturada = mover(origem, destino);
                        boolean testCheck = testCheck(cor);
                        desfazerMovimento(origem, destino, pecaCapturada);
                        if(!testCheck) return false;
                    }
                }
            }
        }
        return  true;
    }

    private void novoLugarPeca(char coluna, int linha, PecaXadrez peca) {
        tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).posicionar());
        pecasNoTabuleiro.add(peca);
    }

    private void configuracaoInicial() {
        novoLugarPeca('a', 1, new Torre(tabuleiro, Cor.BRANCO));
        novoLugarPeca('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        novoLugarPeca('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
        novoLugarPeca('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
        novoLugarPeca('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
        novoLugarPeca('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
        novoLugarPeca('h', 1, new Torre(tabuleiro, Cor.BRANCO));
        novoLugarPeca('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
        novoLugarPeca('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        novoLugarPeca('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        novoLugarPeca('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        novoLugarPeca('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        novoLugarPeca('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        novoLugarPeca('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        novoLugarPeca('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
        novoLugarPeca('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

        novoLugarPeca('a', 8, new Torre(tabuleiro, Cor.PRETO));
        novoLugarPeca('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
        novoLugarPeca('c', 8, new Bispo(tabuleiro, Cor.PRETO));
        novoLugarPeca('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
        novoLugarPeca('f', 8, new Bispo(tabuleiro, Cor.PRETO));
        novoLugarPeca('d', 8, new Rainha(tabuleiro, Cor.PRETO));
        novoLugarPeca('h', 8, new Torre(tabuleiro, Cor.PRETO));
        novoLugarPeca('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
        novoLugarPeca('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
        novoLugarPeca('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
        novoLugarPeca('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
        novoLugarPeca('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
        novoLugarPeca('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
        novoLugarPeca('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
        novoLugarPeca('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
        novoLugarPeca('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
    }
}
