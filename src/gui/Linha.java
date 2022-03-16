package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

public class Linha extends JPanel {
    private JPanel panelIcon;
    private JPanel panelDescricao;

    public Linha(JPanel descricao, JPanel icon) {
        this.panelDescricao = descricao;
        this.panelIcon = icon;
        this.setLayout(new GridBagLayout());
        this.add(this.panelIcon, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        this.add(this.panelDescricao, new GridBagConstraints(0, 0, 1, 1, 0.0D, 0.0D, 10, 1, new Insets(0, 0, 0, 0), 0, 0));
        this.add(this.panelIcon);
        this.add(this.panelDescricao);
    }

    public JPanel getPanelDescricao() {
        return this.panelDescricao;
    }

    public void setPanelDescricao(JPanel panelDescricao) {
        this.panelDescricao = panelDescricao;
    }

    public JPanel getPanelIcon() {
        return this.panelIcon;
    }

    public void setPanelIcon(JPanel panelIcon) {
        this.panelIcon = panelIcon;
    }
}
