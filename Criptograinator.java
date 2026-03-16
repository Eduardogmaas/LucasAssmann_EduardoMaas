import javax.swing.*;
import java.awt.*;

public class Criptograinator extends JFrame {

    private static final String PALAVRA_FIXA = "ALFABETIZARR";
    private static final int MODULO = 36;

    private JTextField campoEntrada;
    private JTextField campoResultado;

    private int tamanhoOriginal = 0;

    public Criptograinator() {

        setTitle("Criptograinator");
        setSize(420, 220);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 5, 5));

        campoEntrada = new JTextField();
        campoResultado = new JTextField();
        campoResultado.setEditable(false);

        JButton btnCriptografar = new JButton("Criptografar");
        JButton btnDescriptografar = new JButton("Descriptografar");

        add(new JLabel("Digite até 12 caracteres (letras ou números):"));
        add(campoEntrada);
        add(btnCriptografar);
        add(btnDescriptografar);
        add(new JLabel("Resultado:"));
        add(campoResultado);

        btnCriptografar.addActionListener(e -> criptografarAcao());
        btnDescriptografar.addActionListener(e -> descriptografarAcao());
    }

    // ================================
    // AÇÕES DOS BOTÕES
    // ================================

    private void criptografarAcao() {

        String texto = campoEntrada.getText();

        if (texto.length() > PALAVRA_FIXA.length()) {
            JOptionPane.showMessageDialog(this,
                    "Máximo de 12 caracteres!");
            return;
        }

        tamanhoOriginal = texto.length();
        campoResultado.setText(criptografar(texto));
    }

    private void descriptografarAcao() {

        String textoCripto = campoResultado.getText();

        if (textoCripto.isEmpty()) return;

        campoEntrada.setText(descriptografar(textoCripto, tamanhoOriginal));
    }

    // ================================
    // CONVERSÕES
    // ================================

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

    // ================================
    // CRIPTOGRAFIA
    // ================================

    private static String criptografar(String texto) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < PALAVRA_FIXA.length(); i++) {

            char original = (i < texto.length()) ? texto.charAt(i) : 'A';

            boolean minuscula = Character.isLowerCase(original);

            int numTexto = charParaNumero(original);
            int numFixo = charParaNumero(PALAVRA_FIXA.charAt(i));

            int soma = (numTexto + numFixo - 1) % MODULO + 1;

            char cripto = numeroParaChar(soma);

            if (minuscula)
                cripto = Character.toLowerCase(cripto);

            resultado.append(cripto);
        }

        return resultado.toString();
    }

    // ================================
    // DESCRIPTOGRAFIA
    // ================================

    private static String descriptografar(String textoCripto, int tamanhoOriginal) {

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < tamanhoOriginal; i++) {

            char c = textoCripto.charAt(i);
            boolean minuscula = Character.isLowerCase(c);

            int numCripto = charParaNumero(c);
            int numFixo = charParaNumero(PALAVRA_FIXA.charAt(i));

            int sub = (numCripto - numFixo + MODULO - 1) % MODULO + 1;

            char normal = numeroParaChar(sub);

            if (minuscula)
                normal = Character.toLowerCase(normal);

            resultado.append(normal);
        }

        return resultado.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new Criptograinator().setVisible(true));
    }
}