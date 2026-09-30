/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.model;

import java.time.LocalDate;

public class CarteiraBiblioteca {

    private Long id;
    private String numero;
    private LocalDate dataCriacao;
    private String status;
    private int limiteEmprestimos;
    private int quantidadeEmprestimos;
    private LocalDate dataUltimaAtualizacao;
    private String observacao;
    private Usuario usuario;
    private String codigoCarteira;

    public CarteiraBiblioteca() {
    }

    public CarteiraBiblioteca(Long id, String numero,
            LocalDate dataCriacao, String status,
            int limiteEmprestimos, int quantidadeEmprestimos,
            LocalDate dataUltimaAtualizacao, String observacao,
            Usuario usuario, String codigoCarteira) {

        this.id = id;
        this.numero = numero;
        this.dataCriacao = dataCriacao;
        this.status = status;
        this.limiteEmprestimos = limiteEmprestimos;
        this.quantidadeEmprestimos = quantidadeEmprestimos;
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
        this.observacao = observacao;
        this.usuario = usuario;
        this.codigoCarteira = codigoCarteira;
    }

    public void ativar() {
        this.status = "ATIVA";
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public void desativar() {
        this.status = "INATIVA";
        this.dataUltimaAtualizacao = LocalDate.now();
    }

    public boolean verificarAtiva() {
        return "ATIVA".equalsIgnoreCase(status);
    }

    public boolean atingiuLimiteEmprestimos() {
        return quantidadeEmprestimos >= limiteEmprestimos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getLimiteEmprestimos() {
        return limiteEmprestimos;
    }

    public void setLimiteEmprestimos(int limiteEmprestimos) {
        this.limiteEmprestimos = limiteEmprestimos;
    }

    public int getQuantidadeEmprestimos() {
        return quantidadeEmprestimos;
    }

    public void setQuantidadeEmprestimos(int quantidadeEmprestimos) {
        this.quantidadeEmprestimos = quantidadeEmprestimos;
    }

    public LocalDate getDataUltimaAtualizacao() {
        return dataUltimaAtualizacao;
    }

    public void setDataUltimaAtualizacao(
            LocalDate dataUltimaAtualizacao) {
        this.dataUltimaAtualizacao = dataUltimaAtualizacao;
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

    public String getCodigoCarteira() {
        return codigoCarteira;
    }

    public void setCodigoCarteira(String codigoCarteira) {
        this.codigoCarteira = codigoCarteira;
    }
}