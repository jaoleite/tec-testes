package br.com.tecconcursos.criptografia;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class SenhaTeste {

	private static final int ITERATIONS = 1000;
	private static final String ALGORITHM = "PBKDF2WithHmacSHA1";
	private static final String RANDON_ALGORITHM = "SHA1PRNG";
	private static final String SPLIT_SEARCH = ":";
	
	private static int workload = 12;
	
	public String generateStorngPasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
		int iterations = ITERATIONS;
		char[] chars = password.toCharArray();
		byte[] salt = getSalt();
		
		PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
		byte[] hash = skf.generateSecret(spec).getEncoded();
		return iterations + SPLIT_SEARCH + toHex(salt) + SPLIT_SEARCH + toHex(hash);
	}
	
	public boolean validatePassword(String originalPassword, String storedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException {
		String[] parts = storedPassword.split(SPLIT_SEARCH);
		int iterationsIndex = 0;
		int saltIndex = 1;
		int hashIndex = 2;
		
		int iterations = Integer.parseInt(parts[iterationsIndex]);
		byte[] salt = fromHex(parts[saltIndex]);
		byte[] hash = fromHex(parts[hashIndex]);
		 
		PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, hash.length * 8);
		SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
		byte[] testHash = skf.generateSecret(spec).getEncoded();
		 
		int diff = hash.length ^ testHash.length;
		for(int i = 0; i < hash.length && i < testHash.length; i++) {
			diff |= hash[i] ^ testHash[i];
		}
		return diff == 0;
    }
	
	private byte[] getSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance(RANDON_ALGORITHM);
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return salt;
	}
	
	private String toHex(byte[] array) throws NoSuchAlgorithmException {
		BigInteger bi = new BigInteger(1, array);
		String hex = bi.toString(16);
		int paddingLength = (array.length * 2) - hex.length();
		if(paddingLength > 0) {
			return String.format("%0" + paddingLength + "d", 0) + hex;
		} else {
			return hex;
		}
    }
	
	private byte[] fromHex(String hex) throws NoSuchAlgorithmException {
		byte[] bytes = new byte[hex.length() / 2];
		for(int i = 0; i<bytes.length ;i++) {
			bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return bytes;
    }
	
	public static void main(String[] args) {
		/*String password = "Teste123";
		String[] strings = senhas();
		for (String storedHash : strings) {
			System.out.println(checkPassword(password, storedHash));
		}*/
		
		System.out.println(BCrypt.gensalt(workload));
	}
	
	
	public static String[] senhas() {
		String[] strings = {
		"$2a$12$qYQONZUz.IaRT9KwGUhS9e7BC6l2VSqDbSpqbWXQg7OueY4QdcmAm",
		"$2a$12$NviA9ZlqJnBAkzmTSBG6zOp.DcNIh5iyQ2rHuemMDjm5ikFU6g/gO",
		"$2a$12$ScRRKZBmzURWkP9CeeXU7.Om3tEP6O8GSyoOufu3kzC.7gqfa4gL2",
		"$2a$12$EOVf7Fe4/K.wBkumCMPUfOq3/NAaYxVkEAr3mgbXR1CbwpwnSFMdm",
		"$2a$12$H6CTr5sK.aLRU4FXGsbTXO2bWA3vKZVU76UihJiiTdouYeKl1qd1S"};
		
		return strings;
	}
	
	public static String hashPassword(String password) {
		String salt = BCrypt.gensalt(workload);
		String hashed = BCrypt.hashpw(password, salt);
		return(hashed);
	}
	
	public static boolean checkPassword(String password, String storedHash) {
		boolean passwordVerified = false;
		if(null == storedHash || !storedHash.startsWith("$2a$"))
			throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");
		passwordVerified = BCrypt.checkpw(password, storedHash);
		return(passwordVerified);
	}

}
