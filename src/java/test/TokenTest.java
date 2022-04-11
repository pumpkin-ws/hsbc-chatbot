package test;
import com.spark.TokenManager;
import org.junit.jupiter.api.Test;

public class TokenTest {
    @Test
    void validTest() {
        TokenManager.isValid();
    }
    @Test
    void updateToken() {
        TokenManager.updateToken();
    }
    @Test
    void getToken() {
        System.out.println(TokenManager.getToken());
    }
}
