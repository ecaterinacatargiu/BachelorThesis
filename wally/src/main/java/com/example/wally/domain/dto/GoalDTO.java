package com.example.wally.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.Objects;

public class GoalDTO extends BaseEntityDTO {


    private Long clientID;
    private String goalName;
    private Double price;
    private Boolean type;
    private String category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date startLine;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date deadline;
    private Boolean accomplished;

    public GoalDTO(String goalName, Double price, Date startLine, Date deadline, Boolean accomplished)
    {
        this.goalName = goalName;
        this.price = price;
        this.type = true;
        this.category = "Wishlist";
        this.startLine = startLine;
        this.deadline = deadline;
        this.accomplished = accomplished;
    }

    public GoalDTO() {}

    @Override
    public Long getId() {
        return super.getId();
    }

    public Long getClientID() {
        return clientID;
    }

    public String getGoalName() {
        return goalName;
    }

    public Double getPrice() {
        return price;
    }

    public Boolean getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public Date getStartLine() {
        return startLine;
    }

    public Date getDeadline() {
        return deadline;
    }

    public Boolean getAccomplished() {
        return accomplished;
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setClientID(Long clientID) {
        this.clientID = clientID;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStartLine(Date startLine) {
        this.startLine = startLine;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public void setAccomplished(Boolean accomplished) {
        this.accomplished = accomplished;
    }

    @Override
    public String toString() {
        return "GoalDTO{" +
                "clientID=" + clientID +
                ", goalName='" + goalName + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", startLine=" + startLine +
                ", deadline=" + deadline +
                ", accomplished=" + accomplished +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoalDTO)) return false;
        GoalDTO goalDTO = (GoalDTO) o;
        return Objects.equals(clientID, goalDTO.clientID) &&
                Objects.equals(goalName, goalDTO.goalName) &&
                Objects.equals(price, goalDTO.price) &&
                Objects.equals(type, goalDTO.type) &&
                Objects.equals(category, goalDTO.category) &&
                Objects.equals(startLine, goalDTO.startLine) &&
                Objects.equals(deadline, goalDTO.deadline) &&
                Objects.equals(accomplished, goalDTO.accomplished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientID, goalName, price, type, category, startLine, deadline, accomplished);
    }
}
