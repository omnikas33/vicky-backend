package com.lsit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lsit.entity.CessEntry;

public interface CessEntryRepository extends JpaRepository<CessEntry, String> {

}
