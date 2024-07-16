package main;

import xadrez.PartidaXadrez;
import xadrez.PecaXadrez;
import xadrez.XadrezPosicao;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PartidaXadrez partidaXadrez = new PartidaXadrez();
        while (true) {
            InterfaceUsuario.mostrarTabuleiro(partidaXadrez.getPecas());
            System.out.println();
            System.out.print("Origem: ");
            XadrezPosicao origem = InterfaceUsuario.lerXadrezPosicao(sc);

            System.out.println();
            System.out.print("Destino: ");
            XadrezPosicao destino = InterfaceUsuario.lerXadrezPosicao(sc);

            PecaXadrez pecaCapturada = partidaXadrez.executarMovimento(origem, destino);
        }
    }
}