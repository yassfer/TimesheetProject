package tn.esprit.spring.services;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;


@Service
public class TimesheetServiceImpl implements ITimesheetService {
	

	@Autowired
	MissionRepository missionRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;
	@Autowired
	EmployeRepository employeRepository;
	
	private static final Logger l = LogManager.getLogger(TimesheetServiceImpl.class);
	
	
	
	//Nada
	public int ajouterMission(Mission mission) {
		missionRepository.save(mission);
		return mission.getId();
		
	}
    
	//Nada
	public void affecterMissionADepartement(int missionId, int depId) {
		Mission mission = missionRepository.findById(missionId).get();
		Departement dep = deptRepoistory.findById(depId).get();
		mission.setDepartement(dep);
		missionRepository.save(mission);
		//logging
		 l.info("affecterMissionADepartement : "+ mission +dep); 
	}

	//NON
	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin) {
		TimesheetPK timesheetPK = new TimesheetPK();
		timesheetPK.setDateDebut(dateDebut);
		timesheetPK.setDateFin(dateFin);
		timesheetPK.setIdEmploye(employeId);
		timesheetPK.setIdMission(missionId);
		
		Timesheet timesheet = new Timesheet();
		timesheet.setTimesheetPK(timesheetPK);
		timesheet.setValide(false); //par defaut non valide
		timesheetRepository.save(timesheet);
		
	}

	//NON
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		System.out.println("In valider Timesheet");
		Employe validateur = employeRepository.findById(validateurId).get();
		Mission mission = missionRepository.findById(missionId).get();
		//verifier s'il est un chef de departement (interet des enum)
		if(!validateur.getRole().equals(Role.CHEF_DEPARTEMENT)){
			System.out.println("l'employe doit etre chef de departement pour valider une feuille de temps !");
			return;
		}
		//verifier s'il est le chef de departement de la mission en question
		boolean chefDeLaMission = false;
		for(Departement dep : validateur.getDepartements()){
			if(dep.getId() == mission.getDepartement().getId()){
				chefDeLaMission = true;
				break;
			}
		}
		if(!chefDeLaMission){
			System.out.println("l'employe doit etre chef de departement de la mission en question");
			return;
		}
//
		TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
		Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
		timesheet.setValide(true);
		
		//Comment Lire une date de la base de données
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println("dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
		
	}

	//Nada
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		  List<Mission> misList = timesheetRepository.findAllMissionByEmployeJPQL(employeId);
		//logging  
		   for (Mission mis: misList){
			   l.info(" findAllMissionByEmploye : "+ misList); 
		   }
		   
		return misList;
		
		 
	}
	
	//Nada
	public List<Mission> getAllMissions() {
		  List<Mission> misList =(List<Mission>) missionRepository.findAll();
		 
		//logging
        for (Mission mis: misList){
        	l.info("Mission :" + mis); 
        }
        
        return misList;
}
	
	//Nada
	public void deleteMissionById(int misId) {
		Mission Mission = missionRepository.findById(misId).get();
		missionRepository.delete(Mission);

	}
	
	//Nada
	public Mission getMissionById(int misId) {
		 	Mission misList = (Mission)missionRepository.findById(misId).get();	
			//logging
	        l.info("getMissionById : "+ misList);
		 	return misList;
		 	
	}

	//Yasmin
	public List<Employe> getAllEmployeByMission(int missionId) {
		return timesheetRepository.getAllEmployeByMission(missionId);
		
	}
	
	//nada
	public void mettreAjourDescriptionByMissionId(String desc, int misId) {
		missionRepository.mettreAjourDescriptionByMissionId(desc, misId);
	}

	@Override
	public List<Mission> findAllMissionBydepartementJPQL(int depId) {
		List<Mission> misList =missionRepository.findAllMissionBydepartementJPQL(depId); 
	      //logging
	        for (Mission mis: misList){
	        	l.info("findAllMissionBydepartement :" + mis); 
	        }
	        
		 return (List<Mission>) misList;
	}

}