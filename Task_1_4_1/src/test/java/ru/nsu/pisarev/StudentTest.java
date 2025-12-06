package ru.nsu.pisarev;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    public void testAverageScore() {
        Student s = new Student(true);

        GradeBook gb = s.getGradeBook();
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.GOOD));
        gb.addRecord(new GradeRecord(2, ControlType.DIFF_CREDIT, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(2, ControlType.CREDIT, Grade.PASSED));

        assertEquals(4.67, s.getCurrentAverageScore(), 0.01);
    }

    @Test
    public void testTransferToBudgetAllowed() {
        Student s = new Student(true);

        GradeBook gb = s.getGradeBook();
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.GOOD));
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(2, ControlType.EXAM, Grade.EXCELLENT));

        assertTrue(s.canTransferToBudget());
    }

    @Test
    public void testTransferToBudgetDenied() {
        Student s = new Student(true);

        GradeBook gb = s.getGradeBook();
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.SATISFACTORY)); // 3
        gb.addRecord(new GradeRecord(2, ControlType.EXAM, Grade.GOOD));

        assertFalse(s.canTransferToBudget());
    }


    @Test
    public void testRedDiplomaAllowed() {
        Student s = new Student(false);

        GradeBook gb = s.getGradeBook();
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(1, ControlType.DIFF_CREDIT, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(2, ControlType.EXAM, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(8, ControlType.DIPLOMA_DEFENSE, Grade.EXCELLENT));

        assertTrue(s.canReceiveRedDiploma());
    }


    @Test
    public void testRedDiplomaDeniedNotEnoughExcellent() {
        Student s = new Student(false);

        GradeBook gb = s.getGradeBook();
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.GOOD));
        gb.addRecord(new GradeRecord(8, ControlType.DIPLOMA_DEFENSE, Grade.EXCELLENT));

        assertFalse(s.canReceiveRedDiploma());
    }


    @Test
    public void testRedDiplomaDeniedSatisfactory() {
        Student s = new Student(false);

        GradeBook gb = s.getGradeBook();
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(2, ControlType.EXAM, Grade.SATISFACTORY)); // запрещено
        gb.addRecord(new GradeRecord(8, ControlType.DIPLOMA_DEFENSE, Grade.EXCELLENT));

        assertFalse(s.canReceiveRedDiploma());
    }


    @Test
    public void testIncreasedScholarshipAllowed() {
        Student s = new Student(false);

        GradeBook gb = s.getGradeBook();
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(1, ControlType.DIFF_CREDIT, Grade.EXCELLENT));

        assertTrue(s.canGetIncreasedScholarship(1));
    }


    @Test
    public void testIncreasedScholarshipDenied() {
        Student s = new Student(false);

        GradeBook gb = s.getGradeBook();
        gb.addRecord(new GradeRecord(1, ControlType.EXAM, Grade.EXCELLENT));
        gb.addRecord(new GradeRecord(1, ControlType.DIFF_CREDIT, Grade.GOOD));

        assertFalse(s.canGetIncreasedScholarship(1));
    }

}
