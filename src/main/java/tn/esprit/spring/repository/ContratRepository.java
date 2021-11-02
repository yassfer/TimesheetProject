package tn.esprit.spring.repository;





import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import org.springframework.data.repository.query.Param;


public interface ContratRepository extends CrudRepository<Contrat, Integer>{
	@Modifying
	@Transactional
    @Query("DELETE from Contrat")
    public void deleteAllContratJPQL();
	
	
		 @Query("select c from Contrat c where c.employe = :employe" )
	 public Contrat getAllContratByEmploye(@Param("employe")Employe employe);



		@Query("SELECT count(*) FROM Contrat")
	    public int countcontrat();
	    
	
}
