package main;

import xadrez.Cor;
import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezPosicao;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class UI {
    // https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // https: // stackoverflow.com/questions/2979383/java-clear-the-console
    public static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static XadrezPosicao lerXadrezPosicao(Scanner sc) {
        try {
            String s = sc.nextLine();
            char coluna = s.charAt(0);
            int linha = Integer.parseInt(s.substring(1));
            return new XadrezPosicao(coluna, linha);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Erro lendo XadrezPosicao. Valores válidos são " +
                    "de a1 até h8");
        }

    }

    public static void mostrarPartida(PartidaXadrez partidaXadrez, List<PecaXadrez> capturadas) {
        mostrarTabuleiro(partidaXadrez.getPecas());
        System.out.println();
        mostrarPecasCapturadas(capturadas);
        System.out.println();
        System.out.println("Turno : " + partidaXadrez.getTurno());

        if(!partidaXadrez.getCheckMate()) {
            System.out.println("Esperando jogador: " + partidaXadrez.getJogadorAtual());
            if(partidaXadrez.getCheck()) {
                System.out.print("CHECK!");
            }
        }
        else {
            System.out.println("CHECKMATE!");
            System.out.println("Winner: " + partidaXadrez.getJogadorAtual());
        }
    }

    public static void mostrarTabuleiro(PecaXadrez[][] pecas) {
        for(int i = 0;i < pecas.length;i++) {
            System.out.print((8 - i) + " ");
            for(int j = 0;j < pecas.length;j++) {
                mostrarPeca(pecas[i][j], false);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void mostrarTabuleiro(PecaXadrez[][] pecas, boolean[][] movimentosPossiveis) {
        for(int i = 0;i < pecas.length;i++) {
            System.out.print((8 - i) + " ");
            for(int j = 0;j < pecas.length;j++) {
                mostrarPeca(pecas[i][j], movimentosPossiveis[i][j]);
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h");
    }

    public static void exibirLogo() {
        System.out.println("   ____ _           _   __  __           _   _           _   _           _   ");
        System.out.println("  / ___| |__   __ _| |_|  \\/  | __ _ ___ | |_| |__   __ _| |_| |__   __ _| |_ ");
        System.out.println(" | |   | '_ \\ / _` | __| |\\/| |/ _` / __|| __| '_ \\ / _` | __| '_ \\ / _` | __|");
        System.out.println(" | |___| | | | (_| | |_| |  | | (_| \\__ \\| |_| | | | (_| | |_| | | | (_| | |_ ");
        System.out.println("  \\____|_| |_|\\__,_|\\__|_|  |_|\\__,_|___/ \\__|_| |_|\\__,_|\\__|_| |_|\\__,_|\\__|");
        System.out.println();
    }




    private static void mostrarPeca(PecaXadrez peca, boolean background) {
        if(background) {
            System.out.print(ANSI_CYAN_BACKGROUND);
        }
        if (peca == null) {
            System.out.print("-" + ANSI_RESET);
        }
        else {
            if (peca.getCor() == Cor.BRANCO) {
                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
            }
            else {
                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    private static void mostrarPecasCapturadas(List<PecaXadrez> capturadas) {
        List<PecaXadrez> branca = capturadas.stream().filter(x -> x.getCor() == Cor.BRANCO).toList();
        List<PecaXadrez> preta = capturadas.stream().filter(x -> x.getCor() == Cor.PRETO).toList();
        System.out.println("Peças capturadas: ");
        System.out.print("Brancas: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(branca.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Pretas: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(preta.toArray()));
        System.out.print(ANSI_RESET);

    }
}
