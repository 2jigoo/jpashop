package jpabook.jpashop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    
    // 필드 주입보다 생성자 주입을 권장
    // @Autowired
    private final MemberRepository memberRepository;

    // 생성자가 하나면 @Autowired 생략 가능
    // @RequiredArgsConstructor로 대체. final 필드 채워주는 생성자를 만들어 줌
    /* public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    } */


    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }


    // 중복 회원 검사
    // 검증 로직이 있어도, 멀티 쓰레드 상황을 고려해 회원 테이블의 회원명 컬럼에 유니크 제약 조건을 추가하는 것이 안전하다.
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 아이디로 회원 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

}
