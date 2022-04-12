package test;
import com.spark.TokenManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TokenTest {
    /**
     * Check if token is valid
     */
    @Test
    void validTest() {
        assertTrue(TokenManager.isValid());
    }
    /**
     * Run update token to ensure token is valid
     */
    @Test
    void updateToken() {
        TokenManager.updateToken();
    }

    /**
     * Check if the getToken can actually return the token in file
     */
    @Test
    void getToken() {
        System.out.println(TokenManager.getToken());
    }
}
