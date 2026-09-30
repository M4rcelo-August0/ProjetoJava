/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.view;

import sistemabiblioteca.control.CategoriaController;
import sistemabiblioteca.control.LivroController;
import sistemabiblioteca.model.Categoria;
import sistemabiblioteca.model.Livro;

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

public class TelaCadastroLivro extends JFrame {

    private final JTextField txtTitulo = new JTextField();
    private final JTextField txtIsbn = new JTextField();
    private final JTextField txtAutor = new JTextField();
    private final JTextField txtEditora = new JTextField();
    private final JTextField txtAno = new JTextField();
    private final JTextField txtQuantidade =
            new JTextField("1");
    private final JTextField txtDescricao = new JTextField();

    private final JComboBox<String> cmbCategoria =
            new JComboBox<>();

    private final List<Categoria> categorias =
            new ArrayList<>();

    public TelaCadastroLivro() {

        setTitle("Cadastro de livro");
        setSize(550, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel formulario = new JPanel(
                new GridLayout(8, 2, 10, 10)
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        formulario.add(new JLabel("Título:"));
        formulario.add(txtTitulo);

        formulario.add(new JLabel("ISBN:"));
        formulario.add(txtIsbn);

        formulario.add(new JLabel("Autor:"));
        formulario.add(txtAutor);

        formulario.add(new JLabel("Editora:"));
        formulario.add(txtEditora);

        formulario.add(new JLabel("Ano de publicação:"));
        formulario.add(txtAno);

        formulario.add(new JLabel("Quantidade:"));
        formulario.add(txtQuantidade);

        formulario.add(new JLabel("Descrição:"));
        formulario.add(txtDescricao);

        formulario.add(new JLabel("Categoria:"));
        formulario.add(cmbCategoria);

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnAtualizar =
                new JButton("Atualizar categorias");

        btnCadastrar.addActionListener(e -> cadastrar());
        btnAtualizar.addActionListener(e -> carregarCategorias());

        JPanel botoes = new JPanel();
        botoes.add(btnCadastrar);
        botoes.add(btnAtualizar);

        add(formulario, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        carregarCategorias();
    }

    private void carregarCategorias() {

        try {
            categorias.clear();
            cmbCategoria.removeAllItems();

            CategoriaController controller =
                    new CategoriaController();

            for (Categoria categoria :
                    controller.listarTodas()) {

                if ("ATIVA".equalsIgnoreCase(
                        categoria.getStatus())) {

                    categorias.add(categoria);

                    cmbCategoria.addItem(
                            categoria.getId() + " - "
                            + categoria.getNome()
                    );
                }
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
            int indice = cmbCategoria.getSelectedIndex();

            if (indice < 0) {
                throw new IllegalArgumentException(
                        "Selecione uma categoria."
                );
            }

            int quantidade = Integer.parseInt(
                    txtQuantidade.getText().trim()
            );

            Livro livro = new Livro();

            livro.setTitulo(txtTitulo.getText().trim());
            livro.setIsbn(txtIsbn.getText().trim());
            livro.setAutor(txtAutor.getText().trim());
            livro.setEditora(txtEditora.getText().trim());

            livro.setAnoPublicacao(
                    Integer.parseInt(
                            txtAno.getText().trim()
                    )
            );

            livro.setQuantidadeTotal(quantidade);
            livro.setQuantidadeDisponivel(quantidade);
            livro.setDescricao(
                    txtDescricao.getText().trim()
            );
            livro.setCategoria(categorias.get(indice));

            LivroController controller =
                    new LivroController();

            controller.cadastrar(livro);

            JOptionPane.showMessageDialog(
                    this,
                    "Livro cadastrado com sucesso!\n"
                    + "ID: " + livro.getId()
            );

            limpar();

        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ano e quantidade devem ser números.",
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

    private void limpar() {
        txtTitulo.setText("");
        txtIsbn.setText("");
        txtAutor.setText("");
        txtEditora.setText("");
        txtAno.setText("");
        txtQuantidade.setText("1");
        txtDescricao.setText("");
    }
}