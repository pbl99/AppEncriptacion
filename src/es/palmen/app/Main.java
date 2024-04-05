package es.palmen.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

public class Main {
    private static byte[] cifrado = null;

    public static void main(String[] args) {

        JOptionPane.showMessageDialog(null,"Recuerda AES esta en modo: AES/ECB/PKCS5Padding");
        panelPrincipal();
    }

    public static void panelPrincipal() {
        JLabel titulo;
        JTextArea textoIntroductorio;
        JTextArea textoResultado;
        JButton enviar;

        JFrame frame = new JFrame();
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLocation(550, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Carga la imagen que deseas utilizar como icono
        ImageIcon icon = new ImageIcon("resources/logo.png");
        Image image = icon.getImage();

        // Establece la imagen como icono del JFrame
        frame.setIconImage(image);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        titulo = new JLabel();
        titulo.setText("ENCRIPTADOR DE TEXTOS");
        titulo.setBounds(110, 5, 250, 40);
        titulo.setVisible(true);
        panel.add(titulo);

        textoIntroductorio = new JTextArea();
        textoIntroductorio.setText("Introduce el texto a encriptar");
        textoIntroductorio.setBounds(85, 50, 210, 70);
        textoIntroductorio.setLineWrap(true);
        panel.add(textoIntroductorio);

        JScrollPane scrollPane = new JScrollPane(textoIntroductorio);

        // Configuración del scroll vertical
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Establecer las coordenadas y dimensiones del JScrollPane manualmente
        scrollPane.setBounds(85, 50, 210, 70);

        // Agregar el JScrollPane al panel
        panel.add(scrollPane);

        enviar = new JButton();
        enviar.setText("Enviar");
        enviar.setBounds(210, 150, 85, 30);
        panel.add(enviar);

        textoResultado = new JTextArea();
        textoResultado.setText("Aquí saldra el texto encriptado");
        textoResultado.setBackground(Color.white);
        textoResultado.setLineWrap(true);
        textoResultado.setEditable(false);
        textoResultado.setBounds(85, 200, 210, 70);
        panel.add(textoResultado);

        JScrollPane scrollPane2 = new JScrollPane(textoResultado);

        // Configuración del scroll vertical
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Establecer las coordenadas y dimensiones del JScrollPane manualmente
        scrollPane2.setBounds(85, 200, 210, 70);

        // Agregar el JScrollPane al panel
        panel.add(scrollPane2);

        // Array con los elementos de la lista
        String[] options = {"Texto a AES", "AES a texto", "Texto a SHA256"};

        // Crear el JComboBox con las opciones
        JComboBox<String> comboBox = new JComboBox<>(options);

        // Establecer el valor predeterminado seleccionado
        comboBox.setSelectedIndex(0); // Puedes cambiar el índice para seleccionar una opción diferente por defecto

        // Agregar el JComboBox al panel principal
        comboBox.setBounds(85, 150, 120, 30);
        panel.add(comboBox);

        // Agregar un listener de acción al botón
        enviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clave = null;
                String textoDescifrado = null;
                // Código a ejecutar cuando se hace clic en el botón
                //JOptionPane.showMessageDialog(null, "¡Has hecho clic en el botón!");
                if (!comboBox.getSelectedItem().equals("Texto a SHA256")) {
                    clave = JOptionPane.showInputDialog(null, "Ingresa la Clave AES (debe ser de 16 caracteres)");
                }
                String textoTextField = textoIntroductorio.getText();
                if (comboBox.getSelectedItem().equals("Texto a AES")) {
                    try {
                        cifrado = LogicaAES.cifrar(textoTextField, clave);
                        textoResultado.setText(Base64.getEncoder().encodeToString(cifrado));
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,"La clave debe contener 16 caracteres","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else if (comboBox.getSelectedItem().equals("AES a texto")) {
                    try {
                        if (cifrado != null) {
                            textoDescifrado = LogicaAES.descifrar(cifrado, clave);
                            textoResultado.setText(textoDescifrado);
                        } else {
                            byte[] cif = Base64.getDecoder().decode(textoTextField);
                            textoDescifrado = LogicaAES.descifrar(cif, clave);
                            textoResultado.setText(textoDescifrado);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null,"Ha habido un error revise si la clave es la correcta o el mensaje cifrado es correcto","Error",JOptionPane.ERROR_MESSAGE);
                    }
                } else if (comboBox.getSelectedItem().equals("Texto a SHA256")) {
                    textoResultado.setText(LogicaSHA256.cifrar(textoTextField));
                }

            }
        });

        frame.add(panel);
        frame.setVisible(true);

    }
}
