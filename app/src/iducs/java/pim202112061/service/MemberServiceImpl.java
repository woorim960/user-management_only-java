package iducs.java.pim202112061.service;

import iducs.java.pim202112061.domain.Member;
import iducs.java.pim202112061.repository.MemberRepository;
import iducs.java.pim202112061.repository.MemberRepositoryImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MemberServiceImpl<T> implements MemberService<T> {
    // MemberView memberView = new MemberView();
    MemberRepository<T> memberRepository = null;
    private String memberdb = null;
    // Object temporary = null;

    public MemberServiceImpl(String db) {
        this.memberRepository = new MemberRepositoryImpl<>();
        this.memberdb = db;
    }

    @Override
    public T login(String email, String pw) {
        // T : Generic - 1. 컴파일 시점에 자료형 확인할 수 있음.
        // 2. 여러 유형을 처리하는 하나의 메소드로 처리 가능 : ArrayList<String>, ArrayList<Integer> ...
        T member = (T) new Member();
        ((Member) member).setEmail(email);
        ((Member) member).setPw(pw);
        return this.memberRepository.readByEmail(member);
    }

    @Override
    public void logout() {

    }

    @Override
    public int postMember(T member) throws Exception {
        T existedMember = this.memberRepository.readByEmail(member); // DB에 입력한 이메일을 가진 유저를 가져온다.
        if (existedMember != null) // 유저가 존재한다면 등록하지 않고 에러를 발생시킨다.
            throw new Exception("이미 존재하는 이메일입니다.");
        return this.memberRepository.create(member);
    }

    @Override
    public T getMember(T member) {
        return this.memberRepository.readByEmail(member);
    }

    @Override
    public int putMember(T member) {
        return this.memberRepository.update(member);
    }

    @Override
    public int deleteMember(T member) {
        return this.memberRepository.delete(member);
    }

    @Override
    public List<T> getMemberList() {
        return this.memberRepository.getMemberList();
    }

    @Override
    public List<T> findMemberByPhone(T member) {
        return this.memberRepository.readByPhone(member);
    }

    @Override
    public  List<T> sortByName(String order) {
        try {
            return this.memberRepository.readListByName(order);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<T> paginateByPerPage(int pageNo, int perPage) {
        try {
            return this.memberRepository.readListByPerPage(pageNo, perPage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    @Override
    public void readFile() {
        File file = new File(memberdb);
        if(file.canRead()) {
            try {
                MemberFileReader<T> mfr = new MemberFileReader<>(file);
                this.memberRepository.setMemberList(mfr.readMember()); // 파일 -> memberList
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveFile() { // throws : 예외 전파, throw : 예외 발생
        File file = new File(this.memberdb);
        try  {
            MemberFileWriter<Member> mfw = new MemberFileWriter<>(file);
            mfw.saveMember((List<Member>) this.memberRepository.getMemberList());
        } catch(IOException e) { // 예외를 직접 처리, unchecked exception
            e.printStackTrace();
        }
    }

    @Override
    public void applyUpdate() {
        saveFile();
        readFile();
    }
}
