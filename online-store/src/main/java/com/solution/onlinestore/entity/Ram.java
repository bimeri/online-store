package com.solution.onlinestore.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

/**
 * @author Bimeri Noel
 * @date 5/23/2023
 */

@Entity
@Table(name = "rams")
public class Ram {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Basic(optional = false)
 @Column(name = "id")
 private Long id;

 @Basic(optional = false)
 @NotNull
 @Size(min = 1, max = 255)
 @Column(name = "ramName")
 private String ramName;

 @Basic(optional = false)
 @Column(name = "capacity")
 private int capacity;

 @JoinColumn(name = "laptop_id", referencedColumnName = "id")
 @ManyToOne(optional = false)
 private Laptop laptop;

 @Column(name = "created_at")
 @Temporal(TemporalType.TIMESTAMP)
 private Date createdAt = new Date();
 @Column(name = "updated_at")
 @Temporal(TemporalType.TIMESTAMP)
 private Date updatedAt = new Date();

 public Ram(String ramName, int capacity, Laptop laptop) {
  this.ramName = ramName;
  this.capacity = capacity;
  this.laptop = laptop;
 }

 public Ram() {

 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getRamName() {
  return ramName;
 }

 public void setRamName(String ramName) {
  this.ramName = ramName;
 }

 public int getCapacity() {
  return capacity;
 }

 public void setCapacity(int capacity) {
  this.capacity = capacity;
 }

 public Laptop getLaptop() {
  return laptop;
 }

 public void setLaptop(Laptop laptop) {
  this.laptop = laptop;
 }

 public Date getCreatedAt() {
  return createdAt;
 }

 public void setCreatedAt(Date createdAt) {
  this.createdAt = createdAt;
 }

 public Date getUpdatedAt() {
  return updatedAt;
 }

 public void setUpdatedAt(Date updatedAt) {
  this.updatedAt = updatedAt;
 }

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;
  Ram ram = (Ram) o;
  return Objects.equals(id, ram.id) && Objects.equals(ramName, ram.ramName) && Objects.equals(capacity, ram.capacity) && Objects.equals(laptop, ram.laptop) && Objects.equals(createdAt, ram.createdAt) && Objects.equals(updatedAt, ram.updatedAt);
 }

 @Override
 public int hashCode() {
  return Objects.hash(id, ramName, capacity, laptop, createdAt, updatedAt);
 }

 @Override
 public String toString() {
  return "Ram{" +
          "id=" + id +
          ", ramName='" + ramName + '\'' +
          ", capacity='" + capacity + '\'' +
          ", laptop=" + laptop +
          ", createdAt=" + createdAt +
          ", updatedAt=" + updatedAt +
          '}';
 }
}
