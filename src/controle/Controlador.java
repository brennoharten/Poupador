package controle;

import algoritmo.Agentes;
import algoritmo.Ambiente;
import gui.FramePrincipal;
import java.awt.Component;
import java.util.Iterator;
import javax.swing.JOptionPane;

public class Controlador {
    private FramePrincipal framePrincipal;
    private InterpretadorArquivo interpretadorArquivo;
    private ThreadSimulacao threadSimulacao;
    private boolean stop;
    private int[][] matrizSimulacao = null;
    private String caminhoArquivo = null;
    private Ambiente algoritmoLabirinto;

    public Controlador(FramePrincipal framePrincipal) {
        this.framePrincipal = framePrincipal;
        this.threadSimulacao = new ThreadSimulacao(this.algoritmoLabirinto, framePrincipal, this.matrizSimulacao);
        this.stop = true;
        this.interpretadorArquivo = new InterpretadorArquivo();
        this.algoritmoLabirinto = new Ambiente(this, this.matrizSimulacao);
    }

    public void carregaSimulacao(String caminhoArquivo) {
        if (this.stop) {
            this.caminhoArquivo = caminhoArquivo;
            this.matrizSimulacao = this.interpretadorArquivo.leArquivo(caminhoArquivo);
            this.framePrincipal.carregaSimulacao(this.matrizSimulacao);
        } else {
            JOptionPane.showMessageDialog((Component)null, "Não foi possível carregar o arquivo...\nSimulação em execução!", "ERRO", 0);
        }

    }

    public void play() {
        if (this.matrizSimulacao == null) {
            JOptionPane.showMessageDialog((Component)null, "O ambiente não foi carregado!", "ERRO", 0);
        } else if (this.stop) {
            this.algoritmoLabirinto = new Ambiente(this, this.matrizSimulacao);
            this.iniciaSimulacao();
            this.stop = false;
        } else {
            this.voltaSimulacao();
        }

    }

    public void pause() {
        synchronized(this.threadSimulacao) {
            this.threadSimulacao.interrupt();
            this.threadSimulacao.pleaseWait = true;
        }
    }

    public void stop() {
        this.threadSimulacao.interrupt();
        this.threadSimulacao.allDone = true;
        this.stop = true;
        this.framePrincipal.iniciarJogo();
        if (this.caminhoArquivo != null) {
            this.carregaSimulacao(this.caminhoArquivo);
        }

        this.algoritmoLabirinto = null;
    }

    private void iniciaSimulacao() {
        this.threadSimulacao = new ThreadSimulacao(this.algoritmoLabirinto, this.framePrincipal, this.matrizSimulacao);
        this.threadSimulacao.start();
    }

    private void voltaSimulacao() {
        synchronized(this.threadSimulacao) {
            this.threadSimulacao.pleaseWait = false;
            this.threadSimulacao.notify();
        }
    }

    public boolean isPosicaoValida(int x, int y) {
        int linhas = this.matrizSimulacao.length;
        int colunas = this.matrizSimulacao[0].length;
        return x < linhas && y < colunas && x >= 0 && y >= 0;
    }

    public void reduzTempo(int tempoSegundos) {
        this.framePrincipal.reduzCronometro(tempoSegundos);
    }

    public void atualizaTempo(int tempo) {
        this.framePrincipal.aumentarEnergia(tempo);
    }

    public int getEnergiaRestante() {
        return this.framePrincipal.getEnergiaRestante();
    }

    public void isFimSimulacao() {
        int somaPoupador = 0;
        int somaLadrao = 0;
        String ganhador = "";
        if (this.framePrincipal.getEnergiaRestante() <= 0) {
            this.pause();
            Iterator iter = this.algoritmoLabirinto.equipes.iterator();

            while(iter.hasNext()) {
                Agentes element = (Agentes)iter.next();
                System.out.println("Agente: " + element.getArquitetura().getNumeroAgente() + " Numero Moedas: " + element.getArquitetura().getNumerodeMoedas() + " Moedas no Banco: " + element.getArquitetura().getNumerodeMoedasNoBanco());
                if (element.getArquitetura().getNumeroAgente() < Constantes.numeroLadrao01) {
                    somaPoupador += element.getArquitetura().getNumerodeMoedasNoBanco() + element.getArquitetura().getNumerodeMoedas();
                } else {
                    somaLadrao += element.getArquitetura().getNumerodeMoedas();
                }
            }

            if (somaPoupador == somaLadrao) {
                ganhador = "Jogo Empatado";
            } else if (somaPoupador > somaLadrao) {
                ganhador = "Poupador Ganhou!";
            } else {
                ganhador = "Ladrão Ganhou!";
            }

            JOptionPane.showMessageDialog((Component)null, "Fim do Tempo! Poupador: " + somaPoupador + " Ladrão: " + somaLadrao + " \n" + ganhador);
        }

    }
}
