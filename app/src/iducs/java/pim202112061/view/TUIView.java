package iducs.java.pim202112061.view;

import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TUIView { //TUI : Text User Interface, CUI(Character UI)
    Scanner sc = new Scanner(System.in);

    public void showMenu(boolean isLogin, boolean isRoot) {
        if (isLogin == false) { // F & F
            System.out.print("1. 등록\t");
            System.out.print("2. 로그인\n");
        } else {
            if (isRoot == false) { // T & F
                System.out.print("3. 정보조회\t");
                System.out.print("4. 정보수정\t");
                System.out.print("5. 로그아웃\t");
                System.out.print("6. 회원탈퇴\n");
            } else { // 관리자만 종료 가능  T & T
                System.out.print("0. 종료\t");
                System.out.print("3. 정보조회\t");
                System.out.print("4. 정보수정\t");
                System.out.print("5. 로그아웃\t");
                System.out.print("7. 목록조회\t");
                System.out.print("8. 전화번호 검색\t");
                System.out.print("9. 이름 순 정렬하여 조회\t");
                System.out.print("10. 범위 지정 page perCount\n");
            }
        }
    }

    public int inputMenu() {
        int menu = 0;
        do {
            try {
                System.out.println("실행할 기능의 번호를 입력하시오. >");
                menu = this.sc.nextInt();
                if (menu < 0 || menu > 10)
                    System.out.println("해당 메뉴 번호를 입력하시오.");
            } catch (Exception e) {
                System.out.println("숫자 형식을 입력하시오.");
                this.sc.nextLine();
                menu = -1;
            }
        } while (menu < 0 || menu > 10);

        return menu;
    }

    public String inputToString(String entityName) {
        System.out.println(entityName + "를 입력하시오. >");
        return this.sc.next();
    }

    public HashMap<String, String> inputForRegister() throws Exception {
        HashMap<String, String> member = new HashMap<>();
        String regx = "^[A-Za-z0-9+_.-]+@(.+)$"; // 이메일 검증을 위한 정규식 패턴
        Pattern pattern = Pattern.compile(regx); // 위 문자열 형식의 패턴을 실제 정규식으로 컴파일

        System.out.println("이메일을 입력하시오. >");
        String email = this.sc.next();
        Matcher matcher = pattern.matcher(email); // 입력한 이메일이 정규식 패턴과 일치하는지 검증
        if (matcher.matches()) // 정규식과 일치하면 이메일 저장
            member.put("email", email);
        else
            throw new Exception("이메일 양식을 지켜주세요.");

        return this.inputFor(member);
    }

    public HashMap<String, String> inputForUpdate() {
        return this.inputFor(new HashMap<>());
    }

    private HashMap<String, String> inputFor(HashMap<String, String> member) {
        System.out.println("비밀번호를 입력하시오. >");
        member.put("password", this.sc.next());
        System.out.println("이름을 입력하시오. >");
        member.put("name", this.sc.next());
        System.out.println("핸드폰 번호를 입력하시오. >");
        member.put("phone", this.sc.next());
        System.out.println("주소를 입력하시오. >");
        member.put("address", this.sc.next());

        return member;
    }
}

