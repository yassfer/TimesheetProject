package tn.esprit.spring.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;



public interface ITimesheetService {
	
	public int ajouterMission(Mission mission);
	public void affecterMissionADepartement(int missionId, int depId);
	
	public void ajouterTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin);
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId);
	
	public List<Mission> findAllMissionByEmployeJPQL(int employeId);
	public List<Mission> findAllMissionBydepartementJPQL(int depId);	
	public List<Employe> getAllEmployeByMission(int missionId);
	public List<Mission> getAllMissions();
	public void deleteMissionById(int misId);
	public Optional<Mission> getMissionById(int misId);
	public void mettreAjourDescriptionByMissionId(String desc, int misId);
}
