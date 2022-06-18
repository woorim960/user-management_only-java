package iducs.java.pim202112061.view;

public class TUIView { //TUI : Text User Interface, CUI(Character UI)
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
                System.out.print("8. 전화번로 검색\t");
                System.out.print("9. 이름 내림차순 정렬\n");
            }
        }
    }
}
