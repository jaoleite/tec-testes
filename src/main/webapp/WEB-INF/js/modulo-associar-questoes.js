(function($) {
	$.extend({
		bloquearAtalhosAssociacaoQuestoes: false,
		
		exibirQuestaoParaAssociacao: function(params) {
			$.exibirConteudoQuestao($.extend({}, params, { gabarito: true }));
			$.postJSON(params.ajaxUrl + "ajaxBuscarAssuntosQuestao", { idQuestao: params.idQuestao }, function(data) {
				$.atualizarAssuntosQuestao(params, data, params.idQuestao);
			});
		},
		
		alterarProfessor: function(params) {
			if(params.isCatalogador) {
				$.postJSON(params.ajaxUrl + "ajaxListarProfessoresPorQuestao", { idQuestao: params.idQuestao }, function(data) {
					$.each(data.list, function(index, item) {
						$("#alterar-dono-questao").append($("<option>", {
						    value: item.id,
						    text: item.apelido,
						    selected: item.donoDeQuestao
						}));
					});
					
					$("#alterar-dono-questao").on("keydown", function (e) {
						var keys = [37, 38, 39, 40];
						var key = e.which;
						var possui = $.inArray(key, keys) >= 0;
						if (possui) {
							e.stopPropagation();
							return false;
					    }
					});

					if($.buscarStatusQuestao() == 3) {
						$("#alterar-dono-questao").prop("disabled", true);
					}
					
					$("#alterar-dono-questao").bind("keyup keypress keydown change", function(e) {
						if(e.which) {
							e.stopPropagation();
							return false;
						}
						
						var idProfessor = $(this).val();
						var parametros = {
								idQuestao: params.idQuestao,
								idProfessor: idProfessor
						}
						$.postJSON(params.ajaxUrl + "ajaxAlterarDonoDaQuestao", parametros, function(data) {
							if(!data.boolean) {
								$.alerts.alert("Ocorreu um erro ao alterar o dono da questao");
							}
							$("#alterar-dono-questao").blur();
						});
						
					});

					/*$("#alterar-dono-questao").change(function() {
						var idProfessor = $(this).val();
						var parametros = {
								idQuestao: params.idQuestao,
								idProfessor: idProfessor
						}
						$.postJSON(params.ajaxUrl + "ajaxAlterarDonoDaQuestao", parametros, function(data) {
							if(!data.boolean) {
								$.alerts.alert("Ocorreu um erro ao alterar o dono da questão");
							}
						});
						
					});*/
				});
			}
		},
		
		atualizarAssuntosQuestao: function(params, data, idQuestao) {
			
			data.permitirAssociarQuestoes = $.permitirAssociarQuestoes();
			data.permitirRemoverQuestoes = $.permitirRemoverQuestoes();
			data.permitirAlterarDono = $.permitirAlterarDono();
			
			if(params.callbackPreRenderizacao) {
				params.callbackPreRenderizacao(data);
			}
			
			$("#intensivo-assuntos").setTemplate(templateAssociarQuestao).processTemplate(data).show();
			
			var select = $("<select></select>").attr("id", "adicionar-assunto").html($("#adicionar-assunto-template").html());
			$.desabilitarAssuntosPai(select);
			$("#adicionar-assunto-container").append(select);
			
			if(params.callbackAdicionarFuncionalidadesCatalogador) {
				params.callbackAdicionarFuncionalidadesCatalogador(data);
			}
			
			if(params.callbackAdicionarEventosTeclasDeAtalho) {
				params.callbackAdicionarEventosTeclasDeAtalho(params);
			}
			
			//$("#adicionar-assunto").focus();
			
			$("#adicionar-assunto-teclado").focus(function() {
				$(this).show();
				$("#adicionar-assunto").hide();
			});

			$("#adicionar-assunto-teclado").blur(function() {
				$(this).val("").hide();
				$("#lista-assuntos .assunto-temporario").remove();
				$("#adicionar-assunto").show();
			});
			
			if(params.callbackAddEventChangeAutoCompleteAssunto) {
				params.callbackAddEventChangeAutoCompleteAssunto(params);
			} else {
				$("#adicionar-assunto-teclado").keyup(function(e) {
					$("#lista-assuntos .assunto-temporario").remove();
					
					var encontrado = false;
					$("#adicionar-assunto option").each(function() {
						if(params.callbackAdicionarAssuntoTeclado) {
							params.callbackAdicionarAssuntoTeclado($(this));
						} else {
							var optionText = $(this).html();
							var split = optionText.split("-");
							if ($.trim(split[0]) == $("#adicionar-assunto-teclado").val() && $("#lista-assuntos input[value=" + $(this).val() + "]").size() == 0) {
								var link = $("<a class='assunto-questao assunto-temporario'></a>").html(optionText).corner("3px");
								$("#lista-assuntos").append(link);
								encontrado = true;
							}
						}
					});
					
					if (!encontrado) {
						if ($.verificarAssuntoValido($("#adicionar-assunto-teclado").val())) {
							var link = $("<a class='assunto-questao assunto-temporario'></a>").html("Criar assunto: " + $("#adicionar-assunto-teclado").val()).corner("3px");
							$("#lista-assuntos").append(link);
						}
					}
				});
			}
			
			$("#lista-assuntos a").corner("3px");
			
			$("#conteudoQuestao .remover-elemento-hover").click(function() {
				$.removerAssuntoQuestao(params, $(this));
			});
			
			$("#adicionar-assunto").change(function() {
				var select = $(this);
				if (!select.isFieldEmpty()) {
					var idAssunto = select.val();
					$.trocarOuAdicionarAssuntoQuestao(params, idQuestao, idAssunto);
					select.val("");
				}
			});
			
			$.alterarProfessor(params);
		},
		
		desabilitarAssuntosPai: function(select) {
			$(select).find("option").each(function(index, option) {
				var possuiFilho = $.verificarSeAssuntoPossuiFilho(select, option);
				if(possuiFilho) {
					$(option).attr("disabled", "disabled");
				}
			});
		},
		
		verificarSeAssuntoPossuiFilho: function(select, option) {
			var retorno = false;
			if($(option).val() != "") {
				var hierarquia = $.buscarHierarquia(option) + ".";
				$(select).find("option").each(function(index, optionAtual) {
					if(index > 0 && $(optionAtual).val() != $(option).val()) {
						var hierarquiaAtual = $.buscarHierarquia(optionAtual);
						if($(option).val() != $(optionAtual).val() && hierarquiaAtual.indexOf(hierarquia) == 0) {
							retorno = true;
						}
					}
				});
			}
			return retorno;
		},
		
		buscarHierarquia: function(option) {
			var optionText = $(option).text();
			var hierarquias = optionText.split(" - ");
			return hierarquias[0];
		},
		
		removerAssuntoQuestao: function(params, element) {
			var idAssunto = element.find("input").val();
			var parametros;
			if(params.callbackMontarParametrosTrocaAssunto) {
				parametros = params.callbackMontarParametrosTrocaAssunto(idAssunto);
			} else {
				var parametros = {
					idQuestao: $("#idQuestao").val(),
					idAssunto: idAssunto
				};
			}
			
			$.postJSON(params.ajaxUrl + "ajaxRemoverAssuntoQuestao", parametros, function(data) {
				data.list = [];
				if(!params.callbackRemoverAssuntoCatalogador) {
					$.atualizarAssuntosQuestao(params, data, parametros.idQuestao);
				}
				if (params.callbackRemoverAssunto) {
					params.callbackRemoverAssunto(parametros.idQuestao, idAssunto);
				}
				if(params.callbackRemoverAssuntoCatalogador) {
					params.callbackRemoverAssuntoCatalogador(parametros, idAssunto);
				}
				
			});
		},
		
		trocarOuAdicionarAssuntoQuestao: function(params, idQuestao, idAssunto) {
			/*if ($.existeAssuntoAssociado()) {
				alert('Já existe assunto cadastrado.\nA questão deverá ter somente um assunto associado.');
				return false;
			}*/
			
			var idAssuntoOrigem = null;
			var questaoExistente = $("li.questoes a input[value='" + idQuestao + "']");
			if (questaoExistente.length) {
				idAssuntoOrigem = questaoExistente.closest(".assunto").find(".assunto-id").val();
			}
			
			var parametros;
			if(params.callbackMontarParametrosTrocaAssunto) {
				parametros = params.callbackMontarParametrosTrocaAssunto(idAssunto);
			} else {
				parametros = {
						idQuestao: idQuestao,
						idAssunto: idAssunto };
			}
			
			$.postJSON(params.ajaxUrl + "ajaxAdicionarAssuntoQuestao", parametros, function(data) {
				if(!params.callbackAdicionarAssunto) {
					$.atualizarAssuntosQuestao(params, data, idQuestao);
				}
				if (params.callbackTrocarAssunto) {
					params.callbackTrocarAssunto(idQuestao, idAssunto, idAssuntoOrigem);
				}
				if(params.callbackAdicionarAssunto) {
					params.callbackAdicionarAssunto(params, idAssunto);
				}
			});
		},
		
		verificarAssuntoValido: function(assunto) {
			var split = assunto.split("-");
			return (split.length == 2 && /\d+([.]\d+)*/.exec(split[0]) && $.trim(split[1]).length > 0);
		},
		
		atalhosTecladoAssociacaoQuestoes: function(params) {
			params = params || {};
			if (!params.ajaxUrl) {
				params.ajaxUrl = "";
			}
			
			$(document).keydown(function(e) {
				if (!$.bloquearAtalhosAssociacaoQuestoes) {
					if(params.callbackUtilizarTodasAsTeclas) {
						params.callbackUtilizarTodasAsTeclas();
					} else {
						if (e.keyCode >= 48 && e.keyCode < 59) {
							$("#adicionar-assunto-teclado").show();
							$("#adicionar-assunto-teclado").focus();
						}
					}
					
					
				}
			});
			
			$(document).keyup(function(e) {
				var assuntoTecladoAberto = $("#adicionar-assunto-teclado").length && !$("#adicionar-assunto-teclado").is(":hidden");
				if (assuntoTecladoAberto) {
					if ($.permitirAssociarQuestoes()) {
						if (e.keyCode == 13) {
							if(!params.callbackAddEventChangeAutoCompleteAssunto) {
								var encontrado = false;
								$("#adicionar-assunto option").each(function() {
									if ($(this).html().indexOf($("#adicionar-assunto-teclado").val() + " - ") == 0) {
										$.trocarOuAdicionarAssuntoQuestao(params, $.buscarIdQuestaoSelecionada(), $(this).val());
										encontrado = true;
									}
								});
								
								if (!encontrado) {
									var nomeAssunto = $("#adicionar-assunto-teclado").val();
									if ($.verificarAssuntoValido(nomeAssunto)) {
										$.postJSON(params.ajaxUrl + "ajaxCriarNovoAssunto", { nomeAssunto: nomeAssunto }, function(data) {
											var idAssunto = data.long;
											if (idAssunto) {
												$("#adicionar-assunto, #adicionar-assunto-template").each(function() {
													$(this).append($("<option></option>").html(nomeAssunto).val(idAssunto));
												});
												$.trocarOuAdicionarAssuntoQuestao(params, $.buscarIdQuestaoSelecionada(), idAssunto);
											}
										});
									}
								}
							}
							$("#adicionar-assunto-teclado").blur();
							return false;
						}
						if (e.keyCode == 27) {
							$("#adicionar-assunto-teclado").blur();
							return false;
						}
					}
				} else {
				    if (e.keyCode == 73) {
				    	$("#botao-informacoes").click();
				    }
				    if (e.keyCode == 79) {
				    	$("#botao-comentario").click();
				    }
				    if (e.keyCode == 27) {
				    	$("#informacoes-voltar-button, #comentario-fechar-button").click();
				    }
				}
			});
			
			$.atalhosTecladoConteudoQuestao();
			
			$(document).keyup(function(e) {
				e.stopPropagation();
				var keys = [37, 38, 39, 40];
				var key = e.which;
				var possui = $.inArray(key, keys) >= 0;
				if(possui && $(e.target).attr("id") == "alterar-dono-questao") {
					return false;
				}
			});

		},
		
		buscarIdQuestaoSelecionada: function() {
			var idQuestao;
			
			var link = $("li.questoes a.selecionada");
			var href = link.attr("href");
			if (href) {
				var split = href.split("/");				
				idQuestao = split[split.length - 1];
			} else {
				idQuestao = $.trim(link.html());
			}
			return idQuestao;
		},
		
		permitirAssociarQuestoes: function() {
			return $("#permitir-associar-questoes").val() == "true";
		},
		
		permitirRemoverQuestoes: function() {
			return $("#permitir-remover-questoes").val() == "true";
		},
		
		permitirAlterarDono: function() {
			return $("#permitir-alterar-dono").val() == "true";
		},
		
		buscarStatusQuestao: function() {
			return $("#status-questao-atual").val();
		}
		
	});
})(jQuery);
