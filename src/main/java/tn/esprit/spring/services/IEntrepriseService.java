package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;

public interface IEntrepriseService {
	
	public int ajouterEntreprise(Entreprise entreprise);
	public int ajouterDepartement(Departement dep);
	void affecterDepartementAEntreprise(int depId, int entrepriseId);
	List<String> getAllDepartementsNamesByEntreprise(int entrepriseId);
	public void deleteEntrepriseById(int entrepriseId);
	public void deleteDepartementById(int depId);
	public Entreprise getEntrepriseById(int entrepriseId);
	//Dhekra
		public List<Entreprise> retrieveAllEntreprises();
		public String getEntreprisenameById(int entrepriseId);
		public List<String> getAllEntrepriseNamesJPQL();
		public List<Entreprise> getAllEntreprise();
		public String getNameByEntreriseIdJPQL(int entrepriseId);
		public void mettreAjourNameByEntrepriseIdJPQL(String name,int entrepriseId);
		public void deleteAllEntrepriseJPQL();
		public int getNombreEntrepriseJPQL();
		int addOrUpdateEntreprise(Entreprise entreprise);
}
