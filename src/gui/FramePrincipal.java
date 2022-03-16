package gui;

import algoritmo.Agentes;
//import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import controle.Constantes;
import controle.Controlador;
import gui.FramePrincipal1;
import gui.FramePrincipal2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class FramePrincipal extends JFrame implements ActionListener {
    private Container container;
    private JPanel panelNorte;
    private JPanel panelNorteEsq;
    private JPanel panelNorteEsqEsq;
    private JPanel panelNorteEsqDir;
    private JPanel panelNorteDir;
    private JPanel panelCentro;
    private JPanel panelLeste;
    private JPanel panelSul;
    private JToolBar toolBarControle;
    private JLabel labelTempo;
    private JLabel labelTempoValor;
    private Icon iconPlay;
    private Icon iconPause;
    private Icon iconStop;
    private Icon iconCarregar;
    private Icon iconClose;
    private JButton buttonPlay;
    private JButton buttonPause;
    private JButton buttonStop;
    private JButton buttonCarregar;
    private JButton buttonClose;
    private frmGrid gridResultados;
    private Controlador controlador;
    private PainelLabirinto painelLabirinto;

    public FramePrincipal() {
        this.configuraFrame();
        this.instanciaVariaveis();
        this.constroiFrame();
        this.criarGridInformacoes();
        this.registraEventos();
        this.setVisible(true);
    }

    public void criarGridInformacoes() {
        this.gridResultados = new frmGrid();
        this.gridResultados.addColuna("Agente");
        this.gridResultados.getTable().getColumn("Agente").setPreferredWidth(10);
        this.gridResultados.addColuna("Nr. de Moedas");
        this.gridResultados.getTable().getColumn("Nr. de Moedas").setPreferredWidth(30);
        this.gridResultados.addColuna("Nr. de Moedas no Banco");
        this.gridResultados.getTable().getColumn("Nr. de Moedas no Banco").setPreferredWidth(120);
        this.gridResultados.addColuna("Nr. de Jogadas Imunes");
        this.gridResultados.getTable().getColumn("Nr. de Jogadas Imunes").setPreferredWidth(120);
        this.panelSul.add(this.gridResultados.returnPanel());
        this.pack();
    }

    public void atualizaGrid(List equipes) {
        try {
            for(int i = 1; i <= equipes.size(); ++i) {
                this.gridResultados.removeLinha(0);
            }

            Iterator iter = equipes.iterator();

            while(iter.hasNext()) {
                Agentes element = (Agentes)iter.next();
                String[] m = new String[4];
                if (element.getArquitetura().getNumeroAgente() < Constantes.numeroLadrao01) {
                    m[0] = "Poupador_" + (element.getArquitetura().getNumeroAgente() - 100) / 10;
                } else {
                    m[0] = "Ladrão_" + (element.getArquitetura().getNumeroAgente() - 200) / 10;
                }

                m[1] = String.valueOf(element.getArquitetura().getNumerodeMoedas());
                m[2] = String.valueOf(element.getArquitetura().getNumerodeMoedasNoBanco());
                m[3] = String.valueOf(element.getArquitetura().getNumeroJogadasImunes());
                this.gridResultados.addLinha(m);
            }
        } catch (Exception var5) {
            System.out.println("Erro ao Atualizar Grid.");
        }

    }

    private void mudaLookAndFeel() {
        try {
            //UIManager.setLookAndFeel(new WindowsLookAndFeel());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    private void configuraFrame() {
        this.setTitle("Trabalho de IA - Poupador");
        this.setSize(400, 395);
        this.setResizable(false);
        //this.addWindowListener(new 1(this));
        this.setLocationRelativeTo((Component)null);
    }

    private void instanciaVariaveis() {
        this.container = this.getContentPane();
        this.container.setLayout(new BorderLayout());
        this.panelNorte = new JPanel(new BorderLayout());
        this.panelNorteEsq = new JPanel();
        this.panelNorteEsqEsq = new JPanel();
        this.panelNorteEsqDir = new JPanel(new FlowLayout(2));
        this.panelNorteEsqDir.setBorder(new BevelBorder(1));
        this.panelNorteEsqDir.setPreferredSize(new Dimension(80, 30));
        this.panelNorteDir = new JPanel();
        this.panelCentro = new JPanel();
        this.panelCentro.setBackground(Color.WHITE);
        this.panelCentro.setBorder(new BevelBorder(1));
        this.panelLeste = new JPanel();
        this.panelSul = new JPanel(new FlowLayout(0));
        this.panelSul.setBorder(new BevelBorder(1));
        this.panelSul.setPreferredSize(new Dimension(10, 130));
        this.toolBarControle = new JToolBar("Controle");
        this.labelTempo = new JLabel("Tempo : ");
        this.labelTempoValor = new JLabel("0");
        this.iconPlay = new ImageIcon("src/image/play.jpg");
        this.iconPause = new ImageIcon("src/image/pause.jpg");
        this.iconStop = new ImageIcon("src/image/stop.jpg");
        this.iconCarregar = new ImageIcon("src/image/carregaArquivo.jpg");
        this.iconClose = new ImageIcon("src/image/exit.jpg");
        this.buttonPlay = new JButton(this.iconPlay);
        this.buttonPlay.setToolTipText("Play");
        this.buttonPause = new JButton(this.iconPause);
        this.buttonPause.setToolTipText("Pause");
        this.buttonStop = new JButton(this.iconStop);
        this.buttonStop.setToolTipText("Stop");
        this.buttonCarregar = new JButton(this.iconCarregar);
        this.buttonCarregar.setToolTipText("Carregar");
        this.buttonClose = new JButton(this.iconClose);
        this.buttonClose.setToolTipText("Close");
        this.iniciarJogo();
        this.controlador = new Controlador(this);
    }

    private void constroiFrame() {
        this.container.add(this.panelNorte, "North");
        this.container.add(this.panelCentro, "Center");
        this.container.add(this.panelLeste, "East");
        this.container.add(this.panelSul, "South");
        this.panelNorte.add(this.panelNorteEsq, "West");
        this.panelNorte.add(this.panelNorteDir, "East");
        this.panelNorteEsq.add(this.panelNorteEsqEsq, "East");
        this.panelNorteEsq.add(this.panelNorteEsqDir, "East");
        this.panelNorteEsqEsq.add(this.labelTempo);
        this.panelNorteEsqDir.add(this.labelTempoValor);
        this.panelNorteDir.add(this.toolBarControle, "East");
        Legenda l = new Legenda("Legenda");
        l.addLinha(Color.GREEN, 8, 8, "Banco");
        l.addLinha(Color.BLUE, 8, 8, "Parede");
        l.addLinha(Color.YELLOW, 8, 8, "Moedas");
        l.addLinha(Color.CYAN, 8, 8, "Pastilhas do Poder");
        l.addLinha(Color.RED, 8, 8, "Poupador");
        l.addLinha(Color.WHITE, 8, 8, "Ladrão");
        this.panelLeste.add(l);
        this.toolBarControle.add(this.buttonPlay);
        this.toolBarControle.add(this.buttonPause);
        this.toolBarControle.add(this.buttonStop);
        this.toolBarControle.add(this.buttonCarregar);
        this.toolBarControle.add(this.buttonClose);
    }

    private void registraEventos() {
        this.buttonPlay.addActionListener(this);
        this.buttonPause.addActionListener(this);
        this.buttonStop.addActionListener(this);
        this.buttonCarregar.addActionListener(this);
        this.buttonClose.addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == this.buttonPlay) {
            this.controlador.play();
        } else if (source == this.buttonPause) {
            this.controlador.pause();
        } else if (source == this.buttonStop) {
            this.controlador.stop();
        } else if (source == this.buttonCarregar) {
            String caminhoArquivo = this.abrirArquivo();
            if (!caminhoArquivo.equals("")) {
                this.controlador.carregaSimulacao(caminhoArquivo);
            }
        } else if (source == this.buttonClose) {
            System.exit(0);
        }

    }

    private String abrirArquivo() {
        String caminhoArquivo = "";
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        //chooser.setFileFilter(new 2(this));
        int r = chooser.showOpenDialog(new JFrame());
        if (r == 0) {
            caminhoArquivo = chooser.getSelectedFile().getPath();
            System.out.println(caminhoArquivo);
        }

        return caminhoArquivo;
    }

    public void carregaSimulacao(int[][] matrizSimulacao) {
        this.painelLabirinto = new PainelLabirinto();
        this.painelLabirinto.setPixel(15);
        this.painelLabirinto.setGrid(matrizSimulacao);
        this.panelCentro.removeAll();
        this.panelCentro.add(this.painelLabirinto.returnPanel());
        SwingUtilities.updateComponentTreeUI(this.panelCentro);
        this.pack();
    }

    public void atualizaAmbiente(int[][] matrizSimulacao) {
        this.painelLabirinto.setGrid(matrizSimulacao);
        this.panelCentro.removeAll();
        this.panelCentro.add(this.painelLabirinto.returnPanel());
        SwingUtilities.updateComponentTreeUI(this.panelCentro);
    }

    public void aumentarEnergia(int energia) {
        int tempo = Integer.parseInt(this.labelTempoValor.getText());
        tempo += energia;
        this.labelTempoValor.setText(Integer.toString(tempo));
    }

    public void reduzCronometro(int energia) {
        int tempo = Integer.parseInt(this.labelTempoValor.getText());
        tempo -= energia;
        if (tempo < 0) {
            tempo = 0;
        }

        this.labelTempoValor.setText(Integer.toString(tempo));
    }

    public int getEnergiaRestante() {
        return Integer.parseInt(this.labelTempoValor.getText());
    }

    public void iniciarJogo() {
        this.labelTempoValor.setText((new Integer(Constantes.tempoInicialJogo)).toString());
    }

    public void zeraEnergia() {
        this.labelTempoValor.setText(new String("0"));
    }

    public static void main(String[] args) {
        new FramePrincipal();
    }
}
