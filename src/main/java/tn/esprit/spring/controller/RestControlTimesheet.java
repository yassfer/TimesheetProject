package tn.esprit.spring.controller;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RestController
public class RestControlTimesheet {

	@Autowired
	IEmployeService iemployeservice;
	@Autowired
	IEntrepriseService ientrepriseservice;
	@Autowired
	ITimesheetService itimesheetservice;
	
	@PostMapping("/ajouterMission")
	@ResponseBody
	public int ajouterMission(@RequestBody Mission mission) {
		itimesheetservice.ajouterMission(mission);
		return mission.getId();
	}

	@PutMapping(value = "/affecterMissionADepartement/{idmission}/{iddept}") 
	public void affecterMissionADepartement(@PathVariable("idmission") int missionId, @PathVariable("iddept") int depId) {
		itimesheetservice.affecterMissionADepartement(missionId, depId);

	}
	
	
	@PostMapping("/ajouterTimesheet/idmission/idemp/dated/datef")
	@ResponseBody
	public void ajouterTimesheet(@PathVariable("idmission") int missionId, @PathVariable("idemp") int employeId, @PathVariable("dated") Date dateDebut,@PathVariable("datef") Date dateFin) {
		itimesheetservice.ajouterTimesheet(missionId, employeId, dateDebut, dateFin);

	}

	@PutMapping(value = "/validerTimesheet/{idmission}/{iddept}") 
	public void validerTimesheet(int missionId, int employeId, Date dateDebut, Date dateFin, int validateurId) {
		itimesheetservice.validerTimesheet(missionId, employeId, dateDebut, dateFin, validateurId);

	}
	
    @GetMapping(value = "findAllMissionByEmployeJPQL/{idemp}")
    @ResponseBody
	public List<Mission> findAllMissionByEmployeJPQL(@PathVariable("idemp") int employeId) {

		return itimesheetservice.findAllMissionByEmployeJPQL(employeId);
	}

    @GetMapping(value = "getAllEmployeByMission/{idmission}")
    @ResponseBody
	public List<Employe> getAllEmployeByMission(@PathVariable("idmission") int missionId) {

		return itimesheetservice.getAllEmployeByMission(missionId);
	}
    
    @GetMapping(value = "/getAllMission")
    @ResponseBody
	public List<Mission> getAllMissions() {
		
		return itimesheetservice.getAllMissions();
	}
    
    @DeleteMapping("/deleteMissionById/{idmission}") 
   	@ResponseBody 
   	public void deletemissionById(@PathVariable("idmission")int idmission)
   	{
    	itimesheetservice.deleteMissionById(idmission);
   	}

    @GetMapping(value = "getmissionById/{idmission}")
    @ResponseBody
	public Mission getmissionById(@PathVariable("idmission") int idmission) {

		return itimesheetservice.getMissionById(idmission);
	}
    
    @PutMapping(value = "/mettreAjourDescriptionByMissionId/{id}/{description}") 
 	@ResponseBody
	public void mettreAjourDescriptionByMissionId(@PathVariable("description") String description, @PathVariable("id") int id) {	
    	itimesheetservice.mettreAjourDescriptionByMissionId(description, id);
		
	}
    
    @GetMapping(value = "findAllMissionBydepartementJPQL/{iddep}")
    @ResponseBody
	public List<Mission> findAllMissionBydepartementJPQL(@PathVariable("iddep") int iddep) {

		return itimesheetservice.findAllMissionBydepartementJPQL(iddep);
	}
    
}