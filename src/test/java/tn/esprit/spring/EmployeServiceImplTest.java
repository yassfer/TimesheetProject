package tn.esprit.spring;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

	private Employe employe1;
	private Employe employe2;
	private Entreprise entreprise;
	private Departement departement;
	Contrat contrat;

	@Before
	public void setUp() {
		entreprise = entrepRepository.save(new Entreprise("ESPRIT", "educatif"));
		departement = deptRepoistory.save(new Departement("Recherche"));
		departement.setEntreprise(entreprise);
		deptRepoistory.save(departement);

		employe1 = new Employe("Ferchichi", "Yasmine", "yasmine@gmail.com", true, Role.INGENIEUR);
		employe2 = new Employe("Korkad", "Nada", "nada@gmail.com", false, Role.INGENIEUR);
		employeRepository.save(employe1);
		employeRepository.save(employe2);
		employeService.affecterEmployeADepartement(employe1.getId(), departement.getId());

		contrat = contratRepoistory.save(new Contrat(new Date(2020, 04, 10), "CDI", 2000));
		contrat.setEmploye(employe1);
		contratRepoistory.save(contrat);
	}

	@After
	public void tearDown() {
		entrepRepository.deleteAll();
		deptRepoistory.deleteAll();
		contratRepoistory.deleteAll();
		employeRepository.deleteAll();
	}

	@Test
	public void ajouterEmployeTest() {
		Employe savedEmploye = new Employe("Laffet", "Amal", "amal@gmail.com", false, Role.INGENIEUR);
		employeService.ajouterEmploye(savedEmploye);
		assertThat(savedEmploye.getId()).isGreaterThan(0);
	}

	@Test
	public void mettreAjourEmailByEmployeIdTest() {
		employeService.mettreAjourEmailByEmployeId("ferchichi@gmail.com", employe1.getId());
		assertThat(employeRepository.findById(employe1.getId()).get().getEmail()).isEqualTo("ferchichi@gmail.com");
	}

	@Test
	public void getEmployePrenomByIdTest() {
		String prenom = employeService.getEmployePrenomById(employe1.getId());
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
		assertThat(nbr).isEqualTo(2);
	}

	@Test
	public void getAllEmployeNamesJPQLTest() {
		List<String> names = employeService.getAllEmployeNamesJPQL();
		assertThat(names.get(0)).isEqualTo(employe1.getNom());
		assertThat(names.get(1)).isEqualTo(employe2.getNom());
	}

	@Test
	public void getAllEmployeByEntrepriseTest() {
		List<Employe> employes = employeService.getAllEmployeByEntreprise(entreprise);
		assertThat(employes.size()).isEqualTo(1);
	}

	@Test
	public void mettreAjourEmailByEmployeIdJPQLTest() {
		employeService.mettreAjourEmailByEmployeIdJPQL("ferchichi@gmail.com", employe1.getId());
		assertThat(employeRepository.findById(employe1.getId()).get().getEmail()).isEqualTo("ferchichi@gmail.com");
	}

	@Test
	public void getSalaireByEmployeIdJPQLTest() {
		float salaire = employeService.getSalaireByEmployeIdJPQL(employe1.getId());
		assertThat(salaire).isEqualTo(2000);
	}

	@Test
	public void getAllEmployesTest() {
		List<Employe> employes = employeService.getAllEmployes();
		assertThat(employes.size()).isGreaterThan(0);
	}
	
	//////////////amal
	@Test
	public void testaffecterEmployeADepartement() {
		boolean val= true;
		boolean res= false;
		employeService.affecterEmployeADepartement(1,3);
		Optional<Employe> emp = employeService.findById(1);
		List<Departement> deps= emp.get().getDepartements();
		int size=deps.size();
		for (int i=0; i<size; i++){
			if(deps.get(i).getId()==3){
				res=true;
			}
		}
		assertEquals(val,res);

}
	@Test
	public void testdesaffecterEmployeDuDepartement() {
		boolean val= true;
		boolean res= true;
		employeService.desaffecterEmployeDuDepartement(1,4);
		Optional<Employe> emp =  employeService.findById(1);
		List<Departement> deps= emp.get().getDepartements();
		int size=deps.size();
		for (int i=0; i<size; i++){
			if(deps.get(i).getId()==4){
				res=false;
			}
		}
		assertEquals(val,res);

}
	@Test
	public void testgetSalaireMoyenByDepartementId(){
		Double val=(double) 1200;
		Double res= employeService.getSalaireMoyenByDepartementId(4);
		assertEquals(val,res);
	}

}
	


