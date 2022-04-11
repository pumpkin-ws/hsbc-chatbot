package test;
import com.spark.HuaweiCBS;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class CloudTest {
    @Test
    void exampleQA() {
        System.out.println(HuaweiCBS.getAnswer("who are you"));
        assertTrue(HuaweiCBS.getAnswer("who are you").equals("I am metaverse chat bot."));
    }
}
