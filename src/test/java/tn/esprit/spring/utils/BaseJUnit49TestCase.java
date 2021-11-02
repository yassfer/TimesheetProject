package tn.esprit.spring.utils;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public abstract class BaseJUnit49TestCase extends BaseSpringAwareTestCase {

    @Autowired
    protected ApplicationContext applicationContext;

    protected BaseJUnit49TestCase() {
        super();
    }

    @After
    public void tearDown() {

    }

    @Before
    public void setUp() {

    }

}
