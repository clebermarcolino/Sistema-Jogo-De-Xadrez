package main;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezException;
import xadrez.XadrezPosicao;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PartidaXadrez partidaXadrez = new PartidaXadrez();
        do {
            try {
                InterfaceUsuario.limparTela();
                InterfaceUsuario.mostrarPartida(partidaXadrez);
                System.out.println();
                System.out.print("Origem: ");
                XadrezPosicao origem = InterfaceUsuario.lerXadrezPosicao(sc);

                boolean[][] movimentosPossiveis = partidaXadrez.movimentosPossiveis(origem);
                InterfaceUsuario.limparTela();
                InterfaceUsuario.mostrarTabuleiro(partidaXadrez.getPecas(), movimentosPossiveis);

                System.out.println();
                System.out.print("Destino: ");
                XadrezPosicao destino = InterfaceUsuario.lerXadrezPosicao(sc);

                PecaXadrez pecaCapturada = partidaXadrez.executarMovimento(origem, destino);
            } catch (XadrezException | InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        } while (true);
    }
}