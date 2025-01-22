package com.gestao.projeto.master.service;
import com.gestao.projeto.master.DTO.TaskDto;
import com.gestao.projeto.master.DTO.UserDto;
import com.gestao.projeto.master.entity.Role;
import com.gestao.projeto.master.entity.Task;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.enums.Status;
import com.gestao.projeto.master.projection.UserProjection;
import com.gestao.projeto.master.service.exceptions.RessoussesNotFoundException;
import com.gestao.projeto.master.repository.UserRepository;
import com.gestao.projeto.master.service.exceptions.dbExceptions.ViolationException;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Transactional
    public UserDto registerUser(UserDto userDto){
        User user =  new User();

        user.setEmail(userDto.getEmail());
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        for(TaskDto taskDto : userDto.getTasks()){
            user.getTasks().add(new Task(taskDto.getTitle(), taskDto.getDescription(), Status.valueOf(taskDto.getStatus()), user));
        }
        repository.save(user);
        return new UserDto(user);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void removeUser(Long id)  {
        try{
            if(!repository.existsById(id)){
                throw new RessoussesNotFoundException("id nao encontrado");
            }
            User user = repository.getReferenceById(id);
            repository.delete(user);
        }catch (DataIntegrityViolationException e){
            throw new ViolationException("erro de integridade referencial");
        }


    }
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserDto updateUser(long id, UserDto dto) {
        try {
            User user = repository.basicSelect(id).getFirst();
            user.setPassword(dto.getPassword());
            user.setName(dto.getName());
            user.setEmail(dto.getEmail());
            repository.save(user);
            return new UserDto(user);
        }catch (DataIntegrityViolationException e){
            throw new ViolationException("email em uso");
        }catch (NoSuchElementException e){
            throw new RessoussesNotFoundException("id nao encontrado");
        }

    }

    @Transactional
    public UserDto getUser (long id) {
            User user = repository.findById(id).orElseThrow(() -> new RessoussesNotFoundException("id nao encontrado" ));
            return new UserDto(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserProjection> projection = repository.findByEmail(username);

        if(projection.isEmpty()){
            throw new UsernameNotFoundException("email nao encontrado");
        }
        User user = new User(projection.getFirst().getUserName(), projection.getFirst().getEmail(), projection.getFirst().getPassword() );
        for (UserProjection projections : projection){
            user.getRoles().add(new Role(projections.getRoleId(), projections.getAuthority()));

        }
        return user;
    }
}
