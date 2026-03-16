import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Criptograinator extends JFrame {

    static final String PALAVRA_FIXA = "ALFABETIZARR";

    private JTextField campoSenha;
    private JTextField campoResultado;

    public Criptograinator() {

        setTitle("Criptograinator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(4, 1));

        campoSenha = new JTextField();
        campoResultado = new JTextField();
        campoResultado.setEditable(false);

        JButton botaoCriptografar = new JButton("Criptografar");
        JButton botaoDescriptografar = new JButton("Descriptografar");

        add(new JLabel("Digite algo para ser criptografado! (máximo de 12 letras):"));
        add(campoSenha);
        add(botaoCriptografar);
        add(botaoDescriptografar);
        add(new JLabel("Resultado:"));
        add(campoResultado);

        botaoCriptografar.addActionListener(e -> {
            String senha = campoSenha.getText();

            if (senha.length() > 12) {
                JOptionPane.showMessageDialog(this,
                        "Máximo de 12 caracteres!");
                return;
            }

            campoResultado.setText(criptografar(senha));
        });


        botaoDescriptografar.addActionListener(e -> {
            String textoCripto = campoResultado.getText();
            campoSenha.setText(descriptografar(textoCripto, campoSenha.getText().length()));
        });
    }

    public static int letraParaNumero(char letra) {
        letra = Character.toUpperCase(letra);
        if (letra >= 'A' && letra <= 'Z') {
            return letra - 'A' + 1;
        }
        return 1;
    }

    public static char numeroParaLetra(int numero) {
        return (char) ('A' + numero - 1);
    }

    public static String criptografar(String senha) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < PALAVRA_FIXA.length(); i++) {

            int numSenha = (i < senha.length())
                    ? letraParaNumero(senha.charAt(i))
                    : 1;

            int numFixo = letraParaNumero(PALAVRA_FIXA.charAt(i));

            int soma = (numSenha + numFixo - 1) % 26 + 1;

            resultado.append(numeroParaLetra(soma));
        }

        return resultado.toString();
    }

    public static String descriptografar(String textoCripto, int tamanhoOriginal) {
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < tamanhoOriginal; i++) {

            int numCripto = letraParaNumero(textoCripto.charAt(i));
            int numFixo = letraParaNumero(PALAVRA_FIXA.charAt(i));

            int sub = (numCripto - numFixo + 26 - 1) % 26 + 1;

            resultado.append(numeroParaLetra(sub));
        }

        return resultado.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Criptograinator().setVisible(true);
        });
    }
}