/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.view;

import sistemabiblioteca.control.EmprestimoController;
import sistemabiblioteca.model.Emprestimo;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class TelaConsultaEmprestimos extends JFrame {

    private final DefaultTableModel modelo =
            new DefaultTableModel(
                    new Object[]{
                        "ID",
                        "Usuário",
                        "Livro",
                        "Empréstimo",
                        "Previsão",
                        "Devolução",
                        "Status"
                    },
                    0
            ) {
                @Override
                public boolean isCellEditable(
                        int linha,
                        int coluna) {
                    return false;
                }
            };

    private final JTable tabela = new JTable(modelo);

    public TelaConsultaEmprestimos() {

        setTitle("Consulta de empréstimos");
        setSize(950, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton btnAtualizar =
                new JButton("Atualizar");

        btnAtualizar.addActionListener(e -> carregar());

        add(new JScrollPane(tabela), BorderLayout.CENTER);
        add(btnAtualizar, BorderLayout.SOUTH);

        carregar();
    }

    private void carregar() {

        try {
            modelo.setRowCount(0);

            EmprestimoController controller =
                    new EmprestimoController();

            for (Emprestimo emprestimo :
                    controller.listarTodos()) {

                modelo.addRow(new Object[]{
                    emprestimo.getId(),
                    emprestimo.getUsuario().getNome(),
                    emprestimo.getLivro().getTitulo(),
                    emprestimo.getDataEmprestimo(),
                    emprestimo.getDataPrevistaDevolucao(),
                    emprestimo.getDataDevolucao(),
                    emprestimo.getStatus()
                });
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
}
