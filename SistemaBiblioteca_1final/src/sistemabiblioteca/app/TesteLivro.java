/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.app;

import sistemabiblioteca.control.CategoriaController;
import sistemabiblioteca.control.LivroController;
import sistemabiblioteca.model.Categoria;
import sistemabiblioteca.model.Livro;

import java.util.List;

public class TesteLivro {

    public static void main(String[] args) {

        try {
            CategoriaController categoriaController =
                    new CategoriaController();

            List<Categoria> categorias =
                    categoriaController.listarTodas();

            if (categorias.isEmpty()) {
                System.out.println(
                        "Cadastre uma categoria antes do livro."
                );
                return;
            }

            Categoria categoria = categorias.get(0);

            long numeroUnico = System.currentTimeMillis();

            Livro livro = new Livro();

            livro.setTitulo(
                    "Dom Casmurro - Teste " + numeroUnico
            );

            livro.setIsbn(
                    "978" + numeroUnico
            );

            livro.setAutor("Machado de Assis");
            livro.setEditora("Editora Teste");
            livro.setAnoPublicacao(1899);
            livro.setQuantidadeTotal(3);
            livro.setQuantidadeDisponivel(3);

            livro.setDescricao(
                    "Livro cadastrado para testar o sistema."
            );

            livro.setCategoria(categoria);

            LivroController livroController =
                    new LivroController();

            livroController.cadastrar(livro);

            System.out.println(
                    "Livro cadastrado com sucesso!"
            );

            System.out.println(
                    "ID: " + livro.getId()
            );

            System.out.println(
                    "Título: " + livro.getTitulo()
            );

            System.out.println(
                    "Categoria: " + categoria.getNome()
            );

            System.out.println(
                    "Quantidade disponível: "
                    + livro.getQuantidadeDisponivel()
            );

        } catch (Exception erro) {
            System.out.println(
                    "Erro ao cadastrar livro: "
                    + erro.getMessage()
            );

            erro.printStackTrace();
        }
    }
}