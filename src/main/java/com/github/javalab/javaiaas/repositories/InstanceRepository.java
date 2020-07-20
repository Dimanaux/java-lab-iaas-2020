package com.github.javalab.javaiaas.repositories;

import com.github.javalab.javaiaas.models.Instance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface InstanceRepository extends JpaRepository<Instance, Long> {

    @Query("select max(i.instanceId) from Instance i")
    Long findMaxId();


    @Transactional
    @Modifying
    @Query("update  Instance i  set i.status=:status where i.instanceId=:id")
    void startInstance(@Param("status") String status, @Param("id") Long id);

    @Transactional
    @Modifying
    @Query("update  Instance i  set i.status=:status where i.instanceId=:id")
    void stopInstance(@Param("status") String status, @Param("id") Long id);

    @Transactional
//    @Query("from  Instance i where i.user=:id")
    List<Instance> findAllByUserId(@Param("id") Long id);

}
