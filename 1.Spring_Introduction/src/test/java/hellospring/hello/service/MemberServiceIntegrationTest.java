package hellospring.hello.service;

import hellospring.hello.domain.Member;
import hellospring.hello.repository.MemberRepository;
import hellospring.hello.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest //스프링 컨테이너와 테스트를 함께 실행한다.
//테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후 항상 롤백한다. DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.
@Transactional
class MemberServiceIntegrationTest {
    @Autowired  MemberService memberService;
    @Autowired  MemberRepository memberRepository;


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

    }
}