/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.app;

import sistemabiblioteca.control.CategoriaController;
import sistemabiblioteca.model.Categoria;

public class TesteCategoria {

    public static void main(String[] args) {

        try {
            long numeroUnico = System.currentTimeMillis();

            Categoria categoria = new Categoria();

            categoria.setNome(
                    "Literatura " + numeroUnico
            );

            categoria.setCodigo(
                    "LIT-" + numeroUnico
            );

            categoria.setDescricao(
                    "Livros de literatura geral."
            );

            categoria.setFaixaEtaria("Livre");
            categoria.setGenero("Literatura");

            categoria.setObservacao(
                    "Categoria criada no teste do sistema."
            );

            CategoriaController controller =
                    new CategoriaController();

            controller.cadastrar(categoria);

            System.out.println(
                    "Categoria cadastrada com sucesso!"
            );

            System.out.println(
                    "ID: " + categoria.getId()
            );

            System.out.println(
                    "Nome: " + categoria.getNome()
            );

            System.out.println(
                    "Status: " + categoria.getStatus()
            );

        } catch (Exception erro) {
            System.out.println(
                    "Erro ao cadastrar categoria: "
                    + erro.getMessage()
            );

            erro.printStackTrace();
        }
    }
}