package br.edu.iff.jogoforca.dominio.jogador;

import br.edu.iff.factory.EntityFactory;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory {

	private static JogadorFactoryImpl soleInstance;

	public static void createSoleInstance(JogadorRepository repository) {
		if (soleInstance == null) {
			soleInstance = new JogadorFactoryImpl(repository);
		}
	}

	public static JogadorFactoryImpl getSoleInstance() {
		if (soleInstance == null) {
			throw new RuntimeException("CreateSoleInstance não iniciado.");
		}
		return soleInstance;
	}

	private JogadorFactoryImpl(JogadorRepository repository) {
		super(repository);
	}

	private JogadorRepository getJogadorRepository() {
		return (JogadorRepository) this.getRepository();
	}

	@Override
	public Jogador getJogador(String nome) {
		return Jogador.criar(this.getJogadorRepository().getProximoId(), nome);
	}

}
