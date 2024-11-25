package domain.view;

import domain.dao.EstudanteDAO;
import domain.model.Estudante;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaEstudante extends JFrame {
    private JTextField nomeField;
    private JTextField idadeField;
    private JTextField matriculaField;
    private JButton cadastrarButton;
    private JButton listarButton;
    private JButton excluirButton;
    private JTextArea outputArea;

    public TelaEstudante() {
        setTitle("Gerenciamento de Estudantes");
        setSize(400, 400);
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
        cadastrarButton.setBounds(10, 130, 150, 25);
        add(cadastrarButton);

        listarButton = new JButton("Listar");
        listarButton.setBounds(180, 130, 150, 25);
        add(listarButton);

        excluirButton = new JButton("Excluir");
        excluirButton.setBounds(10, 170, 150, 25);
        add(excluirButton);

        outputArea = new JTextArea();
        outputArea.setBounds(10, 210, 350, 150);
        outputArea.setEditable(false);
        add(outputArea);

        JButton btnSair = new JButton("Sair");
        btnSair.setBounds(150, 200, 100, 40);
        add(btnSair);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = nomeField.getText();
                    int idade = Integer.parseInt(idadeField.getText());
                    Long matricula = Long.parseLong(matriculaField.getText());
                    Estudante estudante = new Estudante(nome, idade, matricula);
                    EstudanteDAO dao = new EstudanteDAO();
                    dao.cadastrar(estudante);
                    outputArea.setText("Estudante cadastrado com sucesso!");
                } catch (Exception ex) {
                    outputArea.setText("Erro ao cadastrar estudante: " + ex.getMessage());
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

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Long matricula = Long.parseLong(JOptionPane.showInputDialog("Digite a matrícula do estudante a excluir:"));
                    EstudanteDAO dao = new EstudanteDAO();
                    dao.excluir(matricula);
                    outputArea.setText("Estudante excluído com sucesso!");
                } catch (Exception ex) {
                    outputArea.setText("Erro ao excluir estudante: " + ex.getMessage());
                }
            }
        });

        btnSair.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
