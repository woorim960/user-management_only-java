package iducs.java.pim202112061.repository;

import iducs.java.pim202112061.domain.Member;

import java.util.ArrayList;
import java.util.List;

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
    public List<T> readList() {
        return memberList;
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
        return this.memberList;
    }

    @Override
    public void setMemberList(List<T> memberList) {
        this.memberList = memberList;
    }
}
