package algoritmo;

import java.awt.Point;

public class SensoresLadrao {
    private int[] visaoIdentificacao;
    private int[] ambienteOlfatoLadrao;
    private int[] ambienteOlfatoPoupador;
    private Point posicao;
    private int numeroDeMoedas;

    public SensoresLadrao() {
    }

    public int[] getAmbienteOlfatoLadrao() {
        return this.ambienteOlfatoLadrao;
    }

    public void setAmbienteOlfatoLadrao(int[] ambienteOlfatoEquipe) {
        this.ambienteOlfatoLadrao = ambienteOlfatoEquipe;
    }

    public int[] getAmbienteOlfatoPoupador() {
        return this.ambienteOlfatoPoupador;
    }

    public void setAmbienteOlfatoPoupador(int[] ambienteOlfatoOponente) {
        this.ambienteOlfatoPoupador = ambienteOlfatoOponente;
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