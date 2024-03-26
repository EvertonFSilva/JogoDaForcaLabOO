package br.edu.iff.jogoforca.dominio.boneco.texto;

import br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco {

	private static BonecoTexto soleInstance;
	private final static String[] PARTES_DO_BONECO = { "", "cabeça", "olho esquerdo", "olho direito", "nariz", "boca",
			"tronco", "braço esquerdo", "braço direito", "perna esquerda", "perna direita" };

	public static BonecoTexto getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new BonecoTexto();
		}
		return soleInstance;
	}

	private BonecoTexto() {

	}

	@Override
	public void exibir(Object contexto, int partes) {
		if (partes < 0 || partes >= PARTES_DO_BONECO.length) {
			throw new IllegalArgumentException("Valor inválido passado para exibir o boneco");
		}

		StringBuilder boneco = new StringBuilder();
		for (int i = 1; i <= partes; i++) {
			boneco.append(PARTES_DO_BONECO[i]).append(", ");
		}
		if (partes > 0) {
			boneco.delete(boneco.length() - 2, boneco.length());
		}
		System.out.println(boneco.toString());
	}
}
