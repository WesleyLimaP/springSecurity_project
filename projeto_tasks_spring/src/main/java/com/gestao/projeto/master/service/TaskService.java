package com.gestao.projeto.master.service;

import com.gestao.projeto.master.DTO.TaskDto;
import com.gestao.projeto.master.entity.Task;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.enums.Status;
import com.gestao.projeto.master.projection.TaskProjection;
import com.gestao.projeto.master.repository.TaskRepository;
import com.gestao.projeto.master.repository.UserRepository;
import com.gestao.projeto.master.service.exceptions.RessoussesNotFoundException;
import com.gestao.projeto.master.service.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TaskDto getTaskService(long id, JwtAuthenticationToken token){
        Task task = repository.findById(id).orElseThrow(()-> new RessoussesNotFoundException("task nao encontrada"));
        User user = userRepository.findById(Long.valueOf(token.getName())).orElseThrow(()-> new RessoussesNotFoundException("user nao encontrado"));
        if(!user.hasTask(id) && !user.hasRole("ROLE_ADMIN")){
            throw new UnauthorizedException("acesso negado");
        }
        TaskDto taskDto = new TaskDto();

        taskDto.setDescription(task.getDescription());
        taskDto.setTitle(task.getTitle());
        taskDto.setStatus(task.getStatus().name());
        taskDto.setId(task.getId());

        return taskDto;
    }

    @Transactional
    public TaskDto addTask(TaskDto taskDto, JwtAuthenticationToken token){
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setStatus(Status.valueOf(taskDto.getStatus()));
        task.setTitle(taskDto.getTitle());
        User user = userRepository.findById(Long.valueOf(token.getName())).orElseThrow(()-> new RessoussesNotFoundException("user nao encontrado"));
        task.setUser(user);
        repository.save(task);

        return new TaskDto(task);
    }
    @Transactional
    public void deleteTask(Long id, JwtAuthenticationToken token){
        User user = userRepository.findById(Long.valueOf(token.getName())).orElseThrow(()-> new RessoussesNotFoundException("user nao encontrado"));
            if(!user.hasTask(id)){
                if(!user.hasRole("ROLE_ADMIN")){
                    throw new UnauthorizedException("acesso negado");
                }
                throw new RessoussesNotFoundException("task nao encontrada");
                }

        repository.deleteTask(user.getId(), id);
    }
    @Transactional
    public TaskDto updateTask(Long id, TaskDto dto, JwtAuthenticationToken token){
        Task task = repository.findById(id).orElseThrow(() -> new RessoussesNotFoundException("Task nao encontrada"));
        User user = userRepository.findById(Long.valueOf(token.getName())).orElseThrow(()-> new RessoussesNotFoundException("user nao encontrado"));
        if(!user.hasTask(id) && !user.hasRole("ROLE_ADMIN")){
            throw new UnauthorizedException("acesso negado");
        }
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(Status.valueOf(dto.getStatus()));

        task.setUser(user);

        repository.save(task);
        return new TaskDto(task);
    }

    public List<TaskDto> findAllTasks(JwtAuthenticationToken token){
        User user = userRepository.findById(Long.valueOf(token.getName())).orElseThrow(()-> new RessoussesNotFoundException("user nao encontrado"));
        List<TaskProjection> projections = repository.findAllTasks(user.getId());
        return projections.stream().map(TaskDto::new).toList();
    }


}
