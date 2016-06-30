package de.doubleslash.example.springboot;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Role")
public class Role {

   public Role() {}

   public Role(final String role) {
      this.role = role;
   }

   @Id
   private String role;

   public String getRole() {
      return role;
   }

   public void setRole(final String role) {
      this.role = role;

   }

}