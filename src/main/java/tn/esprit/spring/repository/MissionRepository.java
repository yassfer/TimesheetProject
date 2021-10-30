package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Mission;


public interface MissionRepository extends CrudRepository<Mission,Integer> {
	
	@Modifying
    @Transactional
    @Query("UPDATE Mission m SET m.description=:description where m.id=:misId ")
    public void mettreAjourDescriptionByMissionId(@Param("description")String description, @Param("misId")int misId);

	@Query("select m.name from Mission m,Departement d where m.id=d.id and d.id=:iddep")
	public List<Mission> findAllMissionBydepartementJPQL(@Param("iddep")int iddep);
	
	
}
