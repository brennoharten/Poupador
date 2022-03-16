

package gui;

import java.util.Base64;
import java.util.Base64.Decoder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PainelLabirinto extends JPanel {
    int[][] grid;
    int pixel = 10;

    public PainelLabirinto() {
        this.setBackground(Color.WHITE);
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.desenhaGrid(g);
        this.desenhaGrade(g);
    }

    public void desenhaGrade(Graphics g) {
        g.setColor(Color.BLACK);
        if (this.grid != null) {
            int i;
            for(i = 0; i < this.grid[0].length; ++i) {
                g.drawLine(i * this.pixel, 0, i * this.pixel, this.grid[0].length * this.pixel);
            }

            for(i = 0; i < this.grid.length; ++i) {
                g.drawLine(0, i * this.pixel, this.grid.length * this.pixel, i * this.pixel);
            }
        }

    }

    private Image getImageFromString(String b64img) {
        byte[] image = Base64.getMimeDecoder().decode(b64img);
        ByteArrayInputStream bis = new ByteArrayInputStream(image);
        BufferedImage result = null;

        try {
            result = ImageIO.read(bis);
        } catch (IOException var6) {
            var6.printStackTrace();
        }

        return result;
    }

    public void desenhaGrid(Graphics g) {
        String norte = "/9j/4AAQSkZJRgABAQEAYABgAAD/4QAWRXhpZgAASUkqAAgAAAAAAAAAAAD/2wBDAAsHCAkIBwsJCQkMCwsNEBoREA8PECAXGBMaJiIoKCYiJSQqMD0zKi05LiQlNUg1OT9BREVEKTNLUEpCTz1DREH/2wBDAQsMDBAOEB8RER9BLCUsQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUH/wAARCAAMABcDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDtb7X5lleK2TBDBNxGWz646VDbeIbyEkS4uMnjjH8q6C402zuW3zQKzcjd0P6VHa6ZaWIdoIgp9+a0Uo22M7WZWh1SW4uIoY7fyGCl5POGAB049eaK0Whi87zzGDIq7Qx9M0VlzrsaH//Z";
        if (this.grid != null) {
            for(int i = 0; i < this.grid.length; ++i) {
                for(int j = 0; j < this.grid[0].length; ++j) {
                    switch(this.grid[i][j]) {
                        case -1:
                            g.setColor(this.getCor(-1));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 0:
                            g.setColor(this.getCor(0));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 1:
                            g.setColor(this.getCor(1));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 2:
                            g.setColor(this.getCor(2));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 3:
                            g.setColor(this.getCor(3));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 4:
                            g.setColor(this.getCor(4));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 5:
                            g.setColor(this.getCor(0));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            g.setColor(this.getCor(5));
                            g.fillOval(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 6:
                            g.setColor(this.getCor(6));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 7:
                            g.setColor(this.getCor(7));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 8:
                            g.setColor(this.getCor(8));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 9:
                            g.setColor(this.getCor(9));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 10:
                            g.setColor(this.getCor(10));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            break;
                        case 100:
                        case 110:
                        case 120:
                        case 130:
                        case 140:
                        case 150:
                            g.setColor(this.getCor(0));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            g.setColor(this.getCor(2));
                            g.fillOval(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            g.drawString(" " + (this.grid[i][j] - 100) / 10, i * this.pixel, j * this.pixel);
                            break;
                        case 200:
                        case 210:
                        case 220:
                        case 230:
                        case 240:
                        case 250:
                            g.setColor(this.getCor(0));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            g.setColor(this.getCor(6));
                            g.fillOval(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                            g.drawString(" " + (this.grid[i][j] - 200) / 10, i * this.pixel, j * this.pixel);
                            break;
                        default:
                            g.setColor(this.getCor(10000));
                            g.fillRect(i * this.pixel, j * this.pixel, this.pixel, this.pixel);
                    }
                }
            }
        }

    }

    public Color getCor(int numGrid) {
        switch(numGrid) {
            case -1:
                return Color.WHITE;
            case 0:
                return Color.BLACK;
            case 1:
                return Color.BLUE;
            case 2:
                return Color.RED;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.YELLOW;
            case 5:
                return Color.CYAN;
            case 6:
                return Color.WHITE;
            case 7:
                return Color.WHITE;
            case 8:
                return Color.RED;
            case 9:
                return Color.RED;
            default:
                return Color.CYAN;
        }
    }

    public int[][] getGrid() {
        return this.grid;
    }

    public void setGrid(int[][] grid) {
        this.grid = grid;
        this.setPreferredSize(new Dimension(grid.length * this.pixel, grid[0].length * this.pixel));
    }

    public int getPixel() {
        return this.pixel;
    }

    public void setPixel(int pixel) {
        this.pixel = pixel;
    }

    public JPanel returnPanel() {
        return this;
    }
}
