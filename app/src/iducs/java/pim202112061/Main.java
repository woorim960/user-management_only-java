package iducs.java.pim202112061;
// 다른 패키지의 클래스 또는 인터페이스 사용할 수 있게 함
import iducs.java.pim202112061.controller.PimController;

public class Main {
    public static void main(String[] arg) {
        PimController pimController = new PimController();
        pimController.dispatch();
        // Array + List : 배열의 장점, 리스트의 장점을 융합하여 제공
    }
}
