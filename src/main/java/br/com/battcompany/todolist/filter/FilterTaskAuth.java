package br.com.battcompany.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.battcompany.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component //classe de gerenciamento no springboot 
public class FilterTaskAuth extends OncePerRequestFilter{

    @Autowired
    private IUserRepository userRepository;
    
    @Override
    protected void doFilterInternal(@SuppressWarnings("null") HttpServletRequest request, @SuppressWarnings("null") HttpServletResponse response, @SuppressWarnings("null") FilterChain filterChain)
            throws ServletException, IOException {

                var servletPath = request.getServletPath();                
                System.out.println("PATH: "+servletPath);
                if(servletPath.startsWith("/tasks/")){ //Para quando fazer o update não ficar nulo e poder mudar o path por conta do id da tarefa por conta do parametro
                    // Pegar a autenticação (usario e senha)
                    var authorization = request.getHeader("Authorization");
                    
                    var authEncoded = authorization.substring("Basic".length()).trim();

                    byte[] authDecode = Base64.getDecoder().decode(authEncoded);
                    
                    var authString = new String(authDecode);

                    //[jefferson_santos, 12345@SenhaSegura]
                    String[] credentials = authString.split(":");
                    String username = credentials[0];
                    String password = credentials[1];
                    
                    

                    // Validar usuario
                        var user = this.userRepository.findByUsername(username);
                        if (user == null) {
                            response.sendError(401);
                        }else{
                            // Validar senha
                            var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                            if(passwordVerify.verified){
                                // Autenticado
                                //Segue viagem
                                request.setAttribute("idUser", user.getId());
                                filterChain.doFilter(request, response);
                            }else{
                                response.sendError(401);

                        }
                        
                    }

                    }else{
                        filterChain.doFilter(request, response);
                    }
                    
            }

        }
