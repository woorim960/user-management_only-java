package iducs.java.pim202112061.service;

import iducs.java.pim202112061.domain.Member;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List; // java.awt.List 아님
import java.util.Scanner;

public class MemberFileReader<T> { // 지정한 파일을 읽기 -> Domain
    Scanner sc = null;
    public MemberFileReader(File f) throws FileNotFoundException {
        sc = new Scanner(f); // 어휘 구분
    }
    public List<T> readMember() {
        List<T> memberList = new ArrayList<>();
        while(sc.hasNext()) {
            // 한 라인을 읽고, 탭기호로 구분, 배열 저장
            String[] strArr = sc.nextLine().split("\t");
            Member m = new Member();
            m.setId(Integer.parseInt(strArr[0]));
            m.setEmail(strArr[1]);
            m.setPw(strArr[2]);
            m.setName(strArr[3]);
            m.setPhone(strArr[4]);
            m.setAddress(strArr[5]);
            memberList.add((T) m);
        }
        return memberList;
    }
}
