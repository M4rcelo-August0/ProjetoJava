/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.control;

import sistemabiblioteca.dao.LivroDAO;
import sistemabiblioteca.model.Livro;

import java.sql.SQLException;
import java.time.Year;
import java.util.List;

public class LivroController {

    private final LivroDAO livroDAO;

    public LivroController() {
        this.livroDAO = new LivroDAO();
    }

    public Livro cadastrar(Livro livro)
            throws SQLException {

        validarLivro(livro);

        if (livroDAO.buscarPorIsbn(livro.getIsbn()) != null) {
            throw new IllegalArgumentException(
                    "Já existe um livro cadastrado com esse ISBN."
            );
        }

        return livroDAO.cadastrar(livro);
    }

    public Livro buscarPorId(Long id) throws SQLException {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do livro é inválido."
            );
        }

        return livroDAO.buscarPorId(id);
    }

    public Livro buscarPorIsbn(String isbn)
            throws SQLException {

        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException(
                    "O ISBN deve ser informado."
            );
        }

        return livroDAO.buscarPorIsbn(isbn);
    }

    public List<Livro> listarTodos()
            throws SQLException {

        return livroDAO.listarTodos();
    }

    public List<Livro> listarDisponiveis()
            throws SQLException {

        return livroDAO.listarDisponiveis();
    }

    public void atualizar(Livro livro)
            throws SQLException {

        if (livro == null || livro.getId() == null) {
            throw new IllegalArgumentException(
                    "O livro precisa possuir um ID."
            );
        }

        validarLivro(livro);
        livroDAO.atualizar(livro);
    }

    private void validarLivro(Livro livro) {

        if (livro == null) {
            throw new IllegalArgumentException(
                    "O livro não pode ser nulo."
            );
        }

        if (livro.getTitulo() == null
                || livro.getTitulo().isBlank()) {
            throw new IllegalArgumentException(
                    "O título é obrigatório."
            );
        }

        if (livro.getIsbn() == null
                || livro.getIsbn().isBlank()) {
            throw new IllegalArgumentException(
                    "O ISBN é obrigatório."
            );
        }

        if (livro.getAutor() == null
                || livro.getAutor().isBlank()) {
            throw new IllegalArgumentException(
                    "O autor é obrigatório."
            );
        }

        if (livro.getAnoPublicacao() < 0
                || livro.getAnoPublicacao()
                > Year.now().getValue()) {
            throw new IllegalArgumentException(
                    "O ano de publicação é inválido."
            );
        }

        if (livro.getQuantidadeTotal() < 0) {
            throw new IllegalArgumentException(
                    "A quantidade total não pode ser negativa."
            );
        }

        if (livro.getQuantidadeDisponivel() < 0
                || livro.getQuantidadeDisponivel()
                > livro.getQuantidadeTotal()) {

            throw new IllegalArgumentException(
                    "A quantidade disponível é inválida."
            );
        }

        if (livro.getCategoria() == null
                || livro.getCategoria().getId() == null) {
            throw new IllegalArgumentException(
                    "O livro precisa possuir uma categoria."
            );
        }

        if (!"ATIVA".equalsIgnoreCase(
                livro.getCategoria().getStatus())) {

            throw new IllegalArgumentException(
                    "A categoria do livro precisa estar ativa."
            );
        }
    }
}