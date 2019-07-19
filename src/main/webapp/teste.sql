select
	u.id as usuariositeid,  	u.nomecompleto as nomeUsuario,  	u.sobrenome as sobrenome, 	a.id as assinaturaid,  	u.email as email,  	u.cpf as cpf,  	p.id as pedidoid,  	e.id as enderecoid,  	e.logradouro as logradouro,  	e.numero as numero,  	e.complemento as complemento,  	e.bairro as bairro,  	e.cep as cep,  	c.id as cidadeid,  	c.nome as cidadeNome,  	c.estado as estado,  	sum(valorpago) as valorservico,
	nextval('seqnumeronotafiscalbarueri') as numerorps,
	sum(i.descontoparceria) as valordeducao
	from
		pedido p
		inner join usuariosite u on u.id = p.usuariosite_id
		left outer join endereco e on u.endereco_id = e.id
		left outer join cidade c on u.cidade_id = c.id
		inner join itempedido i on i.pedido_id = p.id
		inner join assinatura a on a.id = p.assinaturaatual_id
		left outer join notafiscal n on (n.pedido_id = p.id and n.assinatura_id = a.id)
	where
		n.id is null
		and (p.status = 'COMPLETA' or p.status = 'APROVADA' or p.status = 'AGUARDANDO_PAGAMENTO')
		and a.status = 'PAID'
		and u.cpf is not null
		and p.codigotransacao is not null
		and p.datatransacao >= ?
group by
	usuariositeid, email, cpf, assinaturaid, pedidoid, enderecoid, cidadeid
having sum(i.valorpago) > 0 