/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.view;

import sistemabiblioteca.control.CategoriaController;
import sistemabiblioteca.model.Categoria;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaCadastroCategoria extends JFrame {

    private final JTextField txtNome = new JTextField();
    private final JTextField txtCodigo = new JTextField();
    private final JTextField txtDescricao = new JTextField();
    private final JTextField txtFaixaEtaria = new JTextField();
    private final JTextField txtGenero = new JTextField();
    private final JTextField txtObservacao = new JTextField();

    public TelaCadastroCategoria() {

        setTitle("Cadastro de categoria");
        setSize(520, 390);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formulario = new JPanel(
                new GridLayout(6, 2, 10, 10)
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        formulario.add(new JLabel("Nome:"));
        formulario.add(txtNome);

        formulario.add(new JLabel("Código:"));
        formulario.add(txtCodigo);

        formulario.add(new JLabel("Descrição:"));
        formulario.add(txtDescricao);

        formulario.add(new JLabel("Faixa etária:"));
        formulario.add(txtFaixaEtaria);

        formulario.add(new JLabel("Gênero:"));
        formulario.add(txtGenero);

        formulario.add(new JLabel("Observação:"));
        formulario.add(txtObservacao);

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
            Categoria categoria = new Categoria();

            categoria.setNome(txtNome.getText().trim());
            categoria.setCodigo(txtCodigo.getText().trim());
            categoria.setDescricao(
                    txtDescricao.getText().trim()
            );
            categoria.setFaixaEtaria(
                    txtFaixaEtaria.getText().trim()
            );
            categoria.setGenero(
                    txtGenero.getText().trim()
            );
            categoria.setObservacao(
                    txtObservacao.getText().trim()
            );

            CategoriaController controller =
                    new CategoriaController();

            controller.cadastrar(categoria);

            JOptionPane.showMessageDialog(
                    this,
                    "Categoria cadastrada com sucesso!\n"
                    + "ID: " + categoria.getId()
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
        txtCodigo.setText("");
        txtDescricao.setText("");
        txtFaixaEtaria.setText("");
        txtGenero.setText("");
        txtObservacao.setText("");
    }
}