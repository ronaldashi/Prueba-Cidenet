package com.example.demo.controller;

import com.example.demo.customHandler.ResponseHandler;
import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UsuarioServiceImpl service;


    @Operation(summary="Register an user")
    @PostMapping("/registeruser")
    public ResponseEntity<Object>registeruser(@RequestBody Usuario user){
        try {
            Usuario result=service.saveuser(user);
            return ResponseHandler.generateResponse("The user was added successfully", HttpStatus.OK,result);
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,null);
        }
    }
    @Operation(summary="Get the users by the query parameters")
    @GetMapping("/users")
    public ResponseEntity<Object>getuser(@Parameter(description = "Parameter filtered by nombreCompleto",allowEmptyValue = true)String nombreCompleto,
                                             @Parameter(description = "Parameter filtered by second nombreCompleto",allowEmptyValue = true)String tipoDocumento,
                                             @Parameter(description = "Parameter filtered by identificacion",allowEmptyValue = true)String identificacion,
                                             @Parameter(description = "Page you search")Integer page){

        try {
            return ResponseHandler.generateResponse("The records were founded successfully", HttpStatus.OK,service.getAll(nombreCompleto,tipoDocumento,identificacion,page));
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,null);
        }
    }
    @Operation(summary = "Update the user information")
    @PutMapping("/user")
    public ResponseEntity<Object>updateuser(@RequestBody Usuario user, @Parameter(description = "The id (database id) of the user you want to update")Integer idDatabase){
        try {
            return ResponseHandler.generateResponse("The user was updated successfully", HttpStatus.OK,service.updateuser(user,idDatabase));
        }catch (Exception e){
            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST,null);
        }
    }
}
