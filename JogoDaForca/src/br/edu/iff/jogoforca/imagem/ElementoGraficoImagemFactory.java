package br.edu.iff.jogoforca.imagem;

import br.edu.iff.bancodepalavras.dominio.letra.Letra;
import br.edu.iff.bancodepalavras.dominio.letra.imagem.LetraImagemFactory;
import br.edu.iff.jogoforca.ElementoGraficoFactory;
import br.edu.iff.jogoforca.dominio.boneco.Boneco;
import br.edu.iff.jogoforca.dominio.boneco.imagem.BonecoImagemFactory;

public class ElementoGraficoImagemFactory implements ElementoGraficoFactory {

	private LetraImagemFactory letraImagemFactory;
	private BonecoImagemFactory bonecoImagemFactory;

	private static ElementoGraficoImagemFactory soleInstance;

	public static ElementoGraficoImagemFactory getSoleInstance() {
		if (soleInstance == null) {
			soleInstance = new ElementoGraficoImagemFactory();
		}
		return soleInstance;
	}

	private ElementoGraficoImagemFactory() {
		this.letraImagemFactory = LetraImagemFactory.getSoleInstance();
		this.bonecoImagemFactory = BonecoImagemFactory.getSoleInstance();
	}

	@Override
	public Boneco getBoneco() {
		return this.bonecoImagemFactory.getBoneco();
	}

	@Override
	public Letra getLetra(char codigo) {
		return this.letraImagemFactory.getLetra(codigo);
	}

	@Override
	public Letra getLetraEncoberta() {
		return this.letraImagemFactory.getLetraEncoberta();
	}

}
