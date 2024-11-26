package domain.view;

import domain.dao.CursoDAO;
import domain.dao.ProfessorDAO;
import domain.model.Curso;
import domain.model.Professor;
import domain.util.RelatorioService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class TelaCurso extends JFrame {
    private JTextField nomeCursoField;
    private JTextField cargaHorariaField;
    private JTextField idProfessorField;
    private JButton cadastrarButton;
    private JButton listarButton;
    private JButton excluirButton;
    private JButton voltarButton;
    private JTextArea outputArea;

    public TelaCurso() {
        RelatorioService relatorioService = new RelatorioService();
        setTitle("Gerenciamento de Cursos");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel nomeCursoLabel = new JLabel("Nome do Curso:");
        nomeCursoLabel.setBounds(10, 10, 120, 25);
        add(nomeCursoLabel);

        nomeCursoField = new JTextField();
        nomeCursoField.setBounds(140, 10, 200, 25);
        add(nomeCursoField);

        JLabel cargaHorariaLabel = new JLabel("Carga Horária:");
        cargaHorariaLabel.setBounds(10, 50, 120, 25);
        add(cargaHorariaLabel);

        cargaHorariaField = new JTextField();
        cargaHorariaField.setBounds(140, 50, 200, 25);
        add(cargaHorariaField);

        JLabel idProfessorLabel = new JLabel("ID do Professor:");
        idProfessorLabel.setBounds(10, 90, 120, 25);
        add(idProfessorLabel);

        idProfessorField = new JTextField();
        idProfessorField.setBounds(140, 90, 200, 25);
        add(idProfessorField);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(10, 130, 200, 25);
        add(cadastrarButton);

        listarButton = new JButton("Listar");
        listarButton.setBounds(180, 130, 200, 25);
        add(listarButton);

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
                    String nomeCurso = nomeCursoField.getText();
                    int cargaHoraria = Integer.parseInt(cargaHorariaField.getText());
                    Long idProfessor = Long.parseLong(idProfessorField.getText());
                    Professor professor = new ProfessorDAO().buscarPorId(idProfessor); // Busca o professor por ID
                    Curso curso = new Curso(0, nomeCurso, cargaHoraria, professor.getId(), professor.getNome());
                    CursoDAO dao = new CursoDAO(relatorioService);
                    dao.cadastrar(curso);
                    outputArea.setText("Curso cadastrado com sucesso!");
                } catch (Exception ex) {
                    outputArea.setText("Erro ao cadastrar curso: " + ex.getMessage());
                }
            }
        });

        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CursoDAO dao = new CursoDAO(relatorioService);
                    List<Curso> cursos = dao.listarTodos();
                    StringBuilder builder = new StringBuilder();
                    for (Curso curso : cursos) {
                        builder.append(curso).append("\n");
                    }
                    outputArea.setText(builder.toString());
                } catch (Exception ex) {
                    outputArea.setText("Erro ao listar cursos: " + ex.getMessage());
                }
            }
        });

        excluirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do curso a excluir:"));

                    CursoDAO dao = new CursoDAO(relatorioService);
                    dao.excluir(id);
                    outputArea.setText("Curso excluído com sucesso!");
                } catch (Exception ex) {
                    outputArea.setText("Erro ao excluir curso: " + ex.getMessage());
                }
            }
        });

        voltarButton.addActionListener(e -> setVisible(false));

        setVisible(true);
    }
}
