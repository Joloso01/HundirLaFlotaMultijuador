package com.company.Game;

import java.io.Serializable;

public class Respuesta implements Serializable {
    public static final long serialVersionUID = 2L;
    String[][] respuesta_Tablero;
    String impacto;

    public String[][] getRespuesta_Tablero() {
        return respuesta_Tablero;
    }

    public void setRespuesta_Tablero(String[][] respuesta_Tablero) {
        this.respuesta_Tablero = respuesta_Tablero;
    }

    public String getImpacto() {
        return impacto;
    }

    public void setImpacto(String impacto) {
        this.impacto = impacto;
    }
}
