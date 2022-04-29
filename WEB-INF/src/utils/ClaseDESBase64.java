package utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.*;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

/**
 * @author JF
 * Esta clase Encripta una cadena y luego la convierte a Base64
 */
public class ClaseDESBase64 {
	Cipher encrypt;
	Cipher decrypt;
	SecretKey key;
	String keyTxt;
	public ClaseDESBase64(){
		keyTxt = "jkdkf555fg5f5g5fh5fg5525gf5gf5hf3535d5g";
		try{
			encrypt = Cipher.getInstance("DES");
			decrypt = Cipher.getInstance("DES");
			if(keyTxt.equals("")){
				SecretKey key = KeyGenerator.getInstance("DES").generateKey();//Genera Clave autom�tica
				encrypt.init(Cipher.ENCRYPT_MODE, key);//Con clave aleatoria
				decrypt.init(Cipher.DECRYPT_MODE, key);//Con clave aleatoria
			}else{
				KeySpec ks = new DESKeySpec(keyTxt.getBytes("UTF8"));
				SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
				SecretKey ky = kf.generateSecret(ks);
				encrypt.init(Cipher.ENCRYPT_MODE, ky);
				decrypt.init(Cipher.DECRYPT_MODE, ky);
			}
		}catch(InvalidKeySpecException ex){
		}catch(UnsupportedEncodingException ex){
		}catch(InvalidKeyException ex){
		}catch(NoSuchAlgorithmException ex){
		}catch(NoSuchPaddingException ex){
		}
	}
	/*
	public String encriptar(String str){
		try {
			// Encode the string into bytes using utf-8
			byte[] utf8 = str.getBytes("UTF8");
			// Encrypt
			byte[] enc = encrypt.doFinal(utf8);
			// Encode bytes to base64 to get a string
			return new sun.misc.BASE64Encoder().encode(enc);
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (java.io.IOException e) {
		}
		return null;
	}
	public String desencriptar(String str) {
		try {
			// Decode base64 to get bytes
			byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
			// Decrypt
			byte[] utf8 = decrypt.doFinal(dec);
			// Decode using utf-8
			return new String(utf8, "UTF8");
		} catch (javax.crypto.BadPaddingException e) {
		} catch (IllegalBlockSizeException e) {
		} catch (UnsupportedEncodingException e) {
		} catch (java.io.IOException e) {
		}
		return null;
	}*/
}