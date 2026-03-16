import javax.swing.*;
import java.awt.*;

public class Criptografinator extends JFrame {

    private static final String PALAVRA_FIXA = "RESPONSABILIDADE";
    private static final int MODULO = 36;

    private JTextField campoEntrada;
    private JTextField campoResultado;

    private int tamanhoOriginal = 0;

    // guarda as posições que são minúsculas
    private boolean[] mapaMinusculas = new boolean[PALAVRA_FIXA.length()];

    // Nossa interface
    public Criptografinator() {

        setTitle("Criptografinator");
        setSize(420, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 5, 5));

        campoEntrada = new JTextField();
        campoResultado = new JTextField();
        campoResultado.setEditable(false);

        JButton btnCriptografar = new JButton("Criptografar");
        JButton btnDescriptografar = new JButton("Descriptografar");

        add(new JLabel("Digite até 16 caracteres (letras ou números):"));
        add(campoEntrada);
        add(btnCriptografar);
        add(btnDescriptografar);
        add(new JLabel("Resultado:"));
        add(campoResultado);

        btnCriptografar.addActionListener(e -> criptografarAcao());
        btnDescriptografar.addActionListener(e -> descriptografarAcao());
    }

    // Funções dos Botões
    private void criptografarAcao() {

        String texto = campoEntrada.getText();

        if (texto.length() > PALAVRA_FIXA.length()) {
            JOptionPane.showMessageDialog(this,
                    "Máximo de 16 caracteres!");
            return;
        }

        tamanhoOriginal = texto.length();

        //salva onde era minúsculo
        for (int i = 0; i < tamanhoOriginal; i++) {
            mapaMinusculas[i] = Character.isLowerCase(texto.charAt(i));
        }

        campoResultado.setText(criptografar(texto));
    }

    private void descriptografarAcao() {

        String textoCripto = campoResultado.getText();
        if (textoCripto.isEmpty()) return;

        campoEntrada.setText(descriptografar(textoCripto, tamanhoOriginal));
    }

    // Conversão dos caracteres
    private static int charParaNumero(char c) {

        if (Character.isLetter(c)) {
            c = Character.toUpperCase(c);
            return c - 'A' + 1;
        }

        if (Character.isDigit(c)) {
            return c - '0' + 27;
        }

        return 1;
    }

    private static char numeroParaChar(int n) {

        if (n <= 26) {
            return (char) ('A' + n - 1);
        } else {
            return (char) ('0' + (n - 27));
        }
    }

    // Função da Criptografia
    private static String criptografar(String texto) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < PALAVRA_FIXA.length(); i++) {

            char original = (i < texto.length()) ? texto.charAt(i) : 'A';

            int numTexto = charParaNumero(original);
            int numFixo = charParaNumero(PALAVRA_FIXA.charAt(i));

            int soma = (numTexto + numFixo - 1) % MODULO + 1;

            char cripto = numeroParaChar(soma);

            resultado.append(cripto);
        }

        return resultado.toString();
    }

    // Função da Descriptografia
    private String descriptografar(String textoCripto, int tamanhoOriginal) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < tamanhoOriginal; i++) {

            int numCripto = charParaNumero(textoCripto.charAt(i));
            int numFixo = charParaNumero(PALAVRA_FIXA.charAt(i));

            int sub = (numCripto - numFixo + MODULO - 1) % MODULO + 1;

            char normal = numeroParaChar(sub);

            //  volta pro formato original
            if (mapaMinusculas[i]) {
                normal = Character.toLowerCase(normal);
            }

            resultado.append(normal);
        }

        return resultado.toString();
    }

    // Main kkkkkkkkkkk
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new Criptografinator().setVisible(true));
    }
}