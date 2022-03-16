package algoritmo;

public abstract class ProgramaPoupador implements Programa {
    public SensoresPoupador sensor = new SensoresPoupador();

    public ProgramaPoupador() {
    }

    public abstract int acao();
}
