package com.example.demo.service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class UsuarioServiceImpl implements IUsuarioService{

    @Autowired
    private UsuarioRepository repository;
    @Override
    public List<Usuario> getAll(String nombreCompleto,
                                String tipoDocumento,
                                String identificacion,
                                int page) {
        System.out.println("Entra antes de"+" "+nombreCompleto);
        if(nombreCompleto.isEmpty()){
            nombreCompleto=null;
        }else{
            nombreCompleto=nombreCompleto.toUpperCase();
        }
        if(tipoDocumento.isEmpty()){
            tipoDocumento=null;
        }else{
            tipoDocumento=tipoDocumento.toUpperCase();
        }
        if(identificacion.isEmpty()){
            identificacion=null;
        }
        List<Usuario>listado= repository.findWithParametersIgnoreCase(nombreCompleto,tipoDocumento,identificacion);
        System.out.println("Entra despues de");
        System.out.println(listado.size());
       if(listado.size()<10){
           return listado;
       }else{
           List<Usuario> sublist=null;
           if(page>1) {
            int end=page*10;
            int start=end-10;
           return listado.subList(start,end);
           }else{
        return listado.subList(0,10);
           }

       }
    }

    @Override
    public Usuario findByNombreCompleto(String nombreCompleto) {
        return repository.findByNombreCompleto(nombreCompleto);
    }

    @Override
    public Usuario findByIdentificacion(String identificacion) {
        return repository.findByIdentificacion(identificacion);
    }

    @Override
    public Usuario saveuser(Usuario user)throws Exception {
        try {
         Usuario verificated=this.validationsBeforeSave(user);
        return repository.save(user);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @Override
    public boolean existByIdentificacion(String identificacion) {
        return repository.existsByIdentificacion(identificacion);
    }

    @Override
    public Usuario updateuser(Usuario user, int id)throws Exception {
        Long ids= (long) id;
        user.setId(ids);
        Usuario actual=repository.findById(ids).get();
        try{

            if(actual.getNombreCompleto().equalsIgnoreCase(user.getNombreCompleto())||
                    actual.getTipoDocumento().equalsIgnoreCase(user.getTipoDocumento())||
                    actual.getIdentificacion().equalsIgnoreCase(user.getIdentificacion())){

                user=this.validationsBeforeSave(user);
            }
        return repository.saveAndFlush(user);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }


    public Usuario validationsBeforeSave(Usuario usuario)throws Exception{

        if(!(usuario.getNombreCompleto().length()>=3 && usuario.getNombreCompleto().length()<=100)){
            throw new Exception("The name must be between 3 and 100 characters");
        }
        if(!(usuario.getTipoDocumento().length()>=1 && usuario.getTipoDocumento().length()<=2)){
            throw new Exception("The typeDocument must be between 1 and 2 characters");
        }
        if(!(usuario.getIdentificacion().length()>=3 && usuario.getIdentificacion().length()<=10)){
            throw new Exception("The identification name must be between 3 and 10 characters");
        }
        usuario.setNombreCompleto(usuario.getNombreCompleto().toUpperCase());
        usuario.setTipoDocumento(usuario.getTipoDocumento().toUpperCase());

        if(usuario.getTipoDocumento().contains("Ñ")){
            throw new Exception("The tipoDocumento cant contain an Ñ");
        }
        if(!usuario.getIdentificacion().matches("^[-\\w.]+")){
            throw new Exception("The id number cant have special characteres");
        }
        boolean existuser=this.existByIdentificacion(usuario.getIdentificacion());
        if(existuser){
            throw new Exception("The user with the id "+usuario.getIdentificacion()+" already exist");
        }

        String nombreCompleto=usuario.getNombreCompleto();
        String tipoDocumento=usuario.getTipoDocumento();
        nombreCompleto= Normalizer.normalize(nombreCompleto, Normalizer.Form.NFD);
        nombreCompleto=nombreCompleto.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        tipoDocumento= Normalizer.normalize(tipoDocumento, Normalizer.Form.NFD);
        tipoDocumento=tipoDocumento.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        usuario.setNombreCompleto(nombreCompleto);
        usuario.setTipoDocumento(tipoDocumento);
        return usuario;
    }



}
