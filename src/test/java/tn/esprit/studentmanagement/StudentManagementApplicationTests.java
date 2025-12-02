package tn.esprit.studentmanagement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentManagementApplicationTests {

   @Test
    void testAddition() {
        int a = 2;
        int b = 3;
        int result = a + b;

        // Vérifie que 2 + 3 = 5
        assertEquals(5, result);
    }

}
