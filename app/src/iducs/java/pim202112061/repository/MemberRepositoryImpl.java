package iducs.java.pim202112061.repository;

import iducs.java.pim202112061.domain.Member;

import java.util.*;
import java.util.stream.Collectors;

public class MemberRepositoryImpl<T> implements MemberRepository<T> {
    // <> : Generic (제너릭) 1. 컴파일 시점에 유형을 확인 2. 사용시 형변환을 줄여줌
    // 파일 또는 데이터베이스를 접근하여 데이터를 처리함(Data Access : create, read, update, delete ...)
    public static long memberId = 1;
    Member memberDTO = null;
    public List<T> memberList = null;
    public MemberRepositoryImpl() {
        // Array 배열 : (정적인 크기를 가진) 동일한 자료형을 인덱스를 활용하여 접근하는 객체
        memberList = new ArrayList<T>(); // Array + List : (동적 - 늘어남) 배열과 리스트 장점
    }
    @Override
    public int create(T member) {
        int ret = 0; // 실패
        try {
            ((Member) member).setId(memberList.size()); // 아이디 번호 자동 증가 기능 (AUTO_INCREMENT)
            memberList.add((T) member); // 형변환
            ret = 1; // 성공
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return ret;
    }

    @Override
    public T readById(T member) {
        return null;
    }

    @Override
    public T readByEmail(T member) {
        for(T m : memberList) { // memberList 객체에 존재하는지 확인
            if (((Member) m).getEmail().equals(((Member) member).getEmail())
                    && ((Member) m).getPw().equals(((Member) member).getPw()))
                return m;
        }
        return null;
    }

    @Override
    public List<T> readByPhone(T member) {
        return this.memberList.stream()
                .filter(m -> ((Member) m).getPhone().equals(((Member) member).getPhone()))
                .collect(Collectors.toList());
    }

    @Override
    public  List<T> readListByName(String order) throws Exception {
        order.toLowerCase(); // 모두 소문자로 변경

        switch (order) {
            case "desc": // 내림차순
                this.memberList.sort((m1, m2) -> ((Member) m2).getName().compareTo(((Member) m1).getName())); // 내부 요소가 제네릭 타입이므로 '제네릭 배열'.sort(정렬 방식) 으로 해주어야 정렬 가능
                return this.memberList;
            case "asc": // 오름차순
                this.memberList.sort((m1, m2) -> ((Member) m1).getName().compareTo(((Member) m2).getName())); // 내부 요소가 제네릭 타입이므로 '제네릭 배열'.sort(정렬 방식) 으로 해주어야 정렬 가능
                return this.memberList;
            default:
                throw new Exception("desc 혹은 asc 중에서 입력해 주십시오.");
        }
    }

    @Override
    public List<T> readListByPerPage(int page, int perPage) throws Exception {
        int startId = (page - 1) * perPage;
        int endId = startId + perPage;
        endId = endId <= this.memberList.size() ? endId : this.memberList.size();

        try {
            return this.memberList.subList(startId, endId);
        } catch(Exception e) {
            throw new Exception("해당 페이지는 비어있습니다.");
        }

    }

    @Override
    public int update(T member) {
        int ret = 0; // 실패
        int idx = 0;
        for(T m : memberList) {
            if(((Member) m).getEmail().equals(((Member) member).getEmail())) {
                memberList.set(idx, member);
                ret++;
            }
            idx++;
        }
        return ret;
    }

    @Override
    public int delete(T member) {
        return 0;
    }

    @Override
    public List<T> getMemberList() {
        // 아이디 순으로 조회
        this.memberList.sort((m1, m2) -> (int) ((Member) m1).getId() - (int) ((Member) m2).getId()); // 내부 요소가 제네릭 타입이므로 '제네릭 배열'.sort(정렬 방식) 으로 해주어야 정렬 가능
        return this.memberList;
    }

    @Override
    public void setMemberList(List<T> memberList) {
        this.memberList = memberList;
    }
}
