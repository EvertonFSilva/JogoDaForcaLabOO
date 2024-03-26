package br.edu.iff.bancodepalavras.dominio.palavra;

import java.util.ArrayList;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.dominio.ObjetoDominioImpl;

public class Palavra extends ObjetoDominioImpl {

	private Tema tema;
	private static LetraFactory letraFactory;
	private Letra encoberta;
	private Letra[] palavra;

	public static void setLetraFactory(LetraFactory factory) {
		letraFactory = factory;
	}

	public LetraFactory getLetraFactory() {
		return letraFactory;
	}

	public static Palavra criar(long id, String palavra, Tema tema) {
		if (letraFactory == null) {
			throw new RuntimeException("O LetraFactory precisa ser iniciado primeiro");
		}
		return new Palavra(id, palavra, tema);
	}

	public static Palavra reconstruir(long id, String palavra, Tema tema) {
		if (letraFactory == null) {
			throw new RuntimeException("O LetraFactory precisa ser iniciado primeiro");
		}
		return new Palavra(id, palavra, tema);
	}

	private Palavra(long id, String palavra, Tema tema) {
		super(id);
		this.palavra = new Letra[palavra.length()];
		for (int letraAtual = 0; letraAtual < palavra.length(); letraAtual++) {
			this.palavra[letraAtual] = letraFactory.getLetra(palavra.charAt(letraAtual));
		}
		this.encoberta = letraFactory.getLetraEncoberta();
		this.tema = tema;
	}

	public Letra[] getLetras() {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra deve ser inicializada primeiro");
		}
		return this.palavra.clone();
	}

	public Letra getLetra(int posicao) {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra deve ser inicializada primeiro");
		}

		if (posicao < 0 || posicao > getTamanho()) {
			throw new RuntimeException("Posição inválida");
		}

		return this.palavra[posicao];
	}

	public void exibir(Object contexto) {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra deve ser inicializada primeiro");
		}

		for (int posicaoAtual = 0; posicaoAtual < this.getTamanho(); posicaoAtual++) {
			this.palavra[posicaoAtual].exibir(contexto);
		}
	}

	public void exibir(Object contexto, boolean[] posicoes) {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra deve ser inicializada primeiro");
		}

		for (int posicaoAtual = 0; posicaoAtual < this.getTamanho(); posicaoAtual++) {
			if (posicoes[posicaoAtual]) {
				this.palavra[posicaoAtual].exibir(contexto);
			} else {
				this.encoberta.exibir(contexto);
			}
		}
	}

	public int[] tentar(char codigo) {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra deve ser inicializada primeiro");
		}

		ArrayList<Integer> posicoesEncontradasLista = new ArrayList<Integer>();
		for (int posicaoAtual = 0; posicaoAtual < this.getTamanho(); posicaoAtual++) {
			if (this.palavra[posicaoAtual].getCodigo() == codigo) {
				posicoesEncontradasLista.add(posicaoAtual);
			}
		}

		int[] posicoesEncontradasArray = new int[posicoesEncontradasLista.size()];
		for (int i = 0; i < posicoesEncontradasArray.length; i++) {
			posicoesEncontradasArray[i] = posicoesEncontradasLista.get(i);
		}
		return posicoesEncontradasArray;
	}

	public Tema getTema() {
		return tema;
	}

	public boolean comparar(String palavra) {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra deve ser inicializada primeiro");
		}
		if (palavra == null || palavra.length() != getTamanho()) {
			return false;
		}

		for (int posicao = 0; posicao < getTamanho(); posicao++) {
			if (this.palavra[posicao].getCodigo() != palavra.charAt(posicao)) {
				return false;
			}
		}

		return true;
	}

	public int getTamanho() {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra deve ser inicializada primeiro");
		}
		return this.palavra.length;
	}

	@Override
	public String toString() {
		if (this.palavra == null) {
			throw new RuntimeException("A palavra deve ser inicializada primeiro");
		}

		StringBuilder builder = new StringBuilder();
		for (Letra letra : palavra) {
			builder.append(letra.getCodigo());
		}
		return builder.toString();
	}
}
