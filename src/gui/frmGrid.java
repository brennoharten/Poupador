

package gui;

import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class frmGrid extends JPanel {
    private JTable table;
    private DefaultTableModel dtm;

    public frmGrid() {
        this.mudaLookAndFeel();
        this.dtm = new DefaultTableModel();
        this.table = new JTable(this.dtm);
        this.setLayout(new GridLayout(1, 1));
        this.add(new JScrollPane(this.table));
    }

    public JTable getTable() {
        return this.table;
    }

    public void addColuna(String nomeColuna) {
        this.dtm.addColumn(nomeColuna);
    }

    public void addLinha(String[] linha) {
        this.dtm.addRow(linha);
    }

    public void removeLinha(int lin) {
        try {
            if (this.dtm.getRowCount() > 0) {
                this.dtm.removeRow(lin);
            }
        } catch (Exception var3) {
            System.out.println("Erro ao remover Linha do Grid.");
        }

    }

    public JPanel returnPanel() {
        return this;
    }

    public void mudaLookAndFeel() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception var2) {
        }

    }

    public static void main(String[] args) {
        String[] arrayValores = new String[]{"AAA", "BBB", "CCC", "DDD"};
        frmGrid geracoes = new frmGrid();
        geracoes.addColuna("C1");
        geracoes.addColuna("C2");
        geracoes.addColuna("C3");
        geracoes.addColuna("C4");

        for(int i = 0; i < 500; ++i) {
            geracoes.addLinha(arrayValores);
        }

        JFrame frame = new JFrame("Teste");
        frame.add(geracoes.returnPanel());
        frame.pack();
        frame.setLocationRelativeTo((Component)null);
        frame.setVisible(true);
    }
}
