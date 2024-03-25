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

	public static void main(String[] args) throws RepositoryException, JogadorNaoEncontradoException {
		Aplicacao app = Aplicacao.getSoleInstance();
		app.configurar();
		TemaRepository temas = app.getRepositoryFactory().getTemaRepository();
		JogadorRepository jogadores = app.getRepositoryFactory().getJogadorRepository();
		JogadorFactory jogadorFactory = app.getJogadorFactory();
		TemaFactory temaFactory = app.getTemaFactory();
		definirTemas(temas, temaFactory);
		jogar(jogadores, jogadorFactory);
	}

	public static void definirTemas(TemaRepository temas, TemaFactory temaFactory) {
		String[] listaDeTemas = { "Frutas", "Veiculos", "Instrumentos", "Profissoes" };
		for (String tema : listaDeTemas) {
			try {
				temas.inserir(temaFactory.getTema(tema));
			} catch (RepositoryException e) {
				e.printStackTrace();
			}
		}

		String[] palavrasFrutas = { "banana", "maca", "uva", "laranja", "pera", "melancia", "abacaxi" };
		for (String palavra : palavrasFrutas) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra, temas.getPorNome(listaDeTemas[0])[0].getId());
		}

		String[] palavrasVeiculos = { "carro", "moto", "bicicleta", "onibus", "caminhao" };
		for (String palavra : palavrasVeiculos) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra, temas.getPorNome(listaDeTemas[1])[0].getId());
		}

		String[] palavrasInstrumentos = { "violino", "piano", "guitarra", "flauta", "bateria" };
		for (String palavra : palavrasInstrumentos) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra, temas.getPorNome(listaDeTemas[2])[0].getId());
		}

		String[] palavrasProfissoes = { "medico", "professor", "engenheiro", "advogado", "cozinheiro" };
		for (String palavra : palavrasProfissoes) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra, temas.getPorNome(listaDeTemas[3])[0].getId());
		}
	}

	public static void jogar(JogadorRepository jogadores, JogadorFactory jogadorFactory)
			throws RepositoryException, JogadorNaoEncontradoException {
		Rodada partida = RodadaAppService.getSoleInstance().novaRodada(obterJogadorDaRodada(jogadores, jogadorFactory));
		Scanner entrada = new Scanner(System.in);
		Object contexto = null;
		String escolha;
		String[] palavrasArriscadas = new String[partida.getPalavra().length];
		do {
			exibirInformacoes(partida, contexto);
			System.out.print("\nEscolha uma opção: 0 para tentar ou 1 para arriscar: ");
			escolha = entrada.nextLine();
			switch (escolha) {
			case "0":
				tentar(partida);
				break;
			case "1":
				arriscar(partida, palavrasArriscadas);
				break;
			default:
				System.out.println("Opção inválida");
				break;
			}
			System.out.println("===============================");
		} while (!partida.encerrou());
		encerrar(partida, contexto);
		RodadaAppService.getSoleInstance().salvarRodada(partida);
		entrada.close();
	}

	public static String obterJogadorDaRodada(JogadorRepository jogadores, JogadorFactory jogadorFactory)
			throws RepositoryException {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);

		System.out.print("Informe seu nome: ");
		String nomeJogador = entrada.nextLine();
		nomeJogador = nomeJogador.substring(0, 1).toUpperCase() + nomeJogador.substring(1).toLowerCase();

		System.out.println();

		jogadores.inserir(jogadorFactory.getJogador(nomeJogador));
		return nomeJogador;
	}

	public static void exibirInformacoes(Rodada partida, Object contexto) {
		System.out.println("================================");
		System.out.println("Tema: " + partida.getTema().getNome());
		System.out.println("Palavras: ");
		partida.exibirItens(contexto);
		System.out.println("Letras erradas: ");
		partida.exibirLetrasErradas(contexto);
		System.out.println("Boneco: ");
		partida.exibirBoneco(contexto);
		System.out.println("================================");
	}

	public static void arriscar(Rodada partida, String[] palavrasArriscadas) {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("## Arriscar ##");
		System.out.println("Digite as palavras:");
		for (int posicaoAtual = 0; posicaoAtual < palavrasArriscadas.length; posicaoAtual++) {
			System.out.print((posicaoAtual + 1) + "ª palavra: ");
			palavrasArriscadas[posicaoAtual] = entrada.nextLine().trim();
		}
		partida.arriscar(palavrasArriscadas);
	}

	public static void tentar(Rodada partida) {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("## Tentar ##");
		System.out.println("Tentativas Restantes: " + partida.getQtdeTentativasRestantes());
		System.out.print("Digite uma letra:");
		char letraTentada = entrada.nextLine().trim().charAt(0);
		if (!(letraTentada >= 'a' && letraTentada <= 'z')) {
			System.out.println("Letra inválida");
		} else {
			partida.tentar(letraTentada);
		}
	}

	public static void encerrar(Rodada partida, Object contexto) {
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		if (partida.descobriu()) {
			mostrarResultadoVitoria(partida);
		} else {
			mostrarResultadoDerrota(partida, contexto);
		}
		System.out.println();
	}

	public static void mostrarResultadoDerrota(Rodada partida, Object contexto) {
		System.out
				.println("O jogador " + partida.getJogador().getNome() + " não conseguiu adivinhar todas as palavras!");
		System.out.println("Número de tentativas: " + partida.getQtdeTentativas());
		System.out.println("Número de acertos: " + partida.getQtdeAcertos());
		System.out.println("Palavras corretas:");
		partida.exibirPalavras(contexto);
	}

	public static void mostrarResultadoVitoria(Rodada partida) {
		System.out.println("Parabéns para o jogador " + partida.getJogador().getNome() + "! Você venceu o jogo!");
		System.out.println("Número de tentativas: " + partida.getQtdeTentativas());
		System.out.println("Número de acertos: " + partida.getQtdeAcertos());
		System.out.println("Pontuação total: " + partida.calcularPontos());
	}
}