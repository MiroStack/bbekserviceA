package com.bbek.BbekServiceA.repository.information;

import com.bbek.BbekServiceA.entities.church_information.ChurchInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChurchInformationRepo extends JpaRepository<ChurchInformationEntity, Long> {

}
