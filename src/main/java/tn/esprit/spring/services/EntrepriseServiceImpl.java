package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.advice.TrackExecutionTime;
import tn.esprit.spring.entities.Departement;
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
		@TrackExecutionTime
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
				Optional<Entreprise> entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId);
				Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
				if(entrepriseManagedEntity.isPresent() && depManagedEntity.isPresent()) {
					depManagedEntity.get().setEntreprise(entrepriseManagedEntity.get());
					deptRepoistory.save(depManagedEntity.get());
				}
	}
	
	//Amal
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Optional<Entreprise> entrepriseManagedEntity = entrepriseRepoistory.findById(entrepriseId);
		List<String> depNames = new ArrayList<>();
		if(entrepriseManagedEntity.isPresent()) {
			for(Departement dep : entrepriseManagedEntity.get().getDepartements()){
				depNames.add(dep.getName());
			}
		}
		return depNames;
	}

	//Dhekra
		@TrackExecutionTime
		@Transactional
		public void deleteEntrepriseById(int entrepriseId) {
			Optional<Entreprise> e = entrepriseRepoistory.findById(entrepriseId);
			if(e.isPresent()) {
				entrepriseRepoistory.delete(e.get());
			}
				
		}


	//Amal
	@Transactional
	public void deleteDepartementById(int depId) {
		Optional<Departement> d = deptRepoistory.findById(depId);
		if(d.isPresent()) {
			deptRepoistory.delete(d.get());
		}
	}



	//Dhekra
	public Optional<Entreprise> getEntrepriseById(int entrepriseId) {
		return Optional.ofNullable(entrepriseRepoistory.findById(entrepriseId)).orElse(null);	
	}
	//Dhekra
			@TrackExecutionTime
			public void mettreAjourNameByEntrepriseId(String name, int entrepriseId) {
				Optional<Entreprise> entreprise = entrepriseRepoistory.findById(entrepriseId);
				if(entreprise.isPresent()) {
					entreprise.get().setName(name);
					entrepriseRepoistory.save(entreprise.get());
				}
			}
		
		//Dhekra
			@TrackExecutionTime
			public List<Entreprise> getAllEntreprise() {
						return (List<Entreprise>) entrepriseRepoistory.findAll();
			}
			@TrackExecutionTime
			public List<String> getAllEntrepriseNamesJPQL() {
				return entrepriseRepoistory.entrepriseNames();

			}

			@TrackExecutionTime
			public int getNbrEntrepriseJPQL() {
				return entrepriseRepoistory.countentreprise();
			}
}
