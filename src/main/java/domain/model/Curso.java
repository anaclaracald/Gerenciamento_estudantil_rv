package domain.model;

public class Curso {
    private Long id;
    private String nomeCurso;
    private int cargaHoraria;
    private Long professorId; // Armazena apenas o ID do professor
    private String professorNome; // Opcional, usado para exibir o nome

    public Curso(long id, String nomeCurso, int cargaHoraria, Long professorId, String professorNome) {
        this.id = id;
        this.nomeCurso = nomeCurso;
        this.cargaHoraria = cargaHoraria;
        this.professorId = professorId;
        this.professorNome = professorNome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public void setProfessorId(long professorId) {
        this.professorId = professorId;
    }

    public void setProfessorNome(String professorNome) {
        this.professorNome = professorNome;
    }

    public Long getId() {
        return id;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public String getProfessorNome() {
        return professorNome;
    }

    @Override
    public String toString() {
        return "Nome do curso: " + this.getNomeCurso() + ", Carga horária: " + this.getCargaHoraria() + ", Professor: " + this.getProfessorNome();
    }
}
