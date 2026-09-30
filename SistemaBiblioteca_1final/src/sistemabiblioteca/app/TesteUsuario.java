/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.app;

import sistemabiblioteca.control.UsuarioController;
import sistemabiblioteca.model.Usuario;

import java.time.LocalDate;

public class TesteUsuario {

    public static void main(String[] args) {

        try {
            Usuario usuario = new Usuario();

            usuario.setNome("João da Silva");
            usuario.setEmail("joao@email.com");
            usuario.setCpf("123.456.789-00");
            usuario.setTelefone("(11) 99999-9999");
            usuario.setEndereco("Rua das Flores, 100");
            usuario.setDataNascimento(
                    LocalDate.of(2000, 5, 20)
            );
            usuario.setSenha("123456");

            UsuarioController controller =
                    new UsuarioController();

            controller.cadastrar(usuario);

            System.out.println("Usuário cadastrado com sucesso!");
            System.out.println("ID: " + usuario.getId());
            System.out.println("Nome: " + usuario.getNome());
            System.out.println("Status: " + usuario.getStatus());

        } catch (Exception erro) {
            System.out.println(
                    "Erro ao cadastrar usuário: "
                    + erro.getMessage()
            );
            erro.printStackTrace();
        }
    }
}