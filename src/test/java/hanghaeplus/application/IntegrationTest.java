package hanghaeplus.application;

import hanghaeplus.application.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class IntegrationTest {

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    void initialize() {
        databaseCleaner.clear();
    }
}