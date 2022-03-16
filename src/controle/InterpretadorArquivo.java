package controle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class InterpretadorArquivo {
    public InterpretadorArquivo() {
    }

    public int[][] leArquivo(String caminhoArquivo) {
        int[][] matrizSimulacao = (int[][])null;
        Vector vectorLinhaMatriz = new Vector();

        try {
            BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));

            for(String line = br.readLine(); line != null; line = br.readLine()) {
                String[] linhaMatriz = line.split(" ");
                vectorLinhaMatriz.add(linhaMatriz);
            }
        } catch (FileNotFoundException var8) {
            var8.printStackTrace();
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        int totalLinhas = ((String[])vectorLinhaMatriz.get(0)).length;
        int totalColunas = vectorLinhaMatriz.size();
        matrizSimulacao = new int[totalLinhas][totalColunas];

        for(int i = 0; i < totalLinhas; ++i) {
            for(int j = 0; j < totalColunas; ++j) {
                matrizSimulacao[j][i] = Integer.parseInt(((String[])vectorLinhaMatriz.get(i))[j]);
            }
        }

        return matrizSimulacao;
    }
}