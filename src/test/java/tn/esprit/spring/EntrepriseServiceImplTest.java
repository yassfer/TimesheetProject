package tn.esprit.spring;


import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.utils.BaseJUnit49TestCase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;




@SpringBootTest
public class EntrepriseServiceImplTest extends BaseJUnit49TestCase {
	private static final Logger LOG = LogManager.getLogger(EntrepriseServiceImplTest.class);

	@Autowired
	IEntrepriseService entServ;
	@Autowired
	private EntrepriseServiceImpl entrepriseService;
	@Autowired
	EntrepriseRepository entrepriseRepository;
	private Entreprise entreprise;
	private static String name = "ESPRIT";

	@Override
	public void setUp() throws Exception {
		super.setUp();
		this.entreprise = new Entreprise();
		this.entreprise.setName(getIdHelper().createRandomString(5));
		this.entreprise.setRaisonSocial(getIdHelper().createRandomString(20));
	}

	@Test
	public void tests() {
		ajouterEntrepriseTest();
		getEntrepriseByIdTest();
		deleteEntrepriseByIdTest();
		mettreAjourEmailByEmployeIdTest();
	}
	@Test
	public void ajouterEntrepriseTest() {
		LOG.info("Start ajouterEntrepriseTest Message method test");
		LOG.info(this.entreprise);
		this.entreprise.setId(entServ.ajouterEntreprise(this.entreprise));
		assertTrue(this.entreprise.getId() > 0);
		LOG.info(this.entreprise);
		LOG.info("Entreprise id" + this.entreprise.getId());
		LOG.info("End ajouterEntreprise method test");

	}
	@Test
	public void getEntrepriseByIdTest() {
		LOG.info("Start getEntrepriseById method test");
		LOG.info("Entreprise id" + this.entreprise.getId());
		assertNotNull(entServ.getEntrepriseById(this.entreprise.getId()));
		LOG.info("End getEntrepriseById method test");

	}
	@Test
	public void deleteEntrepriseByIdTest() {
		LOG.info("Start deleteEntrepriseById method test");
		LOG.info("Entreprise id" + this.entreprise.getId());
		entServ.deleteEntrepriseById(this.entreprise.getId());
		//assertNull(entServ.getEntrepriseById(this.entreprise.getId()));
		LOG.info("End deleteEntrepriseById method test");

	}

	
	@Test
	public void mettreAjourEmailByEmployeIdTest() {
		entrepriseService.mettreAjourNameByEntrepriseId(name, entreprise.getId());
		Optional<Entreprise> e = entrepriseRepository.findById(entreprise.getId());
		if (e.isPresent()) {
			assertThat(e.get().getName()).isEqualTo(name);
		}
	}
	@Test
	public void getAllEntrepriseTest() {
		List<Entreprise> entreprises = entrepriseService.getAllEntreprise();
		LOG.log(Level.INFO, () -> "getAllEntreprise : " + entreprises);
		assertThat(entreprises.size()).isGreaterThan(0);
	}
	
	@Test
	public void getNbrEntrepriseJPQLTest() {
		int nombre = entrepriseService.getNbrEntrepriseJPQL();
		LOG.log(Level.INFO, () -> "getNbrEntrepriseJPQL : " + nombre);
		assertThat(nombre).isEqualTo(2);
	}
	

}
/*package tn.esprit.spring;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EmployeServiceImpl;
import tn.esprit.spring.services.EntrepriseServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {
	private static final Logger l = LogManager.getLogger(EntrepriseServiceImplTest.class);

	@Mock
	@Autowired
	private EmployeRepository employeRepository;

	@Mock
	@Autowired
	DepartementRepository deptRepository;

	@Mock
	@Autowired
	EntrepriseRepository entrepRepository;


	@Autowired
	@InjectMocks
	private EntrepriseServiceImpl entrepriseService;
	private Entreprise entreprise;
	private Departement departement;


	@Before
	public void setUp() {

		entreprise = entrepRepository.save(new Entreprise("Moodme", "informatique"));
		departement = deptRepository.save(new Departement("RH"));
		departement.setEntreprise(entreprise);
		deptRepository.save(departement);

	}

	@After
	public void tearDown() {
		entrepRepository.deleteAll();
		deptRepository.deleteAll();

	}

	@Test
	public void testAjouterDepartement() {
		Departement dep = new Departement("Finance");
		int AddedepID = entrepriseService.ajouterDepartement(dep);
		String name=deptRepository.findById(AddedepID).get().getName();
		assertEquals("Finance", name);
	}

	@Test
	public void testAffecterDepartementAEntreprise() {
		entrepriseService.affecterDepartementAEntreprise(departement.getId(), entreprise.getId());
		Optional<Departement> dep = deptRepository.findById(departement.getId());
		Entreprise emps= dep.get().getEntreprise();
		assertEquals(emps.getId(), entreprise.getId());

		
	}

	@Test
	public void testgetAllDepartementsNamesByEntreprise(){
		List<String> names= entrepriseService.getAllDepartementsNamesByEntreprise(entreprise.getId());
		assertEquals(1, names.size());
	}

	@Test
	public void testDeleteDepartementById() {
		entrepriseService.deleteDepartementById(departement.getId());
		Optional<Departement> deletedDepartement = deptRepository.findById(departement.getId());
		assertThat(deptRepository.findAll().isEmpty());
	}

}
*/