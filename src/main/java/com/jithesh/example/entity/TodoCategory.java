package com.jithesh.example.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table(name = "categories")
@SequenceGenerator(name="CAT_SEQ", initialValue=10, allocationSize=100)
public class TodoCategory {

  @Id
  @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="CAT_SEQ")
  private int id;

  @Column(nullable = false)
  private String name;

  @ToString.Exclude
  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "category")
  List<Todo> todoList;
}
