package chat.psychology_chatbot.controller;

import chat.psychology_chatbot.config.JwtUtil;
import chat.psychology_chatbot.domain.Member;
import chat.psychology_chatbot.domain.PsychologyData;
import chat.psychology_chatbot.repository.PsychologyDataRepository;
import chat.psychology_chatbot.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    @Autowired
    private PsychologyDataRepository psychologyDataRepository;

    public MemberController(MemberService memberService, JwtUtil jwtUtil) {
        this.memberService = memberService;
        this.jwtUtil = jwtUtil;
    }
    //회원가입
    @PostMapping("/api/members")
    public Member createMember(@RequestBody MemberForm form){
        Member member = new Member();
        member.setName(form.getName());
        member.setPassword(form.getPassword());

        memberService.join(member);
        return member;
    }
    @PostMapping("/api/login")
    public ResponseEntity<?> login(@RequestBody LoginForm form){
        try{
            Member member = memberService.login(form.getName(), form.getPassword());

            //로그인 성공 -> 토큰 생성
            final String token = jwtUtil.generateToken(member.getName());
            //생성된 토큰 json 형태 반환
            return ResponseEntity.ok(Map.of("token", token));
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/api/psychology-data")
    public PsychologyData createData(@RequestBody PsychologyData data){
        return psychologyDataRepository.save(data);
    }
    //회원 목록 조회
    @GetMapping("/api/members")
    public List<Member> listMembers(){
        return memberService.findMembers();
    }
}
