package main;

import jogodetabuleiro.Posicao;
import jogodetabuleiro.Tabuleiro;
import xadrez.PartidaXadrez;

public class Main {
    public static void main(String[] args) {
        PartidaXadrez partidaXadrez = new PartidaXadrez();
        InterfaceUsuario.mostrarTabuleiro(partidaXadrez.getPecas());
    }
}