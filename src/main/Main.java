package main;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PartidaXadrez partidaXadrez = new PartidaXadrez();
        List<PecaXadrez> capturadas = new ArrayList<PecaXadrez>();
        do {
            try {
                UI.limparTela();
                UI.mostrarPartida(partidaXadrez, capturadas);
                System.out.println();
                System.out.print("Origem: ");
                XadrezPosicao origem = UI.lerXadrezPosicao(sc);

                boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
                UI.limparTela();
                UI.mostrarTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);

                System.out.println();
                System.out.print("Destino: ");
                XadrezPosicao destino = UI.lerXadrezPosicao(sc);

                PecaXadrez pecaCapturada = partidaXadrez.executarMovimento(origem, destino);

                if(pecaCapturada != null) {
                    capturadas.add(pecaCapturada);
                }
                if(partidaXadrez.getPromovida() != null) {
                    System.out.print("Digite a peça a ser promovida (B/C/T/RA): ");
                    String tipo = sc.nextLine().toUpperCase();
                    while (!tipo.equals("B") && !tipo.equals("C") && !tipo.equals("T") && !tipo.equals("RA")) {
                        System.out.print("Valor inválido! Digite o valor da peça a ser promovida (B/C/T/RA): ");
                        tipo = sc.nextLine().toUpperCase();
                    }
                    partidaXadrez.substituirPecaPromovida(tipo);
                }
            } catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        } while (!partidaXadrez.getCheckMate());
        UI.limparTela();
        UI.mostrarPartida(partidaXadrez, capturadas);
    }
}