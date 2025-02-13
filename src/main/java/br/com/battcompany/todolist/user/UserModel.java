package br.com.battcompany.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "tb_users")
@Data //usando biblioteca lombok corrige o erro do private e consigo user o getusaername no controler

public class UserModel {
    
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true) // caso ter algum username ja existente no bd vai dar erro
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAT;
}
