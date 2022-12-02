package com.example.member.entity.service;

import com.example.member.entity.MemberEntity;
import com.example.member.entity.dto.MemberDTO;
import com.example.member.entity.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Long save(MemberDTO memberDTO) {
//        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
//        memberRepository.save(memberEntity);
        Long savedId = memberRepository.save(MemberEntity.toSaveEntity(memberDTO)).getId();
        return savedId;
    }

    public MemberDTO login(MemberDTO memberDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if (optionalMemberEntity.isPresent()) {
            MemberEntity memberEntity = optionalMemberEntity.get();
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                return MemberDTO.toDTO(memberEntity);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }


    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList){
//            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
//            memberDTOList.add(memberDTO);
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()){
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
//            return memberDTO;
            return MemberDTO.toDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }


    public MemberDTO findByMemberEmail(String loginEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(loginEmail);
        if (optionalMemberEntity.isPresent()){
            return MemberDTO.toDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity updateEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(updateEntity);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public String dupCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()){
            return "ok";
        } else {
            return "no";
        }
    }
}

        // 스프링 부트에서는 업데이트에도 save를 쓴다

        /*
        eamil로 DB에서 조회를 하고
        사용자가 입력한 비번과 DB에서 조회한 비번이 일치하는지를 판단해서
        로그인 성공, 실패 여부를 리턴.
        단, eamil 조회결과가 없을 때도 실패
        * */




