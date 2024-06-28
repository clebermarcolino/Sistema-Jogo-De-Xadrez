package jogodetabuleiro;

public class Tabuleiro {
    private int linhas;
    private int colunas;
    private Peca[][] pecas;

    public Tabuleiro(int linhas, int colunas) {
        if(linhas < 1 || colunas < 1) {
            throw new TabuleiroException("Erro ao criar tabuleiro: É necessário que tenha" +
                    " pelo menos 1 linha e 1 uma coluna ");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        this.pecas = new Peca[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Peca peca(int linha, int coluna) {
        if(!existePosicao(linha, coluna)) {
            throw new TabuleiroException("Sem posição no tabuleiro! ");
        }
        return pecas[linha][coluna];
    }

    public Peca peca(Posicao posicao) {
        if(!existePosicao(posicao)) {
            throw new TabuleiroException("Sem posição no tabuleiro! ");
        }
        return pecas[posicao.getLinha()][posicao.getColuna()];
    }

    public void lugarPeca(Peca peca, Posicao posicao) {
        if(haUmaPeca(posicao)) {
            throw new TabuleiroException("Há uma peça na posição " + posicao);
        }
        pecas[posicao.getLinha()][posicao.getColuna()] = peca;
        peca.posicao = posicao;
    }

    public boolean existePosicao(Posicao posicao) {
        return existePosicao(posicao.getLinha(), posicao.getColuna());
    }

    private boolean existePosicao(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    public boolean haUmaPeca(Posicao posicao) {
        if(!existePosicao(posicao)) {
            throw new TabuleiroException("Sem posição no tabuleiro! ");
        }
        return peca(posicao) != null;
    }
}
