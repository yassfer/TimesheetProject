package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

import java.util.Optional;

@Service
public class EmployeServiceImpl implements IEmployeService {

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	//Yasmin
	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	//Yasmin
	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Optional<Employe> employe = employeRepository.findById(employeId);
		if(employe.isPresent()) {
			employe.get().setEmail(email);
			employeRepository.save(employe.get());
		}
	}

	//Amal
	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
		
		Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		if(depManagedEntity.isPresent() && employeManagedEntity.isPresent()) {
			if(depManagedEntity.get().getEmployes() == null){
				 
				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity.get());
				depManagedEntity.get().setEmployes(employes);
			}else{

				depManagedEntity.get().getEmployes().add(employeManagedEntity.get());
			}
		}

	}
	//Amal
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		Optional<Departement> dep = deptRepoistory.findById(depId);
		if(dep.isPresent()) {
			int employeNb = dep.get().getEmployes().size();
			for(int index = 0; index < employeNb; index++){
				if(dep.get().getEmployes().get(index).getId() == employeId){
					dep.get().getEmployes().remove(index);
					break;//a revoir
				}
			}
		}
		
	}

	//Imen
	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	//Imen
	public void affecterContratAEmploye(int contratId, int employeId) {
		Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		
		if(contratManagedEntity.isPresent() && employeManagedEntity.isPresent()) {
			contratManagedEntity.get().setEmploye(employeManagedEntity.get());
			contratRepoistory.save(contratManagedEntity.get());
		}
	}

	//Yasmin
	public String getEmployePrenomById(int employeId) {
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		if(employeManagedEntity.isPresent()) {
			return employeManagedEntity.get().getPrenom();
		} else {
			return null;
		}
		
	}
	//Yasmin
	public void deleteEmployeById(int employeId)
	{
		Optional<Employe> employe = employeRepository.findById(employeId);

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		if(employe.isPresent()) {
			for(Departement dep : employe.get().getDepartements()){
				dep.getEmployes().remove(employe.get());
				employeRepository.delete(employe.get());
			}
			
		}
	}

	//Imen
	public void deleteContratById(int contratId) {
		Optional<Contrat> contratManagedEntity = contratRepoistory.findById(contratId);
		if(contratManagedEntity.isPresent()) {
			contratRepoistory.delete(contratManagedEntity.get());
		}
	}

	//Yasmin
	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}
	
	//Yasmin
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}
	
	//Yasmin
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	//Yasmin
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	//Imen
	public void deleteAllContratJPQL() {
         employeRepository.deleteAllContratJPQL();
	}
	
	//Yasmin
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	//Amal
	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}
	
	//NON
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	//Yasmin
	public List<Employe> getAllEmployes() {
				return (List<Employe>) employeRepository.findAll();
	}

}
