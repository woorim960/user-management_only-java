package iducs.java.pim202112061.service;

import iducs.java.pim202112061.domain.Member;

import java.util.List;

public interface MemberService<T> {
    // 외부적으로 구현 예정과 사용 방법(이름, 매개변수, 리턴)을 결정
    // Generics : 1. 컴파일 시점에 유형 확인으로 신뢰성 향상
    //            2. 여러 개의 클래스를 정의한 효과를 기대할 수 있음
    T login(String email, String pw);
    void logout();
    int postMember(T member); // 등록
    T getMember(T member); // 조회
    int putMember(T member); // 수정
    int deleteMember(T member); // 삭제, 탈퇴
    List<T> getMemberList(); // 목록조회 : 관리자
    List<T> findMemberByPhone(T member); // 전화번호로 검색
    List<T> sortByName(String order); // 이름으로 정렬된 멤버 검색
    List<T> paginateByPerPage(int pageNo, int perPage); // 페이지 범위를 지정하여 검색
    // file 사용으로 필요한 연산
    void readFile(); // 파일을 읽어서 memberList 객체에 저장, 시작시
    void saveFile(); // memberList 객체의 내용을 파일에 저장,
    // 로그아웃 또는 종료
    void applyUpdate(); // saveFile +  readFile : 등록, 수정, 삭제도 호출 가능
}
