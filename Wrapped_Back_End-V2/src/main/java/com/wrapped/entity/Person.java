package com.wrapped.entity;



import javax.persistence.*;

@Entity

@Table(name = "person")


public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
//    one user can have many people attached
    @ManyToOne
    @JoinColumn(name = "user_attached")
    private User userAttached;

    @ManyToOne
    @JoinColumn(name = "gift_item_attached")
    private Item itemAttachedId;

    @Column(name = "relationship")
    private String relationship;

    public Person() {
    }

    public Person(int id, String firstName, String lastName, User userAttached, Item itemAttachedId, String relationsip) {
        Id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userAttached = userAttached;
        this.itemAttachedId = itemAttachedId;
        this.relationship = relationsip;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User getUserAttached() {
        return userAttached;
    }

    public void setUserAttached(User userAttached) {
        this.userAttached = userAttached;
    }

    public Item getItemAttachedId() {
        return itemAttachedId;
    }

    public void setItemAttachedId(Item itemAttachedId) {
        this.itemAttachedId = itemAttachedId;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @Override
    public String toString() {
        return "Person{" +
                "Id=" + Id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userAttached=" + userAttached +
                ", itemAttachedId=" + itemAttachedId +
                ", relationsip='" + relationship + '\'' +
                '}';
    }
}
