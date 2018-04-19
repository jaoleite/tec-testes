package br.com.tecconcursos.util;

import java.util.HashSet;
import java.util.Set;

public class EmailComProblemaMercadoPagp {

	
	public static void main(String[] args) {
		StringBuilder builder = new StringBuilder();
		builder.append("mailto:hugosaraiva@globo.com");
		builder.append("mailto:hugosaraiva@globo.com");
		builder.append("mailto:lelisadm@gmail.com");
		builder.append("mailto:jackbastos@hotmail.com");
		builder.append("mailto:gutobarroso@gmail.com");
		builder.append("mailto:brunoveiga-am@hotmail.com");
		builder.append("mailto:unicgds@hotmail.com");
		builder.append("mailto:unicgds@hotmail.com");
		builder.append("mailto:unicgds@hotmail.com");
		builder.append("mailto:pabaloobq@hotmail.com");
		builder.append("mailto:unicgds@hotmail.com");
		builder.append("mailto:unicgds@hotmail.com");
		builder.append("mailto:roneyromulo@yahoo.com.br");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:jackbastos@hotmail.com");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:carolmenezes.m@hotmail.com");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:carolmenezes.m@hotmail.com");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:danilotga01@hotmail.com");
		builder.append("mailto:danilotga01@hotmail.com");
		builder.append("mailto:carolmenezes.m@hotmail.com");
		builder.append("mailto:leandrogurgel@yahoo.com.br");
		builder.append("mailto:vpavoni@uol.com.br");
		builder.append("mailto:vpavoni@uol.com.br");
		builder.append("mailto:danilotga01@hotmail.com");
		builder.append("mailto:vpavoni@uol.com.br");
		builder.append("mailto:leandrogurgel@yahoo.com.br");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:decamargos@hotmail.com");
		builder.append("mailto:mgurgel42@gmail.com");
		builder.append("mailto:mgurgel42@gmail.com");
		builder.append("mailto:mgurgel42@gmail.com");
		builder.append("mailto:deisy.tsunoda@hotmail.com");
		builder.append("mailto:pe_melo@hotmail.com");
		builder.append("mailto:caiosantos1649@gmail.com");
		builder.append("mailto:caiosantos1649@gmail.com");
		builder.append("mailto:caiosantos1649@gmail.com");
		builder.append("mailto:caiosantos1649@gmail.com");
		builder.append("mailto:caiosantos1649@gmail.com");
		builder.append("mailto:zairacosta@gmail.com");
		builder.append("mailto:zairacosta@gmail.com");
		builder.append("mailto:drehersolange@yahoo.com.br");
		builder.append("mailto:marcelo_brazil@hotmail.com");
		builder.append("mailto:marcelo_brazil@hotmail.com");
		builder.append("mailto:raiany_pereira@hotmail.com");
		builder.append("mailto:marcelo_brazil@hotmail.com");
		builder.append("mailto:marcelo_brazil@hotmail.com");
		builder.append("mailto:lucassdm@hotmail.com");
		builder.append("mailto:lucassdm@hotmail.com");
		builder.append("mailto:pattyllene.afrfb@hotmail.com");
		builder.append("mailto:mauriciovianaadm@gmail.com");
		builder.append("mailto:mauriciovianaadm@gmail.com");
		builder.append("mailto:lorenasanto@gmail.com");
		builder.append("mailto:lorenasanto@gmail.com");
		builder.append("mailto:lorenasanto@gmail.com");
		builder.append("mailto:lorenasanto@gmail.com");
		builder.append("mailto:pl_vitor@me.com");
		builder.append("mailto:pl_vitor@me.com");
		builder.append("mailto:pl_vitor@me.com");
		builder.append("mailto:pl_vitor@me.com");
		builder.append("mailto:sabrina.xavier@gmail.com");
		builder.append("mailto:sabrina.xavier@gmail.com");
		builder.append("mailto:sabrina.xavier@gmail.com");
		builder.append("mailto:mara.bortoluzzi@hotmail.com");
		builder.append("mailto:mara.bortoluzzi@hotmail.com");
		builder.append("mailto:mara.bortoluzzi@hotmail.com");
		builder.append("mailto:lorenasanto@gmail.com");
		builder.append("mailto:mara.bortoluzzi@hotmail.com");
		builder.append("mailto:lorenasanto@gmail.com");
		builder.append("mailto:adriana.1101@hotmail.com");
		builder.append("mailto:raissa.mota@gmail.com");
		builder.append("mailto:leonildes.lba@hotmail.com");
		builder.append("mailto:raissa.mota@gmail.com");
		builder.append("mailto:raissa.mota@gmail.com");
		builder.append("mailto:renandsiqueirasantos@gmail.com");
		
		String[] strings = builder.toString().split("mailto:");
		Set<String> set = new HashSet<String>();
		
		for (String email : strings) {
			set.add(email);
		}
		
		for (String email : set) {
			System.out.println(email);
		}
	}
	
}
