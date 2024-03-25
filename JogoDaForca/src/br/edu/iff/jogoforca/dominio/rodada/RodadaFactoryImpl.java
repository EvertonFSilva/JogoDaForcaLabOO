package br.edu.iff.jogoforca.dominio.rodada;

import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.factory.EntityFactory;

public abstract class RodadaFactoryImpl extends EntityFactory implements RodadaFactory {

	private TemaRepository temaRepository;
	private PalavraRepository palavraRepository;

	protected RodadaFactoryImpl(RodadaRepository repository, TemaRepository temaRepository,
			PalavraRepository palavraRepository) {
		super(repository);
		this.temaRepository = temaRepository;
		this.palavraRepository = palavraRepository;
	}

	public RodadaRepository getRodadaRepository() {
		return (RodadaRepository) this.getRepository();
	}

	public TemaRepository getTemaRepository() {
		return this.temaRepository;
	}

	public PalavraRepository getPalavraRepository() {
		return this.palavraRepository;
	}
}
