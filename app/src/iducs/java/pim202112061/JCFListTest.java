package iducs.java.pim202112061;

import java.util.ArrayList;
import java.util.HashMap;

public class JCFListTest {
    public static void main(String[] args) {
        HashMap<String, Object> session = new HashMap<>(); // 키와 값으로 구성된 요소를 다룸
        session.put("member", new String("member")); // Member 객체 저장
        session.put("number", Integer.valueOf(1234));
        System.out.println(session.get("number")); // Key 접근
        // 동적 크기, 인덱스 접근, 반복자 접근
        ArrayList<String> strArr = new ArrayList<>(); // Array, List 장점 제공하는 클래스
        strArr.add("인덕대학교");
        strArr.add("컴퓨터소프트웨어학과");
        strArr.add("유응구");
        for(String s : strArr) // 순차접근 : for each 반복 처리
            System.out.println(s);
        for(int i = strArr.size() - 1; i >= 0; i--) // 순차, 역으로
            System.out.println(strArr.get(i)); // 인덱스
    }
}
