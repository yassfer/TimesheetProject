package tn.esprit.spring.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.hibernate.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.services.TimesheetServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MissionServiceImplTest {

	@Autowired
	  private  TimesheetServiceImpl timesheetServiceImpl;
	
	
	
	@Test
	public void TestajouterMission() {
		Mission mission = new Mission("test","test");
		int missionadd = timesheetServiceImpl.ajouterMission(mission);
		assertTrue(mission.getName().equals("test"));
		assertEquals(missionadd,mission.getId());
		timesheetServiceImpl.deleteMissionById(mission.getId());
		
		}
	
	
	
	@Test
	public void TestaffecterMissionADepartement() {
		 timesheetServiceImpl.affecterMissionADepartement(6,1);
		 Mission mission = timesheetServiceImpl.getMissionById(6);
	      assertEquals("dep1", mission.getDepartement().getName());
		  assertNotNull(mission.getDepartement().getId());
		
		}
	
	
	@Test
    public void TestfindAllMissionBydepartementJPQL()
    {   
        //test
		Mission misList = (Mission)timesheetServiceImpl.findAllMissionBydepartementJPQL(1);
		assertEquals("test", misList.getName());
      
       
    }
	
	@Test
    public void TestgetAllMissions()
    {   System.out.println("Running testList...");
        //test
        List<Mission> misList = timesheetServiceImpl.getAllMissions();
        assertEquals(7, misList.size());
        
    }
	
	@Test
    public void TestfindAllMissionByEmployeJPQL()
    {   
        //test
		Mission misList = (Mission)timesheetServiceImpl.findAllMissionByEmployeJPQL(1);
        assertNotEquals("Lokesh", misList.getName());
        assertEquals("test", misList.getDescription());
       
    }
	
	@Test
    public void TestgetMissionById()
    {   
        //test
		Mission misList = (Mission)timesheetServiceImpl.getMissionById(6);
        assertEquals("test", misList.getName());
        assertEquals("test", misList.getDescription());
    }
     
	
	@Test
	public void testdeleteMissionById() {
	    System.out.println("Running testDelete...");
	    timesheetServiceImpl.deleteMissionById(8);
       Mission mission = timesheetServiceImpl.getMissionById(5);
        assertThat(mission).isNull();
   
	}
	    
	 @Test
	    public void testmettreAjourDescriptionByMissionId() {
	        
		 timesheetServiceImpl.mettreAjourDescriptionByMissionId("test4",6);
		 Mission mission = timesheetServiceImpl.getMissionById(6);
	      assertEquals("test4", mission.getDescription());
	     
	    }
	 
	 
	 
	}
	
	
