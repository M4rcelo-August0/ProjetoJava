/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.control;

import sistemabiblioteca.dao.UsuarioDAO;
import sistemabiblioteca.model.Usuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public Usuario cadastrar(Usuario usuario) throws SQLException {

        validarUsuario(usuario);

        if (usuarioDAO.buscarPorCpf(usuario.getCpf()) != null) {
            throw new IllegalArgumentException(
                    "Já existe um usuário cadastrado com esse CPF."
            );
        }

        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(LocalDate.now());
        }

        if (usuario.getStatus() == null
                || usuario.getStatus().isBlank()) {
            usuario.setStatus("ATIVO");
        }

        return usuarioDAO.cadastrar(usuario);
    }

    public Usuario buscarPorId(Long id) throws SQLException {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID do usuário é inválido."
            );
        }

        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> listarTodos() throws SQLException {
        return usuarioDAO.listarTodos();
    }

    public void atualizar(Usuario usuario) throws SQLException {

        if (usuario.getId() == null) {
            throw new IllegalArgumentException(
                    "O usuário precisa possuir um ID."
            );
        }

        validarUsuario(usuario);
        usuarioDAO.atualizar(usuario);
    }

    public void alterarStatus(Long id, String status)
            throws SQLException {

        Set<String> statusPermitidos = Set.of(
                "ATIVO",
                "INATIVO",
                "BLOQUEADO"
        );

        if (!statusPermitidos.contains(status)) {
            throw new IllegalArgumentException(
                    "Status de usuário inválido."
            );
        }

        usuarioDAO.alterarStatus(id, status);
    }

    private void validarUsuario(Usuario usuario) {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "O usuário não pode ser nulo."
            );
        }

        if (usuario.getNome() == null
                || usuario.getNome().isBlank()) {
            throw new IllegalArgumentException(
                    "O nome é obrigatório."
            );
        }

        if (usuario.getEmail() == null
                || usuario.getEmail().isBlank()) {
            throw new IllegalArgumentException(
                    "O e-mail é obrigatório."
            );
        }

        if (usuario.getCpf() == null
                || usuario.getCpf().isBlank()) {
            throw new IllegalArgumentException(
                    "O CPF é obrigatório."
            );
        }

        if (usuario.getSenha() == null
                || usuario.getSenha().isBlank()) {
            throw new IllegalArgumentException(
                    "A senha é obrigatória."
            );
        }
    }
}
