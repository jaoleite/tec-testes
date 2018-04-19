package br.com.tecconcursos.barcode;

import java.awt.Color;

public class ConfiguracaoCodigoBarras {

	private int largura;
	private int altura;
	private int tamanhoFonte;
	private int larguraBorda;
	private Color corDasBarras;
	private Color corDeFundo;
	private double amplitude;
	private int alturaCodigoDeBarras;
	
	public ConfiguracaoCodigoBarras() {}
	
	public ConfiguracaoCodigoBarras(int largura, int altura,
			int tamanhoFonte, int larguraBorda,
			Color corDasBarras, Color corDeFundo,
			double amplitude, int alturaCodigoDeBarras) {
		super();
		this.largura = largura;
		this.altura = altura;
		this.tamanhoFonte = tamanhoFonte;
		this.larguraBorda = larguraBorda;
		this.corDasBarras = corDasBarras;
		this.corDeFundo = corDeFundo;
		this.amplitude = amplitude;
		this.alturaCodigoDeBarras = alturaCodigoDeBarras;
	}

	public static ConfiguracaoCodigoBarras configuracaoPadrao() {
		return new ConfiguracaoCodigoBarras(750, 150, 10, 10,
				Color.BLACK, Color.WHITE, 1.6, 70);
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}

	public int getTamanhoFonte() {
		return tamanhoFonte;
	}

	public int getLarguraBorda() {
		return larguraBorda;
	}

	public Color getCorDasBarras() {
		return corDasBarras;
	}

	public Color getCorDeFundo() {
		return corDeFundo;
	}

	public double getAmplitude() {
		return amplitude;
	}

	public int getAlturaCodigoDeBarras() {
		return alturaCodigoDeBarras;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfiguracaoCodigoBarras [largura=");
		builder.append(largura);
		builder.append(", altura=");
		builder.append(altura);
		builder.append(", tamanhoFonte=");
		builder.append(tamanhoFonte);
		builder.append(", larguraBorda=");
		builder.append(larguraBorda);
		builder.append(", corDasBarras=");
		builder.append(corDasBarras);
		builder.append(", corDeFundo=");
		builder.append(corDeFundo);
		builder.append(", amplitude=");
		builder.append(amplitude);
		builder.append(", alturaCodigoDeBarras=");
		builder.append(alturaCodigoDeBarras);
		builder.append("]");
		return builder.toString();
	}
	
}
