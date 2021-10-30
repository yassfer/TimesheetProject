package tn.esprit.spring;
import static org.junit.Assert.*;

import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.TimesheetServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetServiceImplTest {

	@Autowired
	  private  TimesheetServiceImpl timesheetServiceImpl;
	
	
	
	@Test
	public void TestajouterMission() {
		Mission mission = new Mission("test","test");
		int missionadd = timesheetServiceImpl.ajouterMission(mission);
		timesheetServiceImpl.deleteMissionById(mission.getId());
		assertTrue(mission.getName().equals("test"));
		assertEquals(missionadd,mission.getId());
		
		}
	
	
	
	/*@Test
	public void TestaffecterMissionADepartement() {
		 timesheetServiceImpl.affecterMissionADepartement(9,1);
		 Mission mission = timesheetServiceImpl.getMissionById(9);
	      //assertEquals("dep1", mission.getDepartement().getName());
		  //assertNotNull(mission.getDepartement().getId());
		
		}
	
	
	@Test
    public void TestgetAllMissions()
    {   
        //test
        List<Mission> misList = timesheetServiceImpl.getAllMissions();
        //assertEquals(5, misList.size());
        
    }
	
	@Test
    public void TestfindAllMissionByEmployeJPQL()
    {   //test
		Mission misList = (Mission)timesheetServiceImpl.findAllMissionByEmployeJPQL(1);
        //assertNotEquals("Lokesh", misList.getName());
       // assertEquals("test", misList.getDescription());
       
    } 

	@Test
    public void TestfindAllMissionBydepartementJPQL()
    {   
        //test
		Mission misList = (Mission)timesheetServiceImpl.findAllMissionBydepartementJPQL(1);
		//assertEquals("test", misList.getName());
      
    } 
   
	
	@Test
	public void testdeleteMissionById() {
	 
	  timesheetServiceImpl.deleteMissionById(6);
     Mission mission = timesheetServiceImpl.getMissionById(10);
      //assertThat(mission).isNull();
	}
	
	
	@Test
    public void TestgetMissionById()
    {   
        //test
		Mission misList = (Mission)timesheetServiceImpl.getMissionById(9);
       // assertEquals("test", misList.getName());
       // assertEquals("test", misList.getDescription());
    }
     
	    
	 @Test
	    public void testmettreAjourDescriptionByMissionId() {
	        
		 timesheetServiceImpl.mettreAjourDescriptionByMissionId("test4",10);
		 Mission mission = timesheetServiceImpl.getMissionById(10);
	     // assertEquals("test4", mission.getDescription());
	     
	    }*/
	 
	 
	 
	}
	