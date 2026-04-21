package com.example.demo.model;

import java.time.LocalDate;

public abstract class SchoolMember implements Displayable {
    protected String memberId;
    protected String name;
    protected LocalDate joinDate;

    public SchoolMember(String memberId, String name) {
        this.memberId = memberId;
        this.name = name;
        this.joinDate = LocalDate.now();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    @Override
    public abstract String displayInfo();
}
