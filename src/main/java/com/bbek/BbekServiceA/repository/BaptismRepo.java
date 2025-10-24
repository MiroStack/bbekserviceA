package com.bbek.BbekServiceA.repository;

import com.bbek.BbekServiceA.entities.BaptismEntity;
import com.bbek.BbekServiceA.entities.modified.baptism.ModifiedBaptismEntity;
import com.bbek.BbekServiceA.entities.modified.event.ModifiedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BaptismRepo extends JpaRepository<BaptismEntity, Long> {
    @Query(value = " SELECT " +
            "bap.id, " +
            "bap.profile_id, " +
            "bap.testimony, " +
            "bap.created_dt, " +
            "bap.preferred_dt, " +
            "bap.status_id, " +
            "bap.baptism_dt, " +
            "bap.scheduled_dt, " +
            "bap.baptism_officiant, " +
            "bap.baptism_officiant_id, " +
            "bap.location, " +
            "bap.certificate_status, " +
            "up.firstname, " +
            "up.middlename, " +
            "up.lastname, " +
            "up.age, " +
            "up.birthdate, " +
            "up.address, " +
            "up.email, " +
            "up.contact_no, " +
            "up.emergency_contact_person, " +
            "up.emergency_contact_no, " +
            "up.gender, " +
            "up.profile_image, " +
            "up.image_uuid, " +
            "up.role_id, " +
            " COUNT(*) OVER() AS total_rows   \n" +
            "             FROM bbek.baptism as bap \n" +
            "             INNER JOIN user_profile as up ON bap.profile_id = up.id \n" +
            "             WHERE testimony LIKE :query  \n" +
            "             OR bap.baptism_officiant LIKE :query \n" +
            "             OR up.firstname LIKE :query \n" +
            "             OR up.middlename LIKE :query \n" +
            "             OR up.lastname LIKE :query \n" +
            "             ORDER BY bap.id DESC   \n" +
            "             LIMIT 10  \n" +
            "             OFFSET :numberOfRowsToSkip", nativeQuery = true)
    List<ModifiedBaptismEntity> paginatedBaptism(@Param("query") String query, @Param("numberOfRowsToSkip") int numberOfRowsToSkip);
    BaptismEntity findByProfileId(Long id);
}
