package br.com.tecconcursos.criptografia;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.Provider;
import java.security.cert.Certificate;



public class ManagerKeystore {

	public ManagerKeystore() {
	}
	
	public static void main(String[] args) throws Exception {
		String keystoreFilename = "C:\\Desenvolvimento\\key\\tecconcursos.keystore";
		
		char[] password = "Teste123".toCharArray();
		String alias = "alias";
		
		Provider provider = CertificadoUtil.newPkcs11Provider(keystoreFilename);
		
		FileInputStream fIn = new FileInputStream(keystoreFilename);
		KeyStore keystore = KeyStore.getInstance("PKCS12", provider);
		
		keystore.load(fIn, password);
		
		Certificate cert = keystore.getCertificate(alias);
		
		System.out.println(cert);
	}
	
	
	
	/*private String getAlias() throws KeyStoreException {
		Enumeration<String> aliasesEnum = keyStore.aliases();
		String alias = null;
		while (aliasesEnum.hasMoreElements()) {
			String element = (String) aliasesEnum.nextElement();
			if (keyStore.isKeyEntry(element)) {
				alias = element;
				break;
			}
		}
		return alias;
	}*/

	static class CertificadoUtil {
		
		public static Provider newPkcs11Provider(String fileCfg) {
			try {
				return (Provider) Class.forName("sun.security.pkcs11.SunPKCS11").getDeclaredConstructor(new Class[] { String.class }).newInstance(fileCfg);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
	
}
