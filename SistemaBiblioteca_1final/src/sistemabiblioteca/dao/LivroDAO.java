/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.dao;

import sistemabiblioteca.model.Categoria;
import sistemabiblioteca.model.Livro;
import sistemabiblioteca.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public Livro cadastrar(Livro livro) throws SQLException {

        String sql = "INSERT INTO livro "
                + "(titulo, isbn, autor, editora, ano_publicacao, "
                + "quantidade_total, quantidade_disponivel, "
                + "descricao, categoria_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )
        ) {
            comando.setString(1, livro.getTitulo());
            comando.setString(2, livro.getIsbn());
            comando.setString(3, livro.getAutor());
            comando.setString(4, livro.getEditora());
            comando.setInt(5, livro.getAnoPublicacao());
            comando.setInt(6, livro.getQuantidadeTotal());
            comando.setInt(7, livro.getQuantidadeDisponivel());
            comando.setString(8, livro.getDescricao());

            comando.setLong(
                    9,
                    livro.getCategoria().getId()
            );

            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    livro.setId(resultado.getLong(1));
                }
            }
        }

        return livro;
    }

    public Livro buscarPorId(Long id) throws SQLException {

        String sql = consultaCompleta()
                + " WHERE l.id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarLivro(resultado);
                }
            }
        }

        return null;
    }

    public Livro buscarPorIsbn(String isbn)
            throws SQLException {

        String sql = consultaCompleta()
                + " WHERE l.isbn = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, isbn);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarLivro(resultado);
                }
            }
        }

        return null;
    }

    public List<Livro> listarTodos() throws SQLException {

        String sql = consultaCompleta()
                + " ORDER BY l.titulo";

        List<Livro> livros = new ArrayList<>();

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                livros.add(criarLivro(resultado));
            }
        }

        return livros;
    }

    public List<Livro> listarDisponiveis()
            throws SQLException {

        String sql = consultaCompleta()
                + " WHERE l.quantidade_disponivel > 0 "
                + "ORDER BY l.titulo";

        List<Livro> livros = new ArrayList<>();

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                livros.add(criarLivro(resultado));
            }
        }

        return livros;
    }

    public void atualizar(Livro livro) throws SQLException {

        String sql = "UPDATE livro SET "
                + "titulo = ?, isbn = ?, autor = ?, editora = ?, "
                + "ano_publicacao = ?, quantidade_total = ?, "
                + "quantidade_disponivel = ?, descricao = ?, "
                + "categoria_id = ? "
                + "WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, livro.getTitulo());
            comando.setString(2, livro.getIsbn());
            comando.setString(3, livro.getAutor());
            comando.setString(4, livro.getEditora());
            comando.setInt(5, livro.getAnoPublicacao());
            comando.setInt(6, livro.getQuantidadeTotal());
            comando.setInt(7, livro.getQuantidadeDisponivel());
            comando.setString(8, livro.getDescricao());

            comando.setLong(
                    9,
                    livro.getCategoria().getId()
            );

            comando.setLong(10, livro.getId());

            comando.executeUpdate();
        }
    }

    private String consultaCompleta() {

        return "SELECT l.*, "
                + "c.nome AS categoria_nome, "
                + "c.codigo AS categoria_codigo, "
                + "c.status AS categoria_status "
                + "FROM livro l "
                + "INNER JOIN categoria c "
                + "ON c.id = l.categoria_id";
    }

    private Livro criarLivro(ResultSet resultado)
            throws SQLException {

        Livro livro = new Livro();

        livro.setId(resultado.getLong("id"));
        livro.setTitulo(resultado.getString("titulo"));
        livro.setIsbn(resultado.getString("isbn"));
        livro.setAutor(resultado.getString("autor"));
        livro.setEditora(resultado.getString("editora"));

        livro.setAnoPublicacao(
                resultado.getInt("ano_publicacao")
        );

        livro.setQuantidadeTotal(
                resultado.getInt("quantidade_total")
        );

        livro.setQuantidadeDisponivel(
                resultado.getInt("quantidade_disponivel")
        );

        livro.setDescricao(
                resultado.getString("descricao")
        );

        Categoria categoria = new Categoria();

        categoria.setId(
                resultado.getLong("categoria_id")
        );

        categoria.setNome(
                resultado.getString("categoria_nome")
        );

        categoria.setCodigo(
                resultado.getString("categoria_codigo")
        );

        categoria.setStatus(
                resultado.getString("categoria_status")
        );

        livro.setCategoria(categoria);

        return livro;
    }
}