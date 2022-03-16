package algoritmo;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class Poupador extends ProgramaPoupador {

	private final int MV_PARADO = 0;
	private final int MV_CIMA = 1;
	private final int MV_BAIXO = 2;
	private final int MV_DIREITA = 3;
	private final int MV_ESQUERDA = 4;

	private final int VS_INDISPONIVEL = -2;
	private final int VS_MUNDO_EXTERIOR = -1;
	private final int VS_CELULA_VAZIA = 0;
	private final int VS_PAREDE = 1;
	private final int VS_BANCO = 3;
	private final int VS_MOEDA = 4;
	private final int VS_PASTILHA = 5;
	private final int VS_POUPADOR = 100;
	private final int VS_LADRAO = 200;

	private final int OLFATO_VAZIO = 0;
	private final int OLFATO_UM_ATRAS = 1;
	private final int OLFATO_DOIS_ATRAS = 2;
	private final int OLFATO_TRES_ATRAS = 3;
	private final int OLFATO_QUATRO_ATRAS = 4;
	private final int OLFATO_CINCO_ATRAS = 5;

	private final int UTILIDADE_NULA = 0;

	private int Estresse = 0;

	private List<Point> areaExplorada = new ArrayList<Point>();

	private class Movimento {
	
		protected int[] matrizVisao;
	
		protected int[] matrizOfaltivaLadroes;
	
		protected int[] matrizOfaltivaPoupadores;
	
		protected int moedas;
	
		protected int moedasNoBanco;
	
		protected int jogadasImunes;
	
		protected Point posicao;
	
		protected int utilidade;
	
		protected int movimentacaoRealizada;
	
		public Movimento(int[] matrizVisao, int[] matrizOfaltivaLadroes,
					int[] matrizOfaltivaPoupadores, int moedas, int moedasNoBanco,
					int jogadasImunes, Point posicao, int utilidade,
					int movimentacaoRealizada) {
			super();
			this.matrizVisao = matrizVisao;
			this.matrizOfaltivaLadroes = matrizOfaltivaLadroes;
			this.matrizOfaltivaPoupadores = matrizOfaltivaPoupadores;
			this.moedas = moedas;
			this.moedasNoBanco = moedasNoBanco;
			this.jogadasImunes = jogadasImunes;
			this.posicao = posicao;
			this.utilidade = utilidade;
			this.movimentacaoRealizada = movimentacaoRealizada;
		}
	
	}

	public int acao() {

		Movimento movimentoAtual = new Movimento(sensor.getVisaoIdentificacao(),
				sensor.getAmbienteOlfatoLadrao(),
				sensor.getAmbienteOlfatoPoupador(), sensor.getNumeroDeMoedas(),
				sensor.getNumeroDeMoedasBanco(),
				sensor.getNumeroJogadasImunes(), sensor.getPosicao(),
				UTILIDADE_NULA, MV_PARADO);

		List<Movimento> movimentos = proximaFuncao(movimentoAtual);

		Collections.sort(movimentos, new Comparator<Movimento>() {

			@Override
			public int compare(Movimento movimento1, Movimento movimento2) {
				return Integer.compare(movimento2.utilidade, movimento1.utilidade);
			}

		});

		Movimento melhorMovimento = movimentos.get(0);

		// Adiciona nas areas percorridas o proximo movimento
		if(!areaExplorada.contains(melhorMovimento.posicao)) {
			areaExplorada.add(melhorMovimento.posicao);
		} else {
			Estresse += 10;
			
		}

		if (Estresse > 50) {
			Estresse = 0; 
			return (int) (Math.random() * 5);
		} 

		return melhorMovimento.movimentacaoRealizada;
	}

	private List<Movimento> proximaFuncao(Movimento movimentoAtual) {

		List<Movimento> movimentosSucessores = new ArrayList<Movimento>();

		for (int i = 0; i < MV_ESQUERDA + 1; i++) {

			Movimento movimentoSucessor = null;

			switch (i) {

				case MV_PARADO:

					movimentoSucessor = gerarNovoMovimento(MV_PARADO);

					movimentosSucessores.add(movimentoSucessor);

					break;

				case MV_CIMA:

					movimentoSucessor = gerarNovoMovimento(MV_CIMA);

					movimentosSucessores.add(movimentoSucessor);

					break;

				case MV_BAIXO:

					movimentoSucessor = gerarNovoMovimento(MV_BAIXO);

					movimentosSucessores.add(movimentoSucessor);

					break;

				case MV_DIREITA:

					movimentoSucessor = gerarNovoMovimento(MV_DIREITA);

					movimentosSucessores.add(movimentoSucessor);

					break;

				case MV_ESQUERDA:

					movimentoSucessor = gerarNovoMovimento(MV_ESQUERDA);

					movimentosSucessores.add(movimentoSucessor);

					break;

				default:
					break;
			}

		}
		return movimentosSucessores;
	}

	private Movimento gerarNovoMovimento(int movimentacao) {

		int[] matrizVisao = new int[sensor.getVisaoIdentificacao().length];

		for (int i = 0; i < matrizVisao.length; i++) {
			matrizVisao[i] = sensor.getVisaoIdentificacao()[i];
		}

		int[] matrizOlfatoLadrao = new int[sensor.getAmbienteOlfatoLadrao().length];

		for (int i = 0; i < matrizOlfatoLadrao.length; i++) {
			matrizOlfatoLadrao[i] = sensor.getAmbienteOlfatoLadrao()[i];
		}

		int[] matrizOlfatoPoupador = new int[sensor.getAmbienteOlfatoLadrao().length];

		for (int i = 0; i < matrizOlfatoPoupador.length; i++) {
			matrizOlfatoPoupador[i] = sensor.getAmbienteOlfatoLadrao()[i];
		}


		Movimento movimentoSucessor = new Movimento(matrizVisao, matrizOlfatoLadrao,
				matrizOlfatoPoupador, sensor.getNumeroDeMoedas(),
				sensor.getNumeroDeMoedasBanco(),
				sensor.getNumeroJogadasImunes(), sensor.getPosicao(),
				UTILIDADE_NULA, movimentacao);

		final double x = sensor.getPosicao().getX();
		final double y = sensor.getPosicao().getY();

		

		switch (movimentacao) {

			case MV_PARADO:

				break;

			case MV_CIMA:

				movimentoSucessor.posicao.setLocation(x + 1, y);

				for (int i = 5; i <= 7; i++) {
					movimentoSucessor.matrizOfaltivaLadroes[i] = OLFATO_VAZIO;
					movimentoSucessor.matrizOfaltivaPoupadores[i] = OLFATO_VAZIO;
				}

				for (int i = 14; i <= 23; i++) {
					movimentoSucessor.matrizVisao[i] = VS_INDISPONIVEL;
				}
				break;

			case MV_BAIXO:

				movimentoSucessor.posicao.setLocation(x - 1, y);

				for (int i = 0; i <= 2; i++) {
					movimentoSucessor.matrizOfaltivaLadroes[i] = OLFATO_VAZIO;
					movimentoSucessor.matrizOfaltivaPoupadores[i] = OLFATO_VAZIO;
				}

				for (int i = 0; i <= 9; i++) {
					movimentoSucessor.matrizVisao[i] = VS_INDISPONIVEL;
				}

				break;

			case MV_DIREITA:

				movimentoSucessor.posicao.setLocation(x, y + 1);

				movimentoSucessor.matrizVisao[0] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[1] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[5] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[6] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[10] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[11] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[14] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[15] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[19] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[20] = VS_INDISPONIVEL;

				movimentoSucessor.matrizOfaltivaLadroes[0] = OLFATO_VAZIO;
				movimentoSucessor.matrizOfaltivaLadroes[3] = OLFATO_VAZIO;
				movimentoSucessor.matrizOfaltivaLadroes[5] = OLFATO_VAZIO;

				movimentoSucessor.matrizOfaltivaPoupadores[0] = OLFATO_VAZIO;
				movimentoSucessor.matrizOfaltivaPoupadores[3] = OLFATO_VAZIO;
				movimentoSucessor.matrizOfaltivaPoupadores[5] = OLFATO_VAZIO;

				break;

			case MV_ESQUERDA:

				movimentoSucessor.posicao.setLocation(x, y - 1);

				movimentoSucessor.matrizVisao[3] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[4] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[8] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[9] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[12] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[13] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[17] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[18] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[22] = VS_INDISPONIVEL;
				movimentoSucessor.matrizVisao[23] = VS_INDISPONIVEL;

				movimentoSucessor.matrizOfaltivaLadroes[2] = OLFATO_VAZIO;
				movimentoSucessor.matrizOfaltivaLadroes[4] = OLFATO_VAZIO;
				movimentoSucessor.matrizOfaltivaLadroes[7] = OLFATO_VAZIO;

				movimentoSucessor.matrizOfaltivaPoupadores[2] = OLFATO_VAZIO;
				movimentoSucessor.matrizOfaltivaPoupadores[4] = OLFATO_VAZIO;
				movimentoSucessor.matrizOfaltivaPoupadores[7] = OLFATO_VAZIO;

				break;

			default:
				break;
		}

		movimentoSucessor.utilidade = calculaUtilidade(movimentoSucessor);

		return movimentoSucessor;

	}
	private int calculaUtilidade(Movimento movimento) {

		int pesoAdicional = 0;


		if (movimento.movimentacaoRealizada == MV_PARADO) {
			pesoAdicional += 1000;
		}

		for (Point point : areaExplorada) {
			if (point.equals(movimento.posicao)) {
				pesoAdicional += 500;
				break;
			}
		}

		pesoAdicional += visaoImediata(movimento);
		
		return (utilidadeVisao(movimento) + utilidadeOlfato(movimento)) - pesoAdicional;
	}

	private int visaoImediata(Movimento movimento) {

		int pesoAdicionalImediato = 0;
		int contatoVisual = -999;

		switch (movimento.movimentacaoRealizada) {

			case MV_PARADO:

				break;

			case MV_CIMA:

				contatoVisual = movimento.matrizVisao[7];

				break;

			case MV_BAIXO:

				contatoVisual = movimento.matrizVisao[16];

				break;

			case MV_DIREITA:

				contatoVisual = movimento.matrizVisao[12];

				break;

			case MV_ESQUERDA:

				contatoVisual = movimento.matrizVisao[11];

				break;

			default:
				break;
		}

		switch (contatoVisual) {

			case VS_INDISPONIVEL:

				pesoAdicionalImediato += 100000;

				break;

			case VS_MUNDO_EXTERIOR:

				pesoAdicionalImediato += 100000;

				break;

			case VS_CELULA_VAZIA:

				pesoAdicionalImediato += 0;

				break;

			case VS_PAREDE:

				pesoAdicionalImediato += 100000;

				break;

			case VS_BANCO:

				pesoAdicionalImediato -= (30 * movimento.moedas) + (movimento.moedasNoBanco);

				break;

			case VS_MOEDA:

				pesoAdicionalImediato -= 100000;

				break;

			case VS_PASTILHA:

				if (movimento.jogadasImunes == 0 && movimento.moedas >= 6) {
					pesoAdicionalImediato -= movimento.moedas * 100;
				} else {
					pesoAdicionalImediato += 100;
				}

				break;

			case VS_POUPADOR:

				pesoAdicionalImediato += 100;

				break;

			case VS_LADRAO:

				pesoAdicionalImediato += 1000 * (movimento.moedas + 1);

			default:
				break;
		}
		return pesoAdicionalImediato;
	}

	private int utilidadeVisao(Movimento movimento) {

		int utilidadeVisual = 0;

		for (int i = 0; i < movimento.matrizVisao.length; i++) {

			int contatoVisual = movimento.matrizVisao[i];

			switch (contatoVisual) {

				case VS_INDISPONIVEL:

					utilidadeVisual -= 2;

					continue;

				case VS_MUNDO_EXTERIOR:

					utilidadeVisual += 0;

					continue;

				case VS_CELULA_VAZIA:

					utilidadeVisual += 50;

					break;

				case VS_PAREDE:

					utilidadeVisual += 0;

					break;

				case VS_BANCO:

					utilidadeVisual += (30 * movimento.moedas)
							+ (movimento.moedasNoBanco);

					break;

				case VS_MOEDA:

					utilidadeVisual += 2000;

					break;

				case VS_PASTILHA:

					if (movimento.jogadasImunes == 0 && movimento.moedas >= 10) {
						utilidadeVisual += movimento.moedas;
					} else {
						utilidadeVisual += 0;
					}

					break;

				case VS_POUPADOR:

					utilidadeVisual += -20;

					break;

				case VS_LADRAO:

					utilidadeVisual += -100 * (movimento.moedas + 1);

				default:
					break;
			}

		}

		return utilidadeVisual;

	}

	private int utilidadeOlfato(Movimento movimento) {

		int utilidadeOlfativa = 0;

		for (int i = 0; i < movimento.matrizOfaltivaLadroes.length; i++) {

			int contatoOlfativoLadrao = movimento.matrizOfaltivaLadroes[i];
			int contatoOlfativoPoupador = movimento.matrizOfaltivaLadroes[i];

			switch (contatoOlfativoLadrao) {

				case OLFATO_VAZIO:

					utilidadeOlfativa += 0;

					break;

				case OLFATO_UM_ATRAS:

					utilidadeOlfativa += -100;

					break;

				case OLFATO_DOIS_ATRAS:

					utilidadeOlfativa += -90;

					break;

				case OLFATO_TRES_ATRAS:

					utilidadeOlfativa += -80;

					break;

				case OLFATO_QUATRO_ATRAS:

					utilidadeOlfativa += -50;

					break;

				case OLFATO_CINCO_ATRAS:

					utilidadeOlfativa += -40;

					break;

				default:
					break;
			}

			switch (contatoOlfativoPoupador) {

				case OLFATO_VAZIO:

					utilidadeOlfativa += 0;

					break;

				case OLFATO_UM_ATRAS:

					utilidadeOlfativa += -100;

					break;

				case OLFATO_DOIS_ATRAS:

					utilidadeOlfativa += -90;

					break;

				case OLFATO_TRES_ATRAS:

					utilidadeOlfativa += -80;

					break;

				case OLFATO_QUATRO_ATRAS:

					utilidadeOlfativa += -50;

					break;

				case OLFATO_CINCO_ATRAS:

					utilidadeOlfativa += -40;

					break;

				default:
					break;
			}

		}

		return utilidadeOlfativa;
	}


}