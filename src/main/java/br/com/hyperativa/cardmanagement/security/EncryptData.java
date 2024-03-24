package br.com.hyperativa.cardmanagement.security;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptData {

	public static Key generateAESKey(String password) throws Exception {
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
		PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), "salt".getBytes(), 1000, 256);
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		Key aesKey = new SecretKeySpec(secretKey.getEncoded(), "AES");
		return aesKey;
	}

	public static Object sendEncryptedData(Object data) throws Exception {
		Key aesKey = generateAESKey("CHAVEDECRIPTOGRAFIA");
		Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] encryptedData = aesCipher.doFinal(data.toString().getBytes());
		// send encryptedData via REST API
		return encryptedData;
	}

	public static Object receiveEncryptedData(byte[] data) throws Exception {
		Key aesKey = generateAESKey("CHAVEDECRIPTOGRAFIA");
		Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
		// receive encryptedData via REST API
		byte[] decryptedData = aesCipher.doFinal(data);
		// convert decryptedData to the relevant object
		return decryptedData;
	}

	public static String criptografar(String data) {
		KeyGenerator keygenerator;
		byte[] textoEncriptado = null;
		try {
			keygenerator = KeyGenerator.getInstance("DES");

			SecretKey chaveDES = keygenerator.generateKey();

			Cipher cifraDES;

			// Cria a cifra
			cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// Inicializa a cifra para o processo de encriptação
			cifraDES.init(Cipher.ENCRYPT_MODE, chaveDES);

			// Texto puro
			byte[] textoPuro = data.getBytes();

			// Texto encriptado
		    textoEncriptado = cifraDES.doFinal(textoPuro);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String(textoEncriptado);
	}

	public static String decriptografar(String data) {
		KeyGenerator keygenerator = null;
		byte[] textoDecriptografado = null;
		try {
			keygenerator = KeyGenerator.getInstance("DES");

			SecretKey chaveDES = keygenerator.generateKey();

			Cipher cifraDES;

			// Cria a cifra
			cifraDES = Cipher.getInstance("DES/ECB/PKCS5Padding");

			// Inicializa a cifra também para o processo de decriptação
			cifraDES.init(Cipher.DECRYPT_MODE, chaveDES);

			// Decriptografa o texto
		    textoDecriptografado = cifraDES.doFinal(data.getBytes());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(textoDecriptografado);
	}
}
