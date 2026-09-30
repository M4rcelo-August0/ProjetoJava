/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.view;

import sistemabiblioteca.control.CarteiraBibliotecaController;
import sistemabiblioteca.control.EmprestimoController;
import sistemabiblioteca.control.LivroController;
import sistemabiblioteca.model.CarteiraBiblioteca;
import sistemabiblioteca.model.Emprestimo;
import sistemabiblioteca.model.Livro;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
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

public class TelaEmprestimo extends JFrame {

    private final JComboBox<String> cmbUsuario =
            new JComboBox<>();

    private final JComboBox<String> cmbLivro =
            new JComboBox<>();

    private final JTextField txtDataPrevista =
            new JTextField();

    private final JTextField txtObservacao =
            new JTextField();

    private final List<CarteiraBiblioteca> carteiras =
            new ArrayList<>();

    private final List<Livro> livros =
            new ArrayList<>();

    public TelaEmprestimo() {

        setTitle("Realizar empréstimo");
        setSize(600, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        txtDataPrevista.setText(
                LocalDate.now().plusDays(7).toString()
        );

        JPanel formulario = new JPanel(
                new GridLayout(4, 2, 10, 10)
        );

        formulario.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );

        formulario.add(new JLabel("Usuário:"));
        formulario.add(cmbUsuario);

        formulario.add(new JLabel("Livro:"));
        formulario.add(cmbLivro);

        formulario.add(
                new JLabel("Devolução (AAAA-MM-DD):")
        );
        formulario.add(txtDataPrevista);

        formulario.add(new JLabel("Observação:"));
        formulario.add(txtObservacao);

        JButton btnEmprestar =
                new JButton("Realizar empréstimo");

        JButton btnAtualizar =
                new JButton("Atualizar listas");

        btnEmprestar.addActionListener(e -> emprestar());
        btnAtualizar.addActionListener(e -> carregarDados());

        JPanel botoes = new JPanel();
        botoes.add(btnEmprestar);
        botoes.add(btnAtualizar);

        add(formulario, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        carregarDados();
    }

    private void carregarDados() {

        try {
            carteiras.clear();
            livros.clear();
            cmbUsuario.removeAllItems();
            cmbLivro.removeAllItems();

            CarteiraBibliotecaController carteiraController =
                    new CarteiraBibliotecaController();

            for (CarteiraBiblioteca carteira :
                    carteiraController.listarTodas()) {

                carteiras.add(carteira);

                cmbUsuario.addItem(
                        carteira.getUsuario().getId()
                        + " - "
                        + carteira.getUsuario().getNome()
                        + " | " + carteira.getStatus()
                );
            }

            LivroController livroController =
                    new LivroController();

            livros.addAll(
                    livroController.listarDisponiveis()
            );

            for (Livro livro : livros) {
                cmbLivro.addItem(
                        livro.getId() + " - "
                        + livro.getTitulo()
                        + " | disponíveis: "
                        + livro.getQuantidadeDisponivel()
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

    private void emprestar() {

        try {
            int indiceUsuario =
                    cmbUsuario.getSelectedIndex();

            int indiceLivro =
                    cmbLivro.getSelectedIndex();

            if (indiceUsuario < 0 || indiceLivro < 0) {
                throw new IllegalArgumentException(
                        "Selecione o usuário e o livro."
                );
            }

            CarteiraBiblioteca carteira =
                    carteiras.get(indiceUsuario);

            Livro livro = livros.get(indiceLivro);

            EmprestimoController controller =
                    new EmprestimoController();

            Emprestimo emprestimo =
                    controller.realizarEmprestimo(
                            carteira.getUsuario().getId(),
                            livro.getId(),
                            LocalDate.parse(
                                    txtDataPrevista
                                            .getText()
                                            .trim()
                            ),
                            txtObservacao.getText().trim()
                    );

            JOptionPane.showMessageDialog(
                    this,
                    "Empréstimo realizado com sucesso!\n"
                    + "ID: " + emprestimo.getId()
            );

            txtObservacao.setText("");
            carregarDados();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(
                    this,
                    erro.getMessage(),
                    "Empréstimo não realizado",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}