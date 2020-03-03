package cz.gov.monitor.mfcr.dao;

import cz.gov.monitor.mfcr.model.Organization;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

/**
 * Created by Attila Cseh on 25/01/2020.
 */
@Service
public interface OrganizationDao extends CrudRepository<Organization, Long> {

    @Query("Select o from Organization o where ico = :ico")
    public Organization findOrganizationByIco(@Param("ico") String ico);

    @Query("Select o from Organization o where id = :query")
    public Organization findOrganizationByQuery(@Param("query") String query);
}
