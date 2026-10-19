package modelo.comun;

public enum Categoria {
    SUB13(0, 13),
    SUB15(14, 15),
    SUB17(16, 17),
    MAYORES(18, 120);

    private final int edadMin;
    private final int edadMax;

    Categoria(int edadMin, int edadMax) {
        this.edadMin = edadMin;
        this.edadMax = edadMax;
    }

    public boolean corresponde(int edad) {
        return edad >= edadMin && edad <= edadMax;
    }

    public static Categoria porEdad(int edad) {
        for (Categoria c : values()) {
            if (c.corresponde(edad)) {
                return c;
            }
        }
        return null;
    }
}
