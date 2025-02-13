package br.com.battcompany.todolist.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository <UserModel, UUID>{ //interface apenas tem a representacao dos metodos, diferente da class em que podemos implementar as funcoes
    UserModel findByUsername(String username); //vai fazer um select buscando pela coluna do username
}
