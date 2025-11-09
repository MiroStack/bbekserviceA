package com.bbek.BbekServiceA.repository.history;

import com.bbek.BbekServiceA.entities.history.HistoryLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryLogRepo extends JpaRepository<HistoryLogEntity, Long> {

}
