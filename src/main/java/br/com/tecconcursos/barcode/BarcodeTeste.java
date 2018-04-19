package br.com.tecconcursos.barcode;

public class BarcodeTeste {
	
	/*private int imageHeigth;
	private int imageWidth;
	private Color imageBarColor;
	private Color imageBackgroundColor;
	private double imageMagnification;
	private BufferedImage image = null;
	private int bufferedImageType;
	private int imageStartRenderAxisX;
	private int imageStartRenderAxisY;
	private String imageFormatName = "png";
	private DataType dataType;
	
	private String codigoBarras;
	private ConfiguracaoCodigoBarras configuracao;
	private Symbol symbol;
	
	public BarcodeTeste() {}
	
	public BarcodeTeste configuracao(ConfiguracaoCodigoBarras configuracao) {
		this.configuracao = configuracao;
		falharSeConfiguracaoNull();
		return this;
	}
	
	public BarcodeTeste comConfiguracaoPadrao() {
		configuracao(ConfiguracaoCodigoBarras.configuracaoPadrao());
		return this;
	}
	
	public BarcodeTeste codigoDeBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
		falharSeCodigoBarrasNull().and().falharSeConfiguracaoNull();
		codigoBarras = codigoBarras.replaceAll("\\D", "");
		this.codigoBarras = codigoBarras;
		return this;
	}
	
	public BarcodeTeste gerarCodigoBarrasFebraban() {
		falharSeCodigoBarrasNull().and().falharSeConfiguracaoNull();
		this.symbol = getCode2Of5InterleavedMode();
		this.symbol.setContent(this.codigoBarras);
		
		buildImageConfigurations();
		gerarImagem();
		
		return this;
	}
	
	public void writeTo(OutputStream output) throws IOException {
		falharSeCodigoBarrasNull().and().falharSeConfiguracaoNull().and().falharSeSymbolNull();
		ImageIO.write(this.image, this.imageFormatName, output);
	}
	
	private void buildImageConfigurations() {
		this.imageHeigth = configuracao.getAltura();
		this.imageWidth = configuracao.getLargura();
		this.imageBarColor = configuracao.getCorDasBarras();
		this.imageBackgroundColor = configuracao.getCorDeFundo();
		this.imageMagnification = configuracao.getAmplitude();
		this.bufferedImageType = BufferedImage.TYPE_INT_RGB;
		this.imageStartRenderAxisX = 0;
		this.imageStartRenderAxisY = 0;
		this.dataType = DataType.ECI; //default ECI
	}
	
	private void gerarImagem() {
		this.image = new BufferedImage(this.imageWidth, this.imageHeigth, this.bufferedImageType);
		image.createGraphics();
		
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		graphics.setColor(this.imageBackgroundColor);
		graphics.fillRect(this.imageStartRenderAxisX, this.imageStartRenderAxisY, this.imageWidth, this.imageHeigth);
		graphics.setColor(this.imageBarColor);
		
		Java2DRenderer java2dRenderer = new Java2DRenderer(graphics, this.imageMagnification, this.imageBackgroundColor, this.imageBarColor);
		java2dRenderer.render(this.symbol);
	}
	
	private Code2Of5 getCode2Of5InterleavedMode() {
		Code2Of5 symbol = new Code2Of5();
		symbol.setInterleavedMode();
		symbol.setBarHeight(configuracao.getAlturaCodigoDeBarras());
		symbol.setFontSize(configuracao.getTamanhoFonte());
		symbol.setQuietZoneHorizontal(configuracao.getLarguraBorda());
		symbol.setQuietZoneVertical(configuracao.getLarguraBorda());
		symbol.setDataType(dataType);
		return symbol;
	}
	
	private BarcodeTeste and() {
		return this;
	}
	
	private BarcodeTeste falharSeConfiguracaoNull() {
		if(configuracao == null) {
			throw new RuntimeException("configuracao esta nulo");
		}
		return this;
	}
	
	private BarcodeTeste falharSeCodigoBarrasNull() {
		if(Util.vazio(codigoBarras)) {
			throw new RuntimeException("codigo de barras vazio");
		}
		return this;
	}
	
	private BarcodeTeste falharSeSymbolNull() {
		if(this.symbol == null) {
			throw new RuntimeException("o códido de barras não foi gerado");
		}
		return this;
	}
	
	private static final String FILE = "D:\\Desenvolvimento\\arquivos\\imagens\\barcode.png";
	private static final String BARCODE_TEST = "23791.22928 50000.859939 94000.046907 5 73040000002990";
	
	public static void main(String[] args) throws Exception {
		FileOutputStream stream = new FileOutputStream(new File(FILE));
		new BarcodeTeste().comConfiguracaoPadrao().codigoDeBarras(BARCODE_TEST).gerarCodigoBarrasFebraban().writeTo(stream);
	}*/

}
