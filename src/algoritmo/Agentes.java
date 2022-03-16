package algoritmo;

public class Agentes {
    private Arquitetura arquitetura;
    private Programa programa;

    public Agentes(Arquitetura arquitetura, Programa programa) {
        this.arquitetura = arquitetura;
        this.programa = programa;
    }

    public Arquitetura getArquitetura() {
        return this.arquitetura;
    }

    public void setArquitetura(Arquitetura arquitetura) {
        this.arquitetura = arquitetura;
    }

    public Programa getPrograma() {
        return this.programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
    }
}