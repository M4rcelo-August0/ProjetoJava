/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.app;

import sistemabiblioteca.util.Conexao;
import java.sql.Connection;
import java.sql.SQLException;

public class TesteConexao {

    public static void main(String[] args) {

        try (Connection conexao = Conexao.conectar()) {

            System.out.println(
                    "Conexão realizada com sucesso!"
            );

            System.out.println(
                    "Banco conectado: "
                    + conexao.getCatalog()
            );

        } catch (SQLException erro) {

            System.out.println(
                    "Erro ao conectar com o banco."
            );

            erro.printStackTrace();
        }
    }
}