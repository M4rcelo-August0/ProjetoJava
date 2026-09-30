/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.dao;

import sistemabiblioteca.model.Emprestimo;
import sistemabiblioteca.model.Livro;
import sistemabiblioteca.model.Usuario;
import sistemabiblioteca.util.Conexao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    public Emprestimo realizar(Emprestimo emprestimo)
            throws SQLException {

        Connection conexao = null;

        try {
            conexao = Conexao.conectar();
            conexao.setAutoCommit(false);

            atualizarBloqueios(conexao);

            Long usuarioId =
                    emprestimo.getUsuario().getId();

            Long livroId =
                    emprestimo.getLivro().getId();

            Long carteiraId;
            int quantidadeEmprestimos;
            int limiteEmprestimos;

            String sqlUsuario =
                    "SELECT u.status AS usuario_status, "
                    + "c.id AS carteira_id, "
                    + "c.status AS carteira_status, "
                    + "c.quantidade_emprestimos, "
                    + "c.limite_emprestimos "
                    + "FROM usuario u "
                    + "INNER JOIN carteira_biblioteca c "
                    + "ON c.usuario_id = u.id "
                    + "WHERE u.id = ? "
                    + "FOR UPDATE";

            try (PreparedStatement comando =
                    conexao.prepareStatement(sqlUsuario)) {

                comando.setLong(1, usuarioId);

                try (ResultSet resultado =
                        comando.executeQuery()) {

                    if (!resultado.next()) {
                        throw new IllegalStateException(
                                "O usuário não possui carteira."
                        );
                    }

                    String statusUsuario =
                            resultado.getString(
                                    "usuario_status"
                            );

                    String statusCarteira =
                            resultado.getString(
                                    "carteira_status"
                            );

                    if (!"ATIVO".equalsIgnoreCase(
                            statusUsuario)) {

                        throw new IllegalStateException(
                                "O usuário está bloqueado ou inativo."
                        );
                    }

                    if (!"ATIVA".equalsIgnoreCase(
                            statusCarteira)) {

                        throw new IllegalStateException(
                                "A carteira não está ativa."
                        );
                    }

                    carteiraId =
                            resultado.getLong("carteira_id");

                    quantidadeEmprestimos =
                            resultado.getInt(
                                    "quantidade_emprestimos"
                            );

                    limiteEmprestimos =
                            resultado.getInt(
                                    "limite_emprestimos"
                            );

                    if (quantidadeEmprestimos
                            >= limiteEmprestimos) {

                        throw new IllegalStateException(
                                "O usuário atingiu o limite "
                                + "de empréstimos."
                        );
                    }
                }
            }

            String sqlLivro =
                    "SELECT quantidade_disponivel "
                    + "FROM livro "
                    + "WHERE id = ? "
                    + "FOR UPDATE";

            try (PreparedStatement comando =
                    conexao.prepareStatement(sqlLivro)) {

                comando.setLong(1, livroId);

                try (ResultSet resultado =
                        comando.executeQuery()) {

                    if (!resultado.next()) {
                        throw new IllegalStateException(
                                "Livro não encontrado."
                        );
                    }

                    int quantidadeDisponivel =
                            resultado.getInt(
                                    "quantidade_disponivel"
                            );

                    if (quantidadeDisponivel <= 0) {
                        throw new IllegalStateException(
                                "O livro não está disponível."
                        );
                    }
                }
            }

            String sqlEmprestimo =
                    "INSERT INTO emprestimo "
                    + "(data_emprestimo, "
                    + "data_prevista_devolucao, "
                    + "data_devolucao, status, observacao, "
                    + "usuario_id, livro_id) "
                    + "VALUES (?, ?, NULL, ?, ?, ?, ?)";

            try (PreparedStatement comando =
                    conexao.prepareStatement(
                            sqlEmprestimo,
                            Statement.RETURN_GENERATED_KEYS
                    )) {

                comando.setDate(
                        1,
                        Date.valueOf(
                                emprestimo.getDataEmprestimo()
                        )
                );

                comando.setDate(
                        2,
                        Date.valueOf(
                                emprestimo
                                        .getDataPrevistaDevolucao()
                        )
                );

                comando.setString(3, "ATIVO");

                comando.setString(
                        4,
                        emprestimo.getObservacao()
                );

                comando.setLong(5, usuarioId);
                comando.setLong(6, livroId);

                comando.executeUpdate();

                try (ResultSet resultado =
                        comando.getGeneratedKeys()) {

                    if (resultado.next()) {
                        emprestimo.setId(
                                resultado.getLong(1)
                        );
                    }
                }
            }

            String sqlAtualizarLivro =
                    "UPDATE livro "
                    + "SET quantidade_disponivel = "
                    + "quantidade_disponivel - 1 "
                    + "WHERE id = ?";

            try (PreparedStatement comando =
                    conexao.prepareStatement(
                            sqlAtualizarLivro
                    )) {

                comando.setLong(1, livroId);
                comando.executeUpdate();
            }

            String sqlAtualizarCarteira =
                    "UPDATE carteira_biblioteca "
                    + "SET quantidade_emprestimos = "
                    + "quantidade_emprestimos + 1, "
                    + "data_ultima_atualizacao = CURRENT_DATE "
                    + "WHERE id = ?";

            try (PreparedStatement comando =
                    conexao.prepareStatement(
                            sqlAtualizarCarteira
                    )) {

                comando.setLong(1, carteiraId);
                comando.executeUpdate();
            }

            conexao.commit();

            emprestimo.setStatus("ATIVO");

            return emprestimo;

        } catch (SQLException | RuntimeException erro) {

            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    erro.addSuppressed(erroRollback);
                }
            }

            throw erro;

        } finally {

            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException erroFechamento) {
                    // A operação principal já foi finalizada.
                }
            }
        }
    }

    public void devolver(Long emprestimoId)
            throws SQLException {

        Connection conexao = null;

        try {
            conexao = Conexao.conectar();
            conexao.setAutoCommit(false);

            Long livroId;
            Long usuarioId;

            String sqlBuscar =
                    "SELECT livro_id, usuario_id, "
                    + "data_devolucao "
                    + "FROM emprestimo "
                    + "WHERE id = ? "
                    + "FOR UPDATE";

            try (PreparedStatement comando =
                    conexao.prepareStatement(sqlBuscar)) {

                comando.setLong(1, emprestimoId);

                try (ResultSet resultado =
                        comando.executeQuery()) {

                    if (!resultado.next()) {
                        throw new IllegalStateException(
                                "Empréstimo não encontrado."
                        );
                    }

                    if (resultado.getDate(
                            "data_devolucao") != null) {

                        throw new IllegalStateException(
                                "Este empréstimo já foi devolvido."
                        );
                    }

                    livroId =
                            resultado.getLong("livro_id");

                    usuarioId =
                            resultado.getLong("usuario_id");
                }
            }

            String sqlDevolver =
                    "UPDATE emprestimo "
                    + "SET data_devolucao = CURRENT_DATE, "
                    + "status = 'DEVOLVIDO' "
                    + "WHERE id = ?";

            try (PreparedStatement comando =
                    conexao.prepareStatement(sqlDevolver)) {

                comando.setLong(1, emprestimoId);
                comando.executeUpdate();
            }

            String sqlLivro =
                    "UPDATE livro "
                    + "SET quantidade_disponivel = "
                    + "LEAST(quantidade_total, "
                    + "quantidade_disponivel + 1) "
                    + "WHERE id = ?";

            try (PreparedStatement comando =
                    conexao.prepareStatement(sqlLivro)) {

                comando.setLong(1, livroId);
                comando.executeUpdate();
            }

            String sqlCarteira =
                    "UPDATE carteira_biblioteca "
                    + "SET quantidade_emprestimos = "
                    + "GREATEST(0, "
                    + "quantidade_emprestimos - 1), "
                    + "data_ultima_atualizacao = CURRENT_DATE "
                    + "WHERE usuario_id = ?";

            try (PreparedStatement comando =
                    conexao.prepareStatement(sqlCarteira)) {

                comando.setLong(1, usuarioId);
                comando.executeUpdate();
            }

            atualizarBloqueios(conexao);

            conexao.commit();

        } catch (SQLException | RuntimeException erro) {

            if (conexao != null) {
                try {
                    conexao.rollback();
                } catch (SQLException erroRollback) {
                    erro.addSuppressed(erroRollback);
                }
            }

            throw erro;

        } finally {

            if (conexao != null) {
                try {
                    conexao.setAutoCommit(true);
                    conexao.close();
                } catch (SQLException erroFechamento) {
                    // A operação principal já foi finalizada.
                }
            }
        }
    }

    public List<Emprestimo> listarTodos()
            throws SQLException {

        String sql = consultaCompleta()
                + " ORDER BY e.id DESC";

        List<Emprestimo> emprestimos =
                new ArrayList<>();

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando =
                    conexao.prepareStatement(sql);
            ResultSet resultado = comando.executeQuery()
        ) {
            while (resultado.next()) {
                emprestimos.add(
                        criarEmprestimo(resultado)
                );
            }
        }

        return emprestimos;
    }

    public List<Emprestimo> listarPorUsuario(
            Long usuarioId) throws SQLException {

        String sql = consultaCompleta()
                + " WHERE e.usuario_id = ? "
                + "ORDER BY e.id DESC";

        List<Emprestimo> emprestimos =
                new ArrayList<>();

        try (
            Connection conexao = Conexao.conectar();
            PreparedStatement comando =
                    conexao.prepareStatement(sql)
        ) {
            comando.setLong(1, usuarioId);

            try (ResultSet resultado =
                    comando.executeQuery()) {

                while (resultado.next()) {
                    emprestimos.add(
                            criarEmprestimo(resultado)
                    );
                }
            }
        }

        return emprestimos;
    }

    public void atualizarBloqueios()
            throws SQLException {

        try (Connection conexao = Conexao.conectar()) {
            atualizarBloqueios(conexao);
        }
    }

    private void atualizarBloqueios(Connection conexao)
            throws SQLException {

        String sql =
                "{CALL atualizar_bloqueios_por_atraso()}";

        try (CallableStatement comando =
                conexao.prepareCall(sql)) {

            comando.execute();
        }
    }

    private String consultaCompleta() {

        return "SELECT "
                + "e.id AS emprestimo_id, "
                + "e.data_emprestimo, "
                + "e.data_prevista_devolucao, "
                + "e.data_devolucao, "
                + "e.status AS emprestimo_status, "
                + "e.observacao, "
                + "u.id AS usuario_id, "
                + "u.nome AS usuario_nome, "
                + "u.status AS usuario_status, "
                + "l.id AS livro_id, "
                + "l.titulo AS livro_titulo, "
                + "l.isbn AS livro_isbn, "
                + "l.quantidade_disponivel "
                + "FROM emprestimo e "
                + "INNER JOIN usuario u "
                + "ON u.id = e.usuario_id "
                + "INNER JOIN livro l "
                + "ON l.id = e.livro_id";
    }

    private Emprestimo criarEmprestimo(
            ResultSet resultado) throws SQLException {

        Emprestimo emprestimo = new Emprestimo();

        emprestimo.setId(
                resultado.getLong("emprestimo_id")
        );

        emprestimo.setDataEmprestimo(
                resultado.getDate(
                        "data_emprestimo"
                ).toLocalDate()
        );

        emprestimo.setDataPrevistaDevolucao(
                resultado.getDate(
                        "data_prevista_devolucao"
                ).toLocalDate()
        );

        Date dataDevolucao =
                resultado.getDate("data_devolucao");

        if (dataDevolucao != null) {
            emprestimo.setDataDevolucao(
                    dataDevolucao.toLocalDate()
            );
        }

        emprestimo.setStatus(
                resultado.getString(
                        "emprestimo_status"
                )
        );

        emprestimo.setObservacao(
                resultado.getString("observacao")
        );

        Usuario usuario = new Usuario();

        usuario.setId(
                resultado.getLong("usuario_id")
        );

        usuario.setNome(
                resultado.getString("usuario_nome")
        );

        usuario.setStatus(
                resultado.getString("usuario_status")
        );

        emprestimo.setUsuario(usuario);

        Livro livro = new Livro();

        livro.setId(
                resultado.getLong("livro_id")
        );

        livro.setTitulo(
                resultado.getString("livro_titulo")
        );

        livro.setIsbn(
                resultado.getString("livro_isbn")
        );

        livro.setQuantidadeDisponivel(
                resultado.getInt(
                        "quantidade_disponivel"
                )
        );

        emprestimo.setLivro(livro);

        return emprestimo;
    }
}