package jpabook.jpashop.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import jpabook.jpashop.domain.Member;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;
	
	@Test
	@Transactional // EntityManager를 통한 데이터 변경은 항상 트랜잭션 안에서 이루어져야 한다.
	@Rollback(false)
	public void 회원가입() throws Exception {
		//given
		Member member = new Member();
		member.setName("memberA");
		
		//when
		Long saveId = memberRepository.save(member);
		Member findMember = memberRepository.findOne(saveId);
		
		//then
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
		
		// 영속성 컨텍스트에서 식별자가 같으면 동일한 것으로 봄
		Assertions.assertThat(findMember).isEqualTo(member); // true
		System.out.println("findMember == member: " + (findMember == member)); // true
		
	}

}
