package com.clavrit.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clavrit.Entity.BusinessStatItem;


@Repository
public interface BusinessStatItemRepository extends JpaRepository<BusinessStatItem, Long> {
}
