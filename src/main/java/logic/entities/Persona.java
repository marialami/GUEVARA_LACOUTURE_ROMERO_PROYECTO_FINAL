package logic.entities;

import logic.PersonaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Persona extends Exportable implements Serializable {

    private String name;
    private String lastName;
    private int age;

    private boolean isVictim;
    private Enum aggressionType;
    private Enum side;
    private String full;


    public Persona(String name, String lastName, int age, boolean isVictim, Enum aggressionType, Enum side) throws PersonaException {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.isVictim = isVictim;
        this.aggressionType = aggressionType;
        this.side = side;
        this.full = this.name + " " + this.lastName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        return String.valueOf(age);
    }

    public boolean isVictim() {
        return isVictim;
    }

    public Enum getAggressionType() {
        return aggressionType;
    }

    public Enum getSide() {
        return side;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setVictim(boolean victim) {
        isVictim = victim;
    }

    public void setAggressionType(Enum aggressionType) {
        this.aggressionType = aggressionType;
    }

    public void setSide(Enum side) {
        this.side = side;
    }
    public void setFull(String name, String lastName) {
        this.full = name + " "+ lastName;
    }

    public String getFull() {
        return full;
    }
    public void prueba()
    {
        System.out.println("Hola");
    }

    @Override
    public List<String> toListString() {

        String  vistima = "NO";
        String  agresion = String.valueOf(AggressionType.NO_APLICA);
        String  lado = String.valueOf(Side.CIVILIAN);

        if (isVictim)
            vistima= "SI";
        if (aggressionType.equals(AggressionType.VIOLENCIA_SEXUAL))
            agresion= "Violencia Sexual";
        if (aggressionType.equals(AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS))
            agresion= "Violencia Homicida";
        if (aggressionType.equals(AggressionType.VIOLENCIA_CON_ARMAS))
            agresion= "Violencia Con Armas";
        if (side.equals(Side.POLICE))
            lado = "Policia";
        if (side.equals(Side.CIVILIAN))
            lado = "Manifestante";

        List<String> result = new ArrayList<>();
        result.add(this.name);
        result.add(this.lastName);
        result.add(String.valueOf(this.age));
        result.add(vistima);
        result.add(agresion);
        result.add(lado);
        return result;
    }

    @Override
    public String getHeader() {
        return "Nombre, Apellido, Edad, Es Victima?, Tipo de agresión, Bando";
    }
}
