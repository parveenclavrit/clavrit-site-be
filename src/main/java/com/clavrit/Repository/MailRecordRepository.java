package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.MailRecord;

@Repository
public interface MailRecordRepository extends JpaRepository<MailRecord, Long> {

	@Query("SELECT COUNT(m) > 0 FROM MailRecord m " +
	           "WHERE LOWER(TRIM(m.email)) = LOWER(TRIM(:email)) " +
	           "AND LOWER(TRIM(m.message)) = LOWER(TRIM(:message))")
	    boolean existsByEmailAndMessageIgnoreCaseTrim(@Param("email") String email,
	                                                  @Param("message") String message);
}
