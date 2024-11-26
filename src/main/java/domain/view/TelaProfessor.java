package domain.view;

import domain.dao.ProfessorDAO;
import domain.model.Professor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaProfessor extends JFrame {
    private JTextField nomeField;
    private JTextField idadeField;
    private JTextField especialidadeField;
    private JTextField idField;
    private JButton cadastrarButton;
    private JButton listarButton;
    private JButton excluirButton;
    private JButton gerarRelatorioButton;
    private JButton voltarButton;
    private JTextArea outputArea;

    public TelaProfessor() {
        setTitle("Gerenciamento de Professores");
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

        JLabel especialidadeLabel = new JLabel("Especialidade:");
        especialidadeLabel.setBounds(10, 90, 100, 25);
        add(especialidadeLabel);

        especialidadeField = new JTextField();
        especialidadeField.setBounds(120, 90, 200, 25);
        add(especialidadeField);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(10, 130, 100, 25);
        add(idLabel);

        idField = new JTextField();
        idField.setBounds(120, 130, 200,25);
        add(idField);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(10, 170, 200, 25);
        add(cadastrarButton);

        listarButton = new JButton("Listar");
        listarButton.setBounds(180, 170, 200, 25);
        add(listarButton);

        excluirButton = new JButton("Excluir");
        excluirButton.setBounds(10, 210, 200, 25);
        add(excluirButton);

        gerarRelatorioButton = new JButton("Gerar Relatório");
        gerarRelatorioButton.setBounds(180, 210, 200, 25);
        add(gerarRelatorioButton);

        voltarButton = new JButton("Voltar");
        voltarButton.setBounds(350, 170, 200, 25);
        add(voltarButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 250, 450, 160);
        outputArea.setEditable(false);
        add(outputArea);


        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeField.getText();
                    int idade = Integer.parseInt(idadeField.getText());
                    String especialidade = especialidadeField.getText();
                    long id = Long.parseLong(idField.getText());
                    Professor professor = new Professor(id, nome, idade, especialidade);
                    ProfessorDAO dao = new ProfessorDAO();
                    dao.cadastrar(professor);
                    outputArea.setText("Professor cadastrado com sucesso!");
                } catch (Exception ex) {
                    outputArea.setText("Erro ao cadastrar professor: " + ex.getMessage());
                }
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProfessorDAO dao = new ProfessorDAO();
                    List<Professor> professores = dao.listarTodos();
                    StringBuilder builder = new StringBuilder();
                    for (Professor professor : professores) {
                        builder.append(professor).append("\n");
                    }
                    outputArea.setText(builder.toString());
                } catch (Exception ex) {
                    outputArea.setText("Erro ao listar professores: " + ex.getMessage());
                }
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    long id = Long.parseLong(JOptionPane.showInputDialog("Digite o ID do professor a excluir:"));
                    ProfessorDAO dao = new ProfessorDAO();
                    dao.excluir(id);
                    outputArea.setText("Professor excluído com sucesso!");
                } catch (Exception ex) {
                    outputArea.setText("Erro ao excluir professor: " + ex.getMessage());
                }
            }
        });

        gerarRelatorioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ProfessorDAO dao = new ProfessorDAO();
                    dao.gerarRelatorio(); // Chama a função existente
                    outputArea.setText("Relatório gerado com sucesso!");
                } catch (Exception ex) {
                    outputArea.setText("Erro ao gerar relatório: " + ex.getMessage());
                }
            }
        });

        voltarButton.addActionListener(e -> setVisible(false));

        setVisible(true);
    }
}
