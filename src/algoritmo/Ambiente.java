package algoritmo;

import controle.Constantes;
import controle.Controlador;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Ambiente {
    private Controlador controlador;
    static int[][] matrizSimulacao = null;
    static int[][] matrizOlfatoPoupador = null;
    static int[][] matrizOlfatoLadrao = null;
    public List equipes = new ArrayList();
    Agentes ladrao01;
    Agentes ladrao02;
    Agentes ladrao03;
    Agentes ladrao04;
    Agentes poupador01;
    Agentes poupador02;
    static int lin = 30;
    static int col = 30;

    public Ambiente(Controlador controlador, int[][] matrizSimulacao) {
        this.controlador = controlador;
        Ambiente.matrizSimulacao = matrizSimulacao;
        matrizOlfatoPoupador = new int[lin][col];

        int i;
        int j;
        for(i = 0; i < lin; ++i) {
            for(j = 0; j < col; ++j) {
                matrizOlfatoPoupador[i][j] = 0;
            }
        }

        matrizOlfatoLadrao = new int[lin][col];

        for(i = 0; i < lin; ++i) {
            for(j = 0; j < col; ++j) {
                matrizOlfatoLadrao[i][j] = 0;
            }
        }

        this.criaAgentes();
    }

    private void criaAgentes() {
        Ladrao ld1 = new Ladrao();
        Ladrao ld2 = new Ladrao();
        Ladrao ld3 = new Ladrao();
        Ladrao ld4 = new Ladrao();
        this.ladrao01 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoPoupador, this.controlador, 200, ld1, this.equipes, matrizOlfatoLadrao), ld1);
        this.ladrao02 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoPoupador, this.controlador, 210, ld2, this.equipes, matrizOlfatoLadrao), ld2);
        this.ladrao03 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoPoupador, this.controlador, 220, ld3, this.equipes, matrizOlfatoLadrao), ld3);
        this.ladrao04 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoPoupador, this.controlador, 230, ld4, this.equipes, matrizOlfatoLadrao), ld4);
        Poupador pd1 = new Poupador();
        Poupador pd2 = new Poupador();
        this.poupador01 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoPoupador, this.controlador, 100, pd1, this.equipes, matrizOlfatoLadrao), pd1);
        this.poupador02 = new Agentes(new Arquitetura(matrizSimulacao, matrizOlfatoPoupador, this.controlador, 110, pd2, this.equipes, matrizOlfatoLadrao), pd2);
        this.equipes.add(this.poupador01);
        this.equipes.add(this.poupador02);
        this.equipes.add(this.ladrao01);
        this.equipes.add(this.ladrao02);
        this.equipes.add(this.ladrao03);
        this.equipes.add(this.ladrao04);
    }

    public void executa() {
        this.decrementaImunidade();
        this.ladrao01.getArquitetura().percebeLadrao();
        this.ladrao01.getArquitetura().moverLadrao(this.ladrao01.getPrograma().acao());
        this.ladrao02.getArquitetura().percebeLadrao();
        this.ladrao02.getArquitetura().moverLadrao(this.ladrao02.getPrograma().acao());
        this.ladrao03.getArquitetura().percebeLadrao();
        this.ladrao03.getArquitetura().moverLadrao(this.ladrao03.getPrograma().acao());
        this.ladrao04.getArquitetura().percebeLadrao();
        this.ladrao04.getArquitetura().moverLadrao(this.ladrao04.getPrograma().acao());
        this.poupador01.getArquitetura().percebePoupador();
        this.poupador01.getArquitetura().moverPoupador(this.poupador01.getPrograma().acao());
        this.poupador02.getArquitetura().percebePoupador();
        this.poupador02.getArquitetura().moverPoupador(this.poupador02.getPrograma().acao());
        this.decrementaFeromonio();
        this.controlador.reduzTempo(1);
        this.controlador.isFimSimulacao();
    }

    private void decrementaImunidade() {
        Iterator iter = this.equipes.iterator();

        while(iter.hasNext()) {
            Agentes element = (Agentes)iter.next();
            if (element.getArquitetura().getNumeroAgente() >= Constantes.numeroPoupador01 && element.getArquitetura().getNumeroAgente() < Constantes.numeroLadrao01) {
                int jogadas = element.getArquitetura().getNumeroJogadasImunes();
                if (jogadas > 0) {
                    --jogadas;
                    element.getArquitetura().setNumeroJogadasImunes(jogadas);
                }
            }
        }

    }

    private void decrementaFeromonio() {
        int i;
        int j;
        int var10002;
        for(i = 0; i < lin; ++i) {
            for(j = 0; j < col; ++j) {
                if (matrizOlfatoPoupador[i][j] == -1) {
                    matrizOlfatoPoupador[i][j] = 1;
                } else if (matrizOlfatoPoupador[i][j] != 0) {
                    if (matrizOlfatoPoupador[i][j] >= 5) {
                        matrizOlfatoPoupador[i][j] = 0;
                    } else {
                        var10002 = matrizOlfatoPoupador[i][j]++;
                    }
                }
            }
        }

        for(i = 0; i < lin; ++i) {
            for(j = 0; j < col; ++j) {
                if (matrizOlfatoLadrao[i][j] == -1) {
                    matrizOlfatoLadrao[i][j] = 1;
                } else if (matrizOlfatoLadrao[i][j] != 0) {
                    if (matrizOlfatoLadrao[i][j] >= 5) {
                        matrizOlfatoLadrao[i][j] = 0;
                    } else {
                        var10002 = matrizOlfatoLadrao[i][j]++;
                    }
                }
            }
        }

    }

    private void imprimeFeromonio() {
        System.out.println("-----------------------------------------------------------------------");

        for(int i = 0; i < lin; ++i) {
            for(int j = 0; j < col; ++j) {
                System.out.print(matrizOlfatoPoupador[i][j] + ";");
            }

            System.out.println("");
        }

        System.out.println("fim");
    }
}