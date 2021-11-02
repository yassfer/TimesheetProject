package tn.esprit.spring.utils;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public abstract class BaseSpringAwareTestCase {

    private final IdTestHelper idHelper = new IdTestHelper();

    public BaseSpringAwareTestCase() {
    }

    protected IdTestHelper getIdHelper() {
        return idHelper;
    }

}