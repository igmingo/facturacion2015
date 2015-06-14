package app;
import java.security.*;

public class Encriptacion {
	

	/**  
	 *  
	 * @author Herman Alonso Barrates Víquez  
	 */ 

	public static String MD2 = "MD2";  
	public static String MD5 = "MD5";  
	public static String SHA1 = "SHA-1";  
	public static String SHA256 = "SHA-256";  
	public static String SHA384 = "SHA-384";  
	public static String SHA512 = "SHA-512";  

	/***  

	 * Convierte un arreglo de bytes a String usando valores hexadecimales  
	 * @param digest arreglo de bytes a convertir  
	 * @return String creado a partir de <code>digest</code>  
	 */ 

		private static String toHexadecimal(byte[] digest)
		{  
			String hash = "";  

			for(byte aux : digest) 
			{
				int b = aux & 0xff;  
				if (Integer.toHexString(b).length() == 1) 
					hash += "0";  
				hash += Integer.toHexString(b);  
			}  
			return hash;  
		}  

	/***  
	 * Encripta un mensaje de texto mediante algoritmo de resumen de mensaje.  
	 * @param mensaje texto a encriptar  
	 * @param metodo algoritmo de encriptacion, puede ser: MD2, MD5, SHA-1, SHA-256, SHA-384, SHA-512  
	 * @return mensaje encriptado  
	 */ 

	public static String getMensajeEncriptado(String mensaje, String metodo)
	{  
		byte[] digest = null;  
		byte[] buffer = mensaje.getBytes();  
		try 
		{  
			MessageDigest messageDigest = MessageDigest.getInstance(metodo);  
			messageDigest.reset();  
			messageDigest.update(buffer);  
			digest = messageDigest.digest();  
		} 
		catch (NoSuchAlgorithmException ex) 
		{  
			System.out.println("Error creando Digest");  
		}  
		return toHexadecimal(digest);  
	}   
} 
