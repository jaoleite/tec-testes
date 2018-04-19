package br.com.tecconcursos.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.metamodel.DataContext;
import org.apache.metamodel.data.DataSet;
import org.apache.metamodel.data.Row;
import org.apache.metamodel.pojo.ObjectTableDataProvider;
import org.apache.metamodel.pojo.PojoDataContext;
import org.apache.metamodel.pojo.TableDataProvider;
import org.apache.metamodel.query.SelectItem;
import org.apache.metamodel.query.builder.SatisfiedWhereBuilder;
import org.joda.time.DateTime;

import br.com.tecconcursos.strategy.QueryBuilder;
import br.com.tecconcursos.to.RequestLogDto;

public class Arquivo {

	private static final String EMAIL = "engpetrogasjr@hotmail.com";
	
	@SuppressWarnings("unchecked")
	public static void teste() throws Exception {
		String nomeArquivo = "D:\\desenvolvimento\\logs\\routes.txt";
		List<String> list = IOUtils.readLines(new FileInputStream(new File(nomeArquivo)));
		Set<String> set = new HashSet<String>();
		for (String linha : list) {
			if(!linha.contains("area-restrita")) {
				String url = linha;
				if(linha.contains("{")) {
					String[] array = linha.split("\\{");
					url = array[0];
				}
				
				String[] array = url.split("/");
				if(array.length > 0) {
					set.add(array[array.length - 1]);
				}
			}
		}
		System.out.println(set);
		for (String string : set) {
			getNomeAcao(string);
		}
	}
	
	public static void getNomeAcao(String nome) {
		String tmp = StringUtils.removeStart(nome, "ajax");
		if(tmp.contains("-")) {
			tmp = StringUtils.replace(tmp, "-", " ");
		} else {
			tmp = separarPalavras(tmp);
		}
		tmp = WordUtils.capitalizeFully(tmp);
		System.out.println(tmp);
	}
	
	private static String separarPalavras(String string) {
		if(string == null) {
			return null;
		}
		StringBuilder builder = new StringBuilder();
		for (int i=0; i < string.length(); i++) {
			char c = string.charAt(i);
			if(Character.isUpperCase(c)) {
				builder.append(" ");
			}
			builder.append(c);
		}
		return builder.toString().trim();
	}
	
	
	public static void leitura() throws Exception {
		String directory = "D:\\desenvolvimento\\logs\\requests";
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory));
		List<String> list = new ArrayList<String>();
		String email = "engpetrogasjr@hotmail.com";
		for (Path path : directoryStream) {
			list.addAll(teste2(path, email));
		}
		List<RequestLogDto> dtos = new ArrayList<RequestLogDto>();
		for (String string : list) {
			dtos.add(new RequestLogDto(string));
		}
		System.out.println(dtos.size());
		
		TableDataProvider<RequestLogDto> provider = 
				new ObjectTableDataProvider<RequestLogDto>("requestLog", RequestLogDto.class, dtos);
		DataContext dc = new PojoDataContext("Schema", provider);
		
		SatisfiedWhereBuilder<?> where = dc.query().from("requestLog").selectAll()
				.where("email").eq(email)
				.or("url").like("%" + email + "%");
		
		RequestLogDto request = new RequestLogDto();
		//request.setUrl("ajaxResolverQuestao");
		//request.setUrl("login");
		QueryBuilder builder = new QueryBuilder(request, where);
		builder.buildWhere();
		builder.buildOrderBy();
		
		DataSet ds = where.execute();
		SelectItem[] itens = ds.getSelectItems();
		RequestLogDtoBuilder dtoBuilder = new RequestLogDtoBuilder(itens);
		List<RequestLogDto> requestLogDtos = new ArrayList<RequestLogDto>();
		int i = 0;
		while (ds.next()) {
			i++;
			Row row = ds.getRow();
			RequestLogDto dto = dtoBuilder.buildByRow(row);
			requestLogDtos.add(dto);
			if(i == 100) {
				break;
			}
		}
		
		for (RequestLogDto requestLogDto : requestLogDtos) {
			System.out.println(requestLogDto);
		}
		
		ds.close();
	}
	
	private static List<String> teste2(Path path, String email) throws Exception {
		Charset charset = Charset.forName("UTF-8");
		BufferedReader reader = Files.newBufferedReader(path, charset);
		String line = null;
		List<String> list = new ArrayList<String>();
		while ((line = reader.readLine()) != null) {
			if(line.contains(email)) {
				list.add(line);
			}
		}
		reader.close();
		return list;
	}
		
	public static void leitura2() throws Exception {
		String directory = "D:\\desenvolvimento\\logs\\requests";
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory));
		for (Path path : directoryStream) {
			teste3(path);
		}
	}

	public static void teste3(Path path) throws Exception {
		Charset charset = Charset.forName("UTF-8");
		BufferedReader reader = Files.newBufferedReader(path, charset);
		String line = null;
		List<RequestLogDto> dtos = new ArrayList<RequestLogDto>();
		while ((line = reader.readLine()) != null) {
			RequestLogDto dto = new RequestLogDto(line);
			dtos.add(dto);
		}
		reader.close();
		busca(dtos);
	}
	
	public static void busca(List<RequestLogDto> list) {
		TableDataProvider<RequestLogDto> provider = 
				new ObjectTableDataProvider<RequestLogDto>("requestLog", RequestLogDto.class, list);
		DataContext dc = new PojoDataContext("Schema", provider);
		
		DataSet ds = dc.query().from("requestLog").selectAll()
				.where("email").eq("engpetrogasjr@hotmail.com")
				.and("url").like("%ajaxResolverQuestao%").execute();
				
		while (ds.next()) {
			Row row = ds.getRow();
			//2 é email
			System.out.println(row.getValue(6));
		}
		ds.close();
		System.out.println("fim de uma busca");
	}
	
	public static void teste4() throws Exception {
		String directory = "D:\\desenvolvimento\\logs\\requests";
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(directory));
		for (Path path : directoryStream) {
			RandomAccessFile randomAccessFile = new RandomAccessFile(path.toFile(), "r");
			FileChannel inChannel = randomAccessFile.getChannel();
			MappedByteBuffer buffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
			
			byte[] b = new byte[(int)inChannel.size()];
			buffer.get(b);
			
			inChannel.close();
			randomAccessFile.close();
			
			BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(b)));
			List<RequestLogDto> dtos = new ArrayList<RequestLogDto>();
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				if(line.contains(EMAIL)) {
					dtos.add(new RequestLogDto(line));
				}
			}
			in.close();
			System.out.println(dtos.size());
		}
	}
	
	public static void main(String[] args) throws Exception {
		DateTime inicio = DateTime.now();
		//teste();
		leitura();
		//leitura2();
		//teste4();
		DateTime fim = DateTime.now();
		System.out.println();
		long segundos = (fim.getMillis() - inicio.getMillis()) / 1000;
		System.out.println("Tempo de execução: " + segundos + " segundos");
	}
	
}
