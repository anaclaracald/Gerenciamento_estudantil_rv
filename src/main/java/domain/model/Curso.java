package domain.model;

public class Curso {
    private long id;
    private String nomeCurso;
    private int cargaHoraria;
    private String professor; // Representa apenas o nome do professor

    public Curso(long id, String nomeCurso, int cargaHoraria, String professor) {
        this.id = id;
        this.nomeCurso = nomeCurso;
        this.cargaHoraria = cargaHoraria;
        this.professor = professor;
    }

    public long getId() {
        return id;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Curso{");
        sb.append("id=").append(id);
        sb.append(", nomeCurso='").append(nomeCurso).append('\'');
        sb.append(", cargaHoraria=").append(cargaHoraria);
        sb.append(", professor=").append(professor);
        sb.append('}');
        return sb.toString();
    }
}