package tn.esprit.spring.services;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import java.util.Optional;
import org.apache.logging.log4j.Level;

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
		Optional<Mission> mission = missionRepository.findById(missionId);
		Optional<Departement> dep = deptRepoistory.findById(depId);
		if(mission.isPresent() && dep.isPresent()) {
			mission.get().setDepartement(dep.get());
			missionRepository.save(mission.get());
			//logging
			l.log(Level.INFO, ()-> "affecterMissionADepartement:"+ missionId +"A"+ depId);
		}		
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
			l.log(Level.INFO, ()-> "In valider Timesheet");
			Optional<Employe> validateur = employeRepository.findById(validateurId);
			Optional<Mission> mission = missionRepository.findById(missionId);
			if(validateur.isPresent() && mission.isPresent()) {
				//verifier s'il est un chef de departement (interet des enum)
				if(!validateur.get().getRole().equals(Role.CHEF_DEPARTEMENT)){
					l.log(Level.INFO, ()-> "l'employe doit etre chef de departement pour valider une feuille de temps !");
					return;
				}
				//verifier s'il est le chef de departement de la mission en question
				boolean chefDeLaMission = false;
				for(Departement dep : validateur.get().getDepartements()){
					if(dep.getId() == mission.get().getDepartement().getId()){
						chefDeLaMission = true;
						break;
					}
				}
				if(!chefDeLaMission){
					l.log(Level.INFO, ()-> "l'employe doit etre chef de departement de la mission en question");
					return;
				}
		//
				TimesheetPK timesheetPK = new TimesheetPK(missionId, employeId, dateDebut, dateFin);
				Timesheet timesheet =timesheetRepository.findBytimesheetPK(timesheetPK);
				timesheet.setValide(true);
				
				//Comment Lire une date de la base de données
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				l.log(Level.INFO,()-> "dateDebut : " + dateFormat.format(timesheet.getTimesheetPK().getDateDebut()));
			}
			
		}
		
	//Nada
	public List<Mission> findAllMissionByEmployeJPQL(int employeId) {
		  List<Mission> misList = timesheetRepository.findAllMissionByEmployeJPQL(employeId);
		//logging  
		   for (Mission mis: misList){
			   l.log(Level.INFO,()-> "findAllMissionByEmploye:"+ mis);
		   }
		   
		return misList;
		
		 
	}
	
	//Nada
	public List<Mission> getAllMissions() {
		//logging
		 l.info("getAllMissions:" ); 
		 return (List<Mission>) missionRepository.findAll();
     
}
	
	//Nada
	public void deleteMissionById(int misId) {
		Optional<Mission> misdelete=missionRepository.findById(misId);
		if(misdelete.isPresent()) {
		missionRepository.delete(misdelete.get());
		}
	}
	
	//Nada
	public Optional<Mission> getMissionById(int misId) {
		Optional<Mission> misList = missionRepository.findById(misId) ;	
		//logging
		l.log(Level.INFO, ()-> "getMissionById "+ misId);
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
		 //logging
		l.info("findAllMissionBydepartement:" ); 
		List<Mission> misList =missionRepository.findAllMissionBydepartementJPQL(depId); 
		 for (Mission mis: misList){
			 l.log(Level.INFO, ()-> "findAllMissionBydepartementJPQL:"+mis);
		   }
	     return  misList;
	}

}