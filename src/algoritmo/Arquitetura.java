package algoritmo;

import controle.Constantes;
import controle.Controlador;
import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

public class Arquitetura {
    private int[][] matrizSimulacao = null;
    private int[][] matrizOlfatoPoupador = null;
    private int[][] matrizOlfatoLadrao = null;
    private Controlador controlador;
    private Programa programa;
    private int numeroAgente = -1;
    private int x = -1;
    private int y = -1;
    private int numerodeMoedas;
    private int numerodeMoedasNoBanco;
    private int numeroJogadasImunes;
    private List equipes = new ArrayList();

    public int getNumeroJogadasImunes() {
        return this.numeroJogadasImunes;
    }

    public void setNumeroJogadasImunes(int ticImunes) {
        this.numeroJogadasImunes = ticImunes;
    }

    public int getNumerodeMoedasNoBanco() {
        return this.numerodeMoedasNoBanco;
    }

    public void setNumerodeMoedasNoBanco(int numerodeMoedasNoBanco) {
        this.numerodeMoedasNoBanco = numerodeMoedasNoBanco;
    }

    public Arquitetura(int[][] matrizSimulacao, int[][] matrizOlfatoEquipe1, Controlador controlador, int numeroAgente, Programa programa, List equipes, int[][] matrizOlfatoEquipe2) {
        this.matrizSimulacao = matrizSimulacao;
        this.numeroAgente = numeroAgente;
        this.controlador = controlador;
        this.programa = programa;
        this.matrizOlfatoPoupador = matrizOlfatoEquipe1;
        this.matrizOlfatoLadrao = matrizOlfatoEquipe2;
        this.equipes = equipes;
        this.numerodeMoedas = Constantes.numeroInicialdeMoedas;
        this.numerodeMoedasNoBanco = Constantes.numeroInicialdeMoedas;
        this.numeroJogadasImunes = 0;
        this.getPosicaoAgente();
    }

    private void getPosicaoAgente() {
        boolean achouAgente = false;
        if (this.matrizSimulacao != null) {
            label28:
            for(int i = 0; i < this.matrizSimulacao.length; ++i) {
                for(int j = 0; j < this.matrizSimulacao[0].length; ++j) {
                    if (this.matrizSimulacao[i][j] == this.numeroAgente) {
                        this.x = i;
                        this.y = j;
                        achouAgente = true;
                        break label28;
                    }
                }
            }

            if (!achouAgente) {
                JOptionPane.showMessageDialog((Component)null, "Não foi possível achar o agente de número /'" + this.numeroAgente + "/'!", "Agente Inválido", 0);
            }
        }

    }

    public Point getPosicao() {
        return new Point(this.getX(), this.getY());
    }

    public int getEnergiaRestante() {
        return this.controlador.getEnergiaRestante();
    }

    public void percebeLadrao() {
        SensoresLadrao sensor = new SensoresLadrao();
        sensor.setVisaoIdentificacao(this.percebeAmbienteVisao());
        sensor.setPosicao(this.getPosicao());
        sensor.setNumeroDeMoedas(this.getNumerodeMoedas());
        sensor.setAmbienteOlfatoLadrao(this.percebeAmbienteOlfatoLadrao());
        sensor.setAmbienteOlfatoPoupador(this.percebeAmbienteOlfatoPoupador());
        ((ProgramaLadrao)this.programa).sensor = sensor;
    }

    public void percebePoupador() {
        SensoresPoupador sensor = new SensoresPoupador();
        sensor.setVisaoIdentificacao(this.percebeAmbienteVisao());
        sensor.setPosicao(this.getPosicao());
        sensor.setNumeroDeMoedas(this.getNumerodeMoedas());
        sensor.setNumeroDeMoedasBanco(this.getNumerodeMoedasNoBanco());
        sensor.setNumeroJogadasImunes(this.numeroJogadasImunes);
        sensor.setAmbienteOlfatoPoupador(this.percebeAmbienteOlfatoPoupador());
        sensor.setAmbienteOlfatoLadrao(this.percebeAmbienteOlfatoLadrao());
        ((ProgramaPoupador)this.programa).sensor = sensor;
    }

    public int[] percebeAmbienteVisao() {
        int[] ambiente = new int[24];
        if (this.x != 0 && this.y != 0) {
            ambiente[6] = this.matrizSimulacao[this.x - 1][this.y - 1];
        } else {
            ambiente[6] = Constantes.foraAmbiene;
        }

        if (this.y == 0) {
            ambiente[7] = Constantes.foraAmbiene;
        } else {
            ambiente[7] = this.matrizSimulacao[this.x][this.y - 1];
        }

        if (this.x != 29 && this.y != 0) {
            ambiente[8] = this.matrizSimulacao[this.x + 1][this.y - 1];
        } else {
            ambiente[8] = Constantes.foraAmbiene;
        }

        if (this.x == 0) {
            ambiente[11] = Constantes.foraAmbiene;
        } else {
            ambiente[11] = this.matrizSimulacao[this.x - 1][this.y];
        }

        if (this.x == 29) {
            ambiente[12] = Constantes.foraAmbiene;
        } else {
            ambiente[12] = this.matrizSimulacao[this.x + 1][this.y];
        }

        if (this.x != 0 && this.y != 29) {
            ambiente[15] = this.matrizSimulacao[this.x - 1][this.y + 1];
        } else {
            ambiente[15] = Constantes.foraAmbiene;
        }

        if (this.y == 29) {
            ambiente[16] = Constantes.foraAmbiene;
        } else {
            ambiente[16] = this.matrizSimulacao[this.x][this.y + 1];
        }

        if (this.x != 29 && this.y != 29) {
            ambiente[17] = this.matrizSimulacao[this.x + 1][this.y + 1];
        } else {
            ambiente[17] = Constantes.foraAmbiene;
        }

        if (ambiente[6] == Constantes.numeroParede) {
            ambiente[0] = Constantes.semVisao;
        } else if (this.x > 1 && this.y > 1) {
            ambiente[0] = this.matrizSimulacao[this.x - 2][this.y - 2];
        } else {
            ambiente[0] = Constantes.foraAmbiene;
        }

        if (ambiente[6] == Constantes.numeroParede && ambiente[7] == Constantes.numeroParede) {
            ambiente[1] = Constantes.semVisao;
        } else if (this.x != 0 && this.y > 1) {
            ambiente[1] = this.matrizSimulacao[this.x - 1][this.y - 2];
        } else {
            ambiente[1] = Constantes.foraAmbiene;
        }

        if (ambiente[7] == Constantes.numeroParede) {
            ambiente[2] = Constantes.semVisao;
        } else if (this.y <= 1) {
            ambiente[2] = Constantes.foraAmbiene;
        } else {
            ambiente[2] = this.matrizSimulacao[this.x][this.y - 2];
        }

        if (ambiente[7] == Constantes.numeroParede && ambiente[8] == Constantes.numeroParede) {
            ambiente[3] = Constantes.semVisao;
        } else if (this.x != 29 && this.y > 1) {
            ambiente[3] = this.matrizSimulacao[this.x + 1][this.y - 2];
        } else {
            ambiente[3] = Constantes.foraAmbiene;
        }

        if (ambiente[8] == Constantes.numeroParede) {
            ambiente[4] = Constantes.semVisao;
        } else if (this.x < 28 && this.y > 1) {
            ambiente[4] = this.matrizSimulacao[this.x + 2][this.y - 2];
        } else {
            ambiente[4] = Constantes.foraAmbiene;
        }

        if (ambiente[6] == Constantes.numeroParede && ambiente[11] == Constantes.numeroParede) {
            ambiente[5] = Constantes.semVisao;
        } else if (this.x > 1 && this.y != 0) {
            ambiente[5] = this.matrizSimulacao[this.x - 2][this.y - 1];
        } else {
            ambiente[5] = Constantes.foraAmbiene;
        }

        if (ambiente[8] == Constantes.numeroParede && ambiente[12] == Constantes.numeroParede) {
            ambiente[9] = Constantes.semVisao;
        } else if (this.x < 28 && this.y != 0) {
            ambiente[9] = this.matrizSimulacao[this.x + 2][this.y - 1];
        } else {
            ambiente[9] = Constantes.foraAmbiene;
        }

        if (ambiente[11] == Constantes.numeroParede) {
            ambiente[10] = Constantes.semVisao;
        } else if (this.x <= 1) {
            ambiente[10] = Constantes.foraAmbiene;
        } else {
            ambiente[10] = this.matrizSimulacao[this.x - 2][this.y];
        }

        if (ambiente[12] == Constantes.numeroParede) {
            ambiente[13] = Constantes.semVisao;
        } else if (this.x >= 28) {
            ambiente[13] = Constantes.foraAmbiene;
        } else {
            ambiente[13] = this.matrizSimulacao[this.x + 2][this.y];
        }

        if (ambiente[11] == Constantes.numeroParede && ambiente[15] == Constantes.numeroParede) {
            ambiente[14] = Constantes.semVisao;
        } else if (this.x > 1 && this.y != 29) {
            ambiente[14] = this.matrizSimulacao[this.x - 2][this.y + 1];
        } else {
            ambiente[14] = Constantes.foraAmbiene;
        }

        if (ambiente[12] == Constantes.numeroParede && ambiente[17] == Constantes.numeroParede) {
            ambiente[18] = Constantes.semVisao;
        } else if (this.x < 28 && this.y != 29) {
            ambiente[18] = this.matrizSimulacao[this.x + 2][this.y + 1];
        } else {
            ambiente[18] = Constantes.foraAmbiene;
        }

        if (ambiente[15] == Constantes.numeroParede) {
            ambiente[19] = Constantes.semVisao;
        } else if (this.x > 1 && this.y < 28) {
            ambiente[19] = this.matrizSimulacao[this.x - 2][this.y + 2];
        } else {
            ambiente[19] = Constantes.foraAmbiene;
        }

        if (ambiente[15] == Constantes.numeroParede && ambiente[16] == Constantes.numeroParede) {
            ambiente[20] = Constantes.semVisao;
        } else if (this.x != 0 && this.y < 28) {
            ambiente[20] = this.matrizSimulacao[this.x - 1][this.y + 2];
        } else {
            ambiente[20] = Constantes.foraAmbiene;
        }

        if (ambiente[16] == Constantes.numeroParede) {
            ambiente[21] = Constantes.semVisao;
        } else if (this.y >= 28) {
            ambiente[21] = Constantes.foraAmbiene;
        } else {
            ambiente[21] = this.matrizSimulacao[this.x][this.y + 2];
        }

        if (ambiente[16] == Constantes.numeroParede && ambiente[17] == Constantes.numeroParede) {
            ambiente[22] = Constantes.semVisao;
        } else if (this.x != 29 && this.y < 28) {
            ambiente[22] = this.matrizSimulacao[this.x + 1][this.y + 2];
        } else {
            ambiente[22] = Constantes.foraAmbiene;
        }

        if (ambiente[17] == Constantes.numeroParede) {
            ambiente[23] = Constantes.semVisao;
        } else if (this.x < 28 && this.y < 28) {
            ambiente[23] = this.matrizSimulacao[this.x + 2][this.y + 2];
        } else {
            ambiente[23] = Constantes.foraAmbiene;
        }

        return ambiente;
    }

    public int[] percebeAmbienteOlfatoPoupador() {
        int[] ambienteOlfato = new int[8];
        if (this.x != 0 && this.y != 0) {
            ambienteOlfato[0] = this.matrizOlfatoPoupador[this.x - 1][this.y - 1];
        } else {
            ambienteOlfato[0] = Constantes.foraAmbiene;
        }

        if (this.y == 0) {
            ambienteOlfato[1] = Constantes.foraAmbiene;
        } else {
            ambienteOlfato[1] = this.matrizOlfatoPoupador[this.x][this.y - 1];
        }

        if (this.x != 29 && this.y != 0) {
            ambienteOlfato[2] = this.matrizOlfatoPoupador[this.x + 1][this.y - 1];
        } else {
            ambienteOlfato[2] = Constantes.foraAmbiene;
        }

        if (this.x == 0) {
            ambienteOlfato[3] = Constantes.foraAmbiene;
        } else {
            ambienteOlfato[3] = this.matrizOlfatoPoupador[this.x - 1][this.y];
        }

        if (this.x == 29) {
            ambienteOlfato[4] = Constantes.foraAmbiene;
        } else {
            ambienteOlfato[4] = this.matrizOlfatoPoupador[this.x + 1][this.y];
        }

        if (this.x != 0 && this.y != 29) {
            ambienteOlfato[5] = this.matrizOlfatoPoupador[this.x - 1][this.y + 1];
        } else {
            ambienteOlfato[5] = Constantes.foraAmbiene;
        }

        if (this.y == 29) {
            ambienteOlfato[6] = Constantes.foraAmbiene;
        } else {
            ambienteOlfato[6] = this.matrizOlfatoPoupador[this.x][this.y + 1];
        }

        if (this.x != 29 && this.y != 29) {
            ambienteOlfato[7] = this.matrizOlfatoPoupador[this.x + 1][this.y + 1];
        } else {
            ambienteOlfato[7] = Constantes.foraAmbiene;
        }

        return ambienteOlfato;
    }

    public int[] percebeAmbienteOlfatoLadrao() {
        int[] ambienteOlfato = new int[8];
        if (this.x != 0 && this.y != 0) {
            ambienteOlfato[0] = this.matrizOlfatoLadrao[this.x - 1][this.y - 1];
        } else {
            ambienteOlfato[0] = Constantes.foraAmbiene;
        }

        if (this.y == 0) {
            ambienteOlfato[1] = Constantes.foraAmbiene;
        } else {
            ambienteOlfato[1] = this.matrizOlfatoLadrao[this.x][this.y - 1];
        }

        if (this.x != 29 && this.y != 0) {
            ambienteOlfato[2] = this.matrizOlfatoLadrao[this.x + 1][this.y - 1];
        } else {
            ambienteOlfato[2] = Constantes.foraAmbiene;
        }

        if (this.x == 0) {
            ambienteOlfato[3] = Constantes.foraAmbiene;
        } else {
            ambienteOlfato[3] = this.matrizOlfatoLadrao[this.x - 1][this.y];
        }

        if (this.x == 29) {
            ambienteOlfato[4] = Constantes.foraAmbiene;
        } else {
            ambienteOlfato[4] = this.matrizOlfatoLadrao[this.x + 1][this.y];
        }

        if (this.x != 0 && this.y != 29) {
            ambienteOlfato[5] = this.matrizOlfatoLadrao[this.x - 1][this.y + 1];
        } else {
            ambienteOlfato[5] = Constantes.foraAmbiene;
        }

        if (this.y == 29) {
            ambienteOlfato[6] = Constantes.foraAmbiene;
        } else {
            ambienteOlfato[6] = this.matrizOlfatoLadrao[this.x][this.y + 1];
        }

        if (this.x != 29 && this.y != 29) {
            ambienteOlfato[7] = this.matrizOlfatoLadrao[this.x + 1][this.y + 1];
        } else {
            ambienteOlfato[7] = Constantes.foraAmbiene;
        }

        return ambienteOlfato;
    }

    private void isPastinhaPoder(int xNovo, int yNovo) {
        if (this.isCelulaValida(xNovo, yNovo) && this.matrizSimulacao[xNovo][yNovo] == Constantes.numeroPastinhaPoder && this.numerodeMoedas >= Constantes.custoPastinha) {
            this.numerodeMoedas -= Constantes.custoPastinha;
            this.numeroJogadasImunes = Constantes.numeroTICsImunes;
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    private void isMoeda(int xNovo, int yNovo) {
        if (this.isCelulaValida(xNovo, yNovo) && this.matrizSimulacao[xNovo][yNovo] == Constantes.numeroMoeda) {
            this.numerodeMoedas += Constantes.valorGanhoMoedas;
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    private void isBanco(int xNovo, int yNovo) {
        if (this.isCelulaValida(xNovo, yNovo) && this.matrizSimulacao[xNovo][yNovo] == Constantes.numeroBanco) {
            this.numerodeMoedasNoBanco += this.numerodeMoedas;
            this.numerodeMoedas = 0;
        }

    }

    public void moveCimaLadrao() {
        int xNovo = this.x;
        int yNovo = this.y - 1;
        if (!this.isPosicaoValida(xNovo, yNovo)) {
            if (this.isCelulaValida(xNovo, yNovo)) {
                this.matrizOlfatoLadrao[this.x][this.y] = -1;
            }

            this.isPoupador(xNovo, yNovo);
        } else {
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.matrizOlfatoLadrao[this.x][this.y] = -1;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    public void ficarParadoLadrao() {
    }

    public void ficarParadoPoupador() {
    }

    public void moveBaixoLadrao() {
        int xNovo = this.x;
        int yNovo = this.y + 1;
        if (!this.isPosicaoValida(xNovo, yNovo)) {
            if (this.isCelulaValida(xNovo, yNovo)) {
                this.matrizOlfatoLadrao[this.x][this.y] = -1;
            }

            this.isPoupador(xNovo, yNovo);
        } else {
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.matrizOlfatoLadrao[this.x][this.y] = -1;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    public void moveEsquerdaLadrao() {
        int xNovo = this.x - 1;
        int yNovo = this.y;
        if (!this.isPosicaoValida(xNovo, yNovo)) {
            if (this.isCelulaValida(xNovo, yNovo)) {
                this.matrizOlfatoLadrao[this.x][this.y] = -1;
            }

            this.isPoupador(xNovo, yNovo);
        } else {
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.matrizOlfatoLadrao[this.x][this.y] = -1;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    public void moveDireitaLadrao() {
        int xNovo = this.x + 1;
        int yNovo = this.y;
        if (!this.isPosicaoValida(xNovo, yNovo)) {
            if (this.isCelulaValida(xNovo, yNovo)) {
                this.matrizOlfatoLadrao[this.x][this.y] = -1;
            }

            this.isPoupador(xNovo, yNovo);
        } else {
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.matrizOlfatoLadrao[this.x][this.y] = -1;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    public void moveCimaPoupador() {
        int xNovo = this.x;
        int yNovo = this.y - 1;
        if (!this.isPosicaoValida(xNovo, yNovo)) {
            if (this.isCelulaValida(xNovo, yNovo)) {
                this.matrizOlfatoPoupador[this.x][this.y] = -1;
            }

            this.isLadrao(xNovo, yNovo);
            this.isMoeda(xNovo, yNovo);
            this.isBanco(xNovo, yNovo);
            this.isPastinhaPoder(xNovo, yNovo);
        } else {
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.matrizOlfatoPoupador[this.x][this.y] = -1;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    public void moveBaixoPoupador() {
        int xNovo = this.x;
        int yNovo = this.y + 1;
        if (!this.isPosicaoValida(xNovo, yNovo)) {
            if (this.isCelulaValida(xNovo, yNovo)) {
                this.matrizOlfatoPoupador[this.x][this.y] = -1;
            }

            this.isLadrao(xNovo, yNovo);
            this.isMoeda(xNovo, yNovo);
            this.isBanco(xNovo, yNovo);
            this.isPastinhaPoder(xNovo, yNovo);
        } else {
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.matrizOlfatoPoupador[this.x][this.y] = -1;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    public void moveEsquerdaPoupador() {
        int xNovo = this.x - 1;
        int yNovo = this.y;
        if (!this.isPosicaoValida(xNovo, yNovo)) {
            if (this.isCelulaValida(xNovo, yNovo)) {
                this.matrizOlfatoPoupador[this.x][this.y] = -1;
            }

            this.isLadrao(xNovo, yNovo);
            this.isMoeda(xNovo, yNovo);
            this.isBanco(xNovo, yNovo);
            this.isPastinhaPoder(xNovo, yNovo);
        } else {
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.matrizOlfatoPoupador[this.x][this.y] = -1;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    public void moveDireitaPoupador() {
        int xNovo = this.x + 1;
        int yNovo = this.y;
        if (!this.isPosicaoValida(xNovo, yNovo)) {
            if (this.isCelulaValida(xNovo, yNovo)) {
                this.matrizOlfatoPoupador[xNovo][yNovo] = -1;
            }

            this.isLadrao(xNovo, yNovo);
            this.isMoeda(xNovo, yNovo);
            this.isBanco(xNovo, yNovo);
            this.isPastinhaPoder(xNovo, yNovo);
        } else {
            this.matrizSimulacao[this.x][this.y] = 0;
            this.matrizSimulacao[xNovo][yNovo] = this.numeroAgente;
            this.matrizOlfatoPoupador[this.x][this.y] = -1;
            this.x = xNovo;
            this.y = yNovo;
        }

    }

    private boolean isCelulaValida(int xNovo, int yNovo) {
        return xNovo <= 29 && xNovo >= 0 && yNovo <= 29 && yNovo >= 0;
    }

    public void moverLadrao(int posicao) {
        switch(posicao) {
            case 0:
                this.ficarParadoLadrao();
                break;
            case 1:
                this.moveCimaLadrao();
                break;
            case 2:
                this.moveBaixoLadrao();
                break;
            case 3:
                this.moveDireitaLadrao();
                break;
            case 4:
                this.moveEsquerdaLadrao();
        }

    }

    public void moverPoupador(int posicao) {
        switch(posicao) {
            case 0:
                this.ficarParadoPoupador();
                break;
            case 1:
                this.moveCimaPoupador();
                break;
            case 2:
                this.moveBaixoPoupador();
                break;
            case 3:
                this.moveDireitaPoupador();
                break;
            case 4:
                this.moveEsquerdaPoupador();
        }

    }

    private boolean isPosicaoValida(int xNovo, int yNovo) {
        if (this.isCelulaValida(xNovo, yNovo)) {
            return this.matrizSimulacao[xNovo][yNovo] == Constantes.posicaoLivre;
        } else {
            return false;
        }
    }

    private void isPoupador(int xNovo, int yNovo) {
        if (this.isCelulaValida(xNovo, yNovo) && this.matrizSimulacao[xNovo][yNovo] >= Constantes.numeroPoupador01 && this.matrizSimulacao[xNovo][yNovo] < Constantes.numeroLadrao01) {
            Iterator iter = this.equipes.iterator();

            while(iter.hasNext()) {
                Agentes element = (Agentes)iter.next();
                if (element.getArquitetura().getPosicao().x == xNovo & element.getArquitetura().getPosicao().y == yNovo && element.getArquitetura().getNumeroJogadasImunes() <= 0) {
                    int moedaPoupador = element.getArquitetura().getNumerodeMoedas();
                    int moedaLadrao = this.numerodeMoedas;
                    element.getArquitetura().setNumerodeMoedas(0);
                    this.setNumerodeMoedas(moedaLadrao + moedaPoupador);
                }
            }
        }

    }

    private void isLadrao(int xNovo, int yNovo) {
        if (this.isCelulaValida(xNovo, yNovo) && this.numeroJogadasImunes <= 0 && this.matrizSimulacao[xNovo][yNovo] >= Constantes.numeroLadrao01) {
            Iterator iter = this.equipes.iterator();

            while(iter.hasNext()) {
                Agentes element = (Agentes)iter.next();
                if (element.getArquitetura().getPosicao().x == xNovo & element.getArquitetura().getPosicao().y == yNovo) {
                    int moedaLadrao = element.getArquitetura().getNumerodeMoedas();
                    int moedaPoupador = this.numerodeMoedas;
                    element.getArquitetura().setNumerodeMoedas(moedaLadrao + moedaPoupador);
                    this.setNumerodeMoedas(0);
                }
            }
        }

    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getNumeroAgente() {
        return this.numeroAgente;
    }

    public int getNumerodeMoedas() {
        return this.numerodeMoedas;
    }

    public void setNumerodeMoedas(int energiaIndividual) {
        this.numerodeMoedas = energiaIndividual;
    }

    public int retornaNumeroMoedas(int xAgente, int yAgente) {
        int resultado = 0;
        Iterator iter = this.equipes.iterator();

        while(iter.hasNext()) {
            Agentes element = (Agentes)iter.next();
            if (element.getArquitetura().getPosicao().x == xAgente & element.getArquitetura().getPosicao().y == yAgente) {
                resultado = element.getArquitetura().numerodeMoedas;
            }
        }

        return resultado;
    }
}