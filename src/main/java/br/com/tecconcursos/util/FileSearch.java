package br.com.tecconcursos.util;

import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class FileSearch {

	public static void main(String[] args) throws NamingException {
		String name = "paypal.com";
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial",
		            "com.sun.jndi.dns.DnsContextFactory");
		DirContext dirContext = new InitialDirContext(env);
		Attributes attrs = dirContext.getAttributes(name, new String[] { "TXT" });
		Attribute txt = attrs.get("TXT");
		NamingEnumeration<?> e = txt.getAll();
		while (e.hasMore()) {
		     System.out.println(e.next());
		}
	}
	
}
