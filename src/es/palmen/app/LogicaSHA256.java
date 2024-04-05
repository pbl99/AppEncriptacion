package es.palmen.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LogicaSHA256 {
    public static String cifrar(String texto) {
        try {
            // Obtener instancia de MessageDigest para SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Calcular el resumen del mensaje (hash) para el texto dado
            byte[] hash = digest.digest(texto.getBytes());

            // Convertir el resumen del mensaje (hash) a una representación hexadecimal
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            // Manejar una excepción si el algoritmo no está disponible
            System.err.println(e.getMessage());
            return null;
        }
    }
}

