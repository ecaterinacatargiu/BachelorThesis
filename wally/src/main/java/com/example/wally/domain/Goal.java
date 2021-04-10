package com.example.wally.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="simple_user_id", referencedColumnName = "_id")
    @JsonIgnore
    private SimpleUser simple_user;

    private String goalName;
    private Double price;
    private Boolean type;
    private String category;
    private Date startLine;
    private Date deadline;

    private Boolean accomplished;

    public Goal(SimpleUser simpleUser, String goalName, Double price, Date startLine, Date deadline, Boolean accomplished)
    {
        this.simple_user = simpleUser;
        this.goalName = goalName;
        this.price = price;
        this.type = true;
        this.category = "Wishlist";
        this.startLine = startLine;
        this.deadline = deadline;
        this.accomplished = accomplished;
    }

    public Goal() {}

    public Long getId() {
        return id;
    }

    public SimpleUser getSimpleUser() {
        return simple_user;
    }

    public String getGoalName() {
        return goalName;
    }

    public Boolean getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSimpleUser(SimpleUser simpleUser) {
        this.simple_user = simpleUser;
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
        return "Goal{" +
                "id=" + id +
                ", simpleUser=" + simple_user.toString() +
                ", goalName='" + goalName + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", startLine=" + startLine +
                ", deadline=" + deadline +
                ", accomplished" + accomplished +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goal)) return false;
        Goal goal = (Goal) o;
        return Objects.equals(id, goal.id) &&
                Objects.equals(simple_user, goal.simple_user) &&
                Objects.equals(goalName, goal.goalName) &&
                Objects.equals(price, goal.price) &&
                Objects.equals(type, goal.type) &&
                Objects.equals(category, goal.category) &&
                Objects.equals(startLine, goal.startLine) &&
                Objects.equals(deadline, goal.deadline) &&
                Objects.equals(accomplished, goal.accomplished);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, simple_user, goalName, price, type, category, startLine, deadline, accomplished);
    }
}
