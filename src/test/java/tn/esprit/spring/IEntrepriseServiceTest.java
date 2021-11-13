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
public class IEntrepriseServiceTest extends BaseJUnit49TestCase {
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
	public void setUp(){
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
    /*@Test
	public void getAllEntrepriseTest() {
	//	List<Entreprise> entreprises = entrepriseService.getAllEntreprise();
		//LOG.log(Level.INFO, () -> "getAllEntreprise : " + entreprises);
		//assertThat(entreprises.size()).isGreaterThan(0);
    	
    	List<Entreprise> entreprises = entrepriseService.getAllEntreprise();
    	LOG.log(Level.INFO, () -> "getAllEntreprise : " + entreprises);
    		assertThat(entreprises.get(1)).isEqualTo(entreprise.getName());
    		//assertThat(names.get(1)).isEqualTo(employe2.getNom());
    	}

	*/
	
	/*@Test
	public void getNbrEntrepriseJPQLTest() {
		int nombre = entrepriseService.getNbrEntrepriseJPQL();
		LOG.log(Level.INFO, () -> "getNbrEntrepriseJPQL : " + nombre);
		assertThat(nombre).isEqualTo(2);
	}*/
	

}