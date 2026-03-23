package ch04.school;

import java.time.LocalDate;

/**
 * SchoolMember 抽象類別：學校所有成員的共同父類別。
 * UML: abstract class SchoolMember implements Displayable
 *      -memberId, -name, #joinDate
 */
public abstract class SchoolMember implements Displayable {
    private String memberId;
    private String name;
    protected LocalDate joinDate;

    public SchoolMember(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.joinDate = LocalDate.now();
    }

    public String getMemberId() { return memberId; }
    public String getName() { return name; }
    public LocalDate getJoinDate() { return joinDate; }

    // 子類別必須實作 displayInfo()（來自 Displayable 介面）
    @Override
    public abstract void displayInfo();
}
