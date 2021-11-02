package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EmployeServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeServiceImplTest {

	private static final Logger l = LogManager.getLogger(EmployeServiceImplTest.class);

	@Mock
	@Autowired
	private EmployeRepository employeRepository;

	@Mock
	@Autowired
	DepartementRepository deptRepoistory;

	@Mock
	@Autowired
	EntrepriseRepository entrepRepository;

	@Mock
	@Autowired
	ContratRepository contratRepoistory;

	@Autowired
	@InjectMocks
	private EmployeServiceImpl employeService;
	private static String mail = "ferchichi@gmail.com";
	private Employe employe1;
	private Employe employe2;
	private Employe employe3;
	private Employe employe4;

	private Entreprise entreprise;
	private Departement departement;
	private Contrat contrat1;
	private Contrat contrat2;


	@Before
	public void setUp() {

		entreprise = entrepRepository.save(new Entreprise("ESPRIT", "educatif"));
		departement = deptRepoistory.save(new Departement("Recherche"));
		departement.setEntreprise(entreprise);
		deptRepoistory.save(departement);

		employe1 = new Employe("Ferchichi", "Yasmine", "yasmine@gmail.com", true, Role.INGENIEUR);
		employe2 = new Employe("Korkad", "Nada", "nada@gmail.com", false, Role.INGENIEUR);
		employe3 = new Employe("Salah", "Eya", "eya@gmail.com", false, Role.INGENIEUR);
		employe4 = new Employe("Fahem", "Anas", "anas@gmail.com", false, Role.INGENIEUR);

		employeRepository.save(employe1);
		employeRepository.save(employe3);
		employeRepository.save(employe4);
		employeService.affecterEmployeADepartement(employe1.getId(), departement.getId());
		employeService.affecterEmployeADepartement(employe4.getId(), departement.getId());


		contrat1 = contratRepoistory.save(new Contrat(new Date(2020, 04, 10), "CDI", 2000));
		contrat2 = contratRepoistory.save(new Contrat(new Date(2020, 04, 10), "CDI", 1000));

		contrat1.setEmploye(employe1);
		contrat2.setEmploye(employe4);

		contratRepoistory.save(contrat1);
		contratRepoistory.save(contrat2);

	}

	@After
	public void tearDown() {
		entrepRepository.deleteAll();
		deptRepoistory.deleteAll();
		contratRepoistory.deleteAll();
		employeRepository.deleteAll();
	}

	/*******************amal***************/
	@Test
	public void testaffecterEmployeADepartement() {
		boolean res= false;
		employeService.affecterEmployeADepartement(employe3.getId(), departement.getId());
		Optional<Employe> e = employeRepository.findById(employe3.getId());
		if(e.isPresent()) {
			List<Departement> deps= e.get().getDepartements();
			for (int i=0; i<deps.size(); i++){
				if(deps.get(i).getId()==departement.getId()){
					res=true;
				}
			}
			assertThat(res).isTrue();
		}
	}

	@Test
	public void testdesaffecterEmployeDuDepartement() {
		boolean res= true;
		employeService.desaffecterEmployeDuDepartement(employe3.getId(), departement.getId());
		Optional<Employe> e = employeRepository.findById(employe3.getId());
		if(e.isPresent()) {
			List<Departement> deps= e.get().getDepartements();
			for (int i=0; i<deps.size(); i++){
				if(deps.get(i).getId()==departement.getId()){
					res=false;
				}
			}
			assertThat(res).isTrue();
		}
		
}

	@Test
	public void testgetSalaireMoyenByDepartementId(){
		Double res= employeService.getSalaireMoyenByDepartementId(departement.getId());
		Double test=(double) 1500;
		assertEquals(test,res);
	}
	@Test
	public void ajouterEmployeTest() {
		Employe savedEmploye = new Employe("Laffet", "Amal", "amal@gmail.com", false, Role.INGENIEUR);
		employeService.ajouterEmploye(savedEmploye);
		assertThat(savedEmploye.getRole()).isEqualTo(Role.INGENIEUR);
	}

	@Test
	public void mettreAjourEmailByEmployeIdTest() {
		employeService.mettreAjourEmailByEmployeId(mail, employe1.getId());
		Optional<Employe> e = employeRepository.findById(employe1.getId());
		if (e.isPresent()) {
			assertThat(e.get().getEmail()).isEqualTo(mail);
		}
	}

	@Test
	public void getEmployePrenomByIdTest() {
		String prenom = employeService.getEmployePrenomById(employe1.getId());
		l.log(Level.INFO, () -> "getEmployePrenomById : " + prenom);
		assertThat(prenom).isEqualTo("Yasmine");
	}

	@Test
	public void deleteEmployeByIdTest() {
		employeService.deleteEmployeById(employe2.getId());
		Optional<Employe> deletedEmploye = employeRepository.findById(employe2.getId());
		assertThat(deletedEmploye).isEmpty();
	}

	@Test
	public void getNombreEmployeJPQLTest() {
		int nbr = employeService.getNombreEmployeJPQL();
		l.log(Level.INFO, () -> "getNombreEmployeJPQL : " + nbr);
		assertThat(nbr).isEqualTo(3);
	}

	@Test
	public void getAllEmployeNamesJPQLTest() {
		List<String> names = employeService.getAllEmployeNamesJPQL();
		l.log(Level.INFO, () -> "getAllEmployeNamesJPQL : " + names);
		//assertThat(names.get(0)).isEqualTo(employe1.getNom());
		//assertThat(names.get(1)).isEqualTo(employe2.getNom());
	}

	@Test
	public void getAllEmployeByEntrepriseTest() {
		List<Employe> employes = employeService.getAllEmployeByEntreprise(entreprise);
		l.log(Level.INFO, () -> "getAllEmployeByEntreprise : " + employes);
		assertThat(employes.size()).isEqualTo(2);
	}

	@Test
	public void mettreAjourEmailByEmployeIdJPQLTest() {
		employeService.mettreAjourEmailByEmployeIdJPQL("ferchichi@gmail.com", employe1.getId());
		Optional<Employe> e = employeRepository.findById(employe1.getId());
		if (e.isPresent()) {
			assertThat(e.get().getEmail()).isEqualTo("ferchichi@gmail.com");
		}
	}

	@Test
	public void getSalaireByEmployeIdJPQLTest() {
		float salaire = employeService.getSalaireByEmployeIdJPQL(employe1.getId());
		l.log(Level.INFO, () -> "getSalaireByEmployeIdJPQL : " + salaire);
		assertThat(salaire).isEqualTo(2000);
	}

	@Test
	public void getAllEmployesTest() {
		List<Employe> employes = employeService.getAllEmployes();
		l.log(Level.INFO, () -> "getAllEmployes : " + employes);
		assertThat(employes.size()).isGreaterThan(0);
	}
	



}
