/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.view;

import sistemabiblioteca.control.CarteiraBibliotecaController;
import sistemabiblioteca.control.UsuarioController;
import sistemabiblioteca.model.CarteiraBiblioteca;
import sistemabiblioteca.model.Usuario;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaCadastroCarteira extends JFrame {

    private final JComboBox<String> cmbUsuario =
            new JComboBox<>();

    private final JTextField txtNumero = new JTextField();
    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtLimite =
            new JTextField("3");

    private final List<Usuario> usuarios =
            new ArrayList<>();

    public TelaCadastroCarteira() {

        setTitle("Cadastro de carteira");
        setSize(500, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formulario = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        formulario.add(new JLabel("Usuário:"));
        formulario.add(cmbUsuario);

        formulario.add(new JLabel("Número:"));
        formulario.add(txtNumero);

        formulario.add(new JLabel("Código:"));
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Limite de empréstimos:"));
        formulario.add(txtLimite);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar = new JButton("Atualizar usuários");

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> carregarUsuarios());

        JPanel botoes = new JPanel();
        botoes.add(btnCadastrar);
        botoes.add(btnAtualizar);

        add(formulario, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        carregarUsuarios();
    }

    private void carregarUsuarios() {

        try {
            usuarios.clear();
            cmbUsuario.removeAllItems();

            UsuarioController controller =
                    new UsuarioController();

            usuarios.addAll(controller.listarTodos());

            for (Usuario usuario : usuarios) {
                cmbUsuario.addItem(
                        usuario.getId() + " - "
                        + usuario.getNome()
                );
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cadastrar() {

        try {
            int indice = cmbUsuario.getSelectedIndex();

            if (indice < 0) {
                throw new IllegalArgumentException(
                        "Selecione um usuário."
                );
            }

            CarteiraBiblioteca carteira =
                    new CarteiraBiblioteca();

            carteira.setUsuario(usuarios.get(indice));
            carteira.setNumero(txtNumero.getText().trim());
            carteira.setCodigoCarteira(
                    txtCodigo.getText().trim()
            );

            carteira.setLimiteEmprestimos(
                    Integer.parseInt(
                            txtLimite.getText().trim()
                    )
            );

            CarteiraBibliotecaController controller =
                    new CarteiraBibliotecaController();

            controller.cadastrar(carteira);

            JOptionPane.showMessageDialog(
                    this,
                    "Carteira cadastrada com sucesso!\n"
                    + "ID: " + carteira.getId()
            );

            txtNumero.setText("");
            txtCodigo.setText("");
            txtLimite.setText("3");

        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(
                    this,
                    "O limite deve ser um número inteiro.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}