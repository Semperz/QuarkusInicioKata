package edu.badpals;

import edu.badpals.domain.Item;
import edu.badpals.domain.Orden;
import edu.badpals.domain.Usuaria;
import jakarta.enterprise.context.ApplicationScoped;

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

    public List<Orden> comandaMultiple(String usuaria, List<String> objeto) {
        return null;
    }
}
