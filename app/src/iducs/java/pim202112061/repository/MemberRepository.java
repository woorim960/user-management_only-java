package iducs.java.pim202112061.repository;

import iducs.java.pim202112061.domain.Member;

import java.util.List;

public interface MemberRepository<T> {
    // 구현의 방향을 제시, 외부 사용법을 결정
    int create(T member); // 등록
    T readById(T member);  // 정보조회 - id 기준
    T readByEmail(T member); // 정보조회 - email 기준
    List<T> readList(); // 목록 조회
    List<T> readByPhone(T member); // 전화번호로 검색
    List<T> readListByName(String order) throws Exception; // 이름으로 정렬된 멤버들 검색
    List<T> readListByPerPage(int page, int perPage); // 페이지 범위를 지정하여 검색
    int update(T member); // 수정
    int delete(T member); // 탈퇴

    List<T> getMemberList();
    void setMemberList(List<T> memberList);
}
