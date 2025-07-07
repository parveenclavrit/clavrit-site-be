package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.MailRecord;

@Repository
public interface MailRecordRepository extends JpaRepository<MailRecord, Long> {

}
