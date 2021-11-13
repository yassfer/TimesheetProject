package tn.esprit.spring.utils;


import org.junit.After;
import org.junit.Before;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import tn.esprit.spring.repository.EntrepriseRepository;

public abstract class BaseJUnit49TestCase extends BaseSpringAwareTestCase {

    @Autowired
    protected ApplicationContext applicationContext;
    @Mock
	@Autowired
	EntrepriseRepository entrepRepository;

    protected BaseJUnit49TestCase() {
        super();
    }

    @After
    public void tearDown(){
    	entrepRepository.deleteAll();
		

    }

    @Before
    public void setUp(){

    }

}
