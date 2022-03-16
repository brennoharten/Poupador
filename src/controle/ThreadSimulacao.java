package controle;

import algoritmo.Ambiente;
import gui.FramePrincipal;

public class ThreadSimulacao extends Thread {
    private FramePrincipal framePrincipal;
    public boolean pleaseWait = false;
    public boolean allDone = false;
    private int tempoSimulação;
    private int[][] matrizSimulacao = null;
    private Ambiente ambiente;

    public ThreadSimulacao(Ambiente algoritmoLabirinto, FramePrincipal framePrincipal, int[][] matrizSimulacao) {
        this.ambiente = algoritmoLabirinto;
        this.framePrincipal = framePrincipal;
        this.matrizSimulacao = matrizSimulacao;
        this.tempoSimulação = 100;
    }

    public void run() {
        do {
            try {
                Thread.sleep((long)this.tempoSimulação);
                this.ambiente.executa();
                this.framePrincipal.atualizaGrid(this.ambiente.equipes);
                this.framePrincipal.atualizaAmbiente(this.matrizSimulacao);
            } catch (InterruptedException var4) {
                var4.printStackTrace();
                System.out.println(var4.toString());
            }

            synchronized(this) {
                while(this.pleaseWait) {
                    try {
                        this.wait();
                    } catch (Exception var3) {
                    }

                    if (this.allDone) {
                        break;
                    }
                }
            }
        } while(!this.allDone);

        System.out.println("Thread Simulação Morreu");
    }
}