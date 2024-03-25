package br.edu.iff.jogoforca.dominio.jogador.emmemoria;

import java.util.ArrayList;
import java.util.List;

import br.edu.iff.jogoforca.dominio.jogador.Jogador;
import br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import br.edu.iff.repository.RepositoryException;

public class MemoriaJogadorRepository implements JogadorRepository {

	private List<Jogador> pool;
	private static MemoriaJogadorRepository soleInstance;

	public static MemoriaJogadorRepository getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new MemoriaJogadorRepository();
		}
		return soleInstance;
	}

	private MemoriaJogadorRepository() {
		this.pool = new ArrayList<Jogador>();
	}

	@Override
	public long getProximoId() {
		return this.pool.size() + 1;
	}

	@Override
	public Jogador getPorId(long id) {
		for (Jogador jogador : this.pool) {
			if (jogador.getId() == id) {
				return jogador;
			}
		}
		return null;
	}

	@Override
	public Jogador[] getPorNome(String nome) {
		List<Jogador> jogadoresEscolhidos = new ArrayList<Jogador>();
		for (Jogador jogador : this.pool) {
			if (jogador.getNome().compareTo(nome) == 0) {
				jogadoresEscolhidos.add(jogador);
			}
		}
		return jogadoresEscolhidos.toArray(new Jogador[jogadoresEscolhidos.size()]);
	}

	@Override
	public void inserir(Jogador jogador) throws RepositoryException {
		if (this.pool.contains(jogador)) {
			throw new RepositoryException();
		}
		this.pool.add(jogador);
	}

	@Override
	public void atualizar(Jogador jogador) throws RepositoryException {
		if (!this.pool.contains(jogador)) {
			throw new RepositoryException();
		}

		for (int i = 0; i < this.pool.size(); i++) {
			Jogador jogadorAtual = this.pool.get(i);
			if (jogadorAtual.getId() == jogador.getId()) {
				this.pool.set(i, jogador);
				return;
			}
		}
	}

	@Override
	public void remover(Jogador jogador) throws RepositoryException {
		if (!this.pool.contains(jogador)) {
			throw new RepositoryException();
		}
		this.pool.remove(jogador);
	}

}
