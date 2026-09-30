/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.app;

import sistemabiblioteca.control.CarteiraBibliotecaController;
import sistemabiblioteca.control.EmprestimoController;
import sistemabiblioteca.control.LivroController;
import sistemabiblioteca.model.CarteiraBiblioteca;
import sistemabiblioteca.model.Emprestimo;
import sistemabiblioteca.model.Livro;
import sistemabiblioteca.model.Usuario;

import java.time.LocalDate;
import java.util.List;

public class TesteEmprestimo {

    public static void main(String[] args) {

        try {
            CarteiraBibliotecaController carteiraController =
                    new CarteiraBibliotecaController();

            List<CarteiraBiblioteca> carteiras =
                    carteiraController.listarTodas();

            if (carteiras.isEmpty()) {
                System.out.println(
                        "Cadastre uma carteira primeiro."
                );
                return;
            }

            CarteiraBiblioteca carteira =
                    carteiras.get(0);

            Usuario usuario = carteira.getUsuario();

            LivroController livroController =
                    new LivroController();

            List<Livro> livros =
                    livroController.listarDisponiveis();

            if (livros.isEmpty()) {
                System.out.println(
                        "Não existem livros disponíveis."
                );
                return;
            }

            Livro livro = livros.get(0);

            EmprestimoController controller =
                    new EmprestimoController();

            Emprestimo emprestimo =
                    controller.realizarEmprestimo(
                            usuario.getId(),
                            livro.getId(),
                            LocalDate.now().plusDays(7),
                            "Empréstimo criado no teste."
                    );

            System.out.println(
                    "Empréstimo realizado com sucesso!"
            );

            System.out.println(
                    "ID: " + emprestimo.getId()
            );

            System.out.println(
                    "Usuário: " + usuario.getNome()
            );

            System.out.println(
                    "Livro: " + livro.getTitulo()
            );

            System.out.println(
                    "Data prevista: "
                    + emprestimo
                            .getDataPrevistaDevolucao()
            );

            System.out.println(
                    "Status: " + emprestimo.getStatus()
            );

        } catch (Exception erro) {
            System.out.println(
                    "Erro ao realizar empréstimo: "
                    + erro.getMessage()
            );

            erro.printStackTrace();
        }
    }
}