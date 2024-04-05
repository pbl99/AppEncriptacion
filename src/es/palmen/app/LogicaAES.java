package es.palmen.app;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class LogicaAES {
    public static byte[] cifrar(String texto, String clave) throws Exception {
        SecretKeySpec claveSecreta = new SecretKeySpec(clave.getBytes(), "AES");
        Cipher cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cifrador.init(Cipher.ENCRYPT_MODE, claveSecreta);
        return cifrador.doFinal(texto.getBytes());
    }

    public static String descifrar(byte[] textoCifrado, String clave) throws Exception {
        SecretKeySpec claveSecreta = new SecretKeySpec(clave.getBytes(), "AES");
        Cipher cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cifrador.init(Cipher.DECRYPT_MODE, claveSecreta);
        byte[] textoDescifrado = cifrador.doFinal(textoCifrado);
        return new String(textoDescifrado);
    }

}



