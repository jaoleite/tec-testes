package br.com.tecconcursos.search;

import org.apache.lucene.index.IndexWriter;

public abstract class Indexacao<T> {

	static final String INDEX_DIRECTORY_ROOT = "D:\\desenvolvimento\\arquivos\\index";
	
	abstract void indexarTudo();
	
	abstract void indexar(T t);
	
	protected void indexar(T t, IndexWriter indexWriter) {
		/*String html = questao.getEnunciado();
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		String content = doc.text().trim();
		Document document = new Document();
		document.add(new Field(FIELD_ID, questao.getId().toString(), Field.Store.YES, Field.Index.ANALYZED));
		Field field = new Field(FIELD_ENUNCIADO, content, Field.Store.YES, Field.Index.ANALYZED);
		document.add(field);
		indexWriter.addDocument(document);*/
	}
	
	/*private List<java.lang.reflect.Field> listarCamposParaIndexacao(T t) {
		return null;
	}*/
	
}


