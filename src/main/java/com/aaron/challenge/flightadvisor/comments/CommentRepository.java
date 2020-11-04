package com.aaron.challenge.flightadvisor.comments;

import org.springframework.data.jpa.repository.JpaRepository;

interface CommentRepository extends JpaRepository<Comment, String> {

}
