package tn.esprit.spring.service.test;

import org.junit.Test;
import java.util.ArrayList;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.entities.TimesheetPK;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.TimesheetServiceImpl;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import tn.esprit.spring.services.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetServiceImplTest {

	
	private static final Logger l = LogManager.getLogger(TimesheetServiceImplTest.class);
	@Autowired 
	TimesheetServiceImpl timesheetServiceImpl ;
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	MissionRepository missionRepository;
	@Autowired
	IEmployeService iEmployeService;
	@Autowired
	ITimesheetService iTimesheetService;
	@Autowired
	TimesheetRepository timesheetRepository;
	
	
	@Test
	public void ajouterTimesheetTest() throws ParseException {
		int idemploye=1;
		int idmission=1;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		TimesheetPK tpk=timesheetServiceImpl.ajouterTimesheet(idemploye, idmission,simpleDateFormat.parse("10/10/2021"),simpleDateFormat.parse("15/10/2021"));
		
		assertEquals(tpk.getIdEmploye(),idemploye);
	}
	
	@Test
	public void getTimesheetsByMissionAndDate() {
	 
	Employe e = employeRepository.findById(1).get();
	Mission m = missionRepository.findById(1).get();
	Date dateDebut = parseDate("2020-02-14");
	Date dateFin = parseDate("2021-02-14");
    List<Timesheet> t = iEmployeService.getTimesheetsByMissionAndDate(e, m, dateDebut, dateFin) ;
    assertNotNull(t);
			
 }
 
	 public static Date parseDate(String date) {
	     try {
	         return new SimpleDateFormat("yyyy-MM-dd").parse(date);
	     } catch (ParseException e) {
	         return null;
	     }
	  }
	/*@Test
	public void validerTimesheetTest() throws ParseException
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		int idemploye=1;
		int idmission=1;
		Date datedebut= simpleDateFormat.parse("2009/10/04");
		Date datefin=simpleDateFormat.parse("2010/10/07");
		
	}
	
	
	 
	 
	 	
	 @Test 
	 public void TestvaliderTimesheet() {
	 	//(1,3,dd,df,3) et (1, 3, dd, df)le validateur qui est un ingenieur doit etre chef de departement pour valider une feuille de temps 
	 	//(1,3,dd,df,2)et (1, 3, dd, df)le validateur qui est chef de departement mais l'employe doit etre chef de departement de la mission en question
	 	Date dd = parseDate("2020-01-14");
	 	Date df = parseDate("2023-10-07");
	 	iTimesheetService.validerTimesheet(1,1,dd,df,2);
	 	TimesheetPK p =new TimesheetPK(1,1, dd, df);
	 	assertNotNull(timesheetRepository.findBytimesheetPK(p));
	 	
	 }
	 */
	
	 
	 
}
