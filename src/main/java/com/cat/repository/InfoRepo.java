package com.cat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.cat.entity.Info;
import com.cat.entity.User;

public interface InfoRepo extends JpaRepository<Info, Long> {
    
    // Optional<Info> findByPetName(String petName);

    List<Info> findByUser(User user);

    Optional<Info> findByPublicUrl(String publicUrl);

}
