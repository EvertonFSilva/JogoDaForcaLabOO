package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl {

	private Palavra palavra;
	private boolean[] posicoesDescobertas;
	private String palavraArriscada = null;

	static Item criar(long id, Palavra palavra) {
		return new Item(id, palavra);
	}

	public static Item reconstruir(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
		return new Item(id, palavra, posicoesDescobertas, palavraArriscada);
	}

	private Item(long id, Palavra palavra) {
		super(id);
		this.palavra = palavra;
		this.posicoesDescobertas = new boolean[this.palavra.getTamanho()];
	}

	private Item(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
		super(id);
		this.palavra = palavra;
		this.posicoesDescobertas = new boolean[this.palavra.getTamanho()];
		for (int posicao : posicoesDescobertas) {
			this.posicoesDescobertas[posicao] = true;
		}
		this.palavraArriscada = palavraArriscada;
	}

	public Palavra getPalavra() {
		return palavra;
	}

	public Letra[] getLetrasDescobertas() {
		Letra[] letrasDescobertas = new Letra[this.palavra.getTamanho()];
		int index = 0;
		for (int posicaoAtual = 0; posicaoAtual < this.palavra.getTamanho(); posicaoAtual++) {
			if (this.posicoesDescobertas[posicaoAtual]) {
				letrasDescobertas[index++] = this.palavra.getLetra(posicaoAtual);
			}
		}
		return letrasDescobertas;
	}

	public Letra[] getLetrasEncobertas() {
		Letra[] letrasEncobertas = new Letra[this.palavra.getTamanho()];
		int index = 0;
		for (int posicaoAtual = 0; posicaoAtual < this.palavra.getTamanho(); posicaoAtual++) {
			if (!this.posicoesDescobertas[posicaoAtual]) {
				letrasEncobertas[index++] = this.palavra.getLetra(posicaoAtual);
			}
		}
		return letrasEncobertas;
	}

	public int qtdeLetrasEncobertas() {
		int quantidade = 0;
		for (boolean posicao : this.posicoesDescobertas) {
			if (!posicao) {
				quantidade++;
			}
		}
		return quantidade;
	}

	public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
		return this.qtdeLetrasEncobertas() * valorPorLetraEncoberta;
	}

	public boolean descobriu() {
		return this.acertou() || this.qtdeLetrasEncobertas() == 0;
	}

	public void exibir(Object contexto) {
		this.palavra.exibir(contexto, this.posicoesDescobertas);
	}

	boolean tentar(char codigo) {
		int[] posicoes = palavra.tentar(codigo);
		for (int posicao : posicoes) {
			posicoesDescobertas[posicao] = true;
		}
		return posicoes.length > 0;
	}

	void arriscar(String palavra) {
		this.palavraArriscada = palavra;
	}

	public String getPalavraArriscada() {
		return this.palavraArriscada;
	}

	public boolean arriscou() {
		return this.palavraArriscada != null;
	}

	public boolean acertou() {
		return this.palavra.comparar(this.palavraArriscada);
	}
}
