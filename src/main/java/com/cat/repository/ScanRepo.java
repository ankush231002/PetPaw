package com.cat.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cat.entity.Info;
import com.cat.entity.ScanEvent;

public interface ScanRepo extends JpaRepository<ScanEvent, Long>{

    List<ScanEvent> findByInfoOrderByScanTimeDesc(Info info);
}
