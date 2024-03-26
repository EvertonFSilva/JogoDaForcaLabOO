package br.edu.iff;

import java.util.Scanner;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.jogoforca.Aplicacao;
import br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import br.edu.iff.jogoforca.dominio.jogador.JogadorNaoEncontradoException;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.jogoforca.dominio.rodada.Rodada;
import br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import br.edu.iff.repository.RepositoryException;

public class Main {
	private static final Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			Aplicacao aplicacao = Aplicacao.getSoleInstance();
			aplicacao.configurar();
			TemaRepository temas = aplicacao.getRepositoryFactory().getTemaRepository();
			JogadorRepository jogador = aplicacao.getRepositoryFactory().getJogadorRepository();
			JogadorFactory jogadorFactory = aplicacao.getJogadorFactory();
			TemaFactory temaFactory = aplicacao.getTemaFactory();
			definirETemasEInserirPalavras(temas, temaFactory);
			iniciarJogo(jogador, jogadorFactory);
		} catch (RepositoryException e) {
			System.err.println("Erro: " + e.getMessage());
		}
	}

	private static void definirETemasEInserirPalavras(TemaRepository temas, TemaFactory temaFactory)
			throws RepositoryException {
		String[] listaDeTemas = { "Frutas", "Veiculos", "Instrumentos", "Profissoes" };
		for (String tema : listaDeTemas) {
			temas.inserir(temaFactory.getTema(tema));
		}

		String[][] palavrasPorTema = { { "banana", "maca", "uva", "laranja", "pera", "melancia", "abacaxi" },
				{ "carro", "moto", "bicicleta", "onibus", "caminhao" },
				{ "violino", "piano", "guitarra", "flauta", "bateria" },
				{ "medico", "professor", "engenheiro", "advogado", "cozinheiro" } };

		for (int i = 0; i < listaDeTemas.length; i++) {
			for (String palavra : palavrasPorTema[i]) {
				PalavraAppService.getSoleInstance().novaPalavra(palavra, temas.getPorNome(listaDeTemas[i])[0].getId());
			}
		}
	}

	private static void iniciarJogo(JogadorRepository jogadores, JogadorFactory jogadorFactory) {
		try {
			Rodada rodada = RodadaAppService.getSoleInstance().novaRodada(obterJogador(jogadores, jogadorFactory));
			String[] palavrasArriscadas = new String[rodada.getPalavra().length];
			Object contexto = null;
			
			while (!rodada.encerrou()) {
			    exibirInformacoesDaRodada(rodada, contexto);
			    System.out.print("\nEscolha uma opção: 0 para tentar ou 1 para arriscar: ");
			    String escolha = scanner.nextLine();
			    switch (escolha) {
			        case "0":
			            tentarLetra(rodada);
			            break;
			        case "1":
			            arriscarPalavras(rodada, palavrasArriscadas);
			            break;
			        default:
			            System.out.println("Opção inválida");
			            break;
			    }
			    System.out.println("===============================");
			}

			encerrarPartida(rodada, contexto);
			RodadaAppService.getSoleInstance().salvarRodada(rodada);
		} catch (RepositoryException | JogadorNaoEncontradoException e) {
			System.err.println("Erro: " + e.getMessage());
		}
	}

	private static String obterJogador(JogadorRepository jogadores, JogadorFactory jogadorFactory)
			throws RepositoryException {
		System.out.print("Informe seu nome: ");
		String nomeJogador = scanner.nextLine();
		nomeJogador = nomeJogador.substring(0, 1).toUpperCase() + nomeJogador.substring(1).toLowerCase();
		System.out.println();
		jogadores.inserir(jogadorFactory.getJogador(nomeJogador));
		return nomeJogador;
	}

	private static void exibirInformacoesDaRodada(Rodada rodada, Object contexto) {
		System.out.println("================================");
		System.out.println("Tema: " + rodada.getTema().getNome());
		System.out.println("Palavras: ");
		System.out.println();
		rodada.exibirItens(contexto);
		System.out.println();
		System.out.println("Letras Erradas: ");
		rodada.exibirLetrasErradas(contexto);
		System.out.println("Parte do Boneco: ");
		rodada.exibirBoneco(contexto);
		System.out.println("================================");
	}

	private static void arriscarPalavras(Rodada rodada, String[] palavrasArriscadas) {
		System.out.println("## Arriscar ##");
		System.out.println("Digite as palavras:");
		for (int posicaoAtual = 0; posicaoAtual < palavrasArriscadas.length; posicaoAtual++) {
			System.out.print((posicaoAtual + 1) + "ª palavra: ");
			palavrasArriscadas[posicaoAtual] = scanner.nextLine().toLowerCase().trim();
		}
		rodada.arriscar(palavrasArriscadas);
	}

	private static void tentarLetra(Rodada rodada) {
		System.out.println("## Tentar ##");
		System.out.println("Tentativas Restantes: " + rodada.getQtdeTentativasRestantes());
		System.out.print("Digite uma letra:");
		char letraTentada = scanner.nextLine().trim().toLowerCase().charAt(0);
		if (!(letraTentada >= 'a' && letraTentada <= 'z')) {
			System.out.println("Letra inválida");
		} else {
			rodada.tentar(letraTentada);
		}
	}

	private static void encerrarPartida(Rodada rodada, Object contexto) {
		System.out.println("================================");
		if (rodada.descobriu()) {
			exibirResultadoVitoria(rodada);
		} else {
			exibirResultadoDerrota(rodada, contexto);
		}
		System.out.println();
	}

	private static void exibirResultadoVitoria(Rodada rodada) {
		System.out.println("Parabéns para o jogador " + rodada.getJogador().getNome() + "! Você venceu o jogo!");
		System.out.println("Número de tentativas: " + rodada.getQtdeTentativas());
		System.out.println("Número de acertos: " + rodada.getQtdeAcertos());
		System.out.println("Pontuação total: " + rodada.calcularPontos());
	}

	private static void exibirResultadoDerrota(Rodada rodada, Object contexto) {
		System.out
				.println("O jogador " + rodada.getJogador().getNome() + " não conseguiu adivinhar todas as palavras!");
		System.out.println("Número de tentativas: " + rodada.getQtdeTentativas());
		System.out.println("Número de acertos: " + rodada.getQtdeAcertos());
		System.out.println("Palavras corretas:");
		rodada.exibirPalavras(contexto);
	}
}
