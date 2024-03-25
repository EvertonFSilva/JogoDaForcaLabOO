package br.edu.iff.bancodepalavras.dominio.letra;

public abstract class LetraFactoryImpl implements LetraFactory {

	private Letra[] pool;
	private Letra encoberta;

	protected LetraFactoryImpl() {
		this.pool = new Letra[26];
		this.encoberta = null;
	}

	@Override
	public final Letra getLetra(char codigo) {
		if (!(codigo >= 'a' && codigo <= 'z')) {
			throw new RuntimeException("O caractere não é válido");
		}
		int posicaoArmazenada = codigo - 'a';
		Letra letraDesejada = this.pool[posicaoArmazenada];
		if (letraDesejada == null) {
			letraDesejada = this.criarLetra(codigo);
			this.pool[posicaoArmazenada] = letraDesejada;
		}
		return letraDesejada;
	}

	@Override
	public final Letra getLetraEncoberta() {
		if (encoberta == null) {
			this.encoberta = criarLetra('*');
		}
		return this.encoberta;
	}

	protected abstract Letra criarLetra(char codigo);

}
