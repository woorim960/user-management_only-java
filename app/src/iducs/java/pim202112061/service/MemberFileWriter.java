package iducs.java.pim202112061.service;

import iducs.java.pim202112061.domain.Member;

import java.io.*;
import java.util.List;
// JCF(Java Collection Framework) : 집합 객체를 효과적으로 다루기 위한 프레임워크
public class MemberFileWriter<T> { // 요청 객체를 파일에 저장, Generics
    BufferedWriter bw = null; // 버퍼를 활용하여 성능을 향상
    FileWriter fw = null; // File 쓰기를 위한 클래스
    public MemberFileWriter(File f) throws IOException { // IOException 예외 전파
        fw = new FileWriter(f); // FileWriter 클래스 사용시 IOException 발생할 수 있음
    }
    public void saveMember(List<T> memberList) throws IOException {
        for(T member : memberList) { // for each 문장
            try {
                Member m = (Member) member;
                fw.write(m.getId() + "\t");
                fw.write(m.getEmail() + "\t");
                fw.write(m.getPw() + "\t");
                fw.write(m.getName() + "\t");
                fw.write(m.getPhone()+ "\t");
                fw.write(m.getAddress() + "\n");
                fw.flush(); // 연산 적용
            } catch (IOException e) { // 예외 전파하지 않고, 발생한 곳에서 처리
                e.printStackTrace();
            }
        }
        fw.close(); // 자원 반납
    }
}
