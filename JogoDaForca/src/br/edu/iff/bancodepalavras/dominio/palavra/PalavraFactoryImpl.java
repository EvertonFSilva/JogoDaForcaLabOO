package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.factory.EntityFactory;

public class PalavraFactoryImpl extends EntityFactory implements PalavraFactory {

	private static PalavraFactoryImpl soleInstance;

	public static void createSoleInstance(PalavraRepository repository) {
		if (soleInstance == null) {
			soleInstance = new PalavraFactoryImpl(repository);
		}
	}

	public static PalavraFactoryImpl getSoleInstance() {
		if (soleInstance == null) {
			throw new RuntimeException("CreateSoleInstance não iniciado.");
		}
		return soleInstance;
	}

	private PalavraFactoryImpl(PalavraRepository repository) {
		super(repository);
	}

	private PalavraRepository getPalavraRepository() {
		return (PalavraRepository) this.getRepository();
	}

	@Override
	public Palavra getPalavra(String palavra, Tema tema) {
		return Palavra.criar(this.getPalavraRepository().getProximoId(), palavra, tema);
	}

}
