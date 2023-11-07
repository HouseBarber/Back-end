package com.projetoIntegradorII.HouseBarber.controller.scheduling;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.projetoIntegradorII.HouseBarber.dto.scheduling.SchedulingDTO;
import com.projetoIntegradorII.HouseBarber.service.scheduling.SchedulingService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/v1/scheduling/")
public class SchedulingController {

    @Autowired
    private SchedulingService schedulingService;
    
    @GetMapping(value = "{id}")
    public ResponseEntity<SchedulingDTO> findById(@PathVariable Long id) {
        SchedulingDTO obj = schedulingService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<SchedulingDTO> create(@RequestBody SchedulingDTO objDTO) {
        SchedulingDTO newObj = schedulingService.create(objDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
        return ResponseEntity.created(uri).body(newObj);
    }

    @PutMapping(value = "{id}")
    public ResponseEntity<SchedulingDTO> update(@PathVariable Long id, @RequestBody SchedulingDTO objDTO) {
        SchedulingDTO obj = schedulingService.update(id, objDTO);
        return ResponseEntity.ok().body(obj);
    } 
}
