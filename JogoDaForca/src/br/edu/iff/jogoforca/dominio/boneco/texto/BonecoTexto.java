package br.edu.iff.jogoforca.dominio.boneco.texto;

import br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco {

	private static BonecoTexto soleInstance;

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
		switch (partes) {
		case 0:
			System.out.println(" ");
			break;
		case 1:
			System.out.println("cabeça");
			break;
		case 2:
			System.out.println("cabeça, olho esquerdo");
			break;
		case 3:
			System.out.println("cabeça, olho esquerdo, olho direito");
			break;
		case 4:
			System.out.println("cabeça, olho esquerdo, olho direito, nariz");
			break;
		case 5:
			System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca");
			break;
		case 6:
			System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco");
			break;
		case 7:
			System.out.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo");
			break;
		case 8:
			System.out
					.println("cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito");
			break;
		case 9:
			System.out.println(
					"cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda");
			break;
		case 10:
			System.out.println(
					"cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda, perna direita");
			break;
		default:
			throw new IllegalArgumentException("Valor inválido passado para exibir o boneco");
		}
	}
}
