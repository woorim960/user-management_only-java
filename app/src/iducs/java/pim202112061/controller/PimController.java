package iducs.java.pim202112061.controller;

import iducs.java.pim202112061.domain.Member;
import iducs.java.pim202112061.service.MemberService;
import iducs.java.pim202112061.service.MemberServiceImpl;
import iducs.java.pim202112061.view.MemberView;
import iducs.java.pim202112061.view.TUIView;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class PimController {
    // JCF : Java Collection Framework -
    // 집합 객체를 효과적으로 다루기 위한 자료구조, 알고리즘 등을 포함하는 클래스 라이브러리
    // ArrayList, Stack
    public static Map<String, Member> session = new HashMap<>(); // static : 메모리 상주
    public static TUIView tuiView = new TUIView();

    private final String MEMBER_DB = "db202112061.txt"; // 파일명, 디렉터리와 파일명으로 식별 가능함, 변수만 봐도 상수임을 알 수 있도록 대문자로만 이루어진 이름으로 변경
    private Member member = null; // 클래스 내부에서만 접근 가능하도록 private 변수로 변경
    private MemberService<Member> memberService; // 클래스 내부에서만 접근 가능하도록 private 변수로 변경
    private MemberView memberView = null; // 클래스 내부에서만 접근 가능하도록 private 변수로 변경

    public PimController() {
        this.memberService = new MemberServiceImpl<>(MEMBER_DB); // 클래수 내부 변수의 참조를 코드만 봐도 알 수 있도록 this 추가
        this.memberView = new MemberView(); // 클래수 내부 변수의 참조를 코드만 봐도 알 수 있도록 this 추가
    }

    public void dispatch() { // 가져오기 : 메뉴보이기, 선택한 메뉴 처리하기, 결과 반환 반복
        boolean isLogin = false; // 지역변수는 선언된 블록이 종료되면 메모리에서 사라짐
        boolean isRoot = false; // 관리자인지를 검증할 불리언 변수

        Scanner sc = new Scanner(System.in); // 키보드 입력을 받아서 분석 반환
        this.memberService.readFile(); //파일로 부터 사용자 목록 정보 읽기

        int menu = 0;
        do {
            Member sessionMember = (Member) session.get("member");
            if(sessionMember != null) {
                isLogin = true; // 로그인 표시
                if(sessionMember.getEmail().contains("admin"))
                    isRoot = true; // 관리자 표시
            } else {
                isLogin = false;
                isRoot = false;
            }

            String msg = "";
            tuiView.showMenu(isLogin, isRoot);
            menu = sc.nextInt(); // 숫자 입력 후 엔터키
            switch(menu) {
                case 0: msg = "종료";
                    memberService.saveFile(); // memberdb.txt 에 저장
                    break;
                case 1: msg = "등록";
                    member = new Member();
                    member.setId(sc.nextLong()); // Long
                    member.setEmail(sc.next()); // String
                    member.setPw(sc.next());
                    member.setName(sc.next());
                    member.setPhone(sc.next());
                    member.setAddress(sc.next());
                    memberService.postMember(member);
                    memberView.printOne(member);
                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                case 2: msg = "로그인";
                    String id = sc.next();
                    String pw = sc.next();
                    member = (Member) memberService.login(id, pw);
                    if(member != null) {
                        isLogin = true;
                        if(member.getEmail().contains("admin"))
                            isRoot = true;
                        session.put("member", member);
                        memberView.printMsg(msg + "를 성공했습니다.");
                    }
                    else
                        memberView.printMsg("로그인 정보 확인 바랍니다. "); // View 전달
                    break;
                case 3: msg = "정보조회";
                    // printOne : 하나의 member 정보 출력
                    memberView.printOne(memberService.getMember(
                            (Member) session.get("member")));
                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                case 4: msg = "정보수정";
                    member = new Member();
                    member.setId(sessionMember.getId()); // id 변경 불가(같은 값으로 설정)
                    member.setEmail(sessionMember.getEmail()); // email 변경 불가
                    member.setPw(sc.next());
                    member.setName(sc.next());
                    member.setPhone(sc.next());
                    member.setAddress(sc.next());
                    if(memberService.putMember(member) > 0) {
                        memberView.printOne(member);
                        memberView.printMsg(msg + "를 성공했습니다.");
                    }
                    else
                        System.out.println("수정에 실패하였습니다. ");
                    break;
                case 5: msg = "로그아웃";
                    memberService.saveFile();
                    memberService.readFile();
                    if(session.get("member") != null) {
                        session.remove("member");
                    }
                    memberView.printMsg(msg + "를 성공했습니다.");
                break;
                case 6: msg = "회원탈퇴";
                    member = new Member();
                    memberService.deleteMember(member);
                    System.out.println("탈퇴가 되었습니다. ");
                    //    System.out.println("탈퇴에 실패하였습니다. ");
                    break;
                case 7: msg = "회원목록조회";
                    memberView.printList(memberService.getMemberList());
                    memberView.printMsg(msg + "를 성공했습니다.");
                    break;
                default:
                    msg = "입력 코드 확인 :"; break;
            }
        } while(menu != 0);
    }
}
