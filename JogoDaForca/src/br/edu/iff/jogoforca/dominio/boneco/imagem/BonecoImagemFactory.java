package br.edu.iff.jogoforca.dominio.boneco.imagem;

import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;

public class BonecoImagemFactory implements BonecoFactory {

	private static BonecoImagemFactory soleInstance;

	public static BonecoImagemFactory getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new BonecoImagemFactory();
		}
		return soleInstance;
	}

	private BonecoImagemFactory() {

	}

	@Override
	public Boneco getBoneco() {
		return BonecoImagem.getSoleInstance();
	}

}
