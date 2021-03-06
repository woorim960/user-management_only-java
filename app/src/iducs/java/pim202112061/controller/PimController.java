package iducs.java.pim202112061.controller;

import iducs.java.pim202112061.domain.Member;
import iducs.java.pim202112061.service.MemberService;
import iducs.java.pim202112061.service.MemberServiceImpl;
import iducs.java.pim202112061.view.MemberView;
import iducs.java.pim202112061.view.TUIView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PimController {
    // JCF : Java Collection Framework -
    // 집합 객체를 효과적으로 다루기 위한 자료구조, 알고리즘 등을 포함하는 클래스 라이브러리
    // ArrayList, Stack
    public static Map<String, Member> session = new HashMap<>(); // static : 메모리 상주
    public static TUIView tuiView = new TUIView();

    private final String MEMBER_DB = "db202112061.txt"; // 파일명, 디렉터리와 파일명으로 식별 가능함, 상수임을 변수명만 봐도 알 수 있도록 대문자로만 구성된 이름으로 변경
    private Member member = null; // 클래스 내에서만 접근할 수 있도록 private 키워드 추가
    private MemberService<Member> memberService; // 클래스 내에서만 접근할 수 있도록 private 키워드 추가
    private MemberView memberView = null; // 클래스 내에서만 접근할 수 있도록 private 키워드 추가

    public PimController() {
        this.memberService = new MemberServiceImpl<>(MEMBER_DB); // 클래스 내부 멤버 변수를 참조한다는 의미를 코드만 봐도 알 수 있도록 this 키워드 추가
        this.memberView = new MemberView(); // 클래스 내부 멤버 변수를 참조한다는 의미를 코드만 봐도 알 수 있도록 this 키워드 추가
    }

    public void dispatch() {
        this.memberService.readFile(); // 파일로 부터 사용자 목록 정보 읽기, 클래스 내부 멤버 변수를 참조한다는 의미를 코드만 봐도 알 수 있도록 this 키워드 추가
        this.start(); // 프로그램 시작
    }

    private void start() { // 가져오기 : 메뉴보이기, 선택한 메뉴 처리하기, 결과 반환 반복
        boolean isLogin = false; // 지역변수는 선언된 블록이 종료되면 메모리에서 사라짐
        boolean isRoot = false;

        Scanner sc = new Scanner(System.in); // 키보드 입력을 받아서 분석 반환

        int menu = 0;
        do {
            Member sessionMember = (Member) this.session.get("member");
            isLogin = this.isLogined(sessionMember); // 로그인 여부 검증
            isRoot = this.isRoot(sessionMember); // 관리자 여부 검증

            this.tuiView.showMenu(isLogin, isRoot);

            menu = this.tuiView.inputMenu();
            switch(menu) {
                case 0: // 종료;
                    this.memberService.saveFile(); // memberdb.txt 에 저장
                    break;
                case 1: // 등록;
                    try {
                        // 잘못된 이메일 입력 시 에러가 발생되어 catch 구문으로 넘어간다.
                        this.member = this.createMember(this.tuiView.inputForRegister()); // 멤버 등록 메서드 실행
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        break;
                    }

                    try {
                        // 멤버 등록 중 이메일이 중복된게 존재하면 에러가 발생하여 catch 구문으로 넘어간다.
                        this.memberService.postMember(this.member);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        continue;
                    }

                    this.memberView.printHeader();
                    this.memberView.printOne(this.member);
                    this.memberView.printMsg("회원 등록에 성공했습니다.");
                    this.memberService.saveFile(); // memberdb.txt 에 저장
                    break;
                case 2: // 로그인;
                    String email = this.tuiView.inputToString("email");
                    String password = this.tuiView.inputToString("password");
                    this.member = (Member) this.memberService.login(email, password);
                    isLogin = this.isLogined(this.member); // 로그인 여부 검증
                    // isRoot = this.isRoot(this.member); // 관리자 여부 검증

                    if (isLogin) {
                        // 로그인이 됐으면 로그인 정보를 세션에 저장한다.
                        this.session.put("member", this.member);
                        this.memberView.printMsg("로그인이 성공했습니다.");
                    } else
                        this.memberView.printMsg("로그인 정보 확인 바랍니다. "); // View 전달
                    break;
                case 3: // 정보 조회;
                    this.memberView.printHeader();
                    // printOne : 하나의 member 정보 출력
                    this.memberView.printOne(this.memberService.getMember(
                            (Member) this.session.get("member")));
                    this.memberView.printMsg("정보조회를 성공했습니다.");
                    break;
                case 4: // 정보수정;
                    this.member = this.updateMember(this.tuiView.inputForUpdate());

                    if (this.memberService.putMember(this.member) > 0) {
                        this.memberView.printHeader();
                        this.memberView.printOne(this.member);
                        this.memberView.printMsg("정보수정을 성공했습니다.");
                        this.memberService.saveFile(); // memberdb.txt 에 저장
                    } else
                        System.out.println("수정에 실패하였습니다.");
                    break;
                case 5: // 로그아웃;
                    this.memberService.saveFile();
                    this.memberService.readFile();
                    if(this.session.get("member") != null) {
                        this.session.remove("member");
                    }
                    this.memberView.printMsg("로그아웃을 성공했습니다.");
                    break;
                case 6: // 회원탈퇴;
                    int deletedFlag = this.memberService.deleteMember(sessionMember);
                    if (deletedFlag > 0) {
                        System.out.println("탈퇴가 되었습니다. ");
                        this.memberService.saveFile(); // memberdb.txt 에 저장

                        if(this.session.get("member") != null) {
                            // 로그아웃
                            this.session.remove("member");
                        }
                    }
                    else System.out.println("탈퇴에 실패하였습니다. ");
                    break;
                case 7: // 회원목록조회;
                    this.memberView.printList(this.memberService.getMemberList());
                    this.memberView.printMsg("회원 목록 조회를 성공했습니다.");
                    break;
                case 8: // 전화번호 검색;
                    Member member = new Member();
                    member.setPhone(sc.next());
                    List<Member> memberList = this.memberService.findMemberByPhone(member);
                    this.memberView.printList(memberList);
                    break;
                case 9: // 이름 내림차순 정렬;
                    String order = this.tuiView.inputToString("order");
                    memberList = this.memberService.sortByName(order);
                    if (memberList != null)
                        this.memberView.printList(memberList);
                    break;
                case 10: // 범위 지정 page perCount;
                    int pageNo = sc.nextInt();
                    int perCount = sc.nextInt();
                    memberList = this.memberService.paginateByPerPage(pageNo, perCount);
                    if (memberList != null)
                        this.memberView.printList(memberList);
                    break;
                default: // "입력 코드 확인 :";
                    break;
            }
        } while(menu != 0);
    }

    /**
     * isLogined 로그인 여부 검증 함수
     *
     * @param sessionMember 현재 로그인 중인 멤버
     * @return 로그인 여부
     */
    private boolean isLogined(Member sessionMember) {
        return sessionMember != null ? true : false;
    }

    /**
     * isRoot 관리자 여부 검증 함수
     *
     * @param sessionMember 현재 로그인 중인 멤버
     * @return 관리자 여부
     */
    private boolean isRoot(Member sessionMember) {
        return (sessionMember != null) && sessionMember.getEmail().contains("admin") ? true : false;
    }

    /**
     * createMember 입력한 값들로 멤버를 생성하여 반환한다.
     *
     * @param inputtedMember 플레이어가 직접 입력한 회원 정보
     * @return 생성된 멤버
     */
    private Member createMember(HashMap<String, String> inputtedMember) {
        Member member = new Member();

//        member.setId(sc.nextLong()); // 아이디는 기존 DB의 최댓 값의 +1 값이 자동 저장되므로 세팅하지 않는다.
        member.setEmail(inputtedMember.get("email")); // String
        member.setPw(inputtedMember.get("password"));
        member.setName(inputtedMember.get("name"));
        member.setPhone(inputtedMember.get("phone"));
        member.setAddress(inputtedMember.get("address"));

        return member;
    }

    /**
     * updateMember 입력한 값들로 멤버를 수정하여 반환한다.
     *
     * @param inputtedMember 플레이어가 직접 입력한 회원 정보
     * @return 생성된 멤버(실제로는 멤버를 생성하여 반환하지만, 결국 DB에 저장할 때는 같은 ID를 가진 데이터가 이미 있으므로 해당 값의 내용을 변경하게 된다)
     */
    private Member updateMember(HashMap<String, String> inputtedMember) {
        Member member = new Member();
        Member sessionMember = ((Member) this.session.get("member"));

        member.setId(sessionMember.getId()); // id 변경 불가(같은 값으로 설정)
        member.setEmail(sessionMember.getEmail()); // email 변경 불가
        member.setPw(inputtedMember.get("password"));
        member.setName(inputtedMember.get("name"));
        member.setPhone(inputtedMember.get("phone"));
        member.setAddress(inputtedMember.get("address"));

        return member;
    }
}
