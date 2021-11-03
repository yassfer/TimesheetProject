package tn.esprit.spring;

<<<<<<< HEAD
=======
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit4.SpringRunner;
>>>>>>> 40ce4a9



<<<<<<< HEAD
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.IEmployeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratServiceImplTest {

	@Autowired
	IEmployeService iEmployeService;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	EmployeServiceImpl employeServiceImpl;
	@Autowired
	private EmployeRepository employeRepository;
<<<<<<< HEAD

	// private static final Logger logger =
	// Logger.getLogger(ContratServiceImplTest.class);
=======
	
>>>>>>> 40ce4a9
	private static final Logger l = LogManager.getLogger(ContratServiceImplTest.class);

	// private Contrat contr2 ;

	private static String update = "update";

	private Contrat contrat;
	private Employe employe1;

	@Before
	public void setUp() {

		employe1 = new Employe("BenMoussa", "Imen", "Imen@gmail.com", true, Role.INGENIEUR);
		contrat = contratRepoistory.save(new Contrat(new Date(2020, 04, 10), "CDI", 2000));
		employeRepository.save(employe1);
		contrat.setEmploye(employe1);
		contratRepoistory.save(contrat);

	}

	@After
	public void tearDown() {
		contratRepoistory.deleteAll();
		employeRepository.deleteAll();

	}

	@Test
	public void TestajouterContrat() {
		String sDate1 = "02/02/2000";
		Date date1 = new Date(sDate1);
		Contrat contrat = new Contrat(1, date1, "test", 1000, employe1);
		int contratadd = iEmployeService.ajouterContrat(contrat);
		// assertTrue(contrat.getTypeContrat().equals("CDI"));
		assertEquals(contratadd, contrat.getReference());
	}

	@Test
	public void TestgetAllContratByEmploye() {
		Contrat contrat1 = iEmployeService.getAllContratByEmploye(employe1);
		l.info("GetContratByEmploye : " + contrat1);
		assertThat(contrat1.getReference()).isEqualTo(contrat.getReference());
	}

	@Test
	public void TestgetAllContrats() {
		List<Contrat> contrats = iEmployeService.getAllContrats();
		// l.log(Level.INFO, () -> "getAllContrats : " + employes);
		assertThat(contrats.size()).isGreaterThan(0);
	}
<<<<<<< HEAD

	// a revoir pour dyn
	/*
	 * @Test public void TestgetContratTypeById() { assertEquals("test",
	 * iEmployeService.getContratTypeById(1));
	 * 
	 * }
	 */

	/*
	 * @Test public void TestAffecterContratAEmploye() {
	 * iEmployeService.affecterContratAEmploye(24,22); Optional<Contrat> c =
	 * contratRepoistory.findById(24); assertEquals(22,
	 * c.get().getEmploye().getId());
	 * 
	 * }
	 */

	// dyn
	@Test
	public void TestgetNombreContratJPQL() {
		int nbr = iEmployeService.getNombreContratJPQL();
		// l.log(Level.INFO, () -> "getNombreContratJPQL : " + nbr);
		l.info("getNombreContratJPQLTest" + nbr);
		assertThat(nbr).isEqualTo(1);
	}

	// dyn
=======
	
	//a revoir pour dyn	 
	/*	@Test
	public void TestgetContratTypeById() {
		assertEquals("test", iEmployeService.getContratTypeById(1));

	}*/
		

	/*@Test
	public void TestAffecterContratAEmploye() {
		iEmployeService.affecterContratAEmploye(24,22);
		 Optional<Contrat> c = contratRepoistory.findById(24);
		 assertEquals(22, c.get().getEmploye().getId());
	
			}*/


	//dyn
		@Test
		public void TestgetNombreContratJPQL() {
			int nbr = iEmployeService.getNombreContratJPQL();
			l.info("getNombreContratJPQLTest"+ nbr);
			assertThat(nbr).isEqualTo(1);
		}
		
	//dyn
>>>>>>> 40ce4a9
	@Test
	public void TestDeleteAllContratJPQL() {

		iEmployeService.deleteAllContratJPQL();
		assertThat(contratRepoistory.findAll()).isEmpty();

	}

	/*
	 * @Test public void TestgetContratById() { //test Contrat contratList =
	 * (Contrat)iEmployeService.getContratById(1); assertEquals("test",
	 * contratList.getTypeContrat());
	 * 
	 * }
	 */

	// dyn

	@Test
	public void TestdeleteContratById() {

		iEmployeService.deleteContractById(contrat.getReference());
		Optional<Contrat> contratdelete = contratRepoistory.findById(contrat.getReference());
		assertThat(contratdelete).isEmpty();
	}
=======
public class ContratServiceImplTest {

	
}

>>>>>>> 3b2683afef1c3eb0d8175152244153417c154442

}
