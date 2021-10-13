package tn.esprit.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Entreprise;

public interface EntrepriseRepository extends CrudRepository<Entreprise, Integer>  {

	//Dhekra
	 @Query("SELECT name FROM Entreprise")
	    public List<String> entrepriseNames();
	    
	
	 @Query("SELECT count(*) FROM Entreprise")
	    public int countemp();
		
	 
	 @Modifying
	    @Transactional
	    @Query("UPDATE Entreprise e SET e.name=:name1 where e.id=:id")
	    public void mettreAjourNameByEntrepriseIdJPQL(@Param("name1")String name,@Param("id")int id);


	 @Modifying
	    @Transactional
	    @Query("DELETE from Entreprise")
	    public void deleteAllEntrepriseJPQL();
	
	
}
