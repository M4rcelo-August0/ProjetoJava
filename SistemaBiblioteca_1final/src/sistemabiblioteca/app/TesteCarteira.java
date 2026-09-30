/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.app;

import sistemabiblioteca.control.CarteiraBibliotecaController;
import sistemabiblioteca.control.UsuarioController;
import sistemabiblioteca.model.CarteiraBiblioteca;
import sistemabiblioteca.model.Usuario;

import java.util.List;

public class TesteCarteira {

    public static void main(String[] args) {

        try {
            UsuarioController usuarioController =
                    new UsuarioController();

            List<Usuario> usuarios =
                    usuarioController.listarTodos();

            if (usuarios.isEmpty()) {
                System.out.println(
                        "Cadastre um usuário antes da carteira."
                );
                return;
            }

            Usuario usuario = usuarios.get(0);

            long numeroUnico = System.currentTimeMillis();

            CarteiraBiblioteca carteira =
                    new CarteiraBiblioteca();

            carteira.setNumero(
                    "CAR-" + numeroUnico
            );

            carteira.setCodigoCarteira(
                    "COD-" + numeroUnico
            );

            carteira.setLimiteEmprestimos(3);
            carteira.setObservacao(
                    "Carteira criada pelo teste do sistema."
            );
            carteira.setUsuario(usuario);

            CarteiraBibliotecaController controller =
                    new CarteiraBibliotecaController();

            controller.cadastrar(carteira);

            System.out.println(
                    "Carteira cadastrada com sucesso!"
            );

            System.out.println(
                    "ID da carteira: " + carteira.getId()
            );

            System.out.println(
                    "Usuário: " + usuario.getNome()
            );

            System.out.println(
                    "Status: " + carteira.getStatus()
            );

            System.out.println(
                    "Limite: "
                    + carteira.getLimiteEmprestimos()
            );

        } catch (Exception erro) {
            System.out.println(
                    "Erro ao cadastrar carteira: "
                    + erro.getMessage()
            );

            erro.printStackTrace();
        }
    }
}