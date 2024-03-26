package br.edu.iff.bancodepalavras.dominio.palavra.emmemoria;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.repository.RepositoryException;

public class MemoriaPalavraRepository implements PalavraRepository {

	private List<Palavra> pool;
	private static MemoriaPalavraRepository soleInstance;

	public static MemoriaPalavraRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new MemoriaPalavraRepository();
		}
		return soleInstance;
	}

	private MemoriaPalavraRepository() {
		this.pool = new ArrayList<Palavra>();
	}

	@Override
	public long getProximoId() {
		return this.pool.size() + 1;
	}

	@Override
	public Palavra getPorId(long id) {
		for (Palavra palavra : this.pool) {
			if (palavra.getId() == id) {
				return palavra;
			}
		}
		return null;
	}

	@Override
	public Palavra[] getPorTema(Tema tema) {
		List<Palavra> palavrasEscolhidas = new ArrayList<Palavra>();
		for (Palavra palavra : this.pool) {
			if (palavra.getTema().equals(tema)) {
				palavrasEscolhidas.add(palavra);
			}
		}
		return palavrasEscolhidas.toArray(new Palavra[palavrasEscolhidas.size()]);
	}

	@Override
	public Palavra[] getTodas() {
		return this.pool.toArray(new Palavra[this.pool.size()]);
	}

	@Override
	public Palavra getPalavra(String palavra) {
		for (Palavra palavraAtual : this.pool) {
			if (palavraAtual.comparar(palavra)) {
				return palavraAtual;
			}
		}
		return null;
	}

	@Override
	public void inserir(Palavra palavra) throws RepositoryException {
		if (this.pool.contains(palavra)) {
			throw new RepositoryException();
		}
		this.pool.add(palavra);
	}

	@Override
	public void atualizar(Palavra palavra) throws RepositoryException {
		if (!this.pool.contains(palavra)) {
			throw new RepositoryException();
		}
	    for (int i = 0; i < this.pool.size(); i++) {
	        Palavra palavraAtual = this.pool.get(i);
	        if (palavraAtual.getId() == palavra.getId()) {
	            this.pool.set(i, palavra);
	            return;
	        }
	    }
	}

	@Override
	public void remover(Palavra palavra) throws RepositoryException {
		if (!this.pool.contains(palavra)) {
			throw new RepositoryException();
		}
		this.pool.remove(palavra);
	}

}
