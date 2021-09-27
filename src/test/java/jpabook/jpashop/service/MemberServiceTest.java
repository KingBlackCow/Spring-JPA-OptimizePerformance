package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;


    @Test
    @Rollback(false)//rollback을 하지않고 commit해버림 join에 있는 save로직에서 em.flush가 나감
    public void 회원가입() throws Exception{
        Member member = new Member();
        member.setName("kim");

        Long id = memberService.join(member);

        assertEquals(member, memberRepository.findOne(id));
    }

    @Test
    public void 중복화원예외() throws Exception{
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        memberService.join(member1);
        try {
            memberService.join(member2);//예외가 발생해야한다.
        }catch (IllegalStateException e){
            return;
        }
        fail("예외가 발생해야 한다.");
    }
}