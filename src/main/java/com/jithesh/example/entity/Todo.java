package com.jithesh.example.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "todos")
@SequenceGenerator(name="TODO_SEQ", initialValue=10, allocationSize=100)
public class Todo {

  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="TODO_SEQ")
  private int id;

  @Column(nullable = false)
  private boolean completed;

  @Column(nullable = false)
  private String text;

  @ToString.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private TodoCategory category;
}
