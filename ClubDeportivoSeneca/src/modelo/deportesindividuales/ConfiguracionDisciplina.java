package modelo.deportesindividuales;

import java.io.Serializable;

import modelo.comun.Disciplina;

public class ConfiguracionDisciplina implements Serializable {

    private Disciplina disciplina;
    private int maxInstalacionesSimultaneas;

    public ConfiguracionDisciplina(Disciplina disciplina, int maxInstalacionesSimultaneas) {
        this.disciplina = disciplina;
        this.maxInstalacionesSimultaneas = maxInstalacionesSimultaneas;
    }

    public boolean hayCupo(int enUso) {
        return enUso < maxInstalacionesSimultaneas;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public int getMaxInstalacionesSimultaneas() {
        return maxInstalacionesSimultaneas;
    }
}
