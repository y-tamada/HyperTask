package com.hypertask.app.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TMemberRepository extends JpaRepository<TMember, Integer> {
	List<TMember> findByMemberId(String memberId);
}
