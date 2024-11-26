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
    private JButton consultarButton;
    private JTextArea outputArea;

    public TelaEstudante() {
        setTitle("Gerenciamento de Estudantes");
        setSize(750, 600);
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
        matriculaField.setBounds(120, 90, 150, 25);
        add(matriculaField);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(10, 130, 150, 25);
        add(cadastrarButton);

        listarButton = new JButton("Listar");
        listarButton.setBounds(180, 130, 150, 25);
        add(listarButton);

        gerarRelatorioButton = new JButton("Gerar Relatório");
        gerarRelatorioButton.setBounds(510, 130, 150, 25);
        add(gerarRelatorioButton);

        JButton atualizarButton = new JButton("Atualizar");
        atualizarButton.setBounds(350, 170, 150, 25);
        add(atualizarButton);

        excluirButton = new JButton("Excluir");
        excluirButton.setBounds(10, 170, 150, 25);
        add(excluirButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setBounds(180, 170, 150, 25);
        add(voltarButton);

        consultarButton = new JButton("Consultar");
        consultarButton.setBounds(350, 130, 150, 25);
        add(consultarButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 210, 550, 300);
        outputArea.setEditable(false);
        add(outputArea);

        cadastrarButton.addActionListener(e -> {
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
        });

        listarButton.addActionListener(e -> {
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
        });

        gerarRelatorioButton.addActionListener(e -> {
            try {
                EstudanteDAO dao = new EstudanteDAO();
                String relatorio = dao.gerarRelatorio(); // Chama a função existente
                outputArea.setText(relatorio);
            } catch (Exception ex) {
                outputArea.setText("Erro ao gerar relatório: " + ex.getMessage());
            }
        });

        excluirButton.addActionListener(e -> {
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
        });

        atualizarButton.addActionListener(e -> {
            try {
                // Solicita a matrícula do estudante que será atualizado
                String matriculaText = JOptionPane.showInputDialog("Digite a matrícula do estudante a atualizar:");
                if (matriculaText == null || matriculaText.isEmpty()) {
                    throw new IllegalArgumentException("Matrícula não informada.");
                }
                long matricula = Long.parseLong(matriculaText);

                // Solicita os novos dados para o estudante
                String novoNome = JOptionPane.showInputDialog("Digite o novo nome do estudante:");
                if (novoNome == null || novoNome.isEmpty()) {
                    throw new IllegalArgumentException("Nome não informado.");
                }

                String novaIdadeText = JOptionPane.showInputDialog("Digite a nova idade do estudante:");
                if (novaIdadeText == null || novaIdadeText.isEmpty()) {
                    throw new IllegalArgumentException("Idade não informada.");
                }
                int novaIdade = Integer.parseInt(novaIdadeText);

                // Cria o objeto estudante com os dados atualizados
                Estudante estudante = new Estudante(novoNome, novaIdade, matricula);

                // Atualiza no banco de dados
                EstudanteDAO dao = new EstudanteDAO();
                dao.atualizar(estudante);

                outputArea.setText("Estudante atualizado com sucesso!");
            } catch (NumberFormatException ex) {
                outputArea.setText("Erro: Matrícula e idade devem ser numéricas.");
            } catch (IllegalArgumentException ex) {
                outputArea.setText("Erro: " + ex.getMessage());
            } catch (Exception ex) {
                outputArea.setText("Erro ao atualizar estudante: " + ex.getMessage());
            }
        });

        consultarButton.addActionListener(e -> {
            try {
                // Buscar e printar informações do estudante
                String matriculaText = JOptionPane.showInputDialog("Digite a matrícula do estudante a consultar:");
                long matricula = Long.parseLong(matriculaText);
                EstudanteDAO estudanteDAO = new EstudanteDAO();
                Estudante estudante = estudanteDAO.consultarEstudante(matricula);

                String mensagem = String.format(
                        "Estudante encontrado:\nNome: %s\nIdade: %d\nMatrícula: %d",
                        estudante.getNome(),
                        estudante.getIdade(),
                        estudante.getMatricula()
                );
                outputArea.setText(mensagem);

            } catch (Exception ex) {
                outputArea.setText("Erro ao consultar estudante: " + ex.getMessage());
            }
        });

        voltarButton.addActionListener(e -> setVisible(false));

        setVisible(true);
    }
}
