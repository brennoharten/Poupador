package algoritmo;

import java.awt.Point;

public class SensoresPoupador {
    private int[] visaoIdentificacao;
    private int[] ambienteOlfatoPoupador;
    private int[] ambienteOlfatoLadrao;
    private Point posicao;
    private int numeroDeMoedas;
    private int numeroDeMoedasBanco;
    private int numeroJogadasImunes;

    public SensoresPoupador() {
    }

    public int getNumeroJogadasImunes() {
        return this.numeroJogadasImunes;
    }

    public void setNumeroJogadasImunes(int numeroJogadasImunes) {
        this.numeroJogadasImunes = numeroJogadasImunes;
    }

    public int getNumeroDeMoedasBanco() {
        return this.numeroDeMoedasBanco;
    }

    public void setNumeroDeMoedasBanco(int numeroDeMoedasBanco) {
        this.numeroDeMoedasBanco = numeroDeMoedasBanco;
    }

    public int[] getAmbienteOlfatoPoupador() {
        return this.ambienteOlfatoPoupador;
    }

    public void setAmbienteOlfatoPoupador(int[] ambienteOlfatoEquipe) {
        this.ambienteOlfatoPoupador = ambienteOlfatoEquipe;
    }

    public int[] getAmbienteOlfatoLadrao() {
        return this.ambienteOlfatoLadrao;
    }

    public void setAmbienteOlfatoLadrao(int[] ambienteOlfatoOponente) {
        this.ambienteOlfatoLadrao = ambienteOlfatoOponente;
    }

    public int getNumeroDeMoedas() {
        return this.numeroDeMoedas;
    }

    public void setNumeroDeMoedas(int nivelEnergia) {
        this.numeroDeMoedas = nivelEnergia;
    }

    public Point getPosicao() {
        return this.posicao;
    }

    public void setPosicao(Point posicao) {
        this.posicao = posicao;
    }

    public int[] getVisaoIdentificacao() {
        return this.visaoIdentificacao;
    }

    public void setVisaoIdentificacao(int[] visaoIdentificacao) {
        this.visaoIdentificacao = visaoIdentificacao;
    }
}
