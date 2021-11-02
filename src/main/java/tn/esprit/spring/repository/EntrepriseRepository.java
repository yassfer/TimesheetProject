package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entities.Entreprise;

public interface EntrepriseRepository extends CrudRepository<Entreprise, Integer>  {

	@Query("SELECT count(*) FROM Entreprise")
    public int countentreprise();
	
}
