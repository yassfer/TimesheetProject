package tn.esprit.spring.service.test;

import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.EntrepriseServiceImpl;
import tn.esprit.spring.services.IEntrepriseService;
import java.util.ArrayList;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseServiceImplTest {


	
	@Autowired
	EntrepriseRepository entrepriseRepository;
	@Autowired
	IEntrepriseService iEntrepriseService;
	@Autowired
	EntrepriseServiceImpl entrepriseServiceImpl;

	@Test
	public void testAddEntreprise() throws ParseException {
		Entreprise e = new Entreprise("test", "test");
		int entrepriseAdded = iEntrepriseService.ajouterEntreprise(e);
		assertEquals(entrepriseAdded, e.getId());
	}

	@Test
	public void AddOrUpdateEntrepriseTest() {
		int nbr = iEntrepriseService.getNombreEntrepriseJPQL();
		Entreprise e2 = new Entreprise("DHEKRA", "DHEKRA");
		iEntrepriseService.addOrUpdateEntreprise(e2);

		assertEquals(nbr + 1, iEntrepriseService.getNombreEntrepriseJPQL());
	}

	@Test
	public void testDeleteEntrepriseById() {
		iEntrepriseService.deleteEntrepriseById(1);
		List<Entreprise> listEntreprises = iEntrepriseService.retrieveAllEntreprises();
		assertEquals(4, listEntreprises.size());
	}

	@Test
	public void getEntreprisenameById() {
		assertEquals("test", iEntrepriseService.getEntreprisenameById(3));

	}

	@Test
	public void getAllEntreprise() {

		List<Entreprise> L = iEntrepriseService.getAllEntreprise();

		assertEquals(3, L.size());
	}

	@Test
	public void MettreAjourNameByEntrepriseIdTest() {

		int id = entrepriseRepository.findById(2).get().getId();
		iEntrepriseService.mettreAjourNameByEntrepriseIdJPQL("DHEKRA", id);
		;
		assertEquals("DHEKRA", entrepriseRepository.findById(2).get().getName());
	}

	@Test
	public void deleteAllEntrepriseJPQL() {

		iEntrepriseService.deleteAllEntrepriseJPQL();
		assertNull(entrepriseRepository.findAll());

	}

	@Test
	public void getAllEntrepriseNamesJPQL() {

		List<String> L = iEntrepriseService.getAllEntrepriseNamesJPQL();
		String name = L.get(0);
		assertEquals("test", name);
	}

}
