package hellospring.hello.service;

import hellospring.hello.domain.Member;
import hellospring.hello.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {
    MemberService memberService;
    MemoryMemberRepository memberRepository;
    
    //레포지토리에 있는 거랑은 다른 객체다.
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    
    //같은 레포지토리에 있는것을 사용하기 위함
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    
    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given  뭔가 주어 졌을때
        Member member = new Member();
        member.setName("spring");
        //when   이거를 실행 했을때
        Long saveId = memberService.join(member);
        //then   결과가 이게 나와야함
        Member findMember = memberService.findOne(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }
    @Test
    public void 중복_회원_예외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*
        트라이 캐치 하기에는 애매해서 종문법을 제공
        try {
            memberService.join(member1);
            fail("실패했습니다.");
        } catch (IllegalStateException e) {
            assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/
        //then
    }


    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}