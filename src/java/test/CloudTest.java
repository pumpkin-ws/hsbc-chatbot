package test;
import com.spark.HuaweiCBS;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CloudTest {
    @Test
    void exampleQA() {
        assertTrue(HuaweiCBS.getAnswer("who are you").equals("I am metaverse chat bot."));
    }

}
