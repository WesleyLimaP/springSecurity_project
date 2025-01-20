package com.gestao.projeto.master.service;

import com.gestao.projeto.master.DTO.TaskDto;
import com.gestao.projeto.master.DTO.TaskMaxDto;
import com.gestao.projeto.master.entity.Task;
import com.gestao.projeto.master.entity.User;
import com.gestao.projeto.master.enums.Status;
import com.gestao.projeto.master.repository.TaskRepository;
import com.gestao.projeto.master.repository.UserRepository;
import com.gestao.projeto.master.service.exceptions.RessoussesNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    @Autowired
    private TaskRepository repository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public TaskDto getTaskService(long id){
        Task task = repository.findById(id).orElseThrow(()-> new RessoussesNotFoundException("task nao encontrada"));
        TaskDto taskDto = new TaskDto();

        taskDto.setDescription(task.getDescription());
        taskDto.setTitle(task.getTitle());
        taskDto.setStatus(task.getStatus().name());
        taskDto.setId(task.getId());

        return taskDto;
    }

    @Transactional
    public TaskDto addTask(TaskMaxDto taskDto){
        Task task = new Task();
        task.setDescription(taskDto.getDescription());
        task.setStatus(Status.valueOf(taskDto.getStatus()));
        task.setTitle(taskDto.getTitle());
        User user = userRepository.getReferenceById(taskDto.getUserDtoId());
        task.setUser(user);
        repository.save(task);

        return new TaskDto(task);
    }
    @Transactional
    public void deleteTask(Long id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }else {
            throw new RessoussesNotFoundException("task nao encontrada");
        }

    }
    @Transactional
    public TaskDto updateTask(Long id, TaskMaxDto dto){
        Task task = repository.findById(id).orElseThrow(() -> new RessoussesNotFoundException("Task nao encontrada"));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(Status.valueOf(dto.getStatus()));

        task.setUser(userRepository.findById(dto.getUserDtoId()).orElseThrow(() -> new RessoussesNotFoundException("User nao encontrado")));

        repository.save(task);
        return new TaskDto(task);
    }


}
