package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clavrit.Entity.MailRecord;

public interface MailRecordRepository extends JpaRepository<MailRecord, Long> {

}
