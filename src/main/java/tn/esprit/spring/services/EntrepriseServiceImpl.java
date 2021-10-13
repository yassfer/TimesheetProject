package tn.esprit.spring.services;

import java.util.ArrayList;
import org.apache.log4j.Logger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	//Dhekra
	public int ajouterEntreprise(Entreprise entreprise) {
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	//Amal
	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	//Amal
	public void affecterDepartementAEntreprise(int depId, int entrepriseId) {
		//Le bout Master de cette relation N:1 est departement  
				//donc il faut rajouter l'entreprise a departement 
				// ==> c'est l'objet departement(le master) qui va mettre a jour l'association
				//Rappel : la classe qui contient mappedBy represente le bout Slave
				//Rappel : Dans une relation oneToMany le mappedBy doit etre du cote one.
				Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
				Departement depManagedEntity = deptRepoistory.findById(depId).get();
				
				depManagedEntity.setEntreprise(entrepriseManagedEntity);
				deptRepoistory.save(depManagedEntity);
		
	}
	
	//Amal
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
		List<String> depNames = new ArrayList<>();
		for(Departement dep : entrepriseManagedEntity.getDepartements()){
			depNames.add(dep.getName());
		}
		
		return depNames;
	}

	//Dhekra
	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		entrepriseRepoistory.delete(entrepriseRepoistory.findById(entrepriseId).get());	
	}

	//Amal
	@Transactional
	public void deleteDepartementById(int depId) {
		deptRepoistory.delete(deptRepoistory.findById(depId).get());	
	}


	//Dhekra
	public Entreprise getEntrepriseById(int entrepriseId) {
		return entrepriseRepoistory.findById(entrepriseId).get();	
	}

	
	//Dhekra new
		private static final Logger logger = Logger.getLogger(EmployeServiceImpl.class);
		@Override
		public List<Entreprise> retrieveAllEntreprises() {
			logger.info("In  retrieveAllEmployes : "); 
			List<Entreprise> entreprises = (List<Entreprise>) entrepriseRepoistory.findAll();  
			for (Entreprise entreprise : entreprises) {
				logger.debug("entreprise +++ : " + entreprise);
			}
			logger.info("Out of retrieveAllEntreprises."); 
			return entreprises;
			
		}
		
		//Dhekra
		@Override
		public String getEntreprisenameById(int entrepriseId) {
			String name ="test" ;
			try {
			logger.info("In getEntreprisePrenomById(" + entrepriseId + ")");
			Entreprise entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId).get();
			name = entrepriseManagedEntity.getName();
			logger.info("Out getEmployePrenomById : " + name);
			}catch (Exception e) {logger.error("Erreur : " + e);}

			return name;
		}

		@Override
		public List<String> getAllEntrepriseNamesJPQL() {
			// TODO Auto-generated method stub
			return entrepriseRepoistory.entrepriseNames();
		}
		
		/*public List<Entreprise> getAllEntreprise() {
			return (List<Entreprise>) entrepriseRepoistory.findAll();
	}*/

		
		public List<Entreprise> getAllEntreprise() {
			logger.info("|| getAllEntreprise service open :");
			List<Entreprise> listentreprise =(List<Entreprise>) entrepriseRepoistory.findAll();
			for (Entreprise entreprise : listentreprise) {
				logger.debug("entreprise ++ : "+entreprise.toString());
			}
			logger.info("getAllEntreprise service close : ||");
			return listentreprise;
			
		}

		
		@Override
		public String getNameByEntreriseIdJPQL(int entrepriseId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void mettreAjourNameByEntrepriseIdJPQL(String name, int entrepriseId) {
			Entreprise entreprise = entrepriseRepoistory.findById(entrepriseId).get();
			entreprise.setName(name);
			entrepriseRepoistory.save(entreprise);
			
		}

		@Override
		public void deleteAllEntrepriseJPQL() {
			// TODO Auto-generated method stub
			entrepriseRepoistory.deleteAllEntrepriseJPQL();
			
		}

		@Override
		public int getNombreEntrepriseJPQL() {
			// TODO Auto-generated method stub
			return entrepriseRepoistory.countemp();
		}

		@Override
		public int addOrUpdateEntreprise(Entreprise entreprise) {
			// TODO Auto-generated method stub
			
			entrepriseRepoistory.save(entreprise);
			return entreprise.getId();
		}

		
		
	
	
}
