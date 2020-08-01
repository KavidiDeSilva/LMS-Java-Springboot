

package com.nhdasystem.dao;


        import com.nhdasystem.entity.Userstatus;
        import org.springframework.data.domain.Page;
        import org.springframework.data.domain.Pageable;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;

        import java.util.List;


public interface UserstatusDao extends JpaRepository<Userstatus, Integer>
{

    @Query(value="SELECT new Userstatus(u.id,u.name) FROM Userstatus u")
      List<Userstatus> list();


    @Query("SELECT u FROM Userstatus u ORDER BY u.id DESC")
    Page<Userstatus> findAll(Pageable of);


    @Query("SELECT u FROM Userstatus u WHERE u.name= :name")
    Userstatus findByName(@Param("name") String name);
}