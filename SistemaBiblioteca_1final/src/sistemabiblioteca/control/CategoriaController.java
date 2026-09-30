/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.control;

import sistemabiblioteca.dao.CategoriaDAO;
import sistemabiblioteca.model.Categoria;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class CategoriaController {

    private final CategoriaDAO categoriaDAO;

    public CategoriaController() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public Categoria cadastrar(Categoria categoria)
            throws SQLException {

        validarCategoria(categoria);

        if (categoriaDAO.buscarPorNome(
                categoria.getNome()) != null) {

            throw new IllegalArgumentException(
                    "Já existe uma categoria com esse nome."
            );
        }

        if (categoriaDAO.buscarPorCodigo(
                categoria.getCodigo()) != null) {

            throw new IllegalArgumentException(
                    "Já existe uma categoria com esse código."
            );
        }

        if (categoria.getDataCadastro() == null) {
            categoria.setDataCadastro(LocalDate.now());
        }

        if (categoria.getStatus() == null
                || categoria.getStatus().isBlank()) {
            categoria.setStatus("ATIVA");
        }

        return categoriaDAO.cadastrar(categoria);
    }

    public Categoria buscarPorId(Long id)
            throws SQLException {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException(
                    "O ID da categoria é inválido."
            );
        }

        return categoriaDAO.buscarPorId(id);
    }

    public List<Categoria> listarTodas()
            throws SQLException {

        return categoriaDAO.listarTodas();
    }

    public void atualizar(Categoria categoria)
            throws SQLException {

        if (categoria == null
                || categoria.getId() == null) {

            throw new IllegalArgumentException(
                    "A categoria precisa possuir um ID."
            );
        }

        validarCategoria(categoria);
        categoriaDAO.atualizar(categoria);
    }

    public void alterarStatus(Long id, String status)
            throws SQLException {

        Set<String> statusPermitidos =
                Set.of("ATIVA", "INATIVA");

        if (status == null
                || !statusPermitidos.contains(
                        status.toUpperCase())) {

            throw new IllegalArgumentException(
                    "Status de categoria inválido."
            );
        }

        categoriaDAO.alterarStatus(
                id,
                status.toUpperCase()
        );
    }

    private void validarCategoria(Categoria categoria) {

        if (categoria == null) {
            throw new IllegalArgumentException(
                    "A categoria não pode ser nula."
            );
        }

        if (categoria.getNome() == null
                || categoria.getNome().isBlank()) {

            throw new IllegalArgumentException(
                    "O nome da categoria é obrigatório."
            );
        }

        if (categoria.getCodigo() == null
                || categoria.getCodigo().isBlank()) {

            throw new IllegalArgumentException(
                    "O código da categoria é obrigatório."
            );
        }
    }
}