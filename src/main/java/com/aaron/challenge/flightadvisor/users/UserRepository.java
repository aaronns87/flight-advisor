package com.aaron.challenge.flightadvisor.users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);
}