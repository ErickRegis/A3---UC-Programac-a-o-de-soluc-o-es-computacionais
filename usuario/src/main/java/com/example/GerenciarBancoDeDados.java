package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GerenciarBancoDeDados {

    private static final String URL = "jdbc:postgresql://localhost:5432/aula_java";
    private static final String USER = "postgres";
    private static final String PASSWORD = "733520";

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        while (true) {
            System.out.println("\nEscolha uma opção:");
            System.out.println("1. Inserir Dados");
            System.out.println("2. Atualizar Dados");
            System.out.println("3. Apagar Dados");
            System.out.println("4. Listar Dados");
            System.out.println("0. Sair");
            int opcao = teclado.nextInt();
            teclado.nextLine(); // Limpa o buffer de entrada

            switch (opcao) {
                case 1:
                    inserirDados(teclado);
                    break;
                case 2:
                    atualizarDados(teclado);
                    break;
                case 3:
                    apagarDados(teclado);
                    break;
                case 4:
                    listarDados();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    teclado.close();
                    return;
                default:
                    System.out.println("Opção inválida, tente novamente.");
            }
        }
    }

    private static void inserirDados(Scanner teclado) {
        System.out.println("Qual nome quer inserir:");
        String nome = teclado.nextLine();

        System.out.println("Qual senha quer inserir:");
        String senha = teclado.nextLine();

        System.out.println("Qual email quer inserir:");
        String email = teclado.nextLine();

        String sql = "INSERT INTO usuario (nome, senha, email) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("Conexão bem-sucedida ao PostgreSQL!");

            pstmt.setString(1, nome);
            pstmt.setString(2, senha);
            pstmt.setString(3, email);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou executar a operação no PostgreSQL: " + e.getMessage());
        }
    }

    private static void atualizarDados(Scanner teclado) {
        System.out.println("Qual é o ID do registro que deseja atualizar?");
        while (!teclado.hasNextInt()) {
            System.out.println("Por favor, insira um número inteiro válido.");
            teclado.next();
        }
        int id = teclado.nextInt();
        teclado.nextLine();

        System.out.println("Novo nome:");
        String novoNome = teclado.nextLine();

        System.out.println("Nova senha:");
        String novaSenha = teclado.nextLine();

        System.out.println("Novo email:");
        String novoEmail = teclado.nextLine();

        String sql = "UPDATE usuario SET nome = ?, senha = ?, email = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("Conexão bem-sucedida ao PostgreSQL!");

            pstmt.setString(1, novoNome);
            pstmt.setString(2, novaSenha);
            pstmt.setString(3, novoEmail);
            pstmt.setInt(4, id);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou executar a operação no PostgreSQL: " + e.getMessage());
        }
    }

    private static void apagarDados(Scanner teclado) {
        System.out.println("Qual é o ID do registro que deseja apagar?");
        while (!teclado.hasNextInt()) {
            System.out.println("Por favor, insira um número inteiro válido.");
            teclado.next();
        }
        int id = teclado.nextInt();
        teclado.nextLine();

        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("Conexão bem-sucedida ao PostgreSQL!");

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Linhas afetadas: " + rowsAffected);

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou executar a operação no PostgreSQL: " + e.getMessage());
        }
    }

    private static void listarDados() {
        String sql = "SELECT * FROM usuario";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Conexão bem-sucedida ao PostgreSQL!");

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String senha = rs.getString("senha");
                String email = rs.getString("email");

                System.out.println("ID: " + id + ", Nome: " + nome + ", Senha: " + senha + ", Email: " + email);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao conectar ou executar a operação no PostgreSQL: " + e.getMessage());
        }
    }
}
