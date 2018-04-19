package br.com.tecconcursos.serializacao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Joiner;

import br.com.tecconcursos.to.QuestaoSerializadaDto;

public class QuestaoObjetivaSerializada {

	public QuestaoObjetivaSerializada() {
		String filtrosGeracao = "241343:14:::2061:f;470924:14:::1896:f;226324:14:::1902:f;239133:14:::1902:f;244196:14:::1902:f;435334:14:::1902:f;435383:14:::1902:f;470922:14:::1902:f;245140:1:::1133:f;296180:1:::1133:f;470908:1:::1140:f;470376:1:::1142:f;296203:1:::1145:f;379101:1:::1145:f;470937:1:::1145:f;471025:1:::1145:f;296208:1:::549:f;435389:1:::549:f;470909:1:::549:f;462557:30:::4178:f;462831:30:::4178:f;468839:30:::4178:f;482237:30:::4178:f;459769:30::::f;449262:183:::5975:f;450261:183:::5975:f;450262:183:::5975:f;450263:183:::5975:f;450264:183:::5975:f;409023:4:::2387:f;382032:4:::1752:f;408564:4:::1752:f;382035:4:::2404:f;408580:4:::5296:f;408577:4:::2408:f;435323:4:::2408:f;408578:4:::5312:f;409513:4:::2413:f;470295:114:::5012:f;470905:114:::5012:f;80149:114:::4623:f;80150:114:::4623:f;80151:114:::4623:f;80152:114:::4623:f;80153:114:::4623:f;80199:114:::4623:f;80201:114:::4623:f;80202:114:::4623:f;80203:114:::4623:f;80414:114:::4623:f;468223:3:::5629:f;435355:3:::288:f;470994:3:::5667:f;470888:3:::3592:f;470997:3:::5636:f;468241:217:::1017:f;470372:217:::1017:f;470257:217:::1022:f";
		List<String> list = new ArrayList<>(Arrays.asList(filtrosGeracao.split(";")));
		QuestaoSerializadaDto dto = new QuestaoSerializadaDto();
		List<Long> codigos = new ArrayList<>();
		for (String codigoQuestao : list) {
			dto.setCodigoQuestao(codigoQuestao);
			codigos.add(dto.getIdQuestao());
		}
		
		String st = Joiner.on(",\n").join(codigos);
		System.out.println(st);
	}
	
	public static void main(String[] args) {
		new QuestaoObjetivaSerializada();
	}

}
