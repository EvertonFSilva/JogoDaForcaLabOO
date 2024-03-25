package br.edu.iff.bancodepalavras.dominio.tema.emmemoria;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.bancodepalavras.dominio.tema.Tema;
import br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import br.edu.iff.repository.RepositoryException;

public class MemoriaTemaRepository implements TemaRepository {

	private List<Tema> pool;
	private static MemoriaTemaRepository soleInstance;

	public static MemoriaTemaRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new MemoriaTemaRepository();
		}
		return soleInstance;
	}

	private MemoriaTemaRepository() {
		this.pool = new ArrayList<Tema>();
	}

	@Override
	public long getProximoId() {
		return this.pool.size() + 1;
	}

	@Override
	public Tema getPorId(long id) {
		for (Tema tema : this.pool) {
			if (tema.getId() == id) {
				return tema;
			}
		}
		return null;
	}

	@Override
	public Tema[] getPorNome(String nome) {
		List<Tema> temasEscolhidos = new ArrayList<Tema>();
		for (Tema tema : this.pool) {
			if (tema.getNome().compareTo(nome) == 0) {
				temasEscolhidos.add(tema);
			}
		}

		return temasEscolhidos.toArray(new Tema[temasEscolhidos.size()]);
	}

	@Override
	public Tema[] getTodos() {
		return this.pool.toArray(new Tema[this.pool.size()]);
	}

	@Override
	public void inserir(Tema tema) throws RepositoryException {
		if (this.pool.contains(tema)) {
			throw new RepositoryException();
		}
		this.pool.add(tema);
	}

	@Override
	public void atualizar(Tema tema) throws RepositoryException {
		if (!this.pool.contains(tema)) {
			throw new RepositoryException();
		}

		for (int i = 0; i < this.pool.size(); i++) {
			Tema temaAtual = this.pool.get(i);
			if (temaAtual.getId() == tema.getId()) {
				this.pool.set(i, tema);
				return;
			}
		}
	}

	@Override
	public void remover(Tema tema) throws RepositoryException {
		if (!this.pool.contains(tema)) {
			throw new RepositoryException();
		}
		this.pool.remove(tema);
	}

}
