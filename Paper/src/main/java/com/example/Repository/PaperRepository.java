package com.example.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Entity.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {
    // You can add custom query methods here if needed
}
