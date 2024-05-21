package edu.badpals;

import edu.badpals.domain.Item;
import edu.badpals.domain.Orden;
import edu.badpals.domain.Usuaria;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ServiceOlli {
    public ServiceOlli() {
    }

    public Usuaria cargaUsuaria(String nombre) {
        Optional<Usuaria> usuario = Usuaria.findByIdOptional(nombre);
        return usuario.isPresent()? usuario.get():new Usuaria();
    }

    public Item cargaItem(String objeto) {
        Optional<Item> item = Item.findByIdOptional(objeto);
        return item.isPresent()? item.get():new Item();
    }

    public List<Orden> cargaOrden(String usuaria_nombre) {
        return Orden.findByUserName(usuaria_nombre);
    }
    @Transactional
    public Orden comanda(String usuaria, String objeto) {
        Orden orden = null;
        Optional<Usuaria> usuario = Usuaria.findByIdOptional(usuaria);
        Optional<Item> item = Item.findByIdOptional(objeto);
        if (usuario.isPresent() && item.isPresent()
                && usuario.get().getDestreza() >= item.get().getQuality()
        ){
            orden = new Orden(usuario.get(), item.get());
            orden.persist();
        }
        return orden;
    }

    public List<Orden> comandaMultiple(String usuaria, List<String> objetos) {
        Optional<Usuaria> usuario = Usuaria.findByIdOptional(usuaria);
        if (usuario.isEmpty()){
            return Collections.emptyList();
        }
        List<Orden> comandas = new ArrayList<>();
        Orden orden = null;
        for (String objeto: objetos){
            orden = this.comanda(usuaria,objeto);
            if (orden != null){
                comandas.add(orden);
            }
        }
        return comandas;
    }
}
