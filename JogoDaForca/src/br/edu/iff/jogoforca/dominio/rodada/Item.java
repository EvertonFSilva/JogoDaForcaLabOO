package br.edu.iff.jogoforca.dominio.rodada;

import java.util.ArrayList;

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

	private Item(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
	    super(id);
	    this.palavra = palavra;
	    this.posicoesDescobertas = new boolean[this.palavra.getTamanho()];
	    if (posicoesDescobertas != null) {
	        for (int posicao : posicoesDescobertas) {
	            this.posicoesDescobertas[posicao] = true;
	        }
	    }
	    this.palavraArriscada = palavraArriscada;
	}

	private Item(long id, Palavra palavra) {
	    this(id, palavra, null, null);
	}

	public Palavra getPalavra() {
		return palavra;
	}

	public Letra[] getLetrasDescobertas() {
		ArrayList<Letra> letrasDescobertasLista = new ArrayList<Letra>();
		for (int posicaoAtual = 0; posicaoAtual < this.palavra.getTamanho(); posicaoAtual++) {
			if (this.posicoesDescobertas[posicaoAtual]) {
				letrasDescobertasLista.add(this.palavra.getLetra(posicaoAtual));
			}
		}
		return letrasDescobertasLista.toArray(new Letra[letrasDescobertasLista.size()]);
	}

	public Letra[] getLetrasEncobertas() {
		ArrayList<Letra> letrasDescobertasLista = new ArrayList<Letra>();
		for (int posicaoAtual = 0; posicaoAtual < this.palavra.getTamanho(); posicaoAtual++) {
			if (!this.posicoesDescobertas[posicaoAtual]) {
				letrasDescobertasLista.add(this.palavra.getLetra(posicaoAtual));
			}
		}
		return letrasDescobertasLista.toArray(new Letra[letrasDescobertasLista.size()]);
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
