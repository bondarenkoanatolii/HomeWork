package com.hillel.anatoliibondarenko.homework13;


public class ClassTest3 {

    @Test(priority = 3)
    public void Test01() {
        System.out.println("Test01() completed.");
    }

    @Test(priority = 3)
    public void Test02() {
        System.out.println("Test02() completed.");
    }

    @Test(priority = 4)
    public void Test03() {
        System.out.println("Test03() completed.");
    }

    @Test
    public void Test04() {
        System.out.println("Test04() completed.");
    }

    @Test(priority = 2)
    public void Test05() {
        System.out.println("Test05() completed.");
    }

    @Test(priority = 1)
    public void Test06() {
        System.out.println("Test06() completed.");
    }

    @Test(priority = 4)
    public void Test07() {
        System.out.println("Test07() completed.");
    }

    @AfterSuite
    public void Test09() {
        System.out.println("Test09() has annotation @AfterSuite completed.");
    }

    @Test(priority = 10)
    public void Test10() {
        System.out.println("Test10() completed.");
    }

    @Test
    public void Test11() {
        System.out.println("Test11() completed.");
    }

    @Test(priority = 6)
    public void Test12() {
        System.out.println("Test12() completed.");
    }

}
