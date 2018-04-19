package br.com.tecconcursos.pdf.reader;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;
import org.apache.pdfbox.util.PDFTextStripperByArea;

public class Pdf {
	
	private static final String DIRETORIO = "D:\\desenvolvimento\\arquivos\\pdfs";
	private static final String EXTENSAO_PDF = ".pdf";
	private static final String SPLIT = "Código do erro Mensagem de erro";
	private static final String CONTAINS = "Erro:";
	private static final String INDEX_OF = "Stack Trace:";
	private static final String CONTEM_ASSUNTO = "Ocorreu um erro ao enviar para o Mercado Pago";
	private static final String CONTEM_URL = "https://mail.google.com/mail";
	private static final String ARQUIVO_DE_SAIDA = "C:\\Users\\Jão\\Desktop\\Desenvolvimento\\Tec\\Mercado Pago\\problemas\\erros.log";
	
	private List<String[]> list = null;
	private Set<String> set = null;
	private FileOutputStream stream = null;
	
	public List<String[]> listaErros() {
		return list;
	}

	public Pdf() {
		set = new HashSet<String>();
		list = new ArrayList<String[]>();
		try {
			stream = new FileOutputStream(new File(ARQUIVO_DE_SAIDA));
			leitura();
			montarLista();
			IOUtils.closeQuietly(stream);
			//impressao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void leitura() throws Exception {
		File dir = new File(DIRETORIO);
		File[] files = dir.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File file) {
				boolean isPdf = file.exists() && file.isFile() && file.getName().endsWith(EXTENSAO_PDF);
				return isPdf;
			}
		});
		
		for (File file : files) {
			montarListaDeErros(file);
		}
	}

	protected void montarListaDeErros(File file) throws Exception {
		PDDocument document = PDDocument.load(file);
		
		PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		stripper.setSortByPosition( true );
		PDFTextStripper text = new PDFTextStripper();
		String st = text.getText(document);
		
		IOUtils.write(st, stream);
		
		String[] strings = st.split(SPLIT);
		for (String string : strings) {
			set.add(string);
		}
		
		document.close();
	}
	
	protected void montarLista() {
		for (String string : set) {
			if(string.contains(CONTAINS)) {
				String texto = string.substring(2, string.indexOf(INDEX_OF));
				String[] array = montarArrayDeErros(texto);
				list.add(array);
			}
		}
	}
	
	protected String[] montarArrayDeErros(String texto) {
		String[] arrray = texto.split("\n");
		List<String> list = new ArrayList<String>();
		for (String string : arrray) {
			boolean naoContemUrlOuAssunto = !string.contains(CONTEM_ASSUNTO) &&
					!string.contains(CONTEM_URL);
			if(naoContemUrlOuAssunto) {
				list.add(string.trim());
			}
		}
		
		return list.toArray(new String[list.size()]);
	}
	
	protected void impressao() {
		if(list != null && !list.isEmpty()) {
			for (String[] array : list) {
				int i = 0;
				for (String string : array) {
					StringBuilder builder = new StringBuilder();
					if(i > 0 ) {
						builder.append("\t");
					}
					builder.append(string);
					System.out.println(builder.toString());
					i++;
				}
				
				System.out.println();
			}
		}
	}
	
	public static void main(String[] args) {
		Pdf pdf = new Pdf();
		System.out.println(pdf.listaErros().size());
	}
	
}
