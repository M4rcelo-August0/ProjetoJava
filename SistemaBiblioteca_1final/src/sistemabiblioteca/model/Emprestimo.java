/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.model;

import java.time.LocalDate;

public class Emprestimo {

    private Long id;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevistaDevolucao;
    private LocalDate dataDevolucao;
    private String status;
    private String observacao;
    private Usuario usuario;
    private Livro livro;

    public Emprestimo() {
    }

    public Emprestimo(Long id, LocalDate dataEmprestimo,
            LocalDate dataPrevistaDevolucao,
            LocalDate dataDevolucao, String status,
            String observacao, Usuario usuario, Livro livro) {

        this.id = id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.dataDevolucao = dataDevolucao;
        this.status = status;
        this.observacao = observacao;
        this.usuario = usuario;
        this.livro = livro;
    }

    public void realizar() {

        if (usuario == null) {
            throw new IllegalStateException(
                    "O empréstimo precisa de um usuário."
            );
        }

        if (usuario.estaBloqueado()) {
            throw new IllegalStateException(
                    "O usuário está bloqueado por empréstimo atrasado."
            );
        }

        if (livro == null) {
            throw new IllegalStateException(
                    "O empréstimo precisa de um livro."
            );
        }

        livro.emprestar();

        this.dataEmprestimo = LocalDate.now();
        this.status = "ATIVO";

        usuario.adicionarEmprestimo(this);
    }

    public void devolver() {

        if ("DEVOLVIDO".equalsIgnoreCase(status)) {
            throw new IllegalStateException(
                    "Este empréstimo já foi devolvido."
            );
        }

        this.dataDevolucao = LocalDate.now();
        this.status = "DEVOLVIDO";

        if (livro != null) {
            livro.devolver();
        }
    }

    public boolean estaAtrasado() {

        return dataDevolucao == null
                && dataPrevistaDevolucao != null
                && dataPrevistaDevolucao.isBefore(LocalDate.now());
    }

    public void atualizarStatusPorAtraso() {

        if (estaAtrasado()) {
            this.status = "ATRASADO";

            if (usuario != null
                    && !"INATIVO".equalsIgnoreCase(
                            usuario.getStatus())) {
                usuario.setStatus("BLOQUEADO");
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(LocalDate dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public void setDataPrevistaDevolucao(
            LocalDate dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }
}