/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Categoria {

    private Long id;
    private String nome;
    private String descricao;
    private String codigo;
    private LocalDate dataCadastro;
    private String status;
    private String faixaEtaria;
    private String genero;
    private String observacao;

    // Relacionamento: uma categoria possui vários livros
    private List<Livro> livros;

    public Categoria() {
        this.livros = new ArrayList<>();
    }

    public Categoria(Long id, String nome, String descricao,
            String codigo, LocalDate dataCadastro, String status,
            String faixaEtaria, String genero, String observacao) {

        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.codigo = codigo;
        this.dataCadastro = dataCadastro;
        this.status = status;
        this.faixaEtaria = faixaEtaria;
        this.genero = genero;
        this.observacao = observacao;
        this.livros = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) {
        if (livro != null && !livros.contains(livro)) {
            livros.add(livro);
            livro.setCategoria(this);
        }
    }

    public void removerLivro(Livro livro) {
        if (livro != null && livros.remove(livro)) {
            livro.setCategoria(null);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}
