package com.solution.onlinestore.entity;

import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;


/**
 * @author Bimeri Noel
 * @date 5/22/2023
 */

@Entity
@Table(name = "laptops")
public class Laptop {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Basic(optional = false)
 @Column(name = "id")
 private Long id;
 @Column(name = "available_qty")
 private Integer availableQty;
 @Basic(optional = false)
 @NotNull
 @Size(min = 1, max = 255)
 @Column(name = "model_name")
 private String modelName;
 @Column(name = "created_at")
 @Temporal(TemporalType.TIMESTAMP)
 private Date createdAt = new Date();
 @Column(name = "updated_at")
 @Temporal(TemporalType.TIMESTAMP)
 private Date updatedAt = new Date();

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public Integer getAvailableQty() {
  return availableQty;
 }

 public void setAvailableQty(Integer availableQty) {
  this.availableQty = availableQty;
 }


 public String getModelName() {
  return modelName;
 }

 public void setModelName(String modelName) {
  this.modelName = modelName;
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
  public String toString() {
   return "Laptop{" +
           "id=" + id +
           ", availableQty=" + availableQty +
           ", modelName='" + modelName + '\'' +
           ", createdAt=" + createdAt +
           ", updatedAt=" + updatedAt +
           '}';
  }

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;
  Laptop laptop = (Laptop) o;
  return Objects.equals(id, laptop.id) && Objects.equals(availableQty, laptop.availableQty) && Objects.equals(modelName, laptop.modelName) && Objects.equals(createdAt, laptop.createdAt) && Objects.equals(updatedAt, laptop.updatedAt);
 }

 @Override
 public int hashCode() {
  return Objects.hash(id, availableQty, modelName, createdAt, updatedAt);
 }
}
