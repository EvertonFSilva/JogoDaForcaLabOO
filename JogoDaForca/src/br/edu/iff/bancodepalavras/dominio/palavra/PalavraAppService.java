package br.edu.iff.bancodepalavras.dominio.palavra;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

public class PalavraAppService {

	private PalavraRepository palavraRepository;
	private TemaRepository temaRepository;
	private PalavraFactory palavraFactory;

	private static PalavraAppService soleInstance;

	public static void createSoleInstance(PalavraRepository palavraRepository, TemaRepository temaRepository,
			PalavraFactory palavraFactory) {
		if (soleInstance == null) {
			soleInstance = new PalavraAppService(palavraRepository, temaRepository, palavraFactory);
		}
	}

	public static PalavraAppService getSoleInstance() {
		if (soleInstance == null) {
			throw new RuntimeException("CreateSoleInstance não iniciado.");
		}
		return soleInstance;
	}

	private PalavraAppService(PalavraRepository palavraRepository, TemaRepository temaRepository,
			PalavraFactory palavraFactory) {
		this.palavraRepository = palavraRepository;
		this.temaRepository = temaRepository;
		this.palavraFactory = palavraFactory;
	}

	public boolean novaPalavra(String palavra, long temaId) {
		Tema tema = this.temaRepository.getPorId(temaId);

		if (tema == null) {
			throw new RuntimeException("O temaId não corresponde a um Tema existente no repositório de Tema.");
		}

		if (this.palavraRepository.getPalavra(palavra) != null) {
			return true;
		} else {
			try {
				this.palavraRepository.inserir(this.palavraFactory.getPalavra(palavra, tema));
				return true;
			} catch (RepositoryException e) {
				System.out.println(e.getMessage());
				return false;
			}
		}
	}
}
