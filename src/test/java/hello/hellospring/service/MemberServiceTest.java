package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberservice ;
    MemoryMemberRepository repository;

    @BeforeEach
    void beforeEach(){
        repository = new MemoryMemberRepository();
        memberservice = new MemberService(repository);
    }


    @AfterEach
    public void afterEach(){
        repository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("hello");
        //when
        Long seveId = memberservice.join(member);

        //then

        Member findMember = memberservice.findOne(seveId).get();
        Assertions.assertEquals(member.getName(), findMember.getName());
    }

    @Test
    void 중복_회원_에외(){
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        //when

        memberservice.join(member1);
        //Assertions.assertThrows(IllegalStateException.class,()-> memberservice.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberservice.join(member2));

        assertEquals(e.getMessage(),"이미 존재하는 회원입니다.");
//        memberservice.join(member1);
//        try{
//            memberservice.join(member2);
//            fail();
//        }catch(IllegalStateException e){
//            assertEquals(e.getMessage(),"이미 존재하는 회원입니다");
//        }
    }

    @Test
    void findMembers() {

        Member member1 = new Member();
        member1.setName("spring1");
        memberservice.join(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        memberservice.join(member2);

        assertEquals(2,memberservice.findMembers().size());
    }

    @Test
    void findOne() {
    }
}