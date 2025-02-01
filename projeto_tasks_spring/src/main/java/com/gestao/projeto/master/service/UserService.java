package com.gestao.projeto.master.service;
import com.gestao.projeto.master.DTO.*;
import com.gestao.projeto.master.entity.Role;
import com.gestao.projeto.master.entity.Task;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.enums.Status;
import com.gestao.projeto.master.projection.UserProjection;
import com.gestao.projeto.master.repository.RoleRepository;
import com.gestao.projeto.master.service.exceptions.RessoussesNotFoundException;
import com.gestao.projeto.master.repository.UserRepository;
import com.gestao.projeto.master.service.exceptions.UnauthorizedException;
import com.gestao.projeto.master.service.exceptions.dbExceptions.ViolationException;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class UserService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto registerUser(UserDto userDto) {
        try {
            User user = new User();
            String passwordEncoder = this.passwordEncoder.encode(userDto.getPassword());
            user.setEmail(userDto.getEmail());
            user.setName(userDto.getName());
            user.setPassword(passwordEncoder);
            for (TaskDto taskDto : userDto.getTasks()) {
                user.getTasks().add(new Task(taskDto.getTitle(), taskDto.getDescription(), Status.valueOf(taskDto.getStatus()), user));
            }
            for (RoleDto dto : userDto.getRoles()) {
                Role role = roleRepository.findByAlthority(dto.getAuthority()).orElseThrow(() -> new IllegalIdentifierException(""));
                user.getRoles().add(role);
            }
            repository.save(user);
            return new UserDto(user);
        }
        catch (IllegalArgumentException e){
            throw new RessoussesNotFoundException("role nao encontrado");
        }
        catch (DataIntegrityViolationException e) {
            throw new ViolationException("dados invalidos");
        }


    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void removeUser(Long id, JwtAuthenticationToken token) {
        try {
            var dto = repository.findUserByIdRolesEager(id).orElseThrow(()-> new RessoussesNotFoundException("user nao encontrado"));
            var userLogado = repository.findUserByIdRolesEager(Long.valueOf(token.getName())).orElseThrow(() -> new RessoussesNotFoundException("user nao encontrado"));
            if(!dto.isMe(Long.valueOf(token.getName())) && !userLogado.hasRole("ROLE_ADMIN")){
                for(Role role: dto.getRoles()){
                    System.out.println(role.getAuthority());
                }
               throw new UnauthorizedException("permissão negada");
            }
            repository.delete(dto);


        } catch (DataIntegrityViolationException e) {
            throw new ViolationException("erro de integridade referencial");
        }


    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDto updateUser(long id, UpdateUserRequestDto dto, JwtAuthenticationToken token) {
        try {
            var userLogado = repository.findUserByIdRolesEager(Long.valueOf(token.getName())).orElseThrow(() -> new RessoussesNotFoundException("user nao encontrado"));
            User user = repository.findUserByIdRolesEager(id).orElseThrow(()-> new RessoussesNotFoundException("user nao encontrado"));
            if(!userLogado.hasRole("ROLE_ADMIN") && !user.isMe(Long.valueOf(token.getName()))){
                throw new UnauthorizedException("acesso negado");
            }
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            repository.save(user);
            return new UserDto(user);
        } catch (DataIntegrityViolationException e) {
            throw new ViolationException("email em uso");
        } catch (NoSuchElementException e) {
            throw new RessoussesNotFoundException("id nao encontrado");
        }

    }

    @Transactional
    public UserDto getUser(long id, JwtAuthenticationToken token) {
        var userLogado = repository.findUserByIdRolesEager(Long.valueOf(token.getName())).orElseThrow(() -> new RessoussesNotFoundException("user nao encontrado"));
        User user = repository.findById(id).orElseThrow(() -> new RessoussesNotFoundException("id nao encontrado"));
        if(!user.isMe(Long.valueOf(token.getName())) && !userLogado.hasRole("ROLE_ADMIN")){
            throw new UnauthorizedException("acesso negado");
        }
        return new UserDto(user);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserProjection> projection = repository.findByEmail(username);

        if (projection.isEmpty()) {
            throw new UsernameNotFoundException("email nao encontrado");
        }
        User user = new User(projection.getFirst().getId(), projection.getFirst().getUserName(), projection.getFirst().getEmail(), projection.getFirst().getPassword());
        for (UserProjection projections : projection) {
            user.getRoles().add(new Role(projections.getRoleId(), projections.getAuthority()));

        }
        return user;
    }

    @Transactional
    private Boolean correctPassword(LoginRequestDto loginRequestDto, String encoderPass) {
        return passwordEncoder.matches(loginRequestDto.password(), encoderPass);
    }

    @Transactional
    public void checkLogin(LoginRequestDto loginRequestDto, UserDetails userDetails) {
        Boolean result = correctPassword(loginRequestDto, userDetails.getPassword());
        if (!result) {
            throw new RessoussesNotFoundException("nao foi possivel fazer o login. usuario nao encontrado");

        }

    }

    
}

