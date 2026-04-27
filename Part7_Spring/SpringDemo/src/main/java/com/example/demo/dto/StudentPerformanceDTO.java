package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 學業表現報告 DTO
 * 展示非對稱映射與複雜運算結果
 */
public class StudentPerformanceDTO {
    @JsonProperty("studentId")
    private String studentId;
    
    @JsonProperty("maskedName")
    private String maskedName;          // 脫敏後的姓名 (如：王*明)
    
    @JsonProperty("weightedGpa")
    private double weightedGpa;         // 加權平均分
    
    @JsonProperty("totalEarnedCredits")
    private int totalEarnedCredits;     // 總取得學分
    
    @JsonProperty("academicStanding")
    private String academicStanding;    // 學業狀態 (優等/及格/預警)
    
    @JsonProperty("graduationProgress")
    private double graduationProgress;  // 畢業進度百分比
    
    @JsonProperty("isAtRisk")
    private boolean isAtRisk;           // 是否有核心課程不及格
    
    @JsonProperty("failedCourseNames")
    private List<String> failedCourseNames; // 不及格課程清單

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getMaskedName() { return maskedName; }
    public void setMaskedName(String maskedName) { this.maskedName = maskedName; }

    public double getWeightedGpa() { return weightedGpa; }
    public void setWeightedGpa(double weightedGpa) { this.weightedGpa = weightedGpa; }

    public int getTotalEarnedCredits() { return totalEarnedCredits; }
    public void setTotalEarnedCredits(int totalEarnedCredits) { this.totalEarnedCredits = totalEarnedCredits; }

    public String getAcademicStanding() { return academicStanding; }
    public void setAcademicStanding(String academicStanding) { this.academicStanding = academicStanding; }

    public double getGraduationProgress() { return graduationProgress; }
    public void setGraduationProgress(double graduationProgress) { this.graduationProgress = graduationProgress; }

    public boolean isAtRisk() { return isAtRisk; }
    public void setAtRisk(boolean isAtRisk) { this.isAtRisk = isAtRisk; }

    public List<String> getFailedCourseNames() { return failedCourseNames; }
    public void setFailedCourseNames(List<String> failedCourseNames) { this.failedCourseNames = failedCourseNames; }
}
