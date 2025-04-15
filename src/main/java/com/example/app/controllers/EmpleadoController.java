package com.example.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.app.domain.Empleado;
import com.example.app.domain.Genero;
import com.example.app.services.EmpleadoService;

import jakarta.validation.Valid;

@RestController

@SessionAttributes("txtErr")

public class EmpleadoController {

    @Autowired(required = true)
    EmpleadoService empleadoService;

    //MOSTRAR TODOS LOS EMPLEADOS
    @GetMapping("/empleados")
    public List<Empleado> showList() {

        // Primero instanciamos la lista de empleados
        return empleadoService.obtenerTodos();
    }

    //MOSTRAR UN EMPLEADO
    @GetMapping("/empleado/{id}")
    public ResponseEntity<?> showOne(@PathVariable Long id) {
        Empleado empleado = empleadoService.obtenerPorId(id);
        return ResponseEntity.ok(empleado);
    }

    //AÑADIR UN EMPLEADO
    @PostMapping("/empleado/")
    public ResponseEntity<?> showNew(@Valid @RequestBody Empleado empleadoForm) {
        Empleado empleado = empleadoService.add(empleadoForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(empleado);
    }

    // EDITAR EMPLEADO
    @PutMapping("/empleado/{id}")
    public ResponseEntity<?> showEdit(@PathVariable Long id,
            @Valid @RequestBody Empleado empleadoForm) {

        Empleado empleadoAEditar = empleadoService.actualizar(empleadoForm);
        return ResponseEntity.status(HttpStatus.OK).body(empleadoAEditar);
    }

    // BORRAR EMPLEADO
    @DeleteMapping("/empleado/{id}")
    public ResponseEntity<?> showDelete(@PathVariable Long id) {
        empleadoService.eliminarPorId(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // cod 204
    }

    // BUSCADORES
    @GetMapping("/bysalary/{salario}")
    public ResponseEntity<?> geBySalary(@PathVariable Float salario) {

        // Obtenemos los empleados por salario
        List<Empleado> empleados = empleadoService.obtenerPorSalarioMayor(salario);
        if (empleados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(empleados);
        }
    }

    @GetMapping("/maxid")
    public ResponseEntity<?> getMaxId(Model model) {

        // Obtenemos el empleado de maxId
        Empleado empleado = empleadoService.obtenerMaxIdEmpleado();

        if (empleado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(empleado);
        }
    }

    @PostMapping("/findByName")
    public ResponseEntity<?> showFindByName(
            @RequestParam("busqueda") String busqueda) {
        // Creamos el arrayList con los empleados encontrados
        List<Empleado> empleadosEncontrados = empleadoService.buscarPorNombre(busqueda);
        if (empleadosEncontrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(empleadosEncontrados);
        }
    }

    // BÚSQUEDA POR GÉNERO
    @GetMapping("/findByGenero/{genero}")
    public ResponseEntity<?> showFindByGenero(
            @PathVariable Genero genero) {

        // Creamos el arrayList con los empleados encontrados
        List<Empleado> empleadosEncontrados = empleadoService.buscarPorGenero(genero);
        if (empleadosEncontrados.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok().body(empleadosEncontrados);
        }
    }
}
