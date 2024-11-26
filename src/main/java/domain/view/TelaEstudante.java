package domain.view;

import domain.dao.EstudanteDAO;
import domain.dao.ProfessorDAO;
import domain.model.Estudante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TelaEstudante extends JFrame {
    private JTextField nomeField;
    private JTextField idadeField;
    private JTextField matriculaField;
    private JButton cadastrarButton;
    private JButton listarButton;
    private JButton gerarRelatorioButton;
    private JButton excluirButton;
    private JButton voltarButton;
    private JTextArea outputArea;

    public TelaEstudante() {
        setTitle("Gerenciamento de Estudantes");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setBounds(10, 10, 100, 25);
        add(nomeLabel);

        nomeField = new JTextField();
        nomeField.setBounds(120, 10, 200, 25);
        add(nomeField);

        JLabel idadeLabel = new JLabel("Idade:");
        idadeLabel.setBounds(10, 50, 100, 25);
        add(idadeLabel);

        idadeField = new JTextField();
        idadeField.setBounds(120, 50, 200, 25);
        add(idadeField);

        JLabel matriculaLabel = new JLabel("Matrícula:");
        matriculaLabel.setBounds(10, 90, 100, 25);
        add(matriculaLabel);

        matriculaField = new JTextField();
        matriculaField.setBounds(120, 90, 200, 25);
        add(matriculaField);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(10, 130, 200, 25);
        add(cadastrarButton);

        listarButton = new JButton("Listar");
        listarButton.setBounds(180, 130, 200, 25);
        add(listarButton);

        gerarRelatorioButton = new JButton("Gerar Relatório");
        gerarRelatorioButton.setBounds(180, 210, 200, 25);
        add(gerarRelatorioButton);

        excluirButton = new JButton("Excluir");
        excluirButton.setBounds(10, 170, 200, 25);
        add(excluirButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setBounds(180, 170, 200, 25);
        add(voltarButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 210, 450, 150);
        outputArea.setEditable(false);
        add(outputArea);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeField.getText();
                    String idadeText = idadeField.getText();
                    String matriculaText = matriculaField.getText();

                    if (nome.isEmpty() || idadeText.isEmpty() || matriculaText.isEmpty()) {
                        throw new IllegalArgumentException("Todos os campos devem ser preenchidos.");
                    }

                    int idade = Integer.parseInt(idadeText);
                    long matricula = Long.parseLong(matriculaText);

                    Estudante estudante = new Estudante(nome, idade, matricula);
                    EstudanteDAO dao = new EstudanteDAO();
                    dao.cadastrar(estudante);

                    outputArea.setText("Estudante cadastrado com sucesso!");
                } catch (NumberFormatException ex) {
                    outputArea.setText("Erro: Idade e Matrícula devem ser numéricos.");
                } catch (IllegalArgumentException ex) {
                    outputArea.setText("Erro: " + ex.getMessage());
                } catch (SQLException ex) {
                    outputArea.setText("Erro ao acessar o banco de dados: " + ex.getMessage());
                }
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EstudanteDAO dao = new EstudanteDAO();
                    List<Estudante> estudantes = dao.listarTodos();
                    StringBuilder builder = new StringBuilder();
                    for (Estudante estudante : estudantes) {
                        builder.append(estudante).append("\n");
                    }
                    outputArea.setText(builder.toString());
                } catch (Exception ex) {
                    outputArea.setText("Erro ao listar estudantes: " + ex.getMessage());
                }
            }
        });

        gerarRelatorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EstudanteDAO dao = new EstudanteDAO();
                    String relatorio = dao.gerarRelatorio(); // Chama a função existente
                    outputArea.setText(relatorio);
                } catch (Exception ex) {
                    outputArea.setText("Erro ao gerar relatório: " + ex.getMessage());
                }
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String matriculaText = JOptionPane.showInputDialog("Digite a matrícula do estudante a excluir:");
                    if (matriculaText == null || matriculaText.isEmpty()) {
                        throw new IllegalArgumentException("Matrícula não informada.");
                    }

                    long matricula = Long.parseLong(matriculaText);
                    EstudanteDAO dao = new EstudanteDAO();
                    dao.excluir(matricula);

                    outputArea.setText("Estudante excluído com sucesso!");
                } catch (NumberFormatException ex) {
                    outputArea.setText("Erro: Matrícula deve ser numérica.");
                } catch (IllegalArgumentException ex) {
                    outputArea.setText("Erro: " + ex.getMessage());
                } catch (SQLException ex) {
                    outputArea.setText("Erro ao acessar o banco de dados: " + ex.getMessage());
                }
            }
        });

        voltarButton.addActionListener(e -> setVisible(false));

        setVisible(true);
    }
}
