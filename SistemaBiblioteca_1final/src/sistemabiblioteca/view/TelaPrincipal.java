/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistemabiblioteca.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        setTitle("Sistema de Gerenciamento de Biblioteca");
        setSize(720, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel titulo = new JLabel(
                "Sistema de Gerenciamento de Biblioteca",
                SwingConstants.CENTER
        );

        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setBorder(
                BorderFactory.createEmptyBorder(25, 10, 25, 10)
        );

        JPanel painelBotoes = new JPanel(
                new GridLayout(4, 2, 15, 15)
        );

        painelBotoes.setBorder(
                BorderFactory.createEmptyBorder(20, 40, 40, 40)
        );

        JButton btnUsuario =
                new JButton("Cadastrar usuário");

        JButton btnCarteira =
                new JButton("Cadastrar carteira");

        JButton btnCategoria =
                new JButton("Cadastrar categoria");

        JButton btnLivro =
                new JButton("Cadastrar livro");

        JButton btnEmprestimo =
                new JButton("Realizar empréstimo");

        JButton btnDevolucao =
                new JButton("Registrar devolução");

        JButton btnConsulta =
                new JButton("Consultar empréstimos");

        JButton btnSair = new JButton("Sair");

        btnUsuario.addActionListener(e ->
                new TelaCadastroUsuario().setVisible(true)
        );

        btnCarteira.addActionListener(e ->
                new TelaCadastroCarteira().setVisible(true)
        );

        btnCategoria.addActionListener(e ->
                new TelaCadastroCategoria().setVisible(true)
        );

        btnLivro.addActionListener(e ->
                new TelaCadastroLivro().setVisible(true)
        );

        btnEmprestimo.addActionListener(e ->
                new TelaEmprestimo().setVisible(true)
        );

        btnDevolucao.addActionListener(e ->
                new TelaDevolucao().setVisible(true)
        );

        btnConsulta.addActionListener(e ->
                new TelaConsultaEmprestimos().setVisible(true)
        );

        btnSair.addActionListener(e -> System.exit(0));

        painelBotoes.add(btnUsuario);
        painelBotoes.add(btnCarteira);
        painelBotoes.add(btnCategoria);
        painelBotoes.add(btnLivro);
        painelBotoes.add(btnEmprestimo);
        painelBotoes.add(btnDevolucao);
        painelBotoes.add(btnConsulta);
        painelBotoes.add(btnSair);

        add(titulo, BorderLayout.NORTH);
        add(painelBotoes, BorderLayout.CENTER);
    }
}