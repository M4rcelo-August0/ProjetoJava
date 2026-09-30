/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.app;

import sistemabiblioteca.control.EmprestimoController;
import sistemabiblioteca.model.Emprestimo;

import java.util.List;

public class TesteDevolucao {

    public static void main(String[] args) {

        try {
            EmprestimoController controller =
                    new EmprestimoController();

            List<Emprestimo> emprestimos =
                    controller.listarTodos();

            Emprestimo emprestimoPendente = null;

            for (Emprestimo emprestimo : emprestimos) {

                if (emprestimo.getDataDevolucao() == null) {
                    emprestimoPendente = emprestimo;
                    break;
                }
            }

            if (emprestimoPendente == null) {
                System.out.println(
                        "Não existem empréstimos pendentes."
                );
                return;
            }

            System.out.println(
                    "ID do empréstimo: "
                    + emprestimoPendente.getId()
            );

            System.out.println(
                    "Usuário: "
                    + emprestimoPendente
                            .getUsuario()
                            .getNome()
            );

            System.out.println(
                    "Livro: "
                    + emprestimoPendente
                            .getLivro()
                            .getTitulo()
            );

            System.out.println(
                    "Status anterior: "
                    + emprestimoPendente.getStatus()
            );

            controller.devolverLivro(
                    emprestimoPendente.getId()
            );

            System.out.println(
                    "Livro devolvido com sucesso!"
            );

        } catch (Exception erro) {

            System.out.println(
                    "Erro ao devolver livro: "
                    + erro.getMessage()
            );

            erro.printStackTrace();
        }
    }
}
