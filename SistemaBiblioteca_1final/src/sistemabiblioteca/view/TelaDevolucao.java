/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.view;

import sistemabiblioteca.control.EmprestimoController;
import sistemabiblioteca.model.Emprestimo;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class TelaDevolucao extends JFrame {

    private final JComboBox<String> cmbEmprestimo =
            new JComboBox<>();

    private final List<Emprestimo> pendentes =
            new ArrayList<>();

    public TelaDevolucao() {

        setTitle("Devolução de livro");
        setSize(700, 220);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel painel = new JPanel(new BorderLayout(10, 10));

        painel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        JButton btnDevolver =
                new JButton("Confirmar devolução");

        JButton btnAtualizar =
                new JButton("Atualizar empréstimos");

        JPanel botoes = new JPanel();
        botoes.add(btnDevolver);
        botoes.add(btnAtualizar);

        painel.add(cmbEmprestimo, BorderLayout.CENTER);
        painel.add(botoes, BorderLayout.SOUTH);

        btnDevolver.addActionListener(e -> devolver());
        btnAtualizar.addActionListener(e -> carregarPendentes());

        add(painel);

        carregarPendentes();
    }

    private void carregarPendentes() {

        try {
            pendentes.clear();
            cmbEmprestimo.removeAllItems();

            EmprestimoController controller =
                    new EmprestimoController();

            for (Emprestimo emprestimo :
                    controller.listarTodos()) {

                if (emprestimo.getDataDevolucao() == null) {

                    pendentes.add(emprestimo);

                    cmbEmprestimo.addItem(
                            "ID " + emprestimo.getId()
                            + " | "
                            + emprestimo.getUsuario().getNome()
                            + " | "
                            + emprestimo.getLivro().getTitulo()
                            + " | "
                            + emprestimo.getStatus()
                    );
                }
            }

            if (pendentes.isEmpty()) {
                cmbEmprestimo.addItem(
                        "Não existem empréstimos pendentes"
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

    private void devolver() {

        try {
            int indice =
                    cmbEmprestimo.getSelectedIndex();

            if (pendentes.isEmpty() || indice < 0) {
                throw new IllegalArgumentException(
                        "Não existe empréstimo para devolver."
                );
            }

            Emprestimo emprestimo =
                    pendentes.get(indice);

            int resposta = JOptionPane.showConfirmDialog(
                    this,
                    "Confirmar devolução do livro:\n"
                    + emprestimo.getLivro().getTitulo() + "?",
                    "Confirmar devolução",
                    JOptionPane.YES_NO_OPTION
            );

            if (resposta != JOptionPane.YES_OPTION) {
                return;
            }

            EmprestimoController controller =
                    new EmprestimoController();

            controller.devolverLivro(
                    emprestimo.getId()
            );

            JOptionPane.showMessageDialog(
                    this,
                    "Livro devolvido com sucesso!"
            );

            carregarPendentes();

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