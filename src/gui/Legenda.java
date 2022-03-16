package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

public class Legenda extends JPanel {
    private JPanel panelEsq = new JPanel(new GridLayout(0, 1, 0, 0));
    private JPanel panelDir = new JPanel(new GridLayout(0, 1, 0, 0));

    public Legenda(String nomeLegenda) {
        this.setBorder(new TitledBorder(nomeLegenda));
        this.setLayout(new FlowLayout());
        this.add(this.panelEsq);
        this.add(this.panelDir);
    }

    public void addLinha(Color colorEsq, int height, int width, String descricao) {
        this.panelEsq.setLayout(new GridLayout(0, 1, 0, 7));
        this.panelDir.setLayout(new GridLayout(0, 1, 0, 0));
        JPanel linhaEsq = new JPanel();
        JLabel linhaDireita = new JLabel(descricao);
        JPanel linha = new JPanel();
        linhaEsq.setBackground(colorEsq);
        linhaEsq.setPreferredSize(new Dimension(width, height));
        linhaEsq.setBorder(BorderFactory.createBevelBorder(0));
        linha.add(linhaEsq);
        this.panelEsq.add(linha);
        linha = new JPanel();
        linha.add(linhaDireita);
        this.panelDir.add(linha);
    }

    public void addLinha(ImageIcon imageIcon, double sx, double sy, String descricao) {
        this.panelEsq.setLayout(new GridLayout(0, 1, 0, 0));
        this.panelDir.setLayout(new GridLayout(0, 1, 0, 5));
        BufferedImage bufferedImage = toBufferedImage(imageIcon.getImage());
        AffineTransform tx = new AffineTransform();
        tx.scale(sx, sy);
        AffineTransformOp op = new AffineTransformOp(tx, 2);
        bufferedImage = op.filter(bufferedImage, (BufferedImage)null);
        JLabel linhaEsq = new JLabel(new ImageIcon(bufferedImage));
        JLabel linhaDireita = new JLabel(descricao);
        JPanel linha = new JPanel();
        linhaEsq.setBorder(BorderFactory.createBevelBorder(0));
        linha.add(linhaEsq);
        this.panelEsq.add(linha);
        linha = new JPanel();
        linha.add(linhaDireita);
        this.panelDir.add(linha);
    }

    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
        } else {
            image = (new ImageIcon(image)).getImage();
            boolean hasAlpha = hasAlpha(image);
            BufferedImage bimage = null;
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            byte type;
            try {
                type = 1;
                if (hasAlpha) {
                    type = 2;
                }

                GraphicsDevice gs = ge.getDefaultScreenDevice();
                GraphicsConfiguration gc = gs.getDefaultConfiguration();
                bimage = gc.createCompatibleImage(image.getWidth((ImageObserver)null), image.getHeight((ImageObserver)null), type);
            } catch (HeadlessException var7) {
            }

            if (bimage == null) {
                type = 1;
                if (hasAlpha) {
                    type = 2;
                }

                bimage = new BufferedImage(image.getWidth((ImageObserver)null), image.getHeight((ImageObserver)null), type);
            }

            Graphics g = bimage.createGraphics();
            g.drawImage(image, 0, 0, (ImageObserver)null);
            g.dispose();
            return bimage;
        }
    }

    public static boolean hasAlpha(Image image) {
        if (image instanceof BufferedImage) {
            BufferedImage bimage = (BufferedImage)image;
            return bimage.getColorModel().hasAlpha();
        } else {
            PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, false);

            try {
                pg.grabPixels();
            } catch (InterruptedException var3) {
            }

            ColorModel cm = pg.getColorModel();
            return cm.hasAlpha();
        }
    }
}
