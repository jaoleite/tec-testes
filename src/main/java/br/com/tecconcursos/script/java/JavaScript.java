package br.com.tecconcursos.script.java;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class JavaScript {

	public JavaScript() {
		try {
			carregarJS();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public void carregarJS() throws ScriptException {
		//https://secure.mlstatic.com/org-img/checkout/custom/1.0/checkout.js
		ScriptEngine engine = new ScriptEngineManager().getEngineByName("Nashorn");
		//engine.eval("load('nashorn:parser.js')");
		//engine.eval("load('nashorn:mozilla_compat.js')");
		//engine.eval("load('https://secure.mlstatic.com/org-img/checkout/custom/1.0/checkout.js')");
		engine.eval("load('http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.6.0/underscore-min.js')");
	}
	
	public static void main(String[] args) {
		new JavaScript();
	}

}
