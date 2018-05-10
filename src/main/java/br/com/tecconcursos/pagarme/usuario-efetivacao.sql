select
	e.id as "efetivacao",
	u.email,
	u.id as "usuario",
	p.id as "pedido",
	p.assinaturaatual_id as "assinatura",
	i.produto_id as "produto",
	i.id as "item"
from
	public.pedidoefetivacao e
	inner join public.pedido p  on p.id = e.pedido_id
	inner join public.usuariosite u on u.id = p.usuariosite_id
	inner join public.itempedido i on i.pedido_id = p.id
where
	e.efetivado = false
	and e.efetivacao <= '2018-05-07'; // 40, 34