/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.control;

import sistemabiblioteca.dao.EmprestimoDAO;
import sistemabiblioteca.model.Emprestimo;
import sistemabiblioteca.model.Livro;
import sistemabiblioteca.model.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {

    private final EmprestimoDAO emprestimoDAO;

    public EmprestimoController() {
        this.emprestimoDAO = new EmprestimoDAO();
    }

    public Emprestimo realizarEmprestimo(
            Long usuarioId,
            Long livroId,
            LocalDate dataPrevistaDevolucao,
            String observacao) throws SQLException {

        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException(
                    "O ID do usuário é inválido."
            );
        }

        if (livroId == null || livroId <= 0) {
            throw new IllegalArgumentException(
                    "O ID do livro é inválido."
            );
        }

        if (dataPrevistaDevolucao == null) {
            throw new IllegalArgumentException(
                    "Informe a data prevista de devolução."
            );
        }

        if (dataPrevistaDevolucao.isBefore(
                LocalDate.now())) {

            throw new IllegalArgumentException(
                    "A data prevista não pode estar no passado."
            );
        }

        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);

        Livro livro = new Livro();
        livro.setId(livroId);

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setUsuario(usuario);
        emprestimo.setLivro(livro);
        emprestimo.setDataEmprestimo(LocalDate.now());

        emprestimo.setDataPrevistaDevolucao(
                dataPrevistaDevolucao
        );

        emprestimo.setStatus("ATIVO");
        emprestimo.setObservacao(observacao);

        return emprestimoDAO.realizar(emprestimo);
    }

    public void devolverLivro(Long emprestimoId)
            throws SQLException {

        if (emprestimoId == null
                || emprestimoId <= 0) {

            throw new IllegalArgumentException(
                    "O ID do empréstimo é inválido."
            );
        }

        emprestimoDAO.devolver(emprestimoId);
    }

    public List<Emprestimo> listarTodos()
            throws SQLException {

        emprestimoDAO.atualizarBloqueios();
        return emprestimoDAO.listarTodos();
    }

    public List<Emprestimo> listarPorUsuario(
            Long usuarioId) throws SQLException {

        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException(
                    "O ID do usuário é inválido."
            );
        }

        emprestimoDAO.atualizarBloqueios();

        return emprestimoDAO.listarPorUsuario(
                usuarioId
        );
    }

    public void atualizarBloqueios()
            throws SQLException {

        emprestimoDAO.atualizarBloqueios();
    }
}
