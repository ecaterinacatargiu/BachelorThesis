package com.example.wally.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table("goals")
public class Goal {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="simple_user_id", referencedColumnName = "_id")
    @JsonIgnore
    private SimpleUser simpleUser;

    private String goalName;
    private Long price;
    private Boolean type;
    private String category;
    private Date startLine;
    private Date deadline;

    public Goal(SimpleUser simpleUser, String goalName, Long price, Date startLine, Date deadline)
    {
        this.simpleUser = simpleUser;
        this.goalName = goalName;
        this.type = true;
        this.category = "Wishlist";
        this.startLine = startLine;
        this.deadline = deadline;
    }

    public Long getId() {
        return id;
    }

    public SimpleUser getSimpleUser() {
        return simpleUser;
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

    public Long getPrice() {
        return price;
    }

    public Date getStartLine() {
        return startLine;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setSimpleUser(SimpleUser simpleUser) {
        this.simpleUser = simpleUser;
    }

    public void setStartLine(Date startLine) {
        this.startLine = startLine;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", simpleUser=" + simpleUser.toString() +
                ", goalName='" + goalName + '\'' +
                ", price=" + price +
                ", type=" + type +
                ", category='" + category + '\'' +
                ", startLine=" + startLine +
                ", deadline=" + deadline +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Goal)) return false;
        Goal goal = (Goal) o;
        return Objects.equals(id, goal.id) &&
                Objects.equals(simpleUser, goal.simpleUser) &&
                Objects.equals(goalName, goal.goalName) &&
                Objects.equals(price, goal.price) &&
                Objects.equals(type, goal.type) &&
                Objects.equals(category, goal.category) &&
                Objects.equals(startLine, goal.startLine) &&
                Objects.equals(deadline, goal.deadline);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, simpleUser, goalName, price, type, category, startLine, deadline);
    }
}
