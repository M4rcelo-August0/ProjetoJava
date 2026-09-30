/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.dao;

import sistemabiblioteca.model.Usuario;
import sistemabiblioteca.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario cadastrar(Usuario usuario) throws SQLException {

        String sql = """
            INSERT INTO usuario
            (nome, email, cpf, telefone, endereco,
             data_nascimento, data_cadastro, status, senha)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            )
        ) {
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getCpf());
            comando.setString(4, usuario.getTelefone());
            comando.setString(5, usuario.getEndereco());

            if (usuario.getDataNascimento() != null) {
                comando.setDate(
                        6,
                        Date.valueOf(usuario.getDataNascimento())
                );
            } else {
                comando.setNull(6, java.sql.Types.DATE);
            }

            comando.setDate(
                    7,
                    Date.valueOf(usuario.getDataCadastro())
            );

            comando.setString(8, usuario.getStatus());
            comando.setString(9, usuario.getSenha());

            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    usuario.setId(resultado.getLong(1));
                }
            }

            return usuario;
        }
    }

    public Usuario buscarPorId(Long id) throws SQLException {

        String sql = "SELECT * FROM usuario WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarUsuario(resultado);
                }
            }
        }

        return null;
    }

    public Usuario buscarPorCpf(String cpf) throws SQLException {

        String sql = "SELECT * FROM usuario WHERE cpf = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, cpf);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarUsuario(resultado);
                }
            }
        }

        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {

        String sql = "SELECT * FROM usuario ORDER BY nome";
        List<Usuario> usuarios = new ArrayList<>();

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                usuarios.add(criarUsuario(resultado));
            }
        }

        return usuarios;
    }

    public void atualizar(Usuario usuario) throws SQLException {

        String sql = """
            UPDATE usuario
               SET nome = ?,
                   email = ?,
                   cpf = ?,
                   telefone = ?,
                   endereco = ?,
                   data_nascimento = ?,
                   status = ?,
                   senha = ?
             WHERE id = ?
            """;

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, usuario.getNome());
            comando.setString(2, usuario.getEmail());
            comando.setString(3, usuario.getCpf());
            comando.setString(4, usuario.getTelefone());
            comando.setString(5, usuario.getEndereco());

            if (usuario.getDataNascimento() != null) {
                comando.setDate(
                        6,
                        Date.valueOf(usuario.getDataNascimento())
                );
            } else {
                comando.setNull(6, java.sql.Types.DATE);
            }

            comando.setString(7, usuario.getStatus());
            comando.setString(8, usuario.getSenha());
            comando.setLong(9, usuario.getId());

            comando.executeUpdate();
        }
    }

    public void alterarStatus(Long id, String status) throws SQLException {

        String sql = "UPDATE usuario SET status = ? WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, status);
            comando.setLong(2, id);
            comando.executeUpdate();
        }
    }

    private Usuario criarUsuario(ResultSet resultado)
            throws SQLException {

        Usuario usuario = new Usuario();

        usuario.setId(resultado.getLong("id"));
        usuario.setNome(resultado.getString("nome"));
        usuario.setEmail(resultado.getString("email"));
        usuario.setCpf(resultado.getString("cpf"));
        usuario.setTelefone(resultado.getString("telefone"));
        usuario.setEndereco(resultado.getString("endereco"));

        Date nascimento = resultado.getDate("data_nascimento");

        if (nascimento != null) {
            usuario.setDataNascimento(nascimento.toLocalDate());
        }

        usuario.setDataCadastro(
                resultado.getDate("data_cadastro").toLocalDate()
        );

        usuario.setStatus(resultado.getString("status"));
        usuario.setSenha(resultado.getString("senha"));

        return usuario;
    }
}
