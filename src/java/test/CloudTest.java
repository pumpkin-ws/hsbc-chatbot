package test;
import com.spark.HuaweiCBS;
import org.junit.jupiter.api.Test;

public class CloudTest {
    @Test
    void nonsenseQuestion() {
        System.out.println(HuaweiCBS.getAnswer("name"));
    }
}
