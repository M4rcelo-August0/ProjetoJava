/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.dao;

import sistemabiblioteca.model.CarteiraBiblioteca;
import sistemabiblioteca.model.Usuario;
import sistemabiblioteca.util.Conexao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class CarteiraBibliotecaDAO {

    public CarteiraBiblioteca cadastrar(
            CarteiraBiblioteca carteira) throws SQLException {

        String sql = "INSERT INTO carteira_biblioteca "
                + "(numero, data_criacao, status, limite_emprestimos, "
                + "quantidade_emprestimos, data_ultima_atualizacao, "
                + "observacao, usuario_id, codigo_carteira) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(
                    sql,
                    Statement.RETURN_GENERATED_KEYS
            )
        ) {
            comando.setString(1, carteira.getNumero());

            comando.setDate(
                    2,
                    Date.valueOf(carteira.getDataCriacao())
            );

            comando.setString(3, carteira.getStatus());

            comando.setInt(
                    4,
                    carteira.getLimiteEmprestimos()
            );

            comando.setInt(
                    5,
                    carteira.getQuantidadeEmprestimos()
            );

            if (carteira.getDataUltimaAtualizacao() != null) {
                comando.setDate(
                        6,
                        Date.valueOf(
                                carteira.getDataUltimaAtualizacao()
                        )
                );
            } else {
                comando.setNull(6, Types.DATE);
            }

            comando.setString(
                    7,
                    carteira.getObservacao()
            );

            comando.setLong(
                    8,
                    carteira.getUsuario().getId()
            );

            comando.setString(
                    9,
                    carteira.getCodigoCarteira()
            );

            comando.executeUpdate();

            try (ResultSet resultado = comando.getGeneratedKeys()) {
                if (resultado.next()) {
                    carteira.setId(resultado.getLong(1));
                }
            }
        }

        return carteira;
    }

    public CarteiraBiblioteca buscarPorId(Long id)
            throws SQLException {

        String sql = "SELECT c.*, "
                + "u.nome AS usuario_nome, "
                + "u.email AS usuario_email, "
                + "u.status AS usuario_status "
                + "FROM carteira_biblioteca c "
                + "INNER JOIN usuario u ON u.id = c.usuario_id "
                + "WHERE c.id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setLong(1, id);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarCarteira(resultado);
                }
            }
        }

        return null;
    }

    public CarteiraBiblioteca buscarPorUsuario(
            Long usuarioId) throws SQLException {

        String sql = "SELECT c.*, "
                + "u.nome AS usuario_nome, "
                + "u.email AS usuario_email, "
                + "u.status AS usuario_status "
                + "FROM carteira_biblioteca c "
                + "INNER JOIN usuario u ON u.id = c.usuario_id "
                + "WHERE c.usuario_id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setLong(1, usuarioId);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return criarCarteira(resultado);
                }
            }
        }

        return null;
    }

    public List<CarteiraBiblioteca> listarTodas()
            throws SQLException {

        String sql = "SELECT c.*, "
                + "u.nome AS usuario_nome, "
                + "u.email AS usuario_email, "
                + "u.status AS usuario_status "
                + "FROM carteira_biblioteca c "
                + "INNER JOIN usuario u ON u.id = c.usuario_id "
                + "ORDER BY u.nome";

        List<CarteiraBiblioteca> carteiras =
                new ArrayList<>();

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                carteiras.add(criarCarteira(resultado));
            }
        }

        return carteiras;
    }

    public void atualizar(CarteiraBiblioteca carteira)
            throws SQLException {

        String sql = "UPDATE carteira_biblioteca SET "
                + "numero = ?, status = ?, limite_emprestimos = ?, "
                + "quantidade_emprestimos = ?, "
                + "data_ultima_atualizacao = ?, observacao = ?, "
                + "codigo_carteira = ? "
                + "WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, carteira.getNumero());
            comando.setString(2, carteira.getStatus());

            comando.setInt(
                    3,
                    carteira.getLimiteEmprestimos()
            );

            comando.setInt(
                    4,
                    carteira.getQuantidadeEmprestimos()
            );

            if (carteira.getDataUltimaAtualizacao() != null) {
                comando.setDate(
                        5,
                        Date.valueOf(
                                carteira.getDataUltimaAtualizacao()
                        )
                );
            } else {
                comando.setNull(5, Types.DATE);
            }

            comando.setString(
                    6,
                    carteira.getObservacao()
            );

            comando.setString(
                    7,
                    carteira.getCodigoCarteira()
            );

            comando.setLong(8, carteira.getId());

            comando.executeUpdate();
        }
    }

    public void alterarStatus(Long id, String status)
            throws SQLException {

        String sql = "UPDATE carteira_biblioteca "
                + "SET status = ?, "
                + "data_ultima_atualizacao = CURRENT_DATE "
                + "WHERE id = ?";

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando = conexao.prepareStatement(sql)
        ) {
            comando.setString(1, status);
            comando.setLong(2, id);
            comando.executeUpdate();
        }
    }

    private CarteiraBiblioteca criarCarteira(
            ResultSet resultado) throws SQLException {

        CarteiraBiblioteca carteira =
                new CarteiraBiblioteca();

        carteira.setId(resultado.getLong("id"));
        carteira.setNumero(resultado.getString("numero"));

        Date dataCriacao =
                resultado.getDate("data_criacao");

        if (dataCriacao != null) {
            carteira.setDataCriacao(
                    dataCriacao.toLocalDate()
            );
        }

        carteira.setStatus(resultado.getString("status"));

        carteira.setLimiteEmprestimos(
                resultado.getInt("limite_emprestimos")
        );

        carteira.setQuantidadeEmprestimos(
                resultado.getInt("quantidade_emprestimos")
        );

        Date atualizacao =
                resultado.getDate("data_ultima_atualizacao");

        if (atualizacao != null) {
            carteira.setDataUltimaAtualizacao(
                    atualizacao.toLocalDate()
            );
        }

        carteira.setObservacao(
                resultado.getString("observacao")
        );

        carteira.setCodigoCarteira(
                resultado.getString("codigo_carteira")
        );

        Usuario usuario = new Usuario();

        usuario.setId(resultado.getLong("usuario_id"));
        usuario.setNome(
                resultado.getString("usuario_nome")
        );
        usuario.setEmail(
                resultado.getString("usuario_email")
        );
        usuario.setStatus(
                resultado.getString("usuario_status")
        );

        carteira.setUsuario(usuario);

        return carteira;
    }
}