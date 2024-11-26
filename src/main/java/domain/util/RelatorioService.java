package domain.util;

import domain.model.Curso;
import domain.model.Estudante;
import domain.model.Professor;

import java.util.*;

public class RelatorioService {
    private List<Estudante> estudantes;
    private List<Professor> professores;
    private List<Curso> cursos;
    private Map<Estudante, List<Curso>> matriculas;
    private Map<Professor, List<Curso>> associacoes;

    public RelatorioService() {
        this.estudantes = new ArrayList<>();
        this.professores = new ArrayList<>();
        this.cursos = new ArrayList<>();
        this.matriculas = new HashMap<>();
        this.associacoes = new HashMap<>();
    }

    public void adicionarEstudante(Estudante estudante) {
        if (estudante != null) {
            estudantes.add(estudante);
        }
    }

    public void adicionarProfessor(Professor professor) {
        if (professor != null) {
            professores.add(professor);
        }
    }

    public void adicionarCurso(Curso curso) {
        if (curso != null) {
            cursos.add(curso);
        }
    }

    public void matricularEstudante(Estudante estudante, Curso curso) {
        if (estudante != null && curso != null) {
            matriculas.putIfAbsent(estudante, new ArrayList<>());
            matriculas.get(estudante).add(curso);
        }
    }

    public void associarProfessor(Professor professor, Curso curso) {
        if (professor != null && curso != null) {
            associacoes.putIfAbsent(professor, new ArrayList<>());
            associacoes.get(professor).add(curso);
        }
    }

    public void gerarRelatorioEstudantes() {
        System.out.println("Relatório de Estudantes:");
        for (Estudante estudante : estudantes) {
            System.out.println("ID: " + estudante.getMatricula() +
                    ", Nome: " + estudante.getNome() +
                    ", Matriculado nos cursos: " +
                    (matriculas.containsKey(estudante) ? matriculas.get(estudante) : "Nenhum curso"));
        }
    }

    public void gerarRelatorioProfessores() {
        System.out.println("Relatório de Professores:");
        for (Professor professor : professores) {
            System.out.println("ID: " + professor.getId() +
                    ", Nome: " + professor.getNome() +
                    ", Associado aos cursos: " +
                    (associacoes.containsKey(professor) ? associacoes.get(professor) : "Nenhum curso"));
        }
    }

    public void gerarRelatorioCursos() {
        System.out.println("Relatório de Cursos:");
        for (Curso curso : cursos) {
            System.out.println("ID: " + curso.getId() +
                    ", Nome: " + curso.getNomeCurso() +
                    ", Carga Horária: " + curso.getCargaHoraria());
        }
    }

    public List<Estudante> getEstudantes() {
        return estudantes;
    }

    public void setEstudantes(List<Estudante> estudantes) {
        this.estudantes = estudantes;
    }

    public List<Professor> getProfessores() {
        return professores;
    }

    public void setProfessores(List<Professor> professores) {
        this.professores = professores;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Map<Estudante, List<Curso>> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(Map<Estudante, List<Curso>> matriculas) {
        this.matriculas = matriculas;
    }

    public Map<Professor, List<Curso>> getAssociacoes() {
        return associacoes;
    }

    public void setAssociacoes(Map<Professor, List<Curso>> associacoes) {
        this.associacoes = associacoes;
    }
}
