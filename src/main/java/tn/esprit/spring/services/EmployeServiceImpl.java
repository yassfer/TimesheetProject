package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.advice.TrackExecutionTime;
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

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import exceptions.ResourceNotFoundException;

@Service
public class EmployeServiceImpl implements IEmployeService {
	private static final Logger logger = LogManager.getLogger(EmployeServiceImpl.class);

	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	// Yasmin
	@TrackExecutionTime
	public int ajouterEmploye(Employe employe) {
		employeRepository.save(employe);
		return employe.getId();
	}

	// Yasmin
	@TrackExecutionTime
	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Optional<Employe> employe = employeRepository.findById(employeId);
		if (employe.isPresent()) {
			employe.get().setEmail(email);
			employeRepository.save(employe.get());
		}
	}

	// Amal
	@Transactional
	public void affecterEmployeADepartement(int employeId, int depId) {

		Optional<Departement> depManagedEntity = deptRepoistory.findById(depId);
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		if (depManagedEntity.isPresent() && employeManagedEntity.isPresent()) {
			if (depManagedEntity.get().getEmployes() == null) {

				List<Employe> employes = new ArrayList<>();
				employes.add(employeManagedEntity.get());
				depManagedEntity.get().setEmployes(employes);
			} else {

				depManagedEntity.get().getEmployes().add(employeManagedEntity.get());
			}
		}

	}

	// Amal
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId) {
		Optional<Departement> dep = deptRepoistory.findById(depId);
		if (dep.isPresent()) {
			int employeNb = dep.get().getEmployes().size();
			for (int index = 0; index < employeNb; index++) {
				if (dep.get().getEmployes().get(index).getId() == employeId) {
					dep.get().getEmployes().remove(index);
					break;// a revoir
				}
			}
		}

	}

	// Yasmin
	@TrackExecutionTime
	public String getEmployePrenomById(int employeId) {
		Optional<Employe> employeManagedEntity = employeRepository.findById(employeId);
		if (employeManagedEntity.isPresent()) {
			return employeManagedEntity.get().getPrenom();
		} else {
			return null;
		}

	}

	// Yasmin
	@TrackExecutionTime
	public void deleteEmployeById(int employeId) {
		Optional<Employe> employe = employeRepository.findById(employeId);

		// Desaffecter l'employe de tous les departements
		// c'est le bout master qui permet de mettre a jour
		// la table d'association
		if (employe.isPresent()) {
			for (Departement dep : employe.get().getDepartements()) {
				dep.getEmployes().remove(employe.get());
				deptRepoistory.save(dep);
			}
			employeRepository.deleteEmploye(employe.get().getId());
		}
	}

	// Yasmin
	@TrackExecutionTime
	public int getNombreEmployeJPQL() {
		return employeRepository.countemp();
	}

	// Yasmin
	@TrackExecutionTime
	public List<String> getAllEmployeNamesJPQL() {
		return employeRepository.employeNames();

	}

	// Yasmin
	@TrackExecutionTime
	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	// Yasmin
	@TrackExecutionTime
	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}

	// Yasmin
	@TrackExecutionTime
	public float getSalaireByEmployeIdJPQL(int employeId) {
		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	// Amal
	public Double getSalaireMoyenByDepartementId(int departementId) {
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	// NON
	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	// Yasmin
	@TrackExecutionTime
	public List<Employe> getAllEmployes() {
		return (List<Employe>) employeRepository.findAll();
	}

	// Imen
	public int ajouterContrat(Contrat contrat) {
		contratRepoistory.save(contrat);
		return contrat.getReference();
	}

	// Imen
	public int getNombreContratJPQL() {
		return contratRepoistory.countcontrat();
	}

	// Imen
	public void affecterContratAEmploye(int contratId, int employeId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId)
				.orElseThrow(() -> new ResourceNotFoundException("Contrat not found with this id : " + contratId));
		Employe employeManagedEntity = employeRepository.findById(employeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employé not found with this id : " + employeId));

		contratManagedEntity.setEmploye(employeManagedEntity);
		contratRepoistory.save(contratManagedEntity);

	}

	// Imen
	public void deleteContratById(int contratId) {
		Contrat contratManagedEntity = contratRepoistory.findById(contratId)
				.orElseThrow(() -> new ResourceNotFoundException("Contrat not found with this id : " + contratId));
		contratRepoistory.delete(contratManagedEntity);

	}

	// dyna Imen
	public void deleteContractById(int contatId) {
		Optional<Contrat> contdelete = contratRepoistory.findById(contatId);
		if (contdelete.isPresent()) {
			contratRepoistory.delete(contdelete.get());
		}
	}

	// Imen
	public void deleteAllContratJPQL() {
		employeRepository.deleteAllContratJPQL();
	}

	// Imen
	public List<Contrat> getAllContrats() {

		// logging
		logger.info("getAllContrats:");
		return (List<Contrat>) contratRepoistory.findAll();

	}

	// Imen
	@Override
	public String getContratTypeById(int reference) {
		String type = "test";
		try {
			logger.log(Level.INFO, () -> "In getContratTypeById(" + reference + ")");
			Optional<Contrat> contratManagedEntity = contratRepoistory.findById(reference);
			if (contratManagedEntity.isPresent()) {
				type = contratManagedEntity.get().getTypeContrat();
				logger.info("Out getContratTypeById : " + type);
			}

		} catch (Exception e) {
			logger.error("Erreur : " + e);
		}

		return type;
	}

	// Imen dynamique
	public Contrat getContratById(int reference) {
		Optional<Contrat> contratList = contratRepoistory.findById(reference);
		// logging
		if (contratList.isPresent()) {
			logger.info("getContratById : " + contratList);
			return contratList.get();
		}
		return null;
	}

	public Contrat getAllContratByEmploye(Employe employe) {
		return contratRepoistory.getAllContratByEmploye(employe);
	}

}
