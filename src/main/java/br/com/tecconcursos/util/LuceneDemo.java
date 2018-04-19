package br.com.tecconcursos.util;


/*****************************************************************/
/* Copyright 2013 Code Strategies                                */
/* This code may be freely used and distributed in any project.  */
/* However, please do not remove this credit if you publish this */
/* code in paper or electronic form, such as on a web site.      */
/*****************************************************************/

public class LuceneDemo {

	/*private static final String FIELD_ID = "id";
	private static final String FIELD_ENUNCIADO = "enunciado";
	private static final String INDEX_DIRECTORY = "D:\\desenvolvimento\\arquivos\\index";
	//private static final String INDEX_DIRECTORY = "C:\\Desenvolvimento\\tec\\lucene\\indexes\\QuestaoObjetiva";
	
	
	private CadastroQuestaoObjetivaService service = null;
	
	private LuceneDemo() {
		this(false);
	}
	
	private LuceneDemo(boolean instanceService) {
		if(instanceService) {
			this.service = ApplicationContext.find(CadastroQuestaoObjetivaService.class);			
		}
	}
	
	public static LuceneDemo of() {
		return new LuceneDemo();
	}
	
	public static LuceneDemo of(boolean instanceService) {
		return new LuceneDemo(instanceService);
	}
	
	public static void main(String[] args) throws Exception {
		boolean indexacao = false;
		LuceneDemo luceneDemo = LuceneDemo.of(indexacao);
		if(indexacao) {
			luceneDemo.criarIndexQuestaoObjetiva();
		}
		luceneDemo.searchIndex("Analistas, especialmente vindos da biologia, das");
		// menor id = 2 / maior id = 277748
		//luceneDemo.searchIndexById("277748");
		
	}
	
	public void criarIndexQuestaoObjetiva() throws Exception {
		System.out.println("Início");
		int quantidadeQuestoesPorPagina = 1000;
		LocalTime inicio = LocalTime.now();
		PageRequest pagina = new PageRequest(1, quantidadeQuestoesPorPagina);
		//int quantidadeQuestoes = service.quantidadeQuestoes();
		//int quantidadeIteracao = (quantidadeQuestoes / quantidadeQuestoesPorPagina) + 1;
		
		Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
		IndexDeletionPolicy deletionPolicy = new KeepOnlyLastCommitDeletionPolicy();
		
		boolean recreateIndexIfExists = true;
		Analyzer analyzer = new	BrazilianAnalyzer();
		IndexWriter indexWriter = new IndexWriter(directory, analyzer, recreateIndexIfExists, deletionPolicy, IndexWriter.MaxFieldLength.UNLIMITED);
		
		for (int i = 1; i <= 1; i++) {
			pagina.setCurrentPage(i);
			List<QuestaoObjetiva> questoes = service.listarPorQuantidade(pagina);
			System.out.println("Iteração " + i);
			for (QuestaoObjetiva questao : questoes) {
				createIndex(questao, indexWriter);
			}
			indexWriter.commit();
		}
		indexWriter.optimize();
		indexWriter.close();
		LocalTime fim = LocalTime.now();
		LocalTime t = fim.minus(inicio.toNanoOfDay(), ChronoUnit.NANOS);
		System.out.println();
		System.out.println("Fim: " + t);
	}

	public void createIndex(QuestaoObjetiva questao, IndexWriter indexWriter) throws CorruptIndexException, LockObtainFailedException, IOException {
		String html = questao.getEnunciado();
		org.jsoup.nodes.Document doc = Jsoup.parse(html);
		String content = doc.text().trim();
		Document document = new Document();
		document.add(new Field(FIELD_ID, questao.getId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
		Field field = new Field(FIELD_ENUNCIADO, content, Field.Store.COMPRESS, Field.Index.ANALYZED);
		document.add(field);
		indexWriter.addDocument(document);
	}

	public void searchIndex(String searchString) throws IOException, ParseException {
		System.out.println("Searching for '" + searchString + "'");
		Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
		if(IndexReader.indexExists(directory)) {
			IndexReader indexReader = IndexReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			//Analyzer analyzer = new BrazilianAnalyzer();
			
			Term term = new Term(FIELD_ENUNCIADO, searchString);
			Query query = new FuzzyQuery(term);
			
			
			//QueryParser queryParser = new QueryParser(FIELD_ENUNCIADO, analyzer);
			//Query query = queryParser.parse(searchString);
			HitCollectorImpl collector = new HitCollectorImpl(indexSearcher);
			indexSearcher.search(query, collector);
			System.out.println("Number of hits: " + collector.getTotalHits());
			System.out.println(collector.getIds());
			
			//System.out.println("Number of hits: " + docs.totalHits);
			
			//ScoreDoc[] scoreDocs = docs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				Document document = indexSearcher.doc(scoreDoc.doc);
				System.out.println(document.get(FIELD_ID));
				System.out.println(document.get(FIELD_ENUNCIADO));
			}
		} else {
			System.out.println("O diretório não possui indexes");
			System.out.println(directory);
		}
	}
	
	public void searchIndexById(String searchString) throws Exception {
		Directory directory = FSDirectory.getDirectory(INDEX_DIRECTORY);
		if(IndexReader.indexExists(directory)) {
			IndexReader indexReader = IndexReader.open(directory);
			IndexSearcher indexSearcher = new IndexSearcher(indexReader);
			Analyzer analyzer = new BrazilianAnalyzer();
			QueryParser queryParser = new QueryParser(FIELD_ID, analyzer);
			Query query = queryParser.parse(searchString);
			TopDocs docs = indexSearcher.search(query, 1000000);
			System.out.println("Number of hits: " + docs.totalHits);
			ScoreDoc[] scoreDocs = docs.scoreDocs;
			for (ScoreDoc scoreDoc : scoreDocs) {
				Document document = indexSearcher.doc(scoreDoc.doc);
				System.out.println(document.get(FIELD_ID));
				System.out.println(document.get(FIELD_ENUNCIADO));
			}
		}
	}

	public QuestaoObjetiva getQuestaoObjetiva() {
		return service.buscarPorCodigoCarregarTudo(277487L);
	}
	
	public class HitCollectorImpl extends HitCollector {

		private int totalHits;
		private List<Long> ids;
		private IndexSearcher indexSearcher;
		
		public HitCollectorImpl(IndexSearcher indexSearcher) {
			ids = new ArrayList<Long>();
			this.indexSearcher = indexSearcher;
		}
		
		@Override
		public void collect(int doc, float score) {
			try {
				totalHits++;
				Document document = indexSearcher.doc(doc);
				String string = document.get(FIELD_ID);
				ids.add(new Long(string));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public int getTotalHits() {
			return totalHits;
		}

		public List<Long> getIds() {
			return ids;
		}
		
	}*/
}
