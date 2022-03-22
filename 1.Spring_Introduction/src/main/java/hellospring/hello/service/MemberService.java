package hellospring.hello.service; //서비스 클래스는 비지니스 의존적으로 설꼐

import hellospring.hello.domain.Member;
import hellospring.hello.repository.MemberRepository;
import hellospring.hello.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    
    //[Spring] 의존성 주입(Dependency Injection, DI라고함)
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     *
     * 회원가입
     */
    public Long join(Member member){
        //같은 이름이 있는 경우 회원X

//        // 1. Optional 로 값 받아서 쓰기
//        Optional<Member> result = memberRepository.findByName(member.getName());
//
//        //[ifPresent] Null이 아니라 어떤 값이 있으면 동작  Optional 이기 때문에 가능  , Null의 가능성이 있으면  Optional로 감싼다.
//        result.ifPresent(m -> {
//            throw new IllegalStateException("이미 존재하는 회원입니다.")
//        });

        //2.
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    //ctrl + alt + shift + t   9번  Extract method 사용
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                        .ifPresent(m-> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
