package com.hypertask.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hypertask.app.domain.TMember;
import com.hypertask.app.domain.TMemberRepository;
import com.hypertask.app.service.MemberService;

@Service
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	TMemberRepository tmemberRepository;

	@Override
	public TMember getMemberInfo(String memberId){
		List<TMember> aMember = tmemberRepository.findByMemberId(memberId);
		return aMember.get(0);
	}
}
