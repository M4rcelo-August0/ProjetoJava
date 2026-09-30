/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.dao;

import sistemabiblioteca.model.Categoria;
import sistemabiblioteca.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public Categoria cadastrar(Categoria categoria)
            throws SQLException {

        String sql = "INSERT INTO categoria "
                + "(nome, descricao, codigo, data_cadastro, "
                + "status, faixa_etaria, genero, observacao) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )
        ) {
            comando.setString(1, categoria.getNome());
            comando.setString(2, categoria.getDescricao());
            comando.setString(3, categoria.getCodigo());

            comando.setDate(
                    4,
                    Date.valueOf(categoria.getDataCadastro())
            );

            comando.setString(5, categoria.getStatus());
            comando.setString(6, categoria.getFaixaEtaria());
            comando.setString(7, categoria.getGenero());
            comando.setString(8, categoria.getObservacao());

            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    categoria.setId(resultado.getLong(1));
                }
            }
        }

        return categoria;
    }

    public Categoria buscarPorId(Long id) throws SQLException {

        String sql = "SELECT * FROM categoria WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarCategoria(resultado);
                }
            }
        }

        return null;
    }

    public Categoria buscarPorCodigo(String codigo)
            throws SQLException {

        String sql = "SELECT * FROM categoria WHERE codigo = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, codigo);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarCategoria(resultado);
                }
            }
        }

        return null;
    }

    public Categoria buscarPorNome(String nome)
            throws SQLException {

        String sql = "SELECT * FROM categoria WHERE nome = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, nome);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarCategoria(resultado);
                }
            }
        }

        return null;
    }

    public List<Categoria> listarTodas() throws SQLException {

        String sql = "SELECT * FROM categoria ORDER BY nome";
        List<Categoria> categorias = new ArrayList<>();

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                categorias.add(criarCategoria(resultado));
            }
        }

        return categorias;
    }

    public void atualizar(Categoria categoria)
            throws SQLException {

        String sql = "UPDATE categoria SET "
                + "nome = ?, descricao = ?, codigo = ?, "
                + "status = ?, faixa_etaria = ?, genero = ?, "
                + "observacao = ? "
                + "WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, categoria.getNome());
            comando.setString(2, categoria.getDescricao());
            comando.setString(3, categoria.getCodigo());
            comando.setString(4, categoria.getStatus());
            comando.setString(5, categoria.getFaixaEtaria());
            comando.setString(6, categoria.getGenero());
            comando.setString(7, categoria.getObservacao());
            comando.setLong(8, categoria.getId());

            comando.executeUpdate();
        }
    }

    public void alterarStatus(Long id, String status)
            throws SQLException {

        String sql = "UPDATE categoria "
                + "SET status = ? WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, status);
            comando.setLong(2, id);
            comando.executeUpdate();
        }
    }

    private Categoria criarCategoria(ResultSet resultado)
            throws SQLException {

        Categoria categoria = new Categoria();

        categoria.setId(resultado.getLong("id"));
        categoria.setNome(resultado.getString("nome"));
        categoria.setDescricao(resultado.getString("descricao"));
        categoria.setCodigo(resultado.getString("codigo"));

        Date dataCadastro =
                resultado.getDate("data_cadastro");

        if (dataCadastro != null) {
            categoria.setDataCadastro(
                    dataCadastro.toLocalDate()
            );
        }

        categoria.setStatus(resultado.getString("status"));
        categoria.setFaixaEtaria(
                resultado.getString("faixa_etaria")
        );
        categoria.setGenero(resultado.getString("genero"));
        categoria.setObservacao(
                resultado.getString("observacao")
        );

        return categoria;
    }
}