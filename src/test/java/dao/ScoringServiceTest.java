package dao;

import org.junit.BeforeClass;
import org.junit.Test;
import org.wooddog.Config;
import org.wooddog.dao.ScoringService;
import org.wooddog.dao.service.ScoringServiceDao;
import org.wooddog.domain.Scoring;

import java.util.List;

public class ScoringServiceTest {
    private ScoringService service = ScoringServiceDao.getInstance();

    @BeforeClass
    public static void setup() {
        Config.load("org/wooddog/config/mysql.properties");
    }

    @Test
    public void testGetKeyWords() {
        List<String> list = service.getKeyWords(6);

        System.out.println(list.size());

        for (String s : list) {
            System.out.println(s);
        }
    }
}
