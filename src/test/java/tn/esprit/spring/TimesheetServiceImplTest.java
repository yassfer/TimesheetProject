package tn.esprit.spring;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.MissionRepository;
import tn.esprit.spring.services.TimesheetServiceImpl;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetServiceImplTest {

	@Autowired
	  private  TimesheetServiceImpl timesheetServiceImpl;
	
	@Autowired
	private MissionRepository missionRepository;
	
	@Autowired
	DepartementRepository deptRepoistory;

	private Mission	mis1;
	
	private Mission mis2 ;
	
	private static String update = "update";
	
	private Departement departement;
	
	@Before
	public void setUp() {
		
		departement =new Departement("dep");
		deptRepoistory.save(departement);
		mis1 = new Mission("test1","test1");
	    mis2 = new Mission("test2","test2");
	    missionRepository.save(mis1);
	    missionRepository.save(mis2);
	    timesheetServiceImpl.affecterMissionADepartement(mis1.getId(),departement.getId());
	}

	@After
	public void tearDown() {	
		missionRepository.deleteAll();
		deptRepoistory.deleteAll();
		
	}
	
	
	@Test
	public void TestajouterMission() {
		Mission	mission = new Mission("nada","nada1");
		int missionadd = timesheetServiceImpl.ajouterMission(mission);
		// timesheetServiceImpl.deleteMissionById(mission.getId());
		assertTrue(mission.getName().equals("nada"));
		assertEquals(missionadd,mission.getId());
		}
	
	
	 @Test
	    public void testmettreAjourDescriptionByMissionId() {
		
		 timesheetServiceImpl.mettreAjourDescriptionByMissionId(update,mis1.getId());
		 Optional<Mission> mis = missionRepository.findById(mis1.getId());
	      assertEquals("update", mis.get().getDescription());
	    }
	
	
	
	 
	 @Test
	    public void TestgetAllMissions()
	    {   
	        //test
	        List<Mission> misList = timesheetServiceImpl.getAllMissions();
	    	assertThat(misList.size()).isEqualTo(2);
	       
	    }
	 
	 @Test
	    public void TestfindAllMissionBydepartementJPQL()
	    {   
	        //test
			 List<Mission>misList = timesheetServiceImpl.findAllMissionBydepartementJPQL(departement.getId());
			 assertThat(misList.size()).isEqualTo(0);
	      
	    } 
	 
	 @Test
	    public void TestgetMissionById()
	    {   
	        //test
		 Optional<Mission>  misList = timesheetServiceImpl.getMissionById(mis1.getId());
	        assertEquals(mis1.getName(), misList.get().getName());
	        
	    }
	     
	 
	 
	 @Test
		public void testdeleteMissionById() {
		 
		  timesheetServiceImpl.deleteMissionById(mis2.getId());
	    //  assertThat(missiondelete).isNull();
		}
	
	 
	}
	