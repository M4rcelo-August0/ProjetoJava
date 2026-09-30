/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.view;

import sistemabiblioteca.control.UsuarioController;
import sistemabiblioteca.model.Usuario;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TelaCadastroUsuario extends JFrame {

    private final JTextField txtNome = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JTextField txtCpf = new JTextField();
    private final JTextField txtTelefone = new JTextField();
    private final JTextField txtEndereco = new JTextField();
    private final JTextField txtNascimento = new JTextField();
    private final JPasswordField txtSenha =
            new JPasswordField();

    public TelaCadastroUsuario() {

        setTitle("Cadastro de usuário");
        setSize(520, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formulario = new JPanel(
                new GridLayout(7, 2, 10, 10)
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNome);

        formulario.add(new JLabel("E-mail:"));
        formulario.add(txtEmail);

        formulario.add(new JLabel("CPF:"));
        formulario.add(txtCpf);

        formulario.add(new JLabel("Telefone:"));
        formulario.add(txtTelefone);

        formulario.add(new JLabel("Endereço:"));
        formulario.add(txtEndereco);

        formulario.add(
                new JLabel("Nascimento (AAAA-MM-DD):")
        );
        formulario.add(txtNascimento);

        formulario.add(new JLabel("Senha:"));
        formulario.add(txtSenha);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnLimpar = new JButton("Limpar");

        btnCadastrar.addActionListener(e -> cadastrar());
        btnLimpar.addActionListener(e -> limpar());

        JPanel botoes = new JPanel();
        botoes.add(btnCadastrar);
        botoes.add(btnLimpar);

        add(formulario, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
    }

    private void cadastrar() {

        try {
            Usuario usuario = new Usuario();

            usuario.setNome(txtNome.getText().trim());
            usuario.setEmail(txtEmail.getText().trim());
            usuario.setCpf(txtCpf.getText().trim());
            usuario.setTelefone(txtTelefone.getText().trim());
            usuario.setEndereco(txtEndereco.getText().trim());

            if (!txtNascimento.getText().isBlank()) {
                usuario.setDataNascimento(
                        LocalDate.parse(
                                txtNascimento.getText().trim()
                        )
                );
            }

            usuario.setSenha(
                    new String(txtSenha.getPassword())
            );

            UsuarioController controller =
                    new UsuarioController();

            controller.cadastrar(usuario);

            JOptionPane.showMessageDialog(
                    this,
                    "Usuário cadastrado com sucesso!\n"
                    + "ID: " + usuario.getId()
            );

            limpar();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpar() {
        txtNome.setText("");
        txtEmail.setText("");
        txtCpf.setText("");
        txtTelefone.setText("");
        txtEndereco.setText("");
        txtNascimento.setText("");
        txtSenha.setText("");
        txtNome.requestFocus();
    }
}