/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.control;

import sistemabiblioteca.dao.CarteiraBibliotecaDAO;
import sistemabiblioteca.model.CarteiraBiblioteca;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class CarteiraBibliotecaController {

    private final CarteiraBibliotecaDAO carteiraDAO;

    public CarteiraBibliotecaController() {
        this.carteiraDAO =
                new CarteiraBibliotecaDAO();
    }

    public CarteiraBiblioteca cadastrar(
            CarteiraBiblioteca carteira) throws SQLException {

        validarCarteira(carteira);

        CarteiraBiblioteca carteiraExistente =
                carteiraDAO.buscarPorUsuario(
                        carteira.getUsuario().getId()
                );

        if (carteiraExistente != null) {
            throw new IllegalArgumentException(
                    "Este usuário já possui uma carteira."
            );
        }

        if (carteira.getDataCriacao() == null) {
            carteira.setDataCriacao(LocalDate.now());
        }

        if (carteira.getStatus() == null
                || carteira.getStatus().isBlank()) {
            carteira.setStatus("ATIVA");
        }

        if (carteira.getLimiteEmprestimos() <= 0) {
            carteira.setLimiteEmprestimos(3);
        }

        carteira.setQuantidadeEmprestimos(0);
        carteira.setDataUltimaAtualizacao(LocalDate.now());

        return carteiraDAO.cadastrar(carteira);
    }

    public CarteiraBiblioteca buscarPorId(Long id)
            throws SQLException {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da carteira é inválido."
            );
        }

        return carteiraDAO.buscarPorId(id);
    }

    public CarteiraBiblioteca buscarPorUsuario(
            Long usuarioId) throws SQLException {

        if (usuarioId == null || usuarioId <= 0) {
            throw new IllegalArgumentException(
                    "O ID do usuário é inválido."
            );
        }

        return carteiraDAO.buscarPorUsuario(usuarioId);
    }

    public List<CarteiraBiblioteca> listarTodas()
            throws SQLException {

        return carteiraDAO.listarTodas();
    }

    public void atualizar(CarteiraBiblioteca carteira)
            throws SQLException {

        if (carteira == null || carteira.getId() == null) {
            throw new IllegalArgumentException(
                    "A carteira precisa possuir um ID."
            );
        }

        validarCarteira(carteira);
        carteira.setDataUltimaAtualizacao(LocalDate.now());

        carteiraDAO.atualizar(carteira);
    }

    public void alterarStatus(Long id, String status)
            throws SQLException {

        Set<String> statusPermitidos = Set.of(
                "ATIVA",
                "INATIVA",
                "BLOQUEADA"
        );

        if (status == null
                || !statusPermitidos.contains(
                        status.toUpperCase())) {

            throw new IllegalArgumentException(
                    "Status de carteira inválido."
            );
        }

        carteiraDAO.alterarStatus(
                id,
                status.toUpperCase()
        );
    }

    private void validarCarteira(
            CarteiraBiblioteca carteira) {

        if (carteira == null) {
            throw new IllegalArgumentException(
                    "A carteira não pode ser nula."
            );
        }

        if (carteira.getNumero() == null
                || carteira.getNumero().isBlank()) {
            throw new IllegalArgumentException(
                    "O número da carteira é obrigatório."
            );
        }

        if (carteira.getCodigoCarteira() == null
                || carteira.getCodigoCarteira().isBlank()) {
            throw new IllegalArgumentException(
                    "O código da carteira é obrigatório."
            );
        }

        if (carteira.getUsuario() == null
                || carteira.getUsuario().getId() == null) {
            throw new IllegalArgumentException(
                    "A carteira precisa de um usuário."
            );
        }
    }
}