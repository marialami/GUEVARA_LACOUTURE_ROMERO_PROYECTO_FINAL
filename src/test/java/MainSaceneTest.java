import logic.PersonaException;
import logic.entities.AggressionType;
import logic.entities.Persona;
import logic.entities.Side;
import logic.persistence.impl.PersonaPersistence;
import logic.services.impl.PersonaServices;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class MainSaceneTest {
    static PersonaServices pp;
    static Persona p1;
    static Persona p11;
    static Persona p2;
    static Persona p3;
    static Persona p4;
    static Persona p5;
    static Persona p6;
    static Persona p7;
    static Persona p8;



    @BeforeAll
    static void setUp() throws IOException, ClassNotFoundException, PersonaException {
        pp = new PersonaServices();
        p1 = new Persona("Diego", "Prieto", 26, true, AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS, Side.POLICE);
        p11 = new Persona("Diego", "Prieto", 26, true, AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS, Side.POLICE);
        p2 = new Persona("Diego", "Prieto", 26, true, AggressionType.VIOLENCIA_CON_ARMAS, Side.POLICE);
        p3 = new Persona("Diego", "Prieto", 26, true, AggressionType.VIOLENCIA_SEXUAL, Side.POLICE);
        p4 = new Persona("Diego", "Prieto", 26, true, AggressionType.VIOLENCIA_HOMICIDA_CON_ARMAS, Side.CIVILIAN);
        p5 = new Persona("Diego", "Prieto", 26, true, AggressionType.VIOLENCIA_CON_ARMAS, Side.CIVILIAN);
        p6 = new Persona("Diego", "Prieto", 26, true, AggressionType.VIOLENCIA_SEXUAL, Side.CIVILIAN);
        p7 = new Persona("Diego", "Prieto", 150, true, AggressionType.VIOLENCIA_SEXUAL, Side.CIVILIAN);
        p8 = new Persona("Diego", "Prieto", -150, true, AggressionType.VIOLENCIA_SEXUAL, Side.CIVILIAN);


    }

    @Test
    void shouldAddVictim() throws ClassNotFoundException, IOException, PersonaException {

        pp.insert(p1);
        Assertions.assertTrue(pp.getAll().contains(p1));
    }
    @Test
    void shouldDeleteVictim() throws ClassNotFoundException, IOException, PersonaException {
        pp.delete(p1);
        Assertions.assertFalse(pp.getAll().contains(p1));
    }
    @Test
    void shouldUpdateVictim() throws ClassNotFoundException, IOException, PersonaException {

        Persona p2 = null;
        p2 = pp.edit("Quien","Como",3,false,AggressionType.NO_APLICA,Side.CIVILIAN,p1);
        Assertions.assertTrue(pp.getAll().contains(p2));
    }
    @Test
    void shouldAddPoliceHomicidaVictim() throws ClassNotFoundException, IOException, PersonaException {

        pp.insert(p1);
        Assertions.assertTrue(pp.getpViolenciaHomicida().contains(p1));
    }
    @Test
    void shouldAddPoliceArmasVictim() throws ClassNotFoundException, IOException, PersonaException {

        pp.insert(p2);
        Assertions.assertTrue(pp.getpViolenciaConArmas().contains(p2));
    }
    @Test
    void shouldAddPoliceSexualVictim() throws ClassNotFoundException, IOException, PersonaException {

        pp.insert(p3);
        Assertions.assertTrue(pp.getpViolenciaSexual().contains(p3));
    }
    @Test
    void shouldAddCivilianHomicidalVictim() throws ClassNotFoundException, IOException, PersonaException {

        pp.insert(p4);
        Assertions.assertTrue(pp.getmViolenciaHomicida().contains(p4));
    }
    @Test
    void shouldAddCivilianArmasVictim() throws ClassNotFoundException, IOException, PersonaException {

        pp.insert(p5);
        Assertions.assertTrue(pp.getmViolenciaConArmas().contains(p5));
    }
    @Test
    void shouldAddSexualCivilianVictim() throws ClassNotFoundException, IOException, PersonaException {

        pp.insert(p6);
        Assertions.assertTrue(pp.getmViolenciaSexual().contains(p6));
    }
    @Test
    void shouldDeletePoliceHomicidaVictim() throws ClassNotFoundException, IOException, PersonaException {
        pp.delete(p11);
        Assertions.assertFalse(pp.getpViolenciaHomicida().contains(p11));
    }
    @Test
    void shouldDeletePoliceArmasVictim() throws ClassNotFoundException, IOException, PersonaException {
        pp.delete(p2);
        Assertions.assertFalse(pp.getpViolenciaConArmas().contains(p2));
    }
    @Test
    void shouldDeletePoliceSexualVictim() throws ClassNotFoundException, IOException, PersonaException {
        pp.delete(p3);
        Assertions.assertFalse(pp.getpViolenciaSexual().contains(p3));
    }
    @Test
    void shouldDeleteCivilianHomicidaVictim() throws ClassNotFoundException, IOException, PersonaException {
        pp.delete(p4);
        Assertions.assertFalse(pp.getmViolenciaHomicida().contains(p4));
    }
    @Test
    void shouldDeleteCivilianArmasVictim() throws ClassNotFoundException, IOException, PersonaException {
        pp.delete(p5);
        Assertions.assertFalse(pp.getmViolenciaConArmas().contains(p5));
    }
    @Test
    void shouldDeleteCivilianSexualVictim() throws ClassNotFoundException, IOException, PersonaException {
        pp.delete(p6);
        Assertions.assertFalse(pp.getmViolenciaSexual().contains(p6));
    }



}
