package algoritmo;

public abstract class ProgramaLadrao implements Programa {
    public SensoresLadrao sensor = new SensoresLadrao();

    public ProgramaLadrao() {
    }

    public abstract int acao();
}
