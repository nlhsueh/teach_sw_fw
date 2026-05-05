// Note: This requires JUnit 5 library to compile properly
// import org.junit.jupiter.api.*;

class MyTest {

    // @BeforeAll
    static void setupAll() {
        System.out.println("Before all tests");
    }

    // @BeforeEach
    void setup() {
        System.out.println("Before each test");
    }

    // @Test
    void testExample() {
        System.out.println("Running test...");
    }

    // @AfterEach
    void teardown() {
        System.out.println("After each test");
    }

    // @AfterAll
    static void teardownAll() {
        System.out.println("After all tests");
    }
}
